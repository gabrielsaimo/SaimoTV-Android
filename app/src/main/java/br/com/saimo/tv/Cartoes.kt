package br.com.saimo.tv

import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.dispose
import coil.load

/**
 * O que todo cartão de capa faz igual, em qualquer tela.
 *
 * Antes cada adaptador tinha o seu jeito, e os três defeitos que aparecem na TV
 * vinham daí: a imagem passava por cima do canto arredondado do fundo, a letra
 * de reserva continuava atrás do logo PNG transparente dos canais, e o cartão
 * crescia no foco — com a fileira inteira rolando a cada passo para o lado.
 */
object Cartoes {

    /** Recorta a capa no canto arredondado do fundo dela. */
    fun arredondar(moldura: View) {
        moldura.clipToOutline = true
    }

    /**
     * Carrega a capa e só esconde a letra quando a imagem de fato chegou.
     *
     * Logo de canal é PNG transparente: com a letra visível embaixo, ela
     * aparecia através dele.
     */
    fun carregar(imagem: ImageView, inicial: TextView, url: String?) {
        imagem.dispose()
        imagem.setImageDrawable(null)
        inicial.visibility = View.VISIBLE
        if (url.isNullOrEmpty()) {
            imagem.visibility = View.GONE
            return
        }
        imagem.visibility = View.VISIBLE
        imagem.load(url) {
            crossfade(true)
            listener(
                onSuccess = { _, _ -> inicial.visibility = View.INVISIBLE },
                onError = { _, _ -> imagem.visibility = View.GONE; inicial.visibility = View.VISIBLE },
            )
        }
    }

    /**
     * Segura o foco nas pontas da fileira.
     *
     * Sem isto, apertar para a direita no último cartão fazia o sistema
     * procurar "algo à direita" e achar um cartão na fileira de cima ou de
     * baixo. Na ponta, a seta não faz nada; trocar de fileira é só com cima e
     * baixo.
     */
    fun segurarNasPontas(holder: RecyclerView.ViewHolder, codigo: Int, evento: KeyEvent): Boolean {
        if (evento.action != KeyEvent.ACTION_DOWN) return false
        val posicao = holder.bindingAdapterPosition
        val total = holder.bindingAdapter?.itemCount ?: return false
        return when (codigo) {
            KeyEvent.KEYCODE_DPAD_RIGHT -> posicao == total - 1
            KeyEvent.KEYCODE_DPAD_LEFT -> posicao == 0
            else -> false
        }
    }
}
