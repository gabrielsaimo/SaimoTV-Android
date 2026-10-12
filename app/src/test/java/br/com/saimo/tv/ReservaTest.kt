package br.com.saimo.tv

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/** A lista de reserva sai do recurso dentro do APK, pelo mesmo parser da baixada. */
class ReservaTest {

    @Test
    fun `reserva carrega os canais com fontes`() {
        assertTrue("reserva vazia", CATALOG.size > 100)
        assertTrue(CATALOG.all { it.sources.isNotEmpty() })
        assertTrue("sem nenhuma qualidade", CATALOG.any { c -> c.sources.any { it.quality != null } })
    }

    @Test
    fun `restrita leva categoria`() {
        assertTrue(RESTRICTED.isNotEmpty())
        assertTrue(RESTRICTED.all { it.categoria != null })
    }

    @Test
    fun `mesma instancia a cada uso`() {
        assertTrue(CATALOG === CATALOG)
        assertEquals(CATALOG.size, Remote.lerCampos(
            Channel::class.java.classLoader!!.getResourceAsStream("reserva/catalogo.txt")!!.bufferedReader().readText()).size)
    }
}
