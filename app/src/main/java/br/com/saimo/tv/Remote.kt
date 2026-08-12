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
        if (parsed.isNotEmpty()) channels = parsed
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
        if (parsed.isEmpty() || parsed == channels) return@withContext false
        channels = parsed
        runCatching { cacheFile(context).writeText(text) }
        true
    }

    /**
     * One `chave: valor` per line. `canal:` opens a channel, `fonte:` adds a
     * source, and referer/agente/chave belong to the source above them.
     */
    fun parse(text: String): List<Channel> {
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

    private fun cacheFile(context: Context) = File(context.filesDir, "canais.txt")
}
