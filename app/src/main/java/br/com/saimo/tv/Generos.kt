package br.com.saimo.tv

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Request
import java.io.File

/**
 * O gênero de cada título: Ação, Terror, Animação, Comédia.
 *
 * O catálogo não tem gênero — as listas de origem trazem nome e endereço, nada
 * mais. Perguntar ao TMDB por trinta e quatro mil títulos, no aparelho, a cada
 * abertura, é uma tela que nunca abre; num TV Box, nem isso.
 *
 * A pergunta é feita uma vez no repositório (`gerar_generos.py`) e chega aqui
 * pronta, no mesmo arquivo que o Mac, o Windows e o site leem.
 *
 * Sem o arquivo, a régua de gêneros simplesmente não aparece: oferecer um
 * filtro que devolve vazio é pior que não oferecer.
 */
object Generos {

    private const val ARQUIVO = "fichas.txt"
    /// O acervo muda devagar; um dia em disco evita baixar 2 MB por abertura.
    private const val VALIDADE_MS = 24 * 60 * 60 * 1000L

    /**
     * O que se sabe de um título, compacto.
     *
     * Eram quatro mapas de quarenta mil entradas, cada um com a sua cópia do
     * nome e o endereço inteiro da capa — dezenas de MB num TV Box de 1 GB,
     * onde o sistema mata o app que passa de ~250 MB. Agora é um registro por
     * título: o id, só o caminho da capa (a base é uma só) e os gêneros como
     * bits de um inteiro.
     */
    private class Registro(val titulo: String, val id: Int, val poster: String?, val generos: Int)

    private var filmes: HashMap<String, Registro> = HashMap()
    private var series: HashMap<String, Registro> = HashMap()
    private var porId: HashMap<Int, Registro> = HashMap()
    private var baseCapa = ""
    /// Todos os gêneros que aparecem no acervo, em ordem alfabética.
    var todos: List<String> = emptyList()
        private set
    private var indiceGenero: Map<String, Int> = emptyMap()

    val prontos: Boolean get() = filmes.isNotEmpty() || series.isNotEmpty()

    @Volatile
    private var baixando = false

    private fun registro(titulo: String, serie: Boolean): Registro? {
        val mapa = if (serie) series else filmes
        return mapa[titulo] ?: mapa[semAno(titulo)]
    }

    /**
     * Os gêneros de um título.
     *
     * O acervo escreve o ano dentro do nome do filme e guarda o da série à
     * parte; procura-se o nome como chegou e, não achando, sem o ano.
     */
    fun de(titulo: String, serie: Boolean): List<String> {
        val bits = registro(titulo, serie)?.generos ?: return emptyList()
        return todos.filterIndexed { i, _ -> bits and (1 shl i) != 0 }
    }

    private val ANO_NO_FIM = Regex("\\s*\\(\\d{4}\\)\\s*$")

    fun semAno(titulo: String): String = titulo.replace(ANO_NO_FIM, "").trim()

    /** O id do TMDB de um título, quando o gerador o resolveu. */
    fun id(titulo: String, serie: Boolean): Int? = registro(titulo, serie)?.id?.takeIf { it > 0 }

    /** O título do acervo que corresponde a um id do TMDB, se houver. */
    fun titulo(id: Int, serie: Boolean): String? = porId[if (serie) -id else id]?.titulo

    fun tem(titulo: String, serie: Boolean, genero: String): Boolean {
        if (genero.isEmpty()) return true
        val i = indiceGenero[genero] ?: return false
        return (registro(titulo, serie)?.generos ?: 0) and (1 shl i) != 0
    }

    /**
     * Lê as fichas e, quando o arquivo local está velho, busca o novo depois.
     *
     * Antes o download de 3,7 MB vinha primeiro: numa TV Box em wi-fi, isso é
     * a tela inteira sem capa nenhuma até o arquivo chegar, todo dia. Agora o
     * que está em disco entra na hora e o arquivo novo, quando chega, refaz os
     * mapas — a capa que faltava aparece sozinha na próxima rolagem.
     */
    suspend fun carregar(context: Context) = withContext(Dispatchers.IO) {
        if (prontos) return@withContext
        val local = arquivoLocal(context)
        if (local.exists() && local.length() > 0) {
            runCatching { local.bufferedReader().useLines { montar(it) } }
            if (velho(local)) baixarDepois(context)
            if (prontos) return@withContext
        }
        if (baixar(context)) runCatching { local.bufferedReader().useLines { montar(it) } }
    }

    /** Monta os mapas a partir do conteúdo do arquivo, linha a linha. */
    private fun montar(linhas: Sequence<String>) {
        val novosFilmes = HashMap<String, Registro>(40_000)
        val novasSeries = HashMap<String, Registro>(12_000)
        val novoPorId = HashMap<Int, Registro>(50_000)
        val nomes = LinkedHashMap<String, Int>()
        val brutos = ArrayList<Pair<Registro, List<String>>>()
        var base = ""
        // tipo \t título \t id do TMDB \t pôster \t gêneros
        for (linha in linhas) {
            if (linha.startsWith("capa:")) { base = linha.removePrefix("capa:").trim(); continue }
            if (linha.startsWith("#")) continue
            val campos = linha.split('\t')
            if (campos.size < 5) continue
            val serie = campos[0] == "s"
            val titulo = campos[1]
            val id = campos[2].toIntOrNull() ?: 0
            val lista = campos[4].split(',').filter { it.isNotBlank() }
            for (g in lista) if (g !in nomes) nomes[g] = nomes.size
            var bits = 0
            for (g in lista) nomes[g]?.let { if (it < 31) bits = bits or (1 shl it) }
            val registro = Registro(titulo, id, campos[3].ifBlank { null }, bits)
            (if (serie) novasSeries else novosFilmes)[titulo] = registro
            if (id > 0) {
                val marca = if (serie) -id else id
                // O mesmo filme em duas grafias: o primeiro basta.
                if (!novoPorId.containsKey(marca)) novoPorId[marca] = registro
            }
        }
        // Os bits foram dados na ordem em que os gêneros apareceram; a régua
        // mostra em ordem alfabética, então a lista guarda a ordem dos bits.
        val ordemDosBits = nomes.entries.sortedBy { it.value }.map { it.key }
        filmes = novosFilmes
        series = novasSeries
        porId = novoPorId
        baseCapa = base
        todos = ordemDosBits
        indiceGenero = nomes
    }

    /** Gêneros em ordem alfabética, para a régua. */
    val todosEmOrdem: List<String> get() = todos.sorted()

    private fun arquivoLocal(context: Context) =
        File(File(context.filesDir, "vod").apply { mkdirs() }, ARQUIVO)

    private fun velho(local: File) =
        System.currentTimeMillis() - local.lastModified() >= VALIDADE_MS

    /** Baixa o arquivo novo sem segurar a tela, e refaz os mapas quando chega. */
    private fun baixarDepois(context: Context) {
        if (baixando) return
        baixando = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (baixar(context)) {
                    runCatching { arquivoLocal(context).bufferedReader().useLines { montar(it) } }
                }
            } finally {
                baixando = false
            }
        }
    }

    /**
     * O pôster de um título, pelo id que o gerador já resolveu. Quem não tem
     * ficha fica sem capa — e a tela põe uma marca no lugar.
     */
    fun capa(titulo: String, serie: Boolean): String? =
        registro(titulo, serie)?.poster?.let { baseCapa + it }

    /** Baixa direto para o disco, sem passar o arquivo inteiro pela memória. */
    private fun baixar(context: Context): Boolean = runCatching {
        val pedido = Request.Builder().url(Vod.BASE + ARQUIVO)
            .header("User-Agent", Playback.DEFAULT_USER_AGENT)
            .header("Accept", "*/*")
            .build()
        Playback.client.newCall(pedido).execute().use { r ->
            if (!r.isSuccessful) return@use false
            val destino = arquivoLocal(context)
            val temporario = File(destino.path + ".novo")
            r.body!!.byteStream().use { entrada -> temporario.outputStream().use { entrada.copyTo(it) } }
            temporario.length() > 0 && temporario.renameTo(destino)
        }
    }.getOrDefault(false)
}
