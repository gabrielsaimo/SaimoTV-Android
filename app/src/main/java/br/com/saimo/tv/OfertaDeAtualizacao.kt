package br.com.saimo.tv

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

/**
 * Oferece a versão nova, quando há uma, e cuida da instalação.
 *
 * Morava dentro da tela de canais. Com a tela de escolha na abertura, quem só
 * assiste filmes nunca passava por ela — e nunca era avisado de versão nova.
 * Aqui a mesma oferta serve a qualquer tela que a chame.
 *
 * Pergunta em vez de trocar sozinho: atualizar é assunto de quem assiste.
 */
class OfertaDeAtualizacao(
    private val tela: AppCompatActivity,
    /// Onde mostrar o andamento do download.
    private val status: (String) -> Unit,
) {

    /// Uma oferta por vez: a abertura e a volta das configurações podem pedir
    /// juntas, e dois diálogos empilhados confundem quem está no controle.
    private var ofertando = false
    var baixando = false
        private set

    fun ofertar() {
        if (ofertando) return
        ofertando = true
        tela.lifecycleScope.launch(semDerrubar) {
            val versao = try {
                Atualizacao.procurar(tela)
            } finally {
                ofertando = false
            } ?: return@launch
            // A consulta leva segundos; mostrar diálogo numa tela que já
            // fechou é BadTokenException.
            if (tela.isFinishing || tela.isDestroyed) return@launch
            ofertando = true
            android.app.AlertDialog.Builder(tela)
                .setTitle(tela.getString(R.string.update_titulo, versao.numero))
                .setMessage(
                    listOf(tela.getString(R.string.update_atual, BuildConfig.VERSION_NAME),
                           versao.notas.take(400))
                        .filter { it.isNotBlank() }.joinToString("\n\n"))
                .setPositiveButton(R.string.update_agora) { _, _ -> baixar(versao) }
                .setNegativeButton(R.string.update_depois) { _, _ ->
                    Atualizacao.adiar(versao)
                    Atualizacao.esquecerPendente(tela)
                }
                .setNeutralButton(R.string.update_pular) { _, _ ->
                    Atualizacao.pular(tela, versao)
                }
                .setOnDismissListener { ofertando = false }
                .show()
        }
    }

    /**
     * Confere sem interromper: com canal no ar, uma janela no meio do jogo é
     * pior que esperar. Havendo versão nova, só um aviso discreto — a oferta
     * de verdade aparece na abertura ou em Ajustes.
     */
    fun avisarSemInterromper() {
        if (ofertando || baixando) return
        tela.lifecycleScope.launch(semDerrubar) {
            val versao = Atualizacao.procurar(tela) ?: return@launch
            if (tela.isFinishing || tela.isDestroyed) return@launch
            android.widget.Toast.makeText(tela,
                tela.getString(R.string.update_discreto, versao.numero), android.widget.Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Voltando das configurações com a permissão já ligada (nos aparelhos em
     * que o sistema não matou o app no caminho), retoma a atualização.
     */
    fun retomarSePendente() {
        if (!baixando && Atualizacao.temPendente(tela) && podeInstalar()) ofertar()
    }

    private fun baixar(versao: Atualizacao.Versao) {
        Atualizacao.marcarPendente(tela, versao)
        if (!podeInstalar()) {
            pedirPermissaoDeInstalar()
            return
        }
        if (baixando) return
        baixando = true
        status(tela.getString(R.string.update_baixando, 0))
        tela.lifecycleScope.launch(semDerrubar) {
            try {
                val erro = Atualizacao.instalar(tela, versao) { fracao ->
                    tela.runOnUiThread {
                        status(tela.getString(R.string.update_baixando, (fracao * 100).toInt()))
                    }
                }
                status(erro ?: tela.getString(R.string.update_instalando))
            } finally {
                baixando = false
            }
        }
    }

    /// Do Android 8 em diante instalar APK pede a chave "apps desconhecidos"
    /// ligada para este app em particular; antes era uma chave só, do sistema,
    /// que a própria tela de instalação já oferece.
    private fun podeInstalar(): Boolean =
        Build.VERSION.SDK_INT < 26 || tela.packageManager.canRequestPackageInstalls()

    /**
     * Explica antes de mandar para as configurações.
     *
     * Deixar o instalador descobrir sozinho dava na pior experiência possível:
     * a pessoa ligava a chave, o sistema matava o app por ter mudado a
     * permissão, e parecia que a TV tinha travado no meio da atualização.
     */
    private fun pedirPermissaoDeInstalar() {
        if (tela.isFinishing || tela.isDestroyed) return
        android.app.AlertDialog.Builder(tela)
            .setTitle(R.string.update_permissao_titulo)
            .setMessage(R.string.update_permissao_texto)
            .setPositiveButton(R.string.update_permissao_abrir) { _, _ -> abrirPermissaoDeInstalar() }
            .setNegativeButton(R.string.update_depois) { _, _ ->
                Atualizacao.esquecerPendente(tela)
            }
            .show()
    }

    /// Nem todo TV Box tem a tela específica do app; cai para a de segurança
    /// e, sem ela, para a raiz das configurações.
    private fun abrirPermissaoDeInstalar() {
        val tentativas = listOf(
            Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, "package:${tela.packageName}".toUri()),
            Intent(Settings.ACTION_SECURITY_SETTINGS),
            Intent(Settings.ACTION_SETTINGS))
        for (tentativa in tentativas) {
            if (runCatching { tela.startActivity(tentativa) }.isSuccess) return
        }
        status(tela.getString(R.string.update_permissao_sem_tela))
    }
}
