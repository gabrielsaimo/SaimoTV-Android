package br.com.saimo.tv

import android.app.Activity
import android.widget.ImageView
import android.widget.TextView
import androidx.media3.common.util.UnstableApi
import coil.load
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * O destaque do topo: o título focado, com a imagem larga dele ao fundo.
 *
 * Espera o foco descansar um pouco antes de pedir a ficha: quem corre a
 * fileira com a seta não precisa de um pedido ao TMDB por capa que passa.
 */
@UnstableApi
class Destaque(
    private val tela: Activity,
    private val escopo: CoroutineScope,
    private val fundo: ImageView,
    private val titulo: TextView,
    private val meta: TextView,
    private val sinopse: TextView,
) {
    private var trabalho: Job? = null
    private var fundoAtual: String? = null

    fun padrao(tituloPadrao: String, metaPadrao: String) {
        trabalho?.cancel()
        titulo.text = tituloPadrao
        meta.text = metaPadrao
        sinopse.text = ""
        trocarFundo(null)
    }

    fun mostrar(cartao: Inicio.Cartao) {
        trabalho?.cancel()
        val alvo = cartao.alvo
        titulo.text = if (alvo != null) Generos.semAno(alvo.titulo) else cartao.titulo
        meta.text = cartao.subtitulo?.let { tela.getString(R.string.inicio_canal_agora, it) }
            ?: alvo?.let { tela.getString(if (it.serie) R.string.vod_ficha_tipo_serie else R.string.vod_ficha_tipo_filme) }
                .orEmpty()
        sinopse.text = ""
        if (alvo == null) { trocarFundo(null); return }
        trabalho = escopo.launch(semDerrubar) {
            delay(400)
            val ficha = Detalhes.de(alvo.titulo, alvo.serie, alvo.tmdbId.takeIf { it > 0 }) ?: return@launch
            meta.text = listOfNotNull(
                ficha.nota.takeIf { it > 0 }?.let { "★ %.1f".format(it) },
                ficha.ano.takeIf { it.isNotBlank() },
                ficha.temporadas?.let { tela.resources.getQuantityString(R.plurals.vod_ficha_temporadas_n, it, it) },
                ficha.generos.take(3).joinToString(", ").takeIf { it.isNotBlank() },
            ).joinToString("  ·  ")
            sinopse.text = ficha.sinopse
            trocarFundo(ficha.fundo)
        }
    }

    private fun trocarFundo(endereco: String?) {
        if (endereco == fundoAtual) return
        fundoAtual = endereco
        if (endereco == null) {
            fundo.animate().alpha(0f).setDuration(200).start()
            return
        }
        fundo.load(endereco) {
            listener(onSuccess = { _, _ -> fundo.animate().alpha(1f).setDuration(300).start() })
        }
    }
}
