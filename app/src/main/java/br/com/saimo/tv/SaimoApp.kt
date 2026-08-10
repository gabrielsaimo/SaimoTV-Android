package br.com.saimo.tv

import android.app.Application
import androidx.media3.common.util.UnstableApi
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache

/**
 * Application-wide image loading.
 *
 * Without this, Coil builds its own HTTP stack: logos and posters would go
 * through the system resolver while the video goes over DNS-over-HTTPS, so on a
 * filtered network the picture plays and the artwork silently never arrives.
 * Sharing the player's client keeps both on the same path.
 */
@UnstableApi
class SaimoApp : Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader =
        ImageLoader.Builder(this)
            .okHttpClient {
                Playback.client.newBuilder()
                    .addInterceptor { chain ->
                        // Alguns CDNs de pôster recusam cliente sem User-Agent.
                        chain.proceed(
                            chain.request().newBuilder()
                                .header("User-Agent", Playback.DEFAULT_USER_AGENT)
                                .build())
                    }
                    .build()
            }
            // Um TV Box tem pouca RAM: um teto explícito evita que a rolagem da
            // lista empurre o player para fora da memória.
            .memoryCache { MemoryCache.Builder(this).maxSizePercent(0.15).build() }
            // Em disco, as imagens sobrevivem ao reinício e a grade abre cheia.
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("imagens"))
                    .maxSizeBytes(48L * 1024 * 1024)
                    .build()
            }
            .crossfade(false)
            .respectCacheHeaders(false)
            .build()
}
