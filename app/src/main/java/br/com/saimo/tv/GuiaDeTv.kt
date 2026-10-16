package br.com.saimo.tv

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

/**
 * Guia de programação extraído de guiadetv.com.
 *
 * Cobre 121+ canais com horários precisos no fuso de Brasília, sem bloqueio 429
 * e trazendo a sinopse em português diretamente na página.
 * É a fonte primária rápida do aplicativo.
 */
object GuiaDeTv {

    /** Nome normalizado -> slug do guiadetv.com. */
    private val CODES = mapOf(
        "a e" to "ae",
        "ae" to "ae",
        "agro" to "agro",
        "agro mais" to "agro",
        "canal agro" to "agro",
        "amc" to "amc",
        "animal planet" to "animal-planet",
        "arte 1" to "arte-1",
        "arte1" to "arte-1",
        "axn" to "axn",
        "band" to "band",
        "band sp" to "band",
        "band campinas" to "band",
        "band curitiba" to "band",
        "band mg" to "band",
        "band para" to "band",
        "band recife tv tribuna" to "band",
        "band rn" to "band",
        "band rs" to "band",
        "band sergipe" to "band",
        "band rj" to "band-rj",
        "band news" to "bandnews",
        "bandnews" to "bandnews",
        "band sports" to "bandsports",
        "bandsports" to "bandsports",
        "bbc world" to "bbc-world",
        "bbc world news" to "bbc-world",
        "bis" to "bis",
        "bloomberg" to "bloomberg",
        "box kids" to "box-kids",
        "box kids tv" to "box-kids",
        "canal educacao" to "canal-educacao",
        "canal rural" to "canal-rural",
        "canal uol" to "canal-uol",
        "cancao nova" to "cancao-nova",
        "cartoon network" to "cartoon-network",
        "cartoonito" to "cartoonito",
        "cinemax" to "cinemax",
        "cnn brasil" to "cnn-brasil",
        "combate" to "combate",
        "curta" to "curta",
        "discovery channel" to "discovery-channel",
        "discovery home health" to "discovery-home-health",
        "discovery h h" to "discovery-home-health",
        "discovery kids" to "discovery-kids",
        "discovery science" to "discovery-science",
        "discovery theater" to "discovery-theater",
        "discovery turbo" to "discovery-turbo",
        "discovery world" to "discovery-world",
        "discovery id" to "investigacao-discovery",
        "investigacao discovery" to "investigacao-discovery",
        "investigation discovery" to "investigacao-discovery",
        "e" to "e",
        "espn" to "espn",
        "espn 2" to "espn-2",
        "espn 3" to "espn-3",
        "espn 4" to "espn-4",
        "espn 5" to "espn-5",
        "espn 6" to "espn-6",
        "film arts" to "film-arts",
        "food network" to "food-network",
        "fox news" to "fox-news",
        "futura" to "futura",
        "canal futura" to "futura",
        "ge tv" to "ge-tv",
        "getv" to "ge-tv",
        "globo news" to "globo-news",
        "globonews" to "globo-news",
        "globo rj" to "globo-rio-de-janeiro",
        "globo rio de janeiro" to "globo-rio-de-janeiro",
        "globo" to "globo-sp",
        "globo sp" to "globo-sp",
        "globo am" to "globo-sp",
        "globo ce" to "globo-sp",
        "globo df" to "globo-sp",
        "globo es" to "globo-sp",
        "globo go" to "globo-sp",
        "globo mg" to "globo-sp",
        "globo pb" to "globo-sp",
        "globo pe" to "globo-sp",
        "globo rs" to "globo-sp",
        "gloob" to "gloob",
        "gloobinho" to "gloobinho",
        "gnt" to "gnt",
        "hbo" to "hbo",
        "hbo 2" to "hbo-2",
        "hbo2" to "hbo-2",
        "hbo family" to "hbo-family",
        "hbo mundi" to "hbo-mundi",
        "hbo plus" to "hbo-plus",
        "hbo pop" to "hbo-pop",
        "hbo signature" to "hbo-signature",
        "hbo xtreme" to "hbo-xtreme",
        "hgtv" to "hgtv",
        "history" to "history",
        "history channel" to "history",
        "history 2" to "history-2",
        "h2" to "history-2",
        "jovem pan news" to "jovem-pan-news",
        "lifetime" to "lifetime",
        "lifetime movies" to "lifetime",
        "megapix" to "megapix",
        "multishow" to "multishow",
        "music box brazil" to "music-box-brazil",
        "n sports" to "nsports",
        "nsports" to "nsports",
        "n sports jogo 1" to "nsports",
        "off" to "off",
        "canal off" to "off",
        "premiere" to "premiere-fc",
        "premiere clubes" to "premiere-fc",
        "premiere fc" to "premiere-fc",
        "premiere 2" to "premiere-2",
        "premiere 3" to "premiere-3",
        "premiere 4" to "premiere-4",
        "premiere 5" to "premiere-5",
        "premiere 6" to "premiere-6",
        "premiere 7" to "premiere-7",
        "prime box brazil" to "prime-box-brazil",
        "record news" to "record-news",
        "record" to "record-tv",
        "record tv" to "record-tv",
        "record sp" to "record-tv",
        "record go" to "record-tv",
        "record mg" to "record-tv",
        "record rj" to "record-tv",
        "record internacional" to "record-tv",
        "recordtv" to "record-tv",
        "rede brasil" to "rede-brasil",
        "rede familia" to "rede-familia",
        "rede gospel" to "rede-gospel",
        "redetv" to "rede-tv",
        "rede tv" to "rede-tv",
        "rede vida" to "rede-vida",
        "sbt" to "sbt",
        "sbt sp" to "sbt",
        "sbt alterosa" to "sbt",
        "sbt ba" to "sbt",
        "sbt go" to "sbt",
        "sbt interior" to "sbt",
        "sbt londrina" to "sbt",
        "sbt maringa" to "sbt",
        "sbt mg" to "sbt",
        "sbt pi" to "sbt",
        "sbt pr" to "sbt",
        "sbt news" to "sbt-news",
        "sbt rj" to "sbt-rio-de-janeiro",
        "sbt rio de janeiro" to "sbt-rio-de-janeiro",
        "sony" to "sony",
        "sony channel" to "sony",
        "sony movies" to "sony-movies",
        "space" to "space",
        "sportv" to "sportv",
        "sportv 2" to "sportv-2",
        "sportv2" to "sportv-2",
        "sportv 3" to "sportv-3",
        "sportv3" to "sportv-3",
        "sportynet" to "sportynet",
        "sportynet 01" to "sportynet",
        "sportynet 4" to "sportynet",
        "sportynet 1" to "sportynet",
        "sportynet 2" to "sportynet",
        "sportynet 3" to "sportynet",
        "studio universal" to "studio-universal",
        "syfy" to "syfy",
        "usa network" to "syfy",
        "tcm" to "tcm",
        "telecine action" to "telecine-action",
        "telecine cult" to "telecine-cult",
        "telecine fun" to "telecine-fun",
        "telecine pipoca" to "telecine-pipoca",
        "telecine premium" to "telecine-premium",
        "telecine touch" to "telecine-touch",
        "terra viva" to "terra-viva",
        "tlc" to "tlc",
        "tnt" to "tnt",
        "tnt novelas" to "tnt-novelas",
        "tnt series" to "tnt-series",
        "tooncast" to "tooncast",
        "travel box brazil" to "travel-box-brasil",
        "travel box brasil" to "travel-box-brasil",
        "trutv" to "trutv",
        "adult swim" to "trutv",
        "tv aparecida" to "tv-aparecida",
        "tv brasil" to "tv-brasil",
        "tv camara" to "tv-camara",
        "tv cultura" to "tv-cultura",
        "cultura" to "tv-cultura",
        "tv gazeta" to "tv-gazeta",
        "gazeta" to "tv-gazeta",
        "tv ra tim bum" to "tv-ra-tim-bum",
        "ra tim bum" to "tv-ra-tim-bum",
        "tv senado" to "tv-senado",
        "universal tv" to "universal-tv",
        "universal channel" to "universal-tv",
        "viva" to "viva",
        "canal viva" to "viva",
        "globoplay novelas" to "viva",
        "warner" to "warner",
        "warner channel" to "warner",
        "woohoo" to "woohoo",
        "x sports" to "xsports",
        "xsports" to "xsports",
    )

    private val SUFIXO = Regex("""\s*\((?:s\d+|ot|rk|b\d+|alt)\)\s*$""", RegexOption.IGNORE_CASE)

    fun slug(name: String): String? {
        val clean = name.replace(SUFIXO, "")
        val key = Epg.normalise(clean)
        return CODES[key] ?: CODES[Epg.normalise(name)]
    }

    /// Em aparelho de 1 GB, 2 por vez; nos demais, 6.
    private val gate by lazy { Semaphore(if (Aparelho.poucaMemoria) 2 else 6) }

    suspend fun fetch(names: List<String>, from: Long, to: Long): Map<String, List<Programme>> =
        coroutineScope {
            // Agrupar canais por slug para baixar cada página apenas uma vez
            val slugToNames = LinkedHashMap<String, MutableList<String>>()
            for (name in names) {
                val s = slug(name) ?: continue
                slugToNames.getOrPut(s) { ArrayList(1) }.add(name)
            }
            if (slugToNames.isEmpty()) return@coroutineScope emptyMap()

            val results = slugToNames.keys.map { slug ->
                async(Dispatchers.IO) {
                    val lista = gate.withPermit {
                        runCatching {
                            parse(Epg.download("https://www.guiadetv.com/canal/$slug"))
                                .filter { it.stop > from && it.start < to }
                        }.getOrNull()
                    } ?: emptyList()
                    slug to lista
                }
            }.awaitAll()

            val out = LinkedHashMap<String, List<Programme>>()
            for ((slug, lista) in results) {
                if (lista.isEmpty()) continue
                val canais = slugToNames[slug] ?: continue
                for (name in canais) {
                    out[name] = lista
                }
            }
            out
        }

    /**
     * `https://www.guiadetv.com/canal/<slug>`
     * Cada programa é um `<li class="flex gap-4 ...">` contendo:
     * - `<time datetime="AAAA-MM-DDTHH:MM:SS-03:00">HH:MM</time>`
     * - `<span class="text-xs ...">1h30</span>`
     * - `<a href="/programa/..." ...>Título</a>`
     * - `<p class="mt-1 ...">Sinopse...</p>`
     *
     * Feito por split("<li") sem regex sobre o documento inteiro, evitando
     * consumo excessivo de memória nativa do ICU em TV Box com pouca RAM.
     */
    fun parse(html: String): List<Programme> {
        val itens = html.split("<li")
        data class ItemBruto(val start: Long, val duration: Long, val title: String, val desc: String?)
        val lista = ArrayList<ItemBruto>(itens.size)

        for (i in 1 until itens.size) {
            val item = itens[i]
            val dt = between(item, "datetime=\"", "\"", "<time") ?: continue
            if (dt.length < 19) continue

            val ano = dt.substring(0, 4).toIntOrNull() ?: continue
            val mes = dt.substring(5, 7).toIntOrNull() ?: continue
            val dia = dt.substring(8, 10).toIntOrNull() ?: continue
            val hora = dt.substring(11, 13).toIntOrNull() ?: continue
            val minuto = dt.substring(14, 16).toIntOrNull() ?: continue

            // Fuso de Brasília (-03:00) convertido em millis UTC
            val start = (Epg.diasDesde1970(ano, mes, dia) * 86_400L + (hora + 3) * 3_600L + minuto * 60L) * 1000L

            val rawTitle = between(item, ">", "</a>", "/programa/") ?: continue
            val title = Epg.decodeEntities(rawTitle).trim().replace(Regex("""\s+"""), " ")
            if (title.length < 2) continue

            var duration = 3_600_000L
            val durText = between(item, ">", "</span>", "</time>")
            if (durText != null) {
                val t = durText.trim()
                if ('h' in t) {
                    val parts = t.split("h")
                    val h = parts.getOrNull(0)?.toIntOrNull() ?: 0
                    val m = parts.getOrNull(1)?.toIntOrNull() ?: 0
                    duration = (h * 60L + m) * 60_000L
                } else if (t.endsWith("min")) {
                    val m = t.removeSuffix("min").trim().toIntOrNull() ?: 0
                    if (m > 0) duration = m * 60_000L
                }
            }

            val desc = between(item, ">", "</p>", "<p")?.let {
                Epg.decodeEntities(it).trim().replace(Regex("""\s+"""), " ").ifEmpty { null }
            }

            lista += ItemBruto(start, duration, title, desc)
        }

        lista.sortBy { it.start }

        // Deduplicar mesmo horário de início
        val unicos = ArrayList<ItemBruto>(lista.size)
        for (item in lista) {
            val last = unicos.lastOrNull()
            if (last != null && last.start == item.start) continue
            unicos += item
        }

        return unicos.mapIndexed { index, item ->
            var stop = item.start + item.duration
            val next = unicos.getOrNull(index + 1)
            if (next != null) {
                if (stop > next.start || kotlin.math.abs(stop - next.start) <= 60_000L) {
                    stop = next.start
                }
            }
            Programme(
                title = item.title,
                category = "",
                start = item.start,
                stop = if (stop > item.start) stop else item.start + 3_600_000L,
                description = item.desc
            )
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
