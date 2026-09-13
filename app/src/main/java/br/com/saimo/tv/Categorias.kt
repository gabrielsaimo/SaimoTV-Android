package br.com.saimo.tv

/**
 * Seções da lista de canais, as mesmas do Mac e do site.
 *
 * O catálogo publicado declara a seção de cada canal (`categoria:`); o nome só
 * decide quando ela falta — no catálogo embutido, ou num canal que só existe
 * na lista de reservas. A regra pelo nome é a do Mac (`Categoria.de` em
 * Model.swift), para os apps não discordarem sobre onde um canal mora.
 */
object Categorias {

    const val FAVORITOS = "Favoritos"

    val ORDEM = listOf(
        "TV Aberta", "Filmes e Séries", "Esportes", "Notícias", "Infantil",
        "Documentários", "Pluto TV", "24 Horas", "Variedades", "Adulto",
    )

    fun de(canal: Channel): String =
        canal.categoria?.takeIf { it in ORDEM } ?: peloNome(canal.name)

    fun peloNome(nome: String): String {
        val n = Epg.normalise(nome)
        fun tem(vararg termos: String) = termos.any { it in n }
        return when {
            tem("pluto tv") -> "Pluto TV"
            tem("adulto", "sexy hot", "playboy", "sex prive", "penthouse", "venus",
                "hustler", "private", "brasileirinhas") -> "Adulto"
            tem("premiere", "sportv", "espn", "combate", "band sports", "nsports",
                "n sports", "xsports", "x sports", "caze", "tnt sports", "fuel",
                "ge tv", "fox sports") && !tem("universal") -> "Esportes"
            tem("telecine", "hbo", "megapix", "cinemax", "paramount", "space", "tnt",
                "amc", "studio universal", "sony", "warner", "axn", "universal",
                "cinemonde", "darkflix", "tcm", "prime box", "movies", "cine") -> "Filmes e Séries"
            tem("cartoon", "gloob", "nick", "discovery kids", "boomerang", "tooncast",
                "infantil", "kids", "cartoonito", "box kids", "ra tim bum", "babyfirst",
                "dumdum", "anime") -> "Infantil"
            tem("discovery", "history", "animal planet", "nat geo", "investigacao",
                "h2", "a e", "curta", "documenta", "science", "theater", "turbo",
                "world", "id ") -> "Documentários"
            tem("news", "globonews", "cnn", "record news", "jovem pan", "bandnews",
                "band news", "cnbc", "euronews", "dw", "times brasil", "uol") -> "Notícias"
            tem("globo", "sbt", "record", "band", "redetv", "rede tv", "tv brasil",
                "cultura", "gazeta", "rede vida", "cancao nova", "aparecida", "senado",
                "camara", "justica", "escola", "futura", "pampa", "cnt", "rede brasil",
                "play tv", "playtv", "sesc") -> "TV Aberta"
            else -> "Variedades"
        }
    }

    /** Seção em que o canal aparece na lista: favorito vai para o topo. */
    fun secao(canal: Channel): String =
        if (Favorites.contains(canal.name)) FAVORITOS else de(canal)

    /** Favoritos primeiro, depois seção por seção, cada uma em ordem alfabética. */
    fun ordenar(canais: List<Channel>): List<Channel> =
        canais.sortedWith(
            compareBy<Channel> { if (Favorites.contains(it.name)) -1 else posicao(de(it)) }
                .thenBy { it.name.lowercase() })

    private fun posicao(categoria: String): Int =
        ORDEM.indexOf(categoria).let { if (it < 0) ORDEM.size else it }

    /**
     * O rótulo a mostrar sobre a linha, ou nulo: só a primeira de cada seção
     * leva, que é o bastante para quem desce a lista saber onde está.
     */
    fun rotulo(canais: List<Channel>, posicao: Int): String? {
        val atual = canais.getOrNull(posicao)?.let { secao(it) } ?: return null
        val anterior = canais.getOrNull(posicao - 1)?.let { secao(it) }
        return if (atual != anterior) atual else null
    }
}
