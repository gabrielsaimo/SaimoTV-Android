package br.com.saimo.tv

import android.content.Context
import androidx.media3.common.util.UnstableApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
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

    internal const val BASE = "https://raw.githubusercontent.com/gabrielsaimo/SaimoPlayer/main/vod/"

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

    suspend fun filmes(context: Context, letra: String, reservados: Boolean = false,
                       fresco: Boolean = false): List<Filme> {
        val prefixo = if (reservados) "reservado" else "filmes"
        val texto = arquivo(context, "$prefixo-${gaveta(letra)}.txt", fresco) ?: return emptyList()
        return texto.lineSequence().mapNotNull { linha ->
            val campos = linha.split("\t")
            if (campos.size < 2 || campos[0].isBlank()) return@mapNotNull null
            val fontes = campos.drop(1).mapNotNull { parte ->
                val marca = parte.indexOf('=')
                if (marca <= 0) return@mapNotNull null
                parte.take(marca) to FontesDesativadas.peneirar(
                    parte.substring(marca + 1)
                        .split(",").filter { it.isNotBlank() }.map(::montar).filter { it.isNotEmpty() })
            }.filter { it.second.isNotEmpty() }.toMap()
            if (fontes.isEmpty()) null else Filme(campos[0], fontes)
        }.toList()
    }

    suspend fun series(context: Context, letra: String, fresco: Boolean = false): List<Serie> {
        val texto = arquivo(context, "series-${gaveta(letra)}.txt", fresco) ?: return emptyList()
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
    /**
     * Os nomes do acervo comum, para peneirar listas locais.
     *
     * Os extras ficam de fora do índice de busca de propósito — o que não
     * aparece sem o código também não pode aparecer numa busca comum. Quem
     * monta uma fileira a partir do que está gravado no aparelho, como o
     * "continue assistindo", precisa da mesma peneira: a tela inicial abre sem
     * código nenhum e não pode ser por onde um título reservado reaparece.
     */
    suspend fun nomesDoAcervo(context: Context): Set<String> =
        entradas(context).mapTo(HashSet()) { it.titulo }

    /**
     * O acervo inteiro de um tipo, sem passar por letra.
     *
     * O catálogo é publicado por letra porque nenhum TV Box abre um arquivo de
     * vinte megabytes — mas o índice de busca tem todos os nomes, e é dele que
     * sai a lista completa. Abrir um título continua indo ao arquivo da letra.
     */
    suspend fun todos(context: Context, serie: Boolean): List<Achado> =
        entradas(context).filter { it.serie == serie }.map { it.achado }

    /**
     * Uma linha do índice, já normalizada.
     *
     * Normalizar é o caro: acento, caixa e pontuação saem com expressão
     * regular. Antes isso acontecia em cada linha a cada busca — dezenas de
     * milhares de vezes por tecla. Agora acontece uma vez, na primeira busca,
     * e as seguintes só comparam texto.
     */
    class Entrada(val achado: Achado, val chave: String, val palavras: List<String>) {
        val titulo get() = achado.titulo
        val serie get() = achado.serie
    }

    @Volatile
    private var indice: List<Entrada>? = null

    suspend fun entradas(context: Context): List<Entrada> = withContext(Dispatchers.IO) {
        indice?.let { return@withContext it }
        val texto = arquivo(context, "busca.txt") ?: return@withContext emptyList()
        val out = ArrayList<Entrada>(50_000)
        for (linha in texto.lineSequence()) {
            val campos = linha.split("\t")
            if (campos.size < 3 || campos[0].isBlank()) continue
            // Linha sem nome, só com ano (" (2024)"), é defeito do gerador.
            if (Generos.semAno(campos[0]).isBlank()) continue
            val ano = campos.getOrNull(3).orEmpty()
            val achado = Achado(campos[0], campos[1] == "s", campos[2], ano)
            val chave = normalizar(Generos.semAno(campos[0]))
            out += Entrada(achado, chave, chave.split(' ').filter { it.isNotEmpty() })
        }
        indice = out
        out
    }

    /**
     * Procura no acervo inteiro, do mais parecido para o menos.
     *
     * Nome igual vem primeiro, depois o que começa pelo termo, depois o que
     * tem uma palavra começando por ele, e por fim o que só contém. Se isso
     * não achar quase nada, as palavras valem em qualquer ordem e com uma letra
     * trocada — "vingadores ultimato", "avatr", "coracao".
     */
    suspend fun buscar(context: Context, termo: String, limite: Int = 120): List<Achado> =
        withContext(Dispatchers.Default) {
            val alvo = normalizar(termo)
            if (alvo.isEmpty()) return@withContext emptyList()
            val pedacos = alvo.split(' ').filter { it.isNotEmpty() }
            val pontos = ArrayList<Pair<Int, Entrada>>()
            for (entrada in entradas(context)) {
                val p = pontuar(entrada, alvo, pedacos)
                if (p < Int.MAX_VALUE) pontos += p to entrada
            }
            if (pontos.size < 8 && alvo.length >= 4) {
                val ja = pontos.mapTo(HashSet()) { it.second }
                for (entrada in entradas(context)) {
                    if (entrada in ja) continue
                    if (pedacos.all { p -> entrada.palavras.any { quase(it, p) } }) pontos += 50 to entrada
                }
            }
            pontos.sortWith(compareBy({ it.first }, { it.second.chave.length }))
            pontos.take(limite).map { it.second.achado }
        }

    private fun pontuar(e: Entrada, alvo: String, pedacos: List<String>): Int = when {
        e.chave == alvo -> 0
        e.chave.startsWith(alvo) -> 1
        e.chave.contains(" $alvo") -> 2
        e.chave.contains(alvo) -> 3
        pedacos.size > 1 && pedacos.all { p -> e.palavras.any { it.startsWith(p) } } -> 4
        else -> Int.MAX_VALUE
    }

    /** Uma letra a mais, a menos ou trocada — o erro de quem digita no controle. */
    private fun quase(palavra: String, termo: String): Boolean {
        if (termo.length < 4) return palavra.startsWith(termo)
        if (palavra.startsWith(termo)) return true
        val a = if (palavra.length > termo.length + 1) palavra.take(termo.length + 1) else palavra
        if (kotlin.math.abs(a.length - termo.length) > 1) return false
        var i = 0; var j = 0; var erros = 0
        while (i < a.length && j < termo.length) {
            if (a[i] == termo[j]) { i++; j++; continue }
            if (++erros > 1) return false
            when {
                a.length > termo.length -> i++
                a.length < termo.length -> j++
                else -> { i++; j++ }
            }
        }
        return erros + (a.length - i) + (termo.length - j) <= 1
    }

    private val MARCAS = Regex("\\p{Mn}+")
    private val NAO_ALFANUMERICO = Regex("[^a-z0-9]+")

    fun normalizar(texto: String): String =
        NAO_ALFANUMERICO.replace(
            MARCAS.replace(java.text.Normalizer.normalize(texto, java.text.Normalizer.Form.NFD), "")
                .lowercase(), " ").trim()

    /** Um filme específico, pelo nome, dentro da letra dele. */
    suspend fun filme(context: Context, achado: Achado): Filme? =
        filmes(context, achado.letra).firstOrNull { it.titulo == achado.titulo }
            // O título é novo e a letra em disco é de antes dele: baixa de novo.
            ?: filmes(context, achado.letra, fresco = true).firstOrNull { it.titulo == achado.titulo }

    /** Uma série específica, pelo nome, dentro da letra dela. */
    suspend fun serie(context: Context, achado: Achado): Serie? {
        val igual = { it: Serie -> it.titulo == achado.titulo && (achado.ano.isBlank() || it.ano == achado.ano) }
        return series(context, achado.letra).firstOrNull(igual)
            ?: series(context, achado.letra, fresco = true).firstOrNull(igual)
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
            val urls = FontesDesativadas.peneirar(
                campos[3].split(",").filter { it.isNotBlank() }.map(::montar).filter { it.isNotEmpty() })
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
        colecoes[tipo]?.let { return it }
        val nome = "redeflix/links-$tipo.txt"
        // Uma vez por abertura do app: antes cada tela que tocava na coleção
        // baixava o arquivo inteiro de novo. O disco é conferido por trás.
        val texto = arquivo(context, nome) ?: return emptyList()
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
            val urls = FontesDesativadas.peneirar(
                campos[3].split(",").map(String::trim).map(::montar)
                    .filter(String::isNotEmpty))
            if (urls.isEmpty()) continue
            destino.episodios += Episodio(
                campos[0].toIntOrNull() ?: 0,
                campos[1].toIntOrNull() ?: 0,
                campos[2].ifBlank { "dub" },
                urls)
        }
        return out.filter { it.episodios.isNotEmpty() }
            .map { SerieColecao(it.titulo, it.ano, it.tmdbId, it.episodios) }
            .also { colecoes[tipo] = it }
    }

    private val colecoes = java.util.concurrent.ConcurrentHashMap<String, List<SerieColecao>>()

    /** Animes e doramas que batem com o termo — eles não estão no índice de busca. */
    suspend fun buscarColecoes(context: Context, termo: String): List<Pair<String, SerieColecao>> {
        val alvo = normalizar(termo)
        if (alvo.isEmpty()) return emptyList()
        return listOf("animes", "doramas").flatMap { tipo ->
            colecao(context, tipo).filter { normalizar(it.titulo).contains(alvo) }.map { tipo to it }
        }.sortedBy { if (normalizar(it.second.titulo).startsWith(alvo)) 0 else 1 }
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
    /// Depois disto o arquivo em disco é conferido com o servidor, por trás.
    private const val VALIDADE_MS = 6 * 60 * 60 * 1000L

    private val fundo = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val conferindo = java.util.Collections.synchronizedSet(HashSet<String>())

    private fun local(context: Context, nome: String): File {
        val pasta = File(context.filesDir, "vod").apply { mkdirs() }
        return File(pasta, nome.replace("%23", "hash")).also { it.parentFile?.mkdirs() }
    }

    /**
     * Conteúdo do arquivo: o do disco na hora, conferido com o servidor depois.
     *
     * Antes o que estava em disco valia para sempre — só a troca de servidores
     * limpava a pasta. O catálogo muda todo dia (título novo, link corrigido),
     * e a TV Box ficava com o de semanas atrás: o destaque novo abria uma ficha
     * cujo "Assistir" não achava o filme. Agora o disco continua servindo a
     * abertura instantânea, e passadas seis horas uma pergunta barata ao
     * servidor (ETag: 304 quando nada mudou) traz a versão nova para a próxima
     * vez. [fresco] pula o disco — é o que se usa quando um título não foi
     * achado no arquivo local.
     */
    private suspend fun arquivo(context: Context, nome: String, fresco: Boolean = false): String? =
        withContext(Dispatchers.IO) {
            val local = local(context, nome)
            if (!fresco && local.exists() && local.length() > 0) {
                if (System.currentTimeMillis() - local.lastModified() > VALIDADE_MS) conferirDepois(context, nome)
                return@withContext runCatching { local.readText() }.getOrNull()
            }
            baixar(context, nome) ?: runCatching { if (local.exists()) local.readText() else null }.getOrNull()
        }

    private fun conferirDepois(context: Context, nome: String) {
        if (!conferindo.add(nome)) return
        fundo.launch {
            try {
                baixar(context, nome)
            } finally {
                conferindo.remove(nome)
            }
        }
    }

    /** Busca o arquivo na rede e guarda em disco; 304 só renova a data. */
    private fun baixar(context: Context, nome: String): String? {
        val local = local(context, nome)
        val etag = File(local.path + ".etag")
        val texto = runCatching {
            val request = Request.Builder().url(BASE + nome)
                .header("User-Agent", Playback.DEFAULT_USER_AGENT)
                .apply {
                    if (local.exists() && etag.exists()) header("If-None-Match", etag.readText())
                }
                .build()
            Playback.client.newCall(request).execute().use { resposta ->
                if (resposta.code == 304) {
                    local.setLastModified(System.currentTimeMillis())
                    return runCatching { local.readText() }.getOrNull()
                }
                if (!resposta.isSuccessful) return null
                resposta.header("ETag")?.let { runCatching { etag.writeText(it) } }
                resposta.body?.string()
            }
        }.getOrNull() ?: return null

        runCatching { local.writeText(texto) }
        // O índice de busca montado em memória é do arquivo antigo.
        if (nome == "busca.txt") indice = null
        return texto
    }
}
