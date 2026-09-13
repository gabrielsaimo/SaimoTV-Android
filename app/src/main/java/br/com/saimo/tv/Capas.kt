package br.com.saimo.tv

import androidx.media3.common.util.UnstableApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import okhttp3.Request
import org.json.JSONObject
import java.net.URLEncoder
import java.text.Normalizer
import java.util.Collections
import kotlin.math.abs

/**
 * Capa de um filme ou série, procurada pelo nome no TMDB.
 *
 * As listas de origem não trazem imagem nenhuma. A busca acontece só para o
 * que está na tela: são vinte títulos visíveis, não trinta mil.
 *
 * Antes vinha do Cinemeta e ficava com o primeiro resultado, sem comparar
 * nome nenhum — "A 13ª Emenda" batia com qualquer coisa que a busca por esse
 * texto trouxesse primeiro. Agora vem do TMDB, com o mesmo algoritmo de
 * pontuação do `api-saimo-tv`: título exato, título contido, ano de
 * lançamento, popularidade — e um resultado único da busca também conta,
 * porque a busca do TMDB já casa por apelido traduzido que nem `title` nem
 * `original_title` revelam de volta.
 *
 * Nada disso vai para o disco. O que se guarda vive na memória e morre com o
 * app, então cada abertura busca de novo — que é como o dono desta lista quer.
 */
@UnstableApi
object Capas {

    private const val CHAVE_TMDB = "15d2ea6d0dc1d476efbca3eba2b9bbfb"
    private const val BASE = "https://api.themoviedb.org/3"
    private const val PONTUACAO_MINIMA = 10

    /// Só três buscas ao mesmo tempo: rolar a lista depressa não pode virar
    /// cinquenta pedidos, nem para o serviço nem para a rede do TV Box.
    private val portao = Semaphore(3)

    /// Título -> capa. Vazio quer dizer procurado e não achado, e é guardado
    /// também: sem isso a mesma busca infrutífera se repetiria a cada rolagem.
    private val memoria = Collections.synchronizedMap(HashMap<String, String>())

    /**
     * Capa do título, ou nulo. Devolve na hora o que já está na memória.
     */
    suspend fun capa(titulo: String, serie: Boolean): String? {
        val chave = (if (serie) "s:" else "f:") + titulo
        memoria[chave]?.let { return it.ifEmpty { null } }

        val achado = portao.withPermit { melhorPoster(titulo, serie) }
        memoria[chave] = achado.orEmpty()
        return achado
    }

    // MARK: - Busca no TMDB

    private data class Resultado(
        val id: Int,
        val titulo: String?,
        val original: String?,
        val data: String?,
        val posterPath: String?,
        val votos: Int,
    )

    private suspend fun buscarUmaVez(query: String, tv: Boolean, idioma: String): List<Resultado> =
        withContext(Dispatchers.IO) {
            val endpoint = if (tv) "search/tv" else "search/movie"
            val termo = URLEncoder.encode(query, "UTF-8")
            val url = "$BASE/$endpoint?query=$termo&api_key=$CHAVE_TMDB&language=$idioma"
            val corpo = runCatching {
                val pedido = Request.Builder().url(url)
                    .header("User-Agent", Playback.DEFAULT_USER_AGENT).build()
                Playback.client.newCall(pedido).execute().use { resposta ->
                    if (!resposta.isSuccessful) null else resposta.body?.string()
                }
            }.getOrNull() ?: return@withContext emptyList()

            val brutos = runCatching { JSONObject(corpo).optJSONArray("results") }.getOrNull()
                ?: return@withContext emptyList()

            (0 until brutos.length()).mapNotNull { i ->
                val item = brutos.optJSONObject(i) ?: return@mapNotNull null
                Resultado(
                    id = item.optInt("id"),
                    titulo = item.optString(if (tv) "name" else "title").takeIf { it.isNotBlank() },
                    original = item.optString(if (tv) "original_name" else "original_title")
                        .takeIf { it.isNotBlank() },
                    data = item.optString(if (tv) "first_air_date" else "release_date")
                        .takeIf { it.isNotBlank() },
                    posterPath = item.optString("poster_path").takeIf { it.isNotBlank() },
                    votos = item.optInt("vote_count"))
            }
        }

    /** pt-BR primeiro; sem resultado, tenta en-US. */
    private suspend fun buscar(query: String, tv: Boolean): List<Resultado> {
        val pt = buscarUmaVez(query, tv, "pt-BR")
        if (pt.isNotEmpty()) return pt
        return buscarUmaVez(query, tv, "en-US")
    }

    private fun pontuar(nomeLocal: String, resultado: Resultado, ano: Int?, unico: Boolean): Int {
        val localNorm = normalizar(limpar(nomeLocal))
        val tituloNorm = normalizar(resultado.titulo.orEmpty())
        val originalNorm = normalizar(resultado.original.orEmpty())

        var pontos = 0
        when {
            localNorm == tituloNorm || localNorm == originalNorm -> pontos += 100
            tituloNorm.startsWith(localNorm) || localNorm.startsWith(tituloNorm) -> pontos += 70
            originalNorm.startsWith(localNorm) || localNorm.startsWith(originalNorm) -> pontos += 65
            tituloNorm.contains(localNorm) || localNorm.contains(tituloNorm) -> pontos += 50
            originalNorm.contains(localNorm) || localNorm.contains(originalNorm) -> pontos += 45
            // A busca do TMDB já casa por título traduzido que nem `titulo`
            // nem `original` revelam de volta — um resultado único já é
            // sinal suficiente de que a relevância deles achou o certo.
            unico -> pontos += 12
        }

        val votos = resultado.votos
        if (votos > 1000) pontos += 15 else if (votos > 100) pontos += 8

        if (ano != null && resultado.data != null && resultado.data.length >= 4) {
            val anoTmdb = resultado.data.take(4).toIntOrNull()
            if (anoTmdb != null) {
                val diff = abs(ano - anoTmdb)
                when {
                    diff == 0 -> pontos += 25
                    diff == 1 -> pontos += 10
                    diff > 2 -> pontos -= 25
                }
            }
        }
        return pontos
    }

    /**
     * Tenta o tipo pedido em todas as variantes do nome; sem sorte, tenta o
     * tipo oposto — um "anime" catalogado como filme às vezes é uma série no
     * TMDB, e vice-versa.
     */
    private suspend fun melhorPoster(nome: String, serie: Boolean): String? {
        val ano = extrairAno(nome)
        val variantes = variantesDeBusca(nome)

        var melhor: Resultado? = null
        var melhorPontos = 0

        for (variante in variantes) {
            val resultados = buscar(variante, tv = serie)
            val unico = resultados.size == 1
            for (r in resultados.take(5)) {
                val pontos = pontuar(nome, r, ano, unico)
                if (pontos > melhorPontos) { melhorPontos = pontos; melhor = r }
            }
            if (melhorPontos >= 90) break
        }

        if (melhorPontos < PONTUACAO_MINIMA) {
            for (variante in variantes) {
                val resultados = buscar(variante, tv = !serie)
                val unico = resultados.size == 1
                for (r in resultados.take(5)) {
                    val pontos = pontuar(nome, r, ano, unico)
                    if (pontos > melhorPontos) { melhorPontos = pontos; melhor = r }
                }
                if (melhorPontos >= 90) break
            }
        }

        if (melhorPontos < PONTUACAO_MINIMA) return null
        // O TMDB devolve só o caminho ("/abc.jpg"). Entregue assim, o Coil o
        // lia como arquivo local e nenhuma capa aparecia desde a troca do
        // Cinemeta. w342 basta para a grade e poupa memória do TV Box.
        return melhor?.posterPath?.let { "https://image.tmdb.org/t/p/w342$it" }
    }

    // MARK: - Limpeza de título

    private val RUIDO = Regex(
        "\\b(4k|uhd|fhd|hd|sd|h265|hevc|hdr|dv|dual|remux|legendado|dublado|leg|dub)\\b",
        RegexOption.IGNORE_CASE)
    private val COLCHETES = Regex("\\[[^]]*]")
    private val PARENTESES_SEM_ANO = Regex("\\((?!(?:19|20)\\d{2}\\))[^)]*\\)")
    private val ANO = Regex("\\b(19|20)\\d{2}\\b")
    private val ARTIGO_INICIAL = Regex("^(o|a|os|as|um|uma|the|an?)\\s+", RegexOption.IGNORE_CASE)
    private val SUBTITULO = Regex("\\s*[:\\-]\\s+")
    private val ROMANO_FINAL = Regex("\\s+(II|III|IV|V|VI|VII|VIII|IX|X)$", RegexOption.IGNORE_CASE)

    private fun normalizar(texto: String): String {
        val semAcento = Normalizer.normalize(texto, Normalizer.Form.NFD)
            .replace(Regex("\\p{Mn}+"), "")
        return semAcento.lowercase()
            .replace(Regex("[^a-z0-9\\s]"), " ")
            .replace(Regex("\\s+"), " ")
            .trim()
    }

    private fun extrairAno(titulo: String): Int? = ANO.find(titulo)?.value?.toIntOrNull()

    /** Mantém o ano: é ele que separa a capa de uma refilmagem da outra. */
    fun limpar(titulo: String): String {
        var texto = COLCHETES.replace(titulo, " ")
        texto = PARENTESES_SEM_ANO.replace(texto, " ")
        texto = RUIDO.replace(texto, " ")
        return texto.replace(Regex("\\s{2,}"), " ").trim()
    }

    /**
     * As variações que valem tentar: título limpo, sem artigo inicial, sem
     * subtítulo depois de ":" ou "-", sem acento, sem algarismo romano no fim.
     */
    private fun variantesDeBusca(nome: String): List<String> {
        val limpo = limpar(nome)
        val variantes = mutableListOf(limpo)

        fun adicionar(s: String?) {
            if (s != null && s.length > 1 && s !in variantes) variantes += s
        }

        val semArtigo = ARTIGO_INICIAL.replace(limpo, "").trim()
        adicionar(semArtigo.takeIf { it != limpo })

        val posSubtitulo = SUBTITULO.find(limpo)?.range?.first
        if (posSubtitulo != null) {
            val semSubtitulo = limpo.substring(0, posSubtitulo).trim()
            adicionar(semSubtitulo.takeIf { it.length > 2 })
        }

        val semAcento = Normalizer.normalize(limpo, Normalizer.Form.NFD)
            .replace(Regex("\\p{Mn}+"), "")
        adicionar(semAcento.takeIf { it != limpo })

        val semRomano = ROMANO_FINAL.replace(limpo, "").trim()
        adicionar(semRomano.takeIf { it != limpo })

        return variantes
    }
}
