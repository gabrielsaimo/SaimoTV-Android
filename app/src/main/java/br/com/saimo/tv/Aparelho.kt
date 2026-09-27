package br.com.saimo.tv

import android.app.ActivityManager
import android.content.Context

/**
 * O quanto o aparelho aguenta.
 *
 * Um Fire TV Stick tem 1 GB para o sistema inteiro — a tela inicial da Amazon,
 * os serviços dela e este app. Quando a memória aperta, o sistema não avisa:
 * mata o processo, e para quem assiste o app "fecha sozinho". Não há exceção,
 * então a telemetria nunca viu nada — foi a queixa de um usuário de Fire TV
 * que navegava na lista de canais por alguns minutos.
 *
 * O que mais pesa nesse tempo é o buffer do canal, que cresce enquanto a
 * lista está aberta, e o cache de logos. Em aparelho de pouca memória os dois
 * ficam menores; nos TV Box com 2 GB ou mais nada muda.
 */
object Aparelho {

    /// 1,5 GB: pega os Fire TV Stick de 1 GB e os TV Box genéricos de 1 GB,
    /// que o sistema às vezes reporta com um pouco a mais.
    private const val LIMITE_BYTES = 1_536L * 1024 * 1024

    @Volatile
    var poucaMemoria: Boolean = false
        private set

    fun conhecer(context: Context) {
        val gerente = context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
            ?: return
        val info = ActivityManager.MemoryInfo()
        gerente.getMemoryInfo(info)
        poucaMemoria = gerente.isLowRamDevice || info.totalMem in 1..LIMITE_BYTES
    }
}
