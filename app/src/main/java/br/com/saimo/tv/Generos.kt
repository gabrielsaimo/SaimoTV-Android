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

    private const val ARQUIVO = "generos.txt"
    /// O acervo muda devagar; um dia em disco evita baixar 2 MB por abertura.
    private const val VALIDADE_MS = 24 * 60 * 60 * 1000L

    private var mapa: Map<String, List<String>> = emptyMap()
    /// Todos os gêneros que aparecem no acervo, em ordem alfabética.
    var todos: List<String> = emptyList()
        private set

    val prontos: Boolean get() = mapa.isNotEmpty()

    private fun chave(titulo: String, serie: Boolean) = (if (serie) "s|" else "f|") + titulo

    fun de(titulo: String, serie: Boolean): List<String> =
        mapa[chave(titulo, serie)].orEmpty()

    fun tem(titulo: String, serie: Boolean, genero: String): Boolean =
        genero.isEmpty() || genero in de(titulo, serie)

    suspend fun carregar(context: Context) = withContext(Dispatchers.IO) {
        if (prontos) return@withContext
        val texto = texto(context) ?: return@withContext
        val novo = HashMap<String, List<String>>(40_000)
        val vistos = sortedSetOf<String>()
        for (linha in texto.lineSequence()) {
            if (linha.startsWith("#")) continue
            val campos = linha.split("\t")
            if (campos.size < 3 || campos[2].isBlank()) continue
            val lista = campos[2].split(",").filter { it.isNotBlank() }
            if (lista.isEmpty()) continue
            novo["${campos[0]}|${campos[1]}"] = lista
            vistos += lista
        }
        mapa = novo
        todos = vistos.toList()
    }

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
