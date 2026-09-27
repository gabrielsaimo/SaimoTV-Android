package br.com.saimo.tv

import androidx.media3.common.util.UnstableApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import org.json.JSONObject

/**
 * Onde começa e acaba a abertura, a recapitulação e os créditos de um título.
 *
 * Os tempos vêm do TheIntroDB (theintrodb.org), um banco aberto em que quem
 * assiste marca esses trechos e o servidor devolve a média conferida pela
 * comunidade. A consulta é pelo id do TMDB, que o arquivo de fichas já traz
 * para cada título — então não há adivinhação: ou o trecho foi marcado para
 * aquele episódio, ou o botão simplesmente não aparece.
 *
 * Os créditos também servem ao "próximo episódio": o cartão sobe quando eles
 * começam, e não num minuto fixo antes do fim que corta a última cena.
 */
@UnstableApi
object Pulos {

    private const val BASE = "https://api.theintrodb.org/v3/media"

    /** Um trecho, em milissegundos. [fim] nulo quer dizer "até o fim do vídeo". */
    data class Trecho(val tipo: Tipo, val inicio: Long, val fim: Long?)

    enum class Tipo { ABERTURA, RECAPITULACAO, CREDITOS, PREVIA }

    data class Marcas(val trechos: List<Trecho>) {
        val creditos: Long? get() = trechos.firstOrNull { it.tipo == Tipo.CREDITOS }?.inicio
        /** O trecho pulável em que [posicao] está agora, se houver. */
        fun em(posicao: Long, duracao: Long): Trecho? = trechos.firstOrNull {
            it.tipo != Tipo.CREDITOS && posicao >= it.inicio &&
                posicao < (it.fim ?: duracao) - 1_000
        }
    }

    private val guardadas = HashMap<String, Marcas>()

    suspend fun de(tmdbId: Int, temporada: Int = 0, episodio: Int = 0): Marcas? =
        withContext(Dispatchers.IO) {
            if (tmdbId <= 0) return@withContext null
            val chave = "$tmdbId|$temporada|$episodio"
            synchronized(guardadas) { guardadas[chave] }?.let { return@withContext it }
            val url = buildString {
                append(BASE).append("?tmdb_id=").append(tmdbId)
                if (temporada > 0) append("&season=").append(temporada).append("&episode=").append(episodio)
            }
            val json = runCatching {
                val pedido = Request.Builder().url(url)
                    .header("Accept", "application/json")
                    .header("User-Agent", "SaimoTV/${BuildConfig.VERSION_NAME}")
                    .build()
                Playback.client.newCall(pedido).execute().use { r ->
                    if (!r.isSuccessful) null else r.body?.string()?.let(::JSONObject)
                }
            }.getOrNull() ?: return@withContext null
            val marcas = Marcas(ler(json))
            synchronized(guardadas) { guardadas[chave] = marcas }
            marcas
        }

    internal fun ler(json: JSONObject): List<Trecho> {
        val out = mutableListOf<Trecho>()
        for ((campo, tipo) in listOf(
            "intro" to Tipo.ABERTURA, "recap" to Tipo.RECAPITULACAO,
            "credits" to Tipo.CREDITOS, "preview" to Tipo.PREVIA)) {
            val lista = json.optJSONArray(campo) ?: continue
            for (i in 0 until lista.length()) {
                val item = lista.optJSONObject(i) ?: continue
                val inicio = if (item.isNull("start_ms")) 0L else item.optLong("start_ms", 0L)
                val fim = if (item.isNull("end_ms")) null else item.optLong("end_ms")
                // Trecho de menos de três segundos é marcação errada.
                if (fim != null && fim - inicio < 3_000) continue
                out += Trecho(tipo, inicio, fim)
            }
        }
        return out
    }
}
