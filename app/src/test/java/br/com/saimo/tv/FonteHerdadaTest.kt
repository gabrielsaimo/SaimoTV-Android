package br.com.saimo.tv

import org.junit.Assert.assertEquals
import org.junit.Test

class FonteHerdadaTest {
    private fun o(v: String, u: String) = Titulos.Opcao(v, u)

    @Test fun mesmoServidorMesmoComListaEmOutraOrdem() {
        val ops = listOf(o("dub", "https://a.com/1"), o("dub", "https://www.c.com/2"), o("dub", "https://b.com/3"))
        assertEquals(1, Titulos.preferida(ops, "c.com", 2))
    }

    @Test fun semServidorUsaAPosicao() {
        val ops = listOf(o("dub", "https://a.com/1"), o("dub", "https://b.com/2"), o("dub", "https://d.com/3"))
        assertEquals(2, Titulos.preferida(ops, "x.com", 2))
    }

    @Test fun semNadaUsaAOrdemDeHoje() {
        val ops = listOf(o("dub", "https://a.com/1"))
        assertEquals(0, Titulos.preferida(ops, "x.com", 5))
    }

    @Test fun naoSaiDaVersaoEscolhida() {
        val ops = listOf(o("dub", "https://a.com/1"), o("leg", "https://c.com/2"))
        assertEquals(0, Titulos.preferida(ops, "c.com", 0))
    }
}
