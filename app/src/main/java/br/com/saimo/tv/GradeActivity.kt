package br.com.saimo.tv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.dispose
import coil.load
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Um tipo inteiro em capas: filmes, séries, animes, doramas ou favoritos.
 *
 * Antes era letra → lista de nomes → título. Agora são capas em colunas, com
 * as letras numa coluna à esquerda para pular direto (vinte mil filmes não se
 * descem com a seta), o gênero e a ordem no alto.
 */
@UnstableApi
class GradeActivity : AppCompatActivity() {

    companion object {
        const val FILMES = "filmes"
        const val SERIES = "series"
        const val ANIMES = "animes"
        const val DORAMAS = "doramas"
        const val FAVORITOS = "favoritos"
        private const val TIPO = "grade.tipo"
        private const val GENERO = "grade.genero"

        fun abrir(context: Context, tipo: String, genero: String = "") =
            context.startActivity(Intent(context, GradeActivity::class.java)
                .putExtra(TIPO, tipo).putExtra(GENERO, genero))

        private val ANO = Regex("\\((\\d{4})\\)\\s*$")
    }

    private data class Item(val alvo: Alvo, val chave: String, val ano: Int) {
        /// Título em outro alfabeto (grego, cirílico, hindi) normaliza para
        /// vazio: vai para o fim, sob "…", e não para o começo da lista.
        val letra: String = chave.firstOrNull()?.let {
            when { it in 'a'..'z' -> it.uppercase(); it.isDigit() -> "#"; else -> "…" }
        } ?: "…"
        val ordem: String = when (letra) { "#" -> "0$chave"; "…" -> "~" + alvo.titulo; else -> "1$chave" }
    }

    private lateinit var tipo: String
    private lateinit var capas: RecyclerView
    private lateinit var letras: RecyclerView
    private lateinit var estado: TextView
    private var todos: List<Item> = emptyList()
    private var vistos: List<Item> = emptyList()
    private var genero = ""
    private var novosPrimeiro = false
    private val adaptador = Capas()
    private var colunas = 5
    private val adaptadorLetras = Letras()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tipo = intent.getStringExtra(TIPO) ?: FILMES
        genero = intent.getStringExtra(GENERO).orEmpty()
        setContentView(R.layout.activity_grade)
        capas = findViewById(R.id.gradeCapas)
        letras = findViewById(R.id.gradeLetras)
        estado = findViewById(R.id.gradeEstado)
        findViewById<TextView>(R.id.gradeTitulo).text = getString(when (tipo) {
            SERIES -> R.string.vod_series
            ANIMES -> R.string.vod_animes
            DORAMAS -> R.string.vod_doramas
            FAVORITOS -> R.string.vod_favoritos
            else -> R.string.vod_filmes
        })
        val colunas = ((resources.displayMetrics.widthPixels / resources.displayMetrics.density - 140) / 150)
            .toInt().coerceIn(4, 8)
        this.colunas = colunas
        capas.layoutManager = GridLayoutManager(this, colunas)
        capas.adapter = adaptador
        capas.setHasFixedSize(true)
        capas.setItemViewCacheSize(colunas * 2)
        letras.layoutManager = LinearLayoutManager(this)
        letras.adapter = adaptadorLetras

        findViewById<TextView>(R.id.gradeGenero).setOnClickListener { escolherGenero() }
        findViewById<TextView>(R.id.gradeOrdem).setOnClickListener {
            Painel.mostrar(this, getString(R.string.grade_ordem, "").trim(' ', '▾'), listOf(
                Painel.Item(getString(R.string.grade_az), marcado = !novosPrimeiro) { novosPrimeiro = false; aplicar() },
                Painel.Item(getString(R.string.grade_novos), marcado = novosPrimeiro) { novosPrimeiro = true; aplicar() },
            ))
        }
        atualizarBotoes()
        lifecycleScope.launch(semDerrubar) { carregar() }
    }

    override fun onResume() {
        super.onResume()
        // Favoritos mudam de dentro da ficha; a grade de favoritos se refaz.
        if (tipo == FAVORITOS && todos.isNotEmpty()) lifecycleScope.launch(semDerrubar) { carregar() }
        else adaptador.notifyDataSetChanged()
    }

    private suspend fun carregar() {
        Generos.carregar(this)
        todos = withContext(Dispatchers.Default) {
            when (tipo) {
                FILMES, SERIES -> Vod.entradas(this@GradeActivity).filter { it.serie == (tipo == SERIES) }
                    .map { e ->
                        val a = e.achado
                        Item(Alvo(a.titulo, a.serie, a.letra, a.ano), e.chave, anoDe(a.titulo, a.ano))
                    }
                ANIMES, DORAMAS -> Vod.colecao(this@GradeActivity, tipo).map { c ->
                    Item(Alvo(c.titulo, true, "", c.ano, tipo, c.tmdbId.toIntOrNull() ?: 0),
                        Vod.normalizar(c.titulo), anoDe(c.titulo, c.ano))
                }
                else -> VodFavoritos.lista(this@GradeActivity).map { f ->
                    Item(Alvo(f.titulo, f.serie, f.letra, f.ano), Vod.normalizar(f.titulo), anoDe(f.titulo, f.ano))
                }
            }
        }
        aplicar()
    }

    private fun anoDe(titulo: String, ano: String) =
        ano.toIntOrNull() ?: ANO.find(titulo)?.groupValues?.get(1)?.toIntOrNull() ?: 0

    private fun aplicar() {
        var lista = if (genero.isEmpty()) todos else todos.filter { Generos.tem(it.alvo.titulo, it.alvo.serie, genero) }
        lista = if (novosPrimeiro) lista.sortedWith(compareByDescending<Item> { it.ano }.thenBy { it.ordem })
                else lista.sortedBy { it.ordem }
        vistos = lista
        adaptador.notifyDataSetChanged()
        findViewById<TextView>(R.id.gradeContagem).text =
            resources.getQuantityString(R.plurals.vod_titulos, lista.size, lista.size)
        estado.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
        estado.text = if (tipo == FAVORITOS) getString(R.string.grade_vazio_favoritos) else getString(R.string.vod_vazio)
        adaptadorLetras.letras = if (novosPrimeiro) emptyList() else lista.map { it.letra }.distinct()
        letras.visibility = if (adaptadorLetras.letras.isEmpty()) View.GONE else View.VISIBLE
        atualizarBotoes()
        capas.scrollToPosition(0)
        capas.post { capas.getChildAt(0)?.requestFocus() }
    }

    private fun atualizarBotoes() {
        findViewById<TextView>(R.id.gradeGenero).apply {
            text = getString(R.string.grade_genero, genero.ifEmpty { getString(R.string.vod_todos) })
            visibility = if (Generos.todos.isEmpty() || tipo == FAVORITOS) View.GONE else View.VISIBLE
        }
        findViewById<TextView>(R.id.gradeOrdem).text =
            getString(R.string.grade_ordem, getString(if (novosPrimeiro) R.string.grade_novos else R.string.grade_az))
    }

    private fun escolherGenero() {
        val itens = listOf(Painel.Item(getString(R.string.vod_todos), marcado = genero.isEmpty()) {
            genero = ""; aplicar()
        }) + Generos.todosEmOrdem.map { g ->
            Painel.Item(g, marcado = g == genero) { genero = g; aplicar() }
        }
        Painel.mostrar(this, getString(R.string.grade_genero, "").trim(' ', '▾', ':'), itens)
    }

    private fun irParaLetra(letra: String) {
        val i = vistos.indexOfFirst { it.letra == letra }.takeIf { it >= 0 } ?: return
        (capas.layoutManager as GridLayoutManager).scrollToPositionWithOffset(i, 0)
        capas.post { capas.findViewHolderForAdapterPosition(i)?.itemView?.requestFocus() }
    }

    private fun alternarFavorito(item: Item) {
        if (item.alvo.colecao.isNotEmpty()) return
        val marcado = VodFavoritos.alternar(this,
            VodFavoritos.Item(item.alvo.titulo, item.alvo.serie, item.alvo.letra, item.alvo.ano))
        Toast.makeText(this, if (marcado) R.string.vod_ficha_favorito else R.string.inicio_desfavoritar,
            Toast.LENGTH_SHORT).show()
        if (tipo == FAVORITOS) lifecycleScope.launch(semDerrubar) { carregar() }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Esquerda na primeira coluna leva às letras, já na letra do título focado.
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT && capas.hasFocus()) {
            val foco = currentFocus ?: return super.onKeyDown(keyCode, event)
            val pos = capas.findContainingViewHolder(foco)?.bindingAdapterPosition ?: -1
            val colunas = (capas.layoutManager as GridLayoutManager).spanCount
            if (pos >= 0 && pos % colunas == 0 && letras.visibility == View.VISIBLE) {
                val letra = vistos.getOrNull(pos)?.letra
                val i = adaptadorLetras.letras.indexOf(letra).coerceAtLeast(0)
                letras.scrollToPosition(i)
                letras.post { letras.findViewHolderForAdapterPosition(i)?.itemView?.requestFocus() }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private inner class Capas : RecyclerView.Adapter<Capas.Holder>() {
        inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
            val imagem: ImageView = view.findViewById(R.id.capaImagem)
            val inicial: TextView = view.findViewById(R.id.capaInicial)
            val nome: TextView = view.findViewById(R.id.capaNome)
            val progresso: ProgressBar = view.findViewById(R.id.capaProgresso)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_capa, parent, false)
            // A capa ocupa a coluna inteira, na proporção do pôster.
            val largura = (parent.width - parent.paddingStart - parent.paddingEnd) / colunas
            if (largura > 0) {
                val d = resources.displayMetrics.density
                val util = largura - (16 * d).toInt()
                view.layoutParams = RecyclerView.LayoutParams(util, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .apply { setMargins((8 * d).toInt(), (8 * d).toInt(), (8 * d).toInt(), (8 * d).toInt()) }
                view.findViewById<View>(R.id.capaImagem).parent.let { it as View }.layoutParams.height =
                    ((util - 12 * d) * 1.45f).toInt()
            }
            view.setOnFocusChangeListener { v, foco ->
                val escala = if (foco) 1.07f else 1f
                v.animate().scaleX(escala).scaleY(escala).setDuration(100).start()
            }
            return Holder(view)
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val item = vistos[position]
            val alvo = item.alvo
            holder.nome.text = alvo.nomeCompleto
            holder.inicial.text = alvo.titulo.take(1).uppercase()
            val fracao = if (alvo.serie) null else Progresso.fracao(this@GradeActivity, Progresso.chaveFilme(alvo.titulo))
            holder.progresso.visibility = if (fracao != null && fracao > 0f) View.VISIBLE else View.GONE
            fracao?.let { holder.progresso.progress = (it * 1000).toInt() }
            val capa = Generos.capa(alvo.nomeCompleto, alvo.serie) ?: Generos.capa(alvo.titulo, alvo.serie)
            holder.imagem.dispose()
            if (capa == null) {
                holder.imagem.setImageDrawable(null)
                holder.imagem.visibility = View.GONE
            } else {
                holder.imagem.visibility = View.VISIBLE
                holder.imagem.load(capa) { crossfade(true) }
            }
            holder.itemView.setOnClickListener { FichaActivity.abrir(this@GradeActivity, alvo) }
            holder.itemView.setOnLongClickListener { alternarFavorito(item); true }
            holder.itemView.setOnKeyListener { _, codigo, evento ->
                if (evento.action == KeyEvent.ACTION_DOWN && codigo == KeyEvent.KEYCODE_MENU) {
                    alternarFavorito(item); true
                } else false
            }
        }

        override fun getItemCount() = vistos.size
    }

    private inner class Letras : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var letras: List<String> = emptyList()
            set(v) { field = v; notifyDataSetChanged() }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = object : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_letra, parent, false)) {}

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val letra = letras[position]
            (holder.itemView as TextView).text = letra
            // Focar a letra já leva a grade até ela; OK entra nas capas.
            holder.itemView.setOnFocusChangeListener { _, foco ->
                if (foco) {
                    val i = vistos.indexOfFirst { it.letra == letra }
                    if (i >= 0) (capas.layoutManager as GridLayoutManager).scrollToPositionWithOffset(i, 0)
                }
            }
            holder.itemView.setOnClickListener { irParaLetra(letra) }
            holder.itemView.setOnKeyListener { _, codigo, evento ->
                if (evento.action == KeyEvent.ACTION_DOWN && codigo == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    irParaLetra(letra); true
                } else false
            }
        }

        override fun getItemCount() = letras.size
    }
}
