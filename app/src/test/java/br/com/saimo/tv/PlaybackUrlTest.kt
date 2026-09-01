package br.com.saimo.tv

import org.junit.Assert.assertEquals
import org.junit.Test

class PlaybackUrlTest {

    private val source =
        "https://xn--proxy_com_underscore.null-null.shop/tos-alisg-avt-0068/proxy.m3u8" +
            "?container=images&url=https://neosoro.gq/docs/amc/__index.m3u8?sv=47&cc=y"

    @Test
    fun `segmento relativo usa pasta indicada pelo parametro url`() {
        val request =
            "https://xn--proxy_com_underscore.null-null.shop/tos-alisg-avt-0068/1788290944435.pdf"

        assertEquals(
            "https://xn--proxy_com_underscore.null-null.shop/docs/amc/1788290944435.pdf",
            Playback.corrigirCaminhoDaPlaylist(source, request))
    }

    @Test
    fun `query do segmento e preservada`() {
        val request =
            "https://xn--proxy_com_underscore.null-null.shop/tos-alisg-avt-0068/segment.ts?token=abc"

        assertEquals(
            "https://xn--proxy_com_underscore.null-null.shop/docs/amc/segment.ts?token=abc",
            Playback.corrigirCaminhoDaPlaylist(source, request))
    }

    @Test
    fun `recarregamento da playlist permanece intacto`() {
        assertEquals(source, Playback.corrigirCaminhoDaPlaylist(source, source))
    }

    @Test
    fun `url absoluta de outro host permanece intacta`() {
        val request = "https://cdn.example.com/live/segment.ts"
        assertEquals(request, Playback.corrigirCaminhoDaPlaylist(source, request))
    }

    @Test
    fun `playlist comum sem url aninhada permanece intacta`() {
        val ordinary = "https://cdn.example.com/live/index.m3u8?token=abc"
        val request = "https://cdn.example.com/live/segment.ts"
        assertEquals(request, Playback.corrigirCaminhoDaPlaylist(ordinary, request))
    }
}
