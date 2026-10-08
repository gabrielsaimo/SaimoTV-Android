package br.com.saimo.tv

// Gerado de SaimoPlayer/catalogo.txt e restritos.txt — não editar à mão.
// Regenerar com scripts/gen_catalog.py para manter Mac e TV Box iguais.

data class Source(
    val url: String,
    val referer: String? = null,
    val userAgent: String? = null,
    val keyId: String? = null,
    val key: String? = null,
    val quality: String? = null,
) {
    val isDash: Boolean get() = url.contains(".mpd", ignoreCase = true)
    val isHls: Boolean get() =
        url.contains(".m3u8", ignoreCase = true) ||
            url.substringBefore('?').endsWith(".txt", ignoreCase = true)
}

data class Channel(
    val name: String,
    val logo: String? = null,
    val sources: List<Source>,
    val categoria: String? = null,
)


private fun build_CATALOG_PART_0(): List<Channel> = listOf(
    Channel(
        name = "A&E",
        logo = "https://img.faz-o-eli.online/ae.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/ae/ae.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc2MC5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/ae.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518547.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518548.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518549.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY0Ny5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc2Mi5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc2My5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90647.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71760.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71761.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71762.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71763.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90647.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71760.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71761.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71762.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71763.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90647.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71760.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71761.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71762.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71763.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/81599.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13497.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/23156.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/23157.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35224.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35225.ts",
                quality = "HD",
            ),
        ),
        categoria = "Documentários",
    ),
    Channel(
        name = "Adult Swim",
        logo = "https://img.faz-o-eli.online/adultswim.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/adultswim/adultswim.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8yNTE5NTcubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/adultswim.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518592.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518593.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518594.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8yNTE5NjAubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8yNTE5NTgubTN1OA.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8yNTE5NTkubTN1OA.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/251957.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/251958.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/251959.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/251960.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/251961.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/251957.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/251958.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/251959.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/251960.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/251961.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/251957.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/251958.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/251959.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/251960.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/251961.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "AMC",
        logo = "https://img.faz-o-eli.online/amc.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/amc/amc.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc2OC5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/amc.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324427.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324429.m3u8",
                quality = "HD · 960x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY0Ni5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc3MC5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc3MS5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90646.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71768.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71769.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71770.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71771.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90646.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71768.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71769.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71770.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71771.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90646.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71768.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71769.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71770.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71771.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/81601.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13498.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/23687.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/23688.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35226.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35227.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Animal Planet",
        logo = "https://img.faz-o-eli.online/animalplanet.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/animalplanet/animalplanet.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc3Mi5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/animalplanet.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/296619.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/296620.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/296621.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324374.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc3NC5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc3NS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc3Ni5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71774.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71772.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71773.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71775.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71776.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71774.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71772.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71773.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71775.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71776.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71774.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71772.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71773.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71775.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71776.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/304490.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/304493.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/304491.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/304492.ts",
                quality = "HD",
            ),
        ),
        categoria = "Documentários",
    ),
    Channel(
        name = "Band",
        logo = "https://mondrian.claro.com.br/channels/inverse/band.png",
        sources = listOf(
            Source(
                url = "https://media.cdntvms.com.br/band_sat/index.m3u8",
                quality = "SD · 1024x576",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324357.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324358.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324376.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Cartoon Network",
        logo = "https://img.faz-o-eli.online/cartoonnetwork.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/cartoonnetwork/cartoonnetwork.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgyOS5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/cartoonnetwork.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882622.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882623.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882624.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgzMC5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgzMS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgzMi5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71830.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71828.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71829.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71831.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71832.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71830.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71828.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71829.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71831.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71832.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71830.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71828.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71829.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71831.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71832.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/125439.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13504.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8698.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8699.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/38015.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/38016.ts",
                quality = "HD",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "CNN Brasil",
        logo = "https://img.faz-o-eli.online/cnnbrasil.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/cnnbrasil/cnnbrasil.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgzNy5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/cnnbrasil.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://amg01391-sbtinfast-amg01391c4-lg-br-4597.playouts.now.amagi.tv/playlist/amg01391-addigital-cnnbrasil-lgbr/playlist.m3u8",
                quality = "Qualidade não informada",
                userAgent = "Mozilla/5.0 (Linux; U; Android 13; T610K Build/TP1A.220624.014; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/126.0.6478.71 Mobile Safari/537.36 OPR/87.0.2254.75258",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324379.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459554.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459555.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459556.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc5OC5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgzOS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg0MC5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71798.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71837.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71838.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71839.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71840.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71798.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71837.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71838.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71839.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71840.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71798.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71837.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71838.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71839.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71840.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/81597.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8711.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8712.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30166.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30167.ts",
                quality = "HD",
            ),
        ),
        categoria = "Notícias",
    ),
    Channel(
        name = "CNN Brasil Money",
        logo = "https://mondrian.claro.com.br/channels/inverse/cnn-brasil-money.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8yOTU0ODIubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://amg01391-amg01391c57-amgplt0026.playout.now3.amagi.tv/playlist/amg01391-amg01391c57-amgplt0026/playlist.m3u8",
                quality = "Qualidade não informada",
                userAgent = "Mozilla/5.0 (Linux; U; Android 13; T610K Build/TP1A.220624.014; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/126.0.6478.71 Mobile Safari/537.36 OPR/87.0.2254.75258",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8yOTU0ODYubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8yOTU0ODMubTN1OA.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8yOTU0ODQubTN1OA.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/295482.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/295483.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/295484.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/295485.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/295486.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/295482.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/295483.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/295484.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/295485.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/295486.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/295482.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/295483.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/295484.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/295485.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/295486.ts",
                quality = "4K",
            ),
        ),
        categoria = "Notícias",
    ),
    Channel(
        name = "E!",
        logo = "https://commons.wikimedia.org/wiki/Special:FilePath/E%21_Logo_Flat_2012.svg?width=300",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkwMC5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://video49.mais.uol.com.br/live/4503.mpd",
                referer = "https://painel.play.uol.com.br/",
                quality = "Qualidade não informada",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "d021c9ff8ab94c1a583a0f5f2cc82725",
                key = "7de3fcc29e3194b9c65282a42cb7bec6",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518559.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518560.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518561.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM1MDYubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkwMi5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkwMy5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103506.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71900.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71901.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71902.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71903.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103506.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71900.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71901.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71902.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71903.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103506.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71900.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71901.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71902.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71903.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8782.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8783.ts",
                quality = "HD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "GE TV",
        logo = "https://mondrian.claro.com.br/channels/inverse/ge-tv.png",
        sources = listOf(
            Source(
                url = "https://dfr80qz435crc.cloudfront.net/EFGH/Amagi/Globo/GE_Fast_BR/GE_Fast.m3u8",
                quality = "FHD · 1920x1080",
                userAgent = "Mozilla/5.0 (Linux; U; Android 13; T610K Build/TP1A.220624.014; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/126.0.6478.71 Mobile Safari/537.36 OPR/87.0.2254.75258",
            ),
            Source(
                url = "https://amg00716-globo-amg00716c1-tcl-br-9495.playouts.now.amagi.tv/playlist.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460155.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460156.m3u8",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460157.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1139277.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "GloboNews",
        logo = "https://img.faz-o-eli.online/globonews.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/globonews/globonews.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTk5Ny5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/globonews.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/dsfrp5mjrb/out/v1/9fa07e663bc94e9f93c53726a558478a/cenc.mpd",
                referer = "https://www.primevideo.com/",
                quality = "FHD · 1920x1080",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "57ecd6a2086b99cc1d0452b102a7043b",
                key = "11386090a315fc1e88427aeed4a60900",
            ),
            Source(
                url = "http://79.127.238.228:14093",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324385.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459557.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459558.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459559.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDI0OC5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTk5OS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzExMTIubTN1OA.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90248.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71997.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71998.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71999.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131112.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90248.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71997.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71998.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71999.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131112.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90248.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71997.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71998.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71999.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131112.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/81598.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13523.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8853.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8854.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30168.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30169.ts",
                quality = "HD",
            ),
        ),
        categoria = "Notícias",
    ),
    Channel(
        name = "Globoplay Novelas",
        logo = "https://img.faz-o-eli.online/globonovelas.prev.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjQyNy5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/globonovelas.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/ds9ertnhrl/out/v1/cb791b7362754ba1b87d9474ccd95fa3/cenc.mpd",
                referer = "https://www.primevideo.com/",
                quality = "Qualidade não informada",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "eab4523b0358f3c59f1da92b3f478232",
                key = "253ef14355d987d4076b4544e4741977",
            ),
            Source(
                url = "http://79.127.238.228:14455",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324400.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518595.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518596.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518597.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0NTAubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjQyOS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjQzMC5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103450.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72427.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72428.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72429.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72430.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103450.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72427.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72428.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72429.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72430.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103450.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72427.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72428.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72429.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72430.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/107983.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13496.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/43930.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9215.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/131428.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9216.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/33453.ts",
                quality = "HD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/globonovelas/globonovelas.m3u8",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "GNT",
        logo = "https://img.faz-o-eli.online/gnt.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/gnt/gnt.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjEwMy5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/gnt.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/h9c8z9m1dq/out/v1/9b1b1aa15b4f471ea19674290554499e/cenc.mpd",
                referer = "https://www.primevideo.com/",
                quality = "FHD · 1920x1080",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "4c4d19c8cda9e78bae924e07ce49cb04",
                key = "4a3a155e480a67b81c5492befe07fa61",
            ),
            Source(
                url = "http://79.127.238.228:14402",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324387.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518568.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518569.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518570.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDYzOC5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjEwNC5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzExMzYubTN1OA.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90638.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72102.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72103.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72104.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131136.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90638.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72102.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72103.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72104.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131136.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90638.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72102.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72103.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72104.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131136.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/107984.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13527.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8891.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8890.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/66901.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/66902.ts",
                quality = "HD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "History",
        logo = "https://img.faz-o-eli.online/history.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjE0Ni5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/history.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://46.151.196.223:14410",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://45.177.114.114/HISTORY/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://170.83.16.50/HISTORY/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/296613.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/296614.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/296615.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDI0NC5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjE0OC5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjE0OS5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90244.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72146.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72147.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72148.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72149.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90244.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72146.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72147.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72148.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72149.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90244.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72146.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72147.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72148.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72149.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/304495.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/304497.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/304494.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/304496.ts",
                quality = "HD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/history/history.m3u8",
            ),
        ),
        categoria = "Documentários",
    ),
    Channel(
        name = "History 2",
        logo = "https://img.faz-o-eli.online/history2.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/history2.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/296617.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/296618.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90245.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72106.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72107.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72108.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72109.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90245.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72106.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72107.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72108.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72109.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90245.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72106.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72107.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72108.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72109.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/304499.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/304501.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/304498.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/304500.ts",
                quality = "HD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/history2/history2.m3u8",
            ),
        ),
        categoria = "Documentários",
    ),
    Channel(
        name = "Jovem Pan News",
        logo = "https://www.tvlogo.org/brazil/jovem-pan-news-br.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzQwMjMubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://amg01391-sbtinfast-amg01391c3-lg-us-8995.playouts.now.amagi.tv/playlist/amg01391-addigital-jovempan-lgus/playlist.m3u8",
                quality = "Qualidade não informada",
                userAgent = "Mozilla/5.0 (Linux; U; Android 13; T610K Build/TP1A.220624.014; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/126.0.6478.71 Mobile Safari/537.36 OPR/87.0.2254.75258",
            ),
            Source(
                url = "https://jmp2.uk/plu-6317ba014d4d040007227f72.m3u8",
                quality = "SD · 1216x684",
            ),
            Source(
                url = "http://170.83.49.66:8083/JOVEMPANNEWSHD/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://186.219.52.187/jp_news/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324388.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459560.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459561.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459562.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzQwMjEubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzQwMjQubTN1OA.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzQwMjUubTN1OA.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/134021.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/134023.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/134022.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/134024.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/134025.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/134021.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/134023.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/134022.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/134024.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/134025.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/134021.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/134023.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/134022.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/134024.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/134025.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108191.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/167984.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/107918.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/167985.ts",
                quality = "HD",
            ),
        ),
        categoria = "Notícias",
    ),
    Channel(
        name = "Megapix",
        logo = "https://img.faz-o-eli.online/megapix.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/megapix/megapix.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjE2Ni5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY4MC5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/megapix.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/21ilsertww/out/v1/124c84cbafc745b6b2c47fc9be606727/cenc.mpd",
                referer = "https://www.primevideo.com/",
                quality = "FHD · 1920x1080",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "cf8a2c054a3148309bce1039a9a5d603",
                key = "9417daf3a25dff3f78d76c1ebb550654",
            ),
            Source(
                url = "http://79.127.238.228:14592",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324451.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324453.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzExMDQubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjE2OS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzEyMDQubTN1OA.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzExMTgubTN1OA.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzExNzEubTN1OA.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131104.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72166.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72167.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90680.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72169.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131118.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131204.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131171.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131104.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72166.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72167.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90680.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72169.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131118.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131204.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131171.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131104.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72166.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72167.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90680.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72169.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131118.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131204.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131171.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/107982.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/43933.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8961.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8962.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35238.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35239.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Multishow",
        logo = "https://img.faz-o-eli.online/multishow.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/multishow/multishow.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzExOTMubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/multishow.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/x7aaupxajb/out/v1/49d602c6294147a18d798ce6abbb6957/cenc.mpd",
                referer = "https://www.primevideo.com/",
                quality = "FHD · 1920x1080",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "5a93ff790eca6f86ad6adb53929fe1c4",
                key = "6664fa23d82cbfeaf5cdb2d662bec3b3",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518580.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518581.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518582.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzExMzgubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjE4Mi5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzExMzQubTN1OA.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131138.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131193.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131107.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72182.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131134.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131138.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131193.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131107.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72182.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131134.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131138.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131193.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131107.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72182.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131134.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13540.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8974.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8975.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Premiere 2",
        logo = "https://img.faz-o-eli.online/premiere.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/premiere2/premiere2.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjIzOC5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/premiere2.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/oy6rp0jwmf/out/v1/580ecf12bad24979baf8dd993dce053e/cenc.mpd",
                referer = "https://www.primevideo.com/",
                quality = "Qualidade não informada",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "9dc40460c93087aea84d6315f08ecb64",
                key = "f69c8d4624fddff4ca89bd0b31bdc4a7",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460212.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460213.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0ODIubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjIzOS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI0MC5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103482.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72237.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72238.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72239.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72240.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103482.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72237.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72238.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72239.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72240.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103482.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72237.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72238.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72239.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72240.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/81582.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9022.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13576.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/69097.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/69028.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/69029.ts",
                quality = "HD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/premiere/premiere.m3u8",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Premiere 3",
        logo = "https://img.faz-o-eli.online/premiere.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/premiere3/premiere3.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI0MS5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/premiere3.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/6onrfniyry/out/v1/f23069c61dbf4e00890a40b705a84079/cenc.mpd",
                referer = "https://www.primevideo.com/",
                quality = "FHD · 1920x1080",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "d23f7433798a652a7d4f6791d9e1036c",
                key = "4942eebd598b5727c5cc484cc62b52e8",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460215.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460217.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0ODEubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI0My5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI0NC5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103481.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72241.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72242.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72243.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72244.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103481.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72241.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72242.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72243.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72244.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103481.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72241.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72242.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72243.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72244.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/81583.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13577.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/69030.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/69031.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30051.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9026.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Premiere 4",
        logo = "https://img.faz-o-eli.online/premiere.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/premiere4/premiere4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI0NS5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/premiere4.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/tirjor64kh/out/v1/fd2ed9916d994f09a3bd62b64141b9cb/cenc.mpd",
                referer = "https://www.primevideo.com/",
                quality = "FHD · 1920x1080",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "e23365c2ad97870c871712b73f0d6195",
                key = "58709c714320bb862dbd07270df81c94",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460218.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460219.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0ODAubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI0Ny5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI0OC5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103480.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72245.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72246.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72247.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72248.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103480.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72245.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72246.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72247.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72248.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103480.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72245.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72246.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72247.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72248.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/81616.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108295.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108296.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108297.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108298.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108300.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Premiere 5",
        logo = "https://img.faz-o-eli.online/premiere.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/premiere5/premiere5.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI0OS5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/premiere5.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/1obktrybht/out/v1/08265453c8f64d9fbeb3cf43764403a8/cenc.mpd",
                referer = "https://www.primevideo.com/",
                quality = "Qualidade não informada",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "332f62eb3cae824e98a4124da29a7d31",
                key = "d1698cda3d040f9051125a61745b596b",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460221.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460222.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460223.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0NzkubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI1MS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI1Mi5tM3U4.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103479.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72249.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72250.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72251.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72252.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103479.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72249.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72250.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72251.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72252.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103479.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72249.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72250.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72251.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72252.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/82751.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108301.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108302.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108305.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108303.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108304.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108306.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Premiere 6",
        logo = "https://mondrian.claro.com.br/channels/inverse/premiere.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/premiere6/premiere6.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI1My5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/0bmtb2fxcj/out/v1/b5f50c3632264d32bf857652f631b0fb/cenc.mpd",
                referer = "https://www.primevideo.com/",
                quality = "Qualidade não informada",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "8bf16a4ba05bcc97e6259297e50be63d",
                key = "8dd97d486cbdd15cc47ea8c41b264ebb",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460224.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460225.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460226.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0NzgubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI1NS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI1Ni5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103478.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72253.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72254.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72255.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72256.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103478.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72253.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72254.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72255.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72256.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103478.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72253.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72254.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72255.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72256.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/82753.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108307.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108308.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108309.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108310.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Premiere 7",
        logo = "https://img.faz-o-eli.online/premiere.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/premiere7/premiere7.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI1Ny5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/premiere7.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/joij38hkop/out/v1/c920c9b42af24588a253530ed2cbd6eb/cenc.mpd",
                referer = "https://www.primevideo.com/",
                quality = "FHD · 1920x1080",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "238f5cf32228c1877f8b939f5f9d7bd8",
                key = "47571c93e4335b3d1590a5ae3f5c48ef",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460227.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460228.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460229.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0NzcubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI1OS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI2MC5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103477.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72257.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72258.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72259.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72260.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103477.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72257.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72258.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72259.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72260.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103477.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72257.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72258.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72259.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72260.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108313.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108314.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108317.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108315.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108316.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Premiere 8",
        logo = "https://mondrian.claro.com.br/channels/inverse/premiere.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/premiere8/premiere8.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xODAxNTgubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/2s8gkqz2id/out/v1/41da2546a9a34238b8615d3beb4ee600/cenc.mpd",
                referer = "https://www.primevideo.com/",
                quality = "Qualidade não informada",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "19aec31e45751958c1d963435d725f33",
                key = "fb24357e80520fd600dd43c1d8ce8a7a",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460230.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460231.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460232.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xODAxNjIubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xODAxNTkubTN1OA.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xODAxNjAubTN1OA.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/180162.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/180158.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/180161.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/180159.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/180160.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/180162.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/180158.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/180161.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/180159.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/180160.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/180162.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/180158.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/180161.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/180159.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/180160.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13582.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9036.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Premiere Clubes",
        logo = "https://img.faz-o-eli.online/premiere.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/premiere.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/nelfyucw9a/out/v1/6ffb2c365ad14f88b154591beb43d1f6/cenc.mpd",
                referer = "https://www.primevideo.com/",
                quality = "Qualidade não informada",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "56b79c1782b30e6b6fc973b0e8fd4104",
                key = "fa38aaa865a57eda7c77444697ba8ed3",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324401.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460209.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460210.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460211.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72264.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72262.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72263.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72265.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72266.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72264.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72262.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72263.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72265.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72266.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72264.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72262.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72263.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72265.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72266.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/125438.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9037.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/54902.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/94194.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13583.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "SBT",
        logo = "https://mondrian.claro.com.br/channels/inverse/sbt.png",
        sources = listOf(
            Source(
                url = "https://6836041ea1117.streamlock.net/cverde/cverde/playlist.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://media.cdntvms.com.br/sbt_sat/index.m3u8",
                quality = "SD · 1024x576",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324290.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324292.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324390.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459578.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459579.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459580.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9082.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/81580.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/81610.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/26722.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9084.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30120.ts",
                quality = "HD",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "SBT News",
        logo = "https://mondrian.claro.com.br/channels/inverse/sbt-news.png",
        sources = listOf(
            Source(
                url = "https://sbtnews.maissbt.com/index.m3u8",
                referer = "https://mais.sbt.com.br/",
                quality = "HD · 1280x720",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
            ),
            Source(
                url = "https://dai.google.com/linear/hls/event/1XSOdtQ0SH2G8OEmEfGgjQ/master.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459566.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459567.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459568.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1129618.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1129619.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Notícias",
    ),
    Channel(
        name = "Sony Channel",
        logo = "https://img.faz-o-eli.online/sonychannel.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/sonychannel/sonychannel.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/sonychannel.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://170.83.16.50/SONY_CHANNEL/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324457.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324459.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103517.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71824.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71825.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90713.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71826.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90712.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71827.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90711.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103517.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71824.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71825.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90713.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71826.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90712.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71827.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90711.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103517.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71824.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71825.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90713.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71826.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90712.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71827.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90711.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108180.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13584.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9098.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9099.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35242.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35243.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "SporTV",
        logo = "https://img.faz-o-eli.online/sportv.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/sportv/sportv.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjMyOS5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/sportv.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324391.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460200.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460201.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460202.m3u8",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzExODgubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjMzMi5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzExMzIubTN1OA.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131188.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72329.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72330.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72332.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131132.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131188.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72329.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72330.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72332.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131132.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131188.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72329.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72330.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72332.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131132.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/81591.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/69096.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9107.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9111.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30126.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30127.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "SporTV 2",
        logo = "https://img.faz-o-eli.online/sportv2.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/sportv2/sportv2.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjMyMS5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/sportv2.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/dsa3hwuhd1/out/v1/631b48c8d9ea437e8309d1a4b55acef5/cenc.mpd",
                referer = "https://www.primevideo.com/",
                quality = "Qualidade não informada",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "3de028eafb3b2caffec03be1c1c818b3",
                key = "8fbdd8a9ae6748696bb13e547bb093fc",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460203.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460204.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460205.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0NzIubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzExNzYubTN1OA.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjMyNC5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103472.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72321.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131147.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131176.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72324.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103472.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72321.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131147.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131176.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72324.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103472.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72321.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131147.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131176.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72324.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/81592.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13586.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9103.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9104.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30122.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30123.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "SporTV 3",
        logo = "https://img.faz-o-eli.online/sportv3.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/sportv3/sportv3.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjMyNS5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/sportv3.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/6otiglnptp/out/v1/add7499679b0422cb6791f7701f95ecc/cenc.mpd",
                referer = "https://www.primevideo.com/",
                quality = "Qualidade não informada",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "902e5ec0e3d05e665daa32fc23f4f59e",
                key = "7b2322a273843921a43e2c61dac7cae3",
            ),
            Source(
                url = "http://170.83.49.66:8083/SPORTV3HD/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460206.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460207.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460208.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0NzEubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzExMTMubTN1OA.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzExMDgubTN1OA.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72325.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72326.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131113.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131108.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103471.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72325.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72326.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131113.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131108.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103471.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72325.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72326.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131113.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131108.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103471.ts",
                quality = "4K",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/81593.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9106.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9105.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30124.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30125.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Canal OFF",
        logo = "https://img.faz-o-eli.online/off.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/off/off.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/off.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518583.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518584.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518585.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90240.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72209.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72210.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72211.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72212.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90240.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72209.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72210.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72211.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72212.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90240.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72209.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72210.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72211.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72212.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8996.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8997.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8998.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Telecine Action",
        logo = "https://img.faz-o-eli.online/telecineaction.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/telecineaction/telecineaction.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM0Ni5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY2OC5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/telecineaction.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324314.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324316.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324318.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324393.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDIzOS5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM0OC5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM0OS5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY2Ny5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY2Ni5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90239.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72346.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72347.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90668.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72348.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90667.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72349.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90666.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90239.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72346.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72347.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90668.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72348.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90667.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72349.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90666.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90239.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72346.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72347.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90668.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72348.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90667.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72349.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90666.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/94375.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/24533.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9126.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9127.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30134.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30135.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Telecine Pipoca",
        logo = "https://img.faz-o-eli.online/telecinepipoca.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/telecinepipoca/telecinepipoca.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/telecinepipoca.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324332.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324334.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324336.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324396.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90238.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72358.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72359.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90659.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72360.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90658.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72361.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90657.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90238.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72358.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72359.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90659.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72360.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90658.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72361.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90657.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90238.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72358.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72359.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90659.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72360.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90658.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72361.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90657.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/94378.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/24536.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9131.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9132.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30140.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30141.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Telecine Premium",
        logo = "https://img.faz-o-eli.online/telecinepremium.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/telecinepremium/telecinepremium.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM2My5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY1Ni5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/telecinepremium.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324338.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324340.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324342.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324397.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDIzNy5tM3U4.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM2NC5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM2NS5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY1NS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY1NC5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90237.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72362.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72363.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90656.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72364.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90655.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72365.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90654.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90237.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72362.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72363.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90656.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72364.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90655.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72365.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90654.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90237.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72362.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72363.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90656.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72364.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90655.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72365.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90654.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/94379.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/24537.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9133.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9134.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30142.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "TV Brasil",
        logo = "https://mondrian.claro.com.br/channels/inverse/tv-brasil.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM5OS5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://tvbrasil-stream.ebc.com.br/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://45.162.64.114/TV_BRASIL/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://45.177.114.115/TV_BRASIL/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459583.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0NjMubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjQwMS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103463.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72399.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72400.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72401.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103463.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72399.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72400.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72401.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103463.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72399.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72400.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72401.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/43030.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/33013.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Universal Premiere",
        logo = "https://mondrian.claro.com.br/channels/inverse/universal-premiere.png",
        sources = listOf(
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/khnyllds8v/out/v1/1cf61b7a057e4ffdb31f6d82fb24c679/cenc.mpd",
                referer = "https://www.primevideo.com/",
                quality = "Qualidade não informada",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "3531c1c49b8e63c0b94a8061154e0c58",
                key = "90e91e6549d2061b793177c70d35e177",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Universal Reality",
        logo = "https://mondrian.claro.com.br/channels/inverse/universal-reality.png",
        sources = listOf(
            Source(
                url = "https://qw.live.pv-cdn.net/OTTB/gru-nitro/live/clients/dash/enc/5ppjwg1ekb/out/v1/393342b545834c218745c3dd33661013/cenc.mpd",
                referer = "https://www.primevideo.com/",
                quality = "Qualidade não informada",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "4b62d6103d99d62f8daca463d1c15430",
                key = "2fd0079af0bf85289859fff7b8a4e30f",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518601.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518602.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518603.m3u8",
                quality = "SD · 960x540",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Warner",
        logo = "https://img.faz-o-eli.online/warnerchannel.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/warnerchannel/warnerchannel.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/warnerchannel.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324511.m3u8",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324513.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90236.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72431.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72432.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90650.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72433.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90649.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72434.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90648.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90236.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72431.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72432.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90650.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72433.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90649.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72434.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90648.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90236.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72431.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72432.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90650.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72433.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90649.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72434.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90648.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/107981.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/21684.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9219.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9220.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35258.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35259.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "IMPD",
        logo = "https://d5pgibznjcs0s.cloudfront.net/46d92cf6-4807-45c6-9bfd-b38f80288c86/en/images/logo_full.png",
        sources = listOf(
            Source(
                url = "https://68882bdaf156a.streamlock.net/impd/ngrp:impd_all/chunklist_w1464410885_b2691072.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://igrejamundial.nuvemplay.live/hls/stream.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Globo SP",
        logo = "https://img.faz-o-eli.online/globosp.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/globosp/globosp.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjA0MC5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/globosp.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324386.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzEyMTIubTN1OA.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjA0Mi5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72040.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131215.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131212.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72042.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72040.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131215.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131212.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72042.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72040.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131215.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131212.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72042.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1168916.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1168917.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1168918.ts",
                quality = "HD",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Cartoonito",
        logo = "https://img.faz-o-eli.online/cartoonito.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/cartoonito/cartoonito.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgxMi5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/cartoonito.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324378.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882631.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882632.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882633.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY0NC5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgxNC5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgxNS5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90644.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71812.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71813.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71814.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71815.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90644.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71812.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71813.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71814.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71815.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90644.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71812.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71813.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71814.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71815.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13503.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8687.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8688.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/38013.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/106287.ts",
                quality = "HD",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "Cinemax",
        logo = "https://img.faz-o-eli.online/cinemax.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/cinemax/cinemax.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgzMy5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/cinemax.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324445.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324447.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY0My5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgzNS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgzNi5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90643.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71833.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71834.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71835.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71836.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90643.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71833.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71834.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71835.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71836.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90643.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71833.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71834.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71835.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71836.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/107978.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13505.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8708.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8709.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35232.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35233.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Discovery Channel",
        logo = "https://img.faz-o-eli.online/discoverychannel.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg2Mi5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/discoverychannel.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297936.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297937.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297938.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324381.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg2NC5tM3U4.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg2NS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg2Ni5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71864.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71862.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71863.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71865.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71866.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71864.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71862.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71863.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71865.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71866.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71864.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71862.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71863.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71865.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71866.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/91396.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13508.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/43928.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8735.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/131176.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8736.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30148.ts",
                quality = "HD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/discoverychannel/discoverychannel.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Documentários",
    ),
    Channel(
        name = "Discovery Kids",
        logo = "https://img.faz-o-eli.online/discoverykids.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg3MS5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/discoverykids.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297946.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDI1MC5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg3My5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg3NC5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90250.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71871.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71872.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71873.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71874.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90250.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71871.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71872.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71873.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71874.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90250.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71871.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71872.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71873.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71874.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/125440.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8742.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8743.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/38017.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/38018.ts",
                quality = "HD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/discoverykids/discoverykids.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "Discovery Theater",
        logo = "https://img.faz-o-eli.online/discoverytheater.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg3OS5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/discoverytheather.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297954.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297956.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM1MTAubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg4MS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg4Mi5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103510.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71879.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71880.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71881.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71882.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103510.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71879.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71880.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71881.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71882.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103510.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71879.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71880.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71881.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71882.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/91401.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13510.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8748.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8751.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/93904.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30153.ts",
                quality = "HD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/discoverytheather/discoverytheather.m3u8",
            ),
        ),
        categoria = "Documentários",
    ),
    Channel(
        name = "ESPN",
        logo = "https://img.faz-o-eli.online/espn.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/espn/espn.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkxMS5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/espn.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://181.78.197.59:8000/play/a07z/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324384.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460182.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460183.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460184.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY0MC5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkxMy5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkxNC5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90640.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71911.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71912.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71913.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71914.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90640.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71911.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71912.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71913.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71914.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90640.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71911.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71912.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71913.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71914.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/598912.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/598913.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/598914.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/598916.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/598917.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/598918.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "ESPN 2",
        logo = "https://img.faz-o-eli.online/espn2.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/espn2/espn2.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkxOS5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/espn2.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460185.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460186.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460187.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkyMS5tM3U4.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkyMi5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkyMy5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71921.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71919.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71920.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71922.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71923.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71921.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71919.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71920.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71922.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71923.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71921.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71919.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71920.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71922.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71923.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/81587.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13519.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8786.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/131131.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30072.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30071.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "ESPN 4",
        logo = "https://img.faz-o-eli.online/espn4.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/espn4/espn4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTk1OC5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/espn4.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://181.78.197.59:8000/play/a07n/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460191.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460192.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460193.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTk1OS5tM3U4.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTk2MC5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTk2MS5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71959.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71957.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71958.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71960.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71961.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71959.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71957.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71958.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71960.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71961.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71959.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71957.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71958.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71960.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71961.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/81578.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/21647.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8828.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/20947.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30083.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30082.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
)

private fun build_CATALOG_PART_1(): List<Channel> = listOf(
    Channel(
        name = "ESPN 5",
        logo = "https://img.faz-o-eli.online/espn5.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/espn5/espn5.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTk1Mi5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/espn5.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460194.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460195.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460196.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTk1NC5tM3U4.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTk1My5tM3U4.m3u8",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTk1Ni5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71954.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71952.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71953.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71955.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71956.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71954.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71952.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71953.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71955.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71956.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71954.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71952.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71953.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71955.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71956.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/81594.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/20946.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8826.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8827.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30081.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30084.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "ESPN 6",
        logo = "https://img.faz-o-eli.online/espn6.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/espn6/espn6.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkxNS5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/espn6.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460197.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460198.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460199.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM1MDUubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkxNy5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkxOC5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103505.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71915.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71916.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71917.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71918.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103505.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71915.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71916.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71917.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71918.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103505.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71915.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71916.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71917.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71918.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/81590.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/131138.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8794.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30077.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "HBO",
        logo = "https://img.faz-o-eli.online/hbo.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/hbo/hbo.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjExOC5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY5OC5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/hbo.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/514107.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/514108.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDI0Ny5tM3U4.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjEyMC5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjEzMy5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY5Ny5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY5Ni5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90247.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72118.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72119.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90698.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72120.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90697.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72133.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90696.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90247.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72118.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72119.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90698.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72120.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90697.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72133.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90696.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90247.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72118.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72119.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90698.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72120.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90697.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72133.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90696.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/94381.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8902.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8903.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/31862.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/31863.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "HBO2",
        logo = "https://img.faz-o-eli.online/hbo2.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/hbo2/hbo2.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjExMC5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/hbo2.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/514110.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/514111.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDI0Ni5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjExMi5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjExMy5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90246.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72110.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72111.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72112.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72113.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90246.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72110.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72111.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72112.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72113.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90246.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72110.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72111.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72112.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72113.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/94382.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13529.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8897.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8898.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/31859.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/93899.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "HBO Family",
        logo = "https://img.faz-o-eli.online/hbofamily.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/hbofamily/hbofamily.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjExNS5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/hbofamily.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/514113.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/514114.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDYzNy5tM3U4.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjExNi5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjExNy5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90637.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72114.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72115.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72116.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72117.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90637.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72114.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72115.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72116.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72117.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90637.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72114.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72115.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72116.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72117.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/94383.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13530.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8901.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8900.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/31860.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/31861.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "HBO Mundi",
        logo = "https://img.faz-o-eli.online/hbomundi.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/hbomundi/hbomundi.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjEyMS5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY5NS5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/hbomundi.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/514116.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/514117.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0OTYubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjEyMy5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjEyNC5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY5NC5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY5My5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103496.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72121.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72122.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90695.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72123.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90694.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72124.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90693.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103496.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72121.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72122.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90695.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72123.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90694.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72124.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90693.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103496.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72121.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72122.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90695.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72123.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90694.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72124.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90693.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/94384.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13531.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8904.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8905.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/31864.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/93900.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "HBO Plus",
        logo = "https://img.faz-o-eli.online/hboplus.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/hboplus/hboplus.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjEyNS5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY5Mi5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/hboplus.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/514119.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/514120.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDYzNS5tM3U4.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjEyNy5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjEyOC5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY5MS5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY5MC5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90635.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72125.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72126.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90692.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72127.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90691.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72128.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90690.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90635.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72125.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72126.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90692.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72127.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90691.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72128.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90690.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90635.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72125.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72126.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90692.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72127.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90691.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72128.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90690.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/94385.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/24592.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/11771.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/31865.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/31866.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "HBO Pop",
        logo = "https://img.faz-o-eli.online/hbopop.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/hbopop/hbopop.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjEyOS5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY4OS5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/hbopop.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/514122.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/514123.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0OTUubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjEzMS5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjEzMi5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY4OC5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY4Ny5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103495.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72129.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72130.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90689.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72131.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90688.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72132.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90687.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103495.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72129.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72130.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90689.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72131.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90688.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72132.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90687.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103495.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72129.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72130.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90689.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72131.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90688.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72132.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90687.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/94386.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13533.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/24593.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8911.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/31867.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/93901.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "HBO Signature",
        logo = "https://mondrian.claro.com.br/channels/inverse/hbo-signature.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjEzNC5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY4Ni5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/514125.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/514126.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0OTQubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjEzNi5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjEzNy5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY4NS5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY4NC5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103494.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72134.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72135.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90686.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72136.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90685.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72137.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90684.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103494.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72134.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72135.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90686.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72136.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90685.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72137.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90684.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103494.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72134.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72135.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90686.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72136.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90685.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72137.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90684.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/94387.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13534.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8912.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8914.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/31868.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/31869.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "HBO Xtreme",
        logo = "https://mondrian.claro.com.br/channels/inverse/hbo-xtreme.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjEzOC5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY4My5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/514128.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/514129.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDYzNi5tM3U4.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjE0MC5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjE0MS5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY4Mi5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY4MS5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90636.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72138.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72139.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90683.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72140.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90682.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72141.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90681.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90636.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72138.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72139.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90683.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72140.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90682.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72141.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90681.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90636.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72138.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72139.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90683.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72140.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90682.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72141.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90681.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13536.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13535.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8916.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/31870.ts",
                quality = "HD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/hboxtreme/hboxtreme.m3u8",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Record",
        logo = "https://mondrian.claro.com.br/channels/inverse/record-tv.png",
        sources = listOf(
            Source(
                url = "http://79.127.238.228:14057",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://media.cdntvms.com.br/record_nacional_sat/index.m3u8",
                quality = "SD · 1024x576",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35842.ts",
                quality = "HD",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Space",
        logo = "https://img.faz-o-eli.online/space.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/space/space.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjMxOC5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY3Ny5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/space.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324463.m3u8",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324465.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDYzMy5tM3U4.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjMxOS5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjMyMC5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY3Ni5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY3NS5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90633.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72317.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72318.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90677.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72319.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90676.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72320.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90675.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90633.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72317.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72318.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90677.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72319.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90676.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72320.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90675.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90633.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72317.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72318.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90677.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72319.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90676.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72320.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90675.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108181.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13585.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9101.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9102.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35244.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35245.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "TNT",
        logo = "https://img.faz-o-eli.online/tnt.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/tnt/tnt.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM3NS5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY3NC5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/tnt.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324481.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324483.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM3Ny5tM3U4.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM3OC5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM3OS5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY3My5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY3Mi5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72377.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72375.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72376.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90674.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72378.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90673.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72379.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90672.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72377.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72375.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72376.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90674.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72378.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90673.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72379.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90672.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72377.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72375.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72376.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90674.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72378.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90673.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72379.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90672.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/594019.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/594020.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/594021.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/594023.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/594024.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/594025.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "TNT Séries",
        logo = "https://img.faz-o-eli.online/tntseries.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/tntseries/tntseries.m3u8",
                quality = "SD · 1024x576",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM4MC5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY3MS5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/tntseries.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324493.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324495.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM4Mi5tM3U4.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM4My5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM4NC5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY3MC5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY2OS5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72382.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72380.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72381.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90671.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72383.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90670.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72384.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90669.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72382.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72380.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72381.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90671.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72383.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90670.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72384.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90669.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72382.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72380.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72381.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90671.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72383.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90670.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72384.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90669.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108184.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/24156.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9144.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9145.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35254.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35255.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Universal TV",
        logo = "https://img.faz-o-eli.online/universaltv.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/universaltv/universaltv.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjQxOS5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/universaltv.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://getcdn.clarocdn.com.br/Content/Channel/SPOUNVHD/dsc3/manifest.mpd",
                referer = "https://www.clarotvmais.com.br/",
                quality = "Qualidade não informada",
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
                keyId = "0110de67e8a43229a9afee5fbb1bf34c",
                key = "14d17ea503859feb50f395a19491a2ea",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324399.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324499.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324500.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDYzMi5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjQyMS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjQyMi5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90632.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72419.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72420.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72421.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72422.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90632.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72419.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72420.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72421.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72422.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90632.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72419.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72420.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72421.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72422.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108185.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/21683.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/21682.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9212.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35256.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35257.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "AMC Séries",
        logo = "https://mondrian.claro.com.br/channels/inverse/amc.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324530.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324531.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Discovery Turbo",
        logo = "https://img.faz-o-eli.online/discoveryturbo.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg4My5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/discoveryturbo.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297942.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297943.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297944.m3u8",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324383.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518556.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518557.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518558.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY0MS5tM3U4.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg4NS5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg4Ni5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90641.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71883.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71884.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71885.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71886.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90641.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71883.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71884.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71885.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71886.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90641.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71883.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71884.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71885.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71886.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/91399.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13512.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8755.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8756.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30156.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30155.ts",
                quality = "HD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/discoveryturbo/discoveryturbo.m3u8",
            ),
        ),
        categoria = "Documentários",
    ),
    Channel(
        name = "USA Network",
        logo = "https://mondrian.claro.com.br/channels/inverse/usa.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjMzOC5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324505.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324507.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM0MC5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM0MS5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72338.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72339.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72340.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72341.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72338.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72339.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72340.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72341.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72338.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72339.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72340.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72341.ts",
                quality = "SD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Telecine Fun",
        logo = "https://img.faz-o-eli.online/telecinefun.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/telecinefun/telecinefun.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM1NC5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY2Mi5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/telecinefun.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324326.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324328.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324330.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324395.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0NTUubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM1Ni5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM1Ny5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY2MS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY2MC5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103455.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72354.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72355.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90662.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72356.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90661.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72357.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90660.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103455.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72354.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72355.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90662.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72356.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90661.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72357.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90660.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103455.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72354.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72355.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90662.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72356.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90661.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72357.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90660.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/94377.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/24535.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9129.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9130.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30138.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30139.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Telecine Cult",
        logo = "https://img.faz-o-eli.online/telecinecult.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/telecinecult/telecinecult.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM1MC5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY2NS5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/telecinecult.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324320.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324322.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324324.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324394.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0NTYubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM1Mi5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM1My5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY2NC5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY2My5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103456.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72350.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72351.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90665.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72352.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90664.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72353.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90663.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103456.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72350.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72351.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90665.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72352.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90664.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72353.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90663.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103456.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72350.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72351.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90665.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72352.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90664.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72353.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90663.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/94376.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/24534.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/11770.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9128.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30136.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30137.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Telecine Touch",
        logo = "https://img.faz-o-eli.online/telecinetouch.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/telecinetouch/telecinetouch.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM2Ny5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY1My5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/telecinetouch.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324344.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324346.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324348.m3u8",
                quality = "SD · 768x576",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324398.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0NTQubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM2OC5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM2OS5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY1Mi5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY1MS5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103454.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72366.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72367.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90653.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72368.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90652.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72369.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90651.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103454.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72366.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72367.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90653.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72368.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90652.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72369.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90651.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103454.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72366.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72367.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90653.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72368.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90652.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72369.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90651.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/94380.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/24538.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9135.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9136.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30144.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30145.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "SONY Movies",
        logo = "https://www.tvlogo.org/brazil/sony-movies-br.png",
        sources = listOf(
            Source(
                url = "http://45.162.64.114/SONY_MOVIES/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://45.177.114.115/SONY_MOVIES/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://168.197.104.22/SONY_MOVIES/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324518.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Globo",
        logo = "https://mondrian.claro.com.br/channels/inverse/globo.png",
        sources = listOf(
            Source(
                url = "https://media2.cdntvms.com.br/tv_morena_dorados/index.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Tooncast",
        logo = "https://mondrian.claro.com.br/channels/inverse/tooncast.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882638.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882639.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882640.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/125444.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/125443.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9150.ts",
                quality = "HD",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "TLC",
        logo = "https://img.faz-o-eli.online/tlc.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM3MS5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/tlc.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://79.127.238.228:14433",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518586.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518587.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518588.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0NjUubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM3My5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM3NC5tM3U4.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103465.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72371.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72372.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72373.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72374.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103465.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72371.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72372.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72373.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72374.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103465.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72371.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72372.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72373.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72374.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/91400.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13511.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8753.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8754.ts",
                quality = "HD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/tlc/tlc.m3u8",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Arte 1",
        logo = "https://mondrian.claro.com.br/channels/inverse/arte1.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc3Ny5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://45.162.64.114/ARTE1/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://45.177.114.115/ARTE1/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://168.197.104.22/ARTE1/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM1MzEubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc3OS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc4MC5tM3U4.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103531.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71777.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71778.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71779.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71780.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103531.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71777.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71778.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71779.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71780.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103531.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71777.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71778.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71779.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71780.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/67511.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/67512.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "TNT Novelas",
        logo = "https://img.faz-o-eli.online/tntnovelas.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/tntnovelas/tntnovelas.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM0Mi5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/tntnovelas.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324485.m3u8",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324487.m3u8",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0NjYubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM0NC5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM0NS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103466.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72342.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72343.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72344.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72345.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103466.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72342.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72343.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72344.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72345.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103466.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72342.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72343.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72344.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72345.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/275102.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/275101.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/275103.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/275104.ts",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Discovery Science",
        logo = "https://img.faz-o-eli.online/discoveryscience.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg3NS5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/discoveryscience.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297951.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297952.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM1MTEubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg3Ny5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg3OC5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103511.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71875.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71876.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71877.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71878.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103511.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71875.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71876.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71877.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71878.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103511.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71875.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71876.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71877.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71878.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/91402.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13509.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8745.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8746.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/93903.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30151.ts",
                quality = "HD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/discoveryscience/discoveryscience.m3u8",
            ),
        ),
        categoria = "Documentários",
    ),
    Channel(
        name = "AXN",
        logo = "https://img.faz-o-eli.online/axn.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/axn/axn.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc4MS5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDcxNi5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/axn.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://170.83.16.50/AXN/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://170.83.49.66:8083/AXNHD/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324375.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324433.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324435.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDY0NS5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc4My5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc4NC5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDcxNS5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDcxNC5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90645.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71781.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71782.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90716.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71783.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90715.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71784.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90714.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90645.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71781.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71782.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90716.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71783.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90715.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71784.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90714.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90645.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71781.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71782.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90716.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71783.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90715.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71784.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90714.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/81600.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13500.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/23691.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/23692.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35228.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35229.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "TCM",
        logo = "https://img.faz-o-eli.online/tcm.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/tcm.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324475.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324477.m3u8",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9124.ts",
                quality = "HD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/tcm/tcm.m3u8",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Band News",
        logo = "https://img.faz-o-eli.online/bandnews.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/bandnews/bandnews.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc4OS5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/bandnews.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://45.162.64.114/BAND_NEWS/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://45.177.114.115/BAND_NEWS/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://168.197.104.22/BAND_NEWS/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459551.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459552.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459553.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM1MjYubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc5MS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc5Mi5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103526.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71789.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71790.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71791.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71792.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103526.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71789.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71790.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71791.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71792.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103526.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71789.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71790.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71791.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71792.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/81596.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108367.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/11769.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30171.ts",
                quality = "HD",
            ),
        ),
        categoria = "Notícias",
    ),
    Channel(
        name = "BAND SPORTS HD",
        logo = "https://img.faz-o-eli.online/bandsports.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/bandsports/bandsports.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/bandsports.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://45.162.64.114/BAND_SPORTS/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://170.83.16.50/BAND_SPORTS/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://170.83.49.66:8083/BANDSPORTSHD/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324377.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460152.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460153.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460154.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103525.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71801.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71802.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71803.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71804.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103525.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71801.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71802.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71803.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71804.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103525.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71801.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71802.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71803.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71804.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/81585.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8661.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8662.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30064.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/26724.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "RedeTV!",
        logo = "https://www.tvlogo.org/brazil/rede-tv-br.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI5Ny5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://45.162.64.114/REDE_TV/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://168.197.104.22/REDE_TV/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://170.83.16.50/REDE_TV/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459575.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459576.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459577.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xNzg1MzIubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI5OS5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjMwMC5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72297.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72298.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72299.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72300.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/178532.ts",
                quality = "4K",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "TV Cultura",
        logo = "https://img.faz-o-eli.online/cultura.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/cultura/cultura.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/cultura.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://v-us-01.wisestream.io/memfs/e8740862-7a1f-45f3-acb2-4f357e144059.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://jmp2.uk/plu-62e010e6cd663f0007e57dc8.m3u8",
                quality = "SD · 1216x684",
            ),
            Source(
                url = "https://player-tvcultura.stream.uol.com.br/live/tvcultura.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459569.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459570.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459571.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103513.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71851.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71852.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71853.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71854.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103513.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71851.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71852.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71853.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71854.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103513.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71851.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71852.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71853.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71854.ts",
                quality = "SD",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Terra Viva",
        logo = "https://www.tvlogo.org/brazil/terraviva-br.png",
        sources = listOf(
            Source(
                url = "https://9ada494d.wurl.com/master/f36d25e7e52f1ba8d7e56eb859c636563214f541/TEctYnJfVXBseW5rLU5ld2NvX0hMUw/playlist.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://45.162.64.114/TERRAVIVA/index.m3u8",
                quality = "HD · 1080x720",
            ),
            Source(
                url = "http://45.177.114.115/TERRAVIVA/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459589.m3u8",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Woohoo",
        logo = "https://mondrian.claro.com.br/channels/inverse/woohoo.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjQzNS5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://45.162.64.114/WOOHOO/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://168.197.104.22/WOOHOO/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://170.83.16.50/WOOHOO/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0NDkubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjQzNy5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjQzOC5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103449.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72435.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72436.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72437.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72438.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103449.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72435.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72436.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72437.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72438.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103449.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72435.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72436.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72437.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72438.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/125448.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9224.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "ESPN 3",
        logo = "https://img.faz-o-eli.online/espn3.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/espn3/espn3.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkwNy5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/espn3.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://181.78.197.59:8000/play/a081/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460188.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460189.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460190.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM1MDQubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkwOS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkxMC5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103504.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71907.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71908.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71909.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71910.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103504.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71907.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71908.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71909.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71910.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103504.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71907.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71908.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71909.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71910.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/81588.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13520.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8788.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/131132.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8787.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30073.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30074.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "TV Gazeta",
        logo = "https://mondrian.claro.com.br/channels/inverse/tv-gazeta.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDQ5MTkubTN1OA.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "http://45.162.64.114/GAZETA/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://45.162.64.114/TV_GAZETA/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://168.197.104.22/GAZETA/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459587.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104919.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104919.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104919.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/67852.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Record News",
        logo = "https://mondrian.claro.com.br/channels/inverse/recordnews.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xNzg1MjIubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://rnw-rn.otteravision.com/rnw/rn/rnw_rn.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://45.162.64.114/RECORD_NEWS/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://45.177.114.115/RECORD_NEWS/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459563.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459564.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459565.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xNzg1MjMubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI3Mi5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI3My5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/178523.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72271.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/178522.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72272.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72273.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/178523.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72271.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/178522.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72272.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72273.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/178523.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72271.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/178522.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72272.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72273.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9058.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9057.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30170.ts",
                quality = "HD",
            ),
        ),
        categoria = "Notícias",
    ),
    Channel(
        name = "Canção Nova",
        logo = "https://img.faz-o-eli.online/cancaonova.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/cancaonova/cancaonova.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/cancaonova.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://46.151.196.223:14225",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://5c65286fc6ace.streamlock.net/cancaonova/CancaoNova.stream_720p/playlist.m3u8",
                quality = "HD",
            ),
            Source(
                url = "http://45.162.64.114/CANCAO_NOVA/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://168.197.104.22/CANCAO_NOVA/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459542.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72404.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72404.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72404.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/117823.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/117824.ts",
                quality = "HD",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "TV Aparecida",
        logo = "https://mondrian.claro.com.br/channels/inverse/tv-aparecida.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM5NC5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://45.162.64.114/TV_APARECIDA/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://45.177.114.115/TV_APARECIDA_HD/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://168.197.104.22/TV_APARECIDA/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459548.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0NjQubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM5Ni5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM5Ny5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103464.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72394.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72395.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72396.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72397.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103464.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72394.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72395.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72396.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72397.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103464.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72394.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72395.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72396.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72397.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/66680.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30147.ts",
                quality = "HD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/aparecida/aparecida.m3u8",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Rede Vida",
        logo = "https://img.faz-o-eli.online/redevida.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/redevida/redevida.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI5My5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/redevida.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://46.151.196.223:14244",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://168.197.104.22/REDE_VIDA/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://186.219.52.187/rede_vida/index.m3u8",
                quality = "SD · 720x576",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459546.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0NzUubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI5NS5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI5Ni5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103475.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72293.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72294.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72295.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72296.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103475.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72293.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72294.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72295.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72296.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103475.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72293.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72294.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72295.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72296.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/69259.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9076.ts",
                quality = "HD",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Discovery ID",
        logo = "https://img.faz-o-eli.online/discoverychannel.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/discoveryid.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297949.m3u8",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297950.m3u8",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90243.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72150.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72151.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72152.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72153.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90243.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72150.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72151.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72152.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72153.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90243.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72150.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72151.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72152.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72153.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/91403.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8932.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8933.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8934.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30172.ts",
                quality = "HD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/discoveryid/discoveryid.m3u8",
            ),
        ),
        categoria = "Documentários",
    ),
    Channel(
        name = "Fish TV",
        logo = "https://mondrian.claro.com.br/channels/inverse/fish-tv.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkyOC5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "http://45.162.64.114/FISH_TV/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://168.197.104.22/FISH_TV/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://170.83.16.50/FISH_TV/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM1MDIubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkzMC5tM3U4.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkzMS5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103502.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71928.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71929.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71930.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71931.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103502.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71928.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71929.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71930.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71931.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103502.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71928.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71929.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71930.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71931.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8807.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8808.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Box Kids TV",
        logo = "https://www.tvlogo.org/brazil/box-kids-tv-br.png",
        sources = listOf(
            Source(
                url = "http://170.83.49.66:8083/BOXKIDSHD/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882644.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "X Sports",
        logo = "https://img.faz-o-eli.online/xsports.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/xsports/xsports.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8zNDA0NDkubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/xsports.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460175.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460262.m3u8",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460263.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8zNDA0NTAubTN1OA.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8zNDA0NTEubTN1OA.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/340449.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/340450.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/340451.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/340449.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/340450.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/340451.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/340449.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/340450.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/340451.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/631630.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/631631.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "N SPORTS",
        logo = "https://mondrian.claro.com.br/channels/inverse/nsports.png",
        sources = listOf(
            Source(
                url = "https://ogc-nsprt-tcl-roku-syndication.otteravision.com/ogc/nsprt/nsprt.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460165.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460166.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Music Box Brazil",
        logo = "https://commons.wikimedia.org/wiki/Special:Redirect/file/MusicBoxBrazil.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjE4NC5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "http://168.197.104.22/MUSIC/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://170.83.49.66:8083/MUSICBOXBRASILHD/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0ODcubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjE4Ni5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjE4Ny5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103487.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72184.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72185.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72186.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72187.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103487.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72184.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72185.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72186.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72187.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103487.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72184.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72185.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72186.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72187.ts",
                quality = "SD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Band SP",
        logo = "https://img.faz-o-eli.online/band.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/bandsp/bandsp.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83OTMxMS5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/bandsp.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324371.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324372.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjU5Ny5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83OTMxMi5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/79311.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104943.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72597.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/79312.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/79311.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104943.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72597.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/79312.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/79311.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104943.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72597.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/79312.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/107926.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/20525.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/43031.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8660.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/31029.ts",
                quality = "HD",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Band RS",
        logo = "https://mondrian.claro.com.br/channels/inverse/band.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324368.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/72575.ts",
                quality = "HD",
            ),
        ),
        categoria = "TV Aberta",
    ),
)

private fun build_CATALOG_PART_2(): List<Channel> = listOf(
    Channel(
        name = "SBT SP",
        logo = "https://img.faz-o-eli.online/sbt.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/sbtsp/sbtsp.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83Nzk2My5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/sbtsp.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324307.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324308.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjU5OC5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83Nzk2NC5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/77963.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104939.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72598.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/77964.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/77963.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104939.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72598.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/77964.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/77963.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104939.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72598.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/77964.ts",
                quality = "SD",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Record SP",
        logo = "https://img.faz-o-eli.online/record.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/recordsp/recordsp.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/recordsp.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324389.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/77654.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72595.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72973.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/96522.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/77654.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72595.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72973.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/96522.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/77654.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72595.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72973.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/96522.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/82755.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9068.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/12888.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/38065.ts",
                quality = "HD",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Canal Rural",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/brazil/canal-rural-br.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xNzg1MTIubTN1OA.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "http://170.83.49.66:8083/CANALRURALHD/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://186.219.52.187/canal_rural/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459582.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xNzg1MTYubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xNzg1MTMubTN1OA.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgyMy5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/178516.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/178512.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/178515.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/178513.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71823.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/178514.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/178516.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/178512.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/178515.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/178513.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71823.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/178514.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/178516.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/178512.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/178515.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/178513.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71823.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/178514.ts",
                quality = "SD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Agro Mais",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/brazil/agro-mais-br.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc2NC5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "http://45.162.64.114/AGROMAIS/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://45.177.114.115/agromais/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://45.177.114.115/AGROMAIS_HD/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459588.m3u8",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM1MzIubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc2Ni5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTc2Ny5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103532.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71764.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71765.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71766.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71767.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103532.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71764.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71765.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71766.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71767.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103532.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71764.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71765.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71766.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71767.ts",
                quality = "SD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "CNT",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/brazil/rede-cnt-br.png",
        sources = listOf(
            Source(
                url = "http://45.162.64.114/CNT/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://168.197.104.22/CNT/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://170.83.49.66:8083/REDECNTHD/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459547.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "TV Pai Eterno",
        logo = "https://img.faz-o-eli.online/tvpaieterno.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/tvpaieterno/tvpaieterno.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/tvpaieterno.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://video09.logicahost.com.br/paieterno/paieterno/playlist.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://45.162.64.114/TV_PAI_ETERNO/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://168.197.104.22/TV_PAI_ETERNO/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459549.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103484.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72213.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72216.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72214.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72215.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103484.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72213.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72216.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72214.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72215.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103484.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72213.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72216.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72214.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72215.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1165872.ts",
                quality = "HD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "PlayTV",
        logo = "https://i.imgur.com/Ikrj3lk.png",
        sources = listOf(
            Source(
                url = "https://isaocorp.cloudecast.com/playtv/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://45.162.64.114/PLAY_TV/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://45.177.114.115/PLAY_TV/index.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Rede Brasil",
        logo = "https://i.imgur.com/TXJKwzZ.png",
        sources = listOf(
            Source(
                url = "https://redebrasil.nuvemplay.live/hls/stream.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Rede Gospel",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/brazil/rede-gospel-br.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDQ5MjkubTN1OA.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://cdn.live.br1.jmvstream.com/w/LVW-8719/LVW8719_AcLVAxWy5J/playlist.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://redegospel-aovivo.nuvemplay.live/hls/stream.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://45.177.114.115/REDE_GOSPEL/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459545.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xNzg1MzEubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDQ5MzAubTN1OA.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDQ5MzEubTN1OA.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/178531.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104929.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104932.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104930.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104931.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/178531.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104929.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104932.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104930.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104931.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/178531.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104929.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104932.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104930.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104931.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9069.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Trace Brazuca",
        logo = "https://mondrian.claro.com.br/channels/inverse/trace-brazuca.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM4Ni5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://cdn-uw2-prod.tsv2.amagi.tv/linear/amg01131-tracetv-tracebrazuca-samsungbr/playlist.m3u8",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0NTMubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM4OC5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM4OS5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103453.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72386.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72387.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72388.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72389.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103453.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72386.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72387.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72388.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72389.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103453.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72386.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72387.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72388.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72389.ts",
                quality = "SD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "TV Câmara",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/brazil/tv-camara-br.png",
        sources = listOf(
            Source(
                url = "https://stream3.camara.gov.br/tv1/manifest.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://45.162.64.114/TV_CAMARA/index.m3u8",
                quality = "SD · 718x480",
            ),
            Source(
                url = "http://168.197.104.22/TV_CAMARA/index.m3u8",
                quality = "SD · 718x480",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459584.m3u8",
                quality = "SD · 720x480",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/69905.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "TV Evangelizar",
        logo = "https://i.imgur.com/IrYR7Kp.png",
        sources = listOf(
            Source(
                url = "http://45.162.64.114/EVANGELIZAR/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://168.197.104.22/EVANGELIZAR/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://170.83.16.50/TV_EVANGELIZAR/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459550.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/69257.ts",
                quality = "HD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "TV Justiça",
        logo = "https://mondrian.claro.com.br/channels/inverse/tv-justica.png",
        sources = listOf(
            Source(
                url = "http://45.162.64.114/TV_JUSTICA/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://168.197.104.22/TV_JUSTICA/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://170.83.16.50/TV_JUSTICA/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459585.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/69907.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "TV Novo Tempo",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/brazil/novo-tempo-br.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjQwOC5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "http://45.162.64.114/NOVO_TEMPO/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://168.197.104.22/NOVO_TEMPO/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://186.219.52.187/novo_tempo/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459543.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0NTkubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjQxMC5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjQxMS5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103459.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72408.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72409.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72410.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72411.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103459.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72408.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72409.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72410.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72411.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103459.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72408.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72409.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72410.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72411.ts",
                quality = "SD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "TV Senado",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/brazil/tv-senado-br.png",
        sources = listOf(
            Source(
                url = "http://45.162.64.114/TV_SENADO/index.m3u8",
                quality = "SD · 718x480",
            ),
            Source(
                url = "http://168.197.104.22/TV_SENADO/index.m3u8",
                quality = "SD · 718x480",
            ),
            Source(
                url = "http://170.83.16.50/TV_SENADO/index.m3u8",
                quality = "HD · 1310x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459586.m3u8",
                quality = "SD · 718x480",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/69027.ts",
                quality = "HD",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "RIT",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/brazil/rit-br.png",
        sources = listOf(
            Source(
                url = "https://acesso.ecast.site:3648/live/ritlive.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://45.177.114.115/RIT_TV/index.m3u8",
                quality = "HD · 1080x720",
            ),
            Source(
                url = "http://170.83.49.66:8083/RITHD/index.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Discovery Home & Health",
        logo = "https://img.faz-o-eli.online/discoveryhh.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/discoveryhh.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297939.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297940.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297941.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324382.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518553.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518554.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518555.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90251.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71867.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71868.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71869.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71870.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90251.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71867.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71868.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71869.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71870.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90251.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71867.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71868.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71869.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71870.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/91397.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/20757.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8739.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8740.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/93902.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30149.ts",
                quality = "HD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/discoveryhh/discoveryhh.m3u8",
            ),
        ),
        categoria = "Documentários",
    ),
    Channel(
        name = "Canal Agro",
        sources = listOf(
            Source(
                url = "https://aovivo.equipea.com.br:5443/aovivort/streams/pshRLrnv6isXq7RG4747567774043229.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://45.162.64.114/AGRO_CANAL/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://168.197.104.22/AGRO_CANAL/index.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Blits TV",
        logo = "https://i.imgur.com/FO4QTRf.jpeg",
        sources = listOf(
            Source(
                url = "https://stmv1.transmissaodigital.com/blitstv/blitstv/playlist.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "CNBC",
        logo = "https://raw.githubusercontent.com/tv-logo/tv-logos/main/countries/united-states/cnbc-us.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-679a973a97782f0008ff4bb6.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Notícias",
    ),
    Channel(
        name = "Com Brasil",
        logo = "https://i.imgur.com/GrjGwKM.png",
        sources = listOf(
            Source(
                url = "https://br5093.streamingdevideo.com.br/abc/abc/playlist.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://dfr80qz435crc.cloudfront.net/EFGH/Amagi/NewCo/New_Brasil_BR/New_Brasil.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Euronews",
        logo = "https://images-2.rakuten.tv/storage/global-live-channel/translation/artwork/bc84c3b7-6008-4ee3-8f6a-e4bb4365f082-width200-quality90.jpeg",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-619e6614c9d9650007a2b171.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Notícias",
    ),
    Channel(
        name = "SESC TV",
        logo = "https://i.imgur.com/Mu8O6CV.png",
        sources = listOf(
            Source(
                url = "http://45.162.64.114/SESC_TV/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://170.83.49.66:8083/SESCTVHD/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://186.219.52.187/sesc_tv/index.m3u8",
                quality = "SD · 720x576",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "TVideoNews",
        logo = "https://i.imgur.com/vstHOYx.png",
        sources = listOf(
            Source(
                url = "https://video01.logicahost.com.br/tvideonews/tvideonews/playlist.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Notícias",
    ),
    Channel(
        name = "A Feiticeira",
        logo = "https://images.pluto.tv/channels/631fa8dd7f25240007099a40/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-631fa8dd7f25240007099a40.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Acumuladores Obsessivos",
        logo = "https://images.pluto.tv/channels/656e2a4b4261ca00083aa99e/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-656e2a4b4261ca00083aa99e.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Adrenalina Pura TV",
        logo = "https://images.pluto.tv/channels/61b790b985706b00072cb797/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-61b790b985706b00072cb797.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Assombrações",
        logo = "https://images.pluto.tv/channels/620d1512c7986a0007220213/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-620d1512c7986a0007220213.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "BET Pluto TV",
        logo = "https://images.pluto.tv/channels/5ff768b6a4c8b80008498610/colorLogoPNG_1784064875197.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5ff768b6a4c8b80008498610.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Babyfirst",
        logo = "https://images.pluto.tv/channels/5f4fb4cf605ddf000748e16f/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f4fb4cf605ddf000748e16f.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "Bob Esponja Calça Quadrada",
        logo = "https://images.pluto.tv/channels/62545c0b002f4b0007688b61/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-62545c0b002f4b0007688b61.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzgwNjUubTN1OA.m3u8",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138065.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138066.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138067.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138068.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138069.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138070.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138071.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138072.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138073.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138074.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138075.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138065.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138066.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138067.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138068.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138069.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138070.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138071.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138072.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138073.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138074.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138075.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138065.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138066.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138067.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138068.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138069.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138070.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138071.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138072.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138073.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138074.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138075.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Boruto: Naruto Next Generations",
        logo = "https://images.pluto.tv/channels/656f389c3944b60008e5bdab/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-656f389c3944b60008e5bdab.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "CBS News",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-62310f66d5888f0007534342.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Notícias",
    ),
    Channel(
        name = "Canal Educacao",
        logo = "https://i.imgur.com/OOB7nrS.png",
        sources = listOf(
            Source(
                url = "https://canaleducacao-stream.ebc.com.br/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://45.162.64.114/CANAL_EDUCACAO/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://45.177.114.115/CANAL_EDUCACAO/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Canal Futura",
        logo = "https://i.imgur.com/LgynEBC.png",
        sources = listOf(
            Source(
                url = "http://45.162.64.114/FUTURA/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://170.83.16.50/FUTURA/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://186.219.52.187/futura/index.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Canal Gov",
        logo = "https://i.imgur.com/rHPY0Yv.png",
        sources = listOf(
            Source(
                url = "https://canalgov-stream.ebc.com.br/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://45.177.114.115/TV_BRASIL_2/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://168.197.104.22/TV_BRASIL_2/index.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Canal UOL",
        logo = "https://conteudo.imguol.com.br/c/play/logo_canaluol_2024.svg",
        sources = listOf(
            Source(
                url = "https://video24.mais.uol.com.br/live/6146.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Notícias",
    ),
    Channel(
        name = "Canal do Boi",
        logo = "https://i.imgur.com/pVM5MhS.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgyMC5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "http://45.162.64.114/CANAL_DO_BOI/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://168.197.104.22/CANAL_DO_BOI/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://170.83.16.50/CANAL_DO_BOI/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459581.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM1MTYubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgyMS5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgyMi5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103516.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71820.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71821.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71822.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103516.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71820.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71821.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71822.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103516.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71820.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71821.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71822.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/89852.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Chef TV",
        logo = "https://i.imgur.com/UYksTee.png",
        sources = listOf(
            Source(
                url = "http://168.197.104.22/CHEF_TV/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Comedy Central Pluto TV",
        logo = "https://images.pluto.tv/channels/5f357e91b18f0b00073583d2/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f357e91b18f0b00073583d2.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Comedy Central South Park",
        logo = "https://images.pluto.tv/channels/609ae66b359b270007869ff1/colorLogoPNG_1733160636316.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-609ae66b359b270007869ff1.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Cultura Fast",
        sources = listOf(
            Source(
                url = "https://fpa-gateway.tvcultura.com.br:8181/memfs/606caef0-a290-413d-9f1f-8fcdb3a73831.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Detetives Médicos",
        logo = "https://images.pluto.tv/channels/638df93ae2f2a3000737c168/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-638df93ae2f2a3000737c168.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "DumDum",
        logo = "https://upload.wikimedia.org/wikipedia/commons/c/c0/DumDum_logo.svg",
        sources = listOf(
            Source(
                url = "http://45.162.64.114/ZOOMOO_KIDS/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://168.197.104.22/ZOOMOO/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://186.219.52.187/zoomoo/index.m3u8",
                quality = "SD · 720x576",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882641.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882642.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882643.m3u8",
                quality = "SD · 960x540",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "Estado Paranormal",
        logo = "https://images.pluto.tv/channels/656e2a81954b020008ed17a4/colorLogoPNG_1732041604478.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-656e2a81954b020008ed17a4.m3u8",
                quality = "SD · 1024x576",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "FIFA+",
        logo = "https://images.pluto.tv/channels/66997e8d3a4ad20008e50be9/colorLogoPNG_1749841306387.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-66997e8d3a4ad20008e50be9.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "FailArmy",
        logo = "https://images.pluto.tv/channels/5f5141c1605ddf000748eb1b/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f5141c1605ddf000748eb1b.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Filmelier TV",
        logo = "https://images.pluto.tv/channels/633dcebd80386500074a2461/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-633dcebd80386500074a2461.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Filmes Suspense",
        logo = "https://images.pluto.tv/channels/5f171d3442a0500007362f22/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f171d3442a0500007362f22.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Futura",
        logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8e/Canal_Futura.png/960px-Canal_Futura.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTk2Mi5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "http://168.197.104.22/FUTURA/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://170.83.49.66:8083/FUTURAHD/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459572.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459573.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459574.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0OTkubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTk2NC5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTk2NS5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103499.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71962.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71963.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71964.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71965.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103499.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71962.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71963.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71964.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71965.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103499.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71962.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71963.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71964.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71965.ts",
                quality = "SD",
            ),
        ),
        categoria = "TV Aberta",
    ),
)

private fun build_CATALOG_PART_3(): List<Channel> = listOf(
    Channel(
        name = "Homeful",
        logo = "https://images.pluto.tv/channels/67603668f433320008760af1/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-67603668f433320008760af1.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "KpopTV Play",
        logo = "https://i.imgur.com/Tf0vweF.png",
        sources = listOf(
            Source(
                url = "https://giatv.bozztv.com/giatv/giatv-kpoptvplay/kpoptvplay/playlist.m3u8",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "MTV Biggest Pop",
        logo = "https://images.pluto.tv/channels/6047fbdbbb776a0007e7f2ff/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6047fbdbbb776a0007e7f2ff.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "MTV Dating",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6851bb3426beced4f2f67ee6.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "MTV Pluto TV",
        logo = "https://images.pluto.tv/channels/5f1212fb81e85c00077ae9ef/colorLogoPNG_1759263027326.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f1212fb81e85c00077ae9ef.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "MTV Reality",
        logo = "https://images.pluto.tv/channels/6851bdfc9ac48fde5e07f5ae/colorLogoPNG_1783347653051.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6851bdfc9ac48fde5e07f5ae.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "MTV Rocks",
        logo = "https://images.pluto.tv/channels/66a01e07d2d50d0008100d6a/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-66a01e07d2d50d0008100d6a.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Mais MasterChef Brasil",
        logo = "https://images.pluto.tv/channels/681111be5e0764e297fb200e/colorLogoPNG_1749582970597.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-681111be5e0764e297fb200e.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "MasterChef",
        logo = "https://images.pluto.tv/channels/6077045b6031bd00078de127/colorLogoPNG_1785430539950.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6077045b6031bd00078de127.m3u8",
                quality = "SD · 1216x684",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/masterchef/masterchef.m3u8",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Mistérios sem Solução",
        logo = "https://images.pluto.tv/channels/62b5c5a064163d0007b2efe6/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-62b5c5a064163d0007b2efe6.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "MyTime Movie Network",
        logo = "https://i.imgur.com/aiGQtzI.png",
        sources = listOf(
            Source(
                url = "https://appletree-mytime-samsungbrazil.amagi.tv/playlist.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324528.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "NatureTime",
        logo = "https://images.pluto.tv/channels/681ba2ad93d3d19bcab47433/colorLogoPNG_1785430696843.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-681ba2ad93d3d19bcab47433.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Documentários",
    ),
    Channel(
        name = "Nick Jr. Club",
        logo = "https://images.pluto.tv/channels/6824ce95f09106f4b18f4114/colorLogoPNG_1747935179470.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6824ce95f09106f4b18f4114.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "NickOnline",
        logo = "https://x1colegal.com/logo.png",
        sources = listOf(
            Source(
                url = "https://x1colegal.com/hls/stream.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "NickToons Brasil",
        logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/97/Nicktoons_logo_%282023%29.svg/330px-Nicktoons_logo_%282023%29.svg.png",
        sources = listOf(
            Source(
                url = "https://stmv2.srvif.com/nicktoons/nicktoons/playlist.m3u8",
                quality = "SD · 854x480",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "Nickelodeon",
        sources = listOf(
            Source(
                url = "https://stmv2.srvif.com/gafeab/gafeab/playlist.m3u8",
                quality = "SD · 854x480",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "Nickelodeon Clássico",
        logo = "https://images.pluto.tv/channels/6824ce10c5d53e1351ceb8d1/colorLogoPNG_1788384899252.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6824ce10c5d53e1351ceb8d1.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "Nickelodeon Teen",
        logo = "https://images.pluto.tv/channels/60f5fabf0721880007cd50e3/colorLogoPNG_1767888207915.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-60f5fabf0721880007cd50e3.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "Nickelodeon Toons",
        logo = "https://images.pluto.tv/channels/645951c0e94c38000802d2cb/colorLogoPNG_1767888119852.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-645951c0e94c38000802d2cb.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "Nickelodeon iCarly",
        logo = "https://images.pluto.tv/channels/620ff46e0a576e0007dc2f89/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-620ff46e0a576e0007dc2f89.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "O Homem que veio do Céu",
        logo = "https://images.pluto.tv/channels/62052d3b4eeb740007fbe125/colorLogoPNG_1732044609381.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-62052d3b4eeb740007fbe125.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "O Reino Infantil",
        logo = "https://images.pluto.tv/channels/5f5c216df68f920007888315/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f5c216df68f920007888315.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "PFL MMA",
        logo = "https://images.pluto.tv/channels/64f6180130ab3300083d896b/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-64f6180130ab3300083d896b.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Pluto TV Aliens",
        logo = "https://images.pluto.tv/channels/6806d65e84f24b70109485fa/colorLogoPNG_1746817107468.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6806d65e84f24b70109485fa.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Animais",
        logo = "https://images.pluto.tv/channels/6474aa984cfc2c0008883a92/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6474aa984cfc2c0008883a92.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Anime",
        logo = "https://images.pluto.tv/channels/5f12136385bccc00070142ed/colorLogoPNG_1785430875394.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f12136385bccc00070142ed.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Anime Ação",
        logo = "https://images.pluto.tv/channels/604b79c558393100078faeef/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-604b79c558393100078faeef.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Bang Bang",
        logo = "https://images.pluto.tv/channels/663b9dc7cb3ea10008f1a0ce/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-663b9dc7cb3ea10008f1a0ce.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Canal UOL",
        logo = "https://images.pluto.tv/channels/64b9370b409629000802d32b/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-64b9370b409629000802d32b.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Cine Clássicos",
        logo = "https://images.pluto.tv/channels/5fa1612a669ba0000702017b/colorLogoPNG_1733440556804.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5fa1612a669ba0000702017b.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Cine Comédia",
        logo = "https://images.pluto.tv/channels/5f12101f0b12f00007844c7c/colorLogoPNG_1732662798298.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f12101f0b12f00007844c7c.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Cine Comédia Romântica",
        logo = "https://images.pluto.tv/channels/62545ed3dab4380007582f7c/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-62545ed3dab4380007582f7c.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Cine Crime",
        logo = "https://images.pluto.tv/channels/6479ff764f5ba5000878dfe2/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6479ff764f5ba5000878dfe2.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Cine Drama",
        logo = "https://images.pluto.tv/channels/5f1210d14ae1f80007bafb1d/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f1210d14ae1f80007bafb1d.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Cine Família",
        logo = "https://images.pluto.tv/channels/5f171f032cd22e0007f17f3d/colorLogoPNG_1788969213886.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f171f032cd22e0007f17f3d.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Cine Inspiração",
        logo = "https://images.pluto.tv/channels/5fa991b1f09e020007e78626/colorLogoPNG_1748874366972.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5fa991b1f09e020007e78626.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Cine Romance",
        logo = "https://images.pluto.tv/channels/5f171f988ab9780007fa95ea/colorLogoPNG_1771344977802.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f171f988ab9780007fa95ea.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Cine Sucessos",
        logo = "https://images.pluto.tv/channels/5f120e94a5714d00074576a1/colorLogoPNG_1785430162956.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f120e94a5714d00074576a1.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Cine Terror",
        logo = "https://images.pluto.tv/channels/5f12111c9e6c2c00078ef3bb/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f12111c9e6c2c00078ef3bb.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Cozinha",
        logo = "https://images.pluto.tv/channels/5f1ef23020a5ac0007e5e8ea/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f1ef23020a5ac0007e5e8ea.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Desenhos Clássicos",
        logo = "https://images.pluto.tv/channels/655e5c4d2c46f3000877a54b/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-655e5c4d2c46f3000877a54b.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Esportes",
        logo = "https://images.pluto.tv/channels/5f32d2db0af67400077f29c4/colorLogoPNG_1782950090441.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f32d2db0af67400077f29c4.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Ficção Científica",
        logo = "https://images.pluto.tv/channels/5fa15ad6367e170007cdd098/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5fa15ad6367e170007cdd098.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Filmes Aventura",
        logo = "https://images.pluto.tv/channels/66c79a4262e5510008ff68a5/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-66c79a4262e5510008ff68a5.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Filmes Ação",
        logo = "https://images.pluto.tv/channels/5f120f41b7d403000783a6d6/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f120f41b7d403000783a6d6.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Filmes Nacionais",
        logo = "https://images.pluto.tv/channels/5f5a545d0dbf7f0007c09408/colorLogoPNG_1732044719987.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f5a545d0dbf7f0007c09408.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Filmes de Luta",
        logo = "https://images.pluto.tv/channels/6806d62369aec5b19cd628c0/colorLogoPNG_1746817781806.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6806d62369aec5b19cd628c0.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV História",
        logo = "https://images.pluto.tv/channels/5f1ef1a8cec6be00072a7ac9/colorLogoPNG_1754405659295.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f1ef1a8cec6be00072a7ac9.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Investigação",
        logo = "https://images.pluto.tv/channels/5f32cf37c9ff2b00082adbc8/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f32cf37c9ff2b00082adbc8.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Junior",
        logo = "https://images.pluto.tv/channels/5f12141b146d760007934ea7/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f12141b146d760007934ea7.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
)

private fun build_CATALOG_PART_4(): List<Channel> = listOf(
    Channel(
        name = "Pluto TV KFOOD",
        logo = "https://images.pluto.tv/channels/633ee9ba83c08f00076b60a6/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-633ee9ba83c08f00076b60a6.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Karaokê por Stingray",
        logo = "https://images.pluto.tv/channels/604b99d633a72b00078e05ad/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-604b99d633a72b00078e05ad.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Kids",
        logo = "https://images.pluto.tv/channels/5f1214a637c6fd00079c652f/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f1214a637c6fd00079c652f.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Kids Club",
        logo = "https://images.pluto.tv/channels/66c8cae7fed35b0008580ec0/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-66c8cae7fed35b0008580ec0.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Mistérios",
        logo = "https://images.pluto.tv/channels/5fac52f142044f00078e2a51/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5fac52f142044f00078e2a51.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Natureza",
        logo = "https://images.pluto.tv/channels/5f1213ba0ecebc00070e170f/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f1213ba0ecebc00070e170f.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Negócio Fechado",
        logo = "https://images.pluto.tv/channels/64ad7394798def00087b2bfe/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-64ad7394798def00087b2bfe.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Netmovies",
        logo = "https://images.pluto.tv/channels/663b9de4f999220008230fa8/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-663b9de4f999220008230fa8.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Novelas",
        logo = "https://images.pluto.tv/channels/5f512365abe1f50007d3ff56/colorLogoPNG_1785430486083.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f512365abe1f50007d3ff56.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Paisagens por Stingray",
        logo = "https://images.pluto.tv/channels/604a8dedbca75b0007b1c753/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-604a8dedbca75b0007b1c753.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Policial",
        logo = "https://images.pluto.tv/channels/678fdf9e3de7c8cf948e8824/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-678fdf9e3de7c8cf948e8824.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Record News",
        logo = "https://images.pluto.tv/channels/6102e04e9ab1db0007a980a1/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6102e04e9ab1db0007a980a1.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Retrô",
        logo = "https://images.pluto.tv/channels/5f1212ad1728050007a523b8/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f1212ad1728050007a523b8.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Shows por Stingray",
        logo = "https://images.pluto.tv/channels/604b91e0692f770007d9f33f/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-604b91e0692f770007d9f33f.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Star Trek",
        logo = "https://images.pluto.tv/channels/5f99ac4fded33000078f29ab/colorLogoPNG_1753988616763.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f99ac4fded33000078f29ab.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Séries Ação",
        logo = "https://images.pluto.tv/channels/6474ab1da51cb80008bfb5f4/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6474ab1da51cb80008bfb5f4.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Séries Comédia",
        logo = "https://images.pluto.tv/channels/655e5bc94261ca000810cb17/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-655e5bc94261ca000810cb17.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Séries Criminais",
        logo = "https://images.pluto.tv/channels/6474ab5cdc7a760008745008/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6474ab5cdc7a760008745008.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Séries Drama",
        logo = "https://images.pluto.tv/channels/65f060d84e01740008d7421f/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-65f060d84e01740008d7421f.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Séries Novelescas",
        logo = "https://images.pluto.tv/channels/691627e4a29a6123e400b3e0/colorLogoPNG_1764878235665.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-691627e4a29a6123e400b3e0.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Séries Sci-Fi",
        logo = "https://images.pluto.tv/channels/63d2ba2f60bc8f0008981a0e/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-63d2ba2f60bc8f0008981a0e.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Terror Trash",
        logo = "https://images.pluto.tv/channels/66aa67493a4ad2000806d91b/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-66aa67493a4ad2000806d91b.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Turbo",
        logo = "https://images.pluto.tv/channels/6014761dfb91870008ea6463/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6014761dfb91870008ea6463.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Viagens",
        logo = "https://images.pluto.tv/channels/5f32d432d612e50007e56133/colorLogoPNG_1769016717502.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f32d432d612e50007e56133.m3u8",
                quality = "SD · 854x480",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pluto TV Vida Real",
        logo = "https://images.pluto.tv/channels/5f32d4d9ec194100070c7449/colorLogoPNG_1732044921019.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f32d4d9ec194100070c7449.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "Pronto-socorro: Histórias De Emergência",
        logo = "https://images.pluto.tv/channels/61bb72a7bf8c520007a8fd27/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-61bb72a7bf8c520007a8fd27.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "RACER Brasil",
        logo = "https://images.pluto.tv/channels/65a6818c7bdc8d0008457b21/colorLogoPNG_1746817068891.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-65a6818c7bdc8d0008457b21.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Realmadrid TV",
        logo = "https://images.pluto.tv/channels/63dac28760bc8f0008a7654b/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-63dac28760bc8f0008a7654b.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Red Bull TV BR",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-67813f3162bf016db944c9ab.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Rede TV!",
        logo = "https://i.imgur.com/ZJgD38F.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI5Ny5tM3U4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://45.162.64.114/REDE_TV/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://168.197.104.22/REDE_TV/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://170.83.16.50/REDE_TV/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xNzg1MzIubTN1OA.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjI5OS5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjMwMC5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Runtime",
        logo = "https://images.pluto.tv/channels/62c5d32e2c48f9000715b6e9/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-62c5d32e2c48f9000715b6e9.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "SFT Combat",
        logo = "https://images.pluto.tv/channels/6660b636cb3ea10008429c6a/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6660b636cb3ea10008429c6a.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Smithsonian Channel Pluto TV",
        logo = "https://images.pluto.tv/channels/6298bd10d88ef000073f16b7/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6298bd10d88ef000073f16b7.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Pluto TV",
    ),
    Channel(
        name = "South Park: Coleção Cartman",
        logo = "https://images.pluto.tv/channels/65df71008b24c80008f04281/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-65df71008b24c80008f04281.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "South Park: Coleção Kenny",
        logo = "https://images.pluto.tv/channels/65df704366eec8000898e32f/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-65df704366eec8000898e32f.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "South Park: Coleção Kyle",
        logo = "https://images.pluto.tv/channels/65df713dec9fda0008b7a81d/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-65df713dec9fda0008b7a81d.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Tastemade",
        logo = "https://images.pluto.tv/channels/5fd1419a3b4f4b000773ba85/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5fd1419a3b4f4b000773ba85.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Tastemade Casa",
        logo = "https://images.pluto.tv/channels/68b88821e542386ab0bf5bef/colorLogoPNG_1759795743690.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-68b88821e542386ab0bf5bef.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Tastemade Viagem",
        logo = "https://images.pluto.tv/channels/68b8875777201ec428d9eaa5/colorLogoPNG_1759784936254.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-68b8875777201ec428d9eaa5.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "The Pet Collective",
        logo = "https://images.pluto.tv/channels/5f515ebac01c0f00080e8439/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5f515ebac01c0f00080e8439.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Tokusato",
        logo = "https://images.pluto.tv/channels/5ff609de50ab210008025c1b/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-5ff609de50ab210008025c1b.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "Top Barça",
        logo = "https://images.pluto.tv/channels/6888ee858f4a4aa11feb9430/colorLogoPNG_1753987309681.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-6888ee858f4a4aa11feb9430.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Travel Box Brazil",
        logo = "https://i.imgur.com/3tBJERH.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM5MC5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "http://168.197.104.22/TRAVEL_BOX_BRASIL/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://170.83.49.66:8083/TRAVELBOXHD/index.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0NTIubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM5Mi5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjM5My5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103452.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72390.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72391.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72392.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72393.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103452.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72390.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72391.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72392.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72393.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103452.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72390.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72391.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72392.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72393.ts",
                quality = "SD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "UFC",
        logo = "https://images.pluto.tv/channels/69a20556814d27f4ae630a92/colorLogoPNG_1772226745279.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-69a20556814d27f4ae630a92.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "World Poker Tour",
        logo = "https://images.pluto.tv/channels/63eba66da8b2270008436b10/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-63eba66da8b2270008436b10.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Yu-Gi-Oh",
        logo = "https://images.pluto.tv/channels/63988a50be012600070f5db3/colorLogoPNG.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-63988a50be012600070f5db3.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Z Nation",
        logo = "https://images.pluto.tv/channels/66b3af48d2d50d00083d6936/colorLogoPNG_1785430432127.png",
        sources = listOf(
            Source(
                url = "https://jmp2.uk/plu-66b3af48d2d50d00083d6936.m3u8",
                quality = "SD · 1216x684",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "AEROPORTO AREA RESTRITA",
        logo = "http://xvbroker.click:80/images/dsAt8cMDBjbP3mVNdeYR4Vln2ZyBZpf6UImqVMpJX6g5QngYjG1K5Rghi2qcfLTA.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/2945300.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xNTA0ODgubTN1OA.m3u8",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/150488.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/150489.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/150490.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/150488.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/150489.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/150490.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/150488.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/150489.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/150490.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "FILHINHO DA MAMAE",
        logo = "http://xvbroker.click:80/images/dsAt8cMDBjbP3mVNdeYR4Vln2ZyBZpf6UImqVMpJX6g5QngYjG1K5Rghi2qcfLTA.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/2945301.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Amazon Prime Video 01",
        logo = "https://img.faz-o-eli.online/primevideo.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/primevideo/primevideo.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/primevideo.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460179.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/292698.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
)

private fun build_CATALOG_PART_5(): List<Channel> = listOf(
    Channel(
        name = "Amazon Prime Video 02",
        logo = "https://img.faz-o-eli.online/primevideo.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/primevideo2/primevideo2.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/primevideo2.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460180.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/121180.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Amazon Prime Video 03",
        logo = "http://www.fontedecanais.gs/logos/canais/primevideo.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460181.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/121216.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Canal GOAT (Jogo 1)",
        logo = "http://logos.appscms.xyz/logos/2025/canalgoat.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460243.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Canal GOAT (Jogo 2)",
        logo = "http://logos.appscms.xyz/logos/2025/canalgoat.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460244.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/242172.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/242173.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/242174.ts",
                quality = "SD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Canal GOAT (Jogo 3)",
        logo = "http://logos.appscms.xyz/logos/2025/canalgoat.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460245.m3u8",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Combate",
        logo = "https://img.faz-o-eli.online/combate.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg0Mi5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/combate.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297542.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297543.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297544.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297552.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297553.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg0NC5tM3U4.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg0NS5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg0Ni5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71844.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71842.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71843.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71845.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71846.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71844.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71842.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71843.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71845.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71846.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71844.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71842.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71843.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71845.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71846.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/81586.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13506.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8714.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8716.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30066.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30067.ts",
                quality = "HD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/combate/combate.m3u8",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "DAZN (Jogo 1)",
        logo = "http://logos.appscms.xyz/logos/2025/dazn.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460239.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184550.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184551.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184552.ts",
                quality = "SD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "DAZN (Jogo 2)",
        logo = "http://logos.appscms.xyz/logos/2025/dazn.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460240.m3u8",
                quality = "SD · 640x360",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184553.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184554.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184555.ts",
                quality = "SD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "DAZN (Jogo 3)",
        logo = "http://logos.appscms.xyz/logos/2025/dazn.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460241.m3u8",
                quality = "SD · 640x360",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "DAZN (Jogo 4)",
        logo = "http://logos.appscms.xyz/logos/2025/dazn.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460242.m3u8",
                quality = "SD · 640x360",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Disney+ 01",
        logo = "http://www.fontedecanais.me/logos/canais/disneyplus.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/296915.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/142032.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/128347.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/142033.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/142032.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/128347.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/142033.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/142032.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/128347.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/142033.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1169095.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Disney+ 02",
        logo = "http://www.fontedecanais.me/logos/canais/disneyplus.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/296916.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/142034.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/129345.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/142035.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/142034.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/129345.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/142035.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/142034.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/129345.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/142035.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1169096.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Disney+ 03",
        logo = "http://www.fontedecanais.me/logos/canais/disneyplus.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/296917.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/142036.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/129346.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/142037.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/142036.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/129346.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/142037.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/142036.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/129346.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/142037.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1169097.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Disney+ 04",
        logo = "http://www.fontedecanais.me/logos/canais/disneyplus.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/296918.m3u8",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/142038.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/129347.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/142039.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/142038.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/129347.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/142039.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/142038.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/129347.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/142039.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1169098.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Disney+ 05",
        logo = "http://www.fontedecanais.me/logos/canais/disneyplus.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/296919.m3u8",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1169099.ts",
                quality = "HD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Mr Olympia 01",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460265.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/2471612.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Mr Olympia 02",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460266.m3u8",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/2471613.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "NBA 01",
        logo = "http://www.fontedecanais.me/logos/canais/nba.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/514097.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "N Sports (Jogo 1)",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460254.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Paramount+ (Jogo 1)",
        logo = "https://img.faz-o-eli.online/paramountplus.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/paramountplus.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460171.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460273.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103524.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72977.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72978.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72979.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72980.ts",
                quality = "SD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Paramount+ (Jogo 2)",
        logo = "https://www.fontedecanais.gs/logos/canais/paramountplus.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460172.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460274.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103523.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72981.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72982.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72983.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72984.ts",
                quality = "SD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Paramount+ (Jogo 3)",
        logo = "https://www.fontedecanais.gs/logos/canais/paramountplus.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460173.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460275.m3u8",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103522.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72985.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72986.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72987.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72988.ts",
                quality = "SD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Paramount+ (Jogo 6)",
        logo = "https://logos.appscms.xyz/logos/2025/Paramount.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460278.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "SportyNet 01",
        logo = "https://img.faz-o-eli.online/sportynet.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/sportynet.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460158.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460269.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/184572.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/184569.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/184573.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/184570.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/184571.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/184572.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/184569.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/184573.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/184570.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/184571.ts",
                quality = "SD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "UFC Fight Pass 01",
        logo = "https://img.faz-o-eli.online/ufcfightpass.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/ufcfightpass.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297545.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297548.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/2403505.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "UFC Fight Pass 03",
        logo = "http://logos.imperioapps.top/UFC_FIGHT_PASS.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297547.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/297550.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/2403507.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Canal Brasil",
        logo = "http://www.imgu.top/logos/CANAL%20BRASIL.jpg",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgxNi5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324439.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM1MTgubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgxOC5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgxOS5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103518.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71816.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71817.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71818.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71819.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103518.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71816.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71817.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71818.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71819.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103518.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71816.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71817.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71818.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71819.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/107979.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8691.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8692.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35230.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35231.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Cine SKY 01",
        logo = "http://www.fontedecanais.dev/logos/canais/cinesky.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1832882.m3u8",
                quality = "HD · 1920x816",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882617.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13071.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Cine SKY 02",
        logo = "http://www.fontedecanais.org/logos/canais/cinesky.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882618.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13072.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Cine SKY 03",
        logo = "http://www.fontedecanais.org/logos/canais/cinesky.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882619.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13073.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Cine SKY 04",
        logo = "http://www.fontedecanais.org/logos/canais/cinesky.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882620.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Cine SKY 05",
        logo = "http://www.fontedecanais.org/logos/canais/cinesky.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882621.m3u8",
                quality = "HD · 1280x934",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13075.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Lifetime",
        logo = "http://www.fontedecanais.me/logos/canais/lifetime.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjE1NC5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518571.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518572.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518573.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0OTIubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjE1Ni5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjE1Ny5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103492.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72154.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72155.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72156.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72157.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103492.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72154.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72155.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72156.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72157.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103492.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72154.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72155.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72156.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72157.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8943.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8944.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13538.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "MAX 01",
        logo = "http://www.fontedecanais.gs/logos/canais/max.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460233.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140504.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71904.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140510.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140504.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71904.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140510.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140504.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71904.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140510.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/594007.ts",
                quality = "FHD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/max1/max1.m3u8",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "MAX 02",
        logo = "http://www.fontedecanais.gs/logos/canais/max.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3460234.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140505.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71905.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140511.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140505.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71905.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140511.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140505.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71905.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140511.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/594010.ts",
                quality = "HD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/max2/max2.m3u8",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "SBT+ Novelas",
        logo = "http://www.fontedecanais.me/logos/canais/sbt.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324283.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Sony One Emoções",
        logo = "http://logos.appscms.xyz/logos/2025/Sonyone.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324517.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Studio Universal",
        logo = "https://img.faz-o-eli.online/studiouniversal.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/studiouniversal/studiouniversal.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjMzNC5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/studiouniversal.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324392.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324469.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324471.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0NjcubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjMzNi5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjMzNy5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103467.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72334.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72335.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72336.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72337.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103467.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72334.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72335.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72336.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72337.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103467.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72334.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72335.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72336.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72337.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/108183.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9115.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9117.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35246.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35247.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Gloob",
        logo = "https://img.faz-o-eli.online/gloob.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/gloob/gloob.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzExNTMubTN1OA.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/gloob.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882625.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882626.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882627.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0OTgubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjEwMC5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzExMTcubTN1OA.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103498.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131153.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72099.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72100.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131117.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103498.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131153.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72099.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72100.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131117.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103498.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131153.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72099.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72100.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131117.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13526.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8888.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8887.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "Gloobinho",
        logo = "https://logos.imperioapps.top/GLOBINHO.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDYzMC5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882628.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882629.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882630.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0OTcubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy85MDYyOS5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzExNzMubTN1OA.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103497.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90631.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90630.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90629.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131173.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103497.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90631.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90630.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90629.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131173.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103497.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90631.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90630.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90629.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131173.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/69036.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/69037.ts",
                quality = "HD",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "Kids Mais",
        logo = "https://i.imgur.com/86cfDFx.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882637.m3u8",
                quality = "SD · 640x360",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "SBT+ Kids",
        logo = "http://www.fontedecanais.me/logos/canais/sbt.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324284.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "TV Rá Tim Bum",
        logo = "https://logos.imperioapps.top/TV_RA_TIM_BUM.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjQxMi5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882634.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882635.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/1882636.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0NTgubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjQxNC5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjQxNS5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103458.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72412.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72413.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72414.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72415.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103458.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72412.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72413.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72414.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72415.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103458.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72412.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72413.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72414.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72415.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9193.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "TV Zyn",
        logo = "http://www.fontedecanais.me/logos/canais/sbt.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324286.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "Band Campinas",
        logo = "http://www.fontedecanais.me/logos/canais/band.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324352.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324353.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/20522.ts",
                quality = "HD",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Band Curitiba",
        logo = "http://www.fontedecanais.me/logos/canais/band.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324354.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324355.m3u8",
                quality = "SD · 854x480",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Band MG",
        logo = "http://www.fontedecanais.me/logos/canais/band.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324359.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324361.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Band Pará",
        logo = "http://www.fontedecanais.me/logos/canais/band.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324362.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Band Recife TV Tribuna",
        logo = "http://www.fontedecanais.me/logos/canais/band.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324364.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324365.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Band RJ",
        logo = "https://img.faz-o-eli.online/band.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/bandrj.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324366.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/20524.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/bandrj/bandrj.m3u8",
            ),
        ),
        categoria = "TV Aberta",
    ),
)

private fun build_CATALOG_PART_6(): List<Channel> = listOf(
    Channel(
        name = "Band RN",
        logo = "http://www.fontedecanais.me/logos/canais/band.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324367.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Band Sergipe",
        logo = "http://www.fontedecanais.me/logos/canais/band.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324370.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Rede Família",
        logo = "http://www.fontedecanais.gs/logos/canais/redefamilia.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459544.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "SBT Alterosa",
        logo = "http://www.fontedecanais.me/logos/canais/sbt.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324287.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72621.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/218366.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72621.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/218366.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72621.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/218366.ts",
                quality = "FHD",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "SBT BA",
        logo = "http://www.fontedecanais.me/logos/canais/sbt.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324288.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "SBT GO",
        logo = "http://www.fontedecanais.me/logos/canais/sbt.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324291.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "SBT Londrina",
        logo = "http://www.fontedecanais.me/logos/canais/sbt.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324312.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "SBT MA",
        logo = "http://www.fontedecanais.me/logos/canais/sbt.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324295.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "SBT Maringá",
        logo = "http://www.fontedecanais.me/logos/canais/sbt.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324311.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "SBT MG",
        logo = "http://www.fontedecanais.me/logos/canais/sbt.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324296.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324310.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "SBT MT",
        logo = "http://www.fontedecanais.me/logos/canais/sbt.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324297.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "SBT PI",
        logo = "http://www.fontedecanais.me/logos/canais/sbt.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324300.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324309.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/131432.ts",
                quality = "HD",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "SBT PR",
        logo = "http://www.fontedecanais.me/logos/canais/sbt.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324301.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/88432.ts",
                quality = "HD",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "SBT RJ",
        logo = "https://img.faz-o-eli.online/sbt.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/sbtrj/sbtrj.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDg2ODEubTN1OA.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/sbtrj.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324302.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324303.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDg2ODIubTN1OA.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDg2ODMubTN1OA.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/108681.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/108682.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/108683.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/108681.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/108682.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/108683.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/108681.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/108682.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/108683.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/131563.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/35844.ts",
                quality = "HD",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "SBT TV Jornal PE",
        logo = "http://www.fontedecanais.me/logos/canais/sbt.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324313.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "TVE Bahia",
        logo = "http://i.imgur.com/IWGyp65.jpeg",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8yNDE1NTEubTN1OA.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/3459590.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8yNDE1NTIubTN1OA.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8yNDE1NTMubTN1OA.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/241551.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/241552.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/241553.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/241551.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/241552.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/241553.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/241551.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/241552.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/241553.ts",
                quality = "SD",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Tv Goiania",
        logo = "http://www.fontedecanais.me/logos/canais/band.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324373.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "BIS",
        logo = "http://www.fontedecanais.me/logos/canais/bis.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgwNi5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518550.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518551.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518552.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM1MjcubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgwOC5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTgwOS5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103527.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71806.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71807.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71808.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71809.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103527.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71806.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71807.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71808.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71809.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103527.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71806.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71807.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71808.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71809.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/107985.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13502.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8680.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8681.ts",
                quality = "HD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Comedy Central",
        logo = "http://logos.appscms.xyz/logos/2025/comedycentral.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324380.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Food Network",
        logo = "https://img.faz-o-eli.online/foodnetwork.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/foodnetwork/foodnetwork.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkzMi5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/foodnetwork.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518566.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518567.m3u8",
                quality = "SD · 768x432",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM1MDEubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkzNC5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTkzNS5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103501.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71932.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71933.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71934.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71935.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103501.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71932.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71933.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71934.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71935.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103501.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71932.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71933.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71934.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71935.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13522.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8811.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8810.ts",
                quality = "HD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Modo Viagem",
        logo = "http://www.fontedecanais.me/logos/canais/modoviagem.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjE2Mi5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518574.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518575.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/518576.m3u8",
                quality = "SD · 960x540",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0ODgubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjE2NC5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjE2NS5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103488.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72162.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72163.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72164.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72165.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103488.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72162.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72163.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72164.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72165.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103488.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72162.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72163.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72164.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72165.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8883.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/92649.ts",
                quality = "HD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "SBT+ Raiz",
        logo = "http://www.fontedecanais.me/logos/canais/sbt.png",
        sources = listOf(
            Source(
                url = "http://64.31.23.207:80/59471019/60849037/324285.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "24H Chaves",
        logo = "https://img.faz-o-eli.online/24h.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/24h_chaves.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137139.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137140.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137141.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137142.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137143.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137144.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137145.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137139.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137140.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137141.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137142.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137143.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137144.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137145.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137139.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137140.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137141.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137142.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137143.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137144.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137145.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1169218.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1169219.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/24h_chaves/24h_chaves.m3u8",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "24H Dragon Ball Z",
        logo = "https://img.faz-o-eli.online/24h.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/24h_dragonball.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1169226.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/24h_dragonball/24h_dragonball.m3u8",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "24H Naruto Clássico",
        logo = "https://img.faz-o-eli.online/24h.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/24h_naruto.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/24h_naruto/24h_naruto.m3u8",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "24H Os Simpsons",
        logo = "https://img.faz-o-eli.online/24h.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/24h_simpsons.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135601.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135600.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135599.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135598.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135597.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135596.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135595.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135594.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135593.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135592.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135591.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135590.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135589.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135588.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135587.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135586.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135585.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135584.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135583.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135582.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135581.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135580.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135579.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135578.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135577.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135576.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135575.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135574.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135573.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135572.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135571.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135570.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135601.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135600.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135599.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135598.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135597.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135596.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135595.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135594.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135593.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135592.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135591.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135590.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135589.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135588.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135587.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135586.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135585.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135584.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135583.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135582.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135581.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135580.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135579.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135578.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135577.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135576.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135575.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135574.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135573.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135572.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135571.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135570.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135601.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135600.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135599.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135598.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135597.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135596.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135595.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135594.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135593.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135592.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135591.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135590.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135589.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135588.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135587.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135586.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135585.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135584.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135583.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135582.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135581.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135580.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135579.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135578.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135577.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135576.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135575.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135574.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135573.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135572.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135571.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135570.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1169244.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/24h_simpsons/24h_simpsons.m3u8",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "24H Pica Pau",
        logo = "https://img.faz-o-eli.online/24h.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/24h_picapau.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/136821.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/136821.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/136821.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/24h_picapau/24h_picapau.m3u8",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "24H Todo Mundo Odeia o Cris",
        logo = "https://img.faz-o-eli.online/24h.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/24h_odeiachris.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135469.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135470.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135471.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135472.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135469.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135470.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135471.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135472.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135469.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135470.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135471.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135472.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1169249.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/24h_odeiachris/24h_odeiachris.m3u8",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "24H Friends",
        logo = "https://img.faz-o-eli.online/24h.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/24h_friends.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138814.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138815.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138816.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138817.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138818.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138819.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138820.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138821.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138822.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138823.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138814.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138815.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138816.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138817.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138818.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138819.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138820.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138821.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138822.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138823.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138814.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138815.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138816.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138817.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138818.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138819.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138820.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138821.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138822.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138823.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/24h_friends/24h_friends.m3u8",
                quality = "SD · 768x432",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Discovery World",
        logo = "https://img.faz-o-eli.online/discoveryworld.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg4Ny5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/discoveryworld.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM1MDkubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg4OS5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MTg5MC5tM3U4.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103509.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71887.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71888.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71889.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71890.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103509.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71887.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71888.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71889.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71890.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103509.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71887.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71888.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71889.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71890.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/91398.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/13513.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8758.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/8759.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/93905.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30157.ts",
                quality = "HD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/discoveryworld/discoveryworld.m3u8",
            ),
        ),
        categoria = "Documentários",
    ),
    Channel(
        name = "CazeTV 1",
        logo = "https://img.faz-o-eli.online/cazetv.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/caze1/caze1.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/caze1.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/76671.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/76671.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/76671.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/227520.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/245798.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/245797.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "CazeTV 2",
        logo = "https://img.faz-o-eli.online/cazetv.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/caze2/caze2.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8zODQ0NjMubTN1OA.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/caze2.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/384463.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/384463.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/384463.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "PT - A Bola",
        logo = "https://img.faz-o-eli.online/pt_abola.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/pt_abola.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/pt_abola/pt_abola.m3u8",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "PT - Benfica TV",
        logo = "https://img.faz-o-eli.online/pt_benficatv.prev.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/pt_benficatv.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/pt_benficatv/pt_benficatv.m3u8",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "PT - Canal 11",
        logo = "https://img.faz-o-eli.online/pt_canal11.prev.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/pt_canal11.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/pt_canal11/pt_canal11.m3u8",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "PT - Eleven 1",
        logo = "https://img.faz-o-eli.online/pt_eleven1.prev.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/pt_eleven1.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/pt_eleven1/pt_eleven1.m3u8",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "PT - Eleven 2",
        logo = "https://img.faz-o-eli.online/pt_eleven2.prev.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/pt_eleven2.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/pt_eleven2/pt_eleven2.m3u8",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "PT - Eleven 3",
        logo = "https://img.faz-o-eli.online/pt_eleven3.prev.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/pt_eleven3.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/pt_eleven3/pt_eleven3.m3u8",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "PT - Sporttv 1",
        logo = "https://img.faz-o-eli.online/sportv.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/pt_sportv1.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/pt_sportv1/pt_sportv1.m3u8",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "PT - Sporttv 2",
        logo = "https://img.faz-o-eli.online/sportv.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/pt_sportv2.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/pt_sportv2/pt_sportv2.m3u8",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "PT - Sporttv 3",
        logo = "https://img.faz-o-eli.online/sportv.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/pt_sportv3.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/pt_sportv3/pt_sportv3.m3u8",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "PT - Sporttv 4",
        logo = "https://img.faz-o-eli.online/sportv.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/pt_sportv4.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/pt_sportv4/pt_sportv4.m3u8",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "PT - Sporttv 5",
        logo = "https://img.faz-o-eli.online/sportv.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/pt_sportv5.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/pt_sportv5/pt_sportv5.m3u8",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "PT - Sporttv 6",
        logo = "https://img.faz-o-eli.online/sportv.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/pt_sportv6.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/pt_sportv6/pt_sportv6.m3u8",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "PT - Sporttv 7",
        logo = "https://img.faz-o-eli.online/sportv.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/pt_sportv7.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/pt_sportv7/pt_sportv7.m3u8",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "SportyNet+ 1",
        logo = "https://img.faz-o-eli.online/sportynet.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xODQ1NzMubTN1OA.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/sportynetplus1.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xODQ1NzIubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xODQ1NzAubTN1OA.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xODQ1NzEubTN1OA.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184572.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184569.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184573.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184570.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184571.ts",
                quality = "SD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "SportyNet+ 2",
        logo = "https://img.faz-o-eli.online/sportynet.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xODQ1NzQubTN1OA.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/sportynetplus2.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xODQ1NzcubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xODQ1NzUubTN1OA.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xODQ1NzYubTN1OA.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184577.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184574.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184578.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184575.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184576.ts",
                quality = "SD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "SportyNet+ 3",
        logo = "https://img.faz-o-eli.online/sportynet.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xODQ1NzkubTN1OA.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/sportynetplus3.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xODQ1ODIubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xODQ1ODAubTN1OA.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xODQ1ODEubTN1OA.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184582.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184579.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184583.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184580.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184581.ts",
                quality = "SD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Globo AM",
        logo = "https://img.faz-o-eli.online/globosp.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/globoam.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/globoam/globoam.m3u8",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Globo CE",
        logo = "https://img.faz-o-eli.online/globosp.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/globoce.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/globoce/globoce.m3u8",
            ),
        ),
        categoria = "TV Aberta",
    ),
)

private fun build_CATALOG_PART_7(): List<Channel> = listOf(
    Channel(
        name = "Globo ES",
        logo = "https://img.faz-o-eli.online/globosp.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/globoes/globoes.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/globoes.txt",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Globo GO",
        logo = "https://img.faz-o-eli.online/globosp.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/globogo.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/globogo/globogo.m3u8",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Globo PB",
        logo = "https://img.faz-o-eli.online/globosp.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/globopb.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/globopb/globopb.m3u8",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Globo PE",
        logo = "https://img.faz-o-eli.online/globosp.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/globope.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/globope/globope.m3u8",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Globo RJ",
        logo = "https://img.faz-o-eli.online/globorj.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/globorj/globorj.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjAyNS5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/globorj.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzExNjMubTN1OA.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMzExODIubTN1OA.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72025.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131163.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131182.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72025.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131163.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131182.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72025.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131163.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131182.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1168919.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1168920.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1168921.ts",
                quality = "HD",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Globo RS",
        logo = "https://img.faz-o-eli.online/globosp.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/globors/globors.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/globors.txt",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Record GO",
        logo = "https://img.faz-o-eli.online/record.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/recordgo.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/218822.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/218823.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/218822.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/218823.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/218822.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/218823.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/106096.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/106097.ts",
                quality = "HD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/recordgo/recordgo.m3u8",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Record MG",
        logo = "https://img.faz-o-eli.online/record.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/recordmg/recordmg.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/recordmg.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9055.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9054.ts",
                quality = "HD",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Record RJ",
        logo = "https://img.faz-o-eli.online/record.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/recordrj/recordrj.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/recordrj.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/106638.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72594.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/96526.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/106638.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72594.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/96526.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/106638.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72594.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/96526.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/106095.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/71697.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9063.ts",
                quality = "HD",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "HGTV",
        logo = "https://img.faz-o-eli.online/hgtv.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjE0Mi5tM3U4.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/hgtv.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8xMDM0OTMubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjE0NC5tM3U4.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy83MjE0NS5tM3U4.m3u8",
                quality = "SD · 852x480",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103493.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72142.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72143.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72144.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72145.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103493.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72142.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72143.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72144.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72145.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103493.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72142.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72143.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72144.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72145.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/304506.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/304507.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/304508.ts",
                quality = "HD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/hgtv/hgtv.m3u8",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "MTV",
        logo = "https://img.faz-o-eli.online/mtv.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/mtv/mtv.m3u8",
                quality = "FHD · 1536x1024",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/mtv.txt",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "A Fazenda 18 1",
        logo = "https://img.faz-o-eli.online/afazenda2.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/afazenda.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/2470061.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/afazenda/afazenda.m3u8",
            ),
        ),
        categoria = "PPV",
    ),
    Channel(
        name = "A Fazenda 18 2",
        logo = "https://img.faz-o-eli.online/afazenda2.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/afazenda2.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/2470062.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/afazenda2/afazenda2.m3u8",
            ),
        ),
        categoria = "PPV",
    ),
    Channel(
        name = "A Fazenda 18 3",
        logo = "https://img.faz-o-eli.online/afazenda2.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/afazenda3.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/2470063.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/afazenda3/afazenda3.m3u8",
            ),
        ),
        categoria = "PPV",
    ),
    Channel(
        name = "A Fazenda 18 4",
        logo = "https://img.faz-o-eli.online/afazenda2.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/afazenda4.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/2470064.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/afazenda4/afazenda4.m3u8",
            ),
        ),
        categoria = "PPV",
    ),
    Channel(
        name = "A Fazenda 18 5",
        logo = "https://img.faz-o-eli.online/afazenda2.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/afazenda5.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/2470065.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/afazenda5/afazenda5.m3u8",
            ),
        ),
        categoria = "PPV",
    ),
    Channel(
        name = "CazeTV 3",
        logo = "https://img.faz-o-eli.online/cazetv.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/caze3/caze3.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/caze3.txt",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Globo DF",
        logo = "https://img.faz-o-eli.online/globosp.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/globodf/globodf.m3u8",
                quality = "HD · 1280x720",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/globodf.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131151.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131164.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131116.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131151.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131164.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131116.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131151.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131164.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131116.ts",
                quality = "SD",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "Globo MG",
        logo = "https://img.faz-o-eli.online/globosp.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/globomg/globomg.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/globomg.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131200.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131159.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/131209.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131200.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131159.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/131209.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131200.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131159.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/131209.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1168938.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1168939.ts",
                quality = "HD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1168940.ts",
                quality = "HD",
            ),
        ),
        categoria = "TV Aberta",
    ),
    Channel(
        name = "MAX 03",
        logo = "https://img.faz-o-eli.online/max.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/max3/max3.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/max3.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140506.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71906.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140512.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140506.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71906.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140512.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140506.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71906.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140512.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/594011.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Amazon Prime Video 04",
        logo = "https://img.faz-o-eli.online/primevideo.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/primevideo4/primevideo4.m3u8",
                quality = "FHD · 1920x1080",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/primevideo4.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/121217.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "SporTV 4",
        logo = "https://img.faz-o-eli.online/sportv4.png",
        sources = listOf(
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8yNzYwODkubTN1OA.m3u8",
                quality = "FHD",
            ),
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/sportv4.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8yNzYwOTMubTN1OA.m3u8",
                quality = "4K",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8yNzYwOTAubTN1OA.m3u8",
                quality = "HD",
            ),
            Source(
                url = "https://frostview.squareweb.app/relay/m/aHR0cDovL3BhbGRpLnBybzo4MC9saXZlL3RvbmluaG8wMi9DYjQ5ODAzNy8yNzYwOTEubTN1OA.m3u8",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/276089.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/276090.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/276091.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/276092.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/276093.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/276089.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/276090.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/276091.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/276092.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/276093.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/276089.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/276090.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/276091.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/276092.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/276093.ts",
                quality = "4K",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/570369.ts",
                quality = "FHD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/sportv4/sportv4.m3u8",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Star Channel",
        logo = "https://img.faz-o-eli.online/starchannel.png",
        sources = listOf(
            Source(
                url = "https://52d080a3e172c33fd6886a37e7.s23-cloudfront-net.lat/8e8e8b142192ea65/starchannel.txt",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/starchannel/starchannel.m3u8",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "BJJ STARS",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/159276.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/159277.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/159278.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/159276.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/159277.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/159278.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/159276.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/159277.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/159278.ts",
                quality = "SD",
            ),
        ),
        categoria = "PPV",
    ),
    Channel(
        name = "Brasileirao Serie C 1",
        logo = "http://tvonhdbr.com/images/d71a6a98c5c853503ec304c5f182f9b3.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/157407.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/157408.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/157409.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/157407.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/157408.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/157409.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/157407.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/157408.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/157409.ts",
                quality = "SD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Brasileirao Serie C 2",
        logo = "http://tvonhdbr.com/images/d71a6a98c5c853503ec304c5f182f9b3.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/157410.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/157411.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/157412.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/157410.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/157411.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/157412.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/157410.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/157411.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/157412.ts",
                quality = "SD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Brasileirao Serie C 3",
        logo = "http://tvonhdbr.com/images/d71a6a98c5c853503ec304c5f182f9b3.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/157413.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/157414.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/157415.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/157413.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/157414.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/157415.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/157413.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/157414.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/157415.ts",
                quality = "SD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Copa do Brasil 1",
        logo = "http://tvonhdbr.com/images/9a2901b02f405b562056854b5ab87abb.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/150498.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/150499.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/150500.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/150498.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/150499.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/150500.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/150498.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/150499.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/150500.ts",
                quality = "SD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Copa do Brasil 2",
        logo = "http://tvonhdbr.com/images/9a2901b02f405b562056854b5ab87abb.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/150501.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/150502.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/150503.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/150501.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/150502.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/150503.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/150501.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/150502.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/150503.ts",
                quality = "SD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Curta",
        logo = "http://tvonhdbr.com/static/logos/canais/curta.png",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71855.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71856.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71857.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71858.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103512.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71855.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71856.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71857.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71858.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103512.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71855.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71856.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71857.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71858.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103512.ts",
                quality = "4K",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/12948.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Documentários",
    ),
    Channel(
        name = "DOG TV",
        logo = "http://tvonhdbr.com/images/2ecd0b43c0ba5f0fac0f9fbb98bb79cd.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/76922.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104909.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/76923.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104908.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/178517.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/76922.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104909.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/76923.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104908.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/76922.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104909.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/76923.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104908.ts",
                quality = "SD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Eurochannel",
        logo = "http://tvonhdbr.com/images/e319732e23a51cafb209d07c7a26c615.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104910.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104913.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104911.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104912.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104910.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104913.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104911.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104912.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104910.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104913.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104911.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104912.ts",
                quality = "SD",
            ),
        ),
        categoria = "Notícias",
    ),
    Channel(
        name = "FashionTV",
        logo = "https://i.imgur.com/1jtRh4S.png",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103503.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71924.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71925.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71926.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71927.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103503.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71924.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71925.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71926.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71927.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103503.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71924.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71925.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71926.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71927.ts",
                quality = "SD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Film&Arts",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104914.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104920.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/178518.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104915.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104916.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104914.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104920.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/178518.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104915.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104916.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104914.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104920.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/178518.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104915.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104916.ts",
                quality = "SD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Formula 1 TV",
        logo = "https://imgur.com/a/jyyyawL",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71947.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71949.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/71950.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71947.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71949.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/71950.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71947.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71949.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/71950.ts",
                quality = "SD",
            ),
        ),
        categoria = "PPV",
    ),
    Channel(
        name = "France 24",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103500.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104906.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104942.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104907.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104941.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103500.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104906.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104942.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104907.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104941.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103500.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104906.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104942.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104907.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104941.ts",
                quality = "SD",
            ),
        ),
        categoria = "Notícias",
    ),
    Channel(
        name = "Copa do Brasil 3",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/142040.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/105789.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/142041.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/142040.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/105789.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/142041.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/142040.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/105789.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/142041.ts",
                quality = "SD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Max 4",
        logo = "http://tvonhdbr.com/images/48978cd3e3eb7f9b057b64decd5df5b8.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140507.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/76673.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140513.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140507.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/76673.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140513.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140507.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/76673.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140513.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/594013.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Max 5",
        logo = "http://tvonhdbr.com/images/48978cd3e3eb7f9b057b64decd5df5b8.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140508.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/130196.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140514.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140508.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/130196.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140514.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140508.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/130196.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140514.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/594015.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Max 6",
        logo = "http://tvonhdbr.com/images/48978cd3e3eb7f9b057b64decd5df5b8.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140509.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/130197.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140515.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140509.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/130197.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140515.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140509.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/130197.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140515.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/594017.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Liga Futsal 1",
        logo = "http://tvonhdbr.com/images/96c7b35001b7a7a692e26d0331429b2b.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/73006.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/109225.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/155741.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/73006.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/109225.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/155741.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/73006.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/109225.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/155741.ts",
                quality = "SD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Liga Futsal 2",
        logo = "http://tvonhdbr.com/images/96c7b35001b7a7a692e26d0331429b2b.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/155742.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/155743.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/155744.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/155742.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/155743.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/155744.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/155742.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/155743.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/155744.ts",
                quality = "SD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Liga Futsal 3",
        logo = "http://tvonhdbr.com/images/96c7b35001b7a7a692e26d0331429b2b.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/156663.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/156664.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/156665.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/156663.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/156664.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/156665.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/156663.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/156664.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/156665.ts",
                quality = "SD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Liga Futsal 4",
        logo = "http://tvonhdbr.com/images/96c7b35001b7a7a692e26d0331429b2b.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/156666.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/156667.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/156668.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/156666.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/156667.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/156668.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/156666.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/156667.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/156668.ts",
                quality = "SD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Markket",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/164026.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/164030.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/164029.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/164028.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/164027.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/164026.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/164030.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/164029.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/164028.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/164027.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/164026.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/164030.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/164029.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/164028.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/164027.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "NBA League Pass 1",
        logo = "https://i.imgur.com/ynW8fZF.png",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140516.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140517.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140518.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140516.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140517.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140518.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140516.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140517.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140518.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/598889.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "NBA League Pass 2",
        logo = "https://i.imgur.com/ynW8fZF.png",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140519.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140520.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140521.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140519.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140520.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140521.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140519.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140520.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140521.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/598888.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "NBA League Pass 3",
        logo = "https://i.imgur.com/ynW8fZF.png",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140522.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140523.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140524.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140522.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140523.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140524.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140522.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140523.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140524.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/598887.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "NBA League Pass 4",
        logo = "https://i.imgur.com/ynW8fZF.png",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140525.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140526.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140527.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140525.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140526.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140527.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140525.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140526.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140527.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/598886.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "NBA League Pass 5",
        logo = "https://i.imgur.com/ynW8fZF.png",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/141487.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/141488.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/141489.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/141487.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/141488.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/141489.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/141487.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/141488.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/141489.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/598885.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
)

private fun build_CATALOG_PART_8(): List<Channel> = listOf(
    Channel(
        name = "NBA League Pass 6",
        logo = "https://i.imgur.com/ynW8fZF.png",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/141490.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/141491.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/141492.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/141490.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/141491.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/141492.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/141490.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/141491.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/141492.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/598884.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "SportyNet 4",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184584.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184585.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184586.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/184584.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/184585.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/184586.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/184584.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/184585.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/184586.ts",
                quality = "SD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "SportyNet",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184587.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/184587.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/184587.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1165167.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/sportynetplus3/sportynetplus3.m3u8",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "OneFootball 1",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/185800.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/185801.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/185802.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/185800.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/185801.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/185802.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/185800.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/185801.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/185802.ts",
                quality = "SD",
            ),
        ),
        categoria = "PPV",
    ),
    Channel(
        name = "Paramount + 4",
        logo = "http://tvonhdbr.com/images/a2297e25c35601bed705a676d70e41df.png",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103521.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72989.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72990.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72991.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72992.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103521.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72989.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72990.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72991.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72992.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103521.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72989.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72990.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72991.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72992.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/295699.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Paramount Channel",
        logo = "http://tvonhdbr.com/static/logos/canais/paramount.png",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/90634.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72217.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72218.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72219.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72220.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/90634.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72217.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72218.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72219.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72220.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/90634.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72217.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72218.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72219.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72220.ts",
                quality = "SD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Prime Box Brazil",
        logo = "http://tvonhdbr.com/static/logos/canais/primeboxbrazil.png",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103476.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72267.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72268.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72269.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/72270.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103476.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72267.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72268.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72269.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72270.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103476.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72267.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72268.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72269.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72270.ts",
                quality = "SD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Sabor & Arte",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/112013.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/112017.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/112014.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/112016.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/112015.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/112013.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/112017.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/112014.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/112016.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/112015.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/112013.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/112017.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/112014.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/112016.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/112015.ts",
                quality = "SD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "SYFY",
        logo = "http://tvonhdbr.com/static/logos/canais/syfy.png",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/103474.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/103474.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/103474.ts",
                quality = "4K",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "UFC Fight Pass",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184046.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184047.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/184048.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/184046.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/184047.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/184048.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/184046.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/184047.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/184048.ts",
                quality = "SD",
            ),
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/ufcfightpass/ufcfightpass.m3u8",
            ),
        ),
        categoria = "PPV",
    ),
    Channel(
        name = "Zoomoo",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/178533.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104934.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104937.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104935.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/104936.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/178533.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104934.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104937.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104935.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/104936.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/178533.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104934.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104937.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104935.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/104936.ts",
                quality = "SD",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/9230.ts",
                quality = "HD",
            ),
        ),
        categoria = "Infantil",
    ),
    Channel(
        name = "101 Dalmatas",
        logo = "http://24horas.cc:80/images/9c6a2f33abeeef86c653c2cfb53e30b3.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137614.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137614.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137614.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1169265.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "A Beleza Secreta dos Animais",
        logo = "http://24horas.cc:80/images/841daa83c5587e962d6922d8d873a7e3.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138011.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138011.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138011.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Aladdin",
        logo = "http://24horas.cc:80/images/07b77f17e21144dcbd7933ee82820906.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138730.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138730.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138730.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "As Aventuras do Gato de Botas",
        logo = "http://24horas.cc:80/images/10eb05c9613316dfb3cb1a39efdf13e0.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/140528.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140528.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140528.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Cameras Escondidas Silvio Santos",
        logo = "http://tvonhdbr.com/images/2e635c8ad4f61ac93cc35719e5ec1d6d.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/152014.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/152014.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/152014.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Caverna do Dragao",
        logo = "http://24horas.cc:80/images/7ba1bfdb706316720d0ba2bafbfd7347.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135454.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135454.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135454.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1169296.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1169215.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Chapolin Colorado",
        logo = "http://24horas.cc:80/images/77e8debc90dbfd7e69d9decf09b9e05e.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138000.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138001.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138002.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138003.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138004.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138005.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138006.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138000.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138001.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138002.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138003.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138004.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138005.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138006.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138000.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138001.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138002.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138003.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138004.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138005.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138006.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1169217.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Dois Homens e Meio",
        logo = "http://24horas.cc:80/images/66a6edec30215e33b34d9a1a1fd25393.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137623.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137624.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137625.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137626.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137627.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137628.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137629.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137630.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137631.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137632.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137633.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137634.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137623.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137624.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137625.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137626.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137627.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137628.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137629.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137630.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137631.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137632.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137633.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137634.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137623.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137624.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137625.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137626.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137627.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137628.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137629.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137630.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137631.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137632.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137633.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137634.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1169221.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Dora Aventureira",
        logo = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/j30WV7vDXXorlbdKEHgl74stDml.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/151949.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/151949.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/151949.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1169304.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Dragon Ball Super - Saga Deus da Destruicao Bills",
        logo = "http://24horas.cc:80/images/626e71bf1e9d0b71d194c9359413ced6.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135484.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135484.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135484.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Dragon Ball Super - Trunks do Futuro",
        logo = "http://24horas.cc:80/images/626e71bf1e9d0b71d194c9359413ced6.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135487.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135487.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135487.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Escolinha do Professor Raimundo",
        logo = "http://24horas.cc:80/images/151df67d8d15f1c0c39c7779f27935b6.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137611.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137611.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137611.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Eu a Patroa e as Criancas",
        logo = "http://24horas.cc:80/images/f8d46ebce886f39998603845cd3fc331.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138020.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138021.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138022.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138023.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138024.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138020.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138021.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138022.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138023.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138024.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138020.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138021.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138022.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138023.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138024.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Game Shakers",
        logo = "http://24horas.cc:80/images/e1b30a8cf50dbe4b7da802f634c686b3.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138007.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138008.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/138009.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138007.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138008.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/138009.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138007.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138008.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/138009.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "He Man e Os Defensores do Universo",
        logo = "http://24horas.cc:80/images/66c305ee7bff6c27854be94434b46c7e.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135514.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135514.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135514.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Largados e Pelados",
        logo = "http://24horas.cc:80/images/2246772d199347c5251faaac1a270575.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/145928.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/145929.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/145930.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/145931.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/145932.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/145933.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/145934.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/145935.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/145936.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/145937.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/145938.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/145928.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/145929.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/145930.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/145931.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/145932.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/145933.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/145934.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/145935.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/145936.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/145937.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/145938.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/145928.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/145929.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/145930.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/145931.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/145932.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/145933.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/145934.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/145935.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/145936.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/145937.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/145938.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Mestres da Sobrevivencia",
        logo = "http://24horas.cc:80/images/401f441af8a5f5b0211c085d1d755d95.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/137129.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/137129.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/137129.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Nicky Ricky Dicky e Dawn",
        logo = "http://24horas.cc:80/images/fa42fec0efcb2f9a242913cad45ccba8.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/145939.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/145940.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/145941.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/145942.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/145939.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/145940.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/145941.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/145942.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/145939.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/145940.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/145941.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/145942.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Um Maluco no Pedaco",
        logo = "http://24horas.cc:80/images/3e3d6036a0a8a0f316aff080eca66868.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135445.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135446.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135448.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135449.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135450.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/135451.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135445.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135446.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135448.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135449.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135450.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/135451.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135445.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135446.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135448.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135449.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135450.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/135451.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1169252.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Furacao Live",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/192079.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/192080.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/192081.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/192079.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/192080.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/192079.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/192080.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/192081.ts",
                quality = "SD",
            ),
        ),
        categoria = "PPV",
    ),
    Channel(
        name = "Live TV",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/192625.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/192626.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/192627.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/192628.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/192629.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/192625.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/192626.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/192627.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/192628.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/192629.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/192625.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/192626.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/192627.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/192628.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/192629.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Copa do Brasil 4",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/193253.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/193254.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/193255.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/193253.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/193254.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/193255.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/193253.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/193254.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/193255.ts",
                quality = "SD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "League Cup",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/203509.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/203511.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/203510.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/203509.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/203511.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/203510.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/203509.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/203511.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/203510.ts",
                quality = "HD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "FCF TV 1",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/247976.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/247977.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/247978.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/247977.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/247978.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/247976.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/247977.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/247978.ts",
                quality = "SD",
            ),
        ),
        categoria = "PPV",
    ),
    Channel(
        name = "FCF TV 2",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/247979.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/247980.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/247981.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/247979.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/247980.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/247981.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/247979.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/247980.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/247981.ts",
                quality = "SD",
            ),
        ),
        categoria = "PPV",
    ),
    Channel(
        name = "Carros Irado",
        logo = "http://24horas.cc:80/images/2dd914ca34a6a1ad2bdbb727f3e51140.jpg",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/255224.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/255225.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/255226.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/255227.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/255228.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/255229.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/255230.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/255231.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/255232.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/255224.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/255225.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/255226.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/255227.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/255228.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/255229.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/255230.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/255231.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/255232.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/255224.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/255225.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/255226.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/255227.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/255228.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/255229.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/255230.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/255231.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/255232.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Mr. Olympia TV",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/289535.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/289536.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/289537.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/289535.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/289536.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/289537.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/289535.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/289536.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/289537.ts",
                quality = "SD",
            ),
        ),
        categoria = "PPV",
    ),
    Channel(
        name = "CNBC Brasil",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/295478.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/295479.ts",
                quality = "SD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/295480.ts",
                quality = "4K",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/295481.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/295477.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/295478.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/295479.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/295480.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/295481.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/295477.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/295478.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/295479.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/295480.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/295481.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Notícias",
    ),
    Channel(
        name = "Brasileirao Serie A",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/317063.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/317064.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/317065.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/317063.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/317064.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/317065.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/317063.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/317064.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/317065.ts",
                quality = "SD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "Andorra x Malta",
        logo = "http://tvonhdbr.com/static/logos/canais/jogos.png",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/317686.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/317686.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/317686.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Holanda x Alemanha",
        logo = "http://tvonhdbr.com/static/logos/canais/jogos.png",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/317687.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/317687.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/317687.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Sérvia x Grécia",
        logo = "http://tvonhdbr.com/static/logos/canais/jogos.png",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/317688.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/317688.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/317688.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Noruega x Dinamarca",
        logo = "http://tvonhdbr.com/static/logos/canais/jogos.png",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/317689.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/317689.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/317689.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Portugal x País de Gales",
        logo = "http://tvonhdbr.com/static/logos/canais/jogos.png",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/317690.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/317690.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/317690.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Áustria x Israel",
        logo = "http://tvonhdbr.com/static/logos/canais/jogos.png",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/317691.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/317691.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/317691.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Kosovo x Irlanda",
        logo = "http://tvonhdbr.com/static/logos/canais/jogos.png",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/317692.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/317692.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/317692.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Liechtenstein x Lituânia",
        logo = "http://tvonhdbr.com/static/logos/canais/jogos.png",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/317693.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/317693.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/317693.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Informações em Breve",
        logo = "http://tvonhdbr.com/static/logos/canais/jogos.png",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/317694.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/317695.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/317696.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/318080.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/318081.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/318082.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/318083.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/318084.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/318085.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/318086.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/318606.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/318607.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/318608.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/318609.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/318610.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/318611.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/318612.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/318613.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/361372.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/361373.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/361374.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/362685.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/362686.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/362687.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/362688.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/362826.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/317694.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/317695.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/317696.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/318080.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/318081.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/318082.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/318083.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/318084.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/318085.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/318086.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/318606.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/318607.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/318608.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/318609.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/318610.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/318611.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/318612.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/318613.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/361372.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/361373.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/361374.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/362685.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/362686.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/362687.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/362688.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/362826.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/317694.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/317695.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/317696.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/318080.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/318081.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/318082.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/318083.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/318084.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/318085.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/318086.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/318606.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/318607.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/318608.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/318609.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/318610.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/318611.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/318612.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/318613.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/361372.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/361373.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/361374.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/362685.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/362686.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/362687.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/362688.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/362826.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "Gladiadores da Natureza",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/371456.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/371456.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/371456.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
)

private fun build_CATALOG_PART_9(): List<Channel> = listOf(
    Channel(
        name = "Barretos 2026",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/393722.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/393723.ts",
                quality = "HD",
            ),
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/393724.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/393722.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/393723.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/393724.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/393722.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/393723.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/393724.ts",
                quality = "SD",
            ),
        ),
        categoria = "PPV",
    ),
    Channel(
        name = "A Fazenda Cam 1",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/397317.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/397317.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/397317.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "A Fazenda Cam 2",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/397320.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/397320.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/397320.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "A Fazenda Cam 3",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/397321.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/397321.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/397321.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "A Fazenda Cam 4",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/397322.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/397322.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/397322.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "A Fazenda Cam 5",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/397323.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/397323.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/397323.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "A Fazenda Cam 6",
        sources = listOf(
            Source(
                url = "http://tvonhdbr.com:80/493754/NqCxf1/397324.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/397324.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/397324.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "NOSSO FUTEBOL 01",
        logo = "http://www.imgu.top/logos/nosso-futebol250.png",
        sources = listOf(
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/227505.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "PPV",
    ),
    Channel(
        name = "MAX 07",
        logo = "http://i.postimg.cc/L52gtQpb/BeDFSlD.png",
        sources = listOf(
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/594012.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "MAX 08",
        logo = "http://i.postimg.cc/L52gtQpb/BeDFSlD.png",
        sources = listOf(
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/594014.ts",
                quality = "HD",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "PREMIERE 8 MOSAICO",
        logo = "http://1.bp.blogspot.com/-9NsHz_itoFc/Xk70dxuLv7I/AAAAAAAACGQ/wCExIoJZ8xgbleziCR-Guhr-AkuLgFhVQCLcBGAsYHQ/s1600/premiere_2.png",
        sources = listOf(
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/30063.ts",
                quality = "FHD",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "JOGOS DE HOJE",
        logo = "https://i.postimg.cc/cHHX4vYD/canal-futebol.png",
        sources = listOf(
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/339310.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Esportes",
    ),
    Channel(
        name = "NBA TV",
        logo = "http://i.postimg.cc/hjf15W4k/nbatv.png",
        sources = listOf(
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/598890.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "PPV",
    ),
    Channel(
        name = "NETFLIX LUTAS",
        logo = "http://s2-techtudo.glbimg.com/1PV-HCyKJ4cjdq9c_1Wp7QwU5cE=/0x0:1142x567/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_08fbf48bc0524877943fe86e43087e7a/internal_photos/bs/2024/B/G/QOppYiRA21yAyyaFws0w/jake-paul-mike-tyson-3-.jpg",
        sources = listOf(
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/591951.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "PPV",
    ),
    Channel(
        name = "A FAZENDA 18 MOSAICO",
        logo = "https://upload.wikimedia.org/wikipedia/commons/b/b4/Logo_A_Fazenda_-_Atual.png",
        sources = listOf(
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/2470067.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Variedades",
    ),
    Channel(
        name = "CHAPOLIN",
        logo = "http://32q0d.xyz/images/1ff1de774005f8da13f42943881c655f.png",
        sources = listOf(
            Source(
                url = "http://corruptx.org:80/hfp5ejnrey/zq0u3kzads/1169216.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "24 Horas",
    ),
    Channel(
        name = "Disney Plus 1",
        logo = "https://vipcanais.net/images/disneyplus.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/disneyplus1/disneyplus1.m3u8",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Disney Plus 2",
        logo = "https://vipcanais.net/images/disneyplus.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/disneyplus2/disneyplus2.m3u8",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Disney Plus 3",
        logo = "https://vipcanais.net/images/disneyplus.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/disneyplus3/disneyplus3.m3u8",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Paramount Plus 1",
        logo = "https://vipcanais.net/images/paramountplus.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/paramountplus/paramountplus.m3u8",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Paramount Plus 2",
        logo = "https://vipcanais.net/images/paramountplus.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/paramountplus2/paramountplus2.m3u8",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
    Channel(
        name = "Prime Video 3",
        logo = "https://vipcanais.net/images/amazonprimevideo.png",
        sources = listOf(
            Source(
                url = "https://nexustvplay.bbroot.com/nxs/stream/primevideo3/primevideo3.m3u8",
            ),
        ),
        categoria = "Filmes e Séries",
    ),
)

val CATALOG: List<Channel> = build_CATALOG_PART_0() + build_CATALOG_PART_1() + build_CATALOG_PART_2() + build_CATALOG_PART_3() + build_CATALOG_PART_4() + build_CATALOG_PART_5() + build_CATALOG_PART_6() + build_CATALOG_PART_7() + build_CATALOG_PART_8() + build_CATALOG_PART_9()

/// Só entra na lista depois do código, e só sem rede: a que vale é a
/// baixada pelo Remote. Ver Unlock.
private fun build_RESTRICTED_PART_0(): List<Channel> = listOf(
    Channel(
        name = "Sexy Hot",
        logo = "https://mondrian.claro.com.br/channels/inverse/sexy-hot.png",
        sources = listOf(
            Source(
                url = "https://canais.fazoeli.co.za/fontes/smart/sexyhot.m3u8",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/180157.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/180153.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/180156.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/180154.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72310.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/180157.ts",
                quality = "4K",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/180153.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/180156.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/180154.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72310.ts",
                quality = "SD",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Playboy TV",
        logo = "https://mondrian.claro.com.br/channels/inverse/playboy-tv.png",
        sources = listOf(
            Source(
                url = "https://canais.fazoeli.co.za/fontes/smart/playboy.m3u8",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72221.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72224.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72222.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/72223.ts",
                quality = "SD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72221.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72224.ts",
                quality = "FHD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72222.ts",
                quality = "HD",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/72223.ts",
                quality = "SD",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Anal",
        sources = listOf(
            Source(
                url = "https://cdn.adultiptv.net/anal.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Asian",
        sources = listOf(
            Source(
                url = "https://cdn.adultiptv.net/asian.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Big Ass",
        sources = listOf(
            Source(
                url = "https://cdn.adultiptv.net/bigass.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Big Dick",
        sources = listOf(
            Source(
                url = "https://cdn.adultiptv.net/bigdick.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Big Tits",
        sources = listOf(
            Source(
                url = "https://cdn.adultiptv.net/bigtits.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Blowjob",
        sources = listOf(
            Source(
                url = "https://cdn.adultiptv.net/blowjob.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Compilation",
        sources = listOf(
            Source(
                url = "https://cdn.adultiptv.net/compilation.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Cuckold",
        sources = listOf(
            Source(
                url = "https://cdn.adultiptv.net/cuckold.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Fetish",
        sources = listOf(
            Source(
                url = "https://cdn.adultiptv.net/fetish.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Gangbang",
        sources = listOf(
            Source(
                url = "https://cdn.adultiptv.net/gangbang.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Gay",
        sources = listOf(
            Source(
                url = "https://cdn.adultiptv.net/gay.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Hardcore",
        sources = listOf(
            Source(
                url = "https://cdn.adultiptv.net/hardcore.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Interracial",
        sources = listOf(
            Source(
                url = "https://cdn.adultiptv.net/interracial.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Live Cams",
        sources = listOf(
            Source(
                url = "https://cdn.adultiptv.net/livecams.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Pornstar",
        sources = listOf(
            Source(
                url = "https://cdn.adultiptv.net/pornstar.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "POV",
        sources = listOf(
            Source(
                url = "https://cdn.adultiptv.net/pov.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Rough",
        sources = listOf(
            Source(
                url = "https://cdn.adultiptv.net/rough.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Russian",
        sources = listOf(
            Source(
                url = "https://cdn.adultiptv.net/russian.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Threesome",
        sources = listOf(
            Source(
                url = "https://cdn.adultiptv.net/threesome.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Woman",
        sources = listOf(
            Source(
                url = "https://live.redtraffic.net/woman.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "MyCam Anal",
        sources = listOf(
            Source(
                url = "https://live.mycamtv.com/anal.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "MyCam Asian",
        sources = listOf(
            Source(
                url = "https://live.mycamtv.com/asian.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "MyCam Big Ass",
        sources = listOf(
            Source(
                url = "https://live.mycamtv.com/defstream.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "MyCam Big Tits",
        sources = listOf(
            Source(
                url = "https://live.mycamtv.com/bigtits.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "MyCam Blonde",
        sources = listOf(
            Source(
                url = "https://live.mycamtv.com/blonde.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "MyCam Brunette",
        sources = listOf(
            Source(
                url = "https://live.mycamtv.com/brunette.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "MyCam Latina",
        sources = listOf(
            Source(
                url = "https://live.mycamtv.com/latina.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "MyCam Squirt",
        sources = listOf(
            Source(
                url = "https://live.mycamtv.com/squirt.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "MyCam White",
        sources = listOf(
            Source(
                url = "https://live.mycamtv.com/white.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Jenny Live",
        sources = listOf(
            Source(
                url = "https://59ec5453559f0.streamlock.net/JennyLive/JennyLive/playlist.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Miami TV Mexico",
        sources = listOf(
            Source(
                url = "https://59ec5453559f0.streamlock.net/mexicotv/smil:miamitvmexico/playlist.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "O-la-la!",
        logo = "https://i.imgur.com/6aOmZs4.png",
        sources = listOf(
            Source(
                url = "http://31.148.48.15/O-la-la/index.m3u8",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Playboy TV Latin America",
        logo = "https://i.imgur.com/B3DMUM9.png",
        sources = listOf(
            Source(
                url = "http://190.11.225.124:5000/live/playboy_hd/playlist.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Penthouse TV",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/5010/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Penthouse TV 2",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/5012/index.m3u8",
                quality = "SD · 1024x576",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "21 Sexture",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6164/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "SexArt",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6165/index.m3u8",
                quality = "HD · 1686x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Adult Time",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6166/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "My Cam TV 1",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6167/index.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Analized",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6168/index.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Angel Trans",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6169/index.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Babes",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6171/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Bang Bros",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6173/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Bang",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6174/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Bang Bros 2",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6175/index.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "My Cam TV 2",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6176/index.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "InteRacial",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6177/index.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Blacked",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6178/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
)

private fun build_RESTRICTED_PART_1(): List<Channel> = listOf(
    Channel(
        name = "My Cam TV 3",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6180/index.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Brazzers",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6181/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Brazzers 2",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6182/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "My Cam TV 4",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6183/index.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Cento X Cento",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6184/index.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Cherry Pimps",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6185/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Club Sweethearts",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6186/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "My Cam TV 5",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6187/index.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "My Cam TV 6",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6188/index.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Cum Louder",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6189/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Cum 4K",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6190/index.m3u8",
                quality = "4K · 3840x2160",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Daughter Swap",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6191/index.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Brazzers 3",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6192/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "DDF Network",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6193/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "DDF Network 2",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6194/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "XXX",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6195/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "DP",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6196/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Dorcel Club",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6197/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Deep Lush",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6198/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Evil Angel",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6199/index.m3u8",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "XXX 2",
        sources = listOf(
            Source(
                url = "http://s1w8pqrh.megatv.fun/iptv/VZEVDE3KMBMVGUR9Y2EEL7EF/6200/index.m3u8",
                quality = "HD · 1280x720",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Sex Privé",
        sources = listOf(
            Source(
                url = "https://cdn-mg1.satlabscloud.com.br/SEX_PRIVE/index.m3u8?token=ulDZjn1kAAan1kzoXmUL1B84gijOI6v7",
                quality = "FHD · 1920x1080",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "A Casa das Brasileirinhas",
        logo = "http://24horas.cc:80/images/508f0d666609e140b531f552b60c3478.jpg",
        sources = listOf(
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140487.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140488.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140489.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140490.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140491.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140487.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140488.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140489.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140490.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140491.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "All Anal",
        logo = "http://vaiagora.vip/images/22219a38e43a2a4d58d0dd34c43a14c7.jpg",
        sources = listOf(
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/166035.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/166036.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/166037.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/166038.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/166039.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/166040.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/166041.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/166035.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/166036.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/166037.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/166038.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/166039.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/166040.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/166041.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Anal Only",
        logo = "http://vaiagora.vip/images/22219a38e43a2a4d58d0dd34c43a14c7.jpg",
        sources = listOf(
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/149544.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/149544.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Filmes Adultos",
        logo = "http://24horas.cc:80/images/f859ff8b3b8b08745491656f1dab6019.jpg",
        sources = listOf(
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140536.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140537.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140538.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140539.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140540.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/149545.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/150115.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/150116.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/150492.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/150493.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/155424.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/155425.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/155426.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/155427.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/155428.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/225349.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/225348.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/225347.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/225346.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/225345.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/225344.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/225343.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/225342.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/225341.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/225340.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/225339.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/225338.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/225337.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/225336.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/225335.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/225334.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/225333.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/225332.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140536.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140537.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140538.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140539.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140540.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/149545.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/150115.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/150116.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/150492.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/150493.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/155424.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/155425.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/155426.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/155427.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/155428.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/225349.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/225348.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/225347.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/225346.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/225345.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/225344.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/225343.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/225342.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/225341.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/225340.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/225339.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/225338.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/225337.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/225336.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/225335.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/225334.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/225333.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/225332.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Filmes Trans",
        logo = "http://24horas.cc:80/images/f859ff8b3b8b08745491656f1dab6019.jpg",
        sources = listOf(
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/145382.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/145383.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/149546.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/149547.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/150117.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/155441.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/155442.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/155443.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/155444.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/155445.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/155446.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/155447.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/155448.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/155449.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/155450.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/145382.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/145383.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/149546.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/149547.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/150117.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/155441.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/155442.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/155443.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/155444.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/155445.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/155446.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/155447.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/155448.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/155449.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/155450.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Fuck Studies",
        logo = "http://vaiagora.vip/images/22219a38e43a2a4d58d0dd34c43a14c7.jpg",
        sources = listOf(
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/169085.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/169086.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/169087.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/169085.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/169086.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/169087.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Homossexual Masculino",
        logo = "http://24horas.cc:80/images/23282a88114949d0a31f5fbee6dd0733.jpg",
        sources = listOf(
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140492.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140493.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140492.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140493.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Interesseiras",
        logo = "http://vaiagora.vip/images/22219a38e43a2a4d58d0dd34c43a14c7.jpg",
        sources = listOf(
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/149606.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/149606.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Lets Try Anal",
        logo = "http://24horas.cc:80/images/f859ff8b3b8b08745491656f1dab6019.jpg",
        sources = listOf(
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140760.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/141499.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/141500.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140760.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/141499.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/141500.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "MissaX",
        logo = "http://vaiagora.vip/images/22219a38e43a2a4d58d0dd34c43a14c7.jpg",
        sources = listOf(
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/169088.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/169089.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/169090.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/169091.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/169092.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/169093.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/169094.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/169095.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/169096.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/169088.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/169089.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/169090.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/169091.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/169092.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/169093.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/169094.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/169095.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/169096.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Pornhub Yinyleon",
        logo = "http://vaiagora.vip/images/22219a38e43a2a4d58d0dd34c43a14c7.jpg",
        sources = listOf(
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/149548.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/149548.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Teste de Fudelidade",
        logo = "http://24horas.cc:80/images/3e81163f7309a5a4f01613ff430db818.jpg",
        sources = listOf(
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140761.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/140762.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/145384.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140761.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/140762.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/145384.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Adulto",
    ),
    Channel(
        name = "Tweetney",
        logo = "http://vaiagora.vip/images/22219a38e43a2a4d58d0dd34c43a14c7.jpg",
        sources = listOf(
            Source(
                url = "http://vaiagora.vip:80/5q8VUj/5BeUd8/169097.ts",
                quality = "Qualidade não informada",
            ),
            Source(
                url = "http://vivlar.me:80/497238/KK8Kdy/169097.ts",
                quality = "Qualidade não informada",
            ),
        ),
        categoria = "Adulto",
    ),
)

val RESTRICTED: List<Channel> = build_RESTRICTED_PART_0() + build_RESTRICTED_PART_1()

