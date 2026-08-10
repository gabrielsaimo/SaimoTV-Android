package br.com.saimo.tv

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.text.Normalizer
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import java.util.zip.GZIPInputStream

data class Programme(
    val title: String,
    val category: String,
    val start: Long,
    val stop: Long,
    /// Extras que os feeds publicam de forma irregular. Nenhuma fonte traz
    /// classificação indicativa — não existe no dado.
    val poster: String? = null,
    val episode: String? = null,
    val year: String? = null,
) {
    /// XMLTV escreve episódio como "0.2." no sistema xmltv_ns: temporada,
    /// episódio e parte, todos base zero. Cru não diz nada; vira "T1 E3".
    val episodeLabel: String?
        get() {
            val raw = episode?.takeIf { it.isNotBlank() } ?: return null
            val label = if ("." !in raw) raw.replace(Regex("^S(\\d+)"), "T$1") else {
                val fields = raw.split(".").map { it.substringBefore("/") }
                val season = fields.getOrNull(0)?.trim()?.toIntOrNull()?.plus(1)
                val number = fields.getOrNull(1)?.trim()?.toIntOrNull()?.plus(1)
                when {
                    season != null && number != null -> "T$season E$number"
                    season != null -> "T$season"
                    number != null -> "E$number"
                    else -> null
                }
            } ?: return null
            // Programas diários usam o campo como contador corrido: um
            // telejornal em "T80 E221" é ruído, não informação.
            val numbers = Regex("""\d+""").findAll(label).map { it.value.toInt() }.toList()
            val season = numbers.firstOrNull() ?: return null
            if (season > 40) return null
            if (numbers.size > 1 && numbers.last() > 200) return null
            return label
        }

    /// Linha compacta para onde cabe uma segunda informação.
    val shortDetail: String?
        get() = listOfNotNull(category.ifBlank { null }, episodeLabel, year)
            .takeIf { it.isNotEmpty() }?.joinToString(" · ")

    fun isOnAir(now: Long) = start <= now && stop > now
    fun progress(now: Long): Float =
        if (stop <= start) 0f else ((now - start).toFloat() / (stop - start)).coerceIn(0f, 1f)
}

/**
 * Programme guide, mirroring the macOS build.
 *
 * meuguia.tv comes first because the XMLTV feeds carry the wrong schedule for
 * several channels; the feeds then fill whatever it does not cover. What gets
 * cached is the *parsed* result, not the 16 MB of XML, so a later launch has the
 * guide on screen immediately instead of re-parsing.
 */
object Epg {

    private val FEEDS = listOf(
        "https://iptv-epg.org/files/epg-br.xml",
        "https://www.open-epg.com/files/brazil3.xml",
    )
    private const val CACHE_TTL_MS = 6 * 60 * 60 * 1000L
    private const val PAST_WINDOW_MS = 6 * 60 * 60 * 1000L
    private const val FUTURE_WINDOW_MS = 3 * 24 * 60 * 60 * 1000L

    /** Feed names that differ from ours. */
    private val ALIASES = mapOf(
        "history" to "history channel", "sony channel" to "sony",
        "sportv 2" to "sportv2", "sportv 3" to "sportv3", "gnt" to "gnt hd",
        "band" to "band sp", "warner" to "warner channel", "sbt" to "sbt sp",
    )
    private val NOISE = setOf("hd", "sd", "fhd", "uhd", "4k", "br")

    @Volatile
    private var byChannel: Map<String, List<Programme>> = emptyMap()

    val channelsWithGuide: Int get() = byChannel.size

    /** Todos os programas do canal, para a grade. */
    fun schedule(channel: String): List<Programme> = byChannel[channel] ?: emptyList()

    fun nowNext(channel: String, now: Long = System.currentTimeMillis()): Pair<Programme, Programme?>? {
        val list = byChannel[channel] ?: return null
        val index = list.indexOfFirst { it.isOnAir(now) }
        if (index < 0) return null
        return list[index] to list.getOrNull(index + 1)
    }

    suspend fun load(context: Context, onReady: () -> Unit) = withContext(Dispatchers.IO) {
        readCache(context)?.let {
            byChannel = it
            withContext(Dispatchers.Main) { onReady() }
        }
        val stale = cacheAge(context) > CACHE_TTL_MS || byChannel.isEmpty()
        if (!stale) return@withContext

        val merged = fetchAll()
        if (merged.isNotEmpty()) {
            byChannel = merged
            writeCache(context, merged)
            withContext(Dispatchers.Main) { onReady() }
        }
    }

    private suspend fun fetchAll(): Map<String, List<Programme>> = coroutineScope {
        val names = CATALOG.map { it.name }
        val from = System.currentTimeMillis() - PAST_WINDOW_MS
        val to = System.currentTimeMillis() + FUTURE_WINDOW_MS

        val guia = async(Dispatchers.IO) { MeuGuia.fetch(names, from, to) }
        val feeds = FEEDS.map { url -> async(Dispatchers.IO) { runCatching { download(url) }.getOrNull() } }

        val merged = LinkedHashMap<String, List<Programme>>(guia.await())
        // meuguia publica só título e gênero, então suas entradas são
        // enriquecidas pelos feeds casando por título: a grade continua certa e
        // o pôster, o episódio e o ano vêm junto.
        val byTitle = HashMap<String, Programme>()
        for (deferred in feeds) {
            val xml = deferred.await() ?: continue
            for ((channel, programmes) in parseXmltv(xml, names, from, to)) {
                for (programme in programmes) {
                    val key = normalise(programme.title)
                    if (key.isNotEmpty() && byTitle[key]?.poster == null) byTitle[key] = programme
                }
                if (merged[channel] == null) merged[channel] = programmes
            }
        }
        for ((channel, programmes) in merged.toList()) {
            merged[channel] = programmes.map { programme ->
                if (programme.poster != null) programme
                else byTitle[normalise(programme.title)]?.let {
                    programme.copy(
                        poster = it.poster,
                        episode = programme.episode ?: it.episode,
                        year = programme.year ?: it.year,
                        category = programme.category.ifBlank { it.category })
                } ?: programme
            }
        }
        merged
    }

    // MARK: - XMLTV

    private fun parseXmltv(
        xml: String, wanted: List<String>, from: Long, to: Long,
    ): Map<String, List<Programme>> {
        // display-name -> every id carrying it. Feeds repeat a channel under
        // names that normalise alike, and keeping only the first id binds to
        // whichever copy came first — sometimes the one with no programmes.
        val nameToIds = HashMap<String, MutableList<String>>()
        val channelRegex = Regex("""<channel id="([^"]+)">(.*?)</channel>""", RegexOption.DOT_MATCHES_ALL)
        val displayRegex = Regex("""<display-name[^>]*>([^<]+)</display-name>""")
        for (match in channelRegex.findAll(xml)) {
            val display = displayRegex.find(match.groupValues[2])?.groupValues?.get(1) ?: continue
            nameToIds.getOrPut(normalise(display)) { mutableListOf() }.add(match.groupValues[1])
        }

        // Exact and alias first, marking ids as taken; the loose prefix rule only
        // runs afterwards, and never over an id already claimed.
        val idToChannel = HashMap<String, String>()
        val claimed = HashSet<String>()
        val unresolved = mutableListOf<Pair<String, String>>()
        for (name in wanted) {
            val key = normalise(name)
            val ids = nameToIds[key] ?: ALIASES[key]?.let { nameToIds[it] }
            if (ids != null) {
                ids.forEach { idToChannel[it] = name; claimed.add(it) }
            } else {
                unresolved += name to key
            }
        }
        for ((name, key) in unresolved) {
            val candidates = nameToIds.filterKeys { it.startsWith(key) || key.startsWith(it) }
            if (candidates.size != 1) continue
            candidates.values.first().filterNot { it in claimed }.forEach { idToChannel[it] = name }
        }
        if (idToChannel.isEmpty()) return emptyMap()

        val out = HashMap<String, MutableList<Programme>>()
        val programmeRegex = Regex(
            """<programme([^>]*)>(.*?)</programme>""", RegexOption.DOT_MATCHES_ALL)
        val attr = { text: String, name: String ->
            Regex("""$name="([^"]*)"""").find(text)?.groupValues?.get(1)
        }
        for (match in programmeRegex.findAll(xml)) {
            val attrs = match.groupValues[1]
            val channel = attr(attrs, "channel")?.let { idToChannel[it] } ?: continue
            val start = parseXmltvDate(attr(attrs, "start") ?: continue) ?: continue
            val stop = parseXmltvDate(attr(attrs, "stop") ?: continue) ?: continue
            if (stop <= from || start >= to) continue

            val body = match.groupValues[2]
            val title = Regex("""<title[^>]*>([^<]*)</title>""").find(body)
                ?.groupValues?.get(1)?.let(::decodeEntities)?.trim() ?: continue
            if (title.isEmpty()) continue
            val category = Regex("""<category[^>]*>([^<]*)</category>""").find(body)
                ?.groupValues?.get(1)?.let(::decodeEntities)?.trim() ?: ""
            // O pôster vem em atributo, não em texto de elemento.
            val poster = Regex("""<icon[^>]*src="([^"]+)"""").find(body)
                ?.groupValues?.get(1)?.let(::decodeEntities)
            val episode = Regex("""<episode-num[^>]*>([^<]*)</episode-num>""").find(body)
                ?.groupValues?.get(1)?.trim()
            val year = Regex("""<date[^>]*>([^<]*)</date>""").find(body)
                ?.groupValues?.get(1)?.trim()?.take(4)
            out.getOrPut(channel) { mutableListOf() } +=
                Programme(title, category, start, stop, poster, episode, year)
        }
        return out.mapValues { it.value.sortedBy { p -> p.start } }
    }

    /** XMLTV timestamps look like `20260809153000 -0300`. */
    private fun parseXmltvDate(text: String): Long? {
        if (text.length < 14) return null
        val digits = text.take(14)
        return runCatching {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.clear()
            calendar.set(
                digits.substring(0, 4).toInt(), digits.substring(4, 6).toInt() - 1,
                digits.substring(6, 8).toInt(), digits.substring(8, 10).toInt(),
                digits.substring(10, 12).toInt(), digits.substring(12, 14).toInt())
            var millis = calendar.timeInMillis
            val tail = text.drop(14).trim()
            if (tail.length >= 5 && (tail[0] == '+' || tail[0] == '-')) {
                val offset = (tail.substring(1, 3).toInt() * 3600 +
                    tail.substring(3, 5).toInt() * 60) * 1000L
                millis += if (tail[0] == '-') offset else -offset
            }
            millis
        }.getOrNull()
    }

    // MARK: - Shared helpers

    fun normalise(text: String): String {
        var value = decodeEntities(text).replace(Regex("""^[A-Z]{2}\s*[-|]\s*"""), "")
        value = Normalizer.normalize(value, Normalizer.Form.NFD)
            .replace(Regex("""\p{Mn}+"""), "")
        val tokens = value.lowercase(Locale.ROOT)
            .replace(Regex("""[^a-z0-9]+"""), " ").trim().split(" ")
            .filter { it.isNotEmpty() }.toMutableList()
        while (tokens.size > 1 && tokens.last() in NOISE) tokens.removeAt(tokens.size - 1)
        return tokens.joinToString(" ")
    }

    fun decodeEntities(text: String): String =
        if ('&' !in text) text
        else text.replace("&amp;", "&").replace("&lt;", "<").replace("&gt;", ">")
            .replace("&quot;", "\"").replace("&apos;", "'").replace("&#39;", "'")

    fun download(url: String, referer: String? = null): String {
        val connection = (URL(url).openConnection() as HttpURLConnection).apply {
            setRequestProperty("User-Agent", Playback.DEFAULT_USER_AGENT)
            setRequestProperty("Accept-Encoding", "gzip")
            referer?.let { setRequestProperty("Referer", it) }
            connectTimeout = 25_000
            readTimeout = 60_000
            instanceFollowRedirects = true
        }
        connection.inputStream.use { raw ->
            val stream = if (connection.contentEncoding?.contains("gzip", true) == true) {
                GZIPInputStream(raw)
            } else raw
            return stream.bufferedReader().readText()
        }
    }

    // MARK: - Cache

    private fun cacheFile(context: Context) = File(context.cacheDir, "epg.json")

    /// Marca o instante da última virada; as telas comparam para saber se
    /// precisam redesenhar quando um programa termina.
    @Volatile
    var clock: Long = System.currentTimeMillis()
        private set

    fun tick(): Boolean {
        val now = System.currentTimeMillis()
        val changed = now - clock > 15_000
        if (changed) clock = now
        return changed
    }

    private fun cacheAge(context: Context): Long {
        val file = cacheFile(context)
        return if (file.exists()) System.currentTimeMillis() - file.lastModified() else Long.MAX_VALUE
    }

    private fun readCache(context: Context): Map<String, List<Programme>>? = runCatching {
        val root = JSONObject(cacheFile(context).readText())
        val cutoff = System.currentTimeMillis() - PAST_WINDOW_MS
        val out = HashMap<String, List<Programme>>()
        for (name in root.keys()) {
            val array = root.getJSONArray(name)
            val list = (0 until array.length()).map { index ->
                val item = array.getJSONObject(index)
                Programme(item.getString("t"), item.optString("c"),
                    item.getLong("s"), item.getLong("e"),
                    item.optString("p").ifEmpty { null },
                    item.optString("n").ifEmpty { null },
                    item.optString("y").ifEmpty { null })
            }.filter { it.stop > cutoff }
            if (list.isNotEmpty()) out[name] = list
        }
        out.takeIf { it.isNotEmpty() }
    }.getOrNull()

    private fun writeCache(context: Context, data: Map<String, List<Programme>>) = runCatching {
        val root = JSONObject()
        for ((name, list) in data) {
            val array = JSONArray()
            for (programme in list) {
                array.put(JSONObject().apply {
                    put("t", programme.title)
                    put("c", programme.category)
                    put("s", programme.start)
                    put("e", programme.stop)
                    programme.poster?.let { put("p", it) }
                    programme.episode?.let { put("n", it) }
                    programme.year?.let { put("y", it) }
                })
            }
            root.put(name, array)
        }
        cacheFile(context).writeText(root.toString())
    }
}
