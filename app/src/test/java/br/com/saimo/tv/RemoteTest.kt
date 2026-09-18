package br.com.saimo.tv

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/**
 * Reads the very file that gets published, not a fixture.
 *
 * The published list is what the app plays, so the check that matters is that
 * nothing is lost between the catalogue and the file: a missing logo, referer or
 * ClearKey is a channel that stops working for everyone at once.
 */
class RemoteTest {

    /// O Gradle roda o teste a partir de app/, então o arquivo é procurado
    /// subindo, em vez de fixar quantos níveis são.
    private val published: File = generateSequence(File("").absoluteFile) { it.parentFile }
        .map { File(it, "SaimoPlayer/catalogo.txt") }
        .firstOrNull { it.exists() } ?: File("canais.txt")

    @Test
    fun `a lista restrita publicada bate com a reserva embutida`() {
        val arquivo = File(published.parentFile, "restritos.txt")
        assertTrue("restritos.txt não encontrado", arquivo.exists())
        val parsed = Remote.parse(arquivo.readText())
        assertEquals(RESTRICTED.map { it.name }, parsed.map { it.name })
        assertTrue("Adulto 08 voltou", parsed.none { it.name.startsWith("Adulto ") })
        assertEquals("nome repetido", parsed.size, parsed.map { it.name }.toSet().size)
        assertTrue("canal sem categoria", parsed.all { it.categoria == "Adulto" })
    }

    @Test
    fun `a lista publicada carrega tudo o que o catalogo tem`() {
        assertTrue("catalogo.txt não encontrado", published.exists())
        val text = published.readText()
        val parsed = Remote.parse(text)
        // A lista remota evolui sem recompilar a reserva CATALOG. Compare com
        // os campos do arquivo atual, não com a quantidade antiga embutida.
        val blocks = text.split(Regex("(?m)^canal: *")).drop(1)
        assertTrue("catálogo vazio", blocks.isNotEmpty())
        assertEquals(blocks.map { it.lineSequence().first().trim() }, parsed.map { it.name })
        fun field(block: String, name: String): String? = block.lineSequence()
            .map { it.trim() }.firstOrNull { it.startsWith("$name:") }
            ?.substringAfter(':')?.trim()?.takeIf { it.isNotEmpty() }
        for ((block, channel) in blocks.zip(parsed)) {
            val fallbackLogo = CATALOG.firstOrNull { it.name.equals(channel.name, ignoreCase = true) }?.logo
            assertEquals(field(block, "logo") ?: fallbackLogo, channel.logo)
            assertEquals(field(block, "categoria"), channel.categoria)
            val sources = block.split(Regex("(?m)^fonte: *")).drop(1)
            assertEquals("fontes de ${channel.name}", sources.size, channel.sources.size)
            for ((raw, source) in sources.zip(channel.sources)) {
                assertEquals(raw.lineSequence().first().trim(), source.url)
                assertEquals(field(raw, "referer"), source.referer)
                assertEquals(field(raw, "agente"), source.userAgent)
                val pair = field(raw, "chave")?.split(':')
                if (pair != null) {
                    assertEquals(2, pair.size)
                    assertEquals(pair[0].trim(), source.keyId)
                    assertEquals(pair[1].trim(), source.key)
                    assertTrue(source.keyId?.matches(Regex("[0-9a-fA-F]{32}")) == true)
                    assertTrue(source.key?.matches(Regex("[0-9a-fA-F]{32}")) == true)
                } else {
                    assertEquals(null, source.keyId)
                    assertEquals(null, source.key)
                }
            }
        }
    }

    @Test
    fun `lista quebrada nao derruba o app`() {
        assertTrue(Remote.parse("").isEmpty())
        assertTrue(Remote.parse("qualquer coisa\nsem formato nenhum").isEmpty())
        // Um canal sem fonte não entra: seria uma linha que nunca toca.
        assertTrue(Remote.parse("canal: Fantasma\nlogo: https://x/y.png").isEmpty())
        // Chave sem par descarta a fonte em vez de tocar sem licença.
        val meio = Remote.parse("canal: X\nfonte: https://x/y.mpd\nchave: soaqui")
        assertTrue(meio.isEmpty())
    }

    @Test
    fun `formato antigo nao vira lista vazia por engano`() {
        // O arquivo que está publicado hoje é a lista antiga, legível mas sem
        // campos. Tem de dar zero, para o app cair no catálogo embutido.
        val antigo = """
            SAIMO TV — LISTA DE CANAIS
            71 canais · gerado em 11/08/2026

              1. A&E
                 https://exemplo/aie.m3u8
        """.trimIndent()
        assertTrue(Remote.parse(antigo).isEmpty())
    }
}

/** O par do ClearKey tem de sobreviver inteiro até a licença. */
class ClearKeyTest {

    @Test
    fun `toda fonte DASH tem KID e chave utilizaveis`() {
        val dash = CATALOG.flatMap { c -> c.sources.map { c.name to it } }
            .filter { it.second.isDash }
        val comChave = dash.filter { it.second.key != null }
        println("fontes DASH: ${dash.size} | com chave: ${comChave.size}")

        val semChave = dash.filter { it.second.key == null }.map { it.first }
        println("DASH sem chave: $semChave")

        for ((nome, source) in comChave) {
            val kid = source.keyId
            val key = source.key
            assertTrue("$nome: KID inválido ($kid)", kid != null && kid.matches(Regex("[0-9a-fA-F]{32}")))
            assertTrue("$nome: chave inválida ($key)", key != null && key.matches(Regex("[0-9a-fA-F]{32}")))
            assertTrue("$nome: KID igual à chave, par trocado", kid != key)
        }
    }

    @Test
    fun `a lista publicada preserva o par`() {
        val published = generateSequence(File("").absoluteFile) { it.parentFile }
            .map { File(it, "SaimoPlayer/catalogo.txt") }
            .first { it.exists() }
        val parsed = Remote.parse(published.readText())
        val doArquivo = parsed.flatMap { it.sources }.filter { it.key != null }
        val doCatalogo = CATALOG.flatMap { it.sources }.filter { it.key != null }
        assertEquals(doCatalogo.map { it.keyId }, doArquivo.map { it.keyId })
        assertEquals(doCatalogo.map { it.key }, doArquivo.map { it.key })
    }
}

/** A lista publicada soma ao catálogo; não substitui. */
class MergeTest {

    private val m3u = """
        #EXTM3U
        #EXTINF:-1 tvg-id="A&E" tvg-logo="https://exemplo/ae.png" group-title="X", A&E (ST)
        http://exemplo/ae-reserva.ts
        #EXTINF:-1 tvg-id="Canal Novo" tvg-logo="https://exemplo/novo.png" group-title="X", Canal Novo
        http://exemplo/novo.ts
    """.trimIndent()

    @Test
    fun `fontes publicadas entram como reserva, sem apagar as do catalogo`() {
        val publicada = Remote.parse(m3u)
        val merged = Remote.mergeForTest(publicada)

        val ae = merged.first { it.name == "A&E" }
        val original = CATALOG.first { it.name == "A&E" }
        // As do catálogo continuam na frente, na mesma ordem.
        assertEquals(original.sources.map { it.url }, ae.sources.dropLast(1).map { it.url })
        assertEquals("http://exemplo/ae-reserva.ts", ae.sources.last().url)
        // E a chave do ClearKey não se perdeu no caminho.
        assertEquals(original.sources.map { it.key }, ae.sources.dropLast(1).map { it.key })

        // O que só existe na lista publicada entra como canal novo, no fim.
        assertEquals("Canal Novo", merged.last().name)
        assertEquals(CATALOG.size + 1, merged.size)
    }
}
