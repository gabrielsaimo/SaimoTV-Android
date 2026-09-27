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
        /** A imagem larga do título, para o fundo da ficha. */
        val fundo: String? = null,
        /** Quantas temporadas a série tem, segundo o TMDB. */
        val temporadas: Int? = null,
    ) {
        val vazia: Boolean
            get() = sinopse.isBlank() && elenco.isEmpty() && generos.isEmpty() && duracao == null
    }

    private val guardadas = HashMap<String, Ficha>()

    /**
     * A ficha de um título do acervo, ou nula quando o TMDB não a conhece.
     *
     * [idConhecido] vem das coleções de anime e dorama, que publicam o id do
     * TMDB no próprio arquivo — e acertam onde o arquivo de fichas erra: o
     * anime "Kevin" casava ali com outra série de mesmo nome.
     */
    suspend fun de(titulo: String, serie: Boolean, idConhecido: Int? = null): Ficha? =
        withContext(Dispatchers.IO) {
        val marca = (if (serie) "s:" else "f:") + titulo + (idConhecido?.let { "#$it" } ?: "")
        synchronized(guardadas) { guardadas[marca] }?.let { return@withContext it }
        val id = idConhecido?.takeIf { it > 0 } ?: Generos.id(titulo, serie)
            ?: return@withContext null
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
            // w780 e não original: o fundo fica escurecido e desfocado pelo
            // degradê, e um TV Box de 1 GB não precisa decodificar 4K para isso.
            fundo = json.optString("backdrop_path").takeIf { it.isNotBlank() && it != "null" }
                ?.let { IMAGENS + "w780" + it },
            temporadas = if (serie) json.optInt("number_of_seasons").takeIf { it > 0 } else null,
        )
    }

    /** Quem é a pessoa: foto grande, biografia, nascimento, de onde é. */
    data class Perfil(
        val nome: String,
        val foto: String?,
        val biografia: String,
        val nascimento: String,
        val falecimento: String,
        val local: String,
        val conhecidaPor: String,
    )

    private val perfis = HashMap<Int, Perfil>()

    /**
     * O perfil de uma pessoa. A biografia em português é curta ou falta para
     * quase todo mundo que não é brasileiro, então sem ela vale a em inglês —
     * melhor que um espaço vazio embaixo da foto.
     */
    suspend fun perfil(id: Int): Perfil? = withContext(Dispatchers.IO) {
        synchronized(perfis) { perfis[id] }?.let { return@withContext it }
        val json = pedir("$BASE/person/$id?api_key=$CHAVE&language=pt-BR") ?: return@withContext null
        var biografia = json.optString("biography")
        if (biografia.isBlank()) {
            biografia = pedir("$BASE/person/$id?api_key=$CHAVE&language=en-US")
                ?.optString("biography").orEmpty()
        }
        val perfil = Perfil(
            nome = json.optString("name"),
            foto = json.optString("profile_path").takeIf { it.isNotBlank() && it != "null" }
                ?.let { IMAGENS + "h632" + it },
            biografia = biografia,
            nascimento = json.optString("birthday").takeIf { it != "null" }.orEmpty(),
            falecimento = json.optString("deathday").takeIf { it != "null" }.orEmpty(),
            local = json.optString("place_of_birth").takeIf { it != "null" }.orEmpty(),
            conhecidaPor = when (json.optString("known_for_department")) {
                "Acting" -> "Atuação"
                "Directing" -> "Direção"
                "Writing" -> "Roteiro"
                "Production" -> "Produção"
                "Sound" -> "Música"
                "Camera" -> "Fotografia"
                else -> ""
            },
        )
        synchronized(perfis) { perfis[id] = perfil }
        perfil
    }

    /** Um trabalho da pessoa que existe no acervo, com a capa que o TMDB já mandou. */
    data class Trabalho(val achado: Vod.Achado, val capa: String?, val papel: String)

    /** Mantido para quem chamava: o índice agora é o da busca, sem cópia. */
    suspend fun aquecer(context: Context) {
        withContext(Dispatchers.IO) { runCatching { Vod.entradas(context) } }
    }

    /**
     * O que um ator fez **e que existe neste acervo**.
     *
     * A filmografia inteira do TMDB não serve de dentro do aplicativo: listar
     * oitenta títulos dos quais setenta não abrem é uma lista que frustra. O
     * cruzamento é pelo id do TMDB, que o arquivo de fichas já traz para cada
     * título daqui — nome igual não engana, refilmagem não vira o original.
     *
     * A capa vem da própria resposta do TMDB, em 185 pixels: é pequena, chega
     * rápido e existe para quase todo trabalho — a do arquivo de fichas falta
     * para boa parte deles.
     */
    suspend fun acervoDe(context: Context, ator: Int): List<Trabalho> =
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

            val vistos = HashSet<String>()
            val saida = mutableListOf<Trabalho>()
            for (trabalho in trabalhos) {
                val idDoTitulo = trabalho.optInt("id").takeIf { it > 0 } ?: continue
                val serie = trabalho.optString("media_type") == "tv"
                val titulo = Generos.titulo(idDoTitulo, serie) ?: continue
                val achado = Vod.achar(context, titulo, serie) ?: continue
                if (!vistos.add(achado.nomeCompleto + achado.serie)) continue
                val capa = trabalho.optString("poster_path").takeIf { it.isNotBlank() && it != "null" }
                    ?.let { IMAGENS + "w185" + it }
                    ?: Generos.capa(achado.nomeCompleto, achado.serie)
                val papel = trabalho.optString("character").takeIf { it.isNotBlank() && it != "null" }
                    ?: trabalho.optString("job").takeIf { it.isNotBlank() && it != "null" }
                    ?: ""
                saida += Trabalho(achado, capa, papel)
            }
            saida
        }

    /** Um episódio como o TMDB descreve: nome, imagem, resumo, duração. */
    data class EpisodioTmdb(
        val numero: Int,
        val nome: String,
        val sinopse: String,
        val imagem: String?,
        val duracao: Int?,
    )

    private val temporadas = HashMap<String, List<EpisodioTmdb>>()

    /** Os episódios de uma temporada, para a lista da ficha ter nome e imagem. */
    suspend fun temporada(id: Int, numero: Int): List<EpisodioTmdb> = withContext(Dispatchers.IO) {
        if (id <= 0) return@withContext emptyList()
        val chave = "$id|$numero"
        synchronized(temporadas) { temporadas[chave] }?.let { return@withContext it }
        val json = pedir("$BASE/tv/$id/season/$numero?api_key=$CHAVE&language=pt-BR")
            ?: return@withContext emptyList()
        val lista = json.optJSONArray("episodes") ?: return@withContext emptyList()
        val out = (0 until lista.length()).mapNotNull { i ->
            val ep = lista.optJSONObject(i) ?: return@mapNotNull null
            EpisodioTmdb(
                numero = ep.optInt("episode_number"),
                nome = ep.optString("name").takeIf { it != "null" }.orEmpty(),
                sinopse = ep.optString("overview").takeIf { it != "null" }.orEmpty(),
                // w300: a imagem ocupa um cartão pequeno na lista.
                imagem = ep.optString("still_path").takeIf { it.isNotBlank() && it != "null" }
                    ?.let { IMAGENS + "w300" + it },
                duracao = ep.optInt("runtime").takeIf { it > 0 },
            )
        }
        synchronized(temporadas) { temporadas[chave] = out }
        out
    }

    /**
     * A chave do trailer no YouTube, dublado de preferência.
     *
     * O TMDB guarda vídeos por idioma; o trailer em português existe para boa
     * parte dos lançamentos, e o original em inglês cobre o resto.
     */
    suspend fun trailer(id: Int, serie: Boolean): String? = withContext(Dispatchers.IO) {
        if (id <= 0) return@withContext null
        val tipo = if (serie) "tv" else "movie"
        for (idioma in listOf("pt-BR", "en-US")) {
            val lista = pedir("$BASE/$tipo/$id/videos?api_key=$CHAVE&language=$idioma")
                ?.optJSONArray("results") ?: continue
            val videos = (0 until lista.length()).mapNotNull { lista.optJSONObject(it) }
                .filter { it.optString("site") == "YouTube" }
            val melhor = videos.firstOrNull { it.optString("type") == "Trailer" }
                ?: videos.firstOrNull { it.optString("type") == "Teaser" }
            melhor?.optString("key")?.takeIf { it.isNotBlank() }?.let { return@withContext it }
        }
        null
    }

    /**
     * "Mais como este": o que o TMDB recomenda **e existe no acervo**.
     *
     * Mesmo cruzamento da filmografia: pelo id, então só aparece o que abre.
     */
    suspend fun parecidos(context: Context, id: Int, serie: Boolean): List<Trabalho> =
        withContext(Dispatchers.IO) {
            if (id <= 0) return@withContext emptyList()
            Generos.carregar(context)
            val tipo = if (serie) "tv" else "movie"
            val vistos = HashSet<String>()
            val saida = mutableListOf<Trabalho>()
            for (rota in listOf("recommendations", "similar")) {
                val lista = pedir("$BASE/$tipo/$id/$rota?api_key=$CHAVE&language=pt-BR")
                    ?.optJSONArray("results") ?: continue
                for (i in 0 until lista.length()) {
                    val item = lista.optJSONObject(i) ?: continue
                    val titulo = Generos.titulo(item.optInt("id"), serie) ?: continue
                    val achado = Vod.achar(context, titulo, serie) ?: continue
                    if (!vistos.add(achado.nomeCompleto)) continue
                    val capa = item.optString("poster_path").takeIf { it.isNotBlank() && it != "null" }
                        ?.let { IMAGENS + "w185" + it } ?: Generos.capa(achado.nomeCompleto, serie)
                    saida += Trabalho(achado, capa, "")
                    if (saida.size >= 20) return@withContext saida
                }
            }
            saida
        }

    /** Pessoas pelo nome, para a busca achar atores também. */
    suspend fun pessoas(termo: String): List<Pessoa> = withContext(Dispatchers.IO) {
        if (termo.trim().length < 3) return@withContext emptyList()
        val q = java.net.URLEncoder.encode(termo.trim(), "UTF-8")
        val lista = pedir("$BASE/search/person?api_key=$CHAVE&language=pt-BR&query=$q")
            ?.optJSONArray("results") ?: return@withContext emptyList()
        (0 until minOf(lista.length(), 10)).mapNotNull { i ->
            val p = lista.optJSONObject(i) ?: return@mapNotNull null
            if (p.optString("known_for_department") != "Acting") return@mapNotNull null
            Pessoa(p.optInt("id"), p.optString("name"), "",
                p.optString("profile_path").takeIf { it.isNotBlank() && it != "null" }
                    ?.let { IMAGENS + "w185" + it })
        }
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
