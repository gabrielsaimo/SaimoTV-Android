package br.com.saimo.tv

import android.os.Handler
import android.os.Looper
import androidx.media3.common.util.UnstableApi

/**
 * "Desligar em 60 minutos", para quem dorme com a TV ligada.
 *
 * Um minuto antes, um aviso com a opção de ganhar mais meia hora; sem resposta,
 * o app fecha e a TV volta à tela inicial — onde o protetor de tela ou o
 * desligamento automático dela assumem.
 */
@UnstableApi
object TimerDeSono {

    private val handler = Handler(Looper.getMainLooper())
    var fimEm: Long = 0L
        private set

    val ativo: Boolean get() = fimEm > System.currentTimeMillis()

    fun ligar(minutos: Int) {
        desligar()
        if (minutos <= 0) return
        fimEm = System.currentTimeMillis() + minutos * 60_000L
        handler.postDelayed(avisar, (minutos * 60_000L - 60_000L).coerceAtLeast(0))
        handler.postDelayed(fechar, minutos * 60_000L)
    }

    fun desligar() {
        fimEm = 0L
        handler.removeCallbacks(avisar)
        handler.removeCallbacks(fechar)
    }

    fun restanteMin(): Int = ((fimEm - System.currentTimeMillis()) / 60_000L).toInt().coerceAtLeast(0) + 1

    private val avisar = Runnable {
        val tela = Lembretes.atual ?: return@Runnable
        Painel.mostrar(tela, tela.getString(R.string.timer_aviso), listOf(
            Painel.Item(tela.getString(R.string.timer_mais_30)) { ligar(30) },
            Painel.Item(tela.getString(R.string.timer_cancelar)) { desligar() },
        ))
    }

    private val fechar = Runnable {
        fimEm = 0L
        Lembretes.atual?.finishAffinity()
    }
}
