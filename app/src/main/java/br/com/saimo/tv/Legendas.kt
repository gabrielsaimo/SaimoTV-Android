package br.com.saimo.tv

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import org.json.JSONObject
import java.util.Locale

/**
 * Legendas externas de filmes e séries, pelo OpenSubtitles.
 *
 * O serviço é o addon público do Stremio para o OpenSubtitles
 * (opensubtitles-v3.strem.io): sem chave, sem cadastro, e entrega o arquivo já
 * em UTF-8. Só entende IMDb; o acervo só conhece o id do TMDB, e a ponte está
 * publicada em `vod/imdb/` (gerar_imdb.py) em fragmentos de uns 8 KB — baixa-se
 * o fragmento do título aberto, nunca o mapa inteiro. Mesma fonte do site, do
 * Mac, do Windows e do celular.
 *
 * Nada é baixado antes da hora: abrir um título custa uma lista de ~30 KB, e o
 * arquivo .srt (~40 KB) só vem quando a pessoa escolhe uma legenda.
 */
object Legendas {

    private const val OPENSUBTITLES = "https://opensubtitles-v3.strem.io/subtitles"
    private const val FRAGMENTOS = 100

    /** O id da faixa de legenda externa dentro do player, para achá-la e não repetir no menu. */
    const val ID_FAIXA = "saimo-legenda-externa"

    /** Idiomas oferecidos, na ordem em que aparecem: código, nome, quantas versões. */
    private val IDIOMAS = listOf(
        Triple("pob", "Português (Brasil)", 5),
        Triple("por", "Português (Portugal)", 3),
        Triple("eng", "Inglês", 3),
        Triple("spa", "Espanhol", 2),
    )

    /** [idioma] é o código do OpenSubtitles: "pob", "por", "eng", "spa". */
    data class Opcao(val id: String, val idioma: String, val rotulo: String, val url: String) {
        /** O código que o ExoPlayer entende. */
        val bcp47: String get() = when (idioma) {
            "pob" -> "pt-BR"; "por" -> "pt-PT"; "eng" -> "en"; "spa" -> "es"; else -> idioma
        }
    }

    private val fragmentos = HashMap<String, Map<Int, String>>()
    private val listas = HashMap<String, List<Opcao>>()

    private fun texto(url: String): String? = runCatching {
        val pedido = Request.Builder().url(url)
            .header("Accept", "*/*")
            .header("User-Agent", "SaimoTV/${BuildConfig.VERSION_NAME}")
            .build()
        Playback.client.newCall(pedido).execute().use { r ->
            if (r.isSuccessful) r.body?.string() else null
        }
    }.getOrNull()

    private fun imdbDe(tmdbId: Int, serie: Boolean): String? {
        val nome = "${if (serie) "s" else "f"}-${"%02d".format(tmdbId % FRAGMENTOS)}"
        synchronized(fragmentos) { fragmentos[nome] }?.let { return it[tmdbId] }
        // Sem o fragmento (rede fora, título ainda não mapeado) não guarda nada:
        // a próxima abertura tenta de novo.
        val corpo = texto("${Vod.BASE}imdb/$nome.txt") ?: return null
        val mapa = HashMap<Int, String>()
        for (linha in corpo.lineSequence()) {
            val partes = linha.split('\t')
            val id = partes.getOrNull(0)?.trim()?.toIntOrNull() ?: continue
            partes.getOrNull(1)?.trim()?.takeIf { it.isNotEmpty() }?.let { mapa[id] = it }
        }
        synchronized(fragmentos) { fragmentos[nome] = mapa }
        return mapa[tmdbId]
    }

    /** As legendas do título, do melhor idioma para o pior. Vazio quando não há. */
    suspend fun de(tmdbId: Int, serie: Boolean, temporada: Int = 0, episodio: Int = 0): List<Opcao> =
        withContext(Dispatchers.IO) {
            if (tmdbId <= 0) return@withContext emptyList()
            val chave = "$serie|$tmdbId|$temporada|$episodio"
            synchronized(listas) { listas[chave] }?.let { return@withContext it }
            val imdb = imdbDe(tmdbId, serie) ?: return@withContext emptyList()
            val alvo = if (serie && temporada > 0) "series/$imdb:$temporada:$episodio" else "movie/$imdb"
            val json = texto("$OPENSUBTITLES/$alvo.json")?.let { runCatching { JSONObject(it) }.getOrNull() }
                ?: return@withContext emptyList()
            val todas = json.optJSONArray("subtitles") ?: return@withContext emptyList()
            val saida = mutableListOf<Opcao>()
            for ((codigo, nome, limite) in IDIOMAS) {
                var n = 0
                for (i in 0 until todas.length()) {
                    if (n >= limite) break
                    val s = todas.optJSONObject(i) ?: continue
                    val url = s.optString("url")
                    if (s.optString("lang") != codigo || url.isBlank()) continue
                    val versao = listOf(s.optString("releaseGroup"), s.optString("releaseFormat"))
                        .firstOrNull { it.isNotBlank() } ?: (n + 1).toString()
                    saida += Opcao("$codigo-${s.opt("id") ?: i}", codigo, "$nome · $versao", url)
                    n++
                }
            }
            synchronized(listas) { listas[chave] = saida }
            saida
        }

    private val arquivos = HashMap<String, String>()

    /** O SRT da legenda, em UTF-8. Nulo quando o servidor devolveu outra coisa. */
    suspend fun baixar(opcao: Opcao): String? = withContext(Dispatchers.IO) {
        synchronized(arquivos) { arquivos[opcao.url] }?.let { return@withContext it }
        // Página de erro no lugar do arquivo: melhor sem legenda que com HTML na tela.
        val corpo = texto(opcao.url)?.takeIf { it.contains("-->") } ?: return@withContext null
        synchronized(arquivos) { arquivos[opcao.url] = corpo }
        corpo
    }

    private val TEMPO = Regex("""(\d+):(\d{2}):(\d{2})[,.](\d{1,3})""")

    private fun milissegundos(m: MatchResult): Long =
        m.groupValues[1].toLong() * 3_600_000 + m.groupValues[2].toLong() * 60_000 +
            m.groupValues[3].toLong() * 1_000 + m.groupValues[4].padEnd(3, '0').toLong()

    private fun formatar(ms: Long): String {
        val t = ms.coerceAtLeast(0)
        return String.format(Locale.US, "%02d:%02d:%02d,%03d", t / 3_600_000, t / 60_000 % 60, t / 1_000 % 60, t % 1_000)
    }

    /**
     * O mesmo SRT com todas as marcas de tempo deslocadas em [segundos]
     * (positivo atrasa, negativo adianta). O ExoPlayer não tem atraso de
     * legenda, então o ajuste é feito no próprio arquivo, antes de entregar.
     */
    fun deslocar(srt: String, segundos: Double): String {
        if (segundos == 0.0) return srt
        val delta = Math.round(segundos * 1000)
        return srt.lineSequence().joinToString("\n") { linha ->
            if (!linha.contains("-->")) linha
            else TEMPO.replace(linha) { formatar(milissegundos(it) + delta) }
        }
    }
}
