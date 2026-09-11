package br.com.saimo.tv

// Gerado a partir de SaimoPlayer/Sources/Channels.swift — não editar à mão.
// Regenerar com scripts/gen_catalog.py para manter Mac e TV Box iguais.

data class Source(
    val url: String,
    val referer: String? = null,
    val userAgent: String? = null,
    /// Par KID:chave do ClearKey, em hexadecimal, para as fontes DASH.
    val keyId: String? = null,
    val key: String? = null,
) {
    val isDash: Boolean get() = url.contains(".mpd", ignoreCase = true)
}

data class Channel(
    val name: String,
    val logo: String? = null,
    val sources: List<Source>,
)


val CATALOG: List<Channel> = listOf(
    Channel(
        name = "A&E",
        logo = "https://www.tvlogo.org/brazil/a-and-e-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/AE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Adult Swim",
        logo = "https://mondrian.claro.com.br/channels/inverse/adult-swim.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TRUTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "AMC",
        logo = "https://mondrian.claro.com.br/channels/inverse/amc.png",
        sources = listOf(
            Source(
                url = "https://xn--l---------------------------_________________________-2w85c.null-null.shop/tos-alisg-avt-0068/proxy.m3u8?container=images&refresh=10&url=https://neosoro.gq/docs/amc/__index.m3u8?sv=12&cc=y&secure_uri=true&nu3zAQc9HC3GbwJq=1788473027-1p5ON3MLfzAs8siCd4PYlYy%2FbYd54KR6B6BZBJq8I%2Bo%3D",
            ),
        ),
    ),
    Channel(
        name = "Animal Planet",
        logo = "https://mondrian.claro.com.br/channels/inverse/animal-planet.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/ANIMAL_PLANET_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Band",
        logo = "https://mondrian.claro.com.br/channels/inverse/band.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/BAND_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Cartoon Network",
        logo = "https://mondrian.claro.com.br/channels/inverse/cartoon-network.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/CARTOON_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "CazéTV",
        logo = "https://commons.wikimedia.org/wiki/Special:FilePath/Caz%C3%A9TV_wordmark.svg?width=300",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/CAZE_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "CNN Brasil",
        logo = "https://mondrian.claro.com.br/channels/inverse/cnn-brasil.png",
        sources = listOf(
            Source(
                url = "https://amg01391-sbtinfast-amg01391c4-lg-br-4597.playouts.now.amagi.tv/playlist/amg01391-addigital-cnnbrasil-lgbr/playlist.m3u8",
                userAgent = "Mozilla/5.0 (Linux; U; Android 13; T610K Build/TP1A.220624.014; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/126.0.6478.71 Mobile Safari/537.36 OPR/87.0.2254.75258",
            ),
        ),
    ),
    Channel(
        name = "CNN Brasil Money",
        logo = "https://mondrian.claro.com.br/channels/inverse/cnn-brasil-money.png",
        sources = listOf(
            Source(
                url = "https://amg01391-amg01391c57-amgplt0026.playout.now3.amagi.tv/playlist/amg01391-amg01391c57-amgplt0026/playlist.m3u8",
                userAgent = "Mozilla/5.0 (Linux; U; Android 13; T610K Build/TP1A.220624.014; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/126.0.6478.71 Mobile Safari/537.36 OPR/87.0.2254.75258",
            ),
        ),
    ),
    Channel(
        name = "E!",
        logo = "https://commons.wikimedia.org/wiki/Special:FilePath/E%21_Logo_Flat_2012.svg?width=300",
        sources = listOf(
            Source(
                url = "https://video49.mais.uol.com.br/live/4503.mpd",
                referer = "https://painel.play.uol.com.br/",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "d021c9ff8ab94c1a583a0f5f2cc82725",
                key = "7de3fcc29e3194b9c65282a42cb7bec6",
            ),
        ),
    ),
    Channel(
        name = "GE TV",
        logo = "https://mondrian.claro.com.br/channels/inverse/ge-tv.png",
        sources = listOf(
            Source(
                url = "https://dfr80qz435crc.cloudfront.net/EFGH/Amagi/Globo/GE_Fast_BR/GE_Fast.m3u8",
                userAgent = "Mozilla/5.0 (Linux; U; Android 13; T610K Build/TP1A.220624.014; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/126.0.6478.71 Mobile Safari/537.36 OPR/87.0.2254.75258",
            ),
        ),
    ),
    Channel(
        name = "Globo RJ",
        logo = "https://mondrian.claro.com.br/channels/inverse/globo.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/GLOBO_RIO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "GloboNews",
        logo = "https://mondrian.claro.com.br/channels/inverse/globonews.png",
        sources = listOf(
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/dsfrp5mjrb/out/v1/9fa07e663bc94e9f93c53726a558478a/cenc.mpd",
                referer = "https://www.primevideo.com/",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "57ecd6a2086b99cc1d0452b102a7043b",
                key = "11386090a315fc1e88427aeed4a60900",
            ),
            Source(
                url = "http://79.127.238.228:14093",
            ),
        ),
    ),
    Channel(
        name = "Globoplay Novelas",
        logo = "https://mondrian.claro.com.br/channels/inverse/globoplay-novelas.png",
        sources = listOf(
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/ds9ertnhrl/out/v1/cb791b7362754ba1b87d9474ccd95fa3/cenc.mpd",
                referer = "https://www.primevideo.com/",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "eab4523b0358f3c59f1da92b3f478232",
                key = "253ef14355d987d4076b4544e4741977",
            ),
            Source(
                url = "http://79.127.238.228:14455",
            ),
        ),
    ),
    Channel(
        name = "GNT",
        logo = "https://mondrian.claro.com.br/channels/inverse/gnt.png",
        sources = listOf(
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/h9c8z9m1dq/out/v1/9b1b1aa15b4f471ea19674290554499e/cenc.mpd",
                referer = "https://www.primevideo.com/",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "4c4d19c8cda9e78bae924e07ce49cb04",
                key = "4a3a155e480a67b81c5492befe07fa61",
            ),
            Source(
                url = "http://79.127.238.228:14402",
            ),
        ),
    ),
    Channel(
        name = "History",
        logo = "https://mondrian.claro.com.br/channels/inverse/history-channel.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/HISTORY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://46.151.196.223:14410",
            ),
        ),
    ),
    Channel(
        name = "History 2",
        logo = "https://mondrian.claro.com.br/channels/inverse/history-2.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/H2_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Jovem Pan News",
        logo = "https://www.tvlogo.org/brazil/jovem-pan-news-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/JP_NEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://amg01391-sbtinfast-amg01391c3-lg-us-8995.playouts.now.amagi.tv/playlist/amg01391-addigital-jovempan-lgus/playlist.m3u8",
                userAgent = "Mozilla/5.0 (Linux; U; Android 13; T610K Build/TP1A.220624.014; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/126.0.6478.71 Mobile Safari/537.36 OPR/87.0.2254.75258",
            ),
        ),
    ),
    Channel(
        name = "Megapix",
        logo = "https://mondrian.claro.com.br/channels/inverse/megapix.png",
        sources = listOf(
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/21ilsertww/out/v1/124c84cbafc745b6b2c47fc9be606727/cenc.mpd",
                referer = "https://www.primevideo.com/",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "cf8a2c054a3148309bce1039a9a5d603",
                key = "9417daf3a25dff3f78d76c1ebb550654",
            ),
            Source(
                url = "http://79.127.238.228:14592",
            ),
        ),
    ),
    Channel(
        name = "Multishow",
        logo = "https://mondrian.claro.com.br/channels/inverse/multishow.png",
        sources = listOf(
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/x7aaupxajb/out/v1/49d602c6294147a18d798ce6abbb6957/cenc.mpd",
                referer = "https://www.primevideo.com/",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "5a93ff790eca6f86ad6adb53929fe1c4",
                key = "6664fa23d82cbfeaf5cdb2d662bec3b3",
            ),
        ),
    ),
    Channel(
        name = "Premiere 2",
        logo = "https://mondrian.claro.com.br/channels/inverse/premiere.png",
        sources = listOf(
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/oy6rp0jwmf/out/v1/580ecf12bad24979baf8dd993dce053e/cenc.mpd",
                referer = "https://www.primevideo.com/",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "9dc40460c93087aea84d6315f08ecb64",
                key = "f69c8d4624fddff4ca89bd0b31bdc4a7",
            ),
        ),
    ),
    Channel(
        name = "Premiere 3",
        logo = "https://mondrian.claro.com.br/channels/inverse/premiere.png",
        sources = listOf(
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/6onrfniyry/out/v1/f23069c61dbf4e00890a40b705a84079/cenc.mpd",
                referer = "https://www.primevideo.com/",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "d23f7433798a652a7d4f6791d9e1036c",
                key = "4942eebd598b5727c5cc484cc62b52e8",
            ),
        ),
    ),
    Channel(
        name = "Premiere 4",
        logo = "https://mondrian.claro.com.br/channels/inverse/premiere.png",
        sources = listOf(
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/tirjor64kh/out/v1/fd2ed9916d994f09a3bd62b64141b9cb/cenc.mpd",
                referer = "https://www.primevideo.com/",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "e23365c2ad97870c871712b73f0d6195",
                key = "58709c714320bb862dbd07270df81c94",
            ),
        ),
    ),
    Channel(
        name = "Premiere 5",
        logo = "https://mondrian.claro.com.br/channels/inverse/premiere.png",
        sources = listOf(
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/1obktrybht/out/v1/08265453c8f64d9fbeb3cf43764403a8/cenc.mpd",
                referer = "https://www.primevideo.com/",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "332f62eb3cae824e98a4124da29a7d31",
                key = "d1698cda3d040f9051125a61745b596b",
            ),
        ),
    ),
    Channel(
        name = "Premiere 6",
        logo = "https://mondrian.claro.com.br/channels/inverse/premiere.png",
        sources = listOf(
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/0bmtb2fxcj/out/v1/b5f50c3632264d32bf857652f631b0fb/cenc.mpd",
                referer = "https://www.primevideo.com/",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "8bf16a4ba05bcc97e6259297e50be63d",
                key = "8dd97d486cbdd15cc47ea8c41b264ebb",
            ),
        ),
    ),
    Channel(
        name = "Premiere 7",
        logo = "https://mondrian.claro.com.br/channels/inverse/premiere.png",
        sources = listOf(
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/joij38hkop/out/v1/c920c9b42af24588a253530ed2cbd6eb/cenc.mpd",
                referer = "https://www.primevideo.com/",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "238f5cf32228c1877f8b939f5f9d7bd8",
                key = "47571c93e4335b3d1590a5ae3f5c48ef",
            ),
        ),
    ),
    Channel(
        name = "Premiere 8",
        logo = "https://mondrian.claro.com.br/channels/inverse/premiere.png",
        sources = listOf(
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/2s8gkqz2id/out/v1/41da2546a9a34238b8615d3beb4ee600/cenc.mpd",
                referer = "https://www.primevideo.com/",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "19aec31e45751958c1d963435d725f33",
                key = "fb24357e80520fd600dd43c1d8ce8a7a",
            ),
        ),
    ),
    Channel(
        name = "Premiere Clubes",
        logo = "https://mondrian.claro.com.br/channels/inverse/premiere.png",
        sources = listOf(
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/nelfyucw9a/out/v1/6ffb2c365ad14f88b154591beb43d1f6/cenc.mpd",
                referer = "https://www.primevideo.com/",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "56b79c1782b30e6b6fc973b0e8fd4104",
                key = "fa38aaa865a57eda7c77444697ba8ed3",
            ),
        ),
    ),
    Channel(
        name = "SBT",
        logo = "https://mondrian.claro.com.br/channels/inverse/sbt.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/SBT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "SBT News",
        logo = "https://mondrian.claro.com.br/channels/inverse/sbt-news.png",
        sources = listOf(
            Source(
                url = "https://sbtnews.maissbt.com/index.m3u8",
                referer = "https://mais.sbt.com.br/",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
            ),
        ),
    ),
    Channel(
        name = "Sony Channel",
        logo = "https://mondrian.claro.com.br/channels/inverse/sony.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/SONY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "SporTV",
        logo = "https://mondrian.claro.com.br/channels/inverse/sportv-3.png",
        sources = listOf(
            Source(
                url = "https://p17-common-sign.dynamic.pages.cloudflareusercontent.com/tos-alisg-avt-0068/proxy?container=images&refresh=10&url=https://neosoro.gq/docs/sportv1/__index.m3u8?sv=159&cc=y&secure_uri=true&nu3zAQc9HC3GbwJq=1786408554-MK0K7%2F0RIabb2i7ktFkDI1P2aEeyuwRUwvOxauQ5e1c%3D",
            ),
        ),
    ),
    Channel(
        name = "SporTV 2",
        logo = "https://mondrian.claro.com.br/channels/inverse/sportv-3.png",
        sources = listOf(
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/dsa3hwuhd1/out/v1/631b48c8d9ea437e8309d1a4b55acef5/cenc.mpd",
                referer = "https://www.primevideo.com/",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "3de028eafb3b2caffec03be1c1c818b3",
                key = "8fbdd8a9ae6748696bb13e547bb093fc",
            ),
        ),
    ),
    Channel(
        name = "SporTV 3",
        logo = "https://mondrian.claro.com.br/channels/inverse/sportv-3.png",
        sources = listOf(
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/6otiglnptp/out/v1/add7499679b0422cb6791f7701f95ecc/cenc.mpd",
                referer = "https://www.primevideo.com/",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "902e5ec0e3d05e665daa32fc23f4f59e",
                key = "7b2322a273843921a43e2c61dac7cae3",
            ),
        ),
    ),
    Channel(
        name = "Telecine Action",
        logo = "https://upload.wikimedia.org/wikipedia/commons/3/37/Telecine_Action_2.png",
        sources = listOf(
            Source(
                url = "http://46.151.196.223:14326",
            ),
        ),
    ),
    Channel(
        name = "Telecine Pipoca",
        logo = "https://upload.wikimedia.org/wikipedia/commons/2/2f/Telecine_Pipoca_%282021%29.png",
        sources = listOf(
            Source(
                url = "https://xn--l---------------------------_________________________-2w85c.null-null.shop/tos-alisg-avt-0068/proxy.m3u8?container=images&refresh=10&url=https://neosoro.gq/docs/telecinepipoca/__index.m3u8?sv=39&cc=y&secure_uri=true&nu3zAQc9HC3GbwJq=1788472639-q0t4kfoYFwuz0fd9JMSAa%2B%2B62MGZTiRr2rqnMJx96lU%3D",
            ),
        ),
    ),
    Channel(
        name = "Telecine Premium",
        logo = "https://upload.wikimedia.org/wikipedia/commons/1/11/App-telecine-premium-252x252.png",
        sources = listOf(
            Source(
                url = "https://xn--l---------------------------_________________________-2w85c.null-null.shop/tos-alisg-avt-0068/proxy.m3u8?container=images&refresh=10&url=https://neosoro.gq/docs/telecinepremium/__index.m3u8?sv=96&cc=y&secure_uri=true&nu3zAQc9HC3GbwJq=1788472691-FnJa9ar3%2BK%2B9Fsm2Hh%2Bec16N3WQUmrIk78qNE9Mwzic%3D",
            ),
        ),
    ),
    Channel(
        name = "TV Brasil",
        logo = "https://mondrian.claro.com.br/channels/inverse/tv-brasil.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TV_BRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TVE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Universal Premiere",
        logo = "https://mondrian.claro.com.br/channels/inverse/universal-premiere.png",
        sources = listOf(
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/khnyllds8v/out/v1/1cf61b7a057e4ffdb31f6d82fb24c679/cenc.mpd",
                referer = "https://www.primevideo.com/",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "3531c1c49b8e63c0b94a8061154e0c58",
                key = "90e91e6549d2061b793177c70d35e177",
            ),
        ),
    ),
    Channel(
        name = "Universal Reality",
        logo = "https://mondrian.claro.com.br/channels/inverse/universal-reality.png",
        sources = listOf(
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/5ppjwg1ekb/out/v1/393342b545834c218745c3dd33661013/cenc.mpd",
                referer = "https://www.primevideo.com/",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "4b62d6103d99d62f8daca463d1c15430",
                key = "2fd0079af0bf85289859fff7b8a4e30f",
            ),
        ),
    ),
    Channel(
        name = "Warner",
        logo = "https://mondrian.claro.com.br/channels/inverse/warner-channel.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/WARNER_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "IMPD",
        logo = "https://d5pgibznjcs0s.cloudfront.net/46d92cf6-4807-45c6-9bfd-b38f80288c86/en/images/logo_full.png",
        sources = listOf(
            Source(
                url = "https://68882bdaf156a.streamlock.net/impd/ngrp:impd_all/chunklist_w1464410885_b2691072.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Globo SP",
        logo = "https://mondrian.claro.com.br/channels/inverse/globo.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/GLOBO_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Cartoonito",
        logo = "https://mondrian.claro.com.br/channels/inverse/cartoonito.png",
        sources = listOf(
            Source(
                url = "https://xn--l---------------------------_________________________-2w85c.null-null.shop/tos-alisg-avt-0068/proxy.m3u8?container=images&refresh=10&url=https://neosoro.gq/docs/cartoonito/__index.m3u8?sv=7&cc=y&secure_uri=true&nu3zAQc9HC3GbwJq=1788473248-7Yzu%2FtAxzRQJhmQMxTVyaqyf5vslWwRo1yq49kLi7ms%3D",
            ),
        ),
    ),
    Channel(
        name = "Cinemax",
        logo = "https://mondrian.claro.com.br/channels/inverse/cinemax.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/CINEMAX/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Discovery Channel",
        logo = "https://mondrian.claro.com.br/channels/inverse/discovery.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/DISCOVERY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Discovery Kids",
        logo = "https://mondrian.claro.com.br/channels/inverse/discovery-kids.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/DISCOVERY_KIDS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Discovery Theater",
        logo = "https://mondrian.claro.com.br/channels/inverse/discovery-theater.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/DISCOVERY_THEATER_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Discovery World",
        logo = "https://mondrian.claro.com.br/channels/inverse/discovery-world.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/DISCOVERY_WORLD_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "ESPN",
        logo = "https://mondrian.claro.com.br/channels/inverse/espn-3.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/ESPN_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "ESPN 2",
        logo = "https://mondrian.claro.com.br/channels/inverse/espn-3.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/ESPN2_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "ESPN 4",
        logo = "https://mondrian.claro.com.br/channels/inverse/espn-3.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/ESPN4_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "ESPN 5",
        logo = "https://mondrian.claro.com.br/channels/inverse/espn-3.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/ESPN5/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "ESPN 6",
        logo = "https://mondrian.claro.com.br/channels/inverse/espn-3.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/ESPN6/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "HBO",
        logo = "https://mondrian.claro.com.br/channels/inverse/hbo.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/HBO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "HBO2",
        logo = "https://mondrian.claro.com.br/channels/inverse/hbo-2.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/HBO2/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "HBO Family",
        logo = "https://mondrian.claro.com.br/channels/inverse/hbo-family.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/HBO_FAMILY/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "HBO Mundi",
        logo = "https://mondrian.claro.com.br/channels/inverse/hbo-mundi.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/HBO_MUNDI_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "HBO Plus",
        logo = "https://mondrian.claro.com.br/channels/inverse/hboplus.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/HBO_PLUS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "HBO Pop",
        logo = "https://mondrian.claro.com.br/channels/inverse/hbo-pop.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/HBO_POP_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "HBO Signature",
        logo = "https://mondrian.claro.com.br/channels/inverse/hbo-signature.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/HBO_SIGNATURE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "HBO Xtreme",
        logo = "https://mondrian.claro.com.br/channels/inverse/hbo-xtreme.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/HBO_EXTREME_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "HGTV",
        logo = "https://mondrian.claro.com.br/channels/inverse/hgtv.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/HGTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Record",
        logo = "https://mondrian.claro.com.br/channels/inverse/record-tv.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/RECORD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://79.127.238.228:14057",
            ),
        ),
    ),
    Channel(
        name = "Space",
        logo = "https://mondrian.claro.com.br/channels/inverse/space.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/SPACE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "TNT",
        logo = "https://mondrian.claro.com.br/channels/inverse/tnt.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TNT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "TNT Séries",
        logo = "https://mondrian.claro.com.br/channels/inverse/tnt-series.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TNT_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Universal TV",
        logo = "https://mondrian.claro.com.br/channels/inverse/universal.png",
        sources = listOf(
            Source(
                url = "https://xn--l---------------------------_________________________-2w85c.null-null.shop/tos-alisg-avt-0068/proxy.m3u8?container=images&refresh=10&url=https://neosoro.gq/docs/universal/__index.m3u8?sv=160&cc=y&secure_uri=true&nu3zAQc9HC3GbwJq=1788472902-u%2FRQX14ioJ69OPfvvDO1qYblKfL%2FF5lv7lKx%2Bx9GxPk%3D",
            ),
            Source(
                url = "https://getcdn.clarocdn.com.br/Content/Channel/SPOUNVHD/dsc3/manifest.mpd",
                referer = "https://www.clarotvmais.com.br/",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "0110de67e8a43229a9afee5fbb1bf34c",
                key = "14d17ea503859feb50f395a19491a2ea",
            ),
        ),
    ),
    Channel(
        name = "AMC Séries",
        logo = "https://mondrian.claro.com.br/channels/inverse/amc.png",
        sources = listOf(
            Source(
                url = "https://xn--l---------------------------_________________________-2w85c.null-null.shop/tos-alisg-avt-0068/proxy.m3u8?container=images&refresh=10&url=https://neosoro.gq/docs/amcseries/__index.m3u8?sv=104&cc=y&secure_uri=true&nu3zAQc9HC3GbwJq=1788472213-9X3RxgrzGBnYkKf9TDcG8To6ntcgw1Ltd1LupkS57Fg%3D",
            ),
        ),
    ),
    Channel(
        name = "Discovery Turbo",
        logo = "https://mondrian.claro.com.br/channels/inverse/discovery-turbo.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/DISCOVERY_TURBO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "USA Network",
        logo = "https://mondrian.claro.com.br/channels/inverse/usa.png",
        sources = listOf(
            Source(
                url = "https://xn--l---------------------------_________________________-2w85c.null-null.shop/tos-alisg-avt-0068/proxy.m3u8?container=images&refresh=10&url=https://neosoro.gq/docs/usa/__index.m3u8?sv=152&cc=y&secure_uri=true&nu3zAQc9HC3GbwJq=1788472952-usk1Sd862HUsEeFJpIgF1mgp%2BipLQSEzd%2BnacW%2BsYEs%3D",
            ),
        ),
    ),
    Channel(
        name = "Telecine Fun",
        logo = "https://upload.wikimedia.org/wikipedia/commons/f/f8/Telecine_Fun_%282021%29.png",
        sources = listOf(
            Source(
                url = "https://xn--l---------------------------_________________________-2w85c.null-null.shop/tos-alisg-avt-0068/proxy.m3u8?container=images&refresh=10&url=https://neosoro.gq/docs/telecinefun/__index.m3u8?sv=45&cc=y&secure_uri=true&nu3zAQc9HC3GbwJq=1788472591-MQu9Zm%2F3aV46qgPi%2FGK8lQPITC7rFBu89BYJX9HQhl4%3D",
            ),
        ),
    ),
    Channel(
        name = "Telecine Cult",
        logo = "https://upload.wikimedia.org/wikipedia/commons/9/9a/Telecine_Cult%282021%29.png",
        sources = listOf(
            Source(
                url = "https://xn--l---------------------------_________________________-2w85c.null-null.shop/tos-alisg-avt-0068/proxy.m3u8?container=images&refresh=10&url=https://neosoro.gq/docs/telecinecult/__index.m3u8?sv=147&cc=y&secure_uri=true&nu3zAQc9HC3GbwJq=1788472536-p1uhXCPAv%2BhiNspGPwSLrgfu%2Bk%2F3lPr0weu%2FwQ0t7Xo%3D",
            ),
        ),
    ),
    Channel(
        name = "Telecine Touch",
        logo = "https://upload.wikimedia.org/wikipedia/commons/9/95/TELECINE_Touch_Logo_2021.png",
        sources = listOf(
            Source(
                url = "https://xn--l---------------------------_________________________-2w85c.null-null.shop/tos-alisg-avt-0068/proxy.m3u8?container=images&refresh=10&url=https://neosoro.gq/docs/telecinetouch/__index.m3u8?sv=39&cc=y&secure_uri=true&nu3zAQc9HC3GbwJq=1788472733-mezOvP3QyByaaJYUwIpFQYMT3SQbdg5UhkhHmws96XE%3D",
            ),
        ),
    ),
    Channel(
        name = "SONY Movies",
        logo = "https://www.tvlogo.org/brazil/sony-movies-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/SONY_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),

    Channel(
        name = "Globo",
        logo = "https://mondrian.claro.com.br/channels/inverse/globo.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/GLOBO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Tooncast",
        logo = "https://mondrian.claro.com.br/channels/inverse/tooncast.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TOONCAST/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "TLC",
        logo = "https://mondrian.claro.com.br/channels/inverse/tlc.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TLC_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://79.127.238.228:14433",
            ),
        ),
    ),
    Channel(
        name = "Arte 1",
        logo = "https://mondrian.claro.com.br/channels/inverse/arte1.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/ARTE_1/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "TNT Novelas",
        logo = "https://mondrian.claro.com.br/channels/inverse/tnt-novelas.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TNT_NOVELAS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Discovery Science",
        logo = "https://mondrian.claro.com.br/channels/inverse/discovery-science.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/DISCOVERY_SCIENCE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Boomerang",
        logo = "https://mondrian.claro.com.br/channels/inverse/boomerang.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/BOOMERANG_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "AXN",
        logo = "https://mondrian.claro.com.br/channels/inverse/axn.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/AXN_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "TCM",
        logo = "https://mondrian.claro.com.br/channels/inverse/tcm.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TCM_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Band News",
        logo = "https://mondrian.claro.com.br/channels/inverse/band-news.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/BAND_NEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "BAND SPORTS HD",
        logo = "https://mondrian.claro.com.br/channels/inverse/band-sports.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/BAND_SPORTS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "RedeTV!",
        logo = "https://www.tvlogo.org/brazil/rede-tv-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/REDE_TV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "TV Cultura",
        logo = "https://mondrian.claro.com.br/channels/inverse/cultura.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TV_CULTURA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Terra Viva",
        logo = "https://www.tvlogo.org/brazil/terraviva-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TERRA_VIVA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Woohoo",
        logo = "https://mondrian.claro.com.br/channels/inverse/woohoo.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/WOOHOO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "PRIME BOX BRAZIL",
        logo = "https://www.tvlogo.org/brazil/prime-box-brazil-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/PRIME_BOX_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "ESPN 3",
        logo = "https://mondrian.claro.com.br/channels/inverse/espn-3.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/ESPN3_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "ESPN Extra",
        logo = "https://mondrian.claro.com.br/channels/inverse/espn-extra.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/ESPN_EXTRA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "TV Gazeta",
        logo = "https://mondrian.claro.com.br/channels/inverse/tv-gazeta.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/GAZETA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Record News",
        logo = "https://mondrian.claro.com.br/channels/inverse/recordnews.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/RECORD_NEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Canção Nova",
        logo = "https://mondrian.claro.com.br/channels/inverse/cancao-nova.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/CANCAO_NOVA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://46.151.196.223:14225",
            ),
        ),
    ),
    Channel(
        name = "TV Aparecida",
        logo = "https://mondrian.claro.com.br/channels/inverse/tv-aparecida.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TV_APARECIDA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Rede Vida",
        logo = "https://www.tvlogo.org/brazil/rede-vida-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/REDE_VIDA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://46.151.196.223:14244",
            ),
        ),
    ),
    Channel(
        name = "Discovery ID",
        logo = "https://mondrian.claro.com.br/channels/inverse/investigacao-discovery.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/ID_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Fish TV",
        logo = "https://mondrian.claro.com.br/channels/inverse/fish-tv.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/FISH_TV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Box Kids TV",
        logo = "https://www.tvlogo.org/brazil/box-kids-tv-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/BOX_KIDS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Sabor & Arte",
        logo = "https://mondrian.claro.com.br/channels/inverse/sabor-e-arte.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/SABOR_E_ARTE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "X Sports",
        logo = "https://mondrian.claro.com.br/channels/inverse/xsports.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/XSPORTS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "N SPORTS",
        logo = "https://mondrian.claro.com.br/channels/inverse/nsports.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/NSPORTS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Music Box Brazil",
        logo = "https://commons.wikimedia.org/wiki/Special:Redirect/file/MusicBoxBrazil.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/MUSIC_BOX_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Band SP",
        logo = "https://mondrian.claro.com.br/channels/inverse/band.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/BAND_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Band RS",
        logo = "https://mondrian.claro.com.br/channels/inverse/band.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/BAND_RS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "SBT SP",
        logo = "https://mondrian.claro.com.br/channels/inverse/sbt.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/SBT_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "SBT RS",
        logo = "https://mondrian.claro.com.br/channels/inverse/sbt.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/SBT_RS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Record SP",
        logo = "https://mondrian.claro.com.br/channels/inverse/record-tv.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/RECORD_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Record RS",
        logo = "https://mondrian.claro.com.br/channels/inverse/record-tv.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/RECORD_RS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Canal Rural",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/CANAL_RURAL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Agro Mais",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/AGROMAIS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "CNT",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/CNT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Markket",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/MARKKET/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "TV Pai Eterno",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/PAI_ETERNO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "PlayTV",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/PLAYTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Rede Brasil",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/REDE_BRASIL_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Rede Gospel",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/REDE_GOSPEL_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Rede Super",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/REDE_SUPER/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Trace Brazuca",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TRACE_BRAZUCA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "TV Câmara",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TV_CAMARA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "TV Evangelizar",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TV_EVANGELIZAR/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "TV Justiça",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TV_JUSTICA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "TV Novo Tempo",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TV_NOVO_TEMPO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "TV Senado",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TV_SENADO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "RIT",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/RIT/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "TV Escola",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TV_ESCOLA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Discovery Home & Health",
        logo = "https://mondrian.claro.com.br/channels/inverse/discovery.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/DISC_HH_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
)

/// Só entra na lista depois do código. Ver Unlock.
val RESTRICTED: List<Channel> = listOf(
    Channel(
        name = "Sexy Hot",
        logo = "https://mondrian.claro.com.br/channels/inverse/sexy-hot.png",
        sources = listOf(
            Source(
                url = "https://xn--l---------------------------_________________________-2w85c.null-null.shop/tos-alisg-avt-0068/proxy.m3u8?container=images&refresh=10&url=https://neosoro.gq/docs/sexhot/__index.m3u8?sv=15&cc=y&secure_uri=true&nu3zAQc9HC3GbwJq=1788473176-%2FrETindfv8DLP9eUlGVfeX%2B1m2urVGsPdbroXrlpPhM%3D",
            ),
        ),
    ),
    Channel(
        name = "Playboy TV",
        logo = "https://mondrian.claro.com.br/channels/inverse/playboy-tv.png",
        sources = listOf(
            Source(
                url = "https://xn--l---------------------------_________________________-2w85c.null-null.shop/tos-alisg-avt-0068/proxy.m3u8?container=images&refresh=10&url=https://neosoro.gq/docs/playboytv/__index.m3u8?sv=108&cc=y&secure_uri=true&nu3zAQc9HC3GbwJq=1788473127-vjRDY07Z4OZJsNw%2FoZ8hVP%2FdN0GSUrAvqdY5JmuQ93o%3D",
            ),
        ),
    ),
)
