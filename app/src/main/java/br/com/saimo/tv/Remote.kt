package br.com.saimo.tv

import android.content.Context
import androidx.media3.common.util.UnstableApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import java.io.File

/**
 * The channel line-up, downloaded on every launch.
 *
 * Publishing the list means a dead link is fixed by editing one file, with
 * nothing to rebuild or reinstall. The built-in catalogue stays as the floor:
 * the app opens on the last list it managed to read — cached from the previous
 * run, or the compiled one — and swaps in the fresh one when it arrives, so a
 * missing network never costs the viewer their channels.
 */
@UnstableApi
object Remote {

    private const val URL =
        "https://raw.githubusercontent.com/gabrielsaimo/SaimoPlayer/main/canais.txt"

    @Volatile
    var channels: List<Channel> = CATALOG
        private set

    /** Lista disponível agora, sem tocar na rede. */
    fun loadCached(context: Context) {
        val text = runCatching { cacheFile(context).readText() }.getOrNull() ?: return
        val parsed = parse(text)
        if (parsed.isNotEmpty()) channels = merge(parsed)
    }

    /**
     * Junta a lista publicada ao catálogo em vez de trocar um pelo outro.
     *
     * O catálogo carrega o que a lista publicada não tem como carregar: a chave
     * do ClearKey dos 42 canais com DRM, o Referer e o User-Agent que certos
     * CDNs exigem, e a ordem em que as fontes devem ser tentadas. Trocar um pelo
     * outro apagaria tudo isso. Casando por nome, cada canal fica com as fontes
     * do catálogo primeiro e as publicadas logo atrás, como reserva; o que só
     * existe na lista publicada entra no fim, como canal novo.
     */
    internal fun merge(published: List<Channel>): List<Channel> {
        val extra = published.associateBy { Epg.normalise(it.name) }
        val usados = mutableSetOf<String>()
        val out = CATALOG.map { channel ->
            val chave = Epg.normalise(channel.name)
            val vindas = extra[chave] ?: return@map channel
            usados += chave
            val conhecidas = channel.sources.map { it.url }.toSet()
            val novas = vindas.sources.filter { it.url !in conhecidas }
            if (novas.isEmpty()) channel else channel.copy(sources = channel.sources + novas)
        }
        return out + published.filter { Epg.normalise(it.name) !in usados }
    }

    /**
     * Baixa a lista publicada. Devolve falso quando não conseguiu ler algo
     * aproveitável, e aí quem chamou fica com o que já tinha em vez de esvaziar
     * a lista.
     */
    suspend fun refresh(context: Context): Boolean = withContext(Dispatchers.IO) {
        val text = runCatching {
            val request = Request.Builder()
                .url(URL)
                .header("User-Agent", Playback.DEFAULT_USER_AGENT)
                .header("Cache-Control", "no-cache")
                .build()
            Playback.client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) null else response.body?.string()
            }
        }.getOrNull() ?: return@withContext false

        val parsed = parse(text)
        if (parsed.isEmpty()) return@withContext false
        val merged = merge(parsed)
        runCatching { cacheFile(context).writeText(text) }
        if (merged == channels) return@withContext false
        channels = merged
        true
    }

    /**
     * One `chave: valor` per line. `canal:` opens a channel, `fonte:` adds a
     * source, and referer/agente/chave belong to the source above them.
     */
    fun parse(text: String): List<Channel> {
        if (text.trimStart().startsWith("#EXTM3U")) {
            return parseM3u(text)
        }

        val out = mutableListOf<Channel>()
        var name: String? = null
        var logo: String? = null
        var sources = mutableListOf<Source>()

        fun flush() {
            val current = name
            if (current != null && sources.isNotEmpty()) {
                out += Channel(current, logo, sources.toList())
            }
            name = null
            logo = null
            sources = mutableListOf()
        }

        for (raw in text.lineSequence()) {
            val line = raw.trim()
            if (line.isEmpty() || line.startsWith("#")) continue
            val colon = line.indexOf(':')
            if (colon <= 0) continue
            val field = line.substring(0, colon).lowercase()
            val value = line.substring(colon + 1).trim()
            if (value.isEmpty()) continue

            when (field) {
                "canal" -> { flush(); name = value }
                "logo" -> logo = value
                "fonte" -> sources += Source(value)
                "referer" -> if (sources.isNotEmpty()) {
                    sources[sources.size - 1] = sources.last().copy(referer = value)
                }
                "agente" -> if (sources.isNotEmpty()) {
                    sources[sources.size - 1] = sources.last().copy(userAgent = value)
                }
                // KID:CHAVE. O ExoPlayer precisa dos dois; sem o KID a licença
                // não é montável, então a fonte é descartada em vez de tocar mudo.
                "chave" -> if (sources.isNotEmpty()) {
                    val parts = value.split(":")
                    if (parts.size == 2 && parts.all { it.isNotBlank() }) {
                        sources[sources.size - 1] =
                            sources.last().copy(keyId = parts[0].trim(), key = parts[1].trim())
                    } else {
                        sources.removeAt(sources.size - 1)
                    }
                }
            }
        }
        flush()
        return withKnownLogos(out)
    }

    private fun parseM3u(text: String): List<Channel> {
        val out = mutableMapOf<String, Channel>()
        
        var currentTvgId: String? = null
        var currentName: String? = null
        var currentLogo: String? = null

        for (raw in text.lineSequence()) {
            val line = raw.trim()
            if (line.isEmpty() || line == "#EXTM3U") continue

            if (line.startsWith("#EXTINF:")) {
                val idMatch = "tvg-id=\"([^\"]+)\"".toRegex().find(line)
                currentTvgId = idMatch?.groupValues?.get(1)?.takeIf { it.isNotBlank() }

                val logoMatch = "tvg-logo=\"([^\"]+)\"".toRegex().find(line)
                currentLogo = logoMatch?.groupValues?.get(1)

                var inQuotes = false
                var commaIdx = -1
                for (i in line.indices) {
                    if (line[i] == '"') inQuotes = !inQuotes
                    if (line[i] == ',' && !inQuotes) {
                        commaIdx = i
                        break
                    }
                }
                val rawName = if (commaIdx != -1) line.substring(commaIdx + 1).trim() else line.substringAfterLast(",").trim()
                
                // Group by tvg-id if available, otherwise use the raw name without trailing parens (e.g., "(NX)")
                currentName = currentTvgId ?: rawName.replace(Regex("\\s*\\([^)]+\\)$"), "").trim()
            } else if (!line.startsWith("#")) {
                if (currentName != null) {
                    val existing = out[currentName!!]
                    if (existing != null) {
                        val updatedSources = existing.sources.toMutableList().apply { add(Source(line)) }
                        out[currentName!!] = existing.copy(sources = updatedSources, logo = existing.logo ?: currentLogo)
                    } else {
                        out[currentName!!] = Channel(currentName!!, currentLogo, listOf(Source(line)))
                    }
                }
            }
        }
        return withKnownLogos(out.values.toList())
    }

    /**
     * A published entry without a `logo:` line falls back to the compiled one.
     * The list is edited by hand, and a channel losing its icon because a line
     * was dropped would be a silent regression on every screen at once.
     */
    private fun withKnownLogos(channels: List<Channel>): List<Channel> {
        val known = CATALOG.mapNotNull { c -> c.logo?.let { c.name.lowercase() to it } }.toMap()
        return channels.map { channel ->
            if (channel.logo != null) channel
            else known[channel.name.lowercase()]?.let { channel.copy(logo = it) } ?: channel
        }
    }

    /** Só para o teste alcançar o merge sem abrir o objeto todo. */
    internal fun mergeForTest(published: List<Channel>) = merge(published)

    private fun cacheFile(context: Context) = File(context.filesDir, "canais.txt")
}
