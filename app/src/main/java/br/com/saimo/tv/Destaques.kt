package br.com.saimo.tv

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import java.io.File

/**
 * As fileiras da tela inicial, prontas para desenhar.
 *
 * O acervo tem trinta e quatro mil filmes e nenhuma data de entrada, então não
 * há como um TV Box descobrir sozinho o que é novidade — e perguntar a capa de
 * cada título ao TMDB, a cada abertura, seria uma tela que demora meio minuto
 * para aparecer. A conta é feita no repositório (`gerar_destaques.py`) e chega
 * aqui pronta: seis fileiras, cento e vinte títulos, sete quilobytes, com o
 * caminho do pôster junto. O aparelho baixa uma vez e desenha.
 *
 * O formato de cada item é o mesmo de um resultado de busca — tipo, título,
 * letra, ano —, então abrir um destaque passa pelo caminho que já abre um
 * título procurado, sem código novo para resolver fonte nenhuma.
 */
object Destaques {

    private const val ARQUIVO = "destaques.txt"
    /// A lista muda quando o gerador roda; um dia em disco é o bastante para
    /// abrir instantâneo sem ficar semanas desatualizado.
    private const val VALIDADE_MS = 24 * 60 * 60 * 1000L

    data class Item(
        val titulo: String,
        /// 'f' filme, 's' série, 'a' anime, 'd' dorama.
        val tipo: Char,
        val letra: String,
        val ano: String,
        /// Endereço inteiro da capa, ou vazio quando o gerador não achou uma.
        val capa: String,
    ) {
        val serie: Boolean get() = tipo != 'f'
        val daColecao: Boolean get() = tipo == 'a' || tipo == 'd'
        val colecao: String get() = if (tipo == 'a') "animes" else "doramas"
    }

    data class Fila(val titulo: String, val itens: List<Item>)

    suspend fun filas(context: Context): List<Fila> = withContext(Dispatchers.IO) {
        val texto = texto(context) ?: return@withContext emptyList()
        val out = mutableListOf<Fila>()
        var titulo: String? = null
        var itens = mutableListOf<Item>()
        var base = ""

        fun fechar() {
            val nome = titulo ?: return
            if (itens.isNotEmpty()) out += Fila(nome, itens.toList())
            itens = mutableListOf()
        }

        for (linha in texto.lineSequence()) {
            when {
                linha.isBlank() || linha.startsWith("#") -> Unit
                linha.startsWith("capa:") -> base = linha.removePrefix("capa:").trim()
                linha.startsWith("fila\t") -> {
                    fechar()
                    titulo = linha.removePrefix("fila\t").trim()
                }
                else -> {
                    val campos = linha.split("\t")
                    if (campos.size < 3) continue
                    val poster = campos.getOrNull(4).orEmpty()
                    itens += Item(
                        titulo = campos[1],
                        tipo = campos[0].firstOrNull() ?: 'f',
                        letra = campos[2],
                        ano = campos.getOrNull(3).orEmpty(),
                        capa = if (poster.isEmpty()) "" else base + poster,
                    )
                }
            }
        }
        fechar()
        out
    }

    private fun texto(context: Context): String? {
        val pasta = File(context.filesDir, "vod").apply { mkdirs() }
        val local = File(pasta, ARQUIVO)
        val fresco = local.exists() && local.length() > 0 &&
            System.currentTimeMillis() - local.lastModified() < VALIDADE_MS
        if (fresco) return runCatching { local.readText() }.getOrNull()

        val baixado = runCatching {
            val pedido = Request.Builder().url(Vod.BASE + ARQUIVO)
                .header("User-Agent", Playback.DEFAULT_USER_AGENT)
                .header("Accept", "*/*")
                .build()
            Playback.client.newCall(pedido).execute().use { resposta ->
                if (!resposta.isSuccessful) null else resposta.body?.string()
            }
        }.getOrNull()

        if (baixado != null && baixado.isNotBlank()) {
            runCatching { local.writeText(baixado) }
            return baixado
        }
        // Rede fora: o que está em disco, mesmo vencido, é melhor que nada.
        return runCatching { if (local.exists()) local.readText() else null }.getOrNull()
    }
}
