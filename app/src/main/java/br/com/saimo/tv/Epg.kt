package br.com.saimo.tv

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.Reader
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
    /// A sinopse vem em quase todo programa do XMLTV e era descartada.
    val description: String? = null,
    val cast: List<String> = emptyList(),
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
        "adult swim" to "trutv", "history" to "history channel",
        "sony channel" to "sony",
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
        // Um canal novo no catálogo torna o cache velho na hora, mesmo dentro do
        // prazo: sem isto, quem já tinha o app instalado ficaria até seis horas
        // com o canal recém-chegado sem guia nenhum.
        val stale = cacheAge(context) > CACHE_TTL_MS ||
            byChannel.isEmpty() ||
            readSignature(context) != signature()
        if (!stale) return@withContext

        val names = Remote.channels.map { it.name }
        val from = System.currentTimeMillis() - PAST_WINDOW_MS
        val to = System.currentTimeMillis() + FUTURE_WINDOW_MS
        val merged = LinkedHashMap<String, List<Programme>>()

        suspend fun publish() {
            byChannel = LinkedHashMap(merged)
            withContext(Dispatchers.Main) { onReady() }
        }

        // meuguia primeiro: são 33 páginas pequenas, chegam em segundos e a
        // grade já aparece, em vez de a tela ficar vazia até os feeds baixarem.
        merged.putAll(MeuGuia.fetch(names, from, to))
        if (merged.isNotEmpty()) publish()

        // Reserva do guiadetv: só para quem o meuguia não listou.
        val faltando = names.filter { merged[it] == null }
        if (faltando.isNotEmpty()) {
            merged.putAll(GuiaDeTv.fetch(faltando, from, to))
            if (merged.isNotEmpty()) publish()
        }

        // Os feeds vêm um de cada vez e são lidos em fluxo. Um deles tem 15 MB:
        // virar String de uma vez custa uns 90 MB transitórios de char[] — num
        // TV Box isso é OutOfMemoryError engolido pelo runCatching, ou seja, o
        // guia simplesmente não aparecia.
        val byTitle = HashMap<String, Programme>()
        for (url in FEEDS) {
            val parsed = runCatching { parseFeed(url, names, from, to, byTitle) }
                .getOrNull() ?: continue
            for ((channel, programmes) in parsed) {
                if (merged[channel] == null) merged[channel] = programmes
            }
            // O enriquecimento acontece a cada feed, não no fim de todos: é ele
            // que traz as imagens, e esperar os 19 MB inteiros significava abrir
            // o guia numa grade sem nenhuma.
            enrich(merged, byTitle)
            publish()
        }

        if (merged.isEmpty()) return@withContext
        writeCache(context, merged)
    }

    /**
     * meuguia publica só título e gênero. Casando por título, os feeds trazem o
     * pôster, a sinopse, o elenco, o episódio e o ano sem tocar no horário, que
     * é justamente o dado em que o meuguia é mais confiável.
     *
     * Preenche campo a campo: parar no primeiro programa que já tem pôster
     * deixava sem sinopse justamente os que o feed conseguiu ilustrar.
     */
    private fun enrich(
        merged: MutableMap<String, List<Programme>>, byTitle: Map<String, Programme>,
    ) {
        for ((channel, programmes) in merged.toList()) {
            merged[channel] = programmes.map { programme ->
                if (programme.poster != null && programme.description != null &&
                    programme.cast.isNotEmpty()) return@map programme
                byTitle[normalise(programme.title)]?.let {
                    programme.copy(
                        poster = programme.poster ?: it.poster,
                        episode = programme.episode ?: it.episode,
                        year = programme.year ?: it.year,
                        description = programme.description ?: it.description,
                        cast = programme.cast.ifEmpty { it.cast },
                        category = programme.category.ifBlank { it.category })
                } ?: programme
            }
        }
    }

    // MARK: - XMLTV

    /**
     * Reads one XMLTV feed straight off the socket.
     *
     * XMLTV writes every `<channel>` before the first `<programme>`, so a single
     * forward pass is enough: the id map is resolved the moment programmes
     * start, and only elements inside the window are ever kept.
     */
    internal fun parseFeed(
        url: String, wanted: List<String>, from: Long, to: Long,
        byTitle: MutableMap<String, Programme>,
    ): Map<String, List<Programme>> {
        // display-name -> every id carrying it. Feeds repeat a channel under
        // names that normalise alike, and keeping only the first id binds to
        // whichever copy came first — sometimes the one with no programmes.
        val nameToIds = HashMap<String, MutableList<String>>()
        var idToChannel: Map<String, String>? = null
        val out = HashMap<String, MutableList<Programme>>()

        openStream(url).use { reader ->
            scanElements(reader) { tag, element ->
                if (tag == CHANNEL) {
                    if (idToChannel != null) return@scanElements
                    val id = attribute(element, "id") ?: return@scanElements
                    val display = between(element, "<display-name", "</display-name>")
                        ?.substringAfter('>', "") ?: return@scanElements
                    if (display.isNotBlank()) {
                        nameToIds.getOrPut(normalise(display)) { mutableListOf() }.add(id)
                    }
                    return@scanElements
                }
                val map = idToChannel ?: resolveIds(nameToIds, wanted).also { idToChannel = it }
                if (map.isEmpty()) return@scanElements
                val programme = parseProgramme(element, map, from, to) ?: return@scanElements
                val key = normalise(programme.second.title)
                if (key.isNotEmpty() && byTitle[key]?.poster == null) byTitle[key] = programme.second
                out.getOrPut(programme.first) { mutableListOf() } += programme.second
            }
        }
        return out.mapValues { it.value.sortedBy { p -> p.start } }
    }

    /**
     * Exact and alias matches first, marking ids as taken; the loose prefix rule
     * only runs afterwards, and never over an id already claimed — otherwise
     * "HBO2" swallows the id belonging to "HBO 2".
     */
    private fun resolveIds(
        nameToIds: Map<String, MutableList<String>>, wanted: List<String>,
    ): Map<String, String> {
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
        return idToChannel
    }

    private fun parseProgramme(
        element: String, idToChannel: Map<String, String>, from: Long, to: Long,
    ): Pair<String, Programme>? {
        val head = element.substring(0, element.indexOf('>').takeIf { it > 0 } ?: return null)
        val channel = attribute(head, "channel")?.let { idToChannel[it] } ?: return null
        val start = parseXmltvDate(attribute(head, "start") ?: return null) ?: return null
        val stop = parseXmltvDate(attribute(head, "stop") ?: return null) ?: return null
        if (stop <= from || start >= to) return null

        val title = text(element, "title")?.takeIf { it.isNotEmpty() } ?: return null
        val category = text(element, "category") ?: ""
        // O pôster vem em atributo, não em texto de elemento.
        val poster = between(element, "<icon", ">")?.let { attribute(it, "src") }?.let(::decodeEntities)
        val episode = text(element, "episode-num")
        val year = text(element, "date")?.take(4)
        val description = text(element, "desc")?.takeIf { it.isNotBlank() }
        // O elenco vem como uma lista de <actor> dentro de <credits>.
        val cast = between(element, "<credits", "</credits>")
            ?.let { bloco ->
                Regex("<actor[^>]*>(.*?)</actor>", RegexOption.DOT_MATCHES_ALL)
                    .findAll(bloco)
                    .map { decodeEntities(it.groupValues[1]).trim() }
                    .filter { it.isNotEmpty() }
                    .toList()
            } ?: emptyList()
        return channel to Programme(title, category, start, stop, poster, episode, year,
                                    description, cast)
    }

    /** `name="value"` out of a tag's attribute text. */
    private fun attribute(text: String, name: String): String? {
        var from = 0
        while (true) {
            val marker = text.indexOf("$name=\"", from)
            if (marker < 0) return null
            // Evita casar o sufixo de outro atributo: "channel" dentro de
            // "xchannel" não é o mesmo campo.
            val before = if (marker == 0) ' ' else text[marker - 1]
            if (before == ' ' || before == '\t' || before == '\n' || before == '<') {
                val start = marker + name.length + 2
                val end = text.indexOf('"', start)
                return if (end < 0) null else text.substring(start, end)
            }
            from = marker + 1
        }
    }

    /** Texto de `<tag …>texto</tag>`, já sem entidades. */
    private fun text(element: String, tag: String): String? {
        val body = between(element, "<$tag", "</$tag>") ?: return null
        val start = body.indexOf('>')
        if (start < 0) return null
        return decodeEntities(body.substring(start + 1)).trim()
    }

    private fun between(text: String, open: String, close: String): String? {
        val start = text.indexOf(open)
        if (start < 0) return null
        val end = text.indexOf(close, start + open.length)
        if (end < 0) return null
        return text.substring(start, end)
    }

    private const val CHANNEL = "channel"
    private const val PROGRAMME = "programme"
    /// Cauda mantida entre blocos para uma tag partida no limite do buffer.
    private const val TAIL = 32
    /// Um elemento maior que isto é lixo, não um programa: sem o teto, um
    /// fechamento que nunca vem cresceria o buffer até estourar a heap.
    private const val MAX_ELEMENT = 1 shl 20

    /**
     * Hands `<channel>` and `<programme>` elements to the caller one at a time,
     * holding at most one element in memory instead of the whole document.
     */
    private inline fun scanElements(reader: Reader, onElement: (String, String) -> Unit) {
        val chunk = CharArray(1 shl 16)
        val pending = StringBuilder()
        var open: String? = null
        while (true) {
            val read = reader.read(chunk)
            if (read < 0) break
            pending.append(chunk, 0, read)
            while (true) {
                val tag = open
                if (tag == null) {
                    val found = nextOpening(pending)
                    if (found == null) {
                        if (pending.length > TAIL) pending.delete(0, pending.length - TAIL)
                        break
                    }
                    pending.delete(0, found.first)
                    open = found.second
                } else {
                    val marker = pending.indexOf("</$tag>")
                    if (marker < 0) {
                        if (pending.length > MAX_ELEMENT) { pending.setLength(0); open = null }
                        break
                    }
                    val end = marker + tag.length + 3
                    onElement(tag, pending.substring(0, end))
                    pending.delete(0, end)
                    open = null
                }
            }
        }
    }

    /** Earliest `<channel` or `<programme` opening in the buffer. */
    private fun nextOpening(buffer: StringBuilder): Pair<Int, String>? {
        val channel = buffer.indexOf("<$CHANNEL")
        val programme = buffer.indexOf("<$PROGRAMME")
        return when {
            channel >= 0 && (programme < 0 || channel < programme) -> channel to CHANNEL
            programme >= 0 -> programme to PROGRAMME
            else -> null
        }
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

    fun download(url: String, referer: String? = null): String =
        openStream(url, referer).use { it.readText() }

    /** Leitor já descomprimido, para quem não quer o documento inteiro na mão. */
    fun openStream(url: String, referer: String? = null): Reader {
        val connection = (URL(url).openConnection() as HttpURLConnection).apply {
            setRequestProperty("User-Agent", Playback.DEFAULT_USER_AGENT)
            setRequestProperty("Accept-Encoding", "gzip")
            referer?.let { setRequestProperty("Referer", it) }
            connectTimeout = 25_000
            readTimeout = 60_000
            instanceFollowRedirects = true
        }
        val raw = connection.inputStream
        val stream = if (connection.contentEncoding?.contains("gzip", true) == true) {
            GZIPInputStream(raw)
        } else raw
        return stream.bufferedReader()
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

    /// Assinatura do catálogo gravada junto do cache. O nome começa com um
    /// caractere que nenhum canal usa, então nunca colide com uma chave de canal.
    private const val SIGNATURE_KEY = "#catalogo"

    /// O "v3" força o cache anterior a ser refeito. Além de sinopse e elenco,
    /// esta versão reconhece a grade do Adult Swim publicada como TruTV.
    private fun signature(): String =
        "v3:" + Remote.channels.joinToString("|") { it.name }.hashCode().toString()

    private fun readSignature(context: Context): String? = runCatching {
        JSONObject(cacheFile(context).readText()).optString(SIGNATURE_KEY).ifEmpty { null }
    }.getOrNull()

    private fun cacheAge(context: Context): Long {
        val file = cacheFile(context)
        return if (file.exists()) System.currentTimeMillis() - file.lastModified() else Long.MAX_VALUE
    }

    private fun readCache(context: Context): Map<String, List<Programme>>? = runCatching {
        val root = JSONObject(cacheFile(context).readText())
        val cutoff = System.currentTimeMillis() - PAST_WINDOW_MS
        val out = HashMap<String, List<Programme>>()
        for (name in root.keys()) {
            if (name == SIGNATURE_KEY) continue
            val array = root.getJSONArray(name)
            val list = (0 until array.length()).map { index ->
                val item = array.getJSONObject(index)
                Programme(item.getString("t"), item.optString("c"),
                    item.getLong("s"), item.getLong("e"),
                    item.optString("p").ifEmpty { null },
                    item.optString("n").ifEmpty { null },
                    item.optString("y").ifEmpty { null },
                    item.optString("d").ifEmpty { null },
                    item.optJSONArray("a")?.let { atores ->
                        (0 until atores.length()).map { atores.getString(it) }
                    } ?: emptyList())
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
                    programme.description?.let { put("d", it) }
                    if (programme.cast.isNotEmpty()) put("a", JSONArray(programme.cast))
                })
            }
            root.put(name, array)
        }
        root.put(SIGNATURE_KEY, signature())
        cacheFile(context).writeText(root.toString())
    }
}
