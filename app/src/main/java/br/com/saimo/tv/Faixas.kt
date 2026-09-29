package br.com.saimo.tv

import android.app.Activity
import androidx.media3.common.C
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import java.util.Locale

/**
 * Áudio, legenda e qualidade, nos painéis de TV — para canal e para filme.
 *
 * Substitui o diálogo do Media3, que abria outro diálogo dentro dele, com
 * letra de celular e rádio minúsculo.
 */
@UnstableApi
object Faixas {

    fun audioELegenda(
        tela: Activity, p: ExoPlayer, aoMudarEstilo: () -> Unit = {},
        /// Legendas do OpenSubtitles do que está tocando, a escolhida e o atraso dela.
        externas: List<Legendas.Opcao> = emptyList(),
        escolhida: Legendas.Opcao? = null,
        atraso: Double = 0.0,
        aoEscolherExterna: (Legendas.Opcao?) -> Unit = {},
        aoAjustarAtraso: (Double) -> Unit = {},
    ) {
        val itens = mutableListOf<Painel.Item>()
        val audios = p.currentTracks.groups.filter { it.type == C.TRACK_TYPE_AUDIO }
        if (audios.size > 1) {
            for (grupo in audios) {
                val formato = grupo.getTrackFormat(0)
                itens += Painel.Item(tela.getString(R.string.player_audio_item, idioma(tela, formato.language, formato.label)),
                    formato.channelCount.takeIf { it > 2 }?.let { "$it canais" },
                    marcado = grupo.isSelected) {
                    p.trackSelectionParameters = p.trackSelectionParameters.buildUpon()
                        .setOverrideForType(TrackSelectionOverride(grupo.mediaTrackGroup, 0)).build()
                }
            }
        }
        // A faixa da legenda externa aparece no player como qualquer outra; ela
        // tem lista própria logo abaixo, então sai daqui para não se repetir.
        val legendas = p.currentTracks.groups.filter { grupo ->
            grupo.type == C.TRACK_TYPE_TEXT &&
                (0 until grupo.length).none { grupo.getTrackFormat(it).id == Legendas.ID_FAIXA }
        }
        val semLegenda = escolhida == null && (
            p.trackSelectionParameters.disabledTrackTypes.contains(C.TRACK_TYPE_TEXT) ||
                legendas.none { it.isSelected })
        if (legendas.isNotEmpty() || externas.isNotEmpty()) {
            itens += Painel.Item(tela.getString(R.string.player_sem_legenda), marcado = semLegenda) {
                Preferencias.legendaIdioma = ""
                Preferencias.legendaExterna = ""
                aoEscolherExterna(null)
                p.trackSelectionParameters = p.trackSelectionParameters.buildUpon()
                    .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, true).build()
            }
        }
        for (grupo in legendas) {
            val formato = grupo.getTrackFormat(0)
            itens += Painel.Item(tela.getString(R.string.player_legenda_item, idioma(tela, formato.language, formato.label)),
                marcado = grupo.isSelected && !semLegenda) {
                Preferencias.legendaIdioma = formato.language.orEmpty()
                p.trackSelectionParameters = p.trackSelectionParameters.buildUpon()
                    .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, false)
                    .setOverrideForType(TrackSelectionOverride(grupo.mediaTrackGroup, 0)).build()
            }
        }
        for (opcao in externas) {
            itens += Painel.Item(tela.getString(R.string.player_legenda_item, opcao.rotulo),
                marcado = escolhida?.id == opcao.id) {
                Preferencias.legendaIdioma = ""
                aoEscolherExterna(opcao)
            }
        }
        if (escolhida != null) {
            itens += Painel.Item(tela.getString(R.string.player_sincronia_legenda), rotuloAtraso(tela, atraso)) {
                val passos = listOf(-5.0, -2.0, -1.0, -0.5, 0.0, 0.5, 1.0, 2.0, 5.0)
                Painel.mostrar(tela, tela.getString(R.string.player_sincronia_legenda), passos.map { passo ->
                    Painel.Item(rotuloAtraso(tela, passo), marcado = passo == atraso) { aoAjustarAtraso(passo) }
                })
            }
        }
        val tamanhos = tela.resources.getStringArray(R.array.tamanhos_legenda)
        itens += Painel.Item(tela.getString(R.string.player_tamanho_legenda),
            tamanhos[Preferencias.legenda.coerceIn(tamanhos.indices)]) {
            Painel.mostrar(tela, tela.getString(R.string.player_tamanho_legenda), tamanhos.mapIndexed { i, nome ->
                Painel.Item(nome, marcado = i == Preferencias.legenda) { Preferencias.legenda = i; aoMudarEstilo() }
            })
        }
        Painel.mostrar(tela, tela.getString(R.string.player_audio_legenda), itens,
            if (audios.size <= 1 && legendas.isEmpty() && externas.isEmpty()) tela.getString(R.string.faixas_so_uma) else null)
    }

    private fun rotuloAtraso(tela: Activity, segundos: Double): String {
        val numero = Math.abs(segundos).toString().removeSuffix(".0").replace('.', ',')
        return when {
            segundos == 0.0 -> tela.getString(R.string.player_sincronia_original)
            segundos > 0 -> tela.getString(R.string.player_sincronia_atrasar, numero)
            else -> tela.getString(R.string.player_sincronia_adiantar, numero)
        }
    }

    fun qualidade(tela: Activity, p: ExoPlayer) {
        val alturas = p.currentTracks.groups.filter { it.type == C.TRACK_TYPE_VIDEO }
            .flatMap { g -> (0 until g.length).map { g.getTrackFormat(it).height } }
            .filter { it > 0 }.distinct().sortedDescending()
        val limite = p.trackSelectionParameters.maxVideoHeight
        val itens = mutableListOf(Painel.Item(tela.getString(R.string.player_qualidade_auto),
            tela.getString(R.string.player_qualidade_auto_dica), marcado = limite == Int.MAX_VALUE) {
            p.trackSelectionParameters = p.trackSelectionParameters.buildUpon().clearVideoSizeConstraints().build()
        })
        for (altura in alturas) {
            itens += Painel.Item(rotuloAltura(altura), marcado = limite == altura) {
                p.trackSelectionParameters = p.trackSelectionParameters.buildUpon()
                    .setMaxVideoSize(Int.MAX_VALUE, altura).build()
            }
        }
        if (alturas.size <= 1) itens += Painel.Item(tela.getString(R.string.player_qualidade_unica)) {}
        Painel.mostrar(tela, tela.getString(R.string.player_qualidade), itens)
    }

    fun rotuloAltura(altura: Int) = when {
        altura >= 2000 -> "4K"
        altura >= 1000 -> "Full HD (1080p)"
        altura >= 700 -> "HD (720p)"
        else -> "${altura}p"
    }

    private fun idioma(tela: Activity, codigo: String?, rotulo: String?): String {
        if (!rotulo.isNullOrBlank()) return rotulo
        if (codigo.isNullOrBlank() || codigo == "und") return tela.getString(R.string.player_idioma_padrao)
        return Locale(codigo).getDisplayLanguage(Locale("pt", "BR")).replaceFirstChar { it.uppercase() }
    }
}
