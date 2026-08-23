package br.com.saimo.tv

import androidx.media3.common.util.UnstableApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import okhttp3.Request
import org.json.JSONObject
import java.util.Collections

/**
 * Capa de um filme ou série, procurada pelo nome.
 *
 * As listas de origem não trazem imagem nenhuma, então a capa vem do Cinemeta,
 * que é público e não pede chave. A busca acontece só para o que está na tela:
 * são vinte títulos visíveis, não trinta mil.
 *
 * Nada disso vai para o disco. O que se guarda vive na memória e morre com o
 * app, então cada abertura busca de novo — que é como o dono desta lista quer.
 */
@UnstableApi
object Capas {

    private const val BUSCA = "https://v3-cinemeta.strem.io/catalog"

    /// Só três buscas ao mesmo tempo: rolar a lista depressa não pode virar
    /// cinquenta pedidos, nem para o serviço nem para a rede do TV Box.
    private val portao = Semaphore(3)

    /// Título -> capa. Vazio quer dizer procurado e não achado, e é guardado
    /// também: sem isso a mesma busca infrutífera se repetiria a cada rolagem.
    private val memoria = Collections.synchronizedMap(HashMap<String, String>())

    private val RUIDO = Regex(
        "\\b(4k|uhd|fhd|hd|sd|h265|hevc|hdr|dv|dual|remux|legendado|dublado|leg|dub)\\b",
        RegexOption.IGNORE_CASE)
    private val PARENTESES = Regex("[\\[(][^\\])]*[\\])]")

    /** Nome de busca: sem marca de qualidade, sem colchete, sem ano no fim. */
    fun limpar(titulo: String): String {
        var texto = PARENTESES.replace(titulo, " ")
        texto = RUIDO.replace(texto, " ")
        texto = Regex("\\s+((19|20)\\d{2})\\s*$").replace(texto, " ")
        return texto.replace(Regex("\\s{2,}", RegexOption.IGNORE_CASE), " ").trim()
    }

    /**
     * Capa do título, ou nulo. Devolve na hora o que já está na memória.
     */
    suspend fun capa(titulo: String, serie: Boolean): String? {
        val chave = (if (serie) "s:" else "f:") + titulo
        memoria[chave]?.let { return it.ifEmpty { null } }

        val busca = limpar(titulo)
        if (busca.length < 2) return null

        val achado = portao.withPermit { procurar(busca, serie) }
        memoria[chave] = achado.orEmpty()
        return achado
    }

    private suspend fun procurar(busca: String, serie: Boolean): String? = withContext(Dispatchers.IO) {
        val tipo = if (serie) "series" else "movie"
        val url = "$BUSCA/$tipo/top/search=" + java.net.URLEncoder.encode(busca, "UTF-8") + ".json"
        val corpo = runCatching {
            val pedido = Request.Builder().url(url)
                .header("User-Agent", Playback.DEFAULT_USER_AGENT).build()
            Playback.client.newCall(pedido).execute().use { resposta ->
                if (!resposta.isSuccessful) null else resposta.body?.string()
            }
        }.getOrNull() ?: return@withContext null

        val metas = runCatching { JSONObject(corpo).optJSONArray("metas") }.getOrNull()
            ?: return@withContext null
        // Fica o primeiro resultado, sem conferir o nome. O índice é de
        // títulos e conhece os apelidos em português — "A 13ª Emenda" é "13th"
        // e "A 100 Passos De Um Sonho" é "The Hundred-Foot Journey", que
        // nenhuma comparação de palavras aceitaria. O preço é uma capa errada
        // de vez em quando, quando o título não existe no índice.
        val item = metas.optJSONObject(0) ?: return@withContext null
        item.optString("poster").takeIf { it.isNotBlank() }
    }

}
