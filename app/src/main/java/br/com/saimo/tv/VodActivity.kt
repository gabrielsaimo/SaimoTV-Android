package br.com.saimo.tv

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        data class Letras(val filmes: Boolean) : Passo
        data class Titulos(val filmes: Boolean, val letra: String) : Passo
        data class Temporadas(val letra: String, val serie: Serie) : Passo
        data class Episodios(val letra: String, val serie: Serie, val temporada: Int) : Passo
        data class Versoes(val titulo: String, val fontes: Map<String, String>) : Passo
    }

    private data class Linha(
        val texto: String,
        val detalhe: String? = null,
        val inicial: String = "",
        val aoEscolher: () -> Unit,
    )

    private lateinit var lista: RecyclerView
    private lateinit var titulo: TextView
    private lateinit var trilha: TextView
    private lateinit var contagem: TextView
    private lateinit var estado: TextView
    private lateinit var browse: View
    private lateinit var playerView: PlayerView
    private var player: ExoPlayer? = null

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
            is Passo.Letras -> letras(passo.filmes)
            is Passo.Titulos -> titulos(passo.filmes, passo.letra)
            is Passo.Temporadas -> temporadas(passo.letra, passo.serie)
            is Passo.Episodios -> episodios(passo.temporada)
            is Passo.Versoes -> versoes(passo.fontes)
        }
        estado.visibility = if (linhas.isEmpty()) View.VISIBLE else View.GONE
        if (linhas.isEmpty()) estado.text = getString(R.string.vod_vazio)

        // Letras cabem lado a lado; título e episódio precisam da linha inteira.
        // Letra é curta e cabe muita numa linha; título precisa de largura, mas
        // duas colunas ainda dobram o que se vê sem apertar o texto.
        (lista.layoutManager as GridLayoutManager).spanCount = when (passo) {
            is Passo.Letras -> 6
            is Passo.Titulos, is Passo.Episodios -> 2
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
        is Passo.Letras -> secao(passo.filmes)
        is Passo.Titulos -> "${secao(passo.filmes)} › ${passo.letra}"
        is Passo.Temporadas -> "${getString(R.string.vod_series)} › ${passo.serie.titulo}"
        is Passo.Episodios -> "${passo.serie.titulo} › " +
            getString(R.string.vod_temporada, passo.temporada)
        is Passo.Versoes -> passo.titulo
    }

    private fun secao(filmes: Boolean) =
        getString(if (filmes) R.string.vod_filmes else R.string.vod_series)

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
            })
    }

    private fun letras(filmes: Boolean) = gavetas
        .filter { if (filmes) it.filmes > 0 else it.series > 0 }
        .map { gaveta ->
            val quantos = if (filmes) gaveta.filmes else gaveta.series
            Linha(gaveta.letra, quantos.toString(), "") {
                ir(Passo.Titulos(filmes, gaveta.letra))
            }
        }

    private suspend fun titulos(filmes: Boolean, letra: String): List<Linha> =
        if (filmes) {
            Vod.filmes(this, letra).map { filme ->
                Linha(filme.titulo, versoesDe(filme.fontes.keys), inicial(filme.titulo)) {
                    if (filme.fontes.size == 1) tocar(filme.titulo, filme.fontes.values.first())
                    else ir(Passo.Versoes(filme.titulo, filme.fontes))
                }
            }
        } else {
            Vod.series(this, letra).map { serie ->
                val detalhe = listOfNotNull(
                    serie.ano.takeIf { it.isNotBlank() },
                    getString(R.string.vod_eps, serie.episodios)).joinToString(" · ")
                Linha(serie.titulo, detalhe, inicial(serie.titulo)) {
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
                rotulo(episodio.versao), episodio.numero.toString()) {
                tocar(getString(R.string.vod_episodio, episodio.numero), episodio.url)
            }
        }

    private fun versoes(fontes: Map<String, String>) = fontes.map { (versao, url) ->
        Linha(rotulo(versao), null, "") { tocar(rotulo(versao), url) }
    }

    private fun rotulo(versao: String) =
        getString(if (versao == "leg") R.string.vod_legendado else R.string.vod_dublado)

    private fun versoesDe(chaves: Set<String>) =
        chaves.sorted().joinToString(" · ") { rotulo(it) }

    private fun inicial(texto: String) = texto.trim().take(1).uppercase()

    // MARK: - Reprodução

    private fun tocar(nome: String, url: String) {
        pararFilme()
        // Filme não é canal: pausa, volta e avança, então o controle padrão do
        // player fica à vista em vez da faixa de canal ao vivo.
        val novo = ExoPlayer.Builder(this).build()
        // Pelo mesmo caminho do canal ao vivo: o provedor responde 302 para uma
        // URL com token e recusa cliente sem User-Agent, e é este cliente que
        // segue redirecionamento e ainda resolve por DNS-over-HTTPS.
        novo.setMediaSource(Playback.mediaSource(this, Source(url)))
        novo.playWhenReady = true
        novo.prepare()
        player = novo
        playerView.player = novo
        playerView.visibility = View.VISIBLE
        browse.visibility = View.GONE
        estado.visibility = View.GONE
        playerView.requestFocus()
        titulo.text = nome
    }

    private fun pararFilme() {
        player?.release()
        player = null
        playerView.player = null
        playerView.visibility = View.GONE
        browse.visibility = View.VISIBLE
        titulo.text = getString(R.string.vod)
        lista.post { lista.getChildAt(0)?.requestFocus() }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE) {
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
        }

        override fun getItemCount() = itens.size
    }
}
