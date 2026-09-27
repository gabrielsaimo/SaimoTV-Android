package br.com.saimo.tv

import android.content.Context
import androidx.media3.common.util.UnstableApi

/**
 * De um título do acervo ao que dá para tocar.
 *
 * O filme mora no arquivo da letra; a série, no pedaço dela; anime e dorama,
 * nas coleções. Antes cada tela fazia esse caminho do seu jeito — e cada uma
 * errava num lugar diferente. Aqui é um só, usado pela ficha, pelo player e
 * pelo "Continue assistindo".
 */
@UnstableApi
object Titulos {

    /** Uma fonte tocável, com a versão (dublado/legendado) a que pertence. */
    data class Opcao(val versao: String, val url: String)

    /** Um episódio com todas as versões e fontes juntas. */
    data class Ep(val temporada: Int, val numero: Int, val fontes: Map<String, List<String>>) {
        val versoes: Set<String> get() = fontes.keys
    }

    data class Resolvido(
        val alvo: Alvo,
        /// O nome que vai na chave de progresso — o mesmo desde a 1.x.
        val nomeChave: String,
        val filme: Filme?,
        val episodios: List<Ep>,
        val tmdbId: Int,
    ) {
        val serie: Boolean get() = filme == null
        val temporadas: List<Int> get() = episodios.map { it.temporada }.distinct().sorted()

        fun chave(ep: Ep?): String =
            if (ep == null) Progresso.chaveFilme(nomeChave)
            else Progresso.chaveEpisodio(nomeChave, ep.temporada, ep.numero)

        fun episodio(temporada: Int, numero: Int) =
            episodios.firstOrNull { it.temporada == temporada && it.numero == numero }

        fun seguinte(ep: Ep): Ep? {
            val i = episodios.indexOf(ep)
            return if (i < 0) null else episodios.getOrNull(i + 1)
        }

        fun anterior(ep: Ep): Ep? {
            val i = episodios.indexOf(ep)
            return if (i <= 0) null else episodios.getOrNull(i - 1)
        }

        val endereco: Progresso.Endereco
            get() = Progresso.Endereco(alvo.titulo, alvo.serie, alvo.letra, alvo.ano, alvo.colecao, tmdbId)
    }

    suspend fun resolver(context: Context, alvo: Alvo): Resolvido? {
        Generos.carregar(context)
        if (alvo.colecao.isNotEmpty()) {
            val achado = Vod.colecao(context, alvo.colecao)
                .firstOrNull { it.titulo.equals(alvo.titulo, ignoreCase = true) } ?: return null
            val tmdb = alvo.tmdbId.takeIf { it > 0 } ?: achado.tmdbId.toIntOrNull() ?: 0
            return Resolvido(alvo, Serie(achado.titulo, achado.ano, -1, 0).nomeCompleto, null,
                agrupar(achado.episodios), tmdb)
        }
        val achado = Vod.Achado(alvo.titulo, alvo.serie, alvo.letra, alvo.ano)
        val tmdb = alvo.tmdbId.takeIf { it > 0 } ?: Generos.id(alvo.nomeCompleto, alvo.serie)
            ?: Generos.id(alvo.titulo, alvo.serie) ?: 0
        return if (alvo.serie) {
            val serie = Vod.serie(context, achado) ?: return null
            val eps = Vod.episodios(context, alvo.letra, serie)
            if (eps.isEmpty()) return null
            Resolvido(alvo, serie.nomeCompleto, null, agrupar(eps), tmdb)
        } else {
            val filme = juntarGrafias(context, achado) ?: return null
            Resolvido(alvo, filme.titulo, filme, emptyList(), tmdb)
        }
    }

    /**
     * O filme com as fontes de todas as grafias do mesmo título.
     *
     * O acervo junta listas de origens diferentes, e o mesmo filme aparece
     * duas vezes: "Backrooms Um Nao-Lugar (2026)" com um servidor só (que
     * estava desligado) e "Backrooms: Um Não-Lugar" com vários. Abrir a
     * primeira dava "título indisponível" com o filme ali do lado. Aqui as
     * duas viram uma — a pedida primeiro, as outras como reserva.
     */
    private suspend fun juntarGrafias(context: Context, achado: Vod.Achado): Filme? {
        val chave = Vod.normalizar(Generos.semAno(achado.titulo))
        val daLetra = Vod.filmes(context, achado.letra)
        val iguais = daLetra.filter { Vod.normalizar(Generos.semAno(it.titulo)) == chave }
        val pedido = daLetra.firstOrNull { it.titulo == achado.titulo }
            ?: Vod.filme(context, achado)
        val todos = listOfNotNull(pedido) + iguais.filter { it.titulo != pedido?.titulo }
        if (todos.isEmpty()) return null
        val fontes = linkedMapOf<String, List<String>>()
        for (f in todos) for ((v, urls) in f.fontes) fontes[v] = (fontes[v].orEmpty() + urls).distinct()
        return Filme(pedido?.titulo ?: achado.titulo, fontes)
    }

    private fun agrupar(eps: List<Episodio>): List<Ep> =
        eps.groupBy { it.temporada to it.numero }
            .map { (chave, lista) ->
                val fontes = linkedMapOf<String, List<String>>()
                for (e in lista) fontes[e.versao] = (fontes[e.versao].orEmpty() + e.urls).distinct()
                Ep(chave.first, chave.second, fontes)
            }
            .sortedWith(compareBy({ it.temporada }, { it.numero }))

    /**
     * Todas as fontes, na ordem em que devem ser tentadas.
     *
     * A versão escolhida vem primeiro, com todas as suas fontes; as outras
     * versões ficam atrás, como último recurso — melhor legendado que nada.
     */
    fun ordem(fontes: Map<String, List<String>>, versao: String, soEssa: Boolean = false): List<Opcao> {
        val versoes = fontes.keys.sortedBy { if (it == versao) 0 else if (it == "dub") 1 else 2 }
        val out = mutableListOf<Opcao>()
        for (v in versoes) {
            if (soEssa && v != versao && out.isNotEmpty()) continue
            fontes[v].orEmpty().forEach { out += Opcao(v, it) }
        }
        return out
    }

    fun rotulo(versao: String) = if (versao == "leg") "Legendado" else "Dublado"

    /** Episódio por onde uma série deve começar ou continuar. */
    fun ondeContinuar(context: Context, r: Resolvido): Ep? {
        if (r.episodios.isEmpty()) return null
        val ultimo = Progresso.ultimoEpisodio(context, r.nomeChave)
            ?.let { (t, e) -> r.episodio(t, e) }
        if (ultimo != null) {
            val chave = r.chave(ultimo)
            val terminou = Progresso.visto(context, chave) && Progresso.posicao(context, chave) <= 0
            return if (terminou) r.seguinte(ultimo) ?: ultimo else ultimo
        }
        return r.episodios.first()
    }
}
