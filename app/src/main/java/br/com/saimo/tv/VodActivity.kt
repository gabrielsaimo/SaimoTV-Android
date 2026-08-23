package br.com.saimo.tv

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private sealed interface Passo {
        object Inicio : Passo
        data class Letras(val filmes: Boolean, val reservado: Boolean = false) : Passo
        data class Titulos(val filmes: Boolean, val letra: String, val reservado: Boolean = false) : Passo
        data class Temporadas(val letra: String, val serie: Serie) : Passo
        data class Episodios(val letra: String, val serie: Serie, val temporada: Int) : Passo
        data class Versoes(val titulo: String, val fontes: Map<String, List<String>>) : Passo
        data class Resultados(val termo: String) : Passo
    }

    private data class Linha(
        val texto: String,
        val detalhe: String? = null,
        val inicial: String = "",
        /// Nulo quando a linha não é um título — letra e temporada não têm capa.
        val capaDe: Boolean? = null,
        val aoEscolher: () -> Unit,
    )

    private lateinit var lista: RecyclerView
    private lateinit var titulo: TextView
    private lateinit var trilha: TextView
    private lateinit var contagem: TextView
    private lateinit var estado: TextView
    private lateinit var browse: View
    private lateinit var playerView: PlayerView
    private lateinit var teclado: View
    private lateinit var termo: TextView
    private lateinit var teclas: android.widget.GridLayout
    private lateinit var ficha: View
    private lateinit var fichaNome: TextView
    private lateinit var fichaDetalhe: TextView
    private lateinit var fichaTempo: TextView
    private var player: ExoPlayer? = null

    private val relogio = Handler(Looper.getMainLooper())
    /// A ficha mostra quanto falta, e isso muda enquanto o filme corre.
    private val tique = object : Runnable {
        override fun run() {
            atualizarTempo()
            relogio.postDelayed(this, 5_000)
        }
    }

    private val pilha = ArrayDeque<Passo>()
    private val adapter = Adapter()
    private var gavetas: List<Vod.Gaveta> = emptyList()
    private var episodiosDaSerie: List<Episodio> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_vod)

        lista = findViewById(R.id.vodLista)
        titulo = findViewById(R.id.vodTitulo)
        trilha = findViewById(R.id.vodTrilha)
        contagem = findViewById(R.id.vodContagem)
        estado = findViewById(R.id.vodEstado)
        browse = findViewById(R.id.browse)
        playerView = findViewById(R.id.vodPlayer)
        teclado = findViewById(R.id.vodTeclado)
        termo = findViewById(R.id.vodTermo)
        teclas = findViewById(R.id.vodTeclas)
        montarTeclado()
        ficha = findViewById(R.id.vodFicha)
        fichaNome = findViewById(R.id.vodFichaNome)
        fichaDetalhe = findViewById(R.id.vodFichaDetalhe)
        fichaTempo = findViewById(R.id.vodFichaTempo)
        // A ficha acompanha a barra de controle: aparece com ela e some junto.
        playerView.setControllerVisibilityListener(
            PlayerView.ControllerVisibilityListener { visivel ->
                ficha.visibility = if (player != null && visivel == View.VISIBLE) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            })

        lista.layoutManager = GridLayoutManager(this, 1)
        lista.adapter = adapter
        lista.setHasFixedSize(true)

        titulo.text = getString(R.string.vod)
        ir(Passo.Inicio)
    }

    // MARK: - Navegação

    private fun ir(passo: Passo) {
        pilha.addLast(passo)
        mostrar(passo)
    }

    private fun voltar(): Boolean {
        if (player != null) {
            pararFilme()
            return true
        }
        if (pilha.size <= 1) return false
        pilha.removeLast()
        mostrar(pilha.last())
        return true
    }

    private fun mostrar(passo: Passo) = lifecycleScope.launch {
        estado.text = getString(R.string.vod_carregando)
        estado.visibility = View.VISIBLE
        val linhas: List<Linha> = when (passo) {
            is Passo.Inicio -> inicio()
            is Passo.Letras -> letras(passo)
            is Passo.Titulos -> titulos(passo.filmes, passo.letra, passo.reservado)
            is Passo.Temporadas -> temporadas(passo.letra, passo.serie)
            is Passo.Episodios -> episodios(passo.temporada)
            is Passo.Versoes -> versoes(passo.titulo, passo.fontes)
            is Passo.Resultados -> resultados(passo.termo)
        }
        estado.visibility = if (linhas.isEmpty()) View.VISIBLE else View.GONE
        if (linhas.isEmpty()) estado.text = getString(R.string.vod_vazio)

        // Letras cabem lado a lado; título e episódio precisam da linha inteira.
        // Letra é curta e cabe muita numa linha; título precisa de largura, mas
        // duas colunas ainda dobram o que se vê sem apertar o texto.
        (lista.layoutManager as GridLayoutManager).spanCount = when (passo) {
            is Passo.Letras -> 6
            is Passo.Titulos, is Passo.Episodios, is Passo.Resultados -> 2
            else -> 1
        }
        trilha.text = trilhaDe(passo)
        // Contar duas seções ou vinte e sete letras não diz nada a ninguém; o
        // número só ajuda quando são títulos.
        contagem.text = when {
            linhas.isEmpty() || passo is Passo.Inicio || passo is Passo.Letras -> ""
            else -> getString(R.string.vod_contagem, linhas.size)
        }
        adapter.trocar(linhas)
        lista.post { lista.getChildAt(0)?.requestFocus() ?: lista.requestFocus() }
    }

    private fun trilhaDe(passo: Passo): String = when (passo) {
        is Passo.Inicio -> ""
        is Passo.Letras -> secao(passo.filmes, passo.reservado)
        is Passo.Titulos -> "${secao(passo.filmes, passo.reservado)} › ${passo.letra}"
        is Passo.Temporadas -> "${getString(R.string.vod_series)} › ${passo.serie.titulo}"
        is Passo.Episodios -> "${passo.serie.titulo} › " +
            getString(R.string.vod_temporada, passo.temporada)
        is Passo.Versoes -> passo.titulo
        is Passo.Resultados -> getString(R.string.vod_resultados, passo.termo)
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
            setTextColor(getColor(R.color.text_primary))
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
        Vod.buscar(this, termo).map { achado ->
            Linha(achado.titulo,
                getString(if (achado.serie) R.string.vod_series else R.string.vod_filmes_um),
                inicial(achado.titulo), capaDe = achado.serie) {
                lifecycleScope.launch { abrirAchado(achado) }
            }
        }

    private suspend fun abrirAchado(achado: Vod.Achado) {
        if (achado.serie) {
            Vod.serie(this, achado)?.let { ir(Passo.Temporadas(achado.letra, it)) }
        } else {
            val filme = Vod.filme(this, achado) ?: return
            val unica = filme.fontes.entries.firstOrNull()
            if (filme.fontes.size == 1 && unica != null) {
                tocar(filme.titulo, unica.value,
                      detalhe = getString(R.string.vod_filmes_um) + " · " + rotulo(unica.key))
            } else {
                ir(Passo.Versoes(filme.titulo, filme.fontes))
            }
        }
    }

    // MARK: - Degraus

    private suspend fun inicio(): List<Linha> {
        if (gavetas.isEmpty()) gavetas = Vod.indice(this)
        val filmes = gavetas.sumOf { it.filmes }
        val series = gavetas.sumOf { it.series }
        return listOf(
            Linha(getString(R.string.vod_filmes), getString(R.string.vod_contagem, filmes), "F") {
                ir(Passo.Letras(filmes = true))
            },
            Linha(getString(R.string.vod_series), getString(R.string.vod_contagem, series), "S") {
                ir(Passo.Letras(filmes = false))
            },
            Linha(getString(R.string.vod_buscar), getString(R.string.vod_buscar_dica), "?") {
                abrirBusca()
            }) + reservados()
    }

    /// Só existe depois do código, e como uma linha igual às outras: nada aqui
    /// diz que ela é diferente enquanto não estiver à vista.
    private fun reservados(): List<Linha> {
        if (!Unlock.unlocked) return emptyList()
        val total = gavetas.sumOf { it.reservados }
        if (total == 0) return emptyList()
        return listOf(Linha(getString(R.string.vod_extras),
            getString(R.string.vod_contagem, total), "+") {
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
                Linha(filme.titulo, detalheFilme(filme), inicial(filme.titulo), capaDe = false) {
                    val unica = filme.fontes.entries.firstOrNull()
                    if (filme.fontes.size == 1 && unica != null) {
                        tocar(filme.titulo, unica.value,
                              detalhe = getString(R.string.vod_filmes_um) + " · " + rotulo(unica.key))
                    } else {
                        ir(Passo.Versoes(filme.titulo, filme.fontes))
                    }
                }
            }
        } else {
            Vod.series(this, letra).map { serie ->
                val detalhe = listOfNotNull(
                    serie.ano.takeIf { it.isNotBlank() },
                    getString(R.string.vod_eps, serie.episodios)).joinToString(" · ")
                Linha(serie.titulo, detalhe, inicial(serie.titulo), capaDe = true) {
                    ir(Passo.Temporadas(letra, serie))
                }
            }
        }

    private suspend fun temporadas(letra: String, serie: Serie): List<Linha> {
        episodiosDaSerie = Vod.episodios(this, letra, serie)
        return episodiosDaSerie.map { it.temporada }.distinct().sorted().map { numero ->
            val quantos = episodiosDaSerie.count { it.temporada == numero }
            Linha(getString(R.string.vod_temporada, numero),
                getString(R.string.vod_eps, quantos), numero.toString()) {
                ir(Passo.Episodios(letra, serie, numero))
            }
        }
    }

    private fun episodios(temporada: Int): List<Linha> = episodiosDaSerie
        .filter { it.temporada == temporada }
        .sortedWith(compareBy({ it.numero }, { it.versao }))
        .map { episodio ->
            Linha(getString(R.string.vod_episodio, episodio.numero),
                detalhe(episodio.versao, episodio.urls.size), episodio.numero.toString()) {
                val serie = (pilha.last() as? Passo.Episodios)?.serie?.titulo.orEmpty()
                tocar(serie.ifEmpty { getString(R.string.vod_episodio, episodio.numero) },
                      episodio.urls,
                      detalhe = getString(R.string.vod_temporada, episodio.temporada) + ", " +
                          getString(R.string.vod_episodio, episodio.numero).lowercase() +
                          " · " + rotulo(episodio.versao))
            }
        }

    private fun versoes(titulo: String, fontes: Map<String, List<String>>) =
        fontes.map { (versao, urls) ->
            Linha(rotulo(versao), detalhe(versao, urls.size).takeIf { urls.size > 1 }, "") {
                tocar(titulo, urls,
                      detalhe = getString(R.string.vod_filmes_um) + " · " + rotulo(versao))
            }
        }

    /// Duas fontes não viram duas linhas: viram uma linha e uma reserva. Dizer
    /// quantas há evita a impressão de que o título ficou por um fio.
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

    /// Fontes em ordem: a primeira que entregar imagem fica.
    private var fontesAtuais: List<String> = emptyList()
    private var fonteAtual = 0

    private fun tocar(nome: String, urls: List<String>, indice: Int = 0,
                      detalhe: String = fichaDetalheAtual) {
        if (urls.isEmpty()) return
        fichaDetalheAtual = detalhe
        fontesAtuais = urls
        fonteAtual = indice.coerceIn(urls.indices)
        val url = urls[fonteAtual]
        pararFilme()
        // Filme não é canal: pausa, volta e avança, então o controle padrão do
        // player fica à vista em vez da faixa de canal ao vivo.
        val novo = ExoPlayer.Builder(this).build()
        // Pelo mesmo caminho do canal ao vivo: o provedor responde 302 para uma
        // URL com token e recusa cliente sem User-Agent, e é este cliente que
        // segue redirecionamento e ainda resolve por DNS-over-HTTPS.
        novo.setMediaSource(Playback.mediaSource(this, Source(url)))
        // Fonte morta não pode virar tela preta: cai para a seguinte, como o
        // canal já faz.
        novo.addListener(object : androidx.media3.common.Player.Listener {
            override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                if (fonteAtual + 1 < fontesAtuais.size) {
                    tocar(nome, fontesAtuais, fonteAtual + 1)
                }
            }
        })
        novo.playWhenReady = true
        novo.prepare()
        player = novo
        playerView.player = novo
        playerView.visibility = View.VISIBLE
        browse.visibility = View.GONE
        estado.visibility = View.GONE
        playerView.requestFocus()
        titulo.text = nome
        fichaNome.text = nome
        fichaDetalhe.text = detalhe
        fichaDetalhe.visibility = if (detalhe.isEmpty()) View.GONE else View.VISIBLE
        atualizarTempo()
        ficha.visibility = View.VISIBLE
        relogio.removeCallbacks(tique)
        relogio.postDelayed(tique, 5_000)
    }

    private var fichaDetalheAtual = ""

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

    private fun pararFilme() {
        relogio.removeCallbacks(tique)
        ficha.visibility = View.GONE
        player?.release()
        player = null
        playerView.player = null
        playerView.visibility = View.GONE
        browse.visibility = View.VISIBLE
        titulo.text = getString(R.string.vod)
        lista.post { lista.getChildAt(0)?.requestFocus() }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
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
                keyCode == KeyEvent.KEYCODE_ENTER -> { confirmarBusca(); return true }
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
        player?.playWhenReady = false
    }

    override fun onDestroy() {
        player?.release()
        player = null
        super.onDestroy()
    }

    private inner class Adapter : RecyclerView.Adapter<Adapter.Holder>() {
        private var itens: List<Linha> = emptyList()

        fun trocar(novos: List<Linha>) {
            itens = novos
            notifyDataSetChanged()
        }

        inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
            val nome: TextView = view.findViewById(R.id.vodNome)
            val detalhe: TextView = view.findViewById(R.id.vodDetalhe)
            val inicial: TextView = view.findViewById(R.id.vodInicial)
            val capa: ImageView = view.findViewById(R.id.vodCapa)
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
            holder.itemView.setOnClickListener { linha.aoEscolher() }

            holder.capa.setImageDrawable(null)
            holder.capa.visibility = View.GONE
            holder.pedido = linha.texto
            val serie = linha.capaDe ?: return
            // A busca sai só para o que está na tela, e o resultado é descartado
            // se a linha já tiver sido reusada por outro título enquanto isso.
            lifecycleScope.launch {
                val capa = Capas.capa(linha.texto, serie) ?: return@launch
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
