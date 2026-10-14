package br.com.saimo.tv

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.TextureView
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.doOnPreDraw
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.VideoSize
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer

/**
 * A abertura animada: o logo montado peça por peça, o mesmo vídeo do Mac.
 *
 * Só na partida a frio pelo launcher. Link `saimo://`, volta do segundo plano
 * e rotação abrem direto na tela inicial. O vídeo fica por cima da tela, que
 * continua carregando por baixo (canais, guia, fichas): quando ele acaba, o
 * Início já está montado.
 *
 * TV Box de 1 GB: se o decodificador recusar o vídeo ou o primeiro quadro não
 * chegar em 1,5 s, a abertura sai de cena e a tela inicial aparece na hora.
 */
@UnstableApi
class Abertura(private val tela: Activity, private val aoTerminar: () -> Unit) {

    private val handler = Handler(Looper.getMainLooper())
    private var caixa: FrameLayout? = null
    private var textura: TextureView? = null
    private var player: ExoPlayer? = null
    private var larguraVideo = 0
    private var alturaVideo = 0
    private var saindo = false
    private var saiuEm = 0L
    private var temQuadro = false

    /** Tecla apertada para pular: a soltura dela não pode cair na tela inicial. */
    private var teclaEngolida = KeyEvent.KEYCODE_UNKNOWN

    val ativa get() = caixa != null

    private val semPrimeiroQuadro = Runnable {
        Log.i("SaimoTV", "abertura: o primeiro quadro não veio a tempo")
        sair(suave = false)
    }
    private val tetoDeTempo = Runnable { sair(suave = true) }

    fun mostrar() {
        jaMostrou = true
        val raiz = tela.findViewById<FrameLayout>(android.R.id.content)
        val fundo = FrameLayout(tela).apply {
            setBackgroundColor(ContextCompat.getColor(tela, R.color.abertura_fundo))
            // Toque em qualquer lugar pula (celular e tablet no modo toque).
            isClickable = true
            setOnClickListener { sair(suave = true) }
        }
        val video = TextureView(tela)
        fundo.addView(video, FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER))
        raiz.addView(fundo, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        fundo.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ -> cobrir() }
        caixa = fundo
        textura = video

        // A tela inicial é deitada; tablet com Android 16 ignora a trava e
        // abre em pé, e aí o vídeo 9:16 é o que cobre sem cortar o logo.
        val emPe = tela.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        val arquivo = if (emPe) R.raw.abertura_v else R.raw.abertura_h
        val endereco = "android.resource://${tela.packageName}/$arquivo".toUri()

        val novo = runCatching { ExoPlayer.Builder(tela).build() }.getOrNull()
        if (novo == null) { sair(suave = false); return }
        player = novo
        novo.volume = 0f
        novo.setVideoTextureView(video)
        novo.addListener(object : Player.Listener {
            override fun onRenderedFirstFrame() {
                temQuadro = true
                handler.removeCallbacks(semPrimeiroQuadro)
            }

            override fun onVideoSizeChanged(videoSize: VideoSize) {
                larguraVideo = videoSize.width
                alturaVideo = videoSize.height
                cobrir()
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_ENDED) sair(suave = true)
            }

            override fun onPlayerError(error: PlaybackException) {
                Log.w("SaimoTV", "abertura: o vídeo não tocou", error)
                sair(suave = false)
            }
        })
        novo.setMediaItem(MediaItem.fromUri(endereco))
        novo.prepare()
        novo.playWhenReady = true
        // O prazo conta de quando a tela aparece: num box lento o onCreate
        // leva mais que isso, e o vídeo nem teria onde desenhar ainda.
        fundo.doOnPreDraw { if (!saindo && !temQuadro) handler.postDelayed(semPrimeiroQuadro, PRAZO_PRIMEIRO_QUADRO_MS) }
        // Travou no meio do caminho (o box sem memória parou o decodificador):
        // a tela inicial não fica refém do vídeo.
        handler.postDelayed(tetoDeTempo, TETO_MS)
    }

    /**
     * Teclas enquanto a abertura está na tela: qualquer uma pula. Devolve
     * true quando a tecla era dela.
     */
    fun tecla(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_UP && event.keyCode == teclaEngolida) {
            teclaEngolida = KeyEvent.KEYCODE_UNKNOWN
            return true
        }
        // Quem apertou OK para pular no instante em que o vídeo acabou sozinho
        // não pode cair dentro do primeiro cartão.
        val carencia = saiuEm != 0L && SystemClock.uptimeMillis() - saiuEm < CARENCIA_MS
        if (!ativa && !carencia) return false
        if (event.action == KeyEvent.ACTION_DOWN) {
            teclaEngolida = event.keyCode
            sair(suave = true)
        }
        return true
    }

    /** Cobrir: o vídeo enche a tela sem faixa preta, cortando as sobras. */
    private fun cobrir() {
        val fundo = caixa ?: return
        val video = textura ?: return
        val w = fundo.width
        val h = fundo.height
        if (w == 0 || h == 0 || larguraVideo == 0 || alturaVideo == 0) return
        val escala = maxOf(w.toFloat() / larguraVideo, h.toFloat() / alturaVideo)
        val largura = (larguraVideo * escala).toInt()
        val altura = (alturaVideo * escala).toInt()
        val atual = video.layoutParams
        if (atual.width == largura && atual.height == altura) return
        video.layoutParams = FrameLayout.LayoutParams(largura, altura, Gravity.CENTER)
    }

    private fun sair(suave: Boolean) {
        if (saindo) return
        saindo = true
        handler.removeCallbacks(semPrimeiroQuadro)
        handler.removeCallbacks(tetoDeTempo)
        val fundo = caixa
        if (fundo == null || !suave) { remover(); return }
        fundo.isClickable = false
        fundo.animate().alpha(0f).setDuration(FADE_MS).withEndAction { remover() }.start()
    }

    private fun remover() {
        saiuEm = SystemClock.uptimeMillis()
        player?.release()
        player = null
        caixa?.let { (it.parent as? ViewGroup)?.removeView(it) }
        caixa = null
        textura = null
        aoTerminar()
    }

    /** A tela foi destruída com a abertura ainda rodando. */
    fun liberar() {
        handler.removeCallbacksAndMessages(null)
        player?.release()
        player = null
    }

    companion object {
        private const val PRAZO_PRIMEIRO_QUADRO_MS = 1_500L
        private const val TETO_MS = 9_000L
        private const val FADE_MS = 250L
        private const val CARENCIA_MS = 400L

        /** Uma vez por processo: voltar ao app aberto não repete o vídeo. */
        private var jaMostrou = false

        /** Partida a frio pelo launcher (ou pela lista de recentes), e só ela. */
        fun deveMostrar(intent: Intent?, estado: Bundle?): Boolean {
            if (jaMostrou || estado != null || intent == null) return false
            // Alguns launchers de TV Box mandam só o MAIN, sem categoria.
            return intent.action == Intent.ACTION_MAIN && intent.data == null
        }
    }
}
