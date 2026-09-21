package br.com.saimo.tv

import android.content.Context
import kotlinx.coroutines.Dispatchers
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
    /// Todos os gêneros que aparecem no acervo, em ordem alfabética.
    var todos: List<String> = emptyList()
        private set

    val prontos: Boolean get() = mapa.isNotEmpty()

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

    fun tem(titulo: String, serie: Boolean, genero: String): Boolean =
        genero.isEmpty() || genero in de(titulo, serie)

    suspend fun carregar(context: Context) = withContext(Dispatchers.IO) {
        if (prontos) return@withContext
        val texto = texto(context) ?: return@withContext
        val novo = HashMap<String, List<String>>(40_000)
        val novasCapas = HashMap<String, String>(40_000)
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
            val lista = campos[4].split(",").filter { it.isNotBlank() }
            if (lista.isNotEmpty()) {
                novo[chave] = lista
                vistos += lista
            }
        }
        mapa = novo
        capas = novasCapas
        todos = vistos.toList()
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

    private fun texto(context: Context): String? {
        val pasta = File(context.filesDir, "vod").apply { mkdirs() }
        val local = File(pasta, ARQUIVO)
        val fresco = local.exists() && local.length() > 0 &&
            System.currentTimeMillis() - local.lastModified() < VALIDADE_MS
        if (fresco) return runCatching { local.readText() }.getOrNull()

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
            runCatching { local.writeText(baixado) }
            return baixado
        }
        return runCatching { if (local.exists()) local.readText() else null }.getOrNull()
    }
}
