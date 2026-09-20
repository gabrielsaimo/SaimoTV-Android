package br.com.saimo.tv

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
 * A tela inicial do acervo, em fileiras de capa.
 *
 * Uma lista de nomes serve para procurar o que já se sabe que existe; não serve
 * para descobrir. Quem senta na frente da TV quer ver o que tem, e capa fala
 * mais rápido que texto. Então a primeira tela passa a ser o que toda TV faz:
 * fileiras que correm para o lado, o direcional andando dentro de uma e entre
 * elas, e nada mais para aprender.
 *
 * O peso foi o que mandou no desenho. Um TV Box de 2016 não aguenta dezenas de
 * imagens grandes:
 *
 * - as capas já chegam resolvidas no `destaques.txt`, então abrir a tela não
 *   dispara consulta nenhuma ao TMDB;
 * - as fileiras dividem um só depósito de células, e as capas de uma fileira
 *   que saiu da tela são devolvidas em vez de recriadas;
 * - cada célula tem tamanho fixo, na proporção do pôster, então nada pula
 *   enquanto as imagens chegam;
 * - as capas ficam em disco: rolar para baixo e voltar não baixa de novo.
 */
object Inicio {

    /**
     * Um item da fileira.
     *
     * A ação vem junto de propósito: a fileira não sabe se aquilo é um filme,
     * uma série, um anime ou um atalho para outra tela — ela desenha e chama.
     */
    data class Cartao(
        val titulo: String,
        /// Vazio quando não há imagem: aí a inicial preenche o quadro.
        val capa: String,
        val inicial: String,
        /// Quanto já foi visto, de 0 a 1. Nulo quando nunca foi aberto.
        val progresso: Float? = null,
        /// Quando não veio capa pronta, diz se vale procurar uma no TMDB e se
        /// o título é série. Nulo nos cartões que não são título — "Filmes",
        /// "Buscar" e companhia, que não têm capa nenhuma para procurar.
        val procurarCapa: Boolean? = null,
        val aoEscolher: () -> Unit,
    )

    data class Fila(val titulo: String, val cartoes: List<Cartao>)

    class Adapter(
        /// Só para procurar capa do que não veio com uma: os favoritos e o que
        /// está pela metade não passam pelo gerador.
        private val escopo: kotlinx.coroutines.CoroutineScope? = null,
        private val capaDe: (suspend (String, Boolean) -> String?)? = null,
    ) : RecyclerView.Adapter<Adapter.Holder>() {

        private var filas: List<Fila> = emptyList()
        /// Um depósito só para todas as fileiras: a capa que sai de uma entra
        /// na outra sem inflar nada de novo.
        private val deposito = RecyclerView.RecycledViewPool()
        /// Onde cada fileira estava, para voltar no mesmo lugar depois de ir
        /// abrir um título — e não jogar quem assiste de volta ao começo.
        private val posicoes = mutableMapOf<String, Int>()

        fun trocar(novas: List<Fila>) {
            filas = novas
            notifyDataSetChanged()
        }

        inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
            val titulo: TextView = view.findViewById(R.id.filaTitulo)
            val capas: RecyclerView = view.findViewById(R.id.filaCapas)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val holder = Holder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_fila, parent, false))
            holder.capas.layoutManager =
                LinearLayoutManager(parent.context, LinearLayoutManager.HORIZONTAL, false)
            holder.capas.setRecycledViewPool(deposito)
            // Sem isto o RecyclerView guarda o foco ao rolar e o direcional
            // pára de sair da fileira.
            holder.capas.isFocusable = false
            return holder
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val fila = filas[position]
            holder.titulo.text = fila.titulo
            val adaptador = Capas(fila.cartoes, escopo, capaDe)
            holder.capas.adapter = adaptador
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
        private val escopo: kotlinx.coroutines.CoroutineScope?,
        private val capaDe: (suspend (String, Boolean) -> String?)?,
    ) : RecyclerView.Adapter<Capas.Holder>() {

        class Holder(view: View) : RecyclerView.ViewHolder(view) {
            val imagem: ImageView = view.findViewById(R.id.capaImagem)
            val inicial: TextView = view.findViewById(R.id.capaInicial)
            val nome: TextView = view.findViewById(R.id.capaNome)
            val progresso: ProgressBar = view.findViewById(R.id.capaProgresso)
            /// Para descartar a capa que chegar depois de a célula ser reusada.
            var pedido: String? = null
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val holder = Holder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_capa, parent, false))
            // O mesmo crescer de quando um canal ganha o foco na tela inicial:
            // com o controle na mão, tamanho diz onde se está melhor que cor.
            holder.itemView.setOnFocusChangeListener { view, focado ->
                val escala = if (focado) 1.08f else 1f
                view.animate().scaleX(escala).scaleY(escala).setDuration(110).start()
                if (!focado) return@setOnFocusChangeListener
                // Sem isto a lista de cima rola só o bastante para mostrar a
                // capa, e o nome da fileira fica fora da tela — quem desce de
                // uma fileira para a outra deixa de saber onde está.
                val fileira = view.parent?.let { it as? View }?.parent as? View
                fileira?.post {
                    fileira.requestRectangleOnScreen(
                        android.graphics.Rect(0, 0, fileira.width, fileira.height), false)
                }
            }
            return holder
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val cartao = cartoes[position]
            holder.nome.text = cartao.titulo
            holder.inicial.text = cartao.inicial
            holder.itemView.setOnClickListener { cartao.aoEscolher() }

            holder.progresso.visibility =
                if (cartao.progresso == null) View.GONE else View.VISIBLE
            cartao.progresso?.let { holder.progresso.progress = (it * 1000).toInt() }

            holder.imagem.dispose()
            holder.imagem.setImageDrawable(null)
            holder.pedido = cartao.titulo
            if (cartao.capa.isEmpty()) {
                holder.imagem.visibility = View.GONE
                val serie = cartao.procurarCapa ?: return
                val buscar = capaDe ?: return
                escopo?.launch {
                    val achada = buscar(cartao.titulo, serie) ?: return@launch
                    if (holder.pedido != cartao.titulo) return@launch
                    holder.imagem.visibility = View.VISIBLE
                    holder.imagem.load(achada) { crossfade(true) }
                }
                return
            }
            holder.imagem.visibility = View.VISIBLE
            holder.imagem.load(cartao.capa) {
                // Ao contrário da lista por letra, aqui o endereço da capa é
                // fixo e veio pronto: guardar em disco é de graça e poupa o
                // aparelho de baixar tudo de novo a cada volta para esta tela.
                crossfade(true)
            }
        }

        override fun getItemCount() = cartoes.size
    }
}
