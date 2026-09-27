package br.com.saimo.tv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * A tela inicial.
 *
 * Antes eram dois cartões — TV ou filmes — e só depois de escolher se via
 * alguma coisa. Agora é o que toda TV faz: um menu no topo, o título focado em
 * destaque com a imagem dele ao fundo, e fileiras — o que a pessoa estava
 * vendo, os canais ao vivo agora, os favoritos e as novidades. Tudo a um OK.
 *
 * Continua sendo a tela que o sistema abre (o nome da classe é o que o
 * launcher guarda), e é ela que atende os links `saimo://` da tela inicial da
 * TV e da busca por voz.
 */
@UnstableApi
class EscolhaActivity : TelaComMenu() {

    override val aba = Aba.INICIO
    override fun aoFocarMenu() = cabecalhoPadrao()
    override val aoMenuInicio: (() -> Unit) = { focarPrimeiro() }


    private lateinit var status: TextView
    private lateinit var filas: RecyclerView
    private lateinit var fundo: ImageView
    private lateinit var titulo: TextView
    private lateinit var meta: TextView
    private lateinit var sinopse: TextView
    private val atualizacao by lazy { OfertaDeAtualizacao(this) { mostrarStatus(it) } }
    private var jaIniciou = false
    private val handler = Handler(Looper.getMainLooper())
    private val hora = SimpleDateFormat("HH:mm", Locale("pt", "BR"))
    private var ultimoVoltar = 0L

    private val adaptador by lazy {
        Inicio.Adapter(escopo = lifecycleScope, capaDe = { t, s -> Generos.capa(t, s) },
            aoFocar = { mostrarDestaque(it) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_escolha)
        status = findViewById(R.id.escolhaStatus)
        filas = findViewById(R.id.inicioFilas)
        fundo = findViewById(R.id.inicioFundo)
        titulo = findViewById(R.id.inicioTitulo)
        meta = findViewById(R.id.inicioMeta)
        sinopse = findViewById(R.id.inicioSinopse)

        filas.layoutManager = LinearLayoutManager(this)
        filas.adapter = adaptador
        filas.setItemViewCacheSize(4)
        cabecalhoPadrao()

        lifecycleScope.launch(semDerrubar) {
            // Ler 990 canais do disco leva segundos num TV Box fraco: fora da
            // linha da tela, para ela aparecer já.
            kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                if (Remote.channels === CATALOG) Remote.loadCached(this@EscolhaActivity)
            }
            montarFilas()
            Epg.carregarCache(this@EscolhaActivity)
            montarFilas()
        }

        // A versão nova é oferecida aqui, antes de qualquer vídeo começar.
        filas.postDelayed({ if (!isFinishing) atualizacao.ofertar() }, 2_500)
        lifecycleScope.launch(semDerrubar) { CanalNaTv.publicar(applicationContext) }

        val veioDeLink = intent?.action == Intent.ACTION_VIEW || intent?.action == Intent.ACTION_SEARCH
        atenderLink(intent)
        // "Ir direto ao último canal", escolhido em Ajustes: esta tela fica por
        // baixo, e VOLTAR do canal cai nela.
        if (savedInstanceState == null && !veioDeLink && Preferencias.abrirEm == "canal" &&
            Preferencias.ultimoCanal != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun cabecalhoPadrao() {
        painelDestaque.padrao(saudacao(), getString(R.string.escolha_titulo))
        sinopse.text = getString(R.string.inicio_dica)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        atenderLink(intent)
    }

    override fun onStart() {
        super.onStart()
        if (jaIniciou) {
            atualizacao.retomarSePendente()
            // Voltando do player ou da ficha: o "continuar" mudou.
            lifecycleScope.launch(semDerrubar) { montarFilas() }
        }
        jaIniciou = true
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    private fun abrirAoVivo() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun focarPrimeiro(tentativas: Int = 10) {
        filas.scrollToPosition(0)
        filas.post {
            val fileira = filas.findViewHolderForAdapterPosition(0)?.itemView
            val capas = fileira?.findViewById<RecyclerView>(R.id.filaCapas)
            if (capas?.getChildAt(0)?.requestFocus() == true) return@post
            // A fileira ainda não desenhou as capas: tenta de novo no próximo
            // quadro, em vez de largar o foco no menu.
            if (tentativas > 0) filas.postDelayed({ focarPrimeiro(tentativas - 1) }, 60)
            else MenuGlobal.focar(barraMenu, Aba.INICIO)
        }
    }

    // MARK: - Fileiras

    private var primeiraVez = true
    private val abertaEm = System.currentTimeMillis()

    private suspend fun montarFilas() {
        Generos.carregar(this)
        val lista = mutableListOf<Inicio.Fila>()
        continuar()?.let { lista += it }
        aoVivo()?.let { lista += it }
        favoritos()?.let { lista += it }
        for (fila in Destaques.filas(this)) {
            val cartoes = fila.itens.map { cartaoDe(it) }
            if (cartoes.isNotEmpty()) lista += Inicio.Fila(fila.titulo, cartoes)
        }
        if (isFinishing || isDestroyed) return
        // Refazer as fileiras recria as capas e o foco escaparia para o menu.
        // Nos primeiros segundos (a pessoa ainda não mexeu), ele volta à
        // primeira capa; depois disso, fica onde a pessoa deixou.
        val devolver = primeiraVez || (filas.hasFocus() && System.currentTimeMillis() - abertaEm < 8_000)
        adaptador.trocar(lista)
        if (devolver) {
            primeiraVez = false
            filas.post { focarPrimeiro() }
        }
    }

    private fun continuar(): Inicio.Fila? {
        val itens = Progresso.emAndamento(this).take(20)
        if (itens.isEmpty()) return null
        return Inicio.Fila(getString(R.string.vod_continuar), itens.map { a ->
            val alvo = a.endereco?.alvo
            Inicio.Cartao(
                titulo = a.rotulo,
                capa = "",
                inicial = a.titulo.take(1).uppercase(),
                progresso = a.fracao,
                procurarCapa = a.serie,
                nomeDaCapa = a.titulo,
                alvo = alvo,
                aoMenu = {
                    Painel.mostrar(this, a.rotulo, listOfNotNull(
                        alvo?.let { Painel.Item(getString(R.string.inicio_abrir_ficha), icone = R.drawable.ic_info) { FichaActivity.abrir(this, it) } },
                        Painel.Item(getString(R.string.inicio_remover_continuar)) {
                            Progresso.esquecer(this, a)
                            lifecycleScope.launch(semDerrubar) { montarFilas() }
                        }))
                },
            ) {
                if (alvo != null) {
                    val (t, e) = if (a.terminado) a.temporada to a.episodio + 1 else a.temporada to a.episodio
                    PlayerActivity.abrir(this, alvo, t, e)
                } else {
                    BuscaActivity.abrir(this, Generos.semAno(a.titulo))
                }
            }
        })
    }

    /** Os canais favoritos — ou os abertos — com o que passa agora. */
    private fun aoVivo(): Inicio.Fila? {
        val todos = Remote.channels
        if (todos.isEmpty()) return null
        val ultimo = todos.firstOrNull { it.name == Preferencias.ultimoCanal }
        val favoritos = todos.filter { Favorites.contains(it.name) }
        val escolhidos = (listOfNotNull(ultimo) + favoritos.ifEmpty {
            todos.filter { Categorias.de(it) == "TV Aberta" }
        }).distinct().take(16)
        val agora = System.currentTimeMillis()
        return Inicio.Fila(getString(R.string.inicio_ao_vivo_agora), escolhidos.map { canal ->
            val programa = Epg.nowNext(canal.name, agora)?.first
            Inicio.Cartao(
                titulo = canal.name,
                capa = canal.logo.orEmpty(),
                inicial = canal.name.take(1).uppercase(),
                progresso = programa?.progress(agora),
                subtitulo = programa?.title,
            ) {
                startActivity(Intent(this, MainActivity::class.java).putExtra(MainActivity.EXTRA_CANAL, canal.name))
            }
        }, Inicio.Tipo.LARGO)
    }

    private fun favoritos(): Inicio.Fila? {
        val itens = VodFavoritos.lista(this)
        if (itens.isEmpty()) return null
        return Inicio.Fila(getString(R.string.vod_favoritos), itens.map { item ->
            val alvo = Alvo(item.titulo, item.serie, item.letra, item.ano)
            Inicio.Cartao(
                titulo = item.nomeCompleto, capa = "", inicial = item.titulo.take(1).uppercase(),
                procurarCapa = item.serie, alvo = alvo,
                aoMenu = { alternarFavorito(alvo) },
            ) { FichaActivity.abrir(this, alvo) }
        })
    }

    private fun cartaoDe(item: Destaques.Item): Inicio.Cartao {
        val alvo = Alvo(item.titulo, item.serie, item.letra, item.ano,
            colecao = if (item.daColecao) item.colecao else "")
        return Inicio.Cartao(
            titulo = item.titulo, capa = item.capa, inicial = item.titulo.take(1).uppercase(),
            progresso = if (item.serie) null else Progresso.fracao(this, Progresso.chaveFilme(item.titulo)),
            alvo = alvo,
            aoMenu = if (item.daColecao) null else ({ alternarFavorito(alvo) }),
        ) { FichaActivity.abrir(this, alvo) }
    }

    private fun alternarFavorito(alvo: Alvo) {
        val marcado = VodFavoritos.alternar(this, VodFavoritos.Item(alvo.titulo, alvo.serie, alvo.letra, alvo.ano))
        Toast.makeText(this, if (marcado) R.string.vod_ficha_favorito else R.string.inicio_desfavoritar,
            Toast.LENGTH_SHORT).show()
        lifecycleScope.launch(semDerrubar) { montarFilas() }
    }

    // MARK: - Destaque

    private val painelDestaque by lazy { Destaque(this, lifecycleScope, fundo, titulo, meta, sinopse) }

    private fun mostrarDestaque(cartao: Inicio.Cartao) = painelDestaque.mostrar(cartao)

    // MARK: - Teclas

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_GUIDE, KeyEvent.KEYCODE_CHANNEL_UP,
            KeyEvent.KEYCODE_CHANNEL_DOWN, KeyEvent.KEYCODE_TV -> { abrirAoVivo(); return true }
            KeyEvent.KEYCODE_SEARCH -> { BuscaActivity.abrir(this); return true }
            KeyEvent.KEYCODE_BACK, KeyEvent.KEYCODE_ESCAPE -> {
                // Primeiro VOLTAR sobe para o menu; o segundo, de lá, pede
                // confirmação — sair do app sem querer é o erro mais comum.
                if (!MenuGlobal.temFoco(barraMenu)) {
                    filas.scrollToPosition(0)
                    MenuGlobal.focar(barraMenu, Aba.INICIO)
                    return true
                }
                val agora = System.currentTimeMillis()
                if (agora - ultimoVoltar > 2_500) {
                    ultimoVoltar = agora
                    Toast.makeText(this, R.string.inicio_sair, Toast.LENGTH_SHORT).show()
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * Link vindo de fora do app: o "Continuar assistindo" e a fileira do
     * Saimo na tela inicial, e a busca por voz do sistema.
     */
    private fun atenderLink(intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_VIEW -> {
                if (intent.data?.host == "inicio") return
                Links.abrir(this, intent.data)
                intent.data = null
            }
            Intent.ACTION_SEARCH -> {
                val termo = intent.getStringExtra(android.app.SearchManager.QUERY)?.trim().orEmpty()
                if (termo.isNotEmpty()) BuscaActivity.abrir(this, termo)
                intent.action = null
            }
        }
    }

    /** "Boa noite" às dez da noite: a abertura fala como gente. */
    private fun saudacao(): String {
        val h = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        return getString(when (h) {
            in 5..11 -> R.string.escolha_bom_dia
            in 12..17 -> R.string.escolha_boa_tarde
            else -> R.string.escolha_boa_noite
        })
    }

    private fun mostrarStatus(texto: String) {
        status.text = texto
        status.visibility = View.VISIBLE
    }

    companion object {
        /** Volta à tela inicial, fechando o que estiver por cima dela. */
        fun voltar(context: Context) = context.startActivity(Intent(context, EscolhaActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP))
    }
}
