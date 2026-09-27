package br.com.saimo.tv

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.media3.common.util.UnstableApi

/**
 * "Me avise quando começar", marcado no guia.
 *
 * O jogo começa às 21h30 e a pessoa está vendo um filme: na hora, um painel
 * aparece em cima do que estiver na tela — com o app aberto em qualquer lugar —
 * e um OK leva ao canal. Guardado em disco, para valer mesmo que o app tenha
 * sido fechado e aberto de novo antes da hora.
 */
@UnstableApi
object Lembretes {

    private const val ARQUIVO = "lembretes"
    /// Aviso um minuto antes: dá tempo de sair do que se está vendo.
    private const val ANTECEDENCIA_MS = 60_000L

    data class Lembrete(val canal: String, val titulo: String, val inicio: Long) {
        val chave get() = "$canal|$inicio"
    }

    private lateinit var app: Context
    private val handler = Handler(Looper.getMainLooper())
    private var atual: Activity? = null

    fun iniciar(application: Application) {
        app = application
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityResumed(activity: Activity) { atual = activity; conferir() }
            override fun onActivityPaused(activity: Activity) { if (atual === activity) atual = null }
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
        handler.postDelayed(relogio, 30_000)
    }

    private val relogio = object : Runnable {
        override fun run() {
            conferir()
            handler.postDelayed(this, 30_000)
        }
    }

    private fun prefs() = app.getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE)

    fun todos(): List<Lembrete> = prefs().all.mapNotNull { (chave, valor) ->
        val partes = chave.split("|")
        val inicio = partes.getOrNull(1)?.toLongOrNull() ?: return@mapNotNull null
        Lembrete(partes[0], valor as? String ?: "", inicio)
    }

    fun tem(canal: String, inicio: Long) = prefs().contains("$canal|$inicio")

    /** Marca ou desmarca. Devolve o estado novo. */
    fun alternar(lembrete: Lembrete): Boolean {
        if (tem(lembrete.canal, lembrete.inicio)) {
            prefs().edit().remove(lembrete.chave).apply()
            return false
        }
        prefs().edit().putString(lembrete.chave, lembrete.titulo).apply()
        return true
    }

    private fun conferir() {
        if (!::app.isInitialized) return
        val tela = atual ?: return
        val agora = System.currentTimeMillis()
        val lista = todos()
        // Os que passaram há mais de meia hora não interessam mais.
        lista.filter { it.inicio < agora - 30 * 60_000 }.forEach { prefs().edit().remove(it.chave).apply() }
        val vencido = lista.firstOrNull { it.inicio - ANTECEDENCIA_MS <= agora && it.inicio >= agora - 30 * 60_000 }
            ?: return
        prefs().edit().remove(vencido.chave).apply()
        Painel.mostrar(tela, tela.getString(R.string.lembrete_titulo, vencido.titulo), listOf(
            Painel.Item(tela.getString(R.string.lembrete_assistir, vencido.canal)) {
                tela.startActivity(Intent(tela, MainActivity::class.java)
                    .putExtra(MainActivity.EXTRA_CANAL, vencido.canal)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
            },
            Painel.Item(tela.getString(R.string.lembrete_depois)) {},
        ), tela.getString(R.string.lembrete_sub, vencido.canal))
    }
}
