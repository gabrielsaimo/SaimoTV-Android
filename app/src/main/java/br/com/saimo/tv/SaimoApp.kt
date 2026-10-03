package br.com.saimo.tv

import android.app.Application
import android.util.Log
import androidx.media3.common.util.UnstableApi
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.bitmapConfig
import coil3.request.crossfade
import okio.Path.Companion.toOkioPath
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
class SaimoApp : Application(), SingletonImageLoader.Factory {

    override fun onCreate() {
        super.onCreate()
        Preferencias.iniciar(this)
        Vr.iniciar(this)
        Aparelho.conhecer(this)
        Telemetria.iniciar(this)
        Lembretes.iniciar(this)
    }

    /**
     * O sistema avisa que a memória está acabando antes de matar alguém. Soltar
     * as imagens guardadas aqui é o que tira este app da frente da fila: elas
     * voltam do disco na próxima rolagem, sem ir à rede.
     */
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level >= TRIM_MEMORY_RUNNING_LOW) {
            runCatching { SingletonImageLoader.get(this).memoryCache?.clear() }
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader =
        ImageLoader.Builder(context)
            .components {
                add(OkHttpNetworkFetcherFactory(callFactory = {
                    Playback.client.newBuilder()
                        // Capa vem toda do mesmo servidor, e o padrão do OkHttp é
                        // cinco pedidos por servidor: doze capas na tela chegavam
                        // em três levas. O vídeo usa outro cliente e não disputa.
                        .dispatcher(okhttp3.Dispatcher().apply {
                            maxRequests = 48
                            maxRequestsPerHost = 16
                        })
                        .addInterceptor { chain ->
                            // Alguns CDNs de pôster recusam cliente sem User-Agent.
                            chain.proceed(
                                chain.request().newBuilder()
                                    .header("User-Agent", Playback.DEFAULT_USER_AGENT)
                                    .build())
                        }
                        .build()
                }))
            }
            // Um TV Box tem pouca RAM: um teto explícito evita que a rolagem da
            // lista empurre o player para fora da memória.
            // Num Fire TV de 1 GB o teto cai à metade: logo e capa voltam do
            // disco sem rede, e o que sobra de memória fica para o vídeo.
            .memoryCache {
                // Teto em bytes, não em porcentagem: com largeHeap a porcentagem
                // vira 40 MB de capas num aparelho de 1 GB, e o sistema mata o app.
                MemoryCache.Builder()
                    .maxSizeBytes(if (Aparelho.poucaMemoria) 18 * 1024 * 1024 else 64 * 1024 * 1024)
                    .build()
            }
            // Em disco, as imagens sobrevivem ao reinício e a grade abre cheia.
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("imagens").toOkioPath())
                    .maxSizeBytes(160L * 1024 * 1024)
                    .build()
            }
            // Metade da memória por capa nos aparelhos de 1 GB: numa capa de
            // pôster a diferença de cor não se vê do sofá.
            .bitmapConfig(if (Aparelho.poucaMemoria) android.graphics.Bitmap.Config.RGB_565
                          else android.graphics.Bitmap.Config.ARGB_8888)
            .crossfade(false)
            .build()
}
