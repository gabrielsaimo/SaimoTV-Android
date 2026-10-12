package br.com.saimo.tv

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

/** A conta de datas do guia, sem Calendar, tem de bater com a do Java. */
class EpgDataTest {

    @Test
    fun `dias desde 1970 batem com o java time`() {
        var dia = LocalDate.of(1999, 12, 25)
        val fim = LocalDate.of(2031, 3, 5)
        while (dia <= fim) {
            assertEquals(dia.toString(), dia.toEpochDay(), Epg.diasDesde1970(dia.year, dia.monthValue, dia.dayOfMonth))
            dia = dia.plusDays(1)
        }
    }
}
