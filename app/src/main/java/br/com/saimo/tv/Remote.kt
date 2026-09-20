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

    private const val BASE = "https://raw.githubusercontent.com/gabrielsaimo/SaimoPlayer/main/"
    /// O catálogo inteiro, com chave de ClearKey, Referer e agente. É a lista
    /// que manda: editar este arquivo troca um link sem recompilar nada.
    private const val CATALOG_URL = BASE + "catalogo.txt"
    /// Extras publicados à parte, em M3U. Um M3U não guarda chave nem cabeçalho,
    /// então ele entra como reserva, nunca no lugar do catálogo.
    private const val EXTRAS_URL = BASE + "canais.txt"
    /// Os canais que só aparecem depois do código, publicados à parte para
    /// renomear ou trocar link sem publicar APK novo.
    private const val RESTRITOS_URL = BASE + "restritos.txt"

    @Volatile
    var channels: List<Channel> = CATALOG
        private set

    /// Lista restrita em uso: a baixada, ou a de fábrica.
    @Volatile
    var restritos: List<Channel> = RESTRICTED
        private set

    /** Lista disponível agora, sem tocar na rede. */
    fun loadCached(context: Context) {
        val base = ler(context, "catalogo.txt")
        val extras = ler(context, "canais.txt")
        if (base.isNotEmpty() || extras.isNotEmpty()) channels = merge(base, extras)
        ler(context, "restritos.txt").takeIf { it.isNotEmpty() }?.let { restritos = comoAdulto(it) }
    }

    private fun ler(context: Context, nome: String): List<Channel> {
        val text = runCatching { File(context.filesDir, nome).readText() }.getOrNull()
            ?: return emptyList()
        return parse(text)
    }

    /**
     * Junta as duas listas: o catálogo publicado manda, os extras entram atrás.
     *
     * O catálogo carrega o que um M3U não tem como carregar: a chave
     * do ClearKey dos 42 canais com DRM, o Referer e o User-Agent que certos
     * CDNs exigem, e a ordem em que as fontes devem ser tentadas. Trocar um pelo
     * outro apagaria tudo isso. Casando por nome, cada canal fica com as fontes
     * do catálogo primeiro e as publicadas logo atrás, como reserva; o que só
     * existe na lista publicada entra no fim, como canal novo.
     */
    internal fun merge(base: List<Channel>, published: List<Channel>): List<Channel> {
        val principal = base.ifEmpty { CATALOG }
        val extra = published.associateBy { Epg.normalise(it.name) }
        val usados = mutableSetOf<String>()
        val out = principal.map { channel ->
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
        val base = baixar(context, CATALOG_URL, "catalogo.txt")
        val extras = baixar(context, EXTRAS_URL, "canais.txt")
        val novosRestritos = baixar(context, RESTRITOS_URL, "restritos.txt").map { it }
        var restritosMudaram = false
        if (novosRestritos.isNotEmpty() && comoAdulto(novosRestritos) != restritos) {
            restritos = comoAdulto(novosRestritos)
            restritosMudaram = true
        }
        if (base.isEmpty() && extras.isEmpty()) return@withContext restritosMudaram

        val merged = merge(base.ifEmpty { ler(context, "catalogo.txt") },
                           extras.ifEmpty { ler(context, "canais.txt") })
        if (merged.isEmpty() || merged == channels) return@withContext restritosMudaram
        channels = merged
        true
    }

    /// Baixa e guarda. Devolve vazio quando não veio nada aproveitável, e aí
    /// quem chamou fica com o que já estava em disco.
    private fun baixar(context: Context, url: String, nome: String): List<Channel> {
        val text = runCatching {
            val request = Request.Builder()
                .url(url)
                .header("User-Agent", Playback.DEFAULT_USER_AGENT)
                .header("Cache-Control", "no-cache")
                .build()
            Playback.client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) null else response.body?.string()
            }
        }.getOrNull() ?: return emptyList()

        val parsed = parse(text)
        if (parsed.isEmpty()) return emptyList()
        runCatching { File(context.filesDir, nome).writeText(text) }
        return parsed
    }

    /** Sem categoria declarada, "Brazzers" cairia em Variedades pela regra do nome. */
    private fun comoAdulto(canais: List<Channel>) =
        canais.map { if (it.categoria == null) it.copy(categoria = "Adulto") else it }

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
        var categoria: String? = null
        var sources = mutableListOf<Source>()

        fun flush() {
            val current = name
            if (current != null && sources.isNotEmpty()) {
                out += Channel(current, logo, sources.toList(), categoria)
            }
            name = null
            logo = null
            categoria = null
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
                "categoria" -> categoria = value
                "fonte" -> sources += Source(value)
                "qualidade" -> if (sources.isNotEmpty()) {
                    sources[sources.size - 1] = sources.last().copy(quality = value)
                }
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
    internal fun mergeForTest(published: List<Channel>) = merge(CATALOG, published)
}
