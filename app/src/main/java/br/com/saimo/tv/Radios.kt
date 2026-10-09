package br.com.saimo.tv

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import java.io.File
import java.text.Normalizer
import java.util.Locale

/**
 * A lista de rádios: `vod/radios.txt` do SaimoPlayer, uma por linha,
 * `nome|endereço|logo` (o logo é opcional).
 *
 * Nunca fica vazia. A tela mostra na hora a última lista guardada em disco —
 * ou, na primeira vez e sem internet, a cópia que vem dentro do app — e busca
 * a do repositório por trás. Antes a lista só era baixada quando não havia
 * nenhuma: quem já tinha uma nunca recebia rádio nova, e quando o arquivo saiu
 * do ar (09/10) quem abria pela primeira vez via a tela vazia.
 */
object Radios {

    data class Radio(val nome: String, val url: String, val logo: String?)

    /** Um estilo de rádio, achado pelo nome dela. */
    data class Estilo(val titulo: Int, val palavras: List<String>)

    private const val ARQUIVO = "radios.txt"

    /// A ordem conta: rádio que cabe em dois estilos fica no primeiro.
    val ESTILOS = listOf(
        Estilo(R.string.radios_rock, listOf("rock", "metal", "kiss", "punk")),
        Estilo(R.string.radios_noticias, listOf("news", "noticia", "cbn", "gaucha", "itatiaia", "tupi",
            "esporte", "jornal", "bandnews", "radio globo", "eldorado", "bandeirantes")),
        Estilo(R.string.radios_gospel, listOf("gospel", "evangel", "catolic", "crista", "louvor", "aleluia",
            "cancao nova", "novo tempo", "aparecida", "fe ", "deus", "jesus", "igreja", "biblia")),
        Estilo(R.string.radios_pop, listOf("pop", "hits", "dance", "mix", "jovem pan", "transamerica",
            "energia", "eletro", "club", "pulse", "top")),
        Estilo(R.string.radios_brasileiras, listOf("sertanej", "mpb", "samba", "pagode", "forro", "nativa",
            "caipira", "brega", "axe", "piseiro", "moda de viola")),
        Estilo(R.string.radios_classica, listOf("classic", "jazz", "cultura", "educativa", "universit",
            "instrumental", "blues", "lounge")),
    )

    /** Nome sem acento e em minúsculas, para achar o estilo. */
    fun chave(nome: String): String =
        Normalizer.normalize(nome, Normalizer.Form.NFD).replace(Regex("\\p{M}+"), "").lowercase(Locale.ROOT)

    fun estiloDe(radio: Radio): Estilo? {
        val nome = chave(radio.nome) + " "
        return ESTILOS.firstOrNull { e -> e.palavras.any { nome.contains(it) } }
    }

    private fun ler(texto: String): List<Radio> = texto.lineSequence().mapNotNull { linha ->
        val partes = linha.split("|")
        val nome = partes.getOrNull(0)?.trim().orEmpty()
        val url = partes.getOrNull(1)?.trim().orEmpty()
        if (nome.isEmpty() || !url.startsWith("http")) return@mapNotNull null
        Radio(nome, url, partes.getOrNull(2)?.trim()?.takeIf { it.startsWith("http") })
    }.distinctBy { it.nome }.toList()

    /** O que já está no aparelho: a última lista baixada ou a que vem no app. Rápido, sem rede. */
    fun locais(context: Context): List<Radio> {
        val arquivo = File(context.filesDir, ARQUIVO)
        val guardada = runCatching { arquivo.takeIf { it.exists() }?.readText() }.getOrNull()
            ?.let { ler(it) }?.takeIf { it.isNotEmpty() }
        return guardada ?: runCatching { context.assets.open(ARQUIVO).bufferedReader().readText() }
            .getOrNull()?.let { ler(it) }.orEmpty()
    }

    /**
     * A lista do repositório, guardada em disco para a próxima vez. Nula quando
     * a rede falhou ou veio outra coisa (página de erro, arquivo vazio) — aí
     * fica valendo a local.
     */
    suspend fun baixar(context: Context): List<Radio>? = withContext(Dispatchers.IO) {
        val texto = runCatching {
            val pedido = Request.Builder().url(Vod.BASE + ARQUIVO)
                .header("User-Agent", Playback.DEFAULT_USER_AGENT)
                .build()
            Playback.client.newCall(pedido).execute().use { r -> if (r.isSuccessful) r.body.string() else null }
        }.getOrNull() ?: return@withContext null
        val lista = ler(texto).takeIf { it.size >= 10 } ?: return@withContext null
        runCatching { File(context.filesDir, ARQUIVO).writeText(texto) }
        lista
    }

    // MARK: - Ouvidas por último

    private fun prefs(context: Context) = context.getSharedPreferences("radios", Context.MODE_PRIVATE)

    /** Nomes das rádios ouvidas, da mais recente para a mais antiga. */
    fun recentes(context: Context): List<String> =
        prefs(context).getString("recentes", "").orEmpty().split("\n").filter { it.isNotBlank() }

    fun ouviu(context: Context, nome: String) {
        val nova = (listOf(nome) + recentes(context).filterNot { it == nome }).take(12)
        prefs(context).edit().putString("recentes", nova.joinToString("\n")).apply()
    }
}
