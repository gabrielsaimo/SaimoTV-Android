package br.com.saimo.tv

import android.content.Context
import android.net.Uri
import android.util.Base64
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.ResolvingDataSource
import androidx.media3.datasource.okhttp.OkHttpDataSource
import okhttp3.OkHttpClient
import okhttp3.dnsoverhttps.DnsOverHttps
import okhttp3.HttpUrl.Companion.toHttpUrl
import java.net.InetAddress
import java.net.URI
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.drm.DefaultDrmSessionManager
import androidx.media3.exoplayer.drm.FrameworkMediaDrm
import androidx.media3.exoplayer.drm.LocalMediaDrmCallback
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.source.MediaSource

/**
 * Turns a catalog source into something ExoPlayer can play.
 *
 * The macOS build needs a local proxy and an embedded ffmpeg because
 * AVFoundation refuses DASH outright and decodes HEVC only inside fMP4. None of
 * that applies here: ExoPlayer speaks DASH with ClearKey and HEVC-in-TS
 * natively, so every channel is played straight from its origin.
 */
@UnstableApi
object Playback {

    /**
     * One OkHttp client for everything, resolving names over DNS-over-HTTPS.
     *
     * Some networks filter the resolver and the signed hosts simply do not
     * resolve — that is what forced a DoH fallback into the macOS build. OkHttp
     * ships DoH, so here it is a few lines instead of a hand-written resolver.
     */
    internal val client: OkHttpClient by lazy {
        // As raízes embutidas valem para o próprio DoH também: o certificado do
        // 1.1.1.1 é de uma autoridade que Android antigo não conhece.
        val bootstrap = OkHttpClient.Builder()
            .sslSocketFactory(Confianca.fabrica, Confianca.gerente)
            .build()
        val doh = DnsOverHttps.Builder()
            .client(bootstrap)
            .url("https://1.1.1.1/dns-query".toHttpUrl())
            .bootstrapDnsHosts(
                InetAddress.getByName("1.1.1.1"),
                InetAddress.getByName("1.0.0.1"))
            .includeIPv6(false)
            .build()
        OkHttpClient.Builder()
            .sslSocketFactory(Confianca.fabrica, Confianca.gerente)
            .dns(NomeValido.dns(Resolvedor(doh)))
            .addInterceptor(NomeValido.interceptor)
            .followRedirects(true)
            .followSslRedirects(true)
            .build()
    }

    /**
     * DNS-over-HTTPS primeiro, o resolvedor do sistema quando ele falha.
     *
     * Só DoH era tudo ou nada: bastava o 1.1.1.1 não responder — rede que
     * bloqueia, certificado que o aparelho não aceita — para nenhum nome
     * resolver e todos os canais caírem em "indisponível", mesmo com a
     * internet funcionando para o resto. Depois de algumas vezes em que o
     * sistema achou o que o DoH não achou, o DoH sai de cena até o app fechar,
     * para não pagar a espera dele a cada troca de canal.
     */
    private class Resolvedor(private val doh: okhttp3.Dns) : okhttp3.Dns {
        @Volatile private var derrotas = 0

        override fun lookup(hostname: String): List<InetAddress> {
            if (derrotas >= 3) return okhttp3.Dns.SYSTEM.lookup(hostname)
            return try {
                doh.lookup(hostname).also { derrotas = 0 }
            } catch (falha: java.net.UnknownHostException) {
                val doSistema = runCatching { okhttp3.Dns.SYSTEM.lookup(hostname) }.getOrNull()
                    ?: throw falha
                derrotas++
                doSistema
            }
        }
    }

    /**
     * Faz caber um host que o Java recusa.
     *
     * Alguns links vêm num host cujo primeiro rótulo é só underscore (63
     * deles). Underscore não é nome de domínio válido: o `SNIHostName` recusa
     * antes de qualquer byte sair — "Invalid input to toASCII" — e o canal
     * nunca abre, embora o CDN responda normalmente a quem consegue falar com
     * ele.
     *
     * O pedido sai então para o domínio de verdade, que é o que sobra depois de
     * tirar os rótulos inválidos, com o nome inteiro no cabeçalho `Host` — é
     * por ele que o CDN roteia. E como o domínio encurtado não tem endereço
     * próprio no DNS, a busca continua sendo feita pelo nome original.
     */
    private object NomeValido {

        /// Encurtado -> original, preenchido pelo interceptor antes de o DNS
        /// ser consultado para aquela chamada.
        private val nomes = java.util.concurrent.ConcurrentHashMap<String, String>()

        /// O que sobra tirando os rótulos que o DNS não aceita. Nulo quando o
        /// host já é válido, que é o caso de todos os outros.
        private fun encurtar(host: String): String? {
            val partes = host.split(".")
            if (partes.none { rotulo -> rotulo.any { it == '_' } }) return null
            val bons = partes.filterNot { rotulo -> rotulo.any { it == '_' } }
            return if (bons.size >= 2) bons.joinToString(".") else null
        }

        val interceptor = okhttp3.Interceptor { chain ->
            val pedido = chain.request()
            val host = pedido.url.host
            val curto = encurtar(host) ?: return@Interceptor chain.proceed(pedido)
            nomes[curto] = host
            val resposta = chain.proceed(
                pedido.newBuilder()
                    .url(pedido.url.newBuilder().host(curto).build())
                    // Sem isto o CDN devolve 404: o roteamento é pelo nome
                    // completo, não pelo domínio.
                    .header("Host", host)
                    .build())
            // response.request() devolvendo a URL já encurtada é o que
            // quebrava a playlist ao vivo: o HLS recarrega usando a última
            // URL que viu, e "null-null.shop" sozinho não tem "_" nenhum —
            // o encurtamento não roda de novo, o Host some, e a segunda
            // requisição (poucos segundos depois) leva 404 mesmo o link
            // continuando válido. Devolvendo o pedido original aqui, quem
            // recarrega sempre vê o host completo e passa pelo mesmo caminho.
            resposta.newBuilder().request(pedido).build()
        }

        fun dns(base: okhttp3.Dns) = object : okhttp3.Dns {
            override fun lookup(hostname: String): List<InetAddress> =
                base.lookup(nomes[hostname] ?: hostname)
        }
    }

    fun mediaSource(context: Context, source: Source): MediaSource {
        val embedPlayer = runCatching {
            Uri.parse(source.url).host?.equals("embedplayer2.xyz", ignoreCase = true) == true
        }.getOrDefault(false)
        val cabecalhos = linkedMapOf<String, String>()
        source.referer?.let { cabecalhos["Referer"] = it }
        // Os segmentos de vídeo vêm de plosia*.xyz mascarados como arquivos
        // web e são publicados para a origem do player. Enviar a origem do
        // master também deixa o pedido consistente em aparelhos nos quais o
        // CDN aplica a validação de hotlink.
        if (embedPlayer && source.referer == null) {
            cabecalhos["Referer"] = "https://embedplayer2.xyz/"
            cabecalhos["Origin"] = "https://embedplayer2.xyz"
        }
        val upstream: DataSource.Factory = OkHttpDataSource.Factory(client)
            .setUserAgent(source.userAgent ?: DEFAULT_USER_AGENT)
            .apply {
                // Some CDNs only serve the manifest when a matching Referer is sent.
                if (cabecalhos.isNotEmpty()) setDefaultRequestProperties(cabecalhos)
            }
        val http: DataSource.Factory = ResolvingDataSource.Factory(upstream) { dataSpec ->
            val original = dataSpec.uri.toString()
            val resolved = corrigirCaminhoDaPlaylist(source.url, original)
            if (resolved == original) dataSpec else dataSpec.withUri(Uri.parse(resolved))
        }

        val item = MediaItem.fromUri(source.url)
        return when {
            source.isDash -> DashMediaSource.Factory(http)
                .setDrmSessionManagerProvider { clearKeyManager(source) }
                .createMediaSource(item)
            source.isHls ->
                HlsMediaSource.Factory(http)
                    .setAllowChunklessPreparation(true)
                    .createMediaSource(item)
            // Fluxo MPEG-TS cru, servido direto e não por playlist. Metade das
            // listas de IPTV é assim, muitas vezes sem extensão nenhuma na URL.
            // Mandá-lo para o HLS é pedir uma playlist a quem só tem vídeo: o
            // canal não abria no TV Box e abria no Mac, que passa pelo ffmpeg.
            else -> ProgressiveMediaSource.Factory(http).createMediaSource(item)
        }
    }

    /**
     * Corrige segmentos relativos de proxies cuja playlist aponta para outra pasta.
     *
     * Exemplo real: a playlist chega em `/tos-.../proxy.m3u8`, mas o parâmetro
     * `url=https://origem/docs/amc/__index.m3u8` informa que os segmentos vivem
     * em `/docs/amc/`. O ExoPlayer segue a regra HLS e tenta o segmento ao lado
     * da URL externa; esse CDN, porém, exige a pasta indicada pelo `url=`. O Mac
     * já faz a mesma correção no proxy local.
     *
     * Só pedidos relativos ao diretório da playlist são alterados. A própria
     * playlist, URLs absolutas de outros hosts e fontes comuns ficam intactas.
     */
    internal fun corrigirCaminhoDaPlaylist(origem: String, pedido: String): String {
        val manifest = runCatching { URI.create(origem) }.getOrNull() ?: return pedido
        val request = runCatching { URI.create(pedido) }.getOrNull() ?: return pedido
        val manifestPath = manifest.rawPath ?: return pedido
        val requestPath = request.rawPath ?: return pedido

        // A playlist ao vivo é recarregada periodicamente e deve conservar sua
        // query assinada. Só filhos relativos dela precisam da base alternativa.
        if (requestPath == manifestPath) return pedido
        if (manifest.scheme != request.scheme || manifest.rawAuthority != request.rawAuthority) {
            return pedido
        }

        val nestedValue = manifest.rawQuery
            ?.split('&')
            ?.firstOrNull { it.startsWith("url=") }
            ?.substringAfter("url=")
            ?: return pedido
        val nested = runCatching {
            URI.create(URLDecoder.decode(nestedValue, StandardCharsets.UTF_8.name()))
        }.getOrNull() ?: return pedido
        val nestedPath = nested.rawPath ?: return pedido

        val manifestDir = manifestPath.substringBeforeLast('/', "") + "/"
        val nestedDir = nestedPath.substringBeforeLast('/', "") + "/"
        if (!requestPath.startsWith(manifestDir)) return pedido

        val correctedPath = nestedDir + requestPath.removePrefix(manifestDir)
        return buildString {
            append(request.scheme)
            append("://")
            append(request.rawAuthority)
            append(correctedPath)
            request.rawQuery?.let { append('?').append(it) }
            request.rawFragment?.let { append('#').append(it) }
        }
    }

    /**
     * ClearKey licences are handed over locally — the key already lives in the
     * catalog, so there is no licence server to call.
     */
    private fun clearKeyManager(source: Source): DefaultDrmSessionManager {
        val keyId = source.keyId
        val key = source.key
        val callback = if (keyId != null && key != null) {
            LocalMediaDrmCallback(clearKeyJson(keyId, key).toByteArray())
        } else {
            LocalMediaDrmCallback(ByteArray(0))
        }
        return DefaultDrmSessionManager.Builder()
            .setUuidAndExoMediaDrmProvider(C.CLEARKEY_UUID, FrameworkMediaDrm.DEFAULT_PROVIDER)
            .setMultiSession(false)
            .build(callback)
    }

    /** The W3C ClearKey format wants base64url of the raw bytes, not the hex. */
    private fun clearKeyJson(keyIdHex: String, keyHex: String): String {
        val kid = base64Url(keyIdHex)
        val k = base64Url(keyHex)
        return """{"keys":[{"kty":"oct","kid":"$kid","k":"$k"}],"type":"temporary"}"""
    }

    private fun base64Url(hex: String): String {
        val clean = hex.replace("-", "").trim()
        val bytes = ByteArray(clean.length / 2) { i ->
            clean.substring(i * 2, i * 2 + 2).toInt(16).toByte()
        }
        return Base64.encodeToString(bytes, Base64.NO_PADDING or Base64.NO_WRAP or Base64.URL_SAFE)
    }

    const val DEFAULT_USER_AGENT =
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 " +
            "(KHTML, like Gecko) Version/18.0 Safari/605.1.15"
}
