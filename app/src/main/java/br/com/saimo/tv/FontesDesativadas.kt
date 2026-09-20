package br.com.saimo.tv

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import org.json.JSONObject

/**
 * Servidores desligados à mão, no painel do monitor.
 *
 * Quando um provedor cai, cai inteiro: não é a fonte 3 de um canal que morreu,
 * é o servidor que parou de responder para todo mundo. Editar o catálogo
 * publicado a cada queda é lento e some com o link, que depois precisa voltar.
 * Desligar o servidor no painel some com ele de todo canal e de todo filme, em
 * todos os aplicativos, e religar devolve tudo.
 *
 * A lista é baixada sem chave nenhuma: são nomes de servidor, que o catálogo
 * publicado já mostra, e exigir segredo significaria embutir um segredo num
 * aplicativo que qualquer um baixa.
 *
 * Sem rede a lista fica vazia e nada é escondido — o erro certo a cometer: um
 * canal a mais na tela é melhor que a lista inteira sumindo porque o monitor
 * não respondeu.
 */
object FontesDesativadas {

    private const val ENDERECO = "https://saimo-monitor.gabrielsaimo68.workers.dev/v1/fontes"
    /// Desligar um servidor tem que valer em minutos, que é o tempo que alguém
    /// aguenta um canal quebrado.
    private const val VALIDADE_MS = 120_000L

    @Volatile
    private var hosts: Set<String> = emptySet()
    @Volatile
    private var lidoEm = 0L

    val atuais: Set<String> get() = hosts

    /**
     * Busca a lista quando ela envelheceu.
     *
     * Devolve `true` quando mudou, que é quando quem chamou precisa remontar a
     * lista de canais.
     */
    suspend fun atualizar(): Boolean = withContext(Dispatchers.IO) {
        if (System.currentTimeMillis() - lidoEm < VALIDADE_MS) return@withContext false
        val corpo = runCatching {
            val pedido = Request.Builder().url(ENDERECO)
                .header("User-Agent", Playback.DEFAULT_USER_AGENT)
                .header("Accept", "*/*")
                .build()
            Playback.client.newCall(pedido).execute().use { resposta ->
                if (!resposta.isSuccessful) null else resposta.body?.string()
            }
        }.getOrNull() ?: return@withContext false

        lidoEm = System.currentTimeMillis()
        val lista = runCatching {
            val arranjo = JSONObject(corpo).optJSONArray("desativados") ?: return@runCatching emptySet()
            (0 until arranjo.length()).mapNotNull { arranjo.optString(it).lowercase().ifBlank { null } }.toSet()
        }.getOrNull() ?: return@withContext false

        if (lista == hosts) return@withContext false
        hosts = lista
        Log.i("Saimo", if (lista.isEmpty()) "nenhum servidor desligado"
                       else "servidores desligados: ${lista.sorted().joinToString(", ")}")
        true
    }

    fun desligado(url: String): Boolean {
        if (hosts.isEmpty()) return false
        val host = runCatching { android.net.Uri.parse(url).host?.lowercase() }.getOrNull()
        return host != null && host in hosts
    }

    /** Os endereços de um filme ou episódio sem os que estão desligados. */
    fun peneirar(urls: List<String>): List<String> =
        if (hosts.isEmpty()) urls else urls.filterNot { desligado(it) }

    /**
     * O catálogo sem o que está desligado.
     *
     * Canal que fica sem nenhuma fonte sai da lista: ele não abriria mesmo, e
     * deixá-lo ali só rende clique frustrado. Volta sozinho quando o servidor
     * for religado.
     */
    fun peneirarCanais(canais: List<Channel>): List<Channel> {
        if (hosts.isEmpty()) return canais
        return canais.mapNotNull { canal ->
            val vivas = canal.sources.filterNot { desligado(it.url) }
            when {
                vivas.isEmpty() -> null
                vivas.size == canal.sources.size -> canal
                else -> canal.copy(sources = vivas)
            }
        }
    }

    private object Log {
        fun i(etiqueta: String, mensagem: String) {
            android.util.Log.i(etiqueta, mensagem)
        }
    }
}
