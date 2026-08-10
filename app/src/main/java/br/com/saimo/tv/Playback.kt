package br.com.saimo.tv

import android.content.Context
import android.util.Base64
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.okhttp.OkHttpDataSource
import okhttp3.OkHttpClient
import okhttp3.dnsoverhttps.DnsOverHttps
import okhttp3.HttpUrl.Companion.toHttpUrl
import java.net.InetAddress
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.drm.DefaultDrmSessionManager
import androidx.media3.exoplayer.drm.FrameworkMediaDrm
import androidx.media3.exoplayer.drm.LocalMediaDrmCallback
import androidx.media3.exoplayer.hls.HlsMediaSource
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
    private val client: OkHttpClient by lazy {
        val bootstrap = OkHttpClient.Builder().build()
        val doh = DnsOverHttps.Builder()
            .client(bootstrap)
            .url("https://1.1.1.1/dns-query".toHttpUrl())
            .bootstrapDnsHosts(
                InetAddress.getByName("1.1.1.1"),
                InetAddress.getByName("1.0.0.1"))
            .includeIPv6(false)
            .build()
        OkHttpClient.Builder()
            .dns(doh)
            .followRedirects(true)
            .followSslRedirects(true)
            .build()
    }

    fun mediaSource(context: Context, source: Source): MediaSource {
        val http: DataSource.Factory = OkHttpDataSource.Factory(client)
            .setUserAgent(source.userAgent ?: DEFAULT_USER_AGENT)
            .apply {
                // Some CDNs only serve the manifest when a matching Referer is sent.
                source.referer?.let { setDefaultRequestProperties(mapOf("Referer" to it)) }
            }

        val item = MediaItem.fromUri(source.url)
        return if (source.isDash) {
            DashMediaSource.Factory(http)
                .setDrmSessionManagerProvider { clearKeyManager(source) }
                .createMediaSource(item)
        } else {
            HlsMediaSource.Factory(http)
                .setAllowChunklessPreparation(true)
                .createMediaSource(item)
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
