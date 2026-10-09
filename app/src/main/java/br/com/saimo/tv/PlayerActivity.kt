package br.com.saimo.tv

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Base64
import android.widget.Toast
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.ForwardingPlayer
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.Tracks
import androidx.media3.common.VideoSize
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.FileDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.MergingMediaSource
import androidx.media3.exoplayer.source.SingleSampleMediaSource
import androidx.media3.session.MediaSession
import androidx.media3.ui.CaptionStyleCompat
import androidx.media3.ui.PlayerView
import androidx.media3.ui.SubtitleView
import coil3.load
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * O player de filmes e séries.
 *
 * Antes o vídeo tocava dentro da tela do acervo, com o controle padrão do
 * Media3 e uma fonte só: quando ela morria, a tela ficava preta para sempre e
 * ninguém dizia nada. Aqui o player tem a própria tela e sabe:
 *
 * - tentar todas as fontes do título, a versão escolhida primeiro, com prazo
 *   para cada uma, e dizer claramente quando nenhuma abriu;
 * - perguntar se continua de onde parou;
 * - pular abertura e recapitulação nos tempos marcados no TheIntroDB;
 * - oferecer o próximo episódio quando os créditos começam;
 * - avançar e voltar acelerando enquanto a seta fica apertada.
 */
@UnstableApi
class PlayerActivity : AppCompatActivity() {

    companion object {
        private const val TEMPORADA = "player.temporada"
        private const val EPISODIO = "player.episodio"
        private const val VERSAO = "player.versao"
        private const val DO_INICIO = "player.inicio"

        /// Fonte que não entrega imagem até aqui é fonte morta que não deu erro.
        private const val PRAZO_MS = 15_000L
        private const val ESCONDER_MS = 5_000L
        private const val CONTAGEM_S = 10
        private const val ICONE_PLAY = "\u0000play"
        private const val ICONE_REPLAY = "\u0000replay"

        fun abrir(context: Context, alvo: Alvo, temporada: Int = 0, episodio: Int = 0,
                  versao: String? = null, doInicio: Boolean = false) {
            context.startActivity(alvo.em(Intent(context, PlayerActivity::class.java))
                .putExtra(TEMPORADA, temporada).putExtra(EPISODIO, episodio)
                .putExtra(VERSAO, versao).putExtra(DO_INICIO, doInicio))
        }
    }

    private lateinit var alvo: Alvo
    private lateinit var video: PlayerView
    private lateinit var carregando: View
    private lateinit var status: TextView
    private lateinit var topo: View
    private lateinit var base: View
    private lateinit var titulo: TextView
    private lateinit var subtitulo: TextView
    private lateinit var resolucao: TextView
    private lateinit var relogio: TextView
    private lateinit var bolha: TextView
    private lateinit var linha: View
    private lateinit var agora: TextView
    private lateinit var total: TextView
    private lateinit var barra: ProgressBar
    private lateinit var botoes: LinearLayout
    private lateinit var pular: TextView
    private lateinit var proximo: View
    private lateinit var aviso: View

    private val handler = Handler(Looper.getMainLooper())
    private val hora = SimpleDateFormat("HH:mm", Locale("pt", "BR"))

    private var player: ExoPlayer? = null
    private var sessao: MediaSession? = null

    private var resolvido: Titulos.Resolvido? = null
    private var ep: Titulos.Ep? = null
    private var versao = Preferencias.versao
    private var opcoes: List<Titulos.Opcao> = emptyList()
    private var fonte = 0
    private var retomadas = 0
    private var tocou = false
    private var tentativaDesde = 0L
    private var marcas: Pulos.Marcas? = null
    /// Legendas do OpenSubtitles: as do título, a escolhida (com o arquivo já
    /// baixado) e o atraso dela. Trocar de fonte não as perde.
    private var legendasExt: List<Legendas.Opcao> = emptyList()
    private var legendaExt: Legendas.Opcao? = null
    private var legendaExtSrt: String? = null
    private var legendaAtraso = 0.0
    private var selecionarLegendaExt = false
    private var infoEpisodios: Map<Int, Detalhes.EpisodioTmdb> = emptyMap()
    /// Episódios seguidos que começaram sozinhos — o "ainda está assistindo?".
    private var seguidosSozinhos = 0
    private var comecouSozinho = false
    private var pausaAutoplayDesativada = false
    private var trechoPulado: Pulos.Trecho? = null
    private var proximoDispensado = false
    private var contagem = -1
    private var travadas = 0
    private var travouDesde = 0L
    private var reduziu = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alvo = Alvo.de(intent) ?: run { finish(); return }
        setContentView(R.layout.activity_player)
        if (Vr.ativo) Vr.videoEmTextura(this, R.id.playerVideo)
        video = findViewById(R.id.playerVideo)
        carregando = findViewById(R.id.playerCarregando)
        status = findViewById(R.id.playerStatus)
        topo = findViewById(R.id.playerTopo)
        base = findViewById(R.id.playerBase)
        titulo = findViewById(R.id.playerTitulo)
        subtitulo = findViewById(R.id.playerSubtitulo)
        resolucao = findViewById(R.id.playerResolucao)
        relogio = findViewById(R.id.playerRelogio)
        bolha = findViewById(R.id.playerBolha)
        linha = findViewById(R.id.playerLinha)
        agora = findViewById(R.id.playerAgora)
        total = findViewById(R.id.playerTotal)
        barra = findViewById(R.id.playerProgresso)
        botoes = findViewById(R.id.playerBotoes)
        pular = findViewById(R.id.playerPular)
        proximo = findViewById(R.id.playerProximo)
        aviso = findViewById(R.id.playerAviso)

        intent.getStringExtra(VERSAO)?.let { versao = it }
        titulo.text = Generos.semAno(alvo.titulo)
        status.text = getString(R.string.player_abrindo)
        estiloDaLegenda()

        pular.setOnClickListener { pularTrecho() }
        Icones.inicio(findViewById(R.id.proximoAgora), R.drawable.ic_play)
        findViewById<View>(R.id.proximoAgora).setOnClickListener { irParaProximo(sozinho = false) }
        findViewById<View>(R.id.proximoCancelar).setOnClickListener { dispensarProximo() }
        linha.setOnKeyListener { _, codigo, evento ->
            if (evento.action != KeyEvent.ACTION_DOWN) return@setOnKeyListener false
            when (codigo) {
                KeyEvent.KEYCODE_DPAD_LEFT -> { acumularPulo(-1, evento.repeatCount); true }
                KeyEvent.KEYCODE_DPAD_RIGHT -> { acumularPulo(1, evento.repeatCount); true }
                KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> { alternarPausa(); true }
                else -> false
            }
        }

        if (Vr.ativo) montarVr()

        lifecycleScope.launch(semDerrubar) { preparar() }
        handler.post(tique)
    }


    // MARK: - Óculos de VR

    /**
     * Só nos óculos (ver Vr.kt). Sem D-pad, a barra do filme era um desenho:
     * não se chegava a ela, nem se pulava, nem se escondia. Aqui pinçar sobre
     * o vídeo mostra e esconde os controles, a barra de tempo se arrasta e a
     * fileira de botões ganha voltar e 10 s para trás e para a frente.
     */
    private fun montarVr() {
        val raiz = findViewById<android.view.ViewGroup>(R.id.playerRaiz)
        Vr.camadaDeToque(raiz, 1) {
            if (base.visibility == View.VISIBLE) esconderControles() else mostrarControles(focarLinha = false)
        }
        Vr.arrastavel(linha, barra,
            aoMover = { fracao -> arrastarPara(fracao) },
            aoSoltar = { fracao ->
                arrastarPara(fracao)
                handler.removeCallbacks(confirmarPulo)
                confirmarPulo.run()
            })
    }

    /** O ponto sob o dedo vira o destino do pulo, mostrado na bolha até soltar. */
    private fun arrastarPara(fracao: Float) {
        val p = player ?: return
        val duracao = p.duration.takeIf { it > 0 && it != C.TIME_UNSET } ?: return
        val destino = (duracao * fracao).toLong().coerceIn(0, (duracao - 1_000).coerceAtLeast(0))
        alvoDoPulo = destino
        bolha.text = tempo(destino)
        bolha.visibility = View.VISIBLE
        atualizarTempo()
        adiarEsconder()
    }

    // MARK: - O que tocar

    private suspend fun preparar() {
        val r = Titulos.resolver(this, alvo)
        if (isFinishing || isDestroyed) return
        if (r == null) {
            mostrarErro(getString(R.string.player_sumiu_titulo), getString(R.string.player_sumiu_texto))
            return
        }
        resolvido = r
        val t = intent.getIntExtra(TEMPORADA, 0)
        val e = intent.getIntExtra(EPISODIO, 0)
        ep = if (r.serie) {
            (if (t > 0) r.episodio(t, e) else null) ?: Titulos.ondeContinuar(this, r)
        } else null
        comecar(pedirRetomada = !intent.getBooleanExtra(DO_INICIO, false))
    }

    /** Começa o título/episódio atual do zero ou de onde parou. */
    private fun comecar(pedirRetomada: Boolean) {
        val r = resolvido ?: return
        val fontes = ep?.fontes ?: r.filme?.fontes ?: return
        if (versao !in fontes.keys) versao = if (Preferencias.versao in fontes) Preferencias.versao
                                              else fontes.keys.first()
        opcoes = Titulos.ordem(fontes, versao)
        fonte = 0
        marcas = null
        legendasExt = emptyList()
        legendaExt = null
        legendaExtSrt = null
        legendaAtraso = 0.0
        trechoPulado = null
        proximoDispensado = false
        contagem = -1
        proximo.visibility = View.GONE
        pular.visibility = View.GONE
        esconderAviso()
        if (!r.reservado) Progresso.comecou(this, r.chave(ep), r.endereco, ep?.temporada ?: 0, ep?.numero ?: 0)
        atualizarTitulos()
        montarBotoes()
        carregarMarcas()
        carregarLegendas()

        val retomar = if (pedirRetomada) Progresso.posicao(this, r.chave(ep)) else 0L
        tocarFonte(inicio = retomar, pausado = retomar > 0)
        if (retomar > 0) perguntarRetomada(retomar)
    }

    private fun atualizarTitulos() {
        val r = resolvido ?: return
        val episodio = ep
        if (episodio == null) {
            subtitulo.text = Titulos.rotulo(versao)
            return
        }
        val nome = infoEpisodios[episodio.numero]?.nome?.takeIf { it.isNotBlank() }
        subtitulo.text = listOfNotNull("T${episodio.temporada} E${episodio.numero}", nome,
            Titulos.rotulo(versao)).joinToString(" · ")
    }

    private fun carregarMarcas() {
        val r = resolvido ?: return
        val episodio = ep
        lifecycleScope.launch(semDerrubar) {
            if (r.tmdbId > 0 && episodio != null && infoEpisodios.isEmpty()) {
                infoEpisodios = Detalhes.temporada(r.tmdbId, episodio.temporada).associateBy { it.numero }
                atualizarTitulos()
            }
            val achadas = Pulos.de(r.tmdbId, episodio?.temporada ?: 0, episodio?.numero ?: 0)
            if (episodio == ep) marcas = achadas
        }
    }

    // MARK: - Legendas do OpenSubtitles

    private fun carregarLegendas() {
        val r = resolvido ?: return
        val episodio = ep
        lifecycleScope.launch(semDerrubar) {
            val lista = Legendas.de(r.tmdbId, r.serie, episodio?.temporada ?: 0, episodio?.numero ?: 0)
            if (episodio != ep || lista.isEmpty()) return@launch
            legendasExt = lista
            // Quem já escolheu um idioma antes não precisa escolher de novo.
            val idioma = Preferencias.legendaExterna
            if (idioma.isNotBlank() && legendaExt == null) {
                lista.firstOrNull { it.idioma == idioma }?.let { escolherLegendaExterna(it, guardar = false) }
            }
        }
    }

    /** O vídeo, com a legenda externa ao lado quando há uma escolhida. */
    private fun fonteAtual(url: String): MediaSource {
        val base = Playback.mediaSource(this, Source(url))
        val opcao = legendaExt ?: return base
        val srt = legendaExtSrt ?: return base
        // Salva o arquivo no cache para evitar limites de tamanho e bugs de parsing
        // do DataSchemeDataSource do ExoPlayer com base64 gigantes.
        val arquivo = java.io.File(cacheDir, "saimo_legenda.srt")
        arquivo.writeText(Legendas.deslocar(srt, legendaAtraso), Charsets.UTF_8)
        val configuracao = MediaItem.SubtitleConfiguration.Builder(Uri.fromFile(arquivo))
            .setId(Legendas.ID_FAIXA)
            .setMimeType(MimeTypes.APPLICATION_SUBRIP)
            .setLanguage(opcao.bcp47)
            .setLabel(opcao.rotulo)
            .setSelectionFlags(C.SELECTION_FLAG_DEFAULT)
            .build()
        val legenda = SingleSampleMediaSource.Factory(FileDataSource.Factory())
            .createMediaSource(configuracao, C.TIME_UNSET)
        return MergingMediaSource(base, legenda)
    }

    /** Refaz a fonte na posição de agora, sem a telemetria de "começou". */
    private fun recarregarComLegenda() {
        val p = player ?: return
        val opcao = opcoes.getOrNull(fonte) ?: return
        val posicao = p.currentPosition
        val tocando = p.playWhenReady
        selecionarLegendaExt = legendaExt != null
        p.setMediaSource(fonteAtual(opcao.url), posicao)
        p.playWhenReady = tocando
        p.prepare()
    }

    private fun escolherLegendaExterna(opcao: Legendas.Opcao?, guardar: Boolean = true) {
        if (opcao == null) {
            if (legendaExt == null) return
            legendaExt = null
            legendaExtSrt = null
            legendaAtraso = 0.0
            recarregarComLegenda()
            return
        }
        lifecycleScope.launch(semDerrubar) {
            Toast.makeText(this@PlayerActivity, R.string.player_legenda_baixando, Toast.LENGTH_SHORT).show()
            val srt = Legendas.baixar(opcao)
            if (srt == null) {
                Toast.makeText(this@PlayerActivity, R.string.player_legenda_falhou, Toast.LENGTH_LONG).show()
                return@launch
            }
            legendaExt = opcao
            legendaExtSrt = srt
            legendaAtraso = 0.0
            video.subtitleView?.visibility = View.VISIBLE
            estiloDaLegenda()
            if (guardar) Preferencias.legendaExterna = opcao.idioma
            recarregarComLegenda()
        }
    }

    private fun ajustarAtrasoDaLegenda(segundos: Double) {
        if (legendaExt == null) return
        legendaAtraso = segundos
        recarregarComLegenda()
    }

    // MARK: - Reprodução

    private fun novoPlayer(): ExoPlayer {
        val novo = ExoPlayer.Builder(this)
            .setAudioAttributes(AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA).setContentType(C.AUDIO_CONTENT_TYPE_MOVIE).build(), true)
            .setHandleAudioBecomingNoisy(true)
            .build()
        novo.addListener(ouvinte)
        Telemetria.observar(novo)
        if (Preferencias.economia || reduziu) limitarQualidade(novo)
        aplicarLegendaPreferida(novo)
        return novo
    }

    private fun tocarFonte(inicio: Long, pausado: Boolean = false) {
        val opcao = opcoes.getOrNull(fonte) ?: run { desistir(); return }
        val atual = player ?: novoPlayer().also {
            player = it
            video.player = it
            criarSessao(it)
        }
        tocou = false
        retomadas = 0
        tentativaDesde = SystemClock.elapsedRealtime()
        mostrarCarregando(if (fonte == 0) getString(R.string.player_abrindo)
                          else getString(R.string.player_outro_servidor))
        Telemetria.comecou("vod", nomeTelemetria(), opcao.url, fonte + 1, nova = fonte == 0)
        selecionarLegendaExt = legendaExt != null
        atual.setMediaSource(fonteAtual(opcao.url))
        if (inicio > 0) atual.seekTo(inicio)
        atual.playWhenReady = !pausado
        atual.prepare()
        handler.removeCallbacks(prazo)
        handler.postDelayed(prazo, PRAZO_MS)
        resolucao.visibility = View.GONE
    }

    private fun nomeTelemetria(): String {
        val episodio = ep ?: return alvo.titulo
        return "${alvo.titulo} T${episodio.temporada}E${episodio.numero}"
    }

    /** Fonte que não abriu no prazo: vai para a seguinte. */
    private val prazo = Runnable {
        val p = player ?: return@Runnable
        if (tocou || p.playbackState == Player.STATE_READY) return@Runnable
        Telemetria.falhou("vod", nomeTelemetria(), opcoes.getOrNull(fonte)?.url.orEmpty(), fonte + 1,
            "sem imagem em ${PRAZO_MS / 1000} s")
        proximaFonte(p.currentPosition)
    }

    private fun proximaFonte(posicao: Long) {
        fonte++
        if (fonte >= opcoes.size) { desistir(); return }
        (botoes.findViewWithTag<TextView>("fonte"))?.let { b ->
            b.text = getString(R.string.player_fonte_n, fonte + 1)
            Icones.inicio(b, R.drawable.ic_swap)
        }
        tocarFonte(posicao, pausado = aviso.visibility == View.VISIBLE)
    }

    private fun desistir() {
        handler.removeCallbacks(prazo)
        Telemetria.caiu("vod", nomeTelemetria(), opcoes.size)
        player?.stop()
        val texto = if (resolvido?.serie == true) getString(R.string.player_erro_texto_serie)
                    else getString(R.string.player_erro_texto)
        mostrarErro(getString(R.string.player_erro_titulo), texto)
    }

    private val ouvinte = object : Player.Listener {
        override fun onVideoSizeChanged(videoSize: VideoSize) {
            val rotulo = rotuloResolucao(videoSize.width, videoSize.height).substringAfter(" · ")
            resolucao.text = rotulo
            resolucao.visibility = if (rotulo.isBlank()) View.GONE else View.VISIBLE
        }

        override fun onPlaybackStateChanged(state: Int) {
            val p = player ?: return
            when (state) {
                Player.STATE_READY -> {
                    handler.removeCallbacks(prazo)
                    esconderCarregando()
                    if (travouDesde > 0 && SystemClock.elapsedRealtime() - travouDesde > 5_000) travadas++
                    travouDesde = 0
                    if (travadas >= 2 && !reduziu && !Preferencias.economia) reduzirQualidade()
                    if (!tocou) {
                        tocou = true
                        Telemetria.tocou("vod", nomeTelemetria(), opcoes.getOrNull(fonte)?.url.orEmpty(),
                            fonte + 1, SystemClock.elapsedRealtime() - tentativaDesde)
                        if (base.visibility != View.VISIBLE && aviso.visibility != View.VISIBLE) mostrarControles()
                    }
                }
                Player.STATE_BUFFERING -> if (tocou) {
                    travouDesde = SystemClock.elapsedRealtime()
                    mostrarCarregando(getString(R.string.player_carregando))
                }
                Player.STATE_ENDED -> {
                    // "Acabou" antes de meio minuto não é fim de filme: é
                    // servidor que devolveu arquivo vazio ou cortado. Vai
                    // para o próximo em vez de fechar o player.
                    val duracao = p.duration.takeIf { it > 0 && it != C.TIME_UNSET } ?: 0L
                    if (p.currentPosition < 30_000 || duracao in 1 until 120_000) {
                        Telemetria.falhou("vod", nomeTelemetria(), opcoes.getOrNull(fonte)?.url.orEmpty(),
                            fonte + 1, "terminou em ${p.currentPosition / 1000} s")
                        proximaFonte(0)
                    } else {
                        terminou()
                    }
                }
                else -> Unit
            }
            if (p.isPlaying) retomadas = 0
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            video.keepScreenOn = isPlaying
            atualizarBotaoPausa()
            if (!isPlaying) guardar()
        }

        override fun onPlayerError(error: PlaybackException) {
            val p = player ?: return
            handler.removeCallbacks(prazo)
            Telemetria.falhou("vod", nomeTelemetria(), opcoes.getOrNull(fonte)?.url.orEmpty(),
                fonte + 1, error.errorCodeName)
            // Já tocava: é tropeço da origem, não fonte morta. Pede de novo o
            // mesmo pedaço antes de trocar de servidor — trocar recomeça noutro
            // ponto, às vezes com outro áudio.
            if (tocou && retomadas < 3) {
                retomadas++
                mostrarCarregando(getString(R.string.player_reconectando))
                val ponto = p.currentPosition
                p.seekTo(ponto)
                p.prepare()
                p.playWhenReady = true
                handler.postDelayed(prazo, PRAZO_MS)
                return
            }
            proximaFonte(p.currentPosition)
        }

        override fun onTracksChanged(tracks: Tracks) {
            if (selecionarLegendaExt) {
                // A faixa da legenda escolhida só existe depois de o player
                // preparar; é aqui que ela é ligada.
                val grupo = tracks.groups.firstOrNull { g ->
                    g.type == C.TRACK_TYPE_TEXT && (0 until g.length).any { i ->
                        val formato = g.getTrackFormat(i)
                        formato.id == Legendas.ID_FAIXA ||
                            formato.label == legendaExt?.rotulo ||
                            formato.language == legendaExt?.bcp47
                    }
                }
                val p = player
                if (grupo != null && p != null) {
                    selecionarLegendaExt = false
                    p.trackSelectionParameters = p.trackSelectionParameters.buildUpon()
                        .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, false)
                        .setOverrideForType(TrackSelectionOverride(grupo.mediaTrackGroup, 0)).build()
                    video.subtitleView?.visibility = View.VISIBLE
                    estiloDaLegenda()
                }
            }
            montarBotoes()
        }
    }

    // MARK: - Relógio: tempo, pulos, próximo episódio

    private val tique = object : Runnable {
        override fun run() {
            relogio.text = hora.format(Date())
            atualizarTempo()
            conferirTrechos()
            handler.postDelayed(this, 500)
        }
    }

    private var ultimoGuardado = 0L

    private fun atualizarTempo() {
        val p = player ?: return
        val duracao = p.duration.takeIf { it > 0 && it != C.TIME_UNSET } ?: 0L
        val posicao = alvoDoPulo ?: p.currentPosition
        if (duracao > 0) {
            barra.progress = (posicao * 1000 / duracao).toInt()
            barra.secondaryProgress = (p.bufferedPosition * 1000 / duracao).toInt()
            total.text = "-" + tempo(duracao - posicao)
        } else {
            total.text = ""
        }
        agora.text = tempo(posicao)
        val relogioAgora = SystemClock.elapsedRealtime()
        if (p.isPlaying && relogioAgora - ultimoGuardado > 10_000) {
            ultimoGuardado = relogioAgora
            guardar()
        }
    }

    private fun conferirTrechos() {
        val p = player ?: return
        if (!tocou || aviso.visibility == View.VISIBLE) return
        val duracao = p.duration.takeIf { it > 0 && it != C.TIME_UNSET } ?: return
        val posicao = p.currentPosition

        val trecho = marcas?.em(posicao, duracao)
        if (trecho != null && trecho != trechoPulado) {
            if (Preferencias.pularAutomatico) {
                pularTrecho(trecho)
            } else if (pular.visibility != View.VISIBLE) {
                pular.text = getString(when (trecho.tipo) {
                    Pulos.Tipo.RECAPITULACAO -> R.string.player_pular_recap
                    Pulos.Tipo.PREVIA -> R.string.player_pular_previa
                    else -> R.string.player_pular_abertura
                })
                pular.tag = trecho
                Icones.fim(pular, R.drawable.ic_skip_next)
                pular.visibility = View.VISIBLE
                if (base.visibility != View.VISIBLE) pular.requestFocus()
            }
        } else if (pular.visibility == View.VISIBLE) {
            pular.visibility = View.GONE
            if (currentFocus == null || currentFocus === pular) video.requestFocus()
        }

        val r = resolvido ?: return
        val atual = ep ?: return
        val seguinte = r.seguinte(atual) ?: return
        // O cartão sobe quando os créditos começam; sem marca no TheIntroDB,
        // quarenta segundos antes do fim.
        val creditos = marcas?.creditos?.takeIf { it > duracao / 2 } ?: (duracao - 40_000)
        if (posicao >= creditos && !proximoDispensado && proximo.visibility != View.VISIBLE) {
            mostrarProximo(seguinte)
        }
    }

    private fun pularTrecho(trecho: Pulos.Trecho? = pular.tag as? Pulos.Trecho) {
        val p = player ?: return
        val alvoTrecho = trecho ?: return
        trechoPulado = alvoTrecho
        val fim = alvoTrecho.fim ?: p.duration
        p.seekTo(fim)
        pular.visibility = View.GONE
        video.requestFocus()
    }

    private fun mostrarProximo(seguinte: Titulos.Ep) {
        val info = infoEpisodios[seguinte.numero].takeIf { seguinte.temporada == ep?.temporada }
        findViewById<TextView>(R.id.proximoTitulo).text = listOfNotNull(
            "T${seguinte.temporada} E${seguinte.numero}", info?.nome?.takeIf { it.isNotBlank() })
            .joinToString(" · ")
        val imagem = findViewById<ImageView>(R.id.proximoImagem)
        if (info?.imagem != null) imagem.load(info.imagem) else imagem.setImageDrawable(null)
        esconderControles()
        proximo.visibility = View.VISIBLE
        findViewById<View>(R.id.proximoAgora).requestFocus()
        contagem = if (Preferencias.proximoAutomatico) CONTAGEM_S else -1
        atualizarContagem()
        if (contagem > 0) handler.postDelayed(contar, 1_000)
    }

    private val contar = object : Runnable {
        override fun run() {
            if (proximo.visibility != View.VISIBLE || contagem <= 0) return
            if (player?.isPlaying == false && player?.playbackState != Player.STATE_ENDED) {
                handler.postDelayed(this, 1_000); return
            }
            contagem--
            atualizarContagem()
            if (contagem == 0) irParaProximo(sozinho = true) else handler.postDelayed(this, 1_000)
        }
    }

    private fun atualizarContagem() {
        findViewById<TextView>(R.id.proximoContagem).text =
            if (contagem > 0) getString(R.string.player_proximo_em, contagem)
            else getString(R.string.player_proximo)
    }

    private fun dispensarProximo() {
        proximoDispensado = true
        handler.removeCallbacks(contar)
        proximo.visibility = View.GONE
        video.requestFocus()
    }

    private fun terminou() {
        guardar()
        val r = resolvido
        val atual = ep
        val seguinte = if (r != null && atual != null) r.seguinte(atual) else null
        if (seguinte == null) {
            if (r?.serie == true) Progresso.finalizarSerie(this, r.nomeChave)
            mostrarRecomendacoes()
            return
        }
        if (Preferencias.proximoAutomatico && contagem != 0) {
            irParaProximo(sozinho = true)
        } else if (proximo.visibility != View.VISIBLE) {
            proximoDispensado = false
            mostrarProximo(seguinte)
        }
    }

    /** Tela pós-créditos no estilo de streaming, operável só pelo controle. */
    private fun mostrarRecomendacoes() {
        val r = resolvido
        if (r == null) { finish(); return }
        lifecycleScope.launch(semDerrubar) {
            val parecidos = if (r.tmdbId > 0) {
                runCatching { Detalhes.parecidos(this@PlayerActivity, r.tmdbId, r.serie) }
                    .getOrDefault(emptyList())
                    .map { it.achado }
            } else emptyList<Vod.Achado>()
            Generos.carregar(this@PlayerActivity)
            val candidatos = if (parecidos.isNotEmpty()) parecidos else
                runCatching { 
                    Vod.entradas(this@PlayerActivity)
                        .filter { Generos.capa(it.achado.titulo, it.achado.serie) != null }
                        .shuffled()
                        .map { it.achado } 
                }.getOrDefault(emptyList())
            val recomendados = parecidos
                .ifEmpty { candidatos }
                .filter { it.titulo.isNotBlank() && !it.titulo.equals(r.alvo.titulo, true) }
                .distinctBy { it.titulo.lowercase(Locale.ROOT) }
                .take(20)
            val acoes = mutableListOf(getString(R.string.player_voltar) to { finish() })
            mostrarAviso("Você terminou", "Mais títulos para você assistir", acoes)
            val linha = findViewById<LinearLayout>(R.id.recomendacoesLinha)
            linha.removeAllViews()
            for (achado in recomendados) {
                val alvoSugerido = Alvo(achado.titulo, achado.serie, achado.letra, achado.ano)
                val capa = ImageView(this@PlayerActivity).apply {
                    layoutParams = LinearLayout.LayoutParams(dp(190), dp(220))
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    setBackgroundResource(R.drawable.bg_card)
                    Generos.capa(achado.titulo, achado.serie)?.let { load(it) }
                }
                linha.addView(LinearLayout(this@PlayerActivity).apply {
                    orientation = LinearLayout.VERTICAL
                    gravity = android.view.Gravity.CENTER_HORIZONTAL
                    setBackgroundResource(R.drawable.botao_ficha)
                    isFocusable = true
                    setPadding(dp(4), dp(4), dp(4), dp(8))
                    layoutParams = LinearLayout.LayoutParams(dp(198), dp(270)).apply { marginEnd = dp(12) }
                    setOnClickListener { FichaActivity.abrir(this@PlayerActivity, alvoSugerido) }
                    addView(capa)
                    addView(TextView(this@PlayerActivity).apply {
                        text = Generos.semAno(achado.titulo)
                        gravity = android.view.Gravity.CENTER
                        textSize = 16f
                        maxLines = 1
                        ellipsize = android.text.TextUtils.TruncateAt.END
                        setTextColor(androidx.core.content.ContextCompat.getColor(context, R.color.text_primary))
                        layoutParams = LinearLayout.LayoutParams(dp(184), dp(38))
                    })
                })
            }
            findViewById<View>(R.id.recomendacoesScroll).visibility =
                if (linha.childCount > 0) View.VISIBLE else View.GONE
            linha.getChildAt(0)?.requestFocus()
        }
    }

    private fun irParaProximo(sozinho: Boolean) {
        val r = resolvido ?: return
        val seguinte = ep?.let { r.seguinte(it) } ?: return
        handler.removeCallbacks(contar)
        guardar()
        if (sozinho) seguidosSozinhos++ else seguidosSozinhos = 0
        if (seguinte.temporada != ep?.temporada) infoEpisodios = emptyMap()
        ep = seguinte
        comecouSozinho = sozinho
        // Sete episódios sem ninguém tocar no controle: talvez a pessoa tenha
        // dormido. Pausar aqui economiza a internet de uma noite inteira.
        if (sozinho && !pausaAutoplayDesativada && seguidosSozinhos >= 7) {
            comecar(pedirRetomada = false)
            player?.playWhenReady = false
            perguntarSeAindaAssiste()
            return
        }
        comecar(pedirRetomada = false)
    }

    private fun trocarEpisodio(alvoEp: Titulos.Ep) {
        guardar()
        if (alvoEp.temporada != ep?.temporada) infoEpisodios = emptyMap()
        seguidosSozinhos = 0
        ep = alvoEp
        comecar(pedirRetomada = true)
    }

    private fun guardar() {
        val p = player ?: return
        val r = resolvido ?: return
        if (!tocou) return
        if (r.reservado) return
        Progresso.salvar(this, r.chave(ep), p.currentPosition, p.duration)
        ProximaNaTv.atualizar(this, r, ep, p.currentPosition, p.duration)
    }

    // MARK: - Avisos

    private fun perguntarRetomada(posicao: Long) {
        mostrarAviso(getString(R.string.player_retomar_titulo),
            ep?.let { "T${it.temporada} E${it.numero}" } ?: Generos.semAno(alvo.titulo),
            listOf(
                getString(R.string.player_continuar_de, tempo(posicao)) + ICONE_PLAY to {
                    esconderAviso(); player?.playWhenReady = true
                },
                getString(R.string.player_do_inicio) + ICONE_REPLAY to {
                    esconderAviso(); player?.seekTo(0); player?.playWhenReady = true
                },
            ))
    }

    private fun perguntarSeAindaAssiste() {
        mostrarAviso(getString(R.string.player_ainda_titulo), getString(R.string.player_ainda_texto),
            listOf(
                getString(R.string.player_continuar_novamente) to {
                    seguidosSozinhos = 0; esconderAviso(); player?.playWhenReady = true
                },
                getString(R.string.player_continuar) to {
                    pausaAutoplayDesativada = true
                    seguidosSozinhos = 0
                    esconderAviso()
                    player?.playWhenReady = true
                },
                getString(R.string.player_sair) to { finish() },
            ))
    }

    private fun mostrarErro(tituloAviso: String, texto: String) {
        esconderCarregando()
        val acoes = mutableListOf<Pair<String, () -> Unit>>(
            getString(R.string.player_tentar_de_novo) to {
                esconderAviso(); fonte = 0
                if (resolvido == null) lifecycleScope.launch(semDerrubar) { preparar() }
                else tocarFonte(Progresso.posicao(this, resolvido!!.chave(ep)))
            })
        val r = resolvido
        val seguinte = ep?.let { r?.seguinte(it) }
        if (seguinte != null) acoes += getString(R.string.player_pular_para_proximo) to { irParaProximo(false) }
        acoes += getString(R.string.player_voltar) to { finish() }
        mostrarAviso(tituloAviso, texto, acoes)
    }

    private fun mostrarAviso(tituloAviso: String, texto: String, acoes: List<Pair<String, () -> Unit>>) {
        esconderControles()
        proximo.visibility = View.GONE
        findViewById<View>(R.id.recomendacoesScroll).visibility = View.GONE
        findViewById<TextView>(R.id.avisoTitulo).text = tituloAviso
        findViewById<TextView>(R.id.avisoTexto).text = texto
        val caixa = findViewById<LinearLayout>(R.id.avisoBotoes)
        caixa.removeAllViews()
        for ((rotulo, acao) in acoes) {
            caixa.addView(TextView(this).apply {
                text = rotulo.substringBefore('\u0000')
                textSize = 20f
                gravity = android.view.Gravity.CENTER
                typeface = android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL)
                setTextColor(androidx.core.content.ContextCompat.getColorStateList(context, R.color.texto_botao_ficha))
                setBackgroundResource(R.drawable.botao_ficha)
                isFocusable = true
                setPadding(0, dp(14), 0, dp(14))
                setOnClickListener { acao() }
                when {
                    rotulo.endsWith(ICONE_PLAY) -> Icones.inicio(this, R.drawable.ic_play)
                    rotulo.endsWith(ICONE_REPLAY) -> Icones.inicio(this, R.drawable.ic_replay)
                }
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT).apply { topMargin = dp(10) }
            })
        }
        aviso.visibility = View.VISIBLE
        caixa.getChildAt(0)?.requestFocus()
    }

    private fun esconderAviso() {
        aviso.visibility = View.GONE
        video.requestFocus()
    }

    private fun mostrarCarregando(texto: String) {
        status.text = texto
        carregando.visibility = View.VISIBLE
    }

    private fun esconderCarregando() {
        carregando.visibility = View.GONE
    }

    // MARK: - Barra de controle

    private fun montarBotoes() {
        botoes.removeAllViews()
        val r = resolvido
        // Nos óculos o BACK do controle não existe: sair do filme é um botão.
        if (Vr.ativo) botao(getString(R.string.vr_voltar), "voltar", R.drawable.ic_arrow_back) {
            onBackPressedDispatcher.onBackPressed()
        }
        botao(if (player?.isPlaying == true) getString(R.string.player_pausar)
              else getString(R.string.player_tocar), "pausa",
              if (player?.isPlaying == true) R.drawable.ic_pause else R.drawable.ic_play) { alternarPausa() }
        if (Vr.ativo) {
            botao(getString(R.string.vr_voltar_10), "menos10", R.drawable.ic_replay_10) { acumularPulo(-1, 0) }
            botao(getString(R.string.vr_avancar_10), "mais10", R.drawable.ic_forward_10) { acumularPulo(1, 0) }
        }
        val atual = ep
        if (r != null && atual != null) {
            botao(getString(R.string.player_episodios), "eps", R.drawable.ic_episodes) { painelEpisodios() }
            if (r.seguinte(atual) != null) botao(getString(R.string.player_proximo_ep), "prox", R.drawable.ic_skip_next) { irParaProximo(false) }
        }
        val versoes = (ep?.fontes ?: r?.filme?.fontes)?.keys.orEmpty()
        if (versoes.size > 1) {
            botao(Titulos.rotulo(versao), "versao", R.drawable.ic_translate) { painelVersao() }
        }
        if (opcoes.size > 1) {
            botao(getString(R.string.player_fonte_n, fonte + 1), "fonte", R.drawable.ic_swap) { painelFonte() }
        }
        botao(getString(R.string.player_audio_legenda), "faixas", R.drawable.ic_subtitles) { painelFaixas() }
        botao(getString(R.string.player_qualidade), "qualidade", R.drawable.ic_hd) { painelQualidade() }
        botao(getString(R.string.player_recomecar), "inicio", R.drawable.ic_replay) { player?.seekTo(0); mostrarControles() }
    }

    private fun botao(texto: String, marca: String, icone: Int, acao: () -> Unit) {
        botoes.addView(TextView(this).apply {
            text = texto
            tag = marca
            textSize = 18f
            typeface = android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL)
            setTextColor(androidx.core.content.ContextCompat.getColorStateList(context, R.color.texto_botao_ficha))
            setBackgroundResource(R.drawable.botao_player)
            maxLines = 1
            isFocusable = true
            setPadding(dp(22), dp(10), dp(22), dp(10))
            setOnClickListener { acao(); adiarEsconder() }
            Icones.inicio(this, icone)
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT).apply { marginEnd = dp(12) }
        })
    }

    private fun atualizarBotaoPausa() {
        (botoes.findViewWithTag<TextView>("pausa"))?.let { b ->
            val tocando = player?.playWhenReady == true
            b.text = getString(if (tocando) R.string.player_pausar else R.string.player_tocar)
            Icones.inicio(b, if (tocando) R.drawable.ic_pause else R.drawable.ic_play)
        }
    }

    private fun alternarPausa() {
        val p = player ?: return
        if (p.playbackState == Player.STATE_ENDED) p.seekTo(0)
        p.playWhenReady = !p.playWhenReady
        atualizarBotaoPausa()
        mostrarControles(focarLinha = false)
    }

    private fun mostrarControles(focarLinha: Boolean = true) {
        topo.visibility = View.VISIBLE
        base.visibility = View.VISIBLE
        if (focarLinha && !linha.hasFocus() && !botoes.hasFocus()) linha.requestFocus()
        adiarEsconder()
    }

    private fun esconderControles() {
        handler.removeCallbacks(esconder)
        topo.visibility = View.GONE
        base.visibility = View.GONE
        bolha.visibility = View.INVISIBLE
        if (pular.visibility == View.VISIBLE) pular.requestFocus() else video.requestFocus()
    }

    private val esconder = Runnable {
        // Pausado, a barra fica: é onde se vê em que ponto o filme parou.
        if (player?.isPlaying == true && alvoDoPulo == null) esconderControles() else adiarEsconder()
    }

    private fun adiarEsconder() {
        handler.removeCallbacks(esconder)
        handler.postDelayed(esconder, ESCONDER_MS)
    }

    // MARK: - Avançar e voltar

    /// Para onde o vídeo vai quando a pessoa soltar a seta. Nulo sem pulo pendente.
    private var alvoDoPulo: Long? = null

    /**
     * Cada toque soma um passo; segurar acelera — 10 s, depois 30 s, depois
     * um minuto. O pulo só acontece quando a seta descansa: num TV Box, pedir
     * cada pedacinho de vídeo no caminho travava a imagem.
     */
    private fun acumularPulo(sentido: Int, repeticao: Int) {
        val p = player ?: return
        val passo = when {
            repeticao < 8 -> 10_000L
            repeticao < 20 -> 30_000L
            else -> 60_000L
        }
        val duracao = p.duration.takeIf { it > 0 && it != C.TIME_UNSET } ?: Long.MAX_VALUE
        val base0 = alvoDoPulo ?: p.currentPosition
        val novo = (base0 + sentido * passo).coerceIn(0, duracao - 1_000)
        alvoDoPulo = novo
        val delta = (novo - p.currentPosition) / 1000
        bolha.text = "${tempo(novo)}   (${if (delta >= 0) "+" else "−"}${tempo(kotlin.math.abs(delta) * 1000)})"
        bolha.visibility = View.VISIBLE
        topo.visibility = View.VISIBLE
        base.visibility = View.VISIBLE
        atualizarTempo()
        handler.removeCallbacks(confirmarPulo)
        handler.postDelayed(confirmarPulo, 650)
        adiarEsconder()
    }

    private val confirmarPulo = Runnable {
        val destino = alvoDoPulo ?: return@Runnable
        alvoDoPulo = null
        player?.seekTo(destino)
        bolha.visibility = View.INVISIBLE
        atualizarTempo()
    }

    // MARK: - Painéis

    private fun painelEpisodios(temporada: Int = ep?.temporada ?: 1) {
        val r = resolvido ?: return
        lifecycleScope.launch(semDerrubar) {
            val info = if (temporada == ep?.temporada && infoEpisodios.isNotEmpty()) infoEpisodios
                       else Detalhes.temporada(r.tmdbId, temporada).associateBy { it.numero }
            val itens = mutableListOf<Painel.Item>()
            if (r.temporadas.size > 1) {
                itens += Painel.Item(getString(R.string.player_trocar_temporada, temporada),
                    getString(R.string.player_temporadas_n, r.temporadas.size)) {
                    Painel.mostrar(this@PlayerActivity, getString(R.string.player_temporadas),
                        r.temporadas.map { t ->
                            Painel.Item(getString(R.string.vod_temporada, t), marcado = t == temporada) {
                                painelEpisodios(t)
                            }
                        })
                }
            }
            for (e in r.episodios.filter { it.temporada == temporada }) {
                val chave = r.chave(e)
                val fracao = Progresso.fracao(this@PlayerActivity, chave)
                val estado = when {
                    fracao == null -> null
                    fracao >= 1f -> getString(R.string.player_visto)
                    else -> getString(R.string.player_visto_pct, (fracao * 100).toInt())
                }
                val nome = info[e.numero]?.nome?.takeIf { it.isNotBlank() }
                itens += Painel.Item(
                    listOfNotNull(getString(R.string.vod_episodio, e.numero), nome).joinToString(" · "),
                    listOfNotNull(info[e.numero]?.duracao?.let { "$it min" }, estado,
                        e.versoes.joinToString(" / ") { Titulos.rotulo(it) }).joinToString(" · "),
                    marcado = e == ep) { trocarEpisodio(e) }
            }
            Painel.mostrar(this@PlayerActivity, Generos.semAno(alvo.titulo), itens,
                getString(R.string.vod_temporada, temporada))
        }
    }

    private fun painelVersao() {
        val fontes = ep?.fontes ?: resolvido?.filme?.fontes ?: return
        Painel.mostrar(this, getString(R.string.player_versao), fontes.keys.sortedBy { if (it == "dub") 0 else 1 }.map { v ->
            Painel.Item(Titulos.rotulo(v), getString(R.string.player_servidores_n, fontes[v].orEmpty().size),
                marcado = v == versao) {
                if (v == versao) return@Item
                versao = v
                Preferencias.versao = v
                val ponto = player?.currentPosition ?: 0L
                opcoes = Titulos.ordem(fontes, versao)
                fonte = 0
                atualizarTitulos()
                montarBotoes()
                tocarFonte(ponto)
            }
        })
    }

    /**
     * Todas as fontes do título, de todas as versões, para quem quer escolher.
     *
     * O player já troca sozinho quando uma fonte não abre; aqui é para a que
     * abre mas está ruim — travando, em baixa qualidade, com áudio fora.
     * A troca continua do mesmo ponto.
     */
    private fun painelFonte() {
        Painel.mostrar(this, getString(R.string.player_fontes), opcoes.mapIndexed { i, opcao ->
            val servidor = runCatching { android.net.Uri.parse(opcao.url).host }.getOrNull()
                ?.removePrefix("www.").orEmpty()
            Painel.Item(getString(R.string.player_fonte_n, i + 1),
                listOf(Titulos.rotulo(opcao.versao), servidor).filter { it.isNotEmpty() }.joinToString(" · "),
                marcado = i == fonte) {
                if (i == fonte) return@Item
                val ponto = player?.currentPosition ?: 0L
                fonte = i
                if (opcao.versao != versao) {
                    versao = opcao.versao
                    atualizarTitulos()
                }
                montarBotoes()
                tocarFonte(ponto)
            }
        })
    }

    private fun painelFaixas() {
        player?.let {
            Faixas.audioELegenda(this, it, { estiloDaLegenda() },
                externas = legendasExt, escolhida = legendaExt, atraso = legendaAtraso,
                aoEscolherExterna = ::escolherLegendaExterna, aoAjustarAtraso = ::ajustarAtrasoDaLegenda)
        }
    }

    private fun painelQualidade() { player?.let { Faixas.qualidade(this, it) } }

    private fun limitarQualidade(p: ExoPlayer) {
        p.trackSelectionParameters = p.trackSelectionParameters.buildUpon().setMaxVideoSize(1280, 720).build()
    }

    /** Duas travadas longas seguidas: a internet não dá conta, desce para HD. */
    private fun reduzirQualidade() {
        val p = player ?: return
        reduziu = true
        limitarQualidade(p)
        android.widget.Toast.makeText(this, R.string.player_qualidade_reduzida, android.widget.Toast.LENGTH_LONG).show()
    }

    private fun aplicarLegendaPreferida(p: ExoPlayer) {
        val idioma = Preferencias.legendaIdioma
        p.trackSelectionParameters = p.trackSelectionParameters.buildUpon().apply {
            if (idioma.isBlank()) setPreferredTextLanguage(null) else setPreferredTextLanguage(idioma)
        }.build()
    }

    private fun estiloDaLegenda() {
        val vista = video.subtitleView ?: return
        val fundo = if (Preferencias.legendaFundo) 0x99000000.toInt() else 0x00000000
        vista.setStyle(CaptionStyleCompat(0xFFFFFFFF.toInt(), fundo, 0x00000000,
            CaptionStyleCompat.EDGE_TYPE_DROP_SHADOW, 0xFF000000.toInt(), null))
        val fator = floatArrayOf(0.8f, 1f, 1.25f, 1.55f)[Preferencias.legenda.coerceIn(0, 3)]
        vista.setFractionalTextSize(SubtitleView.DEFAULT_TEXT_SIZE_FRACTION * fator)
    }

    // MARK: - Voz e botões de mídia

    private fun criarSessao(p: ExoPlayer) {
        sessao = runCatching {
            // Id próprio: a tela de canais tem a dela, e duas sessões com o
            // mesmo id derrubam o app.
            MediaSession.Builder(this, object : ForwardingPlayer(p) {
                override fun seekToNext() = irParaProximo(false)
                override fun seekToNextMediaItem() = irParaProximo(false)
                override fun hasNextMediaItem() = ep?.let { resolvido?.seguinte(it) } != null
                override fun isCommandAvailable(command: Int) =
                    if (command == Player.COMMAND_SEEK_TO_NEXT || command == Player.COMMAND_SEEK_TO_NEXT_MEDIA_ITEM)
                        hasNextMediaItem() else super.isCommandAvailable(command)
            }).setId("vod").build()
        }.getOrNull()
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN && player != null && aviso.visibility != View.VISIBLE) {
            when (event.keyCode) {
                KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, KeyEvent.KEYCODE_MEDIA_PLAY,
                KeyEvent.KEYCODE_MEDIA_PAUSE -> {
                    val p = player!!
                    p.playWhenReady = when (event.keyCode) {
                        KeyEvent.KEYCODE_MEDIA_PLAY -> true
                        KeyEvent.KEYCODE_MEDIA_PAUSE -> false
                        else -> !p.playWhenReady
                    }
                    mostrarControles(focarLinha = base.visibility != View.VISIBLE); return true
                }
                KeyEvent.KEYCODE_MEDIA_FAST_FORWARD -> { acumularPulo(1, event.repeatCount + 8); return true }
                KeyEvent.KEYCODE_MEDIA_REWIND -> { acumularPulo(-1, event.repeatCount + 8); return true }
                KeyEvent.KEYCODE_MEDIA_NEXT -> { irParaProximo(false); return true }
                KeyEvent.KEYCODE_CAPTIONS -> { painelFaixas(); return true }
            }
            val controlesAVista = base.visibility == View.VISIBLE
            val cartao = proximo.visibility == View.VISIBLE || pular.hasFocus()
            if (!controlesAVista && !cartao) {
                when (event.keyCode) {
                    KeyEvent.KEYCODE_DPAD_LEFT -> { acumularPulo(-1, event.repeatCount); return true }
                    KeyEvent.KEYCODE_DPAD_RIGHT -> { acumularPulo(1, event.repeatCount); return true }
                    KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> { mostrarControles(); return true }
                    KeyEvent.KEYCODE_DPAD_UP -> { mostrarControles(); return true }
                    KeyEvent.KEYCODE_DPAD_DOWN -> {
                        if (ep != null) painelEpisodios() else mostrarControles(); return true
                    }
                    KeyEvent.KEYCODE_MENU, KeyEvent.KEYCODE_INFO, KeyEvent.KEYCODE_SETTINGS -> {
                        painelFaixas(); return true
                    }
                }
            } else if (controlesAVista) {
                adiarEsconder()
            }
        }
        return super.dispatchKeyEvent(event)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE) {
            when {
                proximo.visibility == View.VISIBLE -> { dispensarProximo(); return true }
                base.visibility == View.VISIBLE -> { esconderControles(); return true }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onStop() {
        super.onStop()
        guardar()
        player?.playWhenReady = false
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        if (player != null) Telemetria.parou()
        sessao?.release()
        player?.release()
        player = null
        super.onDestroy()
    }

    private fun tempo(ms: Long): String {
        val s = (ms / 1000).coerceAtLeast(0)
        val h = s / 3600
        val m = (s % 3600) / 60
        val seg = s % 60
        return if (h > 0) "%d:%02d:%02d".format(h, m, seg) else "%d:%02d".format(m, seg)
    }

    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()
}
