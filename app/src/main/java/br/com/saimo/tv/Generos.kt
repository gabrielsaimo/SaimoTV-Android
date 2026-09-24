package br.com.saimo.tv

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Request
import java.io.File

/**
 * O gênero de cada título: Ação, Terror, Animação, Comédia.
 *
 * O catálogo não tem gênero — as listas de origem trazem nome e endereço, nada
 * mais. Perguntar ao TMDB por trinta e quatro mil títulos, no aparelho, a cada
 * abertura, é uma tela que nunca abre; num TV Box, nem isso.
 *
 * A pergunta é feita uma vez no repositório (`gerar_generos.py`) e chega aqui
 * pronta, no mesmo arquivo que o Mac, o Windows e o site leem.
 *
 * Sem o arquivo, a régua de gêneros simplesmente não aparece: oferecer um
 * filtro que devolve vazio é pior que não oferecer.
 */
object Generos {

    private const val ARQUIVO = "fichas.txt"
    /// O acervo muda devagar; um dia em disco evita baixar 2 MB por abertura.
    private const val VALIDADE_MS = 24 * 60 * 60 * 1000L

    private var mapa: Map<String, List<String>> = emptyMap()
    /// Título -> endereço inteiro do pôster, quando o TMDB conhece o título.
    private var capas: Map<String, String> = emptyMap()
    /// Título -> id do TMDB. Com o id em mãos, a ficha completa de um título
    /// é um pedido só, sem busca por nome nem desempate.
    private var ids: Map<String, Int> = emptyMap()
    /// O caminho inverso: id do TMDB -> título do acervo. É assim que a
    /// filmografia de um ator vira uma lista clicável — só entra o que existe
    /// aqui dentro.
    private var porId: Map<Int, String> = emptyMap()
    /// Todos os gêneros que aparecem no acervo, em ordem alfabética.
    var todos: List<String> = emptyList()
        private set

    val prontos: Boolean get() = mapa.isNotEmpty()

    @Volatile
    private var baixando = false

    private fun chave(titulo: String, serie: Boolean) = (if (serie) "s|" else "f|") + titulo

    /**
     * Os gêneros de um título.
     *
     * A chave é o nome como o acervo o escreve — e o acervo escreve o ano
     * dentro do nome do filme, mas guarda o da série num campo à parte. Quem
     * chama aqui nem sempre sabe de qual dos dois veio, então procura-se o
     * nome como ele chegou e, não achando, sem o ano.
     */
    fun de(titulo: String, serie: Boolean): List<String> =
        mapa[chave(titulo, serie)] ?: mapa[chave(semAno(titulo), serie)].orEmpty()

    private val ANO_NO_FIM = Regex("\\s*\\(\\d{4}\\)\\s*$")

    fun semAno(titulo: String): String = titulo.replace(ANO_NO_FIM, "").trim()

    /** O id do TMDB de um título, quando o gerador o resolveu. */
    fun id(titulo: String, serie: Boolean): Int? =
        ids[chave(titulo, serie)] ?: ids[chave(semAno(titulo), serie)]

    /** O título do acervo que corresponde a um id do TMDB, se houver. */
    fun titulo(id: Int, serie: Boolean): String? = porId[if (serie) -id else id]

    fun tem(titulo: String, serie: Boolean, genero: String): Boolean =
        genero.isEmpty() || genero in de(titulo, serie)

    /**
     * Lê as fichas e, quando o arquivo local está velho, busca o novo depois.
     *
     * Antes o download de 3,7 MB vinha primeiro: numa TV Box em wi-fi, isso é
     * a tela inteira sem capa nenhuma até o arquivo chegar, todo dia. Agora o
     * que está em disco entra na hora e o arquivo novo, quando chega, refaz os
     * mapas — a capa que faltava aparece sozinha na próxima rolagem.
     */
    suspend fun carregar(context: Context) = withContext(Dispatchers.IO) {
        if (prontos) return@withContext
        val local = arquivoLocal(context)
        val guardado = runCatching { if (local.exists()) local.readText() else null }.getOrNull()
        if (!guardado.isNullOrBlank()) {
            montar(guardado)
            if (velho(local)) baixarDepois(context)
            return@withContext
        }
        val texto = texto(context) ?: return@withContext
        montar(texto)
    }

    /** Monta os mapas a partir do conteúdo do arquivo. */
    private fun montar(texto: String) {
        val novo = HashMap<String, List<String>>(40_000)
        val novasCapas = HashMap<String, String>(40_000)
        val novosIds = HashMap<String, Int>(40_000)
        val novoPorId = HashMap<Int, String>(40_000)
        val vistos = sortedSetOf<String>()
        var base = ""
        // tipo \t título \t id do TMDB \t pôster \t gêneros
        for (linha in texto.lineSequence()) {
            if (linha.startsWith("capa:")) { base = linha.removePrefix("capa:").trim(); continue }
            if (linha.startsWith("#")) continue
            val campos = linha.split("\t")
            if (campos.size < 5) continue
            val chave = "${campos[0]}|${campos[1]}"
            val poster = campos[3]
            if (poster.isNotBlank()) novasCapas[chave] = base + poster
            val id = campos[2].toIntOrNull()
            if (id != null && id > 0) {
                novosIds[chave] = id
                val marca = if (campos[0] == "s") -id else id
                // Um mesmo id pode aparecer duas vezes no acervo (o mesmo
                // filme em duas grafias); o primeiro basta.
                if (!novoPorId.containsKey(marca)) novoPorId[marca] = campos[1]
            }
            val lista = campos[4].split(",").filter { it.isNotBlank() }
            if (lista.isNotEmpty()) {
                novo[chave] = lista
                vistos += lista
            }
        }
        mapa = novo
        capas = novasCapas
        ids = novosIds
        porId = novoPorId
        todos = vistos.toList()
    }

    private fun arquivoLocal(context: Context) =
        File(File(context.filesDir, "vod").apply { mkdirs() }, ARQUIVO)

    private fun velho(local: File) =
        System.currentTimeMillis() - local.lastModified() >= VALIDADE_MS

    /** Baixa o arquivo novo sem segurar a tela, e refaz os mapas quando chega. */
    private fun baixarDepois(context: Context) {
        if (baixando) return
        baixando = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val novo = baixar(context)
                if (!novo.isNullOrBlank()) montar(novo)
            } finally {
                baixando = false
            }
        }
    }

    /**
     * O pôster de um título, pelo id que o gerador já resolveu.
     *
     * Antes cada aparelho procurava a capa pelo nome no TMDB: lento, e errado
     * quando dois filmes se chamam igual. Agora o endereço vem pronto do mesmo
     * arquivo dos gêneros. Quem não tem ficha fica sem capa — e a tela põe uma
     * marca no lugar, em vez de gastar uma busca que não vai acertar.
     */
    fun capa(titulo: String, serie: Boolean): String? =
        capas[chave(titulo, serie)] ?: capas[chave(semAno(titulo), serie)]

    /** A primeira vez: não há nada em disco, então só resta esperar a rede. */
    private fun texto(context: Context): String? =
        baixar(context) ?: runCatching {
            val local = arquivoLocal(context)
            if (local.exists()) local.readText() else null
        }.getOrNull()

    private fun baixar(context: Context): String? {
        val baixado = runCatching {
            val pedido = Request.Builder().url(Vod.BASE + ARQUIVO)
                .header("User-Agent", Playback.DEFAULT_USER_AGENT)
                .header("Accept", "*/*")
                .build()
            Playback.client.newCall(pedido).execute().use { r ->
                if (!r.isSuccessful) null else r.body?.string()
            }
        }.getOrNull()
        if (!baixado.isNullOrBlank()) {
            runCatching { arquivoLocal(context).writeText(baixado) }
            return baixado
        }
        return null
    }
}
