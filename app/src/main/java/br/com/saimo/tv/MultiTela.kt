package br.com.saimo.tv

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.TrackSelectionParameters
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.upstream.DefaultBandwidthMeter
import androidx.media3.ui.PlayerView

/**
 * A metade direita da multi tela: um segundo canal ao vivo ao lado do
 * principal, como no Mac e no Windows.
 *
 * O player só existe enquanto a multi tela está aberta — sair dela, ir para
 * outra tela ou fechar o app o solta na hora. Num TV Box de 1 GB um segundo
 * player parado ainda segura decodificador e buffer, e isso é memória que o
 * canal principal precisa.
 *
 * A troca de fonte é a versão curta da do canal principal: fonte que não abre
 * em 12 s ou que dá erro desce para a seguinte; a que estava tocando e caiu é
 * retomada algumas vezes antes. Acabadas as fontes, a metade avisa e espera a
 * pessoa escolher outro canal — não fica tentando para sempre por trás.
 */
@UnstableApi
class SegundaTela(
    private val tela: Activity,
    private val vista: PlayerView,
    /// O selo do topo da metade: logo (ou a inicial) e nome do canal.
    private val rotulo: View,
    private val nome: TextView,
    private val logo: ImageView,
    private val inicial: TextView,
    private val aviso: TextView,
    private val numero: (Channel) -> Int,
    /// O aparelho não deu conta de dois vídeos: quem chama fecha a multi tela.
    private val semSuporte: () -> Unit,
) {
    companion object {
        private const val PRAZO_MS = 12_000L
        private const val RETOMADAS_MAX = 3

        /**
         * Teto de imagem por metade. Cada uma ocupa meia tela, então 720p já
         * sobra; descer do 1080p é o que deixa dois decodificadores caberem
         * num TV Box fraco. Nos de pouca memória, 480p.
         */
        fun limite(base: TrackSelectionParameters): TrackSelectionParameters =
            if (Aparelho.poucaMemoria) base.buildUpon().setMaxVideoSize(854, 480).build()
            else base.buildUpon().setMaxVideoSize(1280, 720).build()

        /**
         * Erro do decodificador, não da fonte: no aparelho com um decodificador
         * de vídeo só, o segundo canal falha assim, em qualquer servidor.
         */
        fun faltaDecodificador(erro: PlaybackException): Boolean = erro.errorCode in setOf(
            PlaybackException.ERROR_CODE_DECODER_INIT_FAILED,
            PlaybackException.ERROR_CODE_DECODING_FAILED,
            PlaybackException.ERROR_CODE_DECODING_FORMAT_EXCEEDS_CAPABILITIES,
            PlaybackException.ERROR_CODE_DECODING_RESOURCES_RECLAIMED,
        )

        /**
         * O selo de uma metade: logo pequeno, número e nome. Sem logo (ou se
         * ele não carregar), a inicial do canal no lugar — o mesmo jeito dos
         * cartões da tela inicial.
         */
        fun preencherRotulo(nome: TextView, logo: ImageView, inicial: TextView, canal: Channel, numero: Int) {
            nome.text = listOfNotNull(numero.takeIf { it > 0 }?.toString(), canal.name).joinToString("  ")
            inicial.text = canal.name.trim().firstOrNull()?.uppercase().orEmpty()
            Cartoes.carregar(logo, inicial, canal.logo)
        }
    }

    private val handler = Handler(Looper.getMainLooper())
    private var player: ExoPlayer? = null

    var canal: Channel? = null
        private set
    private var fonte = 0
    /** A fonte no ar, para a faixa do canal dizer qual temporada/versão é. */
    val fonteAtual: Int get() = fonte
    private var tocou = false
    private var retomadas = 0
    /// Fontes desta volta que caíram por falta de decodificador. Se foram
    /// todas, o problema é o aparelho, não o canal.
    private var falhasDeDecodificador = 0

    /** Se esta metade está mostrando imagem agora. */
    val tocando: Boolean get() = player?.isPlaying == true

    /** O atraso em relação à borda do ao vivo em ms, ou valor padrão de buffer da TV Box. */
    val liveOffset: Long
        get() {
            val p = player ?: return 12_000L
            if (p.currentLiveOffset != androidx.media3.common.C.TIME_UNSET && p.currentLiveOffset > 0) return p.currentLiveOffset
            if (!p.currentTimeline.isEmpty) {
                val w = androidx.media3.common.Timeline.Window()
                p.currentTimeline.getWindow(p.currentMediaItemIndex, w)
                if (w.durationMs != androidx.media3.common.C.TIME_UNSET && w.durationMs > 0) {
                    return (w.durationMs - p.currentPosition).coerceAtLeast(0L)
                }
            }
            return 12_000L
        }

    /** Só a metade ativa tem som; a outra fica muda, mas continua tocando. */
    var comSom: Boolean = false
        set(valor) {
            field = valor
            player?.volume = if (valor) 1f else 0f
        }

    private val prazo = Runnable {
        Log.w("SaimoTV", "segunda tela: sem imagem em ${PRAZO_MS / 1000} s")
        avancar()
    }

    /** Cria o player. Falso se o aparelho não deixou nem criar. */
    fun ligar(): Boolean {
        if (player != null) return true
        val novo = runCatching {
            // Buffer menor que o do canal principal: são dois enchendo ao
            // mesmo tempo, e no aparelho de 1 GB a soma é o que derruba o app.
            val buffer = if (Aparelho.poucaMemoria) {
                DefaultLoadControl.Builder()
                    .setBufferDurationsMs(10_000, 20_000, 1_500, 3_000)
                    .setTargetBufferBytes(16 * 1024 * 1024)
                    .setPrioritizeTimeOverSizeThresholds(false)
                    .build()
            } else {
                DefaultLoadControl.Builder()
                    .setBufferDurationsMs(15_000, 40_000, 1_500, 3_000)
                    .build()
            }
            ExoPlayer.Builder(tela)
                .setBandwidthMeter(DefaultBandwidthMeter.Builder(tela)
                    .setInitialBitrateEstimate(4_000_000L).build())
                .setLoadControl(buffer)
                .build()
        }.onFailure { Log.w("SaimoTV", "segunda tela: player não criado", it) }.getOrNull() ?: return false
        novo.trackSelectionParameters = limite(novo.trackSelectionParameters)
        novo.volume = if (comSom) 1f else 0f
        novo.playWhenReady = true
        novo.addListener(ouvinte)
        Upscaling.aplicar(novo)
        player = novo
        vista.player = novo
        canal = null
        rotulo.visibility = View.GONE
        mostrarAviso(tela.getString(R.string.multi_tela_escolha))
        return true
    }

    /** Põe um canal nesta metade, a partir da primeira fonte. */
    fun tocar(novo: Channel) {
        canal = novo
        falhasDeDecodificador = 0
        preencherRotulo(nome, logo, inicial, novo, numero(novo))
        rotulo.visibility = View.VISIBLE
        abrir(0, retomando = false)
        Connect.notificarMudancaDeEstado()
    }

    /** Tira o canal (ex.: ele sumiu da lista) e volta a pedir um. */
    fun limpar() {
        handler.removeCallbacks(prazo)
        canal = null
        player?.stop()
        player?.clearMediaItems()
        rotulo.visibility = View.GONE
        mostrarAviso(tela.getString(R.string.multi_tela_escolha))
        Connect.notificarMudancaDeEstado()
    }

    /** Solta o player e tudo o que ele segura. Pode ser chamado mais de uma vez. */
    fun soltar() {
        handler.removeCallbacksAndMessages(null)
        vista.player = null
        player?.removeListener(ouvinte)
        player?.release()
        player = null
        canal = null
    }

    private fun abrir(indice: Int, retomando: Boolean) {
        val atual = canal ?: return
        val p = player ?: return
        val origem = atual.sources.getOrNull(indice) ?: return
        fonte = indice
        tocou = false
        if (!retomando) retomadas = 0
        mostrarAviso(tela.getString(if (indice > 0 || retomando) R.string.loading_outra else R.string.loading))
        handler.removeCallbacks(prazo)
        handler.postDelayed(prazo, PRAZO_MS)
        p.setMediaSource(Playback.mediaSource(tela, origem))
        p.prepare()
        p.playWhenReady = true
        Connect.notificarMudancaDeEstado()
    }

    /** A fonte não serviu: vai para a seguinte, ou desiste com um aviso. */
    private fun avancar() {
        val atual = canal ?: return
        if (fonte + 1 < atual.sources.size) {
            abrir(fonte + 1, retomando = false)
            return
        }
        handler.removeCallbacks(prazo)
        player?.stop()
        if (falhasDeDecodificador >= atual.sources.size) {
            semSuporte()
            return
        }
        mostrarAviso(tela.getString(R.string.multi_tela_sem_sinal))
    }

    private fun mostrarAviso(texto: String) {
        aviso.text = texto
        aviso.visibility = View.VISIBLE
    }

    private val ouvinte = object : Player.Listener {
        override fun onPlaybackStateChanged(state: Int) {
            when (state) {
                Player.STATE_READY -> {
                    tocou = true
                    retomadas = 0
                    falhasDeDecodificador = 0
                    handler.removeCallbacks(prazo)
                    aviso.visibility = View.GONE
                    Connect.notificarMudancaDeEstado()
                }
                // Ao vivo não termina: fim de arquivo é a conexão que fechou
                // sem erro. Reabre a mesma fonte.
                Player.STATE_ENDED -> if (tocou) abrir(fonte, retomando = true)
                else -> Unit
            }
        }

        override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
            // Ao vivo não pausa, nesta metade também.
            if (!playWhenReady) player?.playWhenReady = true
        }

        override fun onPlayerError(error: PlaybackException) {
            Log.w("SaimoTV", "segunda tela: ${error.errorCodeName}")
            handler.removeCallbacks(prazo)
            if (faltaDecodificador(error)) {
                falhasDeDecodificador++
                // Já tocou aqui e o decodificador foi tomado: não é a fonte.
                if (tocou) { semSuporte(); return }
                avancar()
                return
            }
            // Estava tocando: tropeço de rede, retoma a mesma antes de trocar.
            val ficouParaTras = error.errorCode == PlaybackException.ERROR_CODE_BEHIND_LIVE_WINDOW
            if ((tocou || ficouParaTras) && retomadas < RETOMADAS_MAX) {
                retomadas++
                abrir(fonte, retomando = true)
                return
            }
            avancar()
        }
    }
}
