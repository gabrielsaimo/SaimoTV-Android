package br.com.saimo.tv

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.Calendar
import java.util.TimeZone

/**
 * Programme guide scraped from meuguia.tv, mirroring the macOS build.
 *
 * The XMLTV feeds carry the wrong schedule for several channels — HBO was
 * listing a line-up that did not match what was on air — so this takes priority
 * for the channels it covers.
 */
object MeuGuia {

    /** Catalog name -> meuguia channel code. */
    private val CODES = mapOf(
        "A&E" to "MDO", "Animal Planet" to "APL", "Band" to "BAN",
        "Cartoon Network" to "CAR", "Cinemax" to "MNX", "Combate" to "135",
        "Discovery Kids" to "DIK", "Discovery World" to "DIW", "ESPN" to "ESP",
        "ESPN 2" to "ES2", "ESPN 4" to "ES4", "GNT" to "GNT", "Globo RJ" to "GRD",
        "Globo SP" to "GRD", "GloboNews" to "GLN", "Gloob" to "GOB", "HBO" to "HBO",
        "HBO Family" to "HFA", "HBO Plus" to "HPL", "HBO2" to "HB2",
        "History" to "HIS", "Megapix" to "MPX", "Record" to "REC", "SBT" to "SBT",
        "Space" to "SPA", "SporTV 2" to "SP2", "SporTV 3" to "SP3", "TNT" to "TNT",
        "TNT Séries" to "TBS", "Telecine Pipoca" to "TC4",
        "Telecine Premium" to "TC1", "Universal TV" to "USA", "Warner" to "WBT",
    )

    suspend fun fetch(names: List<String>, from: Long, to: Long): Map<String, List<Programme>> =
        coroutineScope {
            names.mapNotNull { name -> CODES[name]?.let { name to it } }
                .map { (name, code) ->
                    async(Dispatchers.IO) {
                        val html = runCatching {
                            Epg.download("https://meuguia.tv/programacao/canal/$code")
                        }.getOrNull()
                        name to (html?.let { parse(it) }?.filter { it.stop > from && it.start < to }
                            ?: emptyList())
                    }
                }
                .awaitAll()
                .filter { it.second.isNotEmpty() }
                .toMap()
        }

    /**
     * A flat list of `<li>` items: day headers followed by their programmes,
     * each with a start time, a title and a genre. End times are not published,
     * so a programme runs until the next one starts.
     *
     * Parsing item by item rather than scanning the whole document keeps ad
     * blocks between entries from swallowing the ones that follow.
     */
    fun parse(html: String): List<Programme> {
        val zone = TimeZone.getTimeZone("America/Sao_Paulo")
        val today = Calendar.getInstance(zone)
        var year = today.get(Calendar.YEAR)
        var month = today.get(Calendar.MONTH) + 1
        var day = today.get(Calendar.DAY_OF_MONTH)
        val currentMonth = month

        val starts = mutableListOf<Long>()
        val titles = mutableListOf<String>()
        val categories = mutableListOf<String>()

        for (item in html.split("<li")) {
            if ("subheader" in item) {
                val text = between(item, ">", "<", after = "subheader") ?: continue
                val parts = text.substringAfter(',', "").trim().split("/")
                if (parts.size == 2) {
                    val d = parts[0].trim().toIntOrNull()
                    val m = parts[1].trim().toIntOrNull()
                    if (d != null && m != null) {
                        day = d
                        month = m
                        // The listing carries no year and can cross January.
                        year = if (m < currentMonth) today.get(Calendar.YEAR) + 1
                        else today.get(Calendar.YEAR)
                    }
                }
                continue
            }
            val clock = between(item, "lileft time'>", "<")
                ?: between(item, "lileft time\">", "<") ?: continue
            val hm = clock.trim().split(":")
            if (hm.size != 2) continue
            val hour = hm[0].toIntOrNull() ?: continue
            val minute = hm[1].toIntOrNull() ?: continue
            val title = between(item, "<h2>", "</h2>")?.let(Epg::decodeEntities)?.trim()
                ?: continue
            if (title.isEmpty()) continue

            val calendar = Calendar.getInstance(zone)
            calendar.clear()
            calendar.set(year, month - 1, day, hour, minute, 0)
            starts += calendar.timeInMillis
            titles += title
            categories += between(item, "<h3>", "</h3>")?.let(Epg::decodeEntities)?.trim() ?: ""
        }

        // nowNext scans in order, and the page's chronology is a property of the
        // layout rather than a guarantee.
        val order = starts.indices.sortedBy { starts[it] }
        return order.mapIndexed { position, index ->
            val stop = if (position + 1 < order.size) starts[order[position + 1]]
            else starts[index] + 3_600_000
            Programme(titles[index], categories[index], starts[index], stop)
        }
    }

    private fun between(text: String, open: String, close: String, after: String? = null): String? {
        var from = 0
        if (after != null) {
            val marker = text.indexOf(after)
            if (marker < 0) return null
            from = marker + after.length
        }
        val start = text.indexOf(open, from)
        if (start < 0) return null
        val end = text.indexOf(close, start + open.length)
        if (end < 0) return null
        return text.substring(start + open.length, end)
    }
}
