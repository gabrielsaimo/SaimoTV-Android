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
                url = "https://cdn-mg1.satlabscloud.com.br/AE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/AE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/AE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/AE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/AE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/AE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/AE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/TRUTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TRUTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TRUTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TRUTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TRUTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TRUTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TRUTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/ANIMAL_PLANET_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/ANIMAL_PLANET_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/ANIMAL_PLANET_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/ANIMAL_PLANET_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/ANIMAL_PLANET_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/ANIMAL_PLANET_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/ANIMAL_PLANET_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/BAND_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/BAND_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/BAND_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/BAND_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/BAND_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/BAND_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/BAND_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/BAND_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://media.cdntvms.com.br/band_sat/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Cartoon Network",
        logo = "https://mondrian.claro.com.br/channels/inverse/cartoon-network.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/CARTOON_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/CARTOON_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/CARTOON_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/CARTOON_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/CARTOON_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/CARTOON_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/CARTOON_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/CAZE_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/CAZE_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/CAZE_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/CAZE_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/CAZE_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/CAZE_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/CAZE_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
            Source(
                url = "https://amg00716-globo-amg00716c1-tcl-br-9495.playouts.now.amagi.tv/playlist.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Globo RJ",
        logo = "https://mondrian.claro.com.br/channels/inverse/globo.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/GLOBO_RIO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/GLOBO_RIO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/GLOBO_RIO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/GLOBO_RIO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/GLOBO_RIO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/GLOBO_RIO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/GLOBO_RIO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/HISTORY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/HISTORY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/HISTORY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/HISTORY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/HISTORY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/HISTORY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/HISTORY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/HISTORY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://46.151.196.223:14410",
            ),
            Source(
                url = "http://45.177.114.114/HISTORY/index.m3u8",
            ),
            Source(
                url = "http://170.83.16.50/HISTORY/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "History 2",
        logo = "https://mondrian.claro.com.br/channels/inverse/history-2.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/H2_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/H2_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/H2_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/H2_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/H2_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/H2_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/H2_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/JP_NEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/JP_NEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/JP_NEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/JP_NEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/JP_NEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/JP_NEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/JP_NEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/JP_NEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://amg01391-sbtinfast-amg01391c3-lg-us-8995.playouts.now.amagi.tv/playlist/amg01391-addigital-jovempan-lgus/playlist.m3u8",
                userAgent = "Mozilla/5.0 (Linux; U; Android 13; T610K Build/TP1A.220624.014; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/126.0.6478.71 Mobile Safari/537.36 OPR/87.0.2254.75258",
            ),
            Source(
                url = "https://jmp2.uk/plu-6317ba014d4d040007227f72.m3u8",
            ),
            Source(
                url = "http://170.83.49.66:8083/JOVEMPANNEWSHD/index.m3u8",
            ),
            Source(
                url = "http://186.219.52.187/jp_news/index.m3u8",
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
                url = "https://cdn-mg1.satlabscloud.com.br/SBT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/SBT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/SBT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/SBT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/SBT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/SBT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/SBT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/SBT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://6836041ea1117.streamlock.net/cverde/cverde/playlist.m3u8",
            ),
            Source(
                url = "https://media.cdntvms.com.br/sbt_sat/index.m3u8",
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
            Source(
                url = "https://dai.google.com/linear/hls/event/1XSOdtQ0SH2G8OEmEfGgjQ/master.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Sony Channel",
        logo = "https://mondrian.claro.com.br/channels/inverse/sony.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/SONY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/SONY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/SONY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/SONY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/SONY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/SONY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/SONY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/SONY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://170.83.16.50/SONY_CHANNEL/index.m3u8",
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
            Source(
                url = "http://170.83.49.66:8083/SPORTV3HD/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Telecine Action",
        logo = "https://upload.wikimedia.org/wikipedia/commons/3/37/Telecine_Action_2.png",
        sources = listOf(
            Source(
                url = "https://xn--l---------------------------_________________________-2w85c.null-null.shop/tos-alisg-avt-0068/proxy.m3u8?container=images&refresh=10&url=https://neosoro.gq/docs/telecineaction/__index.m3u8?sv=71&cc=y&secure_uri=true&nu3zAQc9HC3GbwJq=1789218927-xNieqExLcDiWlBkadztoTnelV5wE8DKtLPXJjmzG5Qc%3D",
            ),
        ),
    ),
    Channel(
        name = "Telecine Pipoca",
        logo = "https://upload.wikimedia.org/wikipedia/commons/2/2f/Telecine_Pipoca_%282021%29.png",
        sources = listOf(
            Source(
                url = "https://xn--l---------------------------_________________________-2w85c.null-null.shop/tos-alisg-avt-0068/proxy.m3u8?container=images&refresh=10&url=https://neosoro.gq/docs/telecinepipoca/__index.m3u8?sv=138&cc=y&secure_uri=true&nu3zAQc9HC3GbwJq=1789219374-F6IW5BQoqAEf3pOLIzgpJRdy1gsvHmBfDbZAIqFh1k4%3D",
            ),
        ),
    ),
    Channel(
        name = "Telecine Premium",
        logo = "https://upload.wikimedia.org/wikipedia/commons/1/11/App-telecine-premium-252x252.png",
        sources = listOf(
            Source(
                url = "https://xn--l---------------------------_________________________-2w85c.null-null.shop/tos-alisg-avt-0068/proxy.m3u8?container=images&refresh=10&url=https://neosoro.gq/docs/telecinepremium/__index.m3u8?sv=197&cc=y&secure_uri=true&nu3zAQc9HC3GbwJq=1789219484-aQK3WA9dH6oDAAMndZCMPBvj2NxCQTmQmx9DswrnO5M%3D",
            ),
        ),
    ),
    Channel(
        name = "TV Brasil",
        logo = "https://mondrian.claro.com.br/channels/inverse/tv-brasil.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/TV_BRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TV_BRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TV_BRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TV_BRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TV_BRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TV_BRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TV_BRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TV_BRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/TVE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TVE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TVE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TVE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TVE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TVE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TVE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://tvbrasil-stream.ebc.com.br/index.m3u8",
            ),
            Source(
                url = "http://45.162.64.114/TV_BRASIL/index.m3u8",
            ),
            Source(
                url = "http://45.177.114.115/TV_BRASIL/index.m3u8",
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
                url = "https://cdn-mg1.satlabscloud.com.br/WARNER_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/WARNER_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/WARNER_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/WARNER_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/WARNER_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/WARNER_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/WARNER_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
            Source(
                url = "https://igrejamundial.nuvemplay.live/hls/stream.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Globo SP",
        logo = "https://mondrian.claro.com.br/channels/inverse/globo.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/GLOBO_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/GLOBO_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/GLOBO_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/GLOBO_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/GLOBO_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/GLOBO_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/GLOBO_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/CINEMAX/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/CINEMAX/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/CINEMAX/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/CINEMAX/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/CINEMAX/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/CINEMAX/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/CINEMAX/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/DISCOVERY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/DISCOVERY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/DISCOVERY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/DISCOVERY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/DISCOVERY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/DISCOVERY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/DISCOVERY_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/DISCOVERY_KIDS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/DISCOVERY_KIDS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/DISCOVERY_KIDS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/DISCOVERY_KIDS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/DISCOVERY_KIDS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/DISCOVERY_KIDS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/DISCOVERY_KIDS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/DISCOVERY_THEATER_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/DISCOVERY_THEATER_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/DISCOVERY_THEATER_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/DISCOVERY_THEATER_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/DISCOVERY_THEATER_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/DISCOVERY_THEATER_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/DISCOVERY_THEATER_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/DISCOVERY_WORLD_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/DISCOVERY_WORLD_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/DISCOVERY_WORLD_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/DISCOVERY_WORLD_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/DISCOVERY_WORLD_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/DISCOVERY_WORLD_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/DISCOVERY_WORLD_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/ESPN_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/ESPN_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/ESPN_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/ESPN_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/ESPN_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/ESPN_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/ESPN_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/ESPN_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://181.78.197.59:8000/play/a07z/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "ESPN 2",
        logo = "https://mondrian.claro.com.br/channels/inverse/espn-3.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/ESPN2_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/ESPN2_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/ESPN2_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/ESPN2_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/ESPN2_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/ESPN2_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/ESPN2_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/ESPN4_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/ESPN4_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/ESPN4_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/ESPN4_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/ESPN4_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/ESPN4_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/ESPN4_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/ESPN4_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://181.78.197.59:8000/play/a07n/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "ESPN 5",
        logo = "https://mondrian.claro.com.br/channels/inverse/espn-3.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/ESPN5/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/ESPN5/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/ESPN5/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/ESPN5/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/ESPN5/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/ESPN5/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/ESPN5/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/ESPN6/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/ESPN6/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/ESPN6/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/ESPN6/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/ESPN6/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/ESPN6/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/ESPN6/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/HBO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/HBO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/HBO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/HBO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/HBO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/HBO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/HBO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/HBO2/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/HBO2/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/HBO2/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/HBO2/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/HBO2/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/HBO2/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/HBO2/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/HBO_FAMILY/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/HBO_FAMILY/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/HBO_FAMILY/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/HBO_FAMILY/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/HBO_FAMILY/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/HBO_FAMILY/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/HBO_FAMILY/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/HBO_MUNDI_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/HBO_MUNDI_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/HBO_MUNDI_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/HBO_MUNDI_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/HBO_MUNDI_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/HBO_MUNDI_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/HBO_MUNDI_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/HBO_PLUS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/HBO_PLUS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/HBO_PLUS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/HBO_PLUS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/HBO_PLUS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/HBO_PLUS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/HBO_PLUS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/HBO_POP_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/HBO_POP_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/HBO_POP_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/HBO_POP_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/HBO_POP_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/HBO_POP_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/HBO_POP_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/HBO_SIGNATURE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/HBO_SIGNATURE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/HBO_SIGNATURE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/HBO_SIGNATURE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/HBO_SIGNATURE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/HBO_SIGNATURE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/HBO_SIGNATURE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/HBO_EXTREME_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/HBO_EXTREME_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/HBO_EXTREME_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/HBO_EXTREME_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/HBO_EXTREME_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/HBO_EXTREME_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/HBO_EXTREME_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/HGTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/HGTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/HGTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/HGTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/HGTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/HGTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/HGTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/RECORD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/RECORD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/RECORD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/RECORD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/RECORD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/RECORD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/RECORD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/RECORD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://79.127.238.228:14057",
            ),
            Source(
                url = "https://media.cdntvms.com.br/record_nacional_sat/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Space",
        logo = "https://mondrian.claro.com.br/channels/inverse/space.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/SPACE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/SPACE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/SPACE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/SPACE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/SPACE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/SPACE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/SPACE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/TNT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TNT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TNT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TNT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TNT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TNT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TNT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/TNT_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TNT_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TNT_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TNT_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TNT_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TNT_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TNT_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/DISCOVERY_TURBO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/DISCOVERY_TURBO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/DISCOVERY_TURBO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/DISCOVERY_TURBO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/DISCOVERY_TURBO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/DISCOVERY_TURBO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/DISCOVERY_TURBO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://xn--l---------------------------_________________________-2w85c.null-null.shop/tos-alisg-avt-0068/proxy.m3u8?container=images&refresh=10&url=https://neosoro.gq/docs/telecinefun/__index.m3u8?sv=60&cc=y&secure_uri=true&nu3zAQc9HC3GbwJq=1789219253-QyNo%2BSouIn5NsUWG9CLY3IS1v3VkaHye34ceWOyiOVs%3D",
            ),
        ),
    ),
    Channel(
        name = "Telecine Cult",
        logo = "https://upload.wikimedia.org/wikipedia/commons/9/9a/Telecine_Cult%282021%29.png",
        sources = listOf(
            Source(
                url = "https://xn--l---------------------------_________________________-2w85c.null-null.shop/tos-alisg-avt-0068/proxy.m3u8?container=images&refresh=10&url=https://neosoro.gq/docs/telecinecult/__index.m3u8?sv=62&cc=y&secure_uri=true&nu3zAQc9HC3GbwJq=1789219128-2d6%2FA%2Bghk7W1jCeuEeiIa%2FzqBJvYswTltdkdtIbUKYE%3D",
            ),
        ),
    ),
    Channel(
        name = "Telecine Touch",
        logo = "https://upload.wikimedia.org/wikipedia/commons/9/95/TELECINE_Touch_Logo_2021.png",
        sources = listOf(
            Source(
                url = "https://xn--l---------------------------_________________________-2w85c.null-null.shop/tos-alisg-avt-0068/proxy.m3u8?container=images&refresh=10&url=https://neosoro.gq/docs/telecinetouch/__index.m3u8?sv=24&cc=y&secure_uri=true&nu3zAQc9HC3GbwJq=1789219606-y4NcNO1KSIHSjnN2cGCPByGn4ZDzVdUj4qjXJLBn0dE%3D",
            ),
        ),
    ),
    Channel(
        name = "SONY Movies",
        logo = "https://www.tvlogo.org/brazil/sony-movies-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/SONY_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/SONY_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/SONY_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/SONY_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/SONY_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/SONY_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/SONY_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/SONY_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://45.162.64.114/SONY_MOVIES/index.m3u8",
            ),
            Source(
                url = "http://45.177.114.115/SONY_MOVIES/index.m3u8",
            ),
            Source(
                url = "http://168.197.104.22/SONY_MOVIES/index.m3u8",
            ),
        ),
    ),

    Channel(
        name = "Globo",
        logo = "https://mondrian.claro.com.br/channels/inverse/globo.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/GLOBO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/GLOBO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/GLOBO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/GLOBO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/GLOBO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/GLOBO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/GLOBO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/GLOBO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://media2.cdntvms.com.br/tv_morena_dorados/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Tooncast",
        logo = "https://mondrian.claro.com.br/channels/inverse/tooncast.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/TOONCAST/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TOONCAST/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TOONCAST/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TOONCAST/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TOONCAST/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TOONCAST/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TOONCAST/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/TLC_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TLC_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TLC_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TLC_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TLC_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TLC_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TLC_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/ARTE_1/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/ARTE_1/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/ARTE_1/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/ARTE_1/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/ARTE_1/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/ARTE_1/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/ARTE_1/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/ARTE_1/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://45.162.64.114/ARTE1/index.m3u8",
            ),
            Source(
                url = "http://45.177.114.115/ARTE1/index.m3u8",
            ),
            Source(
                url = "http://168.197.104.22/ARTE1/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "TNT Novelas",
        logo = "https://mondrian.claro.com.br/channels/inverse/tnt-novelas.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/TNT_NOVELAS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TNT_NOVELAS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TNT_NOVELAS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TNT_NOVELAS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TNT_NOVELAS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TNT_NOVELAS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TNT_NOVELAS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/DISCOVERY_SCIENCE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/DISCOVERY_SCIENCE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/DISCOVERY_SCIENCE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/DISCOVERY_SCIENCE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/DISCOVERY_SCIENCE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/DISCOVERY_SCIENCE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/DISCOVERY_SCIENCE_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/BOOMERANG_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/BOOMERANG_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/BOOMERANG_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/BOOMERANG_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/BOOMERANG_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/BOOMERANG_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/BOOMERANG_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/AXN_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/AXN_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/AXN_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/AXN_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/AXN_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/AXN_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/AXN_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/AXN_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://170.83.16.50/AXN/index.m3u8",
            ),
            Source(
                url = "http://170.83.49.66:8083/AXNHD/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "TCM",
        logo = "https://mondrian.claro.com.br/channels/inverse/tcm.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/TCM_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TCM_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TCM_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TCM_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TCM_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TCM_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TCM_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/BAND_NEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/BAND_NEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/BAND_NEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/BAND_NEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/BAND_NEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/BAND_NEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/BAND_NEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/BAND_NEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://45.162.64.114/BAND_NEWS/index.m3u8",
            ),
            Source(
                url = "http://45.177.114.115/BAND_NEWS/index.m3u8",
            ),
            Source(
                url = "http://168.197.104.22/BAND_NEWS/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "BAND SPORTS HD",
        logo = "https://mondrian.claro.com.br/channels/inverse/band-sports.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/BAND_SPORTS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/BAND_SPORTS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/BAND_SPORTS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/BAND_SPORTS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/BAND_SPORTS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/BAND_SPORTS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/BAND_SPORTS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/BAND_SPORTS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://45.162.64.114/BAND_SPORTS/index.m3u8",
            ),
            Source(
                url = "http://170.83.16.50/BAND_SPORTS/index.m3u8",
            ),
            Source(
                url = "http://170.83.49.66:8083/BANDSPORTSHD/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "RedeTV!",
        logo = "https://www.tvlogo.org/brazil/rede-tv-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/REDE_TV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/REDE_TV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/REDE_TV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/REDE_TV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/REDE_TV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/REDE_TV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/REDE_TV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/REDE_TV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://45.162.64.114/REDE_TV/index.m3u8",
            ),
            Source(
                url = "http://168.197.104.22/REDE_TV/index.m3u8",
            ),
            Source(
                url = "http://170.83.16.50/REDE_TV/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "TV Cultura",
        logo = "https://mondrian.claro.com.br/channels/inverse/cultura.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/TV_CULTURA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TV_CULTURA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TV_CULTURA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TV_CULTURA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TV_CULTURA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TV_CULTURA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TV_CULTURA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TV_CULTURA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://v-us-01.wisestream.io/memfs/e8740862-7a1f-45f3-acb2-4f357e144059.m3u8",
            ),
            Source(
                url = "https://jmp2.uk/plu-62e010e6cd663f0007e57dc8.m3u8",
            ),
            Source(
                url = "https://player-tvcultura.stream.uol.com.br/live/tvcultura.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Terra Viva",
        logo = "https://www.tvlogo.org/brazil/terraviva-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/TERRA_VIVA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TERRA_VIVA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TERRA_VIVA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TERRA_VIVA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TERRA_VIVA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TERRA_VIVA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TERRA_VIVA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TERRA_VIVA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://9ada494d.wurl.com/master/f36d25e7e52f1ba8d7e56eb859c636563214f541/TEctYnJfVXBseW5rLU5ld2NvX0hMUw/playlist.m3u8",
            ),
            Source(
                url = "http://45.162.64.114/TERRAVIVA/index.m3u8",
            ),
            Source(
                url = "http://45.177.114.115/TERRAVIVA/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Woohoo",
        logo = "https://mondrian.claro.com.br/channels/inverse/woohoo.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/WOOHOO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/WOOHOO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/WOOHOO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/WOOHOO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/WOOHOO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/WOOHOO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/WOOHOO_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://45.162.64.114/WOOHOO/index.m3u8",
            ),
            Source(
                url = "http://168.197.104.22/WOOHOO/index.m3u8",
            ),
            Source(
                url = "http://170.83.16.50/WOOHOO/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "PRIME BOX BRAZIL",
        logo = "https://www.tvlogo.org/brazil/prime-box-brazil-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/PRIME_BOX_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/PRIME_BOX_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/PRIME_BOX_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/PRIME_BOX_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/PRIME_BOX_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/PRIME_BOX_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/PRIME_BOX_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/ESPN3_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/ESPN3_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/ESPN3_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/ESPN3_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/ESPN3_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/ESPN3_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/ESPN3_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/ESPN3_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://181.78.197.59:8000/play/a081/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "ESPN Extra",
        logo = "https://mondrian.claro.com.br/channels/inverse/espn-extra.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/ESPN_EXTRA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/ESPN_EXTRA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/ESPN_EXTRA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/ESPN_EXTRA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/ESPN_EXTRA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/ESPN_EXTRA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/ESPN_EXTRA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/GAZETA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/GAZETA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/GAZETA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/GAZETA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/GAZETA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/GAZETA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/GAZETA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/GAZETA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://45.162.64.114/GAZETA/index.m3u8",
            ),
            Source(
                url = "http://45.162.64.114/TV_GAZETA/index.m3u8",
            ),
            Source(
                url = "http://168.197.104.22/GAZETA/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Record News",
        logo = "https://mondrian.claro.com.br/channels/inverse/recordnews.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/RECORD_NEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/RECORD_NEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/RECORD_NEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/RECORD_NEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/RECORD_NEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/RECORD_NEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/RECORD_NEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/RECORD_NEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://rnw-rn.otteravision.com/rnw/rn/rnw_rn.m3u8",
            ),
            Source(
                url = "http://45.162.64.114/RECORD_NEWS/index.m3u8",
            ),
            Source(
                url = "http://45.177.114.115/RECORD_NEWS/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Canção Nova",
        logo = "https://mondrian.claro.com.br/channels/inverse/cancao-nova.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/CANCAO_NOVA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/CANCAO_NOVA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/CANCAO_NOVA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/CANCAO_NOVA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/CANCAO_NOVA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/CANCAO_NOVA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/CANCAO_NOVA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/CANCAO_NOVA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://46.151.196.223:14225",
            ),
            Source(
                url = "https://5c65286fc6ace.streamlock.net/cancaonova/CancaoNova.stream_720p/playlist.m3u8",
            ),
            Source(
                url = "http://45.162.64.114/CANCAO_NOVA/index.m3u8",
            ),
            Source(
                url = "http://168.197.104.22/CANCAO_NOVA/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "TV Aparecida",
        logo = "https://mondrian.claro.com.br/channels/inverse/tv-aparecida.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/TV_APARECIDA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TV_APARECIDA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TV_APARECIDA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TV_APARECIDA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TV_APARECIDA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TV_APARECIDA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TV_APARECIDA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TV_APARECIDA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://45.162.64.114/TV_APARECIDA/index.m3u8",
            ),
            Source(
                url = "http://45.177.114.115/TV_APARECIDA_HD/index.m3u8",
            ),
            Source(
                url = "http://168.197.104.22/TV_APARECIDA/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Rede Vida",
        logo = "https://www.tvlogo.org/brazil/rede-vida-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/REDE_VIDA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/REDE_VIDA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/REDE_VIDA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/REDE_VIDA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/REDE_VIDA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/REDE_VIDA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/REDE_VIDA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/REDE_VIDA_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://46.151.196.223:14244",
            ),
            Source(
                url = "http://168.197.104.22/REDE_VIDA/index.m3u8",
            ),
            Source(
                url = "http://186.219.52.187/rede_vida/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Discovery ID",
        logo = "https://mondrian.claro.com.br/channels/inverse/investigacao-discovery.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/ID_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/ID_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/ID_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/ID_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/ID_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/ID_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/ID_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/FISH_TV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/FISH_TV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/FISH_TV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/FISH_TV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/FISH_TV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/FISH_TV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/FISH_TV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/FISH_TV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://45.162.64.114/FISH_TV/index.m3u8",
            ),
            Source(
                url = "http://168.197.104.22/FISH_TV/index.m3u8",
            ),
            Source(
                url = "http://170.83.16.50/FISH_TV/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Box Kids TV",
        logo = "https://www.tvlogo.org/brazil/box-kids-tv-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/BOX_KIDS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/BOX_KIDS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/BOX_KIDS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/BOX_KIDS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/BOX_KIDS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/BOX_KIDS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/BOX_KIDS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/BOX_KIDS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://170.83.49.66:8083/BOXKIDSHD/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Sabor & Arte",
        logo = "https://mondrian.claro.com.br/channels/inverse/sabor-e-arte.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/SABOR_E_ARTE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/SABOR_E_ARTE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/SABOR_E_ARTE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/SABOR_E_ARTE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/SABOR_E_ARTE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/SABOR_E_ARTE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/SABOR_E_ARTE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/XSPORTS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/XSPORTS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/XSPORTS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/XSPORTS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/XSPORTS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/XSPORTS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/XSPORTS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/NSPORTS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/NSPORTS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/NSPORTS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/NSPORTS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/NSPORTS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/NSPORTS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/NSPORTS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/NSPORTS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://ogc-nsprt-tcl-roku-syndication.otteravision.com/ogc/nsprt/nsprt.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Music Box Brazil",
        logo = "https://commons.wikimedia.org/wiki/Special:Redirect/file/MusicBoxBrazil.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/MUSIC_BOX_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/MUSIC_BOX_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/MUSIC_BOX_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/MUSIC_BOX_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/MUSIC_BOX_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/MUSIC_BOX_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/MUSIC_BOX_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/MUSIC_BOX_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://168.197.104.22/MUSIC/index.m3u8",
            ),
            Source(
                url = "http://170.83.49.66:8083/MUSICBOXBRASILHD/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Band SP",
        logo = "https://mondrian.claro.com.br/channels/inverse/band.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/BAND_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/BAND_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/BAND_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/BAND_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/BAND_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/BAND_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/BAND_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/BAND_RS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/BAND_RS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/BAND_RS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/BAND_RS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/BAND_RS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/BAND_RS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/SBT_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/SBT_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/SBT_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/SBT_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/SBT_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/SBT_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/SBT_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/SBT_RS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/SBT_RS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/SBT_RS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/SBT_RS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/SBT_RS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/SBT_RS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/SBT_RS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/RECORD_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/RECORD_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/RECORD_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/RECORD_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/RECORD_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/RECORD_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/RECORD_SP/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/RECORD_RS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/RECORD_RS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/RECORD_RS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/RECORD_RS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/RECORD_RS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/RECORD_RS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/RECORD_RS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/RECORD_RS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Canal Rural",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/brazil/canal-rural-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/CANAL_RURAL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/CANAL_RURAL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/CANAL_RURAL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/CANAL_RURAL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/CANAL_RURAL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/CANAL_RURAL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/CANAL_RURAL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/CANAL_RURAL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://170.83.49.66:8083/CANALRURALHD/index.m3u8",
            ),
            Source(
                url = "http://186.219.52.187/canal_rural/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Agro Mais",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/brazil/agro-mais-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/AGROMAIS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/AGROMAIS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/AGROMAIS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/AGROMAIS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/AGROMAIS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/AGROMAIS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/AGROMAIS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/AGROMAIS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://45.162.64.114/AGROMAIS/index.m3u8",
            ),
            Source(
                url = "http://45.177.114.115/agromais/index.m3u8",
            ),
            Source(
                url = "http://45.177.114.115/AGROMAIS_HD/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "CNT",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/brazil/rede-cnt-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/CNT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/CNT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/CNT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/CNT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/CNT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/CNT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/CNT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/CNT_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://45.162.64.114/CNT/index.m3u8",
            ),
            Source(
                url = "http://168.197.104.22/CNT/index.m3u8",
            ),
            Source(
                url = "http://170.83.49.66:8083/REDECNTHD/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Markket",
        logo = "https://mondrian.claro.com.br/channels/inverse/markket.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/MARKKET/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/MARKKET/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/MARKKET/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/MARKKET/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/MARKKET/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/MARKKET/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/MARKKET/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/MARKKET/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "TV Pai Eterno",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/brazil/tv-pai-eterno-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/PAI_ETERNO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/PAI_ETERNO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/PAI_ETERNO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/PAI_ETERNO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/PAI_ETERNO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/PAI_ETERNO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/PAI_ETERNO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/PAI_ETERNO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://video09.logicahost.com.br/paieterno/paieterno/playlist.m3u8",
            ),
            Source(
                url = "http://45.162.64.114/TV_PAI_ETERNO/index.m3u8",
            ),
            Source(
                url = "http://168.197.104.22/TV_PAI_ETERNO/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "PlayTV",
        logo = "https://i.imgur.com/Ikrj3lk.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/PLAYTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/PLAYTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/PLAYTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/PLAYTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/PLAYTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/PLAYTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/PLAYTV_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://isaocorp.cloudecast.com/playtv/index.m3u8",
            ),
            Source(
                url = "http://45.162.64.114/PLAY_TV/index.m3u8",
            ),
            Source(
                url = "http://45.177.114.115/PLAY_TV/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Rede Brasil",
        logo = "https://i.imgur.com/TXJKwzZ.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/REDE_BRASIL_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/REDE_BRASIL_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/REDE_BRASIL_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/REDE_BRASIL_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/REDE_BRASIL_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/REDE_BRASIL_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/REDE_BRASIL_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/REDE_BRASIL_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://redebrasil.nuvemplay.live/hls/stream.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Rede Gospel",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/brazil/rede-gospel-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/REDE_GOSPEL_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/REDE_GOSPEL_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/REDE_GOSPEL_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/REDE_GOSPEL_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/REDE_GOSPEL_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/REDE_GOSPEL_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/REDE_GOSPEL_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/REDE_GOSPEL_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn.live.br1.jmvstream.com/w/LVW-8719/LVW8719_AcLVAxWy5J/playlist.m3u8",
            ),
            Source(
                url = "https://redegospel-aovivo.nuvemplay.live/hls/stream.m3u8",
            ),
            Source(
                url = "http://45.177.114.115/REDE_GOSPEL/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Rede Super",
        logo = "https://i.imgur.com/X75qTEm.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/REDE_SUPER/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/REDE_SUPER/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/REDE_SUPER/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/REDE_SUPER/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/REDE_SUPER/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/REDE_SUPER/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/REDE_SUPER/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/REDE_SUPER/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Trace Brazuca",
        logo = "https://mondrian.claro.com.br/channels/inverse/trace-brazuca.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/TRACE_BRAZUCA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TRACE_BRAZUCA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TRACE_BRAZUCA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TRACE_BRAZUCA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TRACE_BRAZUCA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TRACE_BRAZUCA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TRACE_BRAZUCA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TRACE_BRAZUCA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-uw2-prod.tsv2.amagi.tv/linear/amg01131-tracetv-tracebrazuca-samsungbr/playlist.m3u8",
            ),
        ),
    ),
    Channel(
        name = "TV Câmara",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/brazil/tv-camara-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/TV_CAMARA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TV_CAMARA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TV_CAMARA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TV_CAMARA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TV_CAMARA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TV_CAMARA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TV_CAMARA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TV_CAMARA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://stream3.camara.gov.br/tv1/manifest.m3u8",
            ),
            Source(
                url = "http://45.162.64.114/TV_CAMARA/index.m3u8",
            ),
            Source(
                url = "http://168.197.104.22/TV_CAMARA/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "TV Evangelizar",
        logo = "https://i.imgur.com/IrYR7Kp.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/TV_EVANGELIZAR/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TV_EVANGELIZAR/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TV_EVANGELIZAR/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TV_EVANGELIZAR/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TV_EVANGELIZAR/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TV_EVANGELIZAR/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TV_EVANGELIZAR/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TV_EVANGELIZAR/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://45.162.64.114/EVANGELIZAR/index.m3u8",
            ),
            Source(
                url = "http://168.197.104.22/EVANGELIZAR/index.m3u8",
            ),
            Source(
                url = "http://170.83.16.50/TV_EVANGELIZAR/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "TV Justiça",
        logo = "https://mondrian.claro.com.br/channels/inverse/tv-justica.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/TV_JUSTICA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TV_JUSTICA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TV_JUSTICA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TV_JUSTICA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TV_JUSTICA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TV_JUSTICA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TV_JUSTICA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TV_JUSTICA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://45.162.64.114/TV_JUSTICA/index.m3u8",
            ),
            Source(
                url = "http://168.197.104.22/TV_JUSTICA/index.m3u8",
            ),
            Source(
                url = "http://170.83.16.50/TV_JUSTICA/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "TV Novo Tempo",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/brazil/novo-tempo-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/TV_NOVO_TEMPO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TV_NOVO_TEMPO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TV_NOVO_TEMPO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TV_NOVO_TEMPO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TV_NOVO_TEMPO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TV_NOVO_TEMPO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TV_NOVO_TEMPO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TV_NOVO_TEMPO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://45.162.64.114/NOVO_TEMPO/index.m3u8",
            ),
            Source(
                url = "http://168.197.104.22/NOVO_TEMPO/index.m3u8",
            ),
            Source(
                url = "http://186.219.52.187/novo_tempo/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "TV Senado",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/brazil/tv-senado-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/TV_SENADO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TV_SENADO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TV_SENADO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TV_SENADO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TV_SENADO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TV_SENADO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TV_SENADO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TV_SENADO/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://45.162.64.114/TV_SENADO/index.m3u8",
            ),
            Source(
                url = "http://168.197.104.22/TV_SENADO/index.m3u8",
            ),
            Source(
                url = "http://170.83.16.50/TV_SENADO/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "RIT",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/brazil/rit-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/RIT/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/RIT/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/RIT/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/RIT/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/RIT/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/RIT/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/RIT/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/RIT/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://acesso.ecast.site:3648/live/ritlive.m3u8",
            ),
            Source(
                url = "http://45.177.114.115/RIT_TV/index.m3u8",
            ),
            Source(
                url = "http://170.83.49.66:8083/RITHD/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "TV Escola",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/brazil/tv-escola-br.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/TV_ESCOLA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TV_ESCOLA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TV_ESCOLA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TV_ESCOLA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TV_ESCOLA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TV_ESCOLA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TV_ESCOLA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
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
                url = "https://cdn-mg1.satlabscloud.com.br/DISC_HH_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/DISC_HH_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/DISC_HH_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/DISC_HH_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/DISC_HH_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/DISC_HH_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/DISC_HH_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/DISC_HH_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Agro Brasil",
        logo = "https://i.imgur.com/aNkP7Zd.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/AGROBRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/AGROBRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/AGROBRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/AGROBRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/AGROBRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/AGROBRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/AGROBRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/AGROBRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Canal Agro",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/AGRO_CANAL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/AGRO_CANAL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/AGRO_CANAL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/AGRO_CANAL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/AGRO_CANAL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/AGRO_CANAL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/AGRO_CANAL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/AGRO_CANAL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://aovivo.equipea.com.br:5443/aovivort/streams/pshRLrnv6isXq7RG4747567774043229.m3u8",
            ),
            Source(
                url = "http://45.162.64.114/AGRO_CANAL/index.m3u8",
            ),
            Source(
                url = "http://168.197.104.22/AGRO_CANAL/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Blits TV",
        logo = "https://i.imgur.com/FO4QTRf.jpeg",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/BLITS_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/BLITS_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/BLITS_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/BLITS_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/BLITS_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/BLITS_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/BLITS_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/BLITS_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://stmv1.transmissaodigital.com/blitstv/blitstv/playlist.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Channel 1",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/CHANNEL1/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/CHANNEL1/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/CHANNEL1/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/CHANNEL1/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/CHANNEL1/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/CHANNEL1/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/CHANNEL1/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/CHANNEL1/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Cinemonde",
        sources = listOf(
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/CINEMONDE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "CNBC",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/united-states/cnbc-us.png",
        sources = listOf(
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/CNBC_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://jmp2.uk/plu-679a973a97782f0008ff4bb6.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Com Brasil",
        logo = "https://i.imgur.com/GrjGwKM.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/COM_BRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/COM_BRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/COM_BRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/COM_BRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/COM_BRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/COM_BRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/COM_BRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/COM_BRASIL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://br5093.streamingdevideo.com.br/abc/abc/playlist.m3u8",
            ),
            Source(
                url = "https://dfr80qz435crc.cloudfront.net/EFGH/Amagi/NewCo/New_Brasil_BR/New_Brasil.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Darkflix",
        logo = "https://mondrian.claro.com.br/channels/inverse/darkflix.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/DARKFLIX/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/DARKFLIX/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/DARKFLIX/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/DARKFLIX/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/DARKFLIX/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/DARKFLIX/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/DARKFLIX/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/DARKFLIX/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "DW",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/international/dw-int.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/DW/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/DW/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/DW/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/DW/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/DW/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/DW/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/DW/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/DW/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Euronews",
        logo = "https://images-2.rakuten.tv/storage/global-live-channel/translation/artwork/bc84c3b7-6008-4ee3-8f6a-e4bb4365f082-width200-quality90.jpeg",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/EURONEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/EURONEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/EURONEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/EURONEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/EURONEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/EURONEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/EURONEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/EURONEWS_HD/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://jmp2.uk/plu-619e6614c9d9650007a2b171.m3u8",
            ),
        ),
    ),
    Channel(
        name = "EWTN",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/international/ewtn-int.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/EWTN/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/EWTN/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/EWTN/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/EWTN/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/EWTN/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/EWTN/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/EWTN/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/EWTN/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Fox News",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/united-states/fox-news-us.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/FOX_NEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/FOX_NEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/FOX_NEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/FOX_NEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/FOX_NEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/FOX_NEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/FOX_NEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/FOX_NEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Fox Sports 2",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/united-states/fox-sports-2-us.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/FOX_SPORTS_2/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/FOX_SPORTS_2/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/FOX_SPORTS_2/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/FOX_SPORTS_2/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/FOX_SPORTS_2/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/FOX_SPORTS_2/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/FOX_SPORTS_2/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/FOX_SPORTS_2/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Fuel TV",
        logo = "https://mondrian.claro.com.br/channels/inverse/fuel-tv.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/FUEL_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/FUEL_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/FUEL_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/FUEL_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/FUEL_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/FUEL_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/FUEL_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/FUEL_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Hallo Anime",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/HALLO_ANIME/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/HALLO_ANIME/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/HALLO_ANIME/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/HALLO_ANIME/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/HALLO_ANIME/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/HALLO_ANIME/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/HALLO_ANIME/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/HALLO_ANIME/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Hallo Doc",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/HALLO_DOC/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/HALLO_DOC/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/HALLO_DOC/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/HALLO_DOC/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/HALLO_DOC/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/HALLO_DOC/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/HALLO_DOC/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/HALLO_DOC/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Hallo Movies",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/HALLO_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/HALLO_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/HALLO_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/HALLO_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/HALLO_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/HALLO_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/HALLO_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/HALLO_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Hallo Music",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/HALLO_MUSIC/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/HALLO_MUSIC/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/HALLO_MUSIC/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/HALLO_MUSIC/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/HALLO_MUSIC/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/HALLO_MUSIC/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/HALLO_MUSIC/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/HALLO_MUSIC/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Hallo Series",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/HALLO_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/HALLO_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/HALLO_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/HALLO_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/HALLO_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/HALLO_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/HALLO_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/HALLO_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "SESC TV",
        logo = "https://i.imgur.com/Mu8O6CV.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/SESC_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/SESC_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/SESC_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/SESC_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/SESC_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/SESC_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/SESC_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/SESC_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "http://45.162.64.114/SESC_TV/index.m3u8",
            ),
            Source(
                url = "http://170.83.49.66:8083/SESCTVHD/index.m3u8",
            ),
            Source(
                url = "http://186.219.52.187/sesc_tv/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Top TV",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/TOPTV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TOPTV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TOPTV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TOPTV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TOPTV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TOPTV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TOPTV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TOPTV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Trace Latina",
        logo = "https://i.imgur.com/CUVAi4u.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/TRACE_LATINA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TRACE_LATINA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TRACE_LATINA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TRACE_LATINA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TRACE_LATINA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TRACE_LATINA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TRACE_LATINA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TRACE_LATINA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Trace Toca",
        logo = "https://i.imgur.com/6SpWnBR.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/TRACE_TOCA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TRACE_TOCA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TRACE_TOCA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TRACE_TOCA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TRACE_TOCA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TRACE_TOCA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TRACE_TOCA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TRACE_TOCA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Trace Urban",
        logo = "https://i.imgur.com/DLIbUMx.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/TRACE_URBAN/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TRACE_URBAN/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TRACE_URBAN/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TRACE_URBAN/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TRACE_URBAN/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TRACE_URBAN/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TRACE_URBAN/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TRACE_URBAN/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "TVideoNews",
        logo = "https://i.imgur.com/vstHOYx.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/TVIDEONEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TVIDEONEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TVIDEONEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TVIDEONEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TVIDEONEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TVIDEONEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TVIDEONEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TVIDEONEWS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://video01.logicahost.com.br/tvideonews/tvideonews/playlist.m3u8",
            ),
        ),
    ),
    Channel(
        name = "TV Pampa",
        logo = "https://mondrian.claro.com.br/channels/inverse/tv-pampa.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/TV_PAMPA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/TV_PAMPA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/TV_PAMPA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/TV_PAMPA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/TV_PAMPA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/TV_PAMPA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/TV_PAMPA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/TV_PAMPA/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Urban Kids",
        logo = "https://i.imgur.com/bDIeiIH.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/URBAN_KIDS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/URBAN_KIDS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/URBAN_KIDS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/URBAN_KIDS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/URBAN_KIDS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/URBAN_KIDS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/URBAN_KIDS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/URBAN_KIDS/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Urban Movies",
        logo = "https://i.imgur.com/wcjtSkq.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/URBAN_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/URBAN_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/URBAN_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/URBAN_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/URBAN_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/URBAN_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/URBAN_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/URBAN_MOVIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Urban Series",
        logo = "https://i.imgur.com/ZZOiT4x.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/URBAN_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/URBAN_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/URBAN_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/URBAN_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/URBAN_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/URBAN_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/URBAN_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/URBAN_SERIES/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Urban Travel",
        logo = "https://i.imgur.com/ST24ALh.png",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/URBAN_TRAVEL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/URBAN_TRAVEL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/URBAN_TRAVEL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/URBAN_TRAVEL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/URBAN_TRAVEL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/URBAN_TRAVEL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/URBAN_TRAVEL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/URBAN_TRAVEL/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "Vivax TV",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/VIVAX_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-ba.satlabscloud.com.br/VIVAX_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rj.satlabscloud.com.br/VIVAX_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-rs1.satlabscloud.com.br/VIVAX_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp4.satlabscloud.com.br/VIVAX_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp1.satlabscloud.com.br/VIVAX_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp3.satlabscloud.com.br/VIVAX_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
            Source(
                url = "https://cdn-sp2.satlabscloud.com.br/VIVAX_TV/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
            ),
        ),
    ),
    Channel(
        name = "A Caçadora de Relíquias",
        logo = "https://images.pluto.tv/channels/67e59a6557487a8b7fe73e23/colorLogoPNG_1745618050777.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-67e59a6557487a8b7fe73e23.m3u8",
            ),
        ),
    ),
    Channel(
        name = "A Feiticeira",
        logo = "https://images.pluto.tv/channels/631fa8dd7f25240007099a40/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-631fa8dd7f25240007099a40.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Acumuladores Obsessivos",
        logo = "https://images.pluto.tv/channels/656e2a4b4261ca00083aa99e/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-656e2a4b4261ca00083aa99e.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Adrenalina Pura TV",
        logo = "https://images.pluto.tv/channels/61b790b985706b00072cb797/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-61b790b985706b00072cb797.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Assombrações",
        logo = "https://images.pluto.tv/channels/620d1512c7986a0007220213/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-620d1512c7986a0007220213.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Avatar: A lenda de Aang",
        logo = "https://images.pluto.tv/channels/6759eeb1bd523200083b4f29/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6759eeb1bd523200083b4f29.m3u8",
            ),
        ),
    ),
    Channel(
        name = "BET Pluto TV",
        logo = "https://images.pluto.tv/channels/5ff768b6a4c8b80008498610/colorLogoPNG_1784064875197.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5ff768b6a4c8b80008498610.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Baby Shark TV",
        logo = "https://images.pluto.tv/channels/63da6bcd60bc8f0008a5d364/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-63da6bcd60bc8f0008a5d364.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Babyfirst",
        logo = "https://images.pluto.tv/channels/5f4fb4cf605ddf000748e16f/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f4fb4cf605ddf000748e16f.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Beyblade",
        logo = "https://images.pluto.tv/channels/633dc392e0282400071b0d39/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-633dc392e0282400071b0d39.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Bob Esponja Calça Quadrada",
        logo = "https://images.pluto.tv/channels/62545c0b002f4b0007688b61/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-62545c0b002f4b0007688b61.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Boruto: Naruto Next Generations",
        logo = "https://images.pluto.tv/channels/656f389c3944b60008e5bdab/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-656f389c3944b60008e5bdab.m3u8",
            ),
        ),
    ),
    Channel(
        name = "CBS News",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-62310f66d5888f0007534342.m3u8",
            ),
        ),
    ),
    Channel(
        name = "CSI: Miami",
        logo = "https://images.pluto.tv/channels/63eb9c5351f5d000085e8d7e/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-63eb9c5351f5d000085e8d7e.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Canal Educacao",
        logo = "https://i.imgur.com/OOB7nrS.png",
        sources = listOf(
            Source(
                url = "https://canaleducacao-stream.ebc.com.br/index.m3u8",
            ),
            Source(
                url = "http://45.162.64.114/CANAL_EDUCACAO/index.m3u8",
            ),
            Source(
                url = "http://45.177.114.115/CANAL_EDUCACAO/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Canal Futura",
        logo = "https://i.imgur.com/LgynEBC.png",
        sources = listOf(
            Source(
                url = "http://45.162.64.114/FUTURA/index.m3u8",
            ),
            Source(
                url = "http://170.83.16.50/FUTURA/index.m3u8",
            ),
            Source(
                url = "http://186.219.52.187/futura/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Canal Gov",
        logo = "https://i.imgur.com/rHPY0Yv.png",
        sources = listOf(
            Source(
                url = "https://canalgov-stream.ebc.com.br/index.m3u8",
            ),
            Source(
                url = "http://45.177.114.115/TV_BRASIL_2/index.m3u8",
            ),
            Source(
                url = "http://168.197.104.22/TV_BRASIL_2/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Canal UOL",
        logo = "https://conteudo.imguol.com.br/c/play/logo_canaluol_2024.svg",
        sources = listOf(
            Source(
                url = "https://video24.mais.uol.com.br/live/6146.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Canal do Boi",
        logo = "https://i.imgur.com/pVM5MhS.png",
        sources = listOf(
            Source(
                url = "http://45.162.64.114/CANAL_DO_BOI/index.m3u8",
            ),
            Source(
                url = "http://168.197.104.22/CANAL_DO_BOI/index.m3u8",
            ),
            Source(
                url = "http://170.83.16.50/CANAL_DO_BOI/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Caçadores de Óvnis",
        logo = "https://images.pluto.tv/channels/656e2a10954b020008ed167c/colorLogoPNG_1732041732595.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-656e2a10954b020008ed167c.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Charmed: Jovens Bruxas",
        logo = "https://images.pluto.tv/channels/67f9602ee173fa5664fafae8/colorLogoPNG_1747333127716.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-67f9602ee173fa5664fafae8.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Chef TV",
        logo = "https://i.imgur.com/UYksTee.png",
        sources = listOf(
            Source(
                url = "http://168.197.104.22/CHEF_TV/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Cocoricó",
        logo = "https://images.pluto.tv/channels/62d969fd8451a30007f0fd94/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-62d969fd8451a30007f0fd94.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Comedy Central Pluto TV",
        logo = "https://images.pluto.tv/channels/5f357e91b18f0b00073583d2/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f357e91b18f0b00073583d2.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Comedy Central South Park",
        logo = "https://images.pluto.tv/channels/609ae66b359b270007869ff1/colorLogoPNG_1733160636316.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-609ae66b359b270007869ff1.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Cultura Fast",
        sources = listOf(
            Source(
                url = "https://fpa-gateway.tvcultura.com.br:8181/memfs/606caef0-a290-413d-9f1f-8fcdb3a73831.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Death Note",
        logo = "https://images.pluto.tv/channels/625464a945b6a200079257d1/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-625464a945b6a200079257d1.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Detetives Médicos",
        logo = "https://images.pluto.tv/channels/638df93ae2f2a3000737c168/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-638df93ae2f2a3000737c168.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Diff’rent Strokes Arnold",
        logo = "https://images.pluto.tv/channels/61f1d27a189ed10007b7393e/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-61f1d27a189ed10007b7393e.m3u8",
            ),
        ),
    ),
    Channel(
        name = "DumDum",
        logo = "https://upload.wikimedia.org/wikipedia/commons/c/c0/DumDum_logo.svg",
        sources = listOf(
            Source(
                url = "http://45.162.64.114/ZOOMOO_KIDS/index.m3u8",
            ),
            Source(
                url = "http://168.197.104.22/ZOOMOO/index.m3u8",
            ),
            Source(
                url = "http://186.219.52.187/zoomoo/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Estado Paranormal",
        logo = "https://images.pluto.tv/channels/656e2a81954b020008ed17a4/colorLogoPNG_1732041604478.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-656e2a81954b020008ed17a4.m3u8",
            ),
        ),
    ),
    Channel(
        name = "FIFA+",
        logo = "https://images.pluto.tv/channels/66997e8d3a4ad20008e50be9/colorLogoPNG_1749841306387.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-66997e8d3a4ad20008e50be9.m3u8",
            ),
        ),
    ),
    Channel(
        name = "FailArmy",
        logo = "https://images.pluto.tv/channels/5f5141c1605ddf000748eb1b/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f5141c1605ddf000748eb1b.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Filmelier TV",
        logo = "https://images.pluto.tv/channels/633dcebd80386500074a2461/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-633dcebd80386500074a2461.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Filmes Suspense",
        logo = "https://images.pluto.tv/channels/5f171d3442a0500007362f22/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f171d3442a0500007362f22.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Futura",
        logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8e/Canal_Futura.png/960px-Canal_Futura.png",
        sources = listOf(
            Source(
                url = "http://168.197.104.22/FUTURA/index.m3u8",
            ),
            Source(
                url = "http://170.83.49.66:8083/FUTURAHD/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Homeful",
        logo = "https://images.pluto.tv/channels/67603668f433320008760af1/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-67603668f433320008760af1.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Hunter x Hunter",
        logo = "https://images.pluto.tv/channels/65d9167818036500080e8780/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-65d9167818036500080e8780.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Inspetor Bugiganga",
        logo = "https://images.pluto.tv/channels/69162648d03565d6c8c8df97/colorLogoPNG_1769479138027.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-69162648d03565d6c8c8df97.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Inuyasha",
        logo = "https://images.pluto.tv/channels/66b26681d2d50d00083abe8b/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-66b26681d2d50d00083abe8b.m3u8",
            ),
        ),
    ),
    Channel(
        name = "JoJo’s Bizarre Adventure",
        logo = "https://images.pluto.tv/channels/66c7982f6838ee00085f0d24/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-66c7982f6838ee00085f0d24.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Jornada nas Estrelas A Nova Geração",
        logo = "https://images.pluto.tv/channels/69162ad79505d0f3b1ebf07d/colorLogoPNG_1766066212478.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-69162ad79505d0f3b1ebf07d.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Jornada nas Estrelas Deep Space Nine",
        logo = "https://images.pluto.tv/channels/69162af591700f4c4c135c95/colorLogoPNG_1766066526851.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-69162af591700f4c4c135c95.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Jornada nas Estrelas Voyager",
        logo = "https://images.pluto.tv/channels/69162b1ef189e235142b17ab/colorLogoPNG_1766150123044.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-69162b1ef189e235142b17ab.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Kenan & Kel",
        logo = "https://images.pluto.tv/channels/5ffcc5130fd98c0007f2e216/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5ffcc5130fd98c0007f2e216.m3u8",
            ),
        ),
    ),
    Channel(
        name = "KpopTV Play",
        logo = "https://i.imgur.com/Tf0vweF.png",
        sources = listOf(
            Source(
                url = "https://giatv.bozztv.com/giatv/giatv-kpoptvplay/kpoptvplay/playlist.m3u8",
            ),
        ),
    ),
    Channel(
        name = "MTV Are you the One?",
        logo = "https://images.pluto.tv/channels/5f6108d8cc331900075e98e4/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f6108d8cc331900075e98e4.m3u8",
            ),
        ),
    ),
    Channel(
        name = "MTV Biggest Pop",
        logo = "https://images.pluto.tv/channels/6047fbdbbb776a0007e7f2ff/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6047fbdbbb776a0007e7f2ff.m3u8",
            ),
        ),
    ),
    Channel(
        name = "MTV Catfish",
        logo = "https://images.pluto.tv/channels/626c2a3502d84a0007cec817/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-626c2a3502d84a0007cec817.m3u8",
            ),
        ),
    ),
    Channel(
        name = "MTV Com o Ex",
        logo = "https://images.pluto.tv/channels/61a528267e1b8b0007357920/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-61a528267e1b8b0007357920.m3u8",
            ),
        ),
    ),
    Channel(
        name = "MTV Dating",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6851bb3426beced4f2f67ee6.m3u8",
            ),
        ),
    ),
    Channel(
        name = "MTV Jovens e Mães",
        logo = "https://images.pluto.tv/channels/620fdc7d8a36fc000710e3ba/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-620fdc7d8a36fc000710e3ba.m3u8",
            ),
        ),
    ),
    Channel(
        name = "MTV Pluto TV",
        logo = "https://images.pluto.tv/channels/5f1212fb81e85c00077ae9ef/colorLogoPNG_1759263027326.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f1212fb81e85c00077ae9ef.m3u8",
            ),
        ),
    ),
    Channel(
        name = "MTV Reality",
        logo = "https://images.pluto.tv/channels/6851bdfc9ac48fde5e07f5ae/colorLogoPNG_1783347653051.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6851bdfc9ac48fde5e07f5ae.m3u8",
            ),
        ),
    ),
    Channel(
        name = "MTV Rocks",
        logo = "https://images.pluto.tv/channels/66a01e07d2d50d0008100d6a/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-66a01e07d2d50d0008100d6a.m3u8",
            ),
        ),
    ),
    Channel(
        name = "MTV Rupaul's Drag Race",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-645111f1d8436e00081bb2bd.m3u8",
            ),
        ),
    ),
    Channel(
        name = "MTV Shore",
        logo = "https://images.pluto.tv/channels/625463563b8ddc0007134aeb/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-625463563b8ddc0007134aeb.m3u8",
            ),
        ),
    ),
    Channel(
        name = "MacGyver",
        logo = "https://images.pluto.tv/channels/63eb9dc84e83e70008abea92/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-63eb9dc84e83e70008abea92.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Mais MasterChef Brasil",
        logo = "https://images.pluto.tv/channels/681111be5e0764e297fb200e/colorLogoPNG_1749582970597.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-681111be5e0764e297fb200e.m3u8",
            ),
        ),
    ),
    Channel(
        name = "MasterChef",
        logo = "https://images.pluto.tv/channels/6077045b6031bd00078de127/colorLogoPNG_1785430539950.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6077045b6031bd00078de127.m3u8",
            ),
        ),
    ),
    Channel(
        name = "MasterChef Brasil Profissionais",
        logo = "https://images.pluto.tv/channels/681110afc188aa63faa147e0/colorLogoPNG_1749226372857.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-681110afc188aa63faa147e0.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Mistérios sem Solução",
        logo = "https://images.pluto.tv/channels/62b5c5a064163d0007b2efe6/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-62b5c5a064163d0007b2efe6.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Moranguinho",
        logo = "https://images.pluto.tv/channels/63eba189c111bc0008ff59c5/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-63eba189c111bc0008ff59c5.m3u8",
            ),
        ),
    ),
    Channel(
        name = "MyTime Movie Network",
        logo = "https://i.imgur.com/aiGQtzI.png",
        sources = listOf(
            Source(
                url = "https://appletree-mytime-samsungbrazil.amagi.tv/playlist.m3u8",
            ),
        ),
    ),
    Channel(
        name = "NCIS",
        logo = "https://images.pluto.tv/channels/63eb9fdda995710008991c54/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-63eb9fdda995710008991c54.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Naruto",
        logo = "https://images.pluto.tv/channels/5f6df5a173d7340007c559f7/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f6df5a173d7340007c559f7.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Naruto Shippuden",
        logo = "https://images.pluto.tv/channels/64c92f965580090008084968/colorLogoPNG_1785430970647.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-64c92f965580090008084968.m3u8",
            ),
        ),
    ),
    Channel(
        name = "NatureTime",
        logo = "https://images.pluto.tv/channels/681ba2ad93d3d19bcab47433/colorLogoPNG_1785430696843.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-681ba2ad93d3d19bcab47433.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Nick Jr. Club",
        logo = "https://images.pluto.tv/channels/6824ce95f09106f4b18f4114/colorLogoPNG_1747935179470.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6824ce95f09106f4b18f4114.m3u8",
            ),
        ),
    ),
    Channel(
        name = "NickOnline",
        logo = "https://x1colegal.com/logo.png",
        sources = listOf(
            Source(
                url = "https://x1colegal.com/hls/stream.m3u8",
            ),
        ),
    ),
    Channel(
        name = "NickOnline Bob Esponja",
        logo = "https://x1colegal.com/nickonlinebobesponja.png",
        sources = listOf(
            Source(
                url = "https://bob.x1colegal.com/hls/stream.m3u8",
            ),
        ),
    ),
    Channel(
        name = "NickToons Brasil",
        logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/97/Nicktoons_logo_%282023%29.svg/330px-Nicktoons_logo_%282023%29.svg.png",
        sources = listOf(
            Source(
                url = "https://stmv2.srvif.com/nicktoons/nicktoons/playlist.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Nickelodeon",
        sources = listOf(
            Source(
                url = "https://stmv2.srvif.com/gafeab/gafeab/playlist.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Nickelodeon Clássico",
        logo = "https://images.pluto.tv/channels/6824ce10c5d53e1351ceb8d1/colorLogoPNG_1788384899252.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6824ce10c5d53e1351ceb8d1.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Nickelodeon Teen",
        logo = "https://images.pluto.tv/channels/60f5fabf0721880007cd50e3/colorLogoPNG_1767888207915.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-60f5fabf0721880007cd50e3.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Nickelodeon Toons",
        logo = "https://images.pluto.tv/channels/645951c0e94c38000802d2cb/colorLogoPNG_1767888119852.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-645951c0e94c38000802d2cb.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Nickelodeon iCarly",
        logo = "https://images.pluto.tv/channels/620ff46e0a576e0007dc2f89/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-620ff46e0a576e0007dc2f89.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Numbers",
        logo = "https://images.pluto.tv/channels/67f960c5441853fe50e7afc1/colorLogoPNG_1747430170532.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-67f960c5441853fe50e7afc1.m3u8",
            ),
        ),
    ),
    Channel(
        name = "O Encantador de Cães",
        logo = "https://images.pluto.tv/channels/61099df8cee03b00074b2ecf/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-61099df8cee03b00074b2ecf.m3u8",
            ),
        ),
    ),
    Channel(
        name = "O Homem que veio do Céu",
        logo = "https://images.pluto.tv/channels/62052d3b4eeb740007fbe125/colorLogoPNG_1732044609381.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-62052d3b4eeb740007fbe125.m3u8",
            ),
        ),
    ),
    Channel(
        name = "O Reino Infantil",
        logo = "https://images.pluto.tv/channels/5f5c216df68f920007888315/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f5c216df68f920007888315.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Oggy e as Baratas Tontas",
        logo = "https://images.pluto.tv/channels/63221bafdc6e110007b50270/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-63221bafdc6e110007b50270.m3u8",
            ),
        ),
    ),
    Channel(
        name = "One Piece",
        logo = "https://images.pluto.tv/channels/624b1c8d4321e200073ee421/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-624b1c8d4321e200073ee421.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Os Arquivos do FBI",
        logo = "https://images.pluto.tv/channels/620d12a82e8ac50007c269c3/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-620d12a82e8ac50007c269c3.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Os Padrinhos Mágicos",
        logo = "https://images.pluto.tv/channels/63221e41af69b500076f84e7/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-63221e41af69b500076f84e7.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Os Smurfs",
        logo = "https://images.pluto.tv/channels/68b88ae1943f6fb1fb2ad749/colorLogoPNG_1762273919644.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-68b88ae1943f6fb1fb2ad749.m3u8",
            ),
        ),
    ),
    Channel(
        name = "PFL MMA",
        logo = "https://images.pluto.tv/channels/64f6180130ab3300083d896b/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-64f6180130ab3300083d896b.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pegadinhas Just for Laughs",
        logo = "https://images.pluto.tv/channels/67802a22a5d8215f99dcee30/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-67802a22a5d8215f99dcee30.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Aliens",
        logo = "https://images.pluto.tv/channels/6806d65e84f24b70109485fa/colorLogoPNG_1746817107468.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6806d65e84f24b70109485fa.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Animais",
        logo = "https://images.pluto.tv/channels/6474aa984cfc2c0008883a92/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6474aa984cfc2c0008883a92.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Anime",
        logo = "https://images.pluto.tv/channels/5f12136385bccc00070142ed/colorLogoPNG_1785430875394.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f12136385bccc00070142ed.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Anime Ação",
        logo = "https://images.pluto.tv/channels/604b79c558393100078faeef/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-604b79c558393100078faeef.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Bang Bang",
        logo = "https://images.pluto.tv/channels/663b9dc7cb3ea10008f1a0ce/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-663b9dc7cb3ea10008f1a0ce.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Canal UOL",
        logo = "https://images.pluto.tv/channels/64b9370b409629000802d32b/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-64b9370b409629000802d32b.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Cine Clássicos",
        logo = "https://images.pluto.tv/channels/5fa1612a669ba0000702017b/colorLogoPNG_1733440556804.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5fa1612a669ba0000702017b.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Cine Comédia",
        logo = "https://images.pluto.tv/channels/5f12101f0b12f00007844c7c/colorLogoPNG_1732662798298.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f12101f0b12f00007844c7c.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Cine Comédia Romântica",
        logo = "https://images.pluto.tv/channels/62545ed3dab4380007582f7c/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-62545ed3dab4380007582f7c.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Cine Crime",
        logo = "https://images.pluto.tv/channels/6479ff764f5ba5000878dfe2/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6479ff764f5ba5000878dfe2.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Cine Drama",
        logo = "https://images.pluto.tv/channels/5f1210d14ae1f80007bafb1d/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f1210d14ae1f80007bafb1d.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Cine Família",
        logo = "https://images.pluto.tv/channels/5f171f032cd22e0007f17f3d/colorLogoPNG_1788969213886.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f171f032cd22e0007f17f3d.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Cine Inspiração",
        logo = "https://images.pluto.tv/channels/5fa991b1f09e020007e78626/colorLogoPNG_1748874366972.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5fa991b1f09e020007e78626.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Cine Romance",
        logo = "https://images.pluto.tv/channels/5f171f988ab9780007fa95ea/colorLogoPNG_1771344977802.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f171f988ab9780007fa95ea.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Cine Sucessos",
        logo = "https://images.pluto.tv/channels/5f120e94a5714d00074576a1/colorLogoPNG_1785430162956.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f120e94a5714d00074576a1.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Cine Terror",
        logo = "https://images.pluto.tv/channels/5f12111c9e6c2c00078ef3bb/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f12111c9e6c2c00078ef3bb.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Cozinha",
        logo = "https://images.pluto.tv/channels/5f1ef23020a5ac0007e5e8ea/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f1ef23020a5ac0007e5e8ea.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Desenhos Clássicos",
        logo = "https://images.pluto.tv/channels/655e5c4d2c46f3000877a54b/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-655e5c4d2c46f3000877a54b.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Esportes",
        logo = "https://images.pluto.tv/channels/5f32d2db0af67400077f29c4/colorLogoPNG_1782950090441.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f32d2db0af67400077f29c4.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Ficção Científica",
        logo = "https://images.pluto.tv/channels/5fa15ad6367e170007cdd098/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5fa15ad6367e170007cdd098.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Filmes Aventura",
        logo = "https://images.pluto.tv/channels/66c79a4262e5510008ff68a5/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-66c79a4262e5510008ff68a5.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Filmes Ação",
        logo = "https://images.pluto.tv/channels/5f120f41b7d403000783a6d6/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f120f41b7d403000783a6d6.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Filmes Nacionais",
        logo = "https://images.pluto.tv/channels/5f5a545d0dbf7f0007c09408/colorLogoPNG_1732044719987.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f5a545d0dbf7f0007c09408.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Filmes de Luta",
        logo = "https://images.pluto.tv/channels/6806d62369aec5b19cd628c0/colorLogoPNG_1746817781806.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6806d62369aec5b19cd628c0.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV História",
        logo = "https://images.pluto.tv/channels/5f1ef1a8cec6be00072a7ac9/colorLogoPNG_1754405659295.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f1ef1a8cec6be00072a7ac9.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Investigação",
        logo = "https://images.pluto.tv/channels/5f32cf37c9ff2b00082adbc8/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f32cf37c9ff2b00082adbc8.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Junior",
        logo = "https://images.pluto.tv/channels/5f12141b146d760007934ea7/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f12141b146d760007934ea7.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV KFOOD",
        logo = "https://images.pluto.tv/channels/633ee9ba83c08f00076b60a6/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-633ee9ba83c08f00076b60a6.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Karaokê por Stingray",
        logo = "https://images.pluto.tv/channels/604b99d633a72b00078e05ad/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-604b99d633a72b00078e05ad.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Kids",
        logo = "https://images.pluto.tv/channels/5f1214a637c6fd00079c652f/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f1214a637c6fd00079c652f.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Kids Club",
        logo = "https://images.pluto.tv/channels/66c8cae7fed35b0008580ec0/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-66c8cae7fed35b0008580ec0.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Mistérios",
        logo = "https://images.pluto.tv/channels/5fac52f142044f00078e2a51/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5fac52f142044f00078e2a51.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Natureza",
        logo = "https://images.pluto.tv/channels/5f1213ba0ecebc00070e170f/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f1213ba0ecebc00070e170f.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Negócio Fechado",
        logo = "https://images.pluto.tv/channels/64ad7394798def00087b2bfe/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-64ad7394798def00087b2bfe.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Netmovies",
        logo = "https://images.pluto.tv/channels/663b9de4f999220008230fa8/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-663b9de4f999220008230fa8.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Novelas",
        logo = "https://images.pluto.tv/channels/5f512365abe1f50007d3ff56/colorLogoPNG_1785430486083.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f512365abe1f50007d3ff56.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Paisagens por Stingray",
        logo = "https://images.pluto.tv/channels/604a8dedbca75b0007b1c753/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-604a8dedbca75b0007b1c753.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Policial",
        logo = "https://images.pluto.tv/channels/678fdf9e3de7c8cf948e8824/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-678fdf9e3de7c8cf948e8824.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Record News",
        logo = "https://images.pluto.tv/channels/6102e04e9ab1db0007a980a1/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6102e04e9ab1db0007a980a1.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Retrô",
        logo = "https://images.pluto.tv/channels/5f1212ad1728050007a523b8/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f1212ad1728050007a523b8.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Shows por Stingray",
        logo = "https://images.pluto.tv/channels/604b91e0692f770007d9f33f/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-604b91e0692f770007d9f33f.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Star Trek",
        logo = "https://images.pluto.tv/channels/5f99ac4fded33000078f29ab/colorLogoPNG_1753988616763.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f99ac4fded33000078f29ab.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Séries Ação",
        logo = "https://images.pluto.tv/channels/6474ab1da51cb80008bfb5f4/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6474ab1da51cb80008bfb5f4.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Séries Comédia",
        logo = "https://images.pluto.tv/channels/655e5bc94261ca000810cb17/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-655e5bc94261ca000810cb17.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Séries Criminais",
        logo = "https://images.pluto.tv/channels/6474ab5cdc7a760008745008/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6474ab5cdc7a760008745008.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Séries Drama",
        logo = "https://images.pluto.tv/channels/65f060d84e01740008d7421f/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-65f060d84e01740008d7421f.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Séries Novelescas",
        logo = "https://images.pluto.tv/channels/691627e4a29a6123e400b3e0/colorLogoPNG_1764878235665.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-691627e4a29a6123e400b3e0.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Séries Sci-Fi",
        logo = "https://images.pluto.tv/channels/63d2ba2f60bc8f0008981a0e/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-63d2ba2f60bc8f0008981a0e.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Terror Trash",
        logo = "https://images.pluto.tv/channels/66aa67493a4ad2000806d91b/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-66aa67493a4ad2000806d91b.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Turbo",
        logo = "https://images.pluto.tv/channels/6014761dfb91870008ea6463/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6014761dfb91870008ea6463.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Viagens",
        logo = "https://images.pluto.tv/channels/5f32d432d612e50007e56133/colorLogoPNG_1769016717502.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f32d432d612e50007e56133.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pluto TV Vida Real",
        logo = "https://images.pluto.tv/channels/5f32d4d9ec194100070c7449/colorLogoPNG_1732044921019.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f32d4d9ec194100070c7449.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pokémon",
        logo = "https://images.pluto.tv/channels/687007a8ee4155e89a8f6d67/colorLogoPNG_1752174174089.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-687007a8ee4155e89a8f6d67.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Popeye",
        logo = "https://images.pluto.tv/channels/677d93f37bffa600080795e7/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-677d93f37bffa600080795e7.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Pronto-socorro: Histórias De Emergência",
        logo = "https://images.pluto.tv/channels/61bb72a7bf8c520007a8fd27/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-61bb72a7bf8c520007a8fd27.m3u8",
            ),
        ),
    ),
    Channel(
        name = "RACER Brasil",
        logo = "https://images.pluto.tv/channels/65a6818c7bdc8d0008457b21/colorLogoPNG_1746817068891.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-65a6818c7bdc8d0008457b21.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Realmadrid TV",
        logo = "https://images.pluto.tv/channels/63dac28760bc8f0008a7654b/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-63dac28760bc8f0008a7654b.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Red Bull TV BR",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-67813f3162bf016db944c9ab.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Rede TV!",
        logo = "https://i.imgur.com/ZJgD38F.png",
        sources = listOf(
            Source(
                url = "http://45.162.64.114/REDE_TV/index.m3u8",
            ),
            Source(
                url = "http://168.197.104.22/REDE_TV/index.m3u8",
            ),
            Source(
                url = "http://170.83.16.50/REDE_TV/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Rookie Blue",
        logo = "https://images.pluto.tv/channels/64ff2d8c6625510008c5a512/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-64ff2d8c6625510008c5a512.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Runtime",
        logo = "https://images.pluto.tv/channels/62c5d32e2c48f9000715b6e9/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-62c5d32e2c48f9000715b6e9.m3u8",
            ),
        ),
    ),
    Channel(
        name = "SFT Combat",
        logo = "https://images.pluto.tv/channels/6660b636cb3ea10008429c6a/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6660b636cb3ea10008429c6a.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Smithsonian Channel Pluto TV",
        logo = "https://images.pluto.tv/channels/6298bd10d88ef000073f16b7/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6298bd10d88ef000073f16b7.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Sony One Shark Tank Brasil",
        logo = "https://images.pluto.tv/channels/6647c0b91050b60008390de4/colorLogoPNG_1772461335237.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6647c0b91050b60008390de4.m3u8",
            ),
        ),
    ),
    Channel(
        name = "South Park: Coleção Cartman",
        logo = "https://images.pluto.tv/channels/65df71008b24c80008f04281/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-65df71008b24c80008f04281.m3u8",
            ),
        ),
    ),
    Channel(
        name = "South Park: Coleção Kenny",
        logo = "https://images.pluto.tv/channels/65df704366eec8000898e32f/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-65df704366eec8000898e32f.m3u8",
            ),
        ),
    ),
    Channel(
        name = "South Park: Coleção Kyle",
        logo = "https://images.pluto.tv/channels/65df713dec9fda0008b7a81d/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-65df713dec9fda0008b7a81d.m3u8",
            ),
        ),
    ),
    Channel(
        name = "South Park: Coleção Stan",
        logo = "https://images.pluto.tv/channels/65df70b0f7f0af0008c3b316/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-65df70b0f7f0af0008c3b316.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Super Onze",
        logo = "https://images.pluto.tv/channels/63988c2750108d00072e2686/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-63988c2750108d00072e2686.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Tastemade",
        logo = "https://images.pluto.tv/channels/5fd1419a3b4f4b000773ba85/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5fd1419a3b4f4b000773ba85.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Tastemade Casa",
        logo = "https://images.pluto.tv/channels/68b88821e542386ab0bf5bef/colorLogoPNG_1759795743690.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-68b88821e542386ab0bf5bef.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Tastemade Viagem",
        logo = "https://images.pluto.tv/channels/68b8875777201ec428d9eaa5/colorLogoPNG_1759784936254.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-68b8875777201ec428d9eaa5.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Teletubbies",
        logo = "https://images.pluto.tv/channels/64e50055286f6b000838c067/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-64e50055286f6b000838c067.m3u8",
            ),
        ),
    ),
    Channel(
        name = "The Pet Collective",
        logo = "https://images.pluto.tv/channels/5f515ebac01c0f00080e8439/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f515ebac01c0f00080e8439.m3u8",
            ),
        ),
    ),
    Channel(
        name = "The Walking Dead by AMC",
        logo = "https://images.pluto.tv/channels/678aa104680721c77c506746/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-678aa104680721c77c506746.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Tokusato",
        logo = "https://images.pluto.tv/channels/5ff609de50ab210008025c1b/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5ff609de50ab210008025c1b.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Top Barça",
        logo = "https://images.pluto.tv/channels/6888ee858f4a4aa11feb9430/colorLogoPNG_1753987309681.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6888ee858f4a4aa11feb9430.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Travel Box Brazil",
        logo = "https://i.imgur.com/3tBJERH.png",
        sources = listOf(
            Source(
                url = "http://168.197.104.22/TRAVEL_BOX_BRASIL/index.m3u8",
            ),
            Source(
                url = "http://170.83.49.66:8083/TRAVELBOXHD/index.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Turma da Mônica",
        logo = "https://images.pluto.tv/channels/5f997e44949bc70007a6941e/colorLogoPNG_1786485992908.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f997e44949bc70007a6941e.m3u8",
            ),
        ),
    ),
    Channel(
        name = "UFC",
        logo = "https://images.pluto.tv/channels/69a20556814d27f4ae630a92/colorLogoPNG_1772226745279.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-69a20556814d27f4ae630a92.m3u8",
            ),
        ),
    ),
    Channel(
        name = "World Poker Tour",
        logo = "https://images.pluto.tv/channels/63eba66da8b2270008436b10/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-63eba66da8b2270008436b10.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Yu-Gi-Oh",
        logo = "https://images.pluto.tv/channels/63988a50be012600070f5db3/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-63988a50be012600070f5db3.m3u8",
            ),
        ),
    ),
    Channel(
        name = "Z Nation",
        logo = "https://images.pluto.tv/channels/66b3af48d2d50d00083d6936/colorLogoPNG_1785430432127.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-66b3af48d2d50d00083d6936.m3u8",
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
