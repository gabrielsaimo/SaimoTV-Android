package br.com.saimo.tv

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
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
    private var contandoDesde = 0L
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
            .put("os", "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})")) { corpo ->
            corpo?.optLong("heartbeatSeconds")?.takeIf { it in 60..3600 }?.let { batidaMs = it * 1000 }
        }
        val prefs = app.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.getString("crash", null)?.let { pilha ->
            evento("crash", detail = pilha)
            prefs.edit().remove("crash").apply()
        }
        contandoDesde = SystemClock.elapsedRealtime()
        handler.removeCallbacks(batida)
        handler.postDelayed(batida, batidaMs)
    }

    private fun fechou() {
        handler.removeCallbacks(batida)
        baterAgora()
        if (tocando != null) evento("play_stop")
    }

    private fun baterAgora() {
        val agora = SystemClock.elapsedRealtime()
        val atual = tocando
        val segundos = if (atual != null) (agora - contandoDesde) / 1000 else 0
        contandoDesde = agora
        enviar("beat", JSONObject()
            .put("version", BuildConfig.VERSION_NAME)
            .put("seconds", segundos)
            .put("playing", atual?.let {
                JSONObject().put("kind", it.kind).put("title", it.titulo).put("host", it.host)
            } ?: JSONObject.NULL))
    }

    /**
     * Algo começou a tocar. `nova` é falso quando é só a próxima fonte do mesmo
     * canal depois de uma falha — aí não conta como mais uma abertura.
     */
    fun comecou(kind: String, titulo: String, url: String, fonte: Int, nova: Boolean = true) {
        if (!::app.isInitialized) return
        val anterior = tocando
        val agora = SystemClock.elapsedRealtime()
        if (anterior != null && anterior.titulo != titulo) {
            val segundos = (agora - contandoDesde) / 1000
            if (segundos >= MINIMO_PARA_CONTAR_S) baterAgora()
        }
        if (anterior?.titulo != titulo) contandoDesde = agora
        tocando = Tocando(kind, titulo, host(url))
        if (nova) evento("play_start", kind, titulo, url, fonte)
    }

    fun tocou(kind: String, titulo: String, url: String, fonte: Int, ms: Long) =
        evento("play_ok", kind, titulo, url, fonte, "${ms} ms")

    fun falhou(kind: String, titulo: String, url: String, fonte: Int, detalhe: String) =
        evento("source_fail", kind, titulo, url, fonte, detalhe)

    fun caiu(kind: String, titulo: String, fontes: Int) =
        evento("channel_down", kind, titulo, detail = "nenhuma das $fontes fonte(s) abriu")

    fun parou() {
        if (!::app.isInitialized || tocando == null) return
        val segundos = (SystemClock.elapsedRealtime() - contandoDesde) / 1000
        if (segundos >= MINIMO_PARA_CONTAR_S) baterAgora()
        tocando = null
        evento("play_stop")
    }

    /** Falha engolida em segundo plano: conta, mas no máximo algumas por abertura. */
    fun erro(erro: Throwable) {
        if (!::app.isInitialized || errosEnviados++ >= 5) return
        evento("error", detail = android.util.Log.getStackTraceString(erro).take(2000))
    }

    private fun evento(tipo: String, kind: String? = null, titulo: String? = null, url: String? = null,
                       fonte: Int? = null, detail: String? = null) {
        if (!::app.isInitialized) return
        enviar("event", JSONObject()
            .put("type", tipo)
            .put("version", BuildConfig.VERSION_NAME)
            .putOpt("kind", kind)
            .putOpt("title", titulo)
            .putOpt("host", url?.let { host(it) })
            .putOpt("source", fonte)
            .putOpt("detail", detail))
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
