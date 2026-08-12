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
        .map { File(it, "SaimoPlayer/canais.txt") }
        .firstOrNull { it.exists() } ?: File("canais.txt")

    @Test
    fun `a lista publicada carrega tudo o que o catalogo tem`() {
        assertTrue("canais.txt não encontrado", published.exists())
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
