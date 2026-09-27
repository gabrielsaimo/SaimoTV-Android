package br.com.saimo.tv

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.media3.common.util.UnstableApi
import androidx.tvprovider.media.tv.PreviewChannel
import androidx.tvprovider.media.tv.PreviewChannelHelper
import androidx.tvprovider.media.tv.PreviewProgram
import androidx.tvprovider.media.tv.TvContractCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * A fileira "Em alta no Saimo" na tela inicial do Android TV.
 *
 * As mesmas capas da primeira fileira do acervo, publicadas no sistema: quem
 * liga a TV já vê o que tem de novo, e um OK abre a ficha do título. Refeita
 * no máximo uma vez a cada doze horas, que é quanto os destaques duram.
 *
 * Aparelho sem o provedor de canais (Android antigo, alguns TV Box) ignora.
 */
@UnstableApi
object CanalNaTv {

    private const val PREFS = "canal_na_tv"
    private const val VALIDADE_MS = 12 * 60 * 60 * 1000L

    suspend fun publicar(context: Context) = withContext(Dispatchers.IO) {
        if (Build.VERSION.SDK_INT < 26) return@withContext
        if (!context.packageManager.hasSystemFeature("android.software.leanback")) return@withContext
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        if (System.currentTimeMillis() - prefs.getLong("quando", 0L) < VALIDADE_MS) return@withContext
        runCatching {
            val fila = Destaques.filas(context).firstOrNull() ?: return@withContext
            val ajudante = PreviewChannelHelper(context)
            var canal = prefs.getLong("canal", -1L)
            if (canal < 0 || runCatching { ajudante.getPreviewChannel(canal) }.getOrNull() == null) {
                val logo = BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher)
                canal = ajudante.publishDefaultChannel(
                    PreviewChannel.Builder()
                        .setDisplayName(fila.titulo)
                        .setAppLinkIntentUri(Uri.parse("saimo://inicio"))
                        .setLogo(logo)
                        .build())
                if (canal < 0) return@withContext
                prefs.edit().putLong("canal", canal).apply()
            }
            // Refaz os cartões: apaga os velhos e publica os de hoje.
            context.contentResolver.delete(TvContractCompat.buildPreviewProgramsUriForChannel(canal), null, null)
            fila.itens.take(20).forEachIndexed { i, item ->
                val alvo = Alvo(item.titulo, item.serie, item.letra, item.ano,
                    if (item.daColecao) item.colecao else "")
                ajudante.publishPreviewProgram(
                    PreviewProgram.Builder()
                        .setChannelId(canal)
                        .setType(if (item.serie) TvContractCompat.PreviewPrograms.TYPE_TV_SERIES
                                 else TvContractCompat.PreviewPrograms.TYPE_MOVIE)
                        .setTitle(Generos.semAno(item.titulo))
                        .setWeight(1000 - i)
                        .setIntentUri(Links.ficha(alvo))
                        .setInternalProviderId(item.titulo)
                        .apply {
                            if (item.capa.isNotBlank()) {
                                setPosterArtUri(Uri.parse(item.capa))
                                setPosterArtAspectRatio(TvContractCompat.PreviewPrograms.ASPECT_RATIO_MOVIE_POSTER)
                            }
                            item.ano.toIntOrNull()?.let { setReleaseDate(it.toString()) }
                        }
                        .build())
            }
            prefs.edit().putLong("quando", System.currentTimeMillis()).apply()
        }.onFailure { Telemetria.erro(it) }
    }
}
