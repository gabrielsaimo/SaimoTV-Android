package br.com.saimo.tv

import org.junit.Assert.*
import org.junit.Test

class VodFormatoTest {
    private val bases = listOf("https://antiga.example/movie/")

    @Test fun `id tmdb sem dois pontos reproduz a excecao do leitor antigo`() {
        val valor = "37308"
        assertThrows(IllegalArgumentException::class.java) { valor.take(valor.indexOf(':')) }
        assertEquals("", VodFormato.montar(valor, bases))
    }

    @Test fun `metadados novos nao viram fontes nem derrubam filmes antigos`() {
        val linhas = listOf(
            "Novo\tdub=https://nova.example/filme.mp4\ttmdb=37308\timdb=tt1234567",
            "Antigo sem Nexus\tdub=0:123,https://reserva.example/a.m3u8\tleg=0:456.mkv"
        )
        val parsed = linhas.map { VodFormato.fontes(it.split('\t').drop(1), bases) }
        assertEquals(setOf("dub"), parsed[0].keys)
        assertEquals(listOf("https://antiga.example/movie/123.mp4", "https://reserva.example/a.m3u8"), parsed[1]["dub"])
        assertEquals(listOf("https://antiga.example/movie/456.mkv"), parsed[1]["leg"])
    }

    @Test fun `tokens ruins sao ignorados preservando todas as fontes validas em ordem`() {
        for (item in listOf("", "123", "tt123", ":123", "0:", "-1:20", "50:20", "abc:20"))
            assertEquals(item, "", VodFormato.montar(item, bases))
        assertEquals(listOf("https://a.example/v.mp4", "https://b.example/v.mp4"),
            VodFormato.fontes(listOf("dub=123,https://a.example/v.mp4", "dub=https://b.example/v.mp4", "tmdb=https://metadata.example"), bases)["dub"])
    }
}
