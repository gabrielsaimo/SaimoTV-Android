package br.com.saimo.tv

import android.content.Context
import androidx.media3.common.util.UnstableApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import java.io.File

/// Versão (dublado/legendado) -> fontes em ordem de preferência. O mesmo filme
/// existe nas duas listas de origem, e em vez de aparecer duas vezes ele aparece
/// uma com as duas fontes.
data class Filme(val titulo: String, val fontes: Map<String, List<String>>)

data class Serie(val titulo: String, val ano: String, val pedaco: Int, val episodios: Int) {
    val nomeCompleto: String
        get() = if (ano.isBlank()) titulo else "$titulo ($ano)"
}

data class Episodio(
    val temporada: Int,
    val numero: Int,
    val versao: String,
    val urls: List<String>,
)

data class SerieColecao(
    val titulo: String,
    val ano: String,
    val tmdbId: String,
    val episodios: List<Episodio>,
) {
    val nomeCompleto: String
        get() = if (ano.isBlank()) titulo else "$titulo ($ano)"
}

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

    /** Letra -> quantos filmes, séries e reservados começam com ela. */
    data class Gaveta(val letra: String, val filmes: Int, val series: Int, val reservados: Int)

    @Volatile
    private var bases: List<String> = emptyList()

    /**
     * Sobe quando o formato do catálogo muda.
     *
     * O cache é por arquivo e sobrevive à atualização do app, então um catálogo
     * gravado por uma versão antiga continua sendo lido pela nova — foi assim
     * que um índice sem as linhas `base:` deixou todo filme com endereço
     * quebrado. Guardar a versão junto e limpar a pasta quando ela muda evita
     * que o formato velho envenene o novo.
     */
    private const val VERSAO_CACHE = "4"

    suspend fun indice(context: Context): List<Gaveta> {
        conferirVersao(context)
        val texto = indiceAtual(context) ?: return emptyList()
        val out = mutableListOf<Gaveta>()
        val encontradas = mutableListOf<String>()
        for (linha in texto.lineSequence()) {
            when {
                linha.startsWith("base:") -> {
                    val partes = linha.removePrefix("base:").trim().split(" ", limit = 2)
                    if (partes.size == 2) encontradas += partes[1]
                }
                linha.isNotBlank() -> {
                    val campos = linha.split("\t")
                    if (campos.size >= 3) {
                        out += Gaveta(campos[0], campos[1].toIntOrNull() ?: 0,
                            campos[2].toIntOrNull() ?: 0,
                            campos.getOrNull(3)?.toIntOrNull() ?: 0)
                    }
                }
            }
        }
        if (encontradas.isNotEmpty()) {
            bases = encontradas
            conferirBases(context, encontradas)
        }
        return out
    }

    /**
     * O índice, sempre da rede quando ela responde.
     *
     * Ele é o único arquivo que dá sentido aos outros: cada filme guarda o
     * número da base, não o endereço. Quando a lista de origens é regerada em
     * outra ordem, um índice velho em disco aponta cada filme para o servidor
     * errado e o catálogo inteiro passa a abrir em tela preta. É menos de um
     * kilobyte, então vale buscar de novo a cada abertura e deixar o disco só
     * como reserva para quando a rede falhar.
     */
    private suspend fun indiceAtual(context: Context): String? = withContext(Dispatchers.IO) {
        baixar(context, "indice.txt") ?: run {
            val local = File(File(context.filesDir, "vod"), "indice.txt")
            if (local.exists() && local.length() > 0) local.readText() else null
        }
    }

    /**
     * Apaga as fatias em disco quando as origens mudam.
     *
     * As fatias por letra só fazem sentido junto do índice que as gerou. Se as
     * bases mudaram, o que está guardado aponta para o lugar errado e precisa
     * ser baixado de novo — o índice em si fica, que acabou de chegar.
     */
    private fun conferirBases(context: Context, encontradas: List<String>) {
        val pasta = File(context.filesDir, "vod").apply { mkdirs() }
        val marca = File(pasta, "bases.txt")
        val atual = encontradas.joinToString("\n")
        val anterior = runCatching { marca.readText() }.getOrNull()
        runCatching { marca.writeText(atual) }
        if (anterior == null || anterior == atual) return
        val guardar = setOf("bases.txt", "versao.txt", "indice.txt")
        pasta.listFiles()?.forEach { if (it.name !in guardar) it.delete() }
    }

    suspend fun filmes(context: Context, letra: String, reservados: Boolean = false): List<Filme> {
        val prefixo = if (reservados) "reservado" else "filmes"
        val texto = arquivo(context, "$prefixo-${gaveta(letra)}.txt") ?: return emptyList()
        return texto.lineSequence().mapNotNull { linha ->
            val campos = linha.split("\t")
            if (campos.size < 2 || campos[0].isBlank()) return@mapNotNull null
            val fontes = campos.drop(1).mapNotNull { parte ->
                val marca = parte.indexOf('=')
                if (marca <= 0) return@mapNotNull null
                parte.take(marca) to parte.substring(marca + 1)
                    .split(",").filter { it.isNotBlank() }.map(::montar).filter { it.isNotEmpty() }
            }.filter { it.second.isNotEmpty() }.toMap()
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

    data class Achado(
        val titulo: String,
        val serie: Boolean,
        val letra: String,
        val ano: String = "",
    ) {
        val nomeCompleto: String
            get() = if (ano.isBlank()) titulo else "$titulo ($ano)"
    }

    /**
     * Procura em todo o acervo.
     *
     * O índice traz só nome, tipo e letra — 780 KB para trinta mil títulos —
     * então dá para procurar no acervo inteiro sem baixar o acervo. Ele fica na
     * memória depois da primeira busca, que é quando a pessoa vai fazer a
     * segunda.
     */
    suspend fun buscar(context: Context, termo: String): List<Achado> {
        val alvo = normalizar(termo)
        if (alvo.length < 2) return emptyList()
        val texto = indiceBusca ?: arquivo(context, "busca.txt")?.also { indiceBusca = it }
            ?: return emptyList()
        val out = mutableListOf<Achado>()
        for (linha in texto.lineSequence()) {
            val campos = linha.split("\t")
            if (campos.size < 3) continue
            val ano = campos.getOrNull(3).orEmpty()
            if (!normalizar(campos[0] + " " + ano).contains(alvo)) continue
            out += Achado(campos[0], campos[1] == "s", campos[2], ano)
            if (out.size >= 200) break
        }
        return out
    }

    @Volatile
    private var indiceBusca: String? = null

    private fun normalizar(texto: String): String =
        java.text.Normalizer.normalize(texto, java.text.Normalizer.Form.NFD)
            .replace(Regex("\\p{Mn}+"), "")
            .lowercase()
            .replace(Regex("[^a-z0-9]+"), " ")
            .trim()

    /** Um filme específico, pelo nome, dentro da letra dele. */
    suspend fun filme(context: Context, achado: Achado): Filme? =
        filmes(context, achado.letra).firstOrNull { it.titulo == achado.titulo }

    /** Uma série específica, pelo nome, dentro da letra dela. */
    suspend fun serie(context: Context, achado: Achado): Serie? =
        series(context, achado.letra).firstOrNull {
            it.titulo == achado.titulo && (achado.ano.isBlank() || it.ano == achado.ano)
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
                val identidade = linha.substring(1).split("\t", limit = 2)
                dentro = identidade[0] == serie.titulo &&
                    identidade.getOrNull(1).orEmpty() == serie.ano
                continue
            }
            if (!dentro) continue
            val campos = linha.split("\t")
            if (campos.size < 4) continue
            val urls = campos[3].split(",").filter { it.isNotBlank() }.map(::montar).filter { it.isNotEmpty() }
            if (urls.isEmpty()) continue
            out += Episodio(
                campos[0].toIntOrNull() ?: 0,
                campos[1].toIntOrNull() ?: 0,
                campos[2],
                urls)
        }
        return out
    }

    /** Coleções pequenas publicadas pelo gerador RedeFlix, já com episódios. */
    suspend fun colecao(context: Context, tipo: String): List<SerieColecao> {
        require(tipo == "animes" || tipo == "doramas")
        if (bases.isEmpty()) indice(context)
        val nome = "redeflix/links-$tipo.txt"
        // Essas listas mudam semanalmente; tenta a rede antes do cache local.
        val texto = withContext(Dispatchers.IO) { baixar(context, nome) }
            ?: arquivo(context, nome) ?: return emptyList()
        data class Parcial(
            val titulo: String,
            val ano: String,
            val tmdbId: String,
            val episodios: MutableList<Episodio> = mutableListOf(),
        )
        val out = mutableListOf<Parcial>()
        var atual: Parcial? = null
        for (linha in texto.lineSequence()) {
            if (linha.isBlank()) continue
            if (linha.startsWith("@")) {
                val campos = linha.drop(1).split("\t")
                val titulo = campos.getOrNull(0).orEmpty().trim()
                atual = if (titulo.isBlank()) null else Parcial(
                    titulo, campos.getOrNull(1).orEmpty().trim(),
                    campos.getOrNull(2).orEmpty().trim()).also(out::add)
                continue
            }
            val destino = atual ?: continue
            val campos = linha.split("\t")
            if (campos.size < 4) continue
            val urls = campos[3].split(",").map(String::trim).map(::montar)
                .filter(String::isNotEmpty)
            if (urls.isEmpty()) continue
            destino.episodios += Episodio(
                campos[0].toIntOrNull() ?: 0,
                campos[1].toIntOrNull() ?: 0,
                campos[2].ifBlank { "dub" },
                urls)
        }
        return out.filter { it.episodios.isNotEmpty() }
            .map { SerieColecao(it.titulo, it.ano, it.tmdbId, it.episodios) }
    }

    /// O item guarda "base:resto"; o endereço inteiro sairia dezenas de vezes
    /// maior, e o começo é sempre o mesmo punhado de servidores.
    private fun montar(valor: String): String {
        if (valor.startsWith("http")) return valor
        // Sem a base o que sobra é "0:19927", que só falha na hora de tocar.
        // Melhor devolver vazio e deixar a fonte de fora.
        val corte = valor.indexOf(':')
        val indice = valor.take(corte).toIntOrNull() ?: return ""
        val resto = valor.substring(corte + 1)
        val base = bases.getOrNull(indice) ?: return ""
        return if (resto.contains('.')) base + resto else "$base$resto.mp4"
    }

    private fun conferirVersao(context: Context) {
        val pasta = File(context.filesDir, "vod").apply { mkdirs() }
        val marca = File(pasta, "versao.txt")
        val atual = runCatching { marca.readText() }.getOrNull()
        if (atual == VERSAO_CACHE) return
        pasta.listFiles()?.forEach { it.delete() }
        runCatching { marca.writeText(VERSAO_CACHE) }
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
        local.parentFile?.mkdirs()
        if (local.exists() && local.length() > 0) return@withContext local.readText()
        baixar(context, nome)
    }

    /** Busca o arquivo na rede e guarda em disco. */
    private fun baixar(context: Context, nome: String): String? {
        val pasta = File(context.filesDir, "vod").apply { mkdirs() }
        val local = File(pasta, nome.replace("%23", "hash"))
        local.parentFile?.mkdirs()
        val texto = runCatching {
            val request = Request.Builder().url(BASE + nome)
                .header("User-Agent", Playback.DEFAULT_USER_AGENT)
                .build()
            Playback.client.newCall(request).execute().use { resposta ->
                if (!resposta.isSuccessful) null else resposta.body?.string()
            }
        }.getOrNull() ?: return null

        runCatching { local.writeText(texto) }
        return texto
    }
}
