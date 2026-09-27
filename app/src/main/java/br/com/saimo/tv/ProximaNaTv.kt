package br.com.saimo.tv

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.media3.common.util.UnstableApi
import androidx.tvprovider.media.tv.PreviewChannelHelper
import androidx.tvprovider.media.tv.TvContractCompat
import androidx.tvprovider.media.tv.WatchNextProgram
import java.util.concurrent.Executors

/**
 * O "Continuar assistindo" da tela inicial do Google TV.
 *
 * O sistema tem uma fileira própria, acima de todos os apps, com o que a
 * pessoa estava vendo. Publicar ali é o que faz o filme pela metade aparecer
 * logo que a TV liga — um OK e ele volta, sem abrir o Saimo e procurar.
 *
 * Só existe do Android 8 em diante e só em aparelho com o provedor de TV; em
 * qualquer outro, cada chamada aqui simplesmente não faz nada.
 */
@UnstableApi
object ProximaNaTv {

    private val fila = Executors.newSingleThreadExecutor()
    private const val PREFS = "proxima_na_tv"
    private var ultimaVez = 0L

    private fun disponivel(context: Context) =
        Build.VERSION.SDK_INT >= 26 &&
            context.packageManager.hasSystemFeature("android.software.leanback")

    fun atualizar(context: Context, r: Titulos.Resolvido, ep: Titulos.Ep?, posicao: Long, duracao: Long) {
        if (!disponivel(context) || duracao <= 0) return
        // A cada meio minuto basta: o sistema só mostra a barrinha.
        val agora = System.currentTimeMillis()
        if (agora - ultimaVez < 30_000 && posicao < duracao - 120_000) return
        ultimaVez = agora
        val app = context.applicationContext
        fila.execute {
            runCatching { publicar(app, r, ep, posicao, duracao) }
        }
    }

    private fun publicar(context: Context, r: Titulos.Resolvido, ep: Titulos.Ep?, posicao: Long, duracao: Long) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        // Um cartão por título: na série, o cartão anda de episódio em episódio.
        val chave = r.nomeChave + if (r.serie) "|s" else "|f"
        val existente = prefs.getLong(chave, -1L)
        val ajudante = PreviewChannelHelper(context)
        val terminou = posicao > duracao - 120_000

        var alvoEp = ep
        var tipo = TvContractCompat.WatchNextPrograms.WATCH_NEXT_TYPE_CONTINUE
        if (terminou) {
            val seguinte = ep?.let { r.seguinte(it) }
            if (seguinte == null) {
                if (existente >= 0) {
                    context.contentResolver.delete(TvContractCompat.buildWatchNextProgramUri(existente), null, null)
                    prefs.edit().remove(chave).apply()
                }
                return
            }
            alvoEp = seguinte
            tipo = TvContractCompat.WatchNextPrograms.WATCH_NEXT_TYPE_NEXT
        }

        val capa = Generos.capa(r.alvo.nomeCompleto, r.serie) ?: Generos.capa(r.alvo.titulo, r.serie)
        val programa = WatchNextProgram.Builder()
            .setType(if (r.serie) TvContractCompat.WatchNextPrograms.TYPE_TV_EPISODE
                     else TvContractCompat.WatchNextPrograms.TYPE_MOVIE)
            .setWatchNextType(tipo)
            .setLastEngagementTimeUtcMillis(System.currentTimeMillis())
            .setTitle(Generos.semAno(r.alvo.titulo))
            .setIntentUri(Links.assistir(r.alvo, alvoEp?.temporada ?: 0, alvoEp?.numero ?: 0))
            .setInternalProviderId(chave)
            .apply {
                if (capa != null) {
                    setPosterArtUri(Uri.parse(capa))
                    setPosterArtAspectRatio(TvContractCompat.PreviewPrograms.ASPECT_RATIO_MOVIE_POSTER)
                }
                if (alvoEp != null) {
                    setEpisodeTitle("T${alvoEp.temporada} E${alvoEp.numero}")
                    setSeasonNumber(alvoEp.temporada)
                    setEpisodeNumber(alvoEp.numero)
                }
                if (!terminou) {
                    setLastPlaybackPositionMillis(posicao.toInt())
                    setDurationMillis(duracao.toInt())
                }
            }
            .build()

        // A pessoa pode ter tirado o cartão da fileira: aí publica de novo.
        if (existente >= 0 && runCatching { ajudante.getWatchNextProgram(existente) }.getOrNull() != null) {
            ajudante.updateWatchNextProgram(programa, existente)
        } else {
            val id = ajudante.publishWatchNextProgram(programa)
            if (id >= 0) prefs.edit().putLong(chave, id).apply()
        }
    }
}
