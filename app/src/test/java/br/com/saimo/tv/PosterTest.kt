package br.com.saimo.tv

import kotlinx.coroutines.runBlocking
import org.junit.Test

/** Quanto do guia realmente ganha imagem, canal a canal. */
class PosterTest {

    @Test
    fun `cobertura de posters nos canais do meuguia`() = runBlocking {
        val names = CATALOG.map { it.name }
        val from = System.currentTimeMillis() - 6 * 60 * 60 * 1000L
        val to = System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000L

        val guia = MeuGuia.fetch(names, from, to)
        val byTitle = HashMap<String, Programme>()
        val feed = Epg.parseFeed("https://iptv-epg.org/files/epg-br.xml", names, from, to, byTitle)

        println("meuguia: ${guia.size} canais | feed: ${feed.size} canais")
        println("títulos distintos com pôster no feed: ${byTitle.values.count { it.poster != null }}")

        var total = 0
        var byTitleHit = 0
        var byOverlapHit = 0
        for ((channel, programmes) in guia) {
            val sameChannel = feed[channel]
            var hitsTitle = 0
            var hitsOverlap = 0
            for (programme in programmes) {
                total++
                if (byTitle[Epg.normalise(programme.title)]?.poster != null) {
                    hitsTitle++
                    byTitleHit++
                }
                val mid = (programme.start + programme.stop) / 2
                val overlap = sameChannel?.firstOrNull { it.start <= mid && it.stop > mid }
                if (overlap?.poster != null) {
                    hitsOverlap++
                    byOverlapHit++
                    if (hitsOverlap <= 2 && Epg.normalise(overlap.title) != Epg.normalise(programme.title)) {
                        println("  divergência em $channel: meuguia='${programme.title}' feed='${overlap.title}'")
                    }
                }
            }
            println("  $channel: ${programmes.size} programas | título $hitsTitle | horário $hitsOverlap")
        }
        println("TOTAL $total | por título $byTitleHit | por horário $byOverlapHit")
    }
}
