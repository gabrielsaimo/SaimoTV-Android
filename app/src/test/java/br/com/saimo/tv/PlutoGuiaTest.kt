package br.com.saimo.tv

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/**
 * Todo canal da Pluto no catálogo publicado precisa sair com id da Pluto: é por
 * ele que o guia casa, e canal sem id fica sem programação.
 */
class PlutoGuiaTest {

    private val catalogo: File = generateSequence(File("").absoluteFile) { it.parentFile }
        .map { File(it, "SaimoPlayer/catalogo.txt") }
        .first { it.exists() }

    @Test
    fun `canais da Pluto saem com o id do guia`() {
        val canais = Remote.parse(catalogo.readText())
        val ids = Epg.idsDaPluto(canais)
        val daPluto = canais.filter { c -> c.sources.any { "jmp2.uk/plu-" in it.url } }
        println("pluto: ${ids.size} ids, ${daPluto.size} canais com link da Pluto")
        assertTrue(daPluto.isNotEmpty())
        assertTrue("canal da Pluto sem id", daPluto.all { it.name in ids.values })
        assertTrue(ids.keys.all { it.length == 24 })

        // Onde a Pluto é só reserva, o guia dela não pode passar na frente.
        val principais = Epg.plutoPrincipais(canais)
        assertTrue("TV Cultura" in ids.values)
        assertTrue("TV Cultura" !in principais)
        assertTrue("A Feiticeira" in principais)
    }
}
