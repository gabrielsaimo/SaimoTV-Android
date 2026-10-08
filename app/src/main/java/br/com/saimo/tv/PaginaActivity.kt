package br.com.saimo.tv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

/**
 * A página de um tipo — filmes, séries, animes, doramas — em fileiras.
 *
 * Como a tela inicial: o que a pessoa estava vendo daquele tipo, os destaques,
 * os lançamentos, e uma fileira por gênero. O acervo inteiro de A a Z fica na
 * última fileira, para quem quer procurar sem saber o nome.
 */
@UnstableApi
class PaginaActivity : TelaComMenu() {

    override val aba: Aba get() = when (intent.getStringExtra(TIPO)) {
        GradeActivity.SERIES -> Aba.ON_DEMAND
        GradeActivity.ANIMES -> Aba.ON_DEMAND
        GradeActivity.DORAMAS -> Aba.ON_DEMAND
        else -> Aba.ON_DEMAND
    }


    companion object {
        private const val TIPO = "pagina.tipo"
        fun intent(context: Context, tipo: String): Intent =
            Intent(context, PaginaActivity::class.java).putExtra(TIPO, tipo)

        fun abrir(context: Context, tipo: String) = context.startActivity(intent(context, tipo))

        /// Os gêneros mais procurados primeiro; o resto entra depois, em ordem.
        private val ORDEM_GENEROS = listOf(
            "Ação", "Comédia", "Drama", "Terror", "Thriller", "Suspense", "Ficção científica",
            "Animação", "Romance", "Aventura", "Crime", "Família", "Fantasia", "Mistério",
            "Documentário", "Guerra", "Faroeste", "História", "Música", "Action & Adventure",
            "Sci-Fi & Fantasy", "Kids", "Reality", "Soap", "Talk", "War & Politics")
        private val ANO = Regex("\\((\\d{4})\\)\\s*$")
    }

    private lateinit var tipo: String
    private lateinit var filas: RecyclerView
    private val adaptador by lazy {
        Inicio.Adapter(escopo = lifecycleScope, capaDe = { t, s -> Generos.capa(t, s) },
            aoFocar = { destaque.mostrar(it) })
    }
    private lateinit var destaque: Destaque
    private var montou = false

    private val serie get() = tipo != GradeActivity.FILMES
    private val colecao get() = tipo == GradeActivity.ANIMES || tipo == GradeActivity.DORAMAS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tipo = intent.getStringExtra(TIPO) ?: GradeActivity.FILMES
        setContentView(R.layout.activity_pagina)
        filas = findViewById(R.id.inicioFilas)
        filas.layoutManager = Inicio.Filas(this)
        filas.adapter = adaptador
        destaque = Destaque(this, lifecycleScope, findViewById<ImageView>(R.id.inicioFundo),
            findViewById(R.id.inicioTitulo), findViewById(R.id.inicioMeta), findViewById(R.id.inicioSinopse))
        val nome = getString(when (tipo) {
            GradeActivity.SERIES -> R.string.vod_series
            GradeActivity.ANIMES -> R.string.vod_animes
            GradeActivity.DORAMAS -> R.string.vod_doramas
            else -> R.string.vod_filmes
        })
        // O menu do topo já diz em que página se está.
        findViewById<TextView>(R.id.paginaNome).visibility = android.view.View.GONE
        destaque.padrao(nome, getString(R.string.vod_carregando))
        lifecycleScope.launch(semDerrubar) { montar() }
    }

    override fun onRestart() {
        super.onRestart()
        // O "continuar" deste tipo muda depois de assistir.
        lifecycleScope.launch(semDerrubar) { montar() }
    }

    private data class Candidato(val alvo: Alvo, val ano: Int, val capa: String, val generos: Int)

    private suspend fun montar() {
        Generos.carregar(this)
        val lista = mutableListOf<Inicio.Fila>()

        continuar()?.let { lista += it }

        // As fileiras do repositório, só com os títulos deste tipo.
        val letraTipo = when (tipo) {
            GradeActivity.FILMES -> 'f'; GradeActivity.SERIES -> 's'
            GradeActivity.ANIMES -> 'a'; else -> 'd'
        }
        for (fila in Destaques.filas(this)) {
            val itens = fila.itens.filter { it.tipo == letraTipo }
            if (itens.size >= 4) lista += Inicio.Fila(fila.titulo, itens.map { item ->
                cartao(Alvo(item.titulo, item.serie, item.letra, item.ano,
                    colecao = if (item.daColecao) item.colecao else ""), item.capa)
            })
        }
        if (!montou) mostrar(lista)

        // Lançamentos e gêneros saem do acervo inteiro, fora da linha principal.
        val candidatos = withContext(Dispatchers.Default) { candidatos() }
        val esteAno = Calendar.getInstance().get(Calendar.YEAR)
        val novos = candidatos.filter { it.ano >= esteAno - 1 }.sortedByDescending { it.ano }.take(30)
        if (novos.size >= 4) lista += Inicio.Fila(getString(R.string.pagina_lancamentos), novos.map { cartao(it.alvo, it.capa) })

        val porGenero = withContext(Dispatchers.Default) {
            val generos = Generos.todos.sortedBy { g -> ORDEM_GENEROS.indexOf(g).let { if (it < 0) 99 else it } }
            generos.mapNotNull { g ->
                val bit = Generos.bit(g)
                val itens = candidatos.filter { it.generos and bit != 0 }
                    .sortedByDescending { it.ano }.take(25)
                if (itens.size >= 6) g to itens else null
            }
        }
        for ((g, itens) in porGenero) {
            lista += Inicio.Fila(g, itens.map { cartao(it.alvo, it.capa) } + verMais(g))
        }
        lista += Inicio.Fila(getString(R.string.pagina_explorar), listOf(
            Inicio.Cartao(getString(R.string.pagina_todos_az), "", "A–Z") {
                GradeActivity.abrir(this, tipo)
            }))
        mostrar(lista)
    }

    private fun mostrar(lista: List<Inicio.Fila>) {
        if (isFinishing || isDestroyed) return
        adaptador.trocar(lista)
        if (!montou) {
            montou = true
            focarQuandoPronto {
                filas.findViewHolderForAdapterPosition(0)?.itemView
                    ?.findViewById<RecyclerView>(R.id.filaCapas)?.getChildAt(0)
            }
        }
    }

    /** Só títulos com capa: fileira de gênero cheia de quadrados vazios não convida ninguém. */
    private suspend fun candidatos(): List<Candidato> {
        if (colecao) {
            return Vod.colecao(this, tipo).mapNotNull { c ->
                val alvo = Alvo(c.titulo, true, "", c.ano, tipo, c.tmdbId.toIntOrNull() ?: 0)
                val capa = Generos.capa(c.titulo, true) ?: return@mapNotNull null
                Candidato(alvo, anoDe(c.titulo, c.ano), capa, Generos.bits(c.titulo, true))
            }
        }
        return Vod.entradas(this).asSequence().filter { it.serie == serie }.mapNotNull { e ->
            val a = e.achado
            val capa = Generos.capa(a.titulo, a.serie) ?: return@mapNotNull null
            Candidato(Alvo(a.titulo, a.serie, a.letra, a.ano), anoDe(a.titulo, a.ano), capa, Generos.bits(a.titulo, a.serie))
        }.toList()
    }

    private val anoMaximo = Calendar.getInstance().get(Calendar.YEAR) + 1

    /** Ano de lançamento; "2050" no meio do nome do filme não é ano. */
    private fun anoDe(titulo: String, ano: String): Int {
        val v = ano.toIntOrNull() ?: ANO.find(titulo)?.groupValues?.get(1)?.toIntOrNull() ?: 0
        return if (v in 1900..anoMaximo) v else 0
    }

    private fun continuar(): Inicio.Fila? {
        val itens = Progresso.emAndamento(this).filter { a ->
            val e = a.endereco ?: return@filter false
            when (tipo) {
                GradeActivity.FILMES -> !e.serie
                GradeActivity.SERIES -> e.serie && e.colecao.isEmpty()
                else -> e.colecao == tipo
            }
        }
        if (itens.isEmpty()) return null
        return Inicio.Fila(getString(R.string.vod_continuar), itens.map { a ->
            val alvo = a.endereco!!.alvo
            Inicio.Cartao(a.rotulo, "", a.titulo.take(1).uppercase(), progresso = a.fracao,
                procurarCapa = a.serie, nomeDaCapa = a.titulo, alvo = alvo) {
                val (t, e) = if (a.terminado) a.temporada to a.episodio + 1 else a.temporada to a.episodio
                PlayerActivity.abrir(this, alvo, t, e)
            }
        })
    }

    private fun cartao(alvo: Alvo, capa: String) = Inicio.Cartao(
        titulo = alvo.nomeCompleto, capa = capa, inicial = alvo.titulo.take(1).uppercase(),
        progresso = if (alvo.serie) null else Progresso.fracao(this, Progresso.chaveFilme(alvo.titulo)),
        alvo = alvo,
        aoMenu = if (alvo.colecao.isNotEmpty()) null else ({
            val marcado = VodFavoritos.alternar(this, VodFavoritos.Item(alvo.titulo, alvo.serie, alvo.letra, alvo.ano))
            Toast.makeText(this, if (marcado) R.string.vod_ficha_favorito else R.string.inicio_desfavoritar,
                Toast.LENGTH_SHORT).show()
        }),
    ) { FichaActivity.abrir(this, alvo) }

    private fun verMais(genero: String) = Inicio.Cartao(getString(R.string.pagina_ver_mais), "", "+") {
        GradeActivity.abrir(this, tipo, genero)
    }
}
