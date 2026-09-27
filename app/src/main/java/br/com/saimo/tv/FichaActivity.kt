package br.com.saimo.tv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.dispose
import coil.load
import coil.transform.CircleCropTransformation
import kotlinx.coroutines.launch

/**
 * O que abrir: um título do acervo, com o bastante para chegar até ele.
 *
 * A letra é onde o título mora no acervo comum; a coleção, quando não é
 * vazia, diz que ele mora na de animes ou na de doramas. O id do TMDB só vem
 * preenchido das coleções, que o publicam no próprio arquivo.
 */
data class Alvo(
    val titulo: String,
    val serie: Boolean,
    val letra: String,
    val ano: String = "",
    val colecao: String = "",
    val tmdbId: Int = 0,
) {
    /// O filme já traz o ano no nome ("O Fim da Rua (2026)"); a série o
    /// guarda à parte. Só se acrescenta quando o nome ainda não tem.
    val nomeCompleto: String get() =
        if (ano.isBlank() || titulo.trimEnd().endsWith("($ano)")) titulo else "$titulo ($ano)"

    fun em(intent: Intent): Intent = intent
        .putExtra(TITULO, titulo).putExtra(SERIE, serie).putExtra(LETRA, letra)
        .putExtra(ANO, ano).putExtra(COLECAO, colecao).putExtra(TMDB, tmdbId)

    companion object {
        private const val TITULO = "alvo.titulo"
        private const val SERIE = "alvo.serie"
        private const val LETRA = "alvo.letra"
        private const val ANO = "alvo.ano"
        private const val COLECAO = "alvo.colecao"
        private const val TMDB = "alvo.tmdb"

        fun de(intent: Intent?): Alvo? {
            val titulo = intent?.getStringExtra(TITULO) ?: return null
            return Alvo(
                titulo = titulo,
                serie = intent.getBooleanExtra(SERIE, false),
                letra = intent.getStringExtra(LETRA).orEmpty(),
                ano = intent.getStringExtra(ANO).orEmpty(),
                colecao = intent.getStringExtra(COLECAO).orEmpty(),
                tmdbId = intent.getIntExtra(TMDB, 0),
            )
        }
    }
}

/**
 * A ficha de um título, como tela inteira.
 *
 * Antes era uma lista de linhas de texto — sinopse numa, elenco em outras,
 * sem foto nem capa. Agora é o que uma TV mostra: a imagem larga do filme ao
 * fundo, a capa, os números em selos, a sinopse, e o elenco em fotos redondas
 * logo abaixo do botão de assistir.
 *
 * "Assistir" não toca daqui: devolve o título para a tela do acervo, que é
 * quem sabe escolher fonte e temporada. Assim o caminho até o vídeo é um só,
 * venha a pessoa de onde vier.
 */
class FichaActivity : AppCompatActivity() {

    private lateinit var alvo: Alvo
    private lateinit var favoritar: TextView
    private val elenco = ElencoAdapter { pessoa -> AtorActivity.abrir(this, pessoa) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alvo = Alvo.de(intent) ?: run { finish(); return }
        setContentView(R.layout.activity_ficha)

        val assistir = findViewById<TextView>(R.id.fichaAssistir)
        favoritar = findViewById(R.id.fichaFavoritar)

        findViewById<TextView>(R.id.fichaTipo).text = getString(when (alvo.colecao) {
            "animes" -> R.string.vod_ficha_tipo_anime
            "doramas" -> R.string.vod_ficha_tipo_dorama
            else -> if (alvo.serie) R.string.vod_ficha_tipo_serie else R.string.vod_ficha_tipo_filme
        })
        // O ano vai no selo; no título ele só empurrava o nome para as
        // reticências — "Monstro: A História de Lizzie Borden (20…".
        findViewById<TextView>(R.id.fichaTitulo).text = Generos.semAno(alvo.titulo)
        findViewById<TextView>(R.id.fichaInicial).text = alvo.titulo.take(1).uppercase()

        // A capa do arquivo de fichas chega na hora; a da ficha do TMDB, se
        // vier diferente, substitui depois.
        Generos.capa(alvo.nomeCompleto, alvo.serie)?.let { mostrarCapa(it) }

        assistir.text = getString(textoDoAssistir())
        assistir.setOnClickListener { assistir() }
        assistir.setOnFocusChangeListener(crescerNoFoco)
        favoritar.setOnFocusChangeListener(crescerNoFoco)
        // Anime e dorama moram fora do acervo comum e os favoritos guardam a
        // letra; sem letra não há como reabrir, então o botão não aparece.
        if (alvo.colecao.isNotEmpty()) {
            favoritar.visibility = View.GONE
        } else {
            atualizarFavorito()
            favoritar.setOnClickListener {
                VodFavoritos.alternar(this,
                    VodFavoritos.Item(alvo.titulo, alvo.serie, alvo.letra, alvo.ano))
                atualizarFavorito()
            }
        }

        findViewById<RecyclerView>(R.id.fichaElenco).apply {
            layoutManager = LinearLayoutManager(this@FichaActivity, RecyclerView.HORIZONTAL, false)
            adapter = elenco
            setHasFixedSize(true)
            // Descer do "Assistir" cai na primeira foto, não onde a busca
            // espacial do sistema calhar.
            nextFocusUpId = R.id.fichaAssistir
        }
        assistir.nextFocusDownId = R.id.fichaElenco
        favoritar.nextFocusDownId = R.id.fichaElenco
        // À direita do último botão não há nada: sem isto a busca espacial
        // descia na diagonal para o meio do elenco.
        favoritar.nextFocusRightId = R.id.fichaFavoritar

        assistir.requestFocus()
        carregar()
    }

    private fun textoDoAssistir(): Int = when {
        alvo.serie -> R.string.vod_ficha_temporadas
        (Progresso.fracao(this, Progresso.chaveFilme(alvo.titulo)) ?: 0f) > 0f ->
            R.string.vod_ficha_continuar
        else -> R.string.vod_ficha_assistir_filme
    }

    private fun carregar() {
        val carregando = findViewById<ProgressBar>(R.id.fichaCarregando)
        lifecycleScope.launch(semDerrubar) {
            // Sem as fichas em memória, o id do título não é conhecido. O
            // arquivo já está em disco na maioria das vezes: é instantâneo.
            Generos.carregar(this@FichaActivity)
            Generos.capa(alvo.nomeCompleto, alvo.serie)?.let { mostrarCapa(it) }
            // Destaque de anime ou dorama chega sem o id: a coleção o publica,
            // e é ele que acerta — o arquivo de fichas casa "Kevin" com outra
            // série de mesmo nome.
            val id = alvo.tmdbId.takeIf { it > 0 }
                ?: alvo.colecao.takeIf { it.isNotEmpty() }?.let { tipo ->
                    runCatching {
                        Vod.colecao(this@FichaActivity, tipo)
                            .firstOrNull { it.titulo.equals(alvo.titulo, ignoreCase = true) }
                            ?.tmdbId?.toIntOrNull()
                    }.getOrNull()
                }
            val ficha = Detalhes.de(alvo.titulo, alvo.serie, id)
            carregando.visibility = View.GONE
            if (isFinishing || isDestroyed) return@launch
            if (ficha == null || ficha.vazia) {
                findViewById<TextView>(R.id.fichaSinopse).text = getString(R.string.vod_ficha_vazia)
                return@launch
            }
            mostrar(ficha)
            if (ficha.elenco.isNotEmpty()) Detalhes.aquecer(this@FichaActivity)
        }
    }

    private fun mostrar(ficha: Detalhes.Ficha) {
        ficha.fundo?.let { endereco ->
            findViewById<ImageView>(R.id.fichaFundo).load(endereco) {
                crossfade(400)
            }
        }
        ficha.capa?.let { mostrarCapa(it) }

        findViewById<TextView>(R.id.fichaFrase).apply {
            text = ficha.frase
            visibility = if (ficha.frase.isBlank()) View.GONE else View.VISIBLE
        }

        val selos = findViewById<LinearLayout>(R.id.fichaSelos)
        selos.removeAllViews()
        if (ficha.nota > 0) selo(selos, "★ %.1f".format(ficha.nota), 0xFFFFD166.toInt())
        if (ficha.ano.isNotBlank()) selo(selos, ficha.ano)
        ficha.duracao?.let {
            selo(selos, if (alvo.serie) getString(R.string.vod_ficha_minutos_ep, it)
                        else duracaoLegivel(it))
        }
        ficha.temporadas?.let {
            selo(selos, resources.getQuantityString(R.plurals.vod_ficha_temporadas_n, it, it))
        }
        ficha.classificacao?.takeIf { it.isNotBlank() }?.let {
            selo(selos, it, corDaClassificacao(it))
        }

        findViewById<TextView>(R.id.fichaGeneros).apply {
            text = ficha.generos.joinToString("  ·  ")
            visibility = if (ficha.generos.isEmpty()) View.GONE else View.VISIBLE
        }
        findViewById<TextView>(R.id.fichaSinopse).text = ficha.sinopse

        val creditos = listOfNotNull(
            ficha.assinatura.takeIf { it.isNotBlank() }?.let {
                getString(if (alvo.serie) R.string.vod_ficha_criacao else R.string.vod_ficha_direcao) +
                    ": " + it
            },
            ficha.roteiro.takeIf { it.isNotBlank() }?.let {
                getString(R.string.vod_ficha_roteiro) + ": " + it
            },
            ficha.produtora.takeIf { it.isNotBlank() }?.let {
                getString(R.string.vod_ficha_producao) + ": " + it
            },
        )
        findViewById<TextView>(R.id.fichaCreditos).apply {
            text = creditos.joinToString("   ·   ")
            visibility = if (creditos.isEmpty()) View.GONE else View.VISIBLE
        }

        if (ficha.elenco.isNotEmpty()) {
            findViewById<View>(R.id.fichaElencoTitulo).visibility = View.VISIBLE
            elenco.trocar(ficha.elenco)
        }
    }

    private fun mostrarCapa(endereco: String) {
        val capa = findViewById<ImageView>(R.id.fichaCapa)
        capa.load(endereco) {
            crossfade(200)
            listener(onSuccess = { _, _ ->
                findViewById<View>(R.id.fichaInicial).visibility = View.GONE
            })
        }
    }

    /** 142 minutos vira "2h 22min": é assim que se lê duração de filme. */
    private fun duracaoLegivel(minutos: Int): String =
        if (minutos < 60) getString(R.string.vod_ficha_minutos, minutos)
        else "${minutos / 60}h ${"%02d".format(minutos % 60)}min"

    /// As cores da classificação indicativa brasileira, as mesmas do celular.
    private fun corDaClassificacao(nota: String): Int = when (nota.uppercase()) {
        "L" -> 0xFF10B981.toInt()
        "10" -> 0xFF3B82F6.toInt()
        "12" -> 0xFFF59E0B.toInt()
        "14" -> 0xFFF97316.toInt()
        "16", "18" -> 0xFFEF4444.toInt()
        else -> 0xFFA8B4C0.toInt()
    }

    private fun selo(onde: LinearLayout, texto: String, cor: Int = 0xFFE6E6E6.toInt()) {
        val dp = { v: Int ->
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, v.toFloat(), resources.displayMetrics)
                .toInt()
        }
        onde.addView(TextView(this).apply {
            text = texto
            setTextColor(cor)
            textSize = 14f
            typeface = android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL)
            setBackgroundResource(R.drawable.bg_selo)
            setPadding(dp(10), dp(4), dp(10), dp(4))
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
            ).apply { marginEnd = dp(8) }
        })
    }

    private fun atualizarFavorito() {
        val marcado = VodFavoritos.contem(this, alvo.titulo, alvo.serie, alvo.ano)
        favoritar.text = getString(if (marcado) R.string.vod_ficha_favorito else R.string.vod_ficha_favoritar)
    }

    /**
     * Volta ao acervo levando o título: a tela de lá abre as fontes do filme
     * ou as temporadas da série, e as telas empilhadas por cima dela — esta
     * ficha e as de ator que vieram antes — fecham no caminho.
     */
    private fun assistir() {
        val volta = alvo.em(Intent(this, VodActivity::class.java))
            .setAction(VodActivity.ABRIR)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(volta)
    }

    companion object {
        fun abrir(context: Context, alvo: Alvo) {
            context.startActivity(alvo.em(Intent(context, FichaActivity::class.java)))
        }

        /// O crescimento no foco é a GPU, não uma nova medição de layout.
        internal val crescerNoFoco = View.OnFocusChangeListener { view, foco ->
            val escala = if (foco) 1.06f else 1f
            view.animate().scaleX(escala).scaleY(escala).setDuration(120).start()
        }
    }
}

/** O elenco em fotos redondas, numa fileira que corre para o lado. */
private class ElencoAdapter(
    private val aoEscolher: (Detalhes.Pessoa) -> Unit,
) : RecyclerView.Adapter<ElencoAdapter.Holder>() {

    private var pessoas: List<Detalhes.Pessoa> = emptyList()

    fun trocar(novas: List<Detalhes.Pessoa>) {
        pessoas = novas
        notifyDataSetChanged()
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val foto: ImageView = view.findViewById(R.id.atorFoto)
        val inicial: TextView = view.findViewById(R.id.atorInicial)
        val nome: TextView = view.findViewById(R.id.atorNome)
        val papel: TextView = view.findViewById(R.id.atorPapel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ator, parent, false)
        view.onFocusChangeListener = FichaActivity.crescerNoFoco
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val pessoa = pessoas[position]
        holder.nome.text = pessoa.nome
        holder.papel.text = pessoa.papel
        holder.inicial.text = pessoa.nome.take(1).uppercase()
        holder.inicial.visibility = View.VISIBLE
        if (pessoa.foto != null) {
            holder.foto.load(pessoa.foto) {
                transformations(CircleCropTransformation())
                crossfade(150)
                listener(onSuccess = { _, _ -> holder.inicial.visibility = View.GONE })
            }
        } else {
            holder.foto.dispose()
            holder.foto.setImageDrawable(null)
        }
        holder.itemView.setOnClickListener { aoEscolher(pessoa) }
    }

    override fun getItemCount() = pessoas.size
}
