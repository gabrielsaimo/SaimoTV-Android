package br.com.saimo.tv

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import org.json.JSONObject

/**
 * A ficha de um título: sinopse, duração, classificação, gêneros e elenco.
 *
 * O catálogo publicado traz nome e endereço; o arquivo de fichas acrescenta o
 * id do TMDB, o pôster e os gêneros. É o bastante para desenhar a fileira, e
 * pouco demais para quem parou num cartaz e quer saber do que se trata antes
 * de gastar dois minutos abrindo o filme numa TV Box.
 *
 * O resto só existe no endereço do próprio título no TMDB, e é um pedido por
 * título aberto — não por título listado. Com o id vindo da ficha não há busca
 * nem desempate: pergunta-se direto pelo número certo.
 *
 * Os mesmos campos, com os mesmos nomes, são o que o celular, o Mac, o Windows
 * e o site mostram.
 */
object Detalhes {

    private const val CHAVE = "15d2ea6d0dc1d476efbca3eba2b9bbfb"
    private const val BASE = "https://api.themoviedb.org/3"
    private const val IMAGENS = "https://image.tmdb.org/t/p/"

    data class Pessoa(val id: Int, val nome: String, val papel: String, val foto: String?)

    data class Ficha(
        val titulo: String = "",
        val sinopse: String = "",
        val frase: String = "",
        /** Minutos: do filme, ou de um episódio da série. */
        val duracao: Int? = null,
        val classificacao: String? = null,
        val nota: Double = 0.0,
        val ano: String = "",
        val generos: List<String> = emptyList(),
        /** Quem dirigiu o filme, ou quem criou a série. */
        val assinatura: String = "",
        val roteiro: String = "",
        val produtora: String = "",
        val elenco: List<Pessoa> = emptyList(),
        val capa: String? = null,
    ) {
        val vazia: Boolean
            get() = sinopse.isBlank() && elenco.isEmpty() && generos.isEmpty() && duracao == null
    }

    private val guardadas = HashMap<String, Ficha>()

    /** A ficha de um título do acervo, ou nula quando o TMDB não a conhece. */
    suspend fun de(titulo: String, serie: Boolean): Ficha? = withContext(Dispatchers.IO) {
        val marca = (if (serie) "s:" else "f:") + titulo
        guardadas[marca]?.let { return@withContext it }
        val id = Generos.id(titulo, serie) ?: return@withContext null
        val ficha = baixar(id, serie) ?: return@withContext null
        synchronized(guardadas) { guardadas[marca] = ficha }
        ficha
    }

    private fun baixar(id: Int, serie: Boolean): Ficha? {
        val tipo = if (serie) "tv" else "movie"
        val extras = if (serie) "credits,content_ratings" else "credits,release_dates"
        val json = pedir("$BASE/$tipo/$id?api_key=$CHAVE&language=pt-BR&append_to_response=$extras")
            ?: return null

        val creditos = json.optJSONObject("credits")
        val equipe = creditos?.optJSONArray("crew")
        val direcao = mutableListOf<String>()
        val roteiro = mutableListOf<String>()
        if (equipe != null) {
            for (i in 0 until equipe.length()) {
                val pessoa = equipe.optJSONObject(i) ?: continue
                when (pessoa.optString("job")) {
                    "Director" -> direcao += pessoa.optString("name")
                    "Screenplay", "Writer", "Story" -> roteiro += pessoa.optString("name")
                }
            }
        }
        // Série não tem diretor único: quem a assina é quem a criou.
        val criadores = json.optJSONArray("created_by")?.let { lista ->
            (0 until lista.length()).mapNotNull { lista.optJSONObject(it)?.optString("name") }
        }.orEmpty()
        val assinatura = (if (direcao.isEmpty()) criadores else direcao).take(2).joinToString(", ")

        val elenco = creditos?.optJSONArray("cast")?.let { lista ->
            (0 until minOf(lista.length(), 20)).mapNotNull { i ->
                val pessoa = lista.optJSONObject(i) ?: return@mapNotNull null
                Pessoa(
                    id = pessoa.optInt("id"),
                    nome = pessoa.optString("name"),
                    papel = pessoa.optString("character"),
                    foto = pessoa.optString("profile_path").takeIf { it.isNotBlank() && it != "null" }
                        ?.let { IMAGENS + "w185" + it },
                )
            }
        }.orEmpty()

        val data = json.optString(if (serie) "first_air_date" else "release_date")
        val duracao = if (serie) {
            json.optJSONArray("episode_run_time")?.takeIf { it.length() > 0 }?.optInt(0)
        } else {
            json.optInt("runtime").takeIf { it > 0 }
        }

        return Ficha(
            titulo = json.optString(if (serie) "name" else "title"),
            sinopse = json.optString("overview"),
            frase = json.optString("tagline"),
            duracao = duracao?.takeIf { it > 0 },
            classificacao = classificacaoBR(json, serie),
            nota = json.optDouble("vote_average", 0.0),
            ano = data.take(4),
            generos = json.optJSONArray("genres")?.let { lista ->
                (0 until lista.length()).mapNotNull { lista.optJSONObject(it)?.optString("name") }
            }.orEmpty(),
            assinatura = assinatura,
            roteiro = roteiro.distinct().take(2).joinToString(", "),
            produtora = json.optJSONArray("production_companies")
                ?.optJSONObject(0)?.optString("name").orEmpty(),
            elenco = elenco,
            capa = json.optString("poster_path").takeIf { it.isNotBlank() && it != "null" }
                ?.let { IMAGENS + "w342" + it },
        )
    }

    /**
     * O que um ator fez **e que existe neste acervo**.
     *
     * A filmografia inteira do TMDB não serve de dentro do aplicativo: listar
     * oitenta títulos dos quais setenta não abrem é uma lista que frustra. O
     * cruzamento é pelo id do TMDB, que o arquivo de fichas já traz para cada
     * título daqui — nome igual não engana, refilmagem não vira o original.
     */
    suspend fun acervoDe(context: Context, ator: Int): List<Vod.Achado> =
        withContext(Dispatchers.IO) {
            val json = pedir("$BASE/person/$ator/combined_credits?api_key=$CHAVE&language=pt-BR")
                ?: return@withContext emptyList()
            val trabalhos = mutableListOf<JSONObject>()
            for (campo in listOf("cast", "crew")) {
                val lista = json.optJSONArray(campo) ?: continue
                for (i in 0 until lista.length()) lista.optJSONObject(i)?.let { trabalhos += it }
            }
            // Ordem de popularidade: o que a pessoa é mais conhecida por fazer
            // vem primeiro, e não a ordem em que o TMDB devolveu.
            trabalhos.sortByDescending { it.optDouble("popularity", 0.0) }

            val acervo = Vod.todos(context, serie = false) + Vod.todos(context, serie = true)
            val porNome = HashMap<String, Vod.Achado>(acervo.size)
            for (achado in acervo) porNome[(if (achado.serie) "s:" else "f:") + achado.titulo] = achado

            val vistos = HashSet<String>()
            val saida = mutableListOf<Vod.Achado>()
            for (trabalho in trabalhos) {
                val idDoTitulo = trabalho.optInt("id").takeIf { it > 0 } ?: continue
                val serie = trabalho.optString("media_type") == "tv"
                val titulo = Generos.titulo(idDoTitulo, serie) ?: continue
                val marca = if (serie) "s:" else "f:"
                val achado = porNome[marca + titulo]
                    ?: porNome[marca + Generos.semAno(titulo)]
                    ?: continue
                if (!vistos.add(achado.nomeCompleto + achado.serie)) continue
                saida += achado
            }
            saida
        }

    private fun classificacaoBR(json: JSONObject, serie: Boolean): String? {
        if (serie) {
            val lista = json.optJSONObject("content_ratings")?.optJSONArray("results")
                ?: return null
            for (i in 0 until lista.length()) {
                val item = lista.optJSONObject(i) ?: continue
                if (item.optString("iso_3166_1") == "BR") {
                    return item.optString("rating").takeIf { it.isNotBlank() }
                }
            }
            return null
        }
        val lista = json.optJSONObject("release_dates")?.optJSONArray("results") ?: return null
        for (i in 0 until lista.length()) {
            val item = lista.optJSONObject(i) ?: continue
            if (item.optString("iso_3166_1") != "BR") continue
            val datas = item.optJSONArray("release_dates") ?: return null
            for (j in 0 until datas.length()) {
                val nota = datas.optJSONObject(j)?.optString("certification")
                if (!nota.isNullOrBlank()) return nota
            }
        }
        return null
    }

    private fun pedir(url: String): JSONObject? = runCatching {
        val pedido = Request.Builder().url(url)
            .header("User-Agent", Playback.DEFAULT_USER_AGENT)
            .header("Accept", "application/json")
            .build()
        Playback.client.newCall(pedido).execute().use { r ->
            if (!r.isSuccessful) null else r.body?.string()?.let { JSONObject(it) }
        }
    }.getOrNull()
}
