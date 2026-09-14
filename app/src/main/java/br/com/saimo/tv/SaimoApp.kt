package br.com.saimo.tv

import android.app.Application
import android.util.Log
import androidx.media3.common.util.UnstableApi
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import kotlinx.coroutines.CoroutineExceptionHandler

/**
 * Para as tarefas que correm por fora do vídeo: guia, catálogo, capas e
 * atualização.
 *
 * Sem ele, qualquer tropeço numa delas — um site que mudou o HTML, um TV Box
 * sem um método do Java — ia direto para o tratador da thread e fechava o app.
 * Foi assim na 1.5: o guia novo chamava um método que o Android 5 e 6 não têm,
 * e a TV fechava dez segundos depois de abrir. Perder o guia é bem menos grave
 * que perder o canal.
 */
val semDerrubar = CoroutineExceptionHandler { _, erro ->
    Log.w("SaimoTV", "tarefa em segundo plano falhou", erro)
    Telemetria.erro(erro)
}

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

    override fun onCreate() {
        super.onCreate()
        Telemetria.iniciar(this)
    }

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
