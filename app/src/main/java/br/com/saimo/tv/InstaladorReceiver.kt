package br.com.saimo.tv

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.widget.Toast

/**
 * Leva adiante a instalação da atualização.
 *
 * O PackageInstaller não instala nada por conta própria quando quem pede não é
 * uma loja: ele responde com `STATUS_PENDING_USER_ACTION` e um Intent, e cabe
 * ao app abrir essa tela. Sem isto o download terminava, a sessão era criada e
 * ali morria — nada aparecia para confirmar, e a versão continuava a antiga.
 */
class InstaladorReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.getIntExtra(PackageInstaller.EXTRA_STATUS, -1)) {
            PackageInstaller.STATUS_PENDING_USER_ACTION -> {
                val confirmar = if (android.os.Build.VERSION.SDK_INT >= 33) {
                    intent.getParcelableExtra(Intent.EXTRA_INTENT, Intent::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    intent.getParcelableExtra(Intent.EXTRA_INTENT) as Intent?
                }
                confirmar?.let {
                    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    // Um receiver que lança exceção derruba o processo inteiro;
                    // aparelho sem a tela de confirmação vira aviso, não fechamento.
                    runCatching { context.startActivity(it) }.onFailure { erro ->
                        Toast.makeText(context,
                            context.getString(R.string.update_falhou, erro.message.orEmpty()),
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
            PackageInstaller.STATUS_SUCCESS -> Unit // O sistema reabre o app.
            PackageInstaller.STATUS_FAILURE_ABORTED -> Unit // Quem cancelou sabe que cancelou.
            else -> {
                val motivo = intent.getStringExtra(PackageInstaller.EXTRA_STATUS_MESSAGE)
                Toast.makeText(context,
                    context.getString(R.string.update_falhou, motivo.orEmpty()),
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}
