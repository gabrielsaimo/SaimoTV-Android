package br.com.saimo.tv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Locale

/**
 * A página de um tipo — filmes, séries, animes, doramas — em fileiras.
 *
 * Como a tela inicial: o que a pessoa estava vendo daquele tipo, os destaques,
 * os lançamentos, e uma fileira por gênero. O acervo inteiro de A a Z fica na
 * última fileira, para quem quer procurar sem saber o nome.
 *
 * Com o tipo [TODOS] é o On Demand: abre com os cartões das quatro categorias
 * e mistura os tipos nas fileiras de baixo.
 */
@UnstableApi
open class PaginaActivity : TelaComMenu() {

    override val aba: Aba get() = Aba.ON_DEMAND

    companion object {
        private const val TIPO = "pagina.tipo"
        /// O On Demand: as quatro categorias juntas.
        const val TODOS = "todos"

        fun intent(context: Context, tipo: String): Intent =
            Intent(context, PaginaActivity::class.java).putExtra(TIPO, tipo)

        fun abrir(context: Context, tipo: String) = context.startActivity(intent(context, tipo))

        /// Os gêneros mais procurados primeiro; o resto entra depois, em ordem.
        private val ORDEM_GENEROS = listOf(
            "Ação", "Comédia", "Drama", "Terror", "Thriller", "Suspense", "Ficção científica",
            "Animação", "Romance", "Aventura", "Crime", "Família", "Fantasia", "Mistério",
            "Documentário", "Guerra", "Faroeste", "História", "Música", "Action & Adventure",
            "Sci-Fi & Fantasy", "Kids", "Reality", "Soap", "Talk", "War & Politics")

        /// No On Demand, só os gêneros que leem bem como "Filmes de ...".
        private val GENEROS_ON_DEMAND = listOf(
            "Ação", "Comédia", "Terror", "Ficção científica", "Romance", "Suspense",
            "Animação", "Aventura", "Drama", "Crime", "Fantasia")

        private val ANO = Regex("\\((\\d{4})\\)\\s*$")

        /// A cor de cada categoria nos cartões do On Demand.
        private const val COR_FILMES = 0xFF4FE3D0.toInt()
        private const val COR_SERIES = 0xFF7AA2FF.toInt()
        private const val COR_ANIMES = 0xFFFFB454.toInt()
        private const val COR_DORAMAS = 0xFFFF7EB6.toInt()
    }

    /// O tipo da página; o On Demand responde [TODOS].
    protected open fun tipoDaPagina(): String = intent.getStringExtra(TIPO) ?: GradeActivity.FILMES

    private lateinit var tipo: String
    private lateinit var filas: RecyclerView
    private val adaptador by lazy {
        Inicio.Adapter(escopo = lifecycleScope, capaDe = { t, s -> Generos.capa(t, s) },
            aoFocar = { destaque.mostrar(it) })
    }
    private lateinit var destaque: Destaque
    private var montou = false
    /// Os cartões das categorias do topo; o "Explorar" do fim usa o mesmo desenho.
    private var categoriasProntas: Inicio.Fila? = null

    private val todos get() = tipo == TODOS
    private val serie get() = tipo != GradeActivity.FILMES
    private val colecao get() = tipo == GradeActivity.ANIMES || tipo == GradeActivity.DORAMAS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tipo = tipoDaPagina()
        setContentView(R.layout.activity_pagina)
        filas = findViewById(R.id.inicioFilas)
        filas.layoutManager = Inicio.Filas(this)
        filas.adapter = adaptador
        destaque = Destaque(this, lifecycleScope, findViewById<ImageView>(R.id.inicioFundo),
            findViewById(R.id.inicioTitulo), findViewById(R.id.inicioMeta), findViewById(R.id.inicioSinopse)) { it.aoEscolher() }
        val nome = getString(when (tipo) {
            TODOS -> R.string.menu_on_demand
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

    /** A letra de cada tipo em destaques.txt. */
    private fun letraDe(t: String) = when (t) {
        GradeActivity.FILMES -> 'f'; GradeActivity.SERIES -> 's'
        GradeActivity.ANIMES -> 'a'; else -> 'd'
    }

    private suspend fun montar() {
        Generos.carregar(this)
        val lista = mutableListOf<Inicio.Fila>()
        val destaques = Destaques.filas(this)

        // As categorias entram já completas: redesenhar a primeira fileira depois
        // de a tela abrir tiraria o foco de quem já está nela.
        val colecoes = if (todos) withContext(Dispatchers.Default) {
            mapOf(GradeActivity.ANIMES to daColecao(GradeActivity.ANIMES),
                GradeActivity.DORAMAS to daColecao(GradeActivity.DORAMAS))
        } else emptyMap()
        if (todos) lista += categorias(destaques, contagens(), colecoes).also { categoriasProntas = it }
        continuar()?.let { lista += it }

        // As fileiras do repositório: no On Demand todas, numa página só as do tipo dela.
        for (fila in destaques) {
            val itens = if (todos) fila.itens else fila.itens.filter { it.tipo == letraDe(tipo) }
            if (itens.size >= 4) lista += Inicio.Fila(fila.titulo, itens.map { item ->
                cartao(Alvo(item.titulo, item.serie, item.letra, item.ano,
                    colecao = if (item.daColecao) item.colecao else ""), item.capa, item.trailer)
            })
        }
        if (!montou) mostrar(lista)

        if (todos) montarTodos(lista) else montarTipo(lista)
        mostrar(lista)
    }

    /** Uma página de um tipo só: lançamentos, gêneros e o A a Z. */
    private suspend fun montarTipo(lista: MutableList<Inicio.Fila>) {
        // Lançamentos e gêneros saem do acervo inteiro, fora da linha principal.
        val candidatos = withContext(Dispatchers.Default) {
            if (colecao) daColecao(tipo) else doAcervo(serie)
        }
        lancamentos(candidatos)?.let { lista += Inicio.Fila(getString(R.string.pagina_lancamentos), it) }

        val porGenero = withContext(Dispatchers.Default) {
            val generos = Generos.todos.sortedBy { g -> ORDEM_GENEROS.indexOf(g).let { if (it < 0) 99 else it } }
            generos.mapNotNull { g -> porGenero(candidatos, g)?.let { g to it } }
        }
        for ((g, itens) in porGenero) {
            lista += Inicio.Fila(g, itens.map { cartao(it.alvo, it.capa) } + verMais(tipo, g))
        }
        lista += Inicio.Fila(getString(R.string.pagina_explorar), listOf(
            Inicio.Cartao(getString(R.string.pagina_todos_az), "", "A–Z") {
                GradeActivity.abrir(this, tipo)
            }))
    }

    /**
     * O On Demand: depois das categorias e dos destaques (que já trazem em
     * alta, lançamentos, animes e doramas), os gêneros de filme e o A a Z de
     * cada categoria no fim.
     */
    private suspend fun montarTodos(lista: MutableList<Inicio.Fila>) {
        val filmes = withContext(Dispatchers.Default) { doAcervo(serie = false) }

        val porGenero = withContext(Dispatchers.Default) {
            GENEROS_ON_DEMAND.filter { it in Generos.todos }.mapNotNull { g -> porGenero(filmes, g)?.let { g to it } }
        }
        for ((g, itens) in porGenero) {
            lista += Inicio.Fila(getString(R.string.ondemand_genero_filmes, g.lowercase(Locale.ROOT)),
                itens.map { cartao(it.alvo, it.capa) } + verMais(GradeActivity.FILMES, g))
        }

        // O fim da página: o acervo inteiro de cada categoria, de A a Z, com o
        // mesmo cartão das categorias do topo — só a ação muda.
        val tipos = listOf(GradeActivity.FILMES, GradeActivity.SERIES, GradeActivity.ANIMES, GradeActivity.DORAMAS)
        categoriasProntas?.let { topo ->
            lista += Inicio.Fila(getString(R.string.pagina_explorar), topo.cartoes.zip(tipos).map { (c, t) ->
                c.copy(titulo = getString(R.string.ondemand_az, c.titulo)) { GradeActivity.abrir(this, t) }
            }, Inicio.Tipo.SECAO)
        }
    }

    /** Quantos títulos cada categoria tem; o índice já fica em memória para a busca. */
    private suspend fun contagens(): Map<String, Int> {
        val entradas = Vod.entradas(this)
        return mapOf(
            GradeActivity.FILMES to entradas.count { !it.serie },
            GradeActivity.SERIES to entradas.count { it.serie },
            GradeActivity.ANIMES to Vod.colecao(this, GradeActivity.ANIMES).size,
            GradeActivity.DORAMAS to Vod.colecao(this, GradeActivity.DORAMAS).size,
        )
    }

    /**
     * Os quatro cartões grandes do topo do On Demand. Cada um abre a página da
     * categoria; no foco, o destaque mostra o que ela tem e o fundo de um título
     * em alta dela. As capas da colagem vêm dos destaques (já resolvidas, sem
     * ir ao TMDB) e, faltando, do acervo.
     */
    private fun categorias(
        destaques: List<Destaques.Fila>,
        contagens: Map<String, Int>,
        acervo: Map<String, List<Candidato>>,
    ): Inicio.Fila {
        val numero = java.text.NumberFormat.getIntegerInstance(Locale.forLanguageTag("pt-BR"))
        val itens = listOf(
            Triple(GradeActivity.FILMES, R.string.vod_filmes, R.string.ondemand_filmes_sobre),
            Triple(GradeActivity.SERIES, R.string.vod_series, R.string.ondemand_series_sobre),
            Triple(GradeActivity.ANIMES, R.string.vod_animes, R.string.ondemand_animes_sobre),
            Triple(GradeActivity.DORAMAS, R.string.vod_doramas, R.string.ondemand_doramas_sobre),
        )
        return Inicio.Fila(getString(R.string.ondemand_categorias), itens.map { (t, nome, sobre) ->
            val emAlta = destaques.flatMap { it.itens }.filter { it.tipo == letraDe(t) && it.capa.isNotEmpty() }
                .distinctBy { it.titulo }
            val colagem = (emAlta.map { it.capa } + acervo[t].orEmpty().take(8).map { it.capa })
                .distinct().take(4)
            val primeiro = emAlta.firstOrNull()?.let {
                Alvo(it.titulo, it.serie, it.letra, it.ano, colecao = if (it.daColecao) it.colecao else "")
            } ?: acervo[t]?.firstOrNull()?.alvo
            val quantos = contagens[t]?.takeIf { it > 0 }
                ?.let { getString(R.string.ondemand_titulos, numero.format(it)) }
            Inicio.Cartao(
                titulo = getString(nome), capa = "", inicial = getString(nome).take(1),
                subtitulo = quantos,
                colagem = colagem,
                icone = when (t) {
                    GradeActivity.FILMES -> R.drawable.ic_movie
                    GradeActivity.SERIES -> R.drawable.ic_episodes
                    GradeActivity.ANIMES -> R.drawable.ic_auto_awesome
                    else -> R.drawable.ic_favorite
                },
                cor = when (t) {
                    GradeActivity.FILMES -> COR_FILMES
                    GradeActivity.SERIES -> COR_SERIES
                    GradeActivity.ANIMES -> COR_ANIMES
                    else -> COR_DORAMAS
                },
                meta = quantos.orEmpty(),
                sinopse = getString(sobre),
                fundoDe = primeiro,
            ) { PaginaActivity.abrir(this, t) }
        }, Inicio.Tipo.SECAO)
    }

    private fun mostrar(lista: List<Inicio.Fila>) {
        if (isFinishing || isDestroyed) return
        adaptador.trocar(lista.toList())
        if (!montou) {
            montou = true
            focarQuandoPronto {
                filas.findViewHolderForAdapterPosition(0)?.itemView
                    ?.findViewById<RecyclerView>(R.id.filaCapas)?.getChildAt(0)
            }
        }
    }

    private val esteAno get() = Calendar.getInstance().get(Calendar.YEAR)

    /** Do ano passado para cá, o mais novo primeiro. */
    private fun lancamentos(candidatos: List<Candidato>): List<Inicio.Cartao>? =
        candidatos.filter { it.ano >= esteAno - 1 }.sortedByDescending { it.ano }.take(30)
            .takeIf { it.size >= 4 }?.map { cartao(it.alvo, it.capa) }

    private fun porGenero(candidatos: List<Candidato>, genero: String): List<Candidato>? {
        val bit = Generos.bit(genero)
        return candidatos.filter { it.generos and bit != 0 }
            .sortedByDescending { it.ano }.take(25)
            .takeIf { it.size >= 6 }
    }

    /** Só títulos com capa: fileira de gênero cheia de quadrados vazios não convida ninguém. */
    private suspend fun daColecao(colecaoTipo: String): List<Candidato> =
        Vod.colecao(this, colecaoTipo).mapNotNull { c ->
            val alvo = Alvo(c.titulo, true, "", c.ano, colecaoTipo, c.tmdbId.toIntOrNull() ?: 0)
            val capa = Generos.capa(c.titulo, true) ?: return@mapNotNull null
            Candidato(alvo, anoDe(c.titulo, c.ano), capa, Generos.bits(c.titulo, true))
        }

    private suspend fun doAcervo(serie: Boolean): List<Candidato> =
        Vod.entradas(this).asSequence().filter { it.serie == serie }.mapNotNull { e ->
            val a = e.achado
            val capa = Generos.capa(a.titulo, a.serie) ?: return@mapNotNull null
            Candidato(Alvo(a.titulo, a.serie, a.letra, a.ano), anoDe(a.titulo, a.ano), capa, Generos.bits(a.titulo, a.serie))
        }.toList()

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
                TODOS -> true
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

    private fun cartao(alvo: Alvo, capa: String, trailer: String = "") = Inicio.Cartao(
        titulo = alvo.nomeCompleto, capa = capa, inicial = alvo.titulo.take(1).uppercase(),
        trailer = trailer.ifEmpty { null },
        progresso = if (alvo.serie) null else Progresso.fracao(this, Progresso.chaveFilme(alvo.titulo)),
        alvo = alvo,
        aoMenu = if (alvo.colecao.isNotEmpty()) null else ({
            val marcado = VodFavoritos.alternar(this, VodFavoritos.Item(alvo.titulo, alvo.serie, alvo.letra, alvo.ano))
            Toast.makeText(this, if (marcado) R.string.vod_ficha_favorito else R.string.inicio_desfavoritar,
                Toast.LENGTH_SHORT).show()
        }),
    ) { FichaActivity.abrir(this, alvo) }

    private fun verMais(tipoDaGrade: String, genero: String) = Inicio.Cartao(getString(R.string.pagina_ver_mais), "", "+") {
        GradeActivity.abrir(this, tipoDaGrade, genero)
    }
}
