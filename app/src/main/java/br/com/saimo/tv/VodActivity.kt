package br.com.saimo.tv

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.net.Uri
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.VideoSize
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.dispose
import coil.load
import kotlinx.coroutines.launch

/**
 * Filmes e séries, navegados só com o direcional.
 *
 * Dezenove mil filmes e nove mil séries não cabem numa tela nem na cabeça de
 * ninguém, então a navegação desce em degraus curtos — tipo, letra, título e,
 * nas séries, temporada e episódio. Cada degrau é uma tela só, com VOLTAR
 * subindo um nível, que é o gesto que todo mundo já conhece do controle.
 */
@UnstableApi
class VodActivity : AppCompatActivity() {

    private data class OpcaoFonte(
        val versao: String,
        val url: String,
        val numero: Int,
        val total: Int,
    )

    private sealed interface Passo {
        object Inicio : Passo
        data class Letras(val filmes: Boolean, val reservado: Boolean = false) : Passo
        data class Titulos(val filmes: Boolean, val letra: String, val reservado: Boolean = false) : Passo
        /// Filmes ou séries inteiros, sem passar por letra.
        data class Tudo(val filmes: Boolean) : Passo
        data class Temporadas(val letra: String, val serie: Serie) : Passo
        data class Episodios(val letra: String, val serie: Serie, val temporada: Int) : Passo
        data class Colecao(val tipo: String) : Passo
        data class Fontes(
            val titulo: String,
            val detalhe: String,
            val opcoes: List<OpcaoFonte>,
            val deSerie: Boolean,
            val chave: String,
        ) : Passo
        data class Resultados(val termo: String) : Passo
        object Favoritos : Passo
        /// A ficha de um título: sinopse, duração, gêneros e elenco.
        data class Ficha(
            val titulo: String,
            val serie: Boolean,
            val ano: String,
            val letra: String,
        ) : Passo
        /// O que um ator fez e que existe neste acervo.
        data class Filmografia(val ator: Int, val nome: String) : Passo
    }

    private data class Linha(
        val texto: String,
        val detalhe: String? = null,
        val inicial: String = "",
        /// Nulo quando a linha não é um título — letra e temporada não têm capa.
        val capaDe: Boolean? = null,
        /// Quanto já foi visto, de 0 a 1. Nulo quando nunca foi aberto.
        val progresso: Float? = null,
        /// Nulo quando a linha não é favoritável — letra, temporada, versão.
        val favorito: VodFavoritos.Item? = null,
        val aoEscolher: () -> Unit,
    )

    private lateinit var lista: RecyclerView
    private lateinit var titulo: TextView
    private lateinit var trilha: TextView
    private lateinit var contagem: TextView
    private lateinit var estado: TextView
    private lateinit var browse: View
    private lateinit var playerView: PlayerView
    private lateinit var seletor: View
    private lateinit var seletorSerie: TextView
    private lateinit var seletorTemporada: TextView
    private lateinit var seletorLista: RecyclerView
    private val seletorAdapter = Adapter()
    private lateinit var campoDeBusca: android.widget.EditText
    private lateinit var secoes: android.widget.LinearLayout
    private lateinit var generosBarra: View
    private lateinit var generosLista: android.widget.LinearLayout
    private lateinit var teclado: View
    private lateinit var termo: TextView
    private lateinit var teclas: android.widget.GridLayout
    private lateinit var ficha: View
    private lateinit var fichaNome: TextView
    private lateinit var fichaDetalhe: TextView
    private lateinit var fichaResolucao: TextView
    private lateinit var fichaTempo: TextView
    private lateinit var fichaCapa: ImageView
    private var player: ExoPlayer? = null

    private val relogio = Handler(Looper.getMainLooper())
    /// A ficha mostra quanto falta, e isso muda enquanto o filme corre.
    private val tique = object : Runnable {
        override fun run() {
            atualizarTempo()
            guardarProgresso()
            relogio.postDelayed(this, 5_000)
        }
    }

    private val pilha = ArrayDeque<Passo>()
    private val adapter = Adapter()
    /// A primeira tela é de fileiras de capa; as outras continuam em lista.
    private val filasAdapter = Inicio.Adapter(
        escopo = lifecycleScope,
        capaDe = { titulo, serie -> Generos.capa(titulo, serie) })
    private var gavetas: List<Vod.Gaveta> = emptyList()
    /// Os nomes do acervo comum, peneira do "continue assistindo".
    private var nomesDoAcervo: Set<String> = emptySet()
    /// Os nomes de animes e doramas, que não entram no índice de busca.
    private var nomesDasColecoes: Set<String> = emptySet()
    /// O gênero escolhido na régua, ou vazio para todos.
    private var genero = ""
    private var episodiosDaSerie: List<Episodio> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_vod)

        lista = findViewById(R.id.vodLista)
        campoDeBusca = findViewById(R.id.vodBusca)
        secoes = findViewById(R.id.vodSecoes)
        generosBarra = findViewById(R.id.vodGenerosBarra)
        generosLista = findViewById(R.id.vodGeneros)
        montarFerramentas()
        titulo = findViewById(R.id.vodTitulo)
        trilha = findViewById(R.id.vodTrilha)
        contagem = findViewById(R.id.vodContagem)
        estado = findViewById(R.id.vodEstado)
        browse = findViewById(R.id.browse)
        // Sem isto o sistema dá o foco ao primeiro focável — o campo de busca —
        // e o teclado abre sozinho em cima da tela que a pessoa veio ver.
        browse.isFocusableInTouchMode = true
        browse.requestFocus()
        playerView = findViewById(R.id.vodPlayer)
        seletor = findViewById(R.id.vodSeletor)
        seletorSerie = findViewById(R.id.vodSeletorSerie)
        seletorTemporada = findViewById(R.id.vodSeletorTemporada)
        seletorLista = findViewById(R.id.vodSeletorLista)
        seletorLista.layoutManager = GridLayoutManager(this, 1)
        seletorLista.adapter = seletorAdapter
        teclado = findViewById(R.id.vodTeclado)
        termo = findViewById(R.id.vodTermo)
        teclas = findViewById(R.id.vodTeclas)
        montarTeclado()
        ficha = findViewById(R.id.vodFicha)
        fichaNome = findViewById(R.id.vodFichaNome)
        fichaDetalhe = findViewById(R.id.vodFichaDetalhe)
        fichaResolucao = findViewById(R.id.vodFichaResolucao)
        fichaTempo = findViewById(R.id.vodFichaTempo)
        fichaCapa = findViewById(R.id.vodFichaCapa)
        // A ficha acompanha a barra de controle: aparece com ela e some junto.
        playerView.setControllerVisibilityListener(
            PlayerView.ControllerVisibilityListener { visivel ->
                ficha.visibility = if (player != null && visivel == View.VISIBLE) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            })
        playerView.setShowSubtitleButton(true)

        lista.layoutManager = GridLayoutManager(this, 1)
        lista.adapter = adapter
        lista.setHasFixedSize(true)

        titulo.text = getString(R.string.vod)
        ir(Passo.Inicio)
    }

    // MARK: - Navegação

    /**
     * Onde cada degrau estava quando alguém saiu dele.
     *
     * Com trinta e quatro mil filmes numa lista só, descer, abrir um título e
     * voltar ao topo é perder o lugar de verdade — e obrigar a descer tudo de
     * novo. A posição é guardada ao sair e devolvida ao voltar.
     */
    private val ondeParou = mutableMapOf<String, Int>()

    private fun marcaDe(passo: Passo): String = passo.toString()

    private fun guardarPosicao() {
        val passo = pilha.lastOrNull() ?: return
        val gerente = lista.layoutManager as? GridLayoutManager ?: return
        val primeiro = gerente.findFirstVisibleItemPosition()
        if (primeiro >= 0) ondeParou[marcaDe(passo)] = primeiro
    }

    private fun ir(passo: Passo) {
        guardarPosicao()
        pilha.addLast(passo)
        mostrar(passo)
    }

    private fun voltar(): Boolean {
        if (player != null) {
            pararFilme()
            return true
        }
        if (pilha.size <= 1) return false
        // O degrau que está saindo não interessa mais; guardar a posição dele
        // aqui sobrescreveria a do degrau de baixo, que é o que vai voltar.
        pilha.removeLast()
        mostrar(pilha.last())
        return true
    }

    private fun mostrar(passo: Passo) = lifecycleScope.launch(semDerrubar) {
        estado.text = getString(R.string.vod_carregando)
        estado.visibility = View.VISIBLE

        if (passo is Passo.Inicio) {
            mostrarInicio()
            return@launch
        }
        if (lista.adapter !== adapter) {
            lista.layoutManager = GridLayoutManager(this@VodActivity, 1)
            lista.adapter = adapter
        }

        val linhas: List<Linha> = when (passo) {
            is Passo.Inicio -> inicio()
            is Passo.Letras -> letras(passo)
            is Passo.Tudo -> tudo(passo.filmes)
            is Passo.Titulos -> titulos(passo.filmes, passo.letra, passo.reservado)
            is Passo.Temporadas -> temporadas(passo.letra, passo.serie)
            is Passo.Episodios -> episodios(passo.temporada)
            is Passo.Colecao -> colecao(passo.tipo)
            is Passo.Fontes -> fontes(passo)
            is Passo.Resultados -> resultados(passo.termo)
            is Passo.Favoritos -> favoritos()
            is Passo.Ficha -> fichaDoTitulo(passo)
            is Passo.Filmografia -> filmografia(passo.ator)
        }
        val visiveis = porGenero(linhas, passo)
        estado.visibility = if (visiveis.isEmpty()) View.VISIBLE else View.GONE
        if (visiveis.isEmpty()) estado.text = getString(R.string.vod_vazio)

        // Letras cabem lado a lado; título e episódio precisam da linha inteira.
        // Letra é curta e cabe muita numa linha; título precisa de largura, mas
        // duas colunas ainda dobram o que se vê sem apertar o texto.
        (lista.layoutManager as GridLayoutManager).spanCount = when (passo) {
            is Passo.Letras -> 6
            is Passo.Titulos, is Passo.Episodios, is Passo.Colecao,
            is Passo.Resultados, is Passo.Favoritos, is Passo.Tudo,
            is Passo.Filmografia -> 2
            else -> 1
        }
        trilha.text = trilhaDe(passo)
        // Contar duas seções ou vinte e sete letras não diz nada a ninguém; o
        // número só ajuda quando são títulos.
        contagem.text = when {
            visiveis.isEmpty() || passo is Passo.Inicio || passo is Passo.Letras -> ""
            passo is Passo.Fontes ->
                resources.getQuantityString(R.plurals.vod_fontes, visiveis.size, visiveis.size)
            else -> resources.getQuantityString(R.plurals.vod_titulos, visiveis.size, visiveis.size)
        }
        atualizarBarraDeGeneros()
        adapter.trocar(visiveis)
        val volta = ondeParou[marcaDe(passo)] ?: 0
        lista.post {
            if (volta in visiveis.indices) {
                (lista.layoutManager as? GridLayoutManager)?.scrollToPosition(volta)
                // Um quadro depois: a célula só existe depois de a lista rolar.
                lista.post {
                    lista.findViewHolderForAdapterPosition(volta)?.itemView?.requestFocus()
                        ?: lista.requestFocus()
                }
            } else {
                lista.getChildAt(0)?.requestFocus() ?: lista.requestFocus()
            }
        }
    }

    private fun trilhaDe(passo: Passo): String = when (passo) {
        is Passo.Inicio -> ""
        is Passo.Letras -> secao(passo.filmes, passo.reservado)
        is Passo.Tudo -> secao(passo.filmes)
        is Passo.Titulos -> "${secao(passo.filmes, passo.reservado)} › ${passo.letra}"
        is Passo.Temporadas -> "${getString(R.string.vod_series)} › ${passo.serie.nomeCompleto}"
        is Passo.Episodios -> "${passo.serie.nomeCompleto} › " +
            getString(R.string.vod_temporada, passo.temporada)
        is Passo.Ficha -> passo.titulo
        is Passo.Filmografia -> passo.nome
        is Passo.Colecao -> getString(
            if (passo.tipo == "animes") R.string.vod_animes else R.string.vod_doramas)
        is Passo.Fontes -> "${passo.titulo} › ${getString(R.string.vod_escolha_fonte)}"
        is Passo.Resultados -> getString(R.string.vod_resultados, passo.termo)
        is Passo.Favoritos -> getString(R.string.vod_favoritos)
    }

    private fun secao(filmes: Boolean, reservado: Boolean = false) = getString(
        when {
            reservado -> R.string.vod_extras
            filmes -> R.string.vod_filmes
            else -> R.string.vod_series
        })

    // MARK: - Busca

    private var digitado = StringBuilder()

    /// Teclado montado em código: vinte e oito botões iguais em XML seriam
    /// vinte e oito blocos para manter em sincronia.
    private fun montarTeclado() {
        val letras = ('A'..'Z').map { it.toString() } + (0..9).map { it.toString() }
        for (tecla in letras) {
            teclas.addView(botaoTecla(tecla, 1) { digitar(tecla) })
        }
        teclas.addView(botaoTecla(getString(R.string.vod_espaco), 2) { digitar(" ") })
        teclas.addView(botaoTecla(getString(R.string.pad_delete), 2) { apagar() })
        teclas.addView(botaoTecla(getString(R.string.vod_buscar), 2) { confirmarBusca() })
    }

    private fun botaoTecla(texto: String, colunas: Int, aoTocar: () -> Unit): View {
        val botao = TextView(this).apply {
            text = texto
            gravity = android.view.Gravity.CENTER
            // Context.getColor é do Android 6; o ContextCompat vale no 5 também.
            setTextColor(ContextCompat.getColor(this@VodActivity, R.color.text_primary))
            textSize = if (colunas > 1) 16f else 20f
            isFocusable = true
            setBackgroundResource(R.drawable.row_focus)
            setOnClickListener { aoTocar() }
        }
        val parametros = android.widget.GridLayout.LayoutParams().apply {
            width = 0
            height = 74
            columnSpec = android.widget.GridLayout.spec(
                android.widget.GridLayout.UNDEFINED, colunas, 1f)
            setMargins(5, 5, 5, 5)
        }
        botao.layoutParams = parametros
        return botao
    }

    /**
     * As ferramentas de busca, no alto do acervo.
     *
     * O campo e as seções ficavam no fim de uma lista que era preciso
     * percorrer — e são justamente as duas coisas que alguém quer ao abrir o
     * acervo. Em cima, o direcional chega nelas de imediato.
     *
     * O campo é um `EditText` de verdade: no TV Box, apertar OK nele abre o
     * teclado do sistema, que já aceita voz no controle de quem tem. O teclado
     * desenhado à mão continua existindo como caminho alternativo, para os
     * aparelhos que não trazem teclado nenhum na tela.
     */
    private fun montarFerramentas() {
        campoDeBusca.setOnEditorActionListener { _, acao, evento ->
            val enter = acao == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH ||
                evento?.keyCode == KeyEvent.KEYCODE_ENTER
            if (enter) buscarDoCampo()
            enter
        }
        // Buscar ao digitar seria uma busca por letra em trinta mil títulos a
        // cada tecla; no TV Box isso trava. Vale quando a pessoa termina.
        campoDeBusca.setOnKeyListener { _, codigo, evento ->
            if (codigo == KeyEvent.KEYCODE_DPAD_CENTER && evento.action == KeyEvent.ACTION_UP &&
                campoDeBusca.text.isNotBlank()) {
                buscarDoCampo(); true
            } else false
        }

        val atalhos = listOf(
            getString(R.string.vod_filmes) to { ir(Passo.Tudo(filmes = true)) },
            getString(R.string.vod_series) to { ir(Passo.Tudo(filmes = false)) },
            getString(R.string.vod_animes) to { ir(Passo.Colecao("animes")) },
            getString(R.string.vod_doramas) to { ir(Passo.Colecao("doramas")) },
            getString(R.string.vod_favoritos) to { ir(Passo.Favoritos) },
        )
        secoes.removeAllViews()
        for ((nome, acao) in atalhos) {
            secoes.addView(pilula(nome) { acao() })
        }
        if (Unlock.unlocked) {
            secoes.addView(pilula(getString(R.string.vod_extras)) {
                ir(Passo.Letras(filmes = true, reservado = true))
            })
        }
        montarGeneros()
    }

    /**
     * A régua de gêneros, depois das seções.
     *
     * O catálogo não tem gênero: ele vem de um arquivo publicado à parte. Por
     * isso a régua só existe depois que esse arquivo chega — oferecer um filtro
     * que devolve vazio é pior que não oferecer.
     */
    private fun montarGeneros() {
        lifecycleScope.launch(semDerrubar) {
            Generos.carregar(this@VodActivity)
            if (Generos.todos.isEmpty() || isFinishing || isDestroyed) return@launch
            // Fileira própria: na mesma linha das seções, a barra ficava longa
            // demais e empurrava o campo de busca para fora da tela.
            generosLista.removeAllViews()
            generosLista.addView(pilula(getString(R.string.vod_todos)) { escolherGenero("") })
            for (nome in Generos.todos) {
                generosLista.addView(pilula(nome) { escolherGenero(nome) })
            }
            atualizarBarraDeGeneros()
        }
    }

    /**
     * A régua de gêneros só existe onde ela filtra alguma coisa.
     *
     * Na tela inicial as fileiras são curadoria — "em alta", "lançamentos" — e
     * peneirá-las por gênero deixa faixas com um cartão ou nenhum. Nas seções,
     * que são o acervo inteiro, é onde o gênero serve.
     */
    private fun atualizarBarraDeGeneros() {
        val passo = pilha.lastOrNull()
        val cabe = Generos.todos.isNotEmpty() &&
            (passo is Passo.Tudo || passo is Passo.Titulos || passo is Passo.Colecao)
        generosBarra.visibility = if (cabe) View.VISIBLE else View.GONE
    }

    private fun escolherGenero(nome: String) {
        genero = if (genero == nome) "" else nome
        // `mostrar` cuida das outras telas; o início tem caminho próprio.
        if (pilha.last() is Passo.Inicio) {
            lifecycleScope.launch(semDerrubar) { mostrarInicio() }
        } else {
            mostrar(pilha.last())
        }
    }



    private fun pilula(texto: String, aoTocar: () -> Unit): View {
        val botao = android.widget.TextView(this).apply {
            this.text = texto
            textSize = 17f
            setTextColor(ContextCompat.getColor(this@VodActivity, R.color.text_primary))
            setBackgroundResource(R.drawable.bg_campo)
            isFocusable = true
            setPadding(26, 12, 26, 12)
            setOnClickListener { aoTocar() }
        }
        val regras = android.widget.LinearLayout.LayoutParams(
            android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
            android.widget.LinearLayout.LayoutParams.WRAP_CONTENT)
        regras.marginStart = 10
        botao.layoutParams = regras
        return botao
    }

    private fun buscarDoCampo() {
        val alvo = campoDeBusca.text.toString().trim()
        if (alvo.length < 2) return
        esconderTeclado()
        ir(Passo.Resultados(alvo))
    }

    private fun esconderTeclado() {
        val servico = getSystemService(android.content.Context.INPUT_METHOD_SERVICE)
            as android.view.inputmethod.InputMethodManager
        servico.hideSoftInputFromWindow(campoDeBusca.windowToken, 0)
        campoDeBusca.clearFocus()
    }

    private fun abrirBusca() {
        digitado = StringBuilder()
        termo.text = ""
        teclado.visibility = View.VISIBLE
        teclado.post { teclas.getChildAt(0)?.requestFocus() }
    }

    private fun fecharBusca() {
        teclado.visibility = View.GONE
        lista.post { lista.getChildAt(0)?.requestFocus() }
    }

    private fun digitar(texto: String) {
        if (digitado.length >= 40) return
        digitado.append(texto)
        termo.text = digitado
    }

    private fun apagar() {
        if (digitado.isNotEmpty()) digitado.deleteCharAt(digitado.length - 1)
        termo.text = digitado
    }

    private fun confirmarBusca() {
        val alvo = digitado.toString().trim()
        teclado.visibility = View.GONE
        if (alvo.length < 2) return
        ir(Passo.Resultados(alvo))
    }

    private suspend fun resultados(termo: String): List<Linha> =
        Vod.buscar(this, termo).also { if (it.isEmpty()) Telemetria.buscouSemAchar("vod", termo) }.map { achado ->
            Linha(achado.nomeCompleto,
                getString(if (achado.serie) R.string.vod_series else R.string.vod_filmes_um),
                inicial(achado.titulo), capaDe = achado.serie,
                // Série guarda progresso por episódio, e a busca devolve o título:
                // sem saber qual episódio, ela não tem o que mostrar aqui.
                progresso = if (achado.serie) null
                            else Progresso.fracao(this, Progresso.chaveFilme(achado.titulo)),
                favorito = VodFavoritos.Item(
                    achado.titulo, achado.serie, achado.letra, achado.ano)) {
                lifecycleScope.launch(semDerrubar) { abrirAchado(achado) }
            }
        }

    private suspend fun abrirAchado(achado: Vod.Achado) {
        // A busca também precisa da chave certa, senão o filme achado por ela
        // não retomaria de onde parou.
        if (achado.serie) {
            Vod.serie(this, achado)?.let { ir(Passo.Temporadas(achado.letra, it)) }
        } else {
            val filme = Vod.filme(this, achado) ?: return
            abrirFilme(filme)
        }
    }

    // MARK: - Degraus

    private suspend fun inicio(): List<Linha> {
        if (gavetas.isEmpty()) gavetas = Vod.indice(this)
        val filmes = gavetas.sumOf { it.filmes }
        val series = gavetas.sumOf { it.series }
        val animes = Vod.colecao(this, "animes").size
        val doramas = Vod.colecao(this, "doramas").size
        // Favoritos em primeiro: quem marcou um título marcou para voltar nele.
        return favoritosNoInicio() + listOf(
            Linha(getString(R.string.vod_filmes), resources.getQuantityString(R.plurals.vod_titulos, filmes, filmes), "F") {
                ir(Passo.Tudo(filmes = true))
            },
            Linha(getString(R.string.vod_series), resources.getQuantityString(R.plurals.vod_titulos, series, series), "S") {
                ir(Passo.Letras(filmes = false))
            },
            Linha(getString(R.string.vod_animes), resources.getQuantityString(R.plurals.vod_titulos, animes, animes), "A") {
                ir(Passo.Colecao("animes"))
            },
            Linha(getString(R.string.vod_doramas), resources.getQuantityString(R.plurals.vod_titulos, doramas, doramas), "D") {
                ir(Passo.Colecao("doramas"))
            },
            Linha(getString(R.string.vod_buscar), getString(R.string.vod_buscar_dica), "?") {
                abrirBusca()
            }) + reservados()
    }

    /**
     * A primeira tela: fileiras de capa, como numa TV.
     *
     * A ordem é a de quem chega: primeiro o que a pessoa já estava vendo,
     * depois o que ela marcou, depois as novidades, e o acervo inteiro por
     * último — quem veio procurar uma coisa específica usa a busca, que
     * continua a um botão de distância.
     *
     * Fileira vazia não entra. Uma faixa com título e nada embaixo é pior que
     * a ausência dela.
     */
    private suspend fun mostrarInicio() {
        if (gavetas.isEmpty()) gavetas = Vod.indice(this)
        if (nomesDoAcervo.isEmpty()) nomesDoAcervo = Vod.nomesDoAcervo(this)
        if (nomesDasColecoes.isEmpty()) {
            nomesDasColecoes = listOf("animes", "doramas")
                .flatMap { Vod.colecao(this, it) }
                .map { it.titulo }
                .toSet()
        }
        val filas = mutableListOf<Inicio.Fila>()

        continuar()?.let { filas += it }
        favoritosEmCapa()?.let { filas += it }

        for (fila in Destaques.filas(this)) {
            val cartoes = fila.itens.map { item -> cartaoDe(item) }
            if (cartoes.isNotEmpty()) filas += Inicio.Fila(fila.titulo, cartoes)
        }

        filas += acervo()

        estado.visibility = if (filas.isEmpty()) View.VISIBLE else View.GONE
        if (filas.isEmpty()) estado.text = getString(R.string.vod_vazio)
        if (lista.adapter !== filasAdapter) {
            lista.layoutManager = LinearLayoutManager(this)
            lista.adapter = filasAdapter
        }
        generosBarra.visibility = View.GONE
        filasAdapter.trocar(filas)
        trilha.visibility = View.GONE
        lista.post { lista.requestFocus() }
    }

    /** Um destaque vira cartão: a capa já veio pronta, a ação reusa a busca. */
    /**
     * As linhas que sobram depois do gênero escolhido.
     *
     * Só vale onde a linha é um título: letra, temporada e escolha de fonte
     * não têm gênero, e some-las seria deixar a tela vazia sem motivo.
     */
    private fun porGenero(linhas: List<Linha>, passo: Passo): List<Linha> {
        if (genero.isEmpty() || !Generos.prontos) return linhas
        val deTitulo = passo is Passo.Titulos || passo is Passo.Colecao ||
            passo is Passo.Resultados || passo is Passo.Favoritos || passo is Passo.Tudo
        if (!deTitulo) return linhas
        return linhas.filter { linha ->
            val serie = linha.capaDe ?: return@filter true
            Generos.tem(linha.texto, serie, genero)
        }
    }

    private fun cartaoDe(item: Destaques.Item) = Inicio.Cartao(
        titulo = item.titulo,
        capa = item.capa,
        inicial = inicial(item.titulo),
        progresso = if (item.serie) null
                    else Progresso.fracao(this, Progresso.chaveFilme(item.titulo)),
    ) {
        lifecycleScope.launch(semDerrubar) { abrirDestaque(item) }
    }

    /**
     * Abre um destaque.
     *
     * Filme e série passam pelo mesmo caminho da busca. Anime e dorama moram
     * nas coleções, que já vêm com os episódios dentro: achando o título ali,
     * dá para ir direto às temporadas em vez de despejar a coleção inteira na
     * frente de quem só queria aquele.
     */
    private suspend fun abrirDestaque(item: Destaques.Item) {
        if (item.daColecao) {
            val achado = Vod.colecao(this, item.colecao)
                .firstOrNull { it.titulo.equals(item.titulo, ignoreCase = true) }
            if (achado == null) {
                ir(Passo.Colecao(item.colecao))
                return
            }
            episodiosDaSerie = achado.episodios
            ir(Passo.Temporadas("", Serie(achado.titulo, achado.ano, -1, achado.episodios.size)))
            return
        }
        abrirAchado(Vod.Achado(item.titulo, item.serie, item.letra, item.ano))
    }

    /**
     * Onde a pessoa parou, do mais recente para o mais antigo.
     *
     * É a fileira que mais importa e a única que não vem do repositório: ela é
     * feita do que está gravado neste aparelho.
     *
     * Só entra o que existe no acervo comum. Os extras ficam de fora do índice
     * de busca de propósito, e a mesma regra vale aqui: a tela inicial abre sem
     * código nenhum e não pode ser por onde um título reservado reaparece.
     * Enquanto o índice não chegou, a fileira fica vazia — mostrar de menos é o
     * erro certo a cometer.
     */
    private fun continuar(): Inicio.Fila? {
        // Anime e dorama não entram no índice de busca (eles moram nas
        // coleções), então a peneira precisa conhecer os dois: sem isso, o
        // episódio de dorama que a pessoa parou no meio sumia daqui.
        val itens = Progresso.emAndamento(this)
            .filter { it.titulo in nomesDoAcervo || it.titulo in nomesDasColecoes }
            .take(20)
        if (itens.isEmpty()) return null
        val cartoes = itens.map { andamento ->
            Inicio.Cartao(
                titulo = andamento.rotulo,
                capa = "",
                inicial = inicial(andamento.titulo),
                progresso = andamento.fracao,
                procurarCapa = andamento.serie,
                // O cartão diz "Série · T1 E3"; a capa é a da série.
                nomeDaCapa = andamento.titulo,
            ) {
                lifecycleScope.launch(semDerrubar) {
                    // A letra não é guardada junto do progresso; a busca a
                    // devolve, e é ela quem sabe achar o título no acervo.
                    val achados = Vod.buscar(this@VodActivity, andamento.titulo)
                    val alvo = achados.firstOrNull {
                        it.titulo.equals(andamento.titulo, ignoreCase = true)
                    } ?: achados.firstOrNull()
                    if (alvo != null) {
                        abrirAchado(alvo)
                        return@launch
                    }
                    // Não está no acervo comum: procura nas coleções, que é
                    // onde moram anime e dorama.
                    val daColecao = listOf("animes", "doramas").firstNotNullOfOrNull { tipo ->
                        Vod.colecao(this@VodActivity, tipo).firstOrNull {
                            it.titulo.equals(andamento.titulo, ignoreCase = true)
                        }
                    }
                    if (daColecao != null) {
                        episodiosDaSerie = daColecao.episodios
                        ir(Passo.Temporadas("",
                            Serie(daColecao.titulo, daColecao.ano, -1, daColecao.episodios.size)))
                        return@launch
                    }
                    // Nem no acervo nem nas coleções: dizer isso é melhor que
                    // um clique que não faz nada.
                    android.widget.Toast.makeText(
                        this@VodActivity,
                        getString(R.string.vod_sumiu, andamento.titulo),
                        android.widget.Toast.LENGTH_LONG,
                    ).show()
                }
            }
        }
        return Inicio.Fila(getString(R.string.vod_continuar), cartoes)
    }

    /** Os favoritos em capa, na mesma fileira. */
    private fun favoritosEmCapa(): Inicio.Fila? {
        val itens = VodFavoritos.lista(this)
        if (itens.isEmpty()) return null
        return Inicio.Fila(getString(R.string.vod_favoritos), itens.map { item ->
            Inicio.Cartao(
                titulo = item.nomeCompleto,
                capa = "",
                inicial = inicial(item.titulo),
                progresso = if (item.serie) null
                            else Progresso.fracao(this, Progresso.chaveFilme(item.titulo)),
                procurarCapa = item.serie,
            ) {
                lifecycleScope.launch(semDerrubar) {
                    abrirAchado(Vod.Achado(item.titulo, item.serie, item.letra, item.ano))
                }
            }
        })
    }

    /** O acervo inteiro, para quem quer navegar em vez de escolher do que tem. */
    private suspend fun acervo(): Inicio.Fila {
        val filmes = gavetas.sumOf { it.filmes }
        val series = gavetas.sumOf { it.series }
        val cartoes = mutableListOf(
            Inicio.Cartao(getString(R.string.vod_filmes), "", "F") { ir(Passo.Letras(filmes = true)) },
            Inicio.Cartao(getString(R.string.vod_series), "", "S") { ir(Passo.Letras(filmes = false)) },
            Inicio.Cartao(getString(R.string.vod_animes), "", "A") { ir(Passo.Colecao("animes")) },
            Inicio.Cartao(getString(R.string.vod_doramas), "", "D") { ir(Passo.Colecao("doramas")) },
            Inicio.Cartao(getString(R.string.vod_buscar), "", "?") { abrirBusca() },
        )
        val reservados = gavetas.sumOf { it.reservados }
        if (reservados > 0 && Unlock.unlocked) {
            cartoes += Inicio.Cartao(getString(R.string.vod_extras), "", "+") {
                ir(Passo.Letras(filmes = true, reservado = true))
            }
        }
        contagem.text = resources.getQuantityString(
            R.plurals.vod_titulos, filmes + series, filmes + series)
        return Inicio.Fila(getString(R.string.vod_acervo), cartoes)
    }

    /// A linha só existe quando há o que abrir: uma seção vazia na primeira
    /// tela seria um beco.
    private fun favoritosNoInicio(): List<Linha> {
        val quantos = VodFavoritos.lista(this).size
        if (quantos == 0) return emptyList()
        return listOf(Linha(getString(R.string.vod_favoritos),
            resources.getQuantityString(R.plurals.vod_titulos, quantos, quantos), "★") {
            ir(Passo.Favoritos)
        })
    }

    /**
     * A ficha de um título, em linhas: sinopse, números, quem assina, elenco.
     *
     * A tela é uma lista de linhas — é o que esta activity sabe desenhar e o
     * que o direcional sabe percorrer. Cada ator é uma linha que abre o que
     * ele tem aqui dentro, que é a única filmografia que vale de dentro do
     * aplicativo.
     */
    private suspend fun fichaDoTitulo(passo: Passo.Ficha): List<Linha> {
        val ficha = Detalhes.de(passo.titulo, passo.serie)
        val linhas = mutableListOf<Linha>()

        linhas += Linha(getString(R.string.vod_ficha_assistir), passo.titulo, "▶") {
            lifecycleScope.launch(semDerrubar) {
                abrirAchado(Vod.Achado(passo.titulo, passo.serie, passo.letra, passo.ano))
            }
        }

        if (ficha == null || ficha.vazia) {
            linhas += Linha(getString(R.string.vod_ficha_vazia), null, "?") {}
            return linhas
        }

        val numeros = buildList {
            if (ficha.ano.isNotBlank()) add(ficha.ano)
            ficha.duracao?.let {
                add(if (passo.serie) getString(R.string.vod_ficha_minutos_ep, it)
                    else getString(R.string.vod_ficha_minutos, it))
            }
            ficha.classificacao?.takeIf { it.isNotBlank() }?.let { add(it) }
            if (ficha.nota > 0) add("★ %.1f".format(ficha.nota))
        }.joinToString("  ·  ")
        if (numeros.isNotBlank()) {
            linhas += Linha(numeros, ficha.generos.joinToString(", ").ifBlank { null }, "i") {}
        }

        if (ficha.sinopse.isNotBlank()) {
            linhas += Linha(getString(R.string.vod_ficha_sinopse), ficha.sinopse, "¶") {}
        }
        if (ficha.assinatura.isNotBlank()) {
            linhas += Linha(
                getString(if (passo.serie) R.string.vod_ficha_criacao
                          else R.string.vod_ficha_direcao),
                ficha.assinatura, "✎") {}
        }
        if (ficha.roteiro.isNotBlank()) {
            linhas += Linha(getString(R.string.vod_ficha_roteiro), ficha.roteiro, "✎") {}
        }
        if (ficha.produtora.isNotBlank()) {
            linhas += Linha(getString(R.string.vod_ficha_producao), ficha.produtora, "©") {}
        }
        for (pessoa in ficha.elenco) {
            linhas += Linha(pessoa.nome, pessoa.papel.ifBlank { null },
                pessoa.nome.take(1).uppercase()) {
                ir(Passo.Filmografia(pessoa.id, pessoa.nome))
            }
        }
        return linhas
    }

    /** O que um ator fez e que existe no acervo, pronto para abrir. */
    private suspend fun filmografia(ator: Int): List<Linha> {
        val achados = Detalhes.acervoDe(this, ator)
        if (achados.isEmpty()) {
            return listOf(Linha(getString(R.string.vod_ficha_sem_acervo), null, "?") {})
        }
        return achados.map { achado ->
            Linha(achado.nomeCompleto,
                getString(if (achado.serie) R.string.vod_series else R.string.vod_filmes_um),
                inicial(achado.titulo), capaDe = achado.serie) {
                lifecycleScope.launch(semDerrubar) { abrirAchado(achado) }
            }
        }
    }

    private fun favoritos(): List<Linha> = VodFavoritos.lista(this).map { item ->
        Linha(item.nomeCompleto,
            getString(if (item.serie) R.string.vod_series else R.string.vod_filmes_um),
            inicial(item.titulo), capaDe = item.serie,
            progresso = if (item.serie) null
                        else Progresso.fracao(this, Progresso.chaveFilme(item.titulo)),
            favorito = item) {
            lifecycleScope.launch(semDerrubar) {
                abrirAchado(Vod.Achado(item.titulo, item.serie, item.letra, item.ano))
            }
        }
    }

    /// Abre a ficha do título em foco. Linha que não é título não tem ficha.
    private fun fichaEmFoco() {
        val item = linhaEmFoco()?.favorito ?: return
        ir(Passo.Ficha(item.titulo, item.serie, item.ano, item.letra))
    }

    private fun linhaEmFoco(): Linha? {
        val foco = currentFocus ?: return null
        val posicao = lista.getChildAdapterPosition(
            generateSequence(foco) { it.parent as? View }
                .firstOrNull { it.parent === lista } ?: return null)
        if (posicao == RecyclerView.NO_POSITION) return null
        return adapter.linha(posicao)
    }

    /// A tecla age sobre a linha em foco, e é o adaptador quem sabe qual é.
    private fun favoritarEmFoco() {
        val foco = currentFocus ?: return
        val posicao = lista.getChildAdapterPosition(
            generateSequence(foco) { it.parent as? View }
                .firstOrNull { it.parent === lista } ?: return)
        if (posicao == RecyclerView.NO_POSITION) return
        adapter.linha(posicao)?.let { favoritar(it) }
    }

    private fun marcado(linha: Linha): Boolean =
        linha.favorito?.let { VodFavoritos.contem(this, it.titulo, it.serie, it.ano) } == true

    /// Marcar de dentro da própria lista de favoritos tira a linha da tela, e aí
    /// a lista precisa ser refeita; nas outras basta a estrela acender.
    private fun favoritar(linha: Linha) {
        val item = linha.favorito ?: return
        VodFavoritos.alternar(this, item)
        if (pilha.lastOrNull() is Passo.Favoritos) mostrar(Passo.Favoritos)
        else adapter.notifyDataSetChanged()
    }

    /// Só existe depois do código, e como uma linha igual às outras: nada aqui
    /// diz que ela é diferente enquanto não estiver à vista.
    private fun reservados(): List<Linha> {
        if (!Unlock.unlocked) return emptyList()
        val total = gavetas.sumOf { it.reservados }
        if (total == 0) return emptyList()
        return listOf(Linha(getString(R.string.vod_extras),
            resources.getQuantityString(R.plurals.vod_titulos, total, total), "+") {
            ir(Passo.Letras(filmes = true, reservado = true))
        })
    }

    private fun letras(passo: Passo.Letras) = gavetas
        .filter {
            when {
                passo.reservado -> it.reservados > 0
                passo.filmes -> it.filmes > 0
                else -> it.series > 0
            }
        }
        .map { gaveta ->
            val quantos = when {
                passo.reservado -> gaveta.reservados
                passo.filmes -> gaveta.filmes
                else -> gaveta.series
            }
            Linha(gaveta.letra, quantos.toString(), "") {
                ir(Passo.Titulos(passo.filmes, gaveta.letra, passo.reservado))
            }
        }

    private suspend fun titulos(filmes: Boolean, letra: String,
                                reservado: Boolean = false): List<Linha> =
        if (filmes) {
            Vod.filmes(this, letra, reservado).map { filme ->
                Linha(filme.titulo, detalheFilme(filme), inicial(filme.titulo), capaDe = false,
                    progresso = Progresso.fracao(this, Progresso.chaveFilme(filme.titulo)),
                    favorito = VodFavoritos.Item(filme.titulo, serie = false, letra = letra)) {
                    abrirFilme(filme)
                }
            }
        } else {
            Vod.series(this, letra).map { serie ->
                Linha(serie.nomeCompleto, getString(R.string.vod_eps, serie.episodios),
                    inicial(serie.titulo), capaDe = true,
                    favorito = VodFavoritos.Item(
                        serie.titulo, serie = true, letra = letra, ano = serie.ano)) {
                    ir(Passo.Temporadas(letra, serie))
                }
            }
        }

    /**
     * O acervo inteiro de um tipo, sem escolher letra antes.
     *
     * A lista sai do índice de busca, que tem todos os nomes; abrir um título
     * continua indo ao arquivo da letra dele. São vinte e dois mil filmes numa
     * lista só, o que o RecyclerView aguenta porque desenha apenas o que está
     * à vista.
     */
    private suspend fun tudo(filmes: Boolean): List<Linha> =
        Vod.todos(this, serie = !filmes).map { achado ->
            Linha(achado.nomeCompleto,
                getString(if (achado.serie) R.string.vod_series else R.string.vod_filmes_um),
                inicial(achado.titulo), capaDe = achado.serie,
                progresso = if (achado.serie) null
                            else Progresso.fracao(this, Progresso.chaveFilme(achado.titulo)),
                favorito = VodFavoritos.Item(
                    achado.titulo, achado.serie, achado.letra, achado.ano)) {
                lifecycleScope.launch(semDerrubar) { abrirAchado(achado) }
            }
        }

    /** Animes e doramas já vêm com os episódios no mesmo arquivo. */
    private suspend fun colecao(tipo: String): List<Linha> =
        Vod.colecao(this, tipo).sortedBy { it.titulo.lowercase() }.map { item ->
            Linha(item.nomeCompleto, getString(R.string.vod_eps, item.episodios.size),
                inicial(item.titulo), capaDe = true) {
                episodiosDaSerie = item.episodios
                val serie = Serie(item.titulo, item.ano, -1, item.episodios.size)
                ir(Passo.Temporadas("", serie))
            }
        }

    private suspend fun temporadas(letra: String, serie: Serie): List<Linha> {
        serieNoAr = serie
        letraNoAr = letra
        if (serie.pedaco >= 0) episodiosDaSerie = Vod.episodios(this, letra, serie)
        // A ficha na frente das temporadas: nem todo controle tem tecla INFO,
        // e saber do que a série trata não pode depender de descobrir o atalho.
        val ficha = Linha(getString(R.string.vod_ficha),
            getString(R.string.vod_ficha_dica), "i") {
            ir(Passo.Ficha(serie.titulo, serie = true, ano = serie.ano, letra = letra))
        }
        return listOf(ficha) + episodiosDaSerie.map { it.temporada }.distinct().sorted().map { numero ->
            val quantos = episodiosDaSerie.count { it.temporada == numero }
            Linha(getString(R.string.vod_temporada, numero),
                getString(R.string.vod_eps, quantos), numero.toString()) {
                ir(Passo.Episodios(letra, serie, numero))
            }
        }
    }

    private fun episodios(temporada: Int): List<Linha> = episodiosDaSerie.also {
        temporadaAberta = temporada
    }
        .filter { it.temporada == temporada }
        .sortedWith(compareBy({ it.numero }, { it.versao }))
        .map { episodio ->
            Linha(getString(R.string.vod_episodio, episodio.numero),
                detalhe(episodio.versao, episodio.urls.size), episodio.numero.toString(),
                progresso = Progresso.fracao(this, Progresso.chaveEpisodio(
                    (pilha.last() as? Passo.Episodios)?.serie?.nomeCompleto.orEmpty(),
                    episodio.temporada, episodio.numero))) {
                val serie = (pilha.last() as? Passo.Episodios)?.serie?.nomeCompleto.orEmpty()
                escolherFonte(
                    titulo = serie.ifEmpty { getString(R.string.vod_episodio, episodio.numero) },
                    fontes = mapOf(episodio.versao to episodio.urls),
                    detalhe = getString(R.string.vod_temporada, episodio.temporada) + ", " +
                        getString(R.string.vod_episodio, episodio.numero).lowercase(),
                    deSerie = true,
                    chave = Progresso.chaveEpisodio(
                        serie, episodio.temporada, episodio.numero))
            }
        }

    private fun fontes(passo: Passo.Fontes): List<Linha> {
        val opcoes = passo.opcoes.map { opcao ->
            Linha(
                getString(R.string.vod_fonte_opcao, rotulo(opcao.versao), opcao.numero, opcao.total),
                origem(opcao.url), opcao.numero.toString()) {
                    tocar(passo.titulo, listOf(opcao.url),
                        detalhe = detalheDaFonte(passo.detalhe, opcao),
                        deSerie = passo.deSerie, chave = passo.chave)
                }
        }
        // Escolher a fonte é o último momento antes de o filme começar: é aqui
        // que ainda dá para conferir do que ele trata.
        if (passo.deSerie) return opcoes
        val ficha = Linha(getString(R.string.vod_ficha),
            getString(R.string.vod_ficha_dica), "i") {
            ir(Passo.Ficha(passo.titulo, serie = false, ano = "", letra = letraDe(passo.titulo)))
        }
        return opcoes + ficha
    }

    /// A letra de um título é a inicial que o acervo usa para guardá-lo.
    private fun letraDe(titulo: String): String = inicial(titulo)

    private fun abrirFilme(filme: Filme) {
        escolherFonte(
            titulo = filme.titulo,
            fontes = filme.fontes,
            detalhe = getString(R.string.vod_filmes_um),
            deSerie = false,
            chave = Progresso.chaveFilme(filme.titulo))
    }

    /// Uma fonte abre direto; duas ou mais viram opções explícitas. O servidor
    /// aparece junto para que duas entradas do mesmo idioma não pareçam iguais.
    private fun escolherFonte(
        titulo: String,
        fontes: Map<String, List<String>>,
        detalhe: String,
        deSerie: Boolean,
        chave: String,
    ) {
        val pares = fontes.keys.sortedWith(compareBy({ if (it == "leg") 1 else 0 }, { it }))
            .flatMap { versao -> fontes[versao].orEmpty().map { versao to it } }
        val opcoes = pares.mapIndexed { indice, (versao, url) ->
            OpcaoFonte(versao, url, indice + 1, pares.size)
        }
        val unica = opcoes.singleOrNull()
        if (unica != null) {
            tocar(titulo, listOf(unica.url), detalhe = detalheDaFonte(detalhe, unica),
                deSerie = deSerie, chave = chave)
        } else if (opcoes.isNotEmpty()) {
            ir(Passo.Fontes(titulo, detalhe, opcoes, deSerie, chave))
        }
    }

    private fun detalheDaFonte(base: String, opcao: OpcaoFonte) =
        "$base · ${rotulo(opcao.versao)} · " +
            getString(R.string.vod_fonte_numero, opcao.numero, opcao.total)

    private fun origem(url: String): String = runCatching {
        Uri.parse(url).host?.removePrefix("www.")
    }.getOrNull().orEmpty().ifEmpty { getString(R.string.vod_servidor_desconhecido) }

    /// A contagem avisa que haverá escolha antes de abrir o item.
    private fun detalhe(versao: String, fontes: Int) =
        if (fontes > 1) "${rotulo(versao)} · $fontes fontes" else rotulo(versao)

    private fun detalheFilme(filme: Filme): String {
        val total = filme.fontes.values.sumOf { it.size }
        val versoes = filme.fontes.keys.sorted().joinToString(" · ") { rotulo(it) }
        return if (total > filme.fontes.size) "$versoes · $total fontes" else versoes
    }

    private fun rotulo(versao: String) =
        getString(if (versao == "leg") R.string.vod_legendado else R.string.vod_dublado)

    private fun inicial(texto: String) = texto.trim().take(1).uppercase()

    // MARK: - Reprodução

    /// Depois da escolha, a reprodução recebe apenas a fonte selecionada.
    private var fontesAtuais: List<String> = emptyList()
    private var fonteAtual = 0

    private fun tocar(nome: String, urls: List<String>, indice: Int = 0,
                      detalhe: String = fichaDetalheAtual, deSerie: Boolean = false,
                      chave: String = chaveAtual) {
        if (urls.isEmpty()) return
        // Guarda onde o anterior parou antes de trocar de filme.
        guardarProgresso()
        chaveAtual = chave
        // Sem isto, ver um filme depois de uma série deixaria o atalho de baixo
        // apontando para os episódios da série anterior.
        if (!deSerie && indice == 0) serieNoAr = null
        fichaDetalheAtual = detalhe
        val trocaPorFalha = indice > 0 && titulo.text.toString() == nome && player != null
        fontesAtuais = urls
        fonteAtual = indice.coerceIn(urls.indices)
        val url = urls[fonteAtual]
        pararFilme(avisar = false)
        val tentativaDesde = android.os.SystemClock.elapsedRealtime()
        var tocouAvisado = false
        // Um erro no meio do filme não é fonte morta: é a origem tropeçando.
        // Trocar aqui recomeça o filme noutro servidor, às vezes com outro
        // áudio e outra qualidade, quando bastava pedir de novo o mesmo pedaço.
        var retomadas = 0
        Telemetria.comecou("vod", nome, url, fonteAtual + 1, nova = !trocaPorFalha)
        // Filme não é canal: pausa, volta e avança, então o controle padrão do
        // player fica à vista em vez da faixa de canal ao vivo.
        val novo = ExoPlayer.Builder(this).build()
        Telemetria.observar(novo)
        // Pelo mesmo caminho do canal ao vivo: o provedor responde 302 para uma
        // URL com token e recusa cliente sem User-Agent, e é este cliente que
        // segue redirecionamento e ainda resolve por DNS-over-HTTPS.
        novo.setMediaSource(Playback.mediaSource(this, Source(url)))
        // Fonte morta não pode virar tela preta: cai para a seguinte, como o
        // canal já faz.
        novo.addListener(object : androidx.media3.common.Player.Listener {
            override fun onVideoSizeChanged(videoSize: VideoSize) {
                val rotulo = rotuloResolucao(videoSize.width, videoSize.height)
                fichaResolucao.text = rotulo
                fichaResolucao.visibility = if (rotulo.isEmpty()) View.GONE else View.VISIBLE
            }

            override fun onPlaybackStateChanged(state: Int) {
                if (state == androidx.media3.common.Player.STATE_READY && !tocouAvisado) {
                    tocouAvisado = true
                    Telemetria.tocou("vod", nome, url, fonteAtual + 1,
                        android.os.SystemClock.elapsedRealtime() - tentativaDesde)
                }
            }

            override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                Telemetria.falhou("vod", nome, url, fonteAtual + 1, error.errorCodeName)
                // A fonte já tinha entregado imagem: tenta de novo nela, do
                // ponto em que parou, antes de descer para a seguinte.
                if (tocouAvisado && retomadas < 3) {
                    retomadas++
                    val ponto = novo.currentPosition
                    novo.seekTo(ponto)
                    novo.prepare()
                    novo.playWhenReady = true
                    return
                }
                if (fonteAtual + 1 < fontesAtuais.size) {
                    tocar(nome, fontesAtuais, fonteAtual + 1, deSerie = serieNoAr != null)
                } else {
                    Telemetria.caiu("vod", nome, fontesAtuais.size)
                }
            }
        })
        // Volta ao ponto em que parou. O seek vai antes do prepare para o
        // player já abrir lá, em vez de mostrar o começo e pular depois.
        val retomar = Progresso.posicao(this, chave)
        if (retomar > 0) novo.seekTo(retomar)
        novo.playWhenReady = true
        novo.prepare()
        player = novo
        playerView.player = novo
        playerView.controllerShowTimeoutMs = 5_000
        playerView.visibility = View.VISIBLE
        browse.visibility = View.GONE
        estado.visibility = View.GONE
        playerView.requestFocus()
        titulo.text = nome
        fichaNome.text = nome
        fichaCapa.dispose()
        fichaCapa.setImageDrawable(null)
        fichaCapa.visibility = View.GONE
        lifecycleScope.launch(semDerrubar) {
            val capa = Generos.capa(nome, deSerie) ?: return@launch
            if (fichaNome.text != nome) return@launch
            fichaCapa.visibility = View.VISIBLE
            fichaCapa.load(capa) {
                diskCachePolicy(coil.request.CachePolicy.DISABLED)
                crossfade(true)
            }
        }
        fichaDetalhe.text = detalhe
        fichaDetalhe.visibility = if (detalhe.isEmpty()) View.GONE else View.VISIBLE
        fichaResolucao.text = ""
        fichaResolucao.visibility = View.GONE
        atualizarTempo()
        ficha.visibility = View.VISIBLE
        adiarEsconder()
        relogio.removeCallbacks(tique)
        relogio.postDelayed(tique, 5_000)
    }

    private var fichaDetalheAtual = ""
    private var chaveAtual = ""

    /// Onde o que está tocando parou. Chamado a cada tique, ao sair e ao trocar.
    private fun guardarProgresso() {
        val atual = player ?: return
        if (chaveAtual.isBlank()) return
        Progresso.salvar(this, chaveAtual, atual.currentPosition, atual.duration)
    }
    /// Série do episódio no ar, para o atalho de trocar de episódio.
    private var serieNoAr: Serie? = null
    private var letraNoAr = ""

    /**
     * Some com a barra e devolve o foco ao vídeo.
     *
     * O player esconde a barra sozinho, mas não enquanto um dos botões dela
     * estiver com o foco — que numa TV é sempre. Sem tirar o foco, a barra
     * ficaria para sempre e o "para baixo" nunca chegaria aos episódios.
     */
    private val esconderBarra = Runnable {
        playerView.hideController()
        playerView.requestFocus()
    }

    /// Mostra a barra já com o foco nela, para o primeiro esquerda-direita
    /// depois do OK arrastar o filme em vez de andar entre botões.
    private fun mostrarControles() {
        playerView.showController()
        playerView.post {
            playerView.findViewById<View>(androidx.media3.ui.R.id.exo_progress)?.requestFocus()
        }
        adiarEsconder()
    }

    private fun adiarEsconder() {
        relogio.removeCallbacks(esconderBarra)
        relogio.postDelayed(esconderBarra, 5_000)
    }

    /// Avança ou volta sem precisar mirar na barra: dez segundos por toque, que
    /// é o passo que se espera de um controle.
    private fun pular(milissegundos: Long) {
        val atual = player ?: return
        val destino = (atual.currentPosition + milissegundos)
            .coerceIn(0, if (atual.duration > 0) atual.duration else Long.MAX_VALUE)
        atual.seekTo(destino)
        mostrarControles()
        atualizarTempo()
    }

    private var temporadaAberta = 1

    /// Abre temporadas e episódios por cima do vídeo, que continua correndo.
    private fun abrirSeletor(): Boolean {
        val serie = serieNoAr ?: return false
        if (episodiosDaSerie.isEmpty()) return false
        seletorSerie.text = serie.nomeCompleto
        preencherSeletor()
        seletor.visibility = View.VISIBLE
        playerView.hideController()
        seletorLista.post {
            seletorLista.getChildAt(0)?.requestFocus() ?: seletorLista.requestFocus()
        }
        return true
    }

    private fun fecharSeletor() {
        seletor.visibility = View.GONE
        playerView.requestFocus()
    }

    private fun trocarTemporada(passo: Int) {
        val temporadas = episodiosDaSerie.map { it.temporada }.distinct().sorted()
        if (temporadas.size < 2) return
        val atual = temporadas.indexOf(temporadaAberta).coerceAtLeast(0)
        temporadaAberta = temporadas[(atual + passo + temporadas.size) % temporadas.size]
        preencherSeletor()
        seletorLista.post { seletorLista.getChildAt(0)?.requestFocus() }
    }

    private fun preencherSeletor() {
        val temporadas = episodiosDaSerie.map { it.temporada }.distinct().sorted()
        if (temporadaAberta !in temporadas) temporadaAberta = temporadas.firstOrNull() ?: 1
        seletorTemporada.text = getString(R.string.vod_temporada, temporadaAberta) +
            if (temporadas.size > 1) " · ${temporadas.size} temporadas" else ""
        val serie = serieNoAr
        seletorAdapter.trocar(episodiosDaSerie
            .filter { it.temporada == temporadaAberta }
            .sortedWith(compareBy({ it.numero }, { it.versao }))
            .flatMap { episodio ->
                val nomeSerie = serie?.nomeCompleto.orEmpty()
                val chave = Progresso.chaveEpisodio(
                    nomeSerie, episodio.temporada, episodio.numero)
                episodio.urls.mapIndexed { indice, url ->
                    val opcao = OpcaoFonte(
                        episodio.versao, url, indice + 1, episodio.urls.size)
                    Linha(
                        if (episodio.urls.size > 1) {
                            getString(R.string.vod_episodio_fonte,
                                episodio.numero, opcao.numero, opcao.total)
                        } else {
                            getString(R.string.vod_episodio, episodio.numero)
                        },
                        "${rotulo(episodio.versao)} · ${origem(url)}",
                        episodio.numero.toString(),
                        progresso = Progresso.fracao(this, chave)) {
                        fecharSeletor()
                        tocar(
                            nomeSerie.ifEmpty {
                                getString(R.string.vod_episodio, episodio.numero)
                            },
                            listOf(url),
                            detalhe = detalheDaFonte(
                                getString(R.string.vod_temporada, episodio.temporada) + ", " +
                                    getString(R.string.vod_episodio, episodio.numero).lowercase(),
                                opcao),
                            deSerie = true, chave = chave)
                    }
                }
            })
    }

    /// "faltam 60 min de 87" — o mesmo que o Mac mostra no lugar do guia.
    private fun atualizarTempo() {
        val atual = player ?: return
        val total = atual.duration
        if (total <= 0) {
            fichaTempo.visibility = View.GONE
            return
        }
        val faltam = ((total - atual.currentPosition) / 60_000L).coerceAtLeast(0)
        fichaTempo.visibility = View.VISIBLE
        fichaTempo.text = getString(R.string.vod_faltam, faltam, total / 60_000L)
    }

    private fun pararFilme(avisar: Boolean = true) {
        if (avisar && player != null) Telemetria.parou()
        guardarProgresso()
        relogio.removeCallbacks(tique)
        relogio.removeCallbacks(esconderBarra)
        seletor.visibility = View.GONE
        ficha.visibility = View.GONE
        fichaResolucao.visibility = View.GONE
        player?.release()
        player = null
        playerView.player = null
        playerView.visibility = View.GONE
        browse.visibility = View.VISIBLE
        titulo.text = getString(R.string.vod)
        lista.post { lista.getChildAt(0)?.requestFocus() }
    }

    /**
     * O player recebe as teclas antes da activity, então a decisão mora aqui.
     *
     * Com o filme no ar o PlayerView consome o direcional e mostra a barra
     * sozinho — foi por isso que o "para baixo" nunca chegava aos episódios.
     * Interceptando no despacho, a regra fica clara: barra escondida, as teclas
     * têm atalho próprio; barra à vista, o direcional é dela.
     */
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        // O ENTER de um teclado de verdade confirma a busca. Sem interceptar
        // aqui, ele chegava antes à tecla em foco e digitava a letra dela.
        if (event.action == KeyEvent.ACTION_DOWN && teclado.visibility == View.VISIBLE &&
            event.keyCode == KeyEvent.KEYCODE_ENTER) {
            confirmarBusca()
            return true
        }
        // MENU favorita a linha em foco — o mesmo botão que favorita canal na
        // tela inicial, para não haver dois gestos para a mesma ideia.
        if (event.action == KeyEvent.ACTION_DOWN && player == null &&
            teclado.visibility != View.VISIBLE &&
            (event.keyCode == KeyEvent.KEYCODE_MENU ||
             event.keyCode == KeyEvent.KEYCODE_BOOKMARK)) {
            favoritarEmFoco()
            return true
        }
        // INFO abre a ficha do título em foco: sinopse, duração, gêneros e
        // elenco, sem precisar abrir o filme para descobrir do que se trata.
        if (event.action == KeyEvent.ACTION_DOWN && player == null &&
            teclado.visibility != View.VISIBLE &&
            event.keyCode == KeyEvent.KEYCODE_INFO) {
            fichaEmFoco()
            return true
        }
        if (event.action != KeyEvent.ACTION_DOWN || player == null ||
            teclado.visibility == View.VISIBLE) {
            return super.dispatchKeyEvent(event)
        }
        if (event.keyCode == KeyEvent.KEYCODE_MENU ||
            event.keyCode == KeyEvent.KEYCODE_SETTINGS ||
            event.keyCode == KeyEvent.KEYCODE_CAPTIONS ||
            event.keyCode == KeyEvent.KEYCODE_INFO) {
            player?.let { TrackMenu.show(this, it) }
            return true
        }
        if (seletor.visibility == View.VISIBLE) {
            when (event.keyCode) {
                KeyEvent.KEYCODE_DPAD_LEFT -> { trocarTemporada(-1); return true }
                KeyEvent.KEYCODE_DPAD_RIGHT -> { trocarTemporada(1); return true }
                KeyEvent.KEYCODE_BACK, KeyEvent.KEYCODE_ESCAPE -> {
                    fecharSeletor(); return true
                }
            }
            return super.dispatchKeyEvent(event)
        }
        if (playerView.isControllerFullyVisible) {
            // A barra está à vista: o direcional é dela, inclusive o para baixo,
            // que passa para a linha de baixo dos controles.
            adiarEsconder()
            return super.dispatchKeyEvent(event)
        }
        when (event.keyCode) {
            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER,
            KeyEvent.KEYCODE_DPAD_UP -> { mostrarControles(); return true }
            KeyEvent.KEYCODE_DPAD_DOWN -> {
                // Numa série, para baixo abre temporada e episódio, que é o que
                // mais se troca. Num filme, mostra a barra.
                if (!abrirSeletor()) mostrarControles()
                return true
            }
            KeyEvent.KEYCODE_DPAD_RIGHT -> { pular(10_000); return true }
            KeyEvent.KEYCODE_DPAD_LEFT -> { pular(-10_000); return true }
            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE -> {
                player?.let { it.playWhenReady = !it.playWhenReady }
                mostrarControles()
                return true
            }
        }
        return super.dispatchKeyEvent(event)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Com filme no ar, o direcional pertence ao player. Sem isto o OK
        // chegava à linha que ficou atrás e mandava tocar o mesmo filme de
        // novo — do começo.
        // Quem tiver teclado — de USB, de celular ou o do próprio aparelho —
        // digita direto, sem passar tecla por tecla no direcional.
        if (teclado.visibility == View.VISIBLE) {
            when {
                keyCode in KeyEvent.KEYCODE_A..KeyEvent.KEYCODE_Z -> {
                    digitar(('A' + (keyCode - KeyEvent.KEYCODE_A)).toString())
                    return true
                }
                keyCode in KeyEvent.KEYCODE_0..KeyEvent.KEYCODE_9 -> {
                    digitar((keyCode - KeyEvent.KEYCODE_0).toString())
                    return true
                }
                keyCode == KeyEvent.KEYCODE_SPACE -> { digitar(" "); return true }
                keyCode == KeyEvent.KEYCODE_DEL -> { apagar(); return true }
            }
        }
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE) {
            if (teclado.visibility == View.VISIBLE) {
                fecharBusca()
                return true
            }
            return voltar() || super.onKeyDown(keyCode, event)
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onStop() {
        super.onStop()
        guardarProgresso()
        player?.playWhenReady = false
    }

    override fun onDestroy() {
        player?.release()
        player = null
        super.onDestroy()
    }

    private inner class Adapter : RecyclerView.Adapter<Adapter.Holder>() {
        private var itens: List<Linha> = emptyList()

        fun linha(posicao: Int): Linha? = itens.getOrNull(posicao)

        fun trocar(novos: List<Linha>) {
            itens = novos
            notifyDataSetChanged()
        }

        inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
            val nome: TextView = view.findViewById(R.id.vodNome)
            val detalhe: TextView = view.findViewById(R.id.vodDetalhe)
            val inicial: TextView = view.findViewById(R.id.vodInicial)
            val capa: ImageView = view.findViewById(R.id.vodCapa)
            val progresso: ProgressBar = view.findViewById(R.id.vodProgresso)
            val estrela: TextView = view.findViewById(R.id.vodEstrela)
            /// Para descartar a capa que chegar depois de a linha ser reusada.
            var pedido: String? = null
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val holder = Holder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_vod, parent, false))
            holder.itemView.setOnFocusChangeListener { view, focado ->
                val escala = if (focado) 1.03f else 1f
                view.animate().scaleX(escala).scaleY(escala).setDuration(120).start()
            }
            return holder
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val linha = itens[position]
            holder.nome.text = linha.texto
            holder.detalhe.text = linha.detalhe.orEmpty()
            holder.detalhe.visibility =
                if (linha.detalhe.isNullOrEmpty()) View.GONE else View.VISIBLE
            holder.inicial.text = linha.inicial
            holder.inicial.visibility = if (linha.inicial.isEmpty()) View.GONE else View.VISIBLE
            holder.progresso.visibility =
                if (linha.progresso == null) View.GONE else View.VISIBLE
            linha.progresso?.let { holder.progresso.progress = (it * 1000).toInt() }
            holder.estrela.visibility = if (marcado(linha)) View.VISIBLE else View.GONE
            holder.itemView.setOnClickListener { linha.aoEscolher() }
            // Segurar OK é o mesmo gesto que favorita um canal na tela inicial.
            holder.itemView.setOnLongClickListener {
                if (linha.favorito == null) false else { favoritar(linha); true }
            }

            holder.capa.dispose()
            holder.capa.setImageDrawable(null)
            holder.capa.visibility = View.GONE
            holder.pedido = linha.texto
            val serie = linha.capaDe ?: return
            // A busca sai só para o que está na tela, e o resultado é descartado
            // se a linha já tiver sido reusada por outro título enquanto isso.
            lifecycleScope.launch(semDerrubar) {
                val capa = Generos.capa(linha.texto, serie)
                if (capa == null) {
                    // Sem ficha no TMDB: a marca de "sem imagem" no lugar,
                    // que é honesto e não custa busca nenhuma.
                    if (holder.pedido == linha.texto) {
                        holder.capa.visibility = View.VISIBLE
                        holder.capa.setImageResource(R.drawable.sem_capa)
                    }
                    return@launch
                }
                if (holder.pedido != linha.texto) return@launch
                holder.capa.visibility = View.VISIBLE
                holder.capa.load(capa) {
                    // Nada de capa em disco: cada abertura busca de novo.
                    diskCachePolicy(coil.request.CachePolicy.DISABLED)
                    crossfade(true)
                }
            }
        }

        override fun getItemCount() = itens.size
    }
}
