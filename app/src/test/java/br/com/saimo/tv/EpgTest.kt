package br.com.saimo.tv

import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Runs the guide against the live feeds on the JVM.
 *
 * The parser is plain Kotlin, so it can be checked here instead of only on the
 * box — which is the whole reason the previous breakage went unnoticed.
 */
class EpgTest {

    private val names = CATALOG.map { it.name }
    private val from = System.currentTimeMillis() - 6 * 60 * 60 * 1000L
    private val to = System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000L

    @Test
    fun `xmltv em fluxo casa canais e programas`() {
        val byTitle = HashMap<String, Programme>()
        val started = System.currentTimeMillis()
        val parsed = Epg.parseFeed(
            "https://iptv-epg.org/files/epg-br.xml", names, from, to, byTitle)
        val elapsed = System.currentTimeMillis() - started

        println("canais casados: ${parsed.size} de ${names.size} em ${elapsed}ms")
        println("títulos com pôster: ${byTitle.values.count { it.poster != null }}")
        parsed.entries.sortedBy { it.key }.take(8).forEach { (channel, list) ->
            val onAir = list.firstOrNull { it.isOnAir(System.currentTimeMillis()) }
            println("  $channel: ${list.size} programas | agora: ${onAir?.title ?: "—"}")
        }

        assertTrue("nenhum canal casado", parsed.size >= 20)
        val total = parsed.values.sumOf { it.size }
        assertTrue("poucos programas: $total", total > 1000)
        // Cronologia é o que nowNext assume ao varrer a lista.
        parsed.forEach { (channel, list) ->
            assertTrue("$channel fora de ordem", list.zipWithNext().all { it.first.start <= it.second.start })
        }
    }

    @Test
    fun `meuguia continua entregando a grade`() {
        val html = Epg.download("https://meuguia.tv/programacao/canal/HBO")
        val list = MeuGuia.parse(html)
        println("HBO: ${list.size} entradas | primeira: ${list.firstOrNull()?.title}")
        assertTrue("HBO sem programação", list.size > 10)
        assertTrue(list.zipWithNext().all { it.first.start <= it.second.start })
    }
}
