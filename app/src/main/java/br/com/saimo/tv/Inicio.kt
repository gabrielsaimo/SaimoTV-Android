package br.com.saimo.tv

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.dispose
import coil.load
import kotlinx.coroutines.launch

/**
 * Fileiras de capa, como numa TV: a tela inicial, os resultados da busca.
 *
 * O peso foi o que mandou no desenho. Um TV Box de 2016 não aguenta dezenas de
 * imagens grandes:
 *
 * - as capas já chegam resolvidas (destaques, fichas), sem consulta ao TMDB;
 * - as fileiras dividem um só depósito de células;
 * - cada célula tem tamanho fixo, então nada pula enquanto as imagens chegam;
 * - as capas ficam em disco: rolar para baixo e voltar não baixa de novo.
 */
object Inicio {

    /**
     * Um item da fileira.
     *
     * A ação vem junto de propósito: a fileira não sabe se aquilo é um filme,
     * uma série, um canal ou um atalho — ela desenha e chama.
     */
    data class Cartao(
        val titulo: String,
        /// Vazio quando não há imagem: aí a inicial preenche o quadro.
        val capa: String,
        val inicial: String,
        /// Quanto já foi visto, de 0 a 1. Nulo quando nunca foi aberto.
        val progresso: Float? = null,
        /// Quando não veio capa pronta, diz se vale procurar uma e se é série.
        val procurarCapa: Boolean? = null,
        /// O nome com que se procura a capa, quando não é o que está no cartão.
        val nomeDaCapa: String? = null,
        /// Linha de baixo do cartão largo (o programa no ar, no canal).
        val subtitulo: String? = null,
        /// O título por trás do cartão, para o destaque do topo e o MENU.
        val alvo: Alvo? = null,
        /// MENU sobre o cartão: favoritar, tirar do "continuar"...
        val aoMenu: (() -> Unit)? = null,
        val aoEscolher: () -> Unit,
    )

    enum class Tipo { CAPA, LARGO }

    data class Fila(val titulo: String, val cartoes: List<Cartao>, val tipo: Tipo = Tipo.CAPA)

    class Adapter(
        private val escopo: kotlinx.coroutines.CoroutineScope? = null,
        private val capaDe: (suspend (String, Boolean) -> String?)? = null,
        /// Chamado quando um cartão ganha o foco — é o que troca o destaque.
        private val aoFocar: ((Cartao) -> Unit)? = null,
    ) : RecyclerView.Adapter<Adapter.Holder>() {

        private var filas: List<Fila> = emptyList()
        private val depositoCapas = RecyclerView.RecycledViewPool()
        private val depositoLargos = RecyclerView.RecycledViewPool()
        /// Onde cada fileira estava, para voltar no mesmo lugar.
        private val posicoes = mutableMapOf<String, Int>()

        fun trocar(novas: List<Fila>) {
            filas = novas
            notifyDataSetChanged()
        }

        inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
            val titulo: TextView = view.findViewById(R.id.filaTitulo)
            val capas: RecyclerView = view.findViewById(R.id.filaCapas)
        }

        override fun getItemViewType(position: Int) = filas[position].tipo.ordinal

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val holder = Holder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_fila, parent, false))
            holder.capas.layoutManager =
                LinearLayoutManager(parent.context, LinearLayoutManager.HORIZONTAL, false).apply {
                    initialPrefetchItemCount = 6
                }
            holder.capas.setRecycledViewPool(if (viewType == Tipo.LARGO.ordinal) depositoLargos else depositoCapas)
            holder.capas.isFocusable = false
            return holder
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val fila = filas[position]
            holder.titulo.text = fila.titulo
            holder.capas.adapter = Capas(fila.cartoes, fila.tipo, escopo, capaDe, aoFocar)
            holder.capas.scrollToPosition(posicoes[fila.titulo] ?: 0)
            holder.capas.clearOnScrollListeners()
            holder.capas.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                    val gerente = rv.layoutManager as? LinearLayoutManager ?: return
                    posicoes[fila.titulo] = gerente.findFirstVisibleItemPosition()
                }
            })
        }

        override fun getItemCount() = filas.size
    }

    /** As capas de uma fileira. */
    private class Capas(
        private val cartoes: List<Cartao>,
        private val tipo: Tipo,
        private val escopo: kotlinx.coroutines.CoroutineScope?,
        private val capaDe: (suspend (String, Boolean) -> String?)?,
        private val aoFocar: ((Cartao) -> Unit)?,
    ) : RecyclerView.Adapter<Capas.Holder>() {

        class Holder(view: View) : RecyclerView.ViewHolder(view) {
            val imagem: ImageView = view.findViewById(R.id.capaImagem)
            val inicial: TextView = view.findViewById(R.id.capaInicial)
            val nome: TextView = view.findViewById(R.id.capaNome)
            val sub: TextView? = view.findViewById(R.id.capaSub)
            val progresso: ProgressBar = view.findViewById(R.id.capaProgresso)
            var pedido: String? = null
            var cartao: Cartao? = null
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val layout = if (tipo == Tipo.LARGO) R.layout.item_cartao_largo else R.layout.item_capa
            val holder = Holder(LayoutInflater.from(parent.context).inflate(layout, parent, false))
            holder.itemView.setOnFocusChangeListener { view, focado ->
                val escala = if (focado) 1.08f else 1f
                view.animate().scaleX(escala).scaleY(escala).setDuration(110).start()
                if (!focado) return@setOnFocusChangeListener
                holder.cartao?.let { aoFocar?.invoke(it) }
                // A fileira inteira à vista, com o nome dela: quem desce de uma
                // fileira para a outra continua sabendo onde está.
                val fileira = view.parent?.let { it as? View }?.parent as? View
                fileira?.post {
                    fileira.requestRectangleOnScreen(
                        android.graphics.Rect(0, 0, fileira.width, fileira.height), false)
                }
            }
            holder.itemView.setOnKeyListener { _, codigo, evento ->
                if (evento.action == KeyEvent.ACTION_DOWN &&
                    (codigo == KeyEvent.KEYCODE_MENU || codigo == KeyEvent.KEYCODE_BOOKMARK)) {
                    holder.cartao?.aoMenu?.let { it(); true } ?: false
                } else false
            }
            holder.itemView.setOnLongClickListener { holder.cartao?.aoMenu?.let { it(); true } ?: false }
            return holder
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val cartao = cartoes[position]
            holder.cartao = cartao
            holder.nome.text = cartao.titulo
            holder.sub?.text = cartao.subtitulo.orEmpty()
            holder.inicial.text = cartao.inicial
            holder.itemView.setOnClickListener { cartao.aoEscolher() }

            holder.progresso.visibility =
                if (cartao.progresso == null || cartao.progresso <= 0f) View.GONE else View.VISIBLE
            cartao.progresso?.let { holder.progresso.progress = (it * 1000).toInt() }

            holder.imagem.dispose()
            holder.imagem.setImageDrawable(null)
            holder.pedido = cartao.titulo
            val direta = cartao.capa.ifEmpty {
                // Sem capa pronta: o arquivo de fichas quase sempre sabe, e em
                // memória — sem ir à rede.
                val serie = cartao.procurarCapa
                if (serie != null) Generos.capa(cartao.nomeDaCapa ?: cartao.titulo, serie).orEmpty() else ""
            }
            if (direta.isEmpty()) {
                holder.imagem.visibility = View.GONE
                val serie = cartao.procurarCapa ?: return
                val buscar = capaDe ?: return
                val nome = cartao.nomeDaCapa ?: cartao.titulo
                escopo?.launch {
                    val achada = buscar(nome, serie) ?: return@launch
                    if (holder.pedido != cartao.titulo) return@launch
                    holder.imagem.visibility = View.VISIBLE
                    holder.imagem.load(achada) { crossfade(true) }
                }
                return
            }
            holder.imagem.visibility = View.VISIBLE
            holder.imagem.load(direta) { crossfade(true) }
        }

        override fun getItemCount() = cartoes.size
    }
}
