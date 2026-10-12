package br.com.saimo.tv

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView vertical que não deixa o foco fugir para o menu do topo.
 *
 * Segurando a seta para baixo, o TV Box antigo não monta a linha seguinte na
 * velocidade das teclas: a busca de foco do sistema não acha nada abaixo e
 * devolve o primeiro focável da tela — o "Buscar" do menu. Um OK logo depois
 * abria a busca no lugar do título. A lista de canais do Ao vivo já anda por
 * índice próprio; esta é a mesma proteção para as outras listas longas.
 *
 * Só a seta para baixo é vigiada, e só quando ainda há item abaixo do focado:
 * no fim da lista, e para cima (que leva ao menu), tudo segue como sempre.
 * Em vez de perder o foco, a lista rola um pouco e o próximo toque já acha a
 * linha montada.
 */
class ListaSemFuga @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : RecyclerView(context, attrs, defStyle) {

    override fun focusSearch(focused: View?, direction: Int): View? {
        val achado = super.focusSearch(focused, direction)
        if (direction != View.FOCUS_DOWN || focused == null) return achado
        if (achado != null && achado !== focused && contem(achado)) return achado
        val item = findContainingItemView(focused) ?: return achado
        val posicao = getChildAdapterPosition(item)
        val total = adapter?.itemCount ?: return achado
        if (posicao == NO_POSITION || !temLinhaAbaixo(posicao, total)) return achado
        if (!canScrollVertically(1)) return achado
        smoothScrollBy(0, (height / 3).coerceAtLeast(1))
        return focused
    }

    private fun temLinhaAbaixo(posicao: Int, total: Int): Boolean {
        val grade = layoutManager as? GridLayoutManager ?: return posicao < total - 1
        val colunas = grade.spanCount.coerceAtLeast(1)
        val busca = grade.spanSizeLookup
        return busca.getSpanGroupIndex(posicao, colunas) < busca.getSpanGroupIndex(total - 1, colunas)
    }

    private fun contem(vista: View): Boolean {
        var atual: View? = vista
        while (atual != null) {
            if (atual === this) return true
            atual = atual.parent as? View
        }
        return false
    }
}
