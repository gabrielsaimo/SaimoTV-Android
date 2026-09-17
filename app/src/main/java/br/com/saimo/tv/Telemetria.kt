package br.com.saimo.tv

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.media3.common.Player
import androidx.media3.common.VideoSize
import androidx.media3.common.util.UnstableApi
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.util.UUID

/**
 * O que o Saimo Monitor fica sabendo desta TV Box.
 *
 * Três coisas, e só elas: que o app abriu (com versão e modelo), o que está
 * tocando de tempos em tempos, e quando uma fonte falha, um canal cai ou o app
 * quebra. O aparelho é um UUID sorteado aqui na primeira abertura — nada de
 * ANDROID_ID, conta, nome ou IP guardado; a cidade sai da borda da Cloudflare.
 *
 * Tudo é fogo e esquece: sem rede, o monitor fica sem o dado e a TV continua
 * exatamente igual. Nenhuma chamada daqui bloqueia o vídeo.
 */
@UnstableApi
object Telemetria {

    private const val BASE = "https://saimo-monitor.gabrielsaimo68.workers.dev/v1"
    private const val PLATAFORMA = "tvbox"
    private const val PREFS = "telemetria"
    /// Zapeando, cada canal que passa não vira batida: só quem ficou.
    private const val MINIMO_PARA_CONTAR_S = 20L

    private data class Tocando(val kind: String, val titulo: String, val host: String?)

    private lateinit var app: Context
    private val handler = Handler(Looper.getMainLooper())
    private val json = "application/json; charset=utf-8".toMediaType()

    private var abertas = 0
    private var batidaMs = 300_000L
    private var tocando: Tocando? = null
    /// Só conta tempo com o vídeo andando: pausado ou carregando não é
    /// assistir. `acumuladoMs` é o que já andou desde a última batida.
    private var acumuladoMs = 0L
    private var rodandoDesde = 0L
    private var confirmado = false
    private var pausado = false
    private var qualidade: String? = null
    private var travouDesde = 0L
    private var ultimoPulo = 0L
    private var errosEnviados = 0

    private val id: String by lazy {
        val prefs = app.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.getString("id", null) ?: UUID.randomUUID().toString().also {
            prefs.edit().putString("id", it).apply()
        }
    }

    private val batida = object : Runnable {
        override fun run() {
            baterAgora()
            handler.postDelayed(this, batidaMs)
        }
    }

    /** No Application.onCreate: crash guardado e ciclo de vida do app inteiro. */
    fun iniciar(application: Application) {
        app = application
        val anterior = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, erro ->
            // commit, e não apply: o processo morre logo em seguida.
            runCatching {
                app.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit()
                    .putString("crash", "${BuildConfig.VERSION_NAME} · ${thread.name}\n" +
                        android.util.Log.getStackTraceString(erro).take(2800))
                    .commit()
            }
            anterior?.uncaughtException(thread, erro)
        }
        // Aberto é "alguma tela do app à vista", e não a MainActivity: abrir os
        // filmes para a principal, mas o app continua aberto.
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityStarted(activity: Activity) { if (abertas++ == 0) abriu() }
            override fun onActivityStopped(activity: Activity) { if (--abertas == 0) fechou() }
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }

    private fun abriu() {
        enviar("hello", JSONObject()
            .put("version", BuildConfig.VERSION_NAME)
            .put("model", "${Build.MANUFACTURER} ${Build.MODEL}".trim())
            .put("os", "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})")
            .putOpt("net", rede())
            .put("screen", app.resources.displayMetrics.let { "${it.widthPixels}x${it.heightPixels}" })
            .put("lang", java.util.Locale.getDefault().toLanguageTag())) { corpo ->
            corpo?.optLong("heartbeatSeconds")?.takeIf { it in 60..3600 }?.let { batidaMs = it * 1000 }
        }
        val prefs = app.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.getString("crash", null)?.let { pilha ->
            evento("crash", detail = pilha)
            prefs.edit().remove("crash").apply()
        }
        handler.removeCallbacks(batida)
        handler.postDelayed(batida, batidaMs)
    }

    private fun fechou() {
        handler.removeCallbacks(batida)
        baterAgora()
        if (tocando != null) evento("play_stop")
    }

    private fun baterAgora() {
        val atual = tocando
        val segundos = if (atual != null) rodandoMs() / 1000 else 0
        acumuladoMs = 0
        if (rodandoDesde > 0) rodandoDesde = SystemClock.elapsedRealtime()
        enviar("beat", JSONObject()
            .put("version", BuildConfig.VERSION_NAME)
            .put("seconds", segundos)
            .put("playing", atual?.let {
                JSONObject().put("kind", it.kind).put("title", it.titulo).put("host", it.host)
                    .put("paused", pausado).putOpt("quality", qualidade)
            } ?: JSONObject.NULL))
    }

    private fun rodandoMs(): Long =
        acumuladoMs + if (rodandoDesde > 0) SystemClock.elapsedRealtime() - rodandoDesde else 0

    private fun limparVideo() {
        acumuladoMs = 0
        rodandoDesde = 0
        confirmado = false
        pausado = false
        qualidade = null
        travouDesde = 0
    }

    /**
     * Acompanha o player: o monitor só conta tempo com o vídeo andando, bate
     * na hora quando pausa ou volta, e registra travamento (carregar depois de
     * já ter começado, menos logo depois de pular).
     */
    fun observar(player: Player) {
        player.addListener(object : Player.Listener {
            override fun onEvents(p: Player, events: Player.Events) = atualizar(p)

            override fun onVideoSizeChanged(videoSize: VideoSize) {
                if (videoSize.height > 0) qualidade = "${videoSize.height}p"
            }

            override fun onPositionDiscontinuity(
                oldPosition: Player.PositionInfo, newPosition: Player.PositionInfo, reason: Int) {
                if (reason == Player.DISCONTINUITY_REASON_SEEK) ultimoPulo = SystemClock.elapsedRealtime()
            }
        })
    }

    private fun atualizar(p: Player) {
        val atual = tocando ?: return
        val agora = SystemClock.elapsedRealtime()
        val carregando = p.playbackState == Player.STATE_BUFFERING
        if (p.playbackState == Player.STATE_READY) confirmado = true
        val pausadoAgora = !p.playWhenReady
        val andando = confirmado && p.isPlaying
        if (andando && rodandoDesde == 0L) rodandoDesde = agora
        if (!andando && rodandoDesde > 0) {
            acumuladoMs += agora - rodandoDesde
            rodandoDesde = 0
        }
        if (agora - ultimoPulo < 3000) {
            travouDesde = 0
        } else if (confirmado && carregando && !pausadoAgora) {
            if (travouDesde == 0L) travouDesde = agora
        } else if (travouDesde > 0) {
            val ms = agora - travouDesde
            travouDesde = 0
            if (ms >= 500 && !pausadoAgora) {
                enviar("event", JSONObject()
                    .put("type", "stall").put("version", BuildConfig.VERSION_NAME)
                    .put("kind", atual.kind).put("title", atual.titulo).putOpt("host", atual.host)
                    .put("ms", ms).put("detail", "$ms ms"))
            }
        }
        if (confirmado && pausadoAgora != pausado) {
            pausado = pausadoAgora
            baterAgora()
        }
    }

    /** Busca confirmada que não achou nada: ideia do que falta no catálogo. */
    fun buscouSemAchar(kind: String, termo: String) {
        val texto = termo.trim()
        if (texto.length < 3) return
        enviar("event", JSONObject()
            .put("type", "search_miss").put("version", BuildConfig.VERSION_NAME)
            .put("kind", kind).put("query", texto))
    }

    private fun rede(): String? = runCatching {
        val cm = app.getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
        val caps = cm.getNetworkCapabilities(cm.activeNetwork) ?: return null
        when {
            caps.hasTransport(android.net.NetworkCapabilities.TRANSPORT_ETHERNET) -> "cabo"
            caps.hasTransport(android.net.NetworkCapabilities.TRANSPORT_WIFI) -> "wifi"
            caps.hasTransport(android.net.NetworkCapabilities.TRANSPORT_CELLULAR) -> "movel"
            else -> "outra"
        }
    }.getOrNull()

    /**
     * Algo começou a tocar. `nova` é falso quando é só a próxima fonte do mesmo
     * canal depois de uma falha — aí não conta como mais uma abertura.
     */
    fun comecou(kind: String, titulo: String, url: String, fonte: Int, nova: Boolean = true) {
        if (!::app.isInitialized) return
        val anterior = tocando
        if (anterior != null && anterior.titulo != titulo && rodandoMs() / 1000 >= MINIMO_PARA_CONTAR_S) {
            baterAgora()
        }
        if (anterior?.titulo != titulo) limparVideo()
        // Fonte nova do mesmo título: só volta a contar quando ela abrir.
        confirmado = false
        travouDesde = 0
        tocando = Tocando(kind, titulo, host(url))
        if (nova) evento("play_start", kind, titulo, url, fonte)
    }

    fun tocou(kind: String, titulo: String, url: String, fonte: Int, ms: Long) {
        confirmado = true
        evento("play_ok", kind, titulo, url, fonte, "${ms} ms", ms)
    }

    fun falhou(kind: String, titulo: String, url: String, fonte: Int, detalhe: String) =
        evento("source_fail", kind, titulo, url, fonte, detalhe)

    fun caiu(kind: String, titulo: String, fontes: Int) =
        evento("channel_down", kind, titulo, detail = "nenhuma das $fontes fonte(s) abriu")

    fun parou() {
        if (!::app.isInitialized || tocando == null) return
        if (rodandoMs() / 1000 >= MINIMO_PARA_CONTAR_S) baterAgora()
        tocando = null
        limparVideo()
        evento("play_stop")
    }

    /** Falha engolida em segundo plano: conta, mas no máximo algumas por abertura. */
    fun erro(erro: Throwable) {
        if (!::app.isInitialized || errosEnviados++ >= 5) return
        evento("error", detail = android.util.Log.getStackTraceString(erro).take(2000))
    }

    private fun evento(tipo: String, kind: String? = null, titulo: String? = null, url: String? = null,
                       fonte: Int? = null, detail: String? = null, ms: Long? = null) {
        if (!::app.isInitialized) return
        enviar("event", JSONObject()
            .put("type", tipo)
            .put("version", BuildConfig.VERSION_NAME)
            .putOpt("kind", kind)
            .putOpt("title", titulo)
            .putOpt("host", url?.let { host(it) })
            .putOpt("source", fonte)
            .putOpt("detail", detail)
            .putOpt("ms", ms))
    }

    private fun host(url: String): String? =
        runCatching { android.net.Uri.parse(url).host?.removePrefix("www.") }.getOrNull()

    private fun enviar(rota: String, corpo: JSONObject, resposta: ((JSONObject?) -> Unit)? = null) {
        if (!::app.isInitialized) return
        runCatching {
            corpo.put("deviceId", id).put("platform", PLATAFORMA)
            val pedido = Request.Builder().url("$BASE/$rota")
                .post(corpo.toString().toRequestBody(json)).build()
            Playback.client.newCall(pedido).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) {
                    response.use { r ->
                        if (resposta == null) return
                        val lido = if (r.isSuccessful) runCatching { JSONObject(r.body!!.string()) }.getOrNull() else null
                        handler.post { resposta(lido) }
                    }
                }
            })
        }
    }
}
