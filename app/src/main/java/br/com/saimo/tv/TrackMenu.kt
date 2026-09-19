package br.com.saimo.tv

import android.app.Activity
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.TrackSelectionDialogBuilder

/**
 * Um único ponto de entrada para as faixas do HLS, usado tanto nos canais
 * quanto em filmes e episódios. O diálogo do Media3 enxerga variantes HLS,
 * áudios alternativos e WebVTT sem depender da extensão dos segmentos.
 */
@UnstableApi
object TrackMenu {
    fun show(activity: Activity, player: ExoPlayer) {
        val opcoes = listOf(
            Triple(activity.getString(R.string.faixa_qualidade), C.TRACK_TYPE_VIDEO, true),
            Triple(activity.getString(R.string.faixa_audio), C.TRACK_TYPE_AUDIO, false),
            Triple(activity.getString(R.string.faixa_legendas), C.TRACK_TYPE_TEXT, false),
        ).filter { (_, tipo, _) ->
            player.currentTracks.groups.any { it.type == tipo && it.length > 0 }
        }
        if (opcoes.isEmpty()) return
        android.app.AlertDialog.Builder(activity)
            .setTitle(R.string.faixas_titulo)
            .setItems(opcoes.map { it.first }.toTypedArray()) { _, indice ->
                val (titulo, tipo, adaptativa) = opcoes[indice]
                TrackSelectionDialogBuilder(activity, titulo, player, tipo)
                    .setAllowAdaptiveSelections(adaptativa)
                    .setShowDisableOption(tipo == C.TRACK_TYPE_TEXT)
                    .build()
                    .show()
            }
            .show()
    }
}

/** Mostra a dimensão realmente decodificada, não uma qualidade presumida pela URL. */
fun rotuloResolucao(largura: Int, altura: Int): String {
    if (largura <= 0 || altura <= 0) return ""
    val classe = when {
        largura >= 3800 || altura >= 2100 -> "4K"
        altura >= 1400 -> "${altura}p"
        altura >= 1000 -> "Full HD"
        altura >= 700 -> "HD"
        else -> "${altura}p"
    }
    return "${largura}×${altura} · $classe"
}
