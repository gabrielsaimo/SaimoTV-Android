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
        val parsed = Remote.parse(published.readText())

        println("canais: ${parsed.size} | fontes: ${parsed.sumOf { it.sources.size }}")
        assertEquals("número de canais mudou", CATALOG.size, parsed.size)
        assertEquals(CATALOG.map { it.name }, parsed.map { it.name })

        // Logo: nenhum canal pode perder o ícone na travessia.
        val semLogo = parsed.filter { it.logo == null }.map { it.name }
        val esperadoSemLogo = CATALOG.filter { it.logo == null }.map { it.name }
        println("sem logo: $semLogo")
        assertEquals(esperadoSemLogo, semLogo)

        // ClearKey: os dois lados do par, senão o canal DASH não monta licença.
        val comChave = parsed.flatMap { it.sources }.filter { it.key != null }
        val esperado = CATALOG.flatMap { it.sources }.filter { it.key != null }
        println("com ClearKey: ${comChave.size}")
        assertEquals(esperado.size, comChave.size)
        assertTrue("KID faltando", comChave.all { it.keyId?.length == 32 })
        assertTrue("chave faltando", comChave.all { it.key?.length == 32 })

        // Referer e agente vêm de fontes que só tocam com eles.
        val comReferer = parsed.flatMap { it.sources }.count { it.referer != null }
        assertEquals(CATALOG.flatMap { it.sources }.count { it.referer != null }, comReferer)
        val comAgente = parsed.flatMap { it.sources }.count { it.userAgent != null }
        assertEquals(CATALOG.flatMap { it.sources }.count { it.userAgent != null }, comAgente)

        // E as URLs em si, que é o ponto do arquivo.
        assertEquals(CATALOG.flatMap { it.sources }.map { it.url },
            parsed.flatMap { it.sources }.map { it.url })
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
