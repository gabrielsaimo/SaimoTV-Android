package br.com.saimo.tv

import android.content.Context
import androidx.media3.common.util.UnstableApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import java.io.File

data class Filme(val titulo: String, val fontes: Map<String, String>)

data class Serie(val titulo: String, val ano: String, val pedaco: Int, val episodios: Int)

data class Episodio(val temporada: Int, val numero: Int, val versao: String, val url: String)

/**
 * Filmes e séries, baixados por pedaço conforme a pessoa navega.
 *
 * A lista de origem tem 30 MB e 300 mil linhas, o que nenhum TV Box abre. Ela é
 * pré-digerida num catálogo fatiado por letra, e as séries ainda em pedaços
 * dentro da letra, de modo que nenhum download passa de uns 100 KB. Como a tela
 * também navega por letra, o download acompanha o dedo em vez de contrariá-lo.
 */
@UnstableApi
object Vod {

    private const val BASE = "https://raw.githubusercontent.com/gabrielsaimo/SaimoPlayer/main/vod/"

    /** Letra -> quantos filmes e quantas séries começam com ela. */
    data class Gaveta(val letra: String, val filmes: Int, val series: Int)

    @Volatile
    private var baseFilme = ""

    @Volatile
    private var baseSerie = ""

    suspend fun indice(context: Context): List<Gaveta> {
        val texto = arquivo(context, "indice.txt") ?: return emptyList()
        val out = mutableListOf<Gaveta>()
        for (linha in texto.lineSequence()) {
            when {
                linha.startsWith("base-filme:") -> baseFilme = linha.substringAfter(":").trim()
                linha.startsWith("base-serie:") -> baseSerie = linha.substringAfter(":").trim()
                linha.isNotBlank() -> {
                    val campos = linha.split("\t")
                    if (campos.size == 3) {
                        out += Gaveta(campos[0], campos[1].toIntOrNull() ?: 0,
                            campos[2].toIntOrNull() ?: 0)
                    }
                }
            }
        }
        return out
    }

    suspend fun filmes(context: Context, letra: String): List<Filme> {
        val texto = arquivo(context, "filmes-${gaveta(letra)}.txt") ?: return emptyList()
        return texto.lineSequence().mapNotNull { linha ->
            val campos = linha.split("\t")
            if (campos.size < 2 || campos[0].isBlank()) return@mapNotNull null
            val fontes = campos.drop(1).mapNotNull { parte ->
                val marca = parte.indexOf('=')
                if (marca <= 0) null
                else parte.take(marca) to montar(parte.substring(marca + 1), baseFilme)
            }.toMap()
            if (fontes.isEmpty()) null else Filme(campos[0], fontes)
        }.toList()
    }

    suspend fun series(context: Context, letra: String): List<Serie> {
        val texto = arquivo(context, "series-${gaveta(letra)}.txt") ?: return emptyList()
        return texto.lineSequence().mapNotNull { linha ->
            val campos = linha.split("\t")
            if (campos.size < 4 || campos[0].isBlank()) return@mapNotNull null
            Serie(campos[0], campos[1], campos[2].toIntOrNull() ?: 0,
                campos[3].toIntOrNull() ?: 0)
        }.toList()
    }

    /** Episódios de uma série. Baixa só o pedaço em que ela está. */
    suspend fun episodios(context: Context, letra: String, serie: Serie): List<Episodio> {
        val nome = "series-${gaveta(letra)}-${serie.pedaco}.txt"
        val texto = arquivo(context, nome) ?: return emptyList()
        val out = mutableListOf<Episodio>()
        var dentro = false
        for (linha in texto.lineSequence()) {
            if (linha.startsWith("@")) {
                if (dentro) break
                dentro = linha.substring(1) == serie.titulo
                continue
            }
            if (!dentro) continue
            val campos = linha.split("\t")
            if (campos.size < 4) continue
            out += Episodio(
                campos[0].toIntOrNull() ?: 0,
                campos[1].toIntOrNull() ?: 0,
                campos[2],
                montar(campos[3], baseSerie))
        }
        return out
    }

    /// O catálogo guarda só o número; o endereço inteiro sairia setenta vezes
    /// maior e é sempre o mesmo.
    private fun montar(valor: String, base: String): String = when {
        valor.startsWith("http") -> valor
        valor.contains('.') -> base + valor
        else -> "$base$valor.mp4"
    }

    private fun gaveta(letra: String) = if (letra == "#") "%23" else letra

    /**
     * Conteúdo do arquivo, do disco quando já foi baixado.
     *
     * O catálogo muda de vez em quando e nunca no meio de uma navegação, então
     * o que está em disco serve: poupa a rede e faz a segunda visita abrir na
     * hora.
     */
    private suspend fun arquivo(context: Context, nome: String): String? = withContext(Dispatchers.IO) {
        val pasta = File(context.filesDir, "vod").apply { mkdirs() }
        val local = File(pasta, nome.replace("%23", "hash"))
        if (local.exists() && local.length() > 0) return@withContext local.readText()

        val texto = runCatching {
            val request = Request.Builder().url(BASE + nome)
                .header("User-Agent", Playback.DEFAULT_USER_AGENT)
                .build()
            Playback.client.newCall(request).execute().use { resposta ->
                if (!resposta.isSuccessful) null else resposta.body?.string()
            }
        }.getOrNull() ?: return@withContext null

        runCatching { local.writeText(texto) }
        texto
    }
}
