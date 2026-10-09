package br.com.saimo.tv

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.ForwardingPlayer
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.VideoSize
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.upstream.DefaultBandwidthMeter
import androidx.media3.session.MediaSession
import androidx.media3.ui.PlayerView
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil3.dispose
import coil3.load
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.min
import kotlin.math.pow

private const val REQUEST_GUIDE = 1
private const val BANNER_MS = 6_000L
private const val TICK_MS = 20_000L
/// TV Box fica dias com o app aberto; sem isto a versão nova só aparecia
/// para quem fechava e abria de novo.
private const val ATUALIZACAO_MS = 60 * 60 * 1000L
/// Uma fonte viva entrega imagem bem antes disto. Passou daqui sem tocar, é
/// fonte morta que não deu erro — e sem este prazo o canal ficaria carregando
/// para sempre em vez de descer para a próxima.
private const val SOURCE_TIMEOUT_MS = 12_000L
/// Quantas vezes a fonte que está no ar é retomada antes de aceitar que caiu.
///
/// Erro de player não é prova de queda: no TV Box o wi-fi oscila, o CDN devolve
/// 5xx solto e a janela ao vivo escapa depois de um engasgo. Trocar de origem
/// nesses casos é pior que o defeito — recomeça o canal noutro ponto, com outro
/// áudio, na frente de quem está assistindo. Só depois destas retomadas sem
/// imagem é que a fonte seguinte entra.
private const val RETOMADAS_MAX = 8
/// Por quanto tempo a fonte continua sendo tratada como viva depois do último
/// quadro que ela entregou.
private const val CREDITO_DE_IMAGEM_MS = 30_000L
/// De quanto em quanto tempo o relógio do vídeo é conferido.
private const val VIGIA_MS = 2_000L
/// Quanto tempo o relógio do vídeo pode ficar parado, com o player achando que
/// toca ou carrega, antes de o vigia retomar a fonte sozinho. Cobre o canal que
/// congela sem erro: engasgo de rede some em poucos segundos, isto não.
private const val CONGELADO_MS = 12_000L

/**
 * The whole app: a channel list and a player, driven entirely by the remote.
 *
 * OK opens the list, OK again plays the focused channel, BACK closes it. Nothing
 * sits permanently over the picture — every overlay shows itself on a keypress
 * and leaves on its own.
 */
@UnstableApi
class MainActivity : AppCompatActivity() {

    companion object {
        /** Nome do canal a sintonizar ao abrir (link saimo://canal). */
        const val EXTRA_CANAL = "br.com.saimo.tv.CANAL"
        const val EXTRA_RADIO_URL = "br.com.saimo.tv.RADIO_URL"
        const val EXTRA_RADIO_NOME = "br.com.saimo.tv.RADIO_NOME"
    }

    private lateinit var player: ExoPlayer
    private var mediaSession: MediaSession? = null
    private lateinit var playerView: PlayerView
    private lateinit var listPanel: View
    private lateinit var channels: RecyclerView
    private lateinit var listCount: TextView
    private lateinit var banner: View
    private lateinit var bannerLogo: ImageView
    private lateinit var bannerNumber: TextView
    private lateinit var bannerChannel: TextView
    private lateinit var bannerProgramme: TextView
    private lateinit var bannerResolution: TextView
    private lateinit var bannerProgress: ProgressBar
    private lateinit var bannerSource: TextView
    private lateinit var bannerTimes: TextView
    private lateinit var status: TextView
    private lateinit var listHeader: View
    private lateinit var numpad: View
    private lateinit var numpadValue: TextView
    private lateinit var vodEntrada: View

    private val handler = Handler(Looper.getMainLooper())
    private val clock = SimpleDateFormat("HH:mm", Locale("pt", "BR"))
    private var guideStarted = false

    private val hideBanner = Runnable {
        banner.animate().alpha(0f).setDuration(220).withEndAction {
            banner.visibility = View.GONE
        }
    }

    /// Sem isto o programa no ar ficaria congelado depois de terminar. Só
    /// redesenha a lista quando ela está à vista: repintar 68 linhas atrás de um
    /// painel fechado é trabalho jogado fora num aparelho fraco.
    private val tick = object : Runnable {
        override fun run() {
            if (Epg.tick()) {
                // O refresh reconstrói as linhas visíveis; sem repor o foco na
                // posição que a pessoa escolheu, ele voltaria para onde o
                // RecyclerView calhar de pousar.
                if (listPanel.visibility == View.VISIBLE) { adapter.refresh(); focusRow(listaFoco) }
                if (banner.visibility == View.VISIBLE) updateBanner()
            }
            handler.postDelayed(this, TICK_MS)
        }
    }

    private val adapter = ChannelAdapter(
        onPick = { play(it) },
        onFavorite = { index ->
            Favorites.toggle(this, ordered[index].name)
            reorder()
        })

    /// Favoritos primeiro, depois a ordem do catálogo.
    private var ordered: List<Channel> = CATALOG
    /// Número de cada canal, fixo: sai da ordem por seção, sem contar os
    /// favoritos. Favoritar um canal não renumera os outros — "canal 5" é o
    /// mesmo hoje e amanhã, no controle e na voz.
    private var numeros: Map<String, Int> = emptyMap()

    private fun numeroDe(canal: Channel?): Int = canal?.let { numeros[it.name] } ?: 0

    private fun indicePorNumero(numero: Int): Int = ordered.indexOfFirst { numeros[it.name] == numero }
    private var isModoRadio = false
    private var current = 0
    private var sourceIndex = 0
    private var retries = 0

    /// Posição focada dentro da lista aberta — não confundir com `current`, que
    /// é o canal no ar. Movida só por nós (ver `moverFoco`), nunca pela busca
    /// de foco do sistema.
    private var listaFoco = 0
    /// Sobe a cada `focusRow` novo, para uma chamada atrasada (o post de
    /// dentro dela) desistir se a pessoa já pediu outra posição enquanto isso.
    private var focoGeracao = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_main)
        if (Vr.ativo) Vr.videoEmTextura(this, R.id.player)

        playerView = findViewById(R.id.player)
        listPanel = findViewById(R.id.listPanel)
        channels = findViewById(R.id.channels)
        listCount = findViewById(R.id.listCount)
        banner = findViewById(R.id.banner)
        bannerLogo = findViewById(R.id.bannerLogo)
        bannerNumber = findViewById(R.id.bannerNumber)
        bannerChannel = findViewById(R.id.bannerChannel)
        bannerProgramme = findViewById(R.id.bannerProgramme)
        bannerResolution = findViewById(R.id.bannerResolution)
        bannerProgress = findViewById(R.id.bannerProgress)
        bannerSource = findViewById(R.id.bannerSource)
        bannerTimes = findViewById(R.id.bannerTimes)
        status = findViewById(R.id.status)
        listHeader = findViewById(R.id.listHeader)
        numpad = findViewById(R.id.numpad)
        numpadValue = findViewById(R.id.numpadValue)

        listHeader.setOnClickListener { openNumpad() }
        vodEntrada = findViewById(R.id.vodEntrada)
        // O menu do topo substitui o atalho "voltar ao início" da lista.
        vodEntrada.visibility = View.GONE
        montarMenuGlobal()
        vodEntrada.setOnClickListener {
            // A tela inicial está sempre por baixo: voltar a ela é fechar esta.
            EscolhaActivity.voltar(this)
        }
        for ((id, digit) in listOf(
            R.id.pad0 to 0, R.id.pad1 to 1, R.id.pad2 to 2, R.id.pad3 to 3, R.id.pad4 to 4,
            R.id.pad5 to 5, R.id.pad6 to 6, R.id.pad7 to 7, R.id.pad8 to 8, R.id.pad9 to 9)) {
            findViewById<View>(id).setOnClickListener { padDigit(digit) }
        }
        findViewById<View>(R.id.padDel).setOnClickListener {
            if (typed.isNotEmpty()) typed.deleteCharAt(typed.length - 1)
            numpadValue.text = typed
        }
        findViewById<View>(R.id.padOk).setOnClickListener { padCommit() }
        if (Vr.ativo) montarVr()

        Favorites.load(this)
        // A lista publicada de ontem já está em disco: abre com ela e troca
        // quando a de hoje chegar, para o app nunca abrir sem canais.
        // Já lida pela tela inicial na maioria das vezes: não relê o disco.
        if (Remote.channels === CATALOG) Remote.loadCached(this)
        channels.layoutManager = LinearLayoutManager(this)
        channels.adapter = adapter
        // As linhas têm todas a mesma altura, então o RecyclerView pode pular a
        // remedição a cada mudança — é o que tira o engasgo ao rolar a lista.
        channels.setHasFixedSize(true)
        channels.setItemViewCacheSize(12)
        reorder()

        // Sem estimativa, o ExoPlayer começa por uma variante baixa e o canal
        // abre borrado até a adaptação subir. Partindo de uma estimativa alta
        // ele escolhe a melhor de cara e desce se a rede pedir — mesma intenção
        // da reordenação do master no macOS.
        val bandwidth = DefaultBandwidthMeter.Builder(this)
            .setInitialBitrateEstimate(8_000_000L)
            .build()
        player = ExoPlayer.Builder(this)
            .setBandwidthMeter(bandwidth)
            .setLoadControl(
                controleDeBuffer())
            .build().apply {
                playWhenReady = true
                addListener(playerListener)
                addAnalyticsListener(analyticsListener)
                Telemetria.observar(this)
            }
        playerView.player = player
        playerView.useController = false

        // A sessão é o que expõe o app pro Assistant (Mi Box) e pra Alexa
        // (Fire TV): "próximo/canal anterior" e "abrir <canal>" chegam por ela
        // enquanto o Saimo TV está na tela, sem precisar de servidor nem de
        // skill cadastrada. O player de fachada existe porque o de verdade
        // troca de fonte trocando o MediaSource inteiro — nunca tem um
        // "próximo item" de playlist para a sessão pedir sozinha.
        // Voz é enfeite: um aparelho que recuse a sessão continua passando canal.
        mediaSession = runCatching {
            MediaSession.Builder(this, ChannelPlayer(player))
                .setCallback(sessionCallback)
                .build()
        }.onFailure { Log.w("SaimoTV", "sessão de mídia indisponível", it) }.getOrNull()

        // Abre no canal pedido por link, ou no último assistido — não no
        // primeiro da lista, que obrigava a zapear até onde se estava.
        val rUrl = intent.getStringExtra(EXTRA_RADIO_URL)
        val rNome = intent.getStringExtra(EXTRA_RADIO_NOME)
        if (rUrl != null && rNome != null) {
            isModoRadio = true
            val radiosFile = java.io.File(filesDir, "radios.txt")
            val radiosFallback = java.io.File(filesDir, "vod/radios.txt")
            val alvo = if (radiosFile.exists()) radiosFile else radiosFallback
            
            val radiosList = mutableListOf<Channel>()
            if (alvo.exists()) {
                alvo.readLines().forEach { linha ->
                    val partes = linha.split("|")
                    if (partes.size >= 2) {
                        radiosList.add(Channel(name = partes[0].trim(), sources = listOf(Source(url = partes[1].trim())), categoria = "Rádio"))
                    }
                }
            }
            if (radiosList.isEmpty()) {
                radiosList.add(Channel(name = rNome, sources = listOf(Source(url = rUrl)), categoria = "Rádio"))
            }
            
            ordered = radiosList
            numeros = ordered.mapIndexed { i, c -> c.name to i + 1 }.toMap()
            play(ordered.indexOfFirst { it.name == rNome }.coerceAtLeast(0))
        } else {
            val pedido = intent.getStringExtra(EXTRA_CANAL) ?: Preferencias.ultimoCanal
            play(ordered.indexOfFirst { it.name == pedido }.coerceAtLeast(0))
        }
        refreshCatalog()
        handler.postDelayed(tick, TICK_MS)
        handler.postDelayed(procurarDeHoraEmHora, ATUALIZACAO_MS)
        // Rede é uma só: 35 downloads do guia disputando banda com o canal que
        // acabou de abrir travam a imagem nos primeiros segundos. O guia entra
        // quando o vídeo já está rodando, ou em três segundos se não rodar.
        handler.postDelayed({ startGuide() }, 3_000)
        handler.postDelayed(vigiaDeImagem, VIGIA_MS)
        handler.post(vigiaDeFontes)
    }

    /**
     * Quanto do canal guardar adiantado.
     *
     * Meio segundo de buffer basta para começar a mostrar; o resto enche
     * depois. Esperar dois segundos antes do primeiro quadro é o que fazia a
     * troca de canal parecer lenta.
     *
     * O teto é que muda com o aparelho. Quarenta segundos priorizando tempo
     * sobre tamanho não têm limite em bytes: num canal Full HD a 8–15 Mbps são
     * 40 a 75 MB, que num Fire TV Stick de 1 GB se somam ao resto do sistema
     * até ele matar o app — com a lista aberta, depois de alguns minutos, que
     * é o tempo de o buffer encher. Lá o teto é 20 segundos e 24 MB, o que
     * ainda cobre qualquer engasgo de rede comum.
     */
    private fun controleDeBuffer(): DefaultLoadControl =
        if (Aparelho.poucaMemoria) {
            DefaultLoadControl.Builder()
                .setBufferDurationsMs(15_000, 30_000, 1_500, 3_000)
                .setTargetBufferBytes(32 * 1024 * 1024)
                .setPrioritizeTimeOverSizeThresholds(false)
                .build()
        } else {
            DefaultLoadControl.Builder()
                .setBufferDurationsMs(32_000, 120_000, 1_500, 5_000)
                .setPrioritizeTimeOverSizeThresholds(true)
                .build()
        }

    /** Pega a lista publicada sem tirar do ar o canal que está tocando. */
    private fun refreshCatalog() {
        lifecycleScope.launch(semDerrubar) {
            if (!Remote.refresh(this@MainActivity)) return@launch
            reorder()
            updateBanner()
        }
    }

    /// Distingue a primeira abertura (que já oferece pelo refreshCatalog) da
    /// volta de outra tela, como as configurações.
    private var jaIniciou = false
    private var emSegundoPlano = false
    private var bitrateDinâmicoBps = 0L
    private var ultimoVideoSize: androidx.media3.common.VideoSize? = null

    private val atualizacao by lazy { OfertaDeAtualizacao(this) { showStatus(it) } }

    /**
     * A checagem de hora em hora só acontece depois que o canal já está no ar:
     * atualizar é assunto de quem assiste, não do começo da abertura.
     */
    private val procurarDeHoraEmHora = object : Runnable {
        override fun run() {
            if (!atualizacao.baixando) atualizacao.avisarSemInterromper()
            handler.postDelayed(this, ATUALIZACAO_MS)
        }
    }

    private fun startGuide() {
        if (guideStarted) return
        guideStarted = true
        lifecycleScope.launch(semDerrubar) {
            Epg.load(this@MainActivity) {
                // O refresh reconstrói as linhas visíveis; sem repor o foco na
                // posição que a pessoa escolheu, ele voltaria para onde o
                // RecyclerView calhar de pousar.
                if (listPanel.visibility == View.VISIBLE) { adapter.refresh(); focusRow(listaFoco) }
                updateBanner()
            }
        }
    }

    // MARK: - Playback

    /// Para o monitor: quando a tentativa começou, se já avisou que tocou e se
    /// já avisou que o canal caiu — um aviso por tentativa, não um por segundo.
    private var tentativaDesde = 0L
    private var tocouAvisado = false
    private var caiuAvisado = false
    /// Retomadas gastas na fonte que está no ar. Zera a cada quadro novo.
    private var retomadas = 0
    /// Quando a fonte no ar entregou imagem pela última vez, e em que ponto.
    private var ultimaImagem = 0L
    private var ultimaPosicao = -1L

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val nome = intent.getStringExtra(EXTRA_CANAL) ?: return
        val indice = ordered.indexOfFirst { it.name == nome }
        if (indice >= 0) play(indice)
    }

    private fun play(index: Int, source: Int = 0, apósFalha: Boolean = false) {
        android.util.Log.d("SAIMO_DEBUG", "play(index=$index, source=$source, aposFalha=$apósFalha)")
        val mudouDeFonte = current != index.coerceIn(ordered.indices) || sourceIndex != source
        val mesmoCanal = ordered.getOrNull(current)?.name == ordered.getOrNull(index.coerceIn(ordered.indices))?.name
        val novo = ordered.getOrNull(index.coerceIn(ordered.indices))?.name
        if (novo != null && novo != Preferencias.ultimoCanal) {
            Preferencias.canalAnterior = Preferencias.ultimoCanal
            Preferencias.ultimoCanal = novo
        }
        current = index.coerceIn(ordered.indices)
        sourceIndex = source
        val channel = ordered[current]
        val chosen = channel.sources.getOrNull(sourceIndex) ?: channel.sources.first()
        tentativaDesde = android.os.SystemClock.elapsedRealtime()
        tocouAvisado = false
        bannerResolution.text = ""
        bannerResolution.visibility = View.GONE
        if (!apósFalha || mudouDeFonte) {
            retomadas = 0
        }
        ultimaImagem = 0L
        ultimaPosicao = -1L
        if (!apósFalha) caiuAvisado = false
        // Falha passando para a próxima fonte, ou a reconexão do mesmo canal,
        // não é mais uma abertura; escolher canal ou fonte à mão é.
        val categoriaMonitor = if (channel.name.contains("Rádio", ignoreCase = true)) "radio" else "live"
        Telemetria.comecou(categoriaMonitor, channel.name, chosen.url, sourceIndex + 1,
            nova = !(apósFalha || (mesmoCanal && retries > 0)))

        // O aviso conta a tentativa inteira, não só o instante da troca: dizer
        // "fonte 1 falhou" por meio segundo e sumir não informa ninguém.
        // Quem assiste não precisa saber de fonte nem de servidor: só que o
        // canal está chegando. O detalhe técnico mora no painel de opções.
        bitrateDinâmicoBps = 0L
        ultimoVideoSize = null
        showStatus(getString(if (apósFalha) R.string.loading_outra else R.string.loading))
        handler.removeCallbacks(sourceTimeout)
        handler.postDelayed(sourceTimeout, SOURCE_TIMEOUT_MS)
        player.setMediaSource(Playback.mediaSource(this, chosen))
        player.prepare()
        player.playWhenReady = true

        adapter.select(current)
        updateBanner()
        revealBanner()
    }

    
    private val analyticsListener = object : androidx.media3.exoplayer.analytics.AnalyticsListener {
        override fun onLoadCompleted(
            eventTime: androidx.media3.exoplayer.analytics.AnalyticsListener.EventTime,
            loadEventInfo: androidx.media3.exoplayer.source.LoadEventInfo,
            mediaLoadData: androidx.media3.exoplayer.source.MediaLoadData
        ) {
            if (mediaLoadData.dataType == androidx.media3.common.C.DATA_TYPE_MEDIA) {
                val bytes = loadEventInfo.bytesLoaded
                val durationMs = mediaLoadData.mediaEndTimeMs - mediaLoadData.mediaStartTimeMs
                if (durationMs > 0 && bytes > 0) {
                    val bps = (bytes * 8000L) / durationMs
                    // Média móvel suave para não piscar muito (70% antigo, 30% novo)
                    if (bitrateDinâmicoBps == 0L) bitrateDinâmicoBps = bps
                    else bitrateDinâmicoBps = (bitrateDinâmicoBps * 7 + bps * 3) / 10
                    
                    // Atualiza a tela se já tivermos um tamanho de vídeo
                    ultimoVideoSize?.let {
                        runOnUiThread {
                            var rotulo = rotuloResolucao(it.width, it.height)
                            if (bitrateDinâmicoBps > 0) {
                                rotulo += String.format(java.util.Locale.US, " · %.1f Mbps", bitrateDinâmicoBps / 1_000_000f)
                            }
                            bannerResolution.text = rotulo
                            bannerResolution.visibility = if (rotulo.isEmpty()) android.view.View.GONE else android.view.View.VISIBLE
                        }
                    }
                }
            }
        }
    }


    private val playerListener = object : Player.Listener {
override fun onVideoSizeChanged(videoSize: VideoSize) {
            ultimoVideoSize = videoSize
            var rotulo = rotuloResolucao(videoSize.width, videoSize.height)
            
            // Tenta pegar do formato se existir (para VOD ou playlists ricas)
            var bitrateDaFaixa = player.videoFormat?.bitrate ?: androidx.media3.common.Format.NO_VALUE
            if (bitrateDaFaixa <= 0) {
                for (grupo in player.currentTracks.groups) {
                    if (grupo.type == androidx.media3.common.C.TRACK_TYPE_VIDEO && grupo.isSelected) {
                        for (i in 0 until grupo.length) {
                            if (grupo.isTrackSelected(i)) {
                                val b = grupo.getTrackFormat(i).bitrate
                                if (b > 0) bitrateDaFaixa = b
                                break
                            }
                        }
                    }
                    if (bitrateDaFaixa > 0) break
                }
            }
            
            // Usa o da faixa se existir, senão usa o dinâmico
            val bitrateFinal = if (bitrateDaFaixa > 0) bitrateDaFaixa.toLong() else bitrateDinâmicoBps
            
            if (bitrateFinal > 0) {
                rotulo += String.format(java.util.Locale.US, " · %.1f Mbps", bitrateFinal / 1_000_000f)
            }
            
            bannerResolution.text = rotulo
            bannerResolution.visibility = if (rotulo.isEmpty()) android.view.View.GONE else android.view.View.VISIBLE
        }


        override fun onPlaybackStateChanged(state: Int) {
            android.util.Log.d("SAIMO_DEBUG", "ExoPlayer onPlaybackStateChanged: state=$state")

            if (state == Player.STATE_ENDED && tocouAvisado && !emSegundoPlano) {
                // TV ao vivo nunca "termina". Se o player achou o fim do arquivo, a conexão caiu
                // limpamente sem dar erro. Devemos forçar a reconexão imediatamente!
                android.util.Log.e("SAIMO_DEBUG", "Fim de stream detectado (EOF). Reconectando...")
                play(current, sourceIndex, apósFalha = true)
                return
            }

            if (state == Player.STATE_READY) {
                if (!tocouAvisado) {
                    tocouAvisado = true
                    ordered.getOrNull(current)?.let { canal ->
                        val categoriaMonitor = if (canal.name.contains("Rádio", ignoreCase = true)) "radio" else "live"
                        Telemetria.tocou(categoriaMonitor, canal.name, canal.sources.getOrNull(sourceIndex)?.url.orEmpty(),
                            sourceIndex + 1, android.os.SystemClock.elapsedRealtime() - tentativaDesde)
                    }
                }
                retries = 0
                retomadas = 0
                ultimaImagem = android.os.SystemClock.elapsedRealtime()
                handler.removeCallbacks(sourceTimeout)
                status.visibility = View.GONE
                startGuide()
            }
        }
        
        override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
            // Se tentar pausar enquanto o app está aberto, força a tocar de novo.
            // TV ao vivo não tem pausa. (O sistema pausa sozinho se perder foco de áudio, por ex).
            if (!playWhenReady && !emSegundoPlano) {
                player.playWhenReady = true
            }
        }


        override fun onPlayerError(error: PlaybackException) {
            android.util.Log.e("SAIMO_DEBUG", "ExoPlayer error: ${error.errorCodeName}", error)

            handler.removeCallbacks(sourceTimeout)
            val channel = ordered[current]
            val categoriaMonitor = if (channel.name.contains("Rádio", ignoreCase = true)) "radio" else "live"
            Telemetria.falhou(categoriaMonitor, channel.name, channel.sources.getOrNull(sourceIndex)?.url.orEmpty(),
                sourceIndex + 1, error.errorCodeName)

            val agora = android.os.SystemClock.elapsedRealtime()
            // A fonte estava entregando imagem até agora há pouco: o erro foi
            // tropeço de rede, não a origem morrendo.
            val viva = ultimaImagem > 0L && agora - ultimaImagem < CREDITO_DE_IMAGEM_MS
            // Ficar para trás da janela ao vivo é o player que perdeu o passo
            // depois de um engasgo, não a fonte acabando. O certo é voltar para
            // a borda do ao vivo — trocar de origem aqui tira quem está
            // assistindo do lugar sem motivo nenhum.
            val ficouParaTras = error.errorCode == PlaybackException.ERROR_CODE_BEHIND_LIVE_WINDOW

            if ((viva || ficouParaTras) && retomadas < RETOMADAS_MAX) {
                retomadas++
                showStatus(getString(R.string.reconnecting))
                play(current, sourceIndex, apósFalha = true)
                // O play() já agenda o sourceTimeout.
                return
            }

            // Each channel lists its sources in preference order; a dead or
            // expired link falls through to the next before giving up.
            if (sourceIndex + 1 < channel.sources.size) {
                play(current, sourceIndex + 1, apósFalha = true)
                return
            }
            avisarQueCaiu(channel)
            retries++
            if (retries > 6) {
                canalForaDoAr(channel)
                return
            }
            showStatus(getString(R.string.reconnecting))
            val delay = min(1.6.pow(retries.toDouble()) * 1000, 15_000.0).toLong()
            handler.postDelayed({ play(current, 0) }, delay)
        }
    }

    // MARK: - Remote

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (numpad.visibility == View.VISIBLE) {
            if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE) {
                closeNumpad()
                return true
            }
            if (keyCode in KeyEvent.KEYCODE_0..KeyEvent.KEYCODE_9) {
                padDigit(keyCode - KeyEvent.KEYCODE_0)
                return true
            }
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (typed.isNotEmpty()) typed.deleteCharAt(typed.length - 1)
                numpadValue.text = typed
                return true
            }
            val padIds = setOf(
                R.id.pad0, R.id.pad1, R.id.pad2, R.id.pad3, R.id.pad4,
                R.id.pad5, R.id.pad6, R.id.pad7, R.id.pad8, R.id.pad9,
                R.id.padDel, R.id.padOk
            )
            val cur = currentFocus
            if (cur == null || cur.id !in padIds) {
                findViewById<View>(R.id.pad1)?.requestFocus()
                return true
            }
            return super.onKeyDown(keyCode, event)
        }
        val listOpen = listPanel.visibility == View.VISIBLE
        return when (keyCode) {
            // Um toque no OK mostra o que está no ar; segurar três segundos é
            // que abre a lista. O toque é o gesto que se dá o tempo todo, então
            // ele fica com a ação que não tira o vídeo da frente.
            // OK abre a lista de canais: é o que mais se faz com o controle, e
            // o que todo app de TV faz. O cartão do canal aparece com INFO ou
            // a cada troca; as opções (áudio, fontes, favorito) no MENU.
            // Toque no OK: programação do canal (e, com ela na tela, as fontes).
            // Segurar o OK: opções — é o MENU de
            // quem não tem MENU no controle (o da Xiaomi, por exemplo). A
            // decisão fica para quando o botão é solto (ver onKeyUp).
            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                if (listOpen) return super.onKeyDown(keyCode, event)
                if (event != null && event.repeatCount == 0) {
                    event.startTracking()
                    okSegurado = false
                    okApertado = true
                }
                true
            }
            KeyEvent.KEYCODE_LAST_CHANNEL -> { canalAnterior(); true }
            KeyEvent.KEYCODE_DPAD_LEFT -> {
                when {
                    !listOpen -> { openList(); true }
                    // Na lista, esquerda sobe para as seções: dali, esquerda e
                    // direita pulam de Esportes para Notícias sem descer 900 canais.
                    channels.hasFocus() -> { focarSecaoAtual(); true }
                    else -> super.onKeyDown(keyCode, event)
                }
            }
            KeyEvent.KEYCODE_BACK, KeyEvent.KEYCODE_ESCAPE -> {
                if (listOpen) { closeList(); true } else super.onKeyDown(keyCode, event)
            }
            // Sobre a imagem o direcional troca de canal, como numa TV: cima e
            // baixo andam na lista, direita abre a programação.
            //
            // Dentro da lista, cima/baixo NÃO passam para o RecyclerView achar
            // o próximo foco sozinho — segurando a tecla, o TV Box antigo não
            // dá conta de gerar as linhas na velocidade dos eventos, a busca de
            // foco falha e ele "desiste" voltando pro topo, num loop. Em vez
            // disso o índice é nosso: cada tecla anda um item e manda rolar
            // para ele, sem depender da busca espacial do sistema.
            KeyEvent.KEYCODE_DPAD_UP, KeyEvent.KEYCODE_CHANNEL_UP, KeyEvent.KEYCODE_MEDIA_PREVIOUS -> {
                when {
                    !listOpen -> { play((current - 1 + ordered.size) % ordered.size); true }
                    // Canal+ / Canal− na lista pulam de seção em seção.
                    channels.hasFocus() && keyCode == KeyEvent.KEYCODE_CHANNEL_UP -> { pularSecao(-1); true }
                    channels.hasFocus() -> { moverFoco(-passo(event)); true }
                    else -> super.onKeyDown(keyCode, event)
                }
            }
            KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_CHANNEL_DOWN, KeyEvent.KEYCODE_MEDIA_NEXT -> {
                when {
                    !listOpen -> { play((current + 1) % ordered.size); true }
                    channels.hasFocus() && keyCode == KeyEvent.KEYCODE_CHANNEL_DOWN -> { pularSecao(1); true }
                    channels.hasFocus() -> { moverFoco(passo(event)); true }
                    else -> super.onKeyDown(keyCode, event)
                }
            }
            // Ao vivo não pausa: a tecla só garante que está tocando.
            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, KeyEvent.KEYCODE_MEDIA_PLAY,
            KeyEvent.KEYCODE_MEDIA_PAUSE -> {
                player.playWhenReady = true; true
            }
            KeyEvent.KEYCODE_INFO -> { if (!listOpen) revealBanner(); true }
            KeyEvent.KEYCODE_DPAD_RIGHT, KeyEvent.KEYCODE_GUIDE -> {
                when {
                    !listOpen -> { openGuide(); true }
                    // Na lista, a direita abre as fontes do canal em foco.
                    keyCode == KeyEvent.KEYCODE_DPAD_RIGHT && channels.hasFocus() -> {
                        escolherFonte(listaFoco); true
                    }
                    else -> super.onKeyDown(keyCode, event)
                }
            }
            in KeyEvent.KEYCODE_0..KeyEvent.KEYCODE_9 -> {
                // Digitar o número é a busca que existe num controle remoto:
                // teclado virtual para texto livre seria pior de usar.
                typeDigit(keyCode - KeyEvent.KEYCODE_0); true
            }
            KeyEvent.KEYCODE_MENU, KeyEvent.KEYCODE_SETTINGS -> {
                if (listOpen && keyCode == KeyEvent.KEYCODE_MENU) {
                    Favorites.toggle(this, ordered.getOrNull(listaFoco)?.name ?: ordered[current].name)
                    reorder()
                } else {
                    opcoes()
                }
                true
            }
            KeyEvent.KEYCODE_CAPTIONS -> { Faixas.audioELegenda(this, player); true }
            else -> {
                if (!listOpen) revealBanner()
                super.onKeyDown(keyCode, event)
            }
        }
    }

    /**
     * Prova de vida da fonte, do jeito que quem assiste vê.
     *
     * Enquanto o relógio do vídeo anda, há imagem na tela — e nenhum erro que
     * o player cuspa no caminho justifica trocar de origem.
     */
    private val vigiaDeImagem = object : Runnable {
        override fun run() {
            if (::player.isInitialized) {
                val pos = player.currentPosition
                val agora = android.os.SystemClock.elapsedRealtime()
                // Relógio para trás é linha do tempo nova — a volta para a
                // borda do ao vivo faz isso. Recomeça a contagem dali.
                if (pos < ultimaPosicao - 1_000) ultimaPosicao = pos
                if (player.isPlaying && pos > ultimaPosicao + 500) {
                    ultimaPosicao = pos
                    ultimaImagem = agora
                    retomadas = 0
                    congeladoDesde = 0L
                } else if (tocouAvisado && player.playWhenReady &&
                    player.playbackState != Player.STATE_IDLE) {
                    // Devia estar tocando e o relógio do vídeo não anda: ficou
                    // em buffering eterno ou congelou sem o player dar erro
                    // nenhum. Sem este vigia só reescolher a fonte à mão
                    // desfazia — o prazo de abertura já foi desligado.
                    if (congeladoDesde == 0L) congeladoDesde = agora
                    if (agora - congeladoDesde >= CONGELADO_MS) {
                        congeladoDesde = agora
                        recuperarCongelado()
                    }
                } else {
                    congeladoDesde = 0L
                }
            }
            handler.postDelayed(this, VIGIA_MS)
        }
    }

    /// Desde quando o vídeo está parado sem motivo; 0 = andando normalmente.
    private var congeladoDesde = 0L

    /**
     * Canal congelado: primeiro pede o mesmo ponto de novo (volta para a
     * borda do ao vivo), que resolve a maioria dos casos sem trocar de
     * origem. Esgotadas as retomadas, desce para a fonte seguinte.
     */
    private fun recuperarCongelado() {
        android.util.Log.e("SAIMO_DEBUG", "recuperarCongelado() chamado!")
        val channel = ordered.getOrNull(current) ?: return
        val categoriaMonitor = if (channel.name.contains("Rádio", ignoreCase = true)) "radio" else "live"
            Telemetria.falhou(categoriaMonitor, channel.name, channel.sources.getOrNull(sourceIndex)?.url.orEmpty(),
            sourceIndex + 1, "congelou por ${CONGELADO_MS / 1000} s")
        if (retomadas < RETOMADAS_MAX) {
            retomadas++
            // Recria a conexão do zero, pois o socket pode estar preso no SO
            play(current, sourceIndex, apósFalha = true)
            return
        }
        if (sourceIndex + 1 < channel.sources.size) {
            play(current, sourceIndex + 1, apósFalha = true)
        } else {
            play(current, 0, apósFalha = true)
        }
    }

    /**
     * Relê a lista de servidores desligados de dois em dois minutos.
     *
     * Desligar um provedor no painel tem que valer sem ninguém fechar o app:
     * quem está assistindo quando a fonte morre é justamente quem precisa que
     * ela suma. Quando a lista muda, a lista de canais é remontada na hora.
     */
    private val vigiaDeFontes = object : Runnable {
        override fun run() {
            lifecycleScope.launch(semDerrubar) {
                if (FontesDesativadas.atualizar()) reorder()
            }
            handler.postDelayed(this, 120_000)
        }
    }

    private val sourceTimeout = Runnable {
        val channel = ordered.getOrNull(current) ?: return@Runnable
        val categoriaMonitor = if (channel.name.contains("Rádio", ignoreCase = true)) "radio" else "live"
            Telemetria.falhou(categoriaMonitor, channel.name, channel.sources.getOrNull(sourceIndex)?.url.orEmpty(),
            sourceIndex + 1, "sem imagem em ${SOURCE_TIMEOUT_MS / 1000} s")
        if (sourceIndex + 1 < channel.sources.size) {
            play(current, sourceIndex + 1, apósFalha = true)
        } else {
            avisarQueCaiu(channel)
            canalForaDoAr(channel)
        }
    }

    private fun avisarQueCaiu(channel: Channel) {
        if (caiuAvisado) return
        caiuAvisado = true
        val categoriaMonitor = if (channel.name.contains("Rádio", ignoreCase = true)) "radio" else "live"
        Telemetria.caiu(categoriaMonitor, channel.name, channel.sources.size)
    }


    private var okSegurado = false
    private var okApertado = false

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean {
        if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) &&
            listPanel.visibility != View.VISIBLE && numpad.visibility != View.VISIBLE) {
            okSegurado = true
            opcoes()
            return true
        }
        return super.onKeyLongPress(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
            // O OK que abriu a lista não pode, ao ser solto, escolher o canal em foco.
            if (SystemClock.elapsedRealtime() - listaAbertaEm < 400) return true
            if (okApertado) {
                okApertado = false
                // OK sobre o vídeo mostra a programação do canal (o rodapé); com
                // ela já na tela, abre as fontes. A lista é na seta esquerda.
                if (!okSegurado && listPanel.visibility != View.VISIBLE) {
                    if (banner.visibility == View.VISIBLE && banner.alpha > 0.5f) escolherFonte(current)
                    else revealBanner()
                }
                okSegurado = false
                return true
            }
        }
        return super.onKeyUp(keyCode, event)
    }

    private var listaAbertaEm = 0L

    private var typed = StringBuilder()
    private val commitTyped = Runnable {
        val entered = typed.toString()
        typed = StringBuilder()
        status.visibility = View.GONE
        if (Unlock.consume(entered)) {
            MenuGlobal.atualizar(menuGlobal)
            reorder()
            return@Runnable
        }
        val number = entered.toIntOrNull()
        val indice = number?.let { indicePorNumero(it) } ?: -1
        if (indice >= 0) play(indice)
    }

    // MARK: - Teclado na tela

    /**
     * Not every remote has number keys — the Xiaomi one does not — and the code
     * that reveals the extra list is typed, so there has to be a way in with
     * nothing but the D-pad and OK. The keypad opens from the list header.
     */
    private fun openNumpad() {
        typed = StringBuilder()
        handler.removeCallbacks(commitTyped)
        numpadValue.text = ""
        numpad.visibility = View.VISIBLE
        numpad.post { findViewById<View>(R.id.pad1).requestFocus() }
    }

    private fun closeNumpad() {
        numpad.visibility = View.GONE
        typed = StringBuilder()
        focusRow(current)
    }

    private fun padDigit(digit: Int) {
        if (typed.length >= 4) return
        typed.append(digit)
        numpadValue.text = typed
    }

    private fun padCommit() {
        val entered = typed.toString()
        typed = StringBuilder()
        numpad.visibility = View.GONE
        if (Unlock.consume(entered)) {
            MenuGlobal.atualizar(menuGlobal)
            reorder()
            focusRow(current)
            return
        }
        val number = entered.toIntOrNull()
        val indice = number?.let { indicePorNumero(it) } ?: -1
        if (indice >= 0) {
            closeList()
            play(indice)
        } else {
            focusRow(current)
        }
    }

    private fun typeDigit(digit: Int) {
        // Quatro dígitos cabem: o catálogo não chega a mil canais, então um
        // quarto dígito nunca é número de canal.
        if (typed.length >= 4) typed = StringBuilder()
        typed.append(digit)
        showStatus(getString(R.string.channel_number, typed.toString()))
        handler.removeCallbacks(commitTyped)
        handler.postDelayed(commitTyped, 1_200)
    }

    private fun reorder() {
        val playing = ordered.getOrNull(current)?.name
        // O que o painel desligou some aqui, antes de a lista chegar à tela:
        // canal sem nenhuma fonte não abriria mesmo.
        if (!isModoRadio) {
            ordered = FontesDesativadas.peneirarCanais(Categorias.ordenar(Unlock.channels()))
            numeros = Categorias.ordenarFixo(ordered).mapIndexed { i, c -> c.name to i + 1 }.toMap()
        }
        adapter.numero = { numeroDe(it) }
        val found = ordered.indexOfFirst { it.name == playing }
        // Ao trancar com um desses no ar, o nome ficaria à vista na faixa;
        // volta para o primeiro canal comum antes de a lista encolher.
        if (found < 0 && playing != null) play(0) else current = found.coerceAtLeast(0)
        adapter.submit(ordered)
        adapter.select(current)
        montarSecoes()
        listCount.text = ordered.size.toString()
    }

    /**
     * Lista as fontes de um canal para escolher uma à mão, como no Mac e no
     * site. A troca automática continua valendo a partir da escolhida: se ela
     * cair, desce para a seguinte.
     */
    private fun escolherFonte(index: Int) {
        val canal = ordered.getOrNull(index) ?: return
        if (isFinishing || isDestroyed) return
        val marcada = if (index == current) sourceIndex else -1
        Painel.mostrar(this, getString(R.string.fontes_titulo, canal.name), canal.sources.mapIndexed { i, fonte ->
            Painel.Item(getString(R.string.fontes_item_amigavel, i + 1),
                listOfNotNull(fonte.quality, fonte.url.toUri().host?.removePrefix("www.")).joinToString(" · "),
                marcado = i == marcada) {
                if (listPanel.visibility == View.VISIBLE) closeList()
                retries = 0
                play(index, i)
            }
        }, getString(R.string.fontes_dica))
    }

    /** MENU com o vídeo na tela: tudo o que se pode fazer com o canal. */
    private fun opcoes() {
        val canal = ordered.getOrNull(current) ?: return
        val favorito = Favorites.contains(canal.name)
        val anterior = Preferencias.canalAnterior?.takeIf { n -> ordered.any { it.name == n } }
        val itens = listOfNotNull(
            Painel.Item(getString(R.string.opcoes_canais), icone = R.drawable.ic_list) { openList() },
            Painel.Item(getString(R.string.guide), icone = R.drawable.ic_guide) { openGuide() },
            anterior?.let { Painel.Item(getString(R.string.opcoes_anterior, it), icone = R.drawable.ic_history) { canalAnterior() } },
            Painel.Item(getString(if (favorito) R.string.opcoes_desfavoritar else R.string.opcoes_favoritar),
                icone = if (favorito) R.drawable.ic_star else R.drawable.ic_star_outline) {
                Favorites.toggle(this, canal.name); reorder()
            },
            Painel.Item(getString(R.string.player_audio_legenda), icone = R.drawable.ic_subtitles) { Faixas.audioELegenda(this, player) },
            Painel.Item(getString(R.string.player_qualidade), icone = R.drawable.ic_hd) { Faixas.qualidade(this, player) },
            if (canal.sources.size > 1) Painel.Item(getString(R.string.opcoes_fontes),
                getString(R.string.opcoes_fontes_dica, canal.sources.size), icone = R.drawable.ic_swap) { escolherFonte(current) } else null,
            Painel.Item(getString(R.string.ajustes_timer),
                if (TimerDeSono.ativo) getString(R.string.ajustes_timer_em, TimerDeSono.restanteMin()) else null,
                icone = R.drawable.ic_timer) {
                Painel.mostrar(this, getString(R.string.ajustes_timer), listOf(0, 30, 60, 90, 120).map { m ->
                    Painel.Item(if (m == 0) getString(R.string.ajustes_desligado) else getString(R.string.ajustes_minutos, m)) {
                        TimerDeSono.ligar(m)
                    }
                })
            },
            Painel.Item(getString(R.string.opcoes_inicio), icone = R.drawable.ic_home) { finish() },
        )
        Painel.mostrar(this, canal.name, itens, Epg.nowNext(canal.name)?.first?.title)
    }

    /**
     * As pílulas de seção no alto da lista. Focar uma já leva a lista até a
     * seção; descer entra no primeiro canal dela.
     */
    private fun montarSecoes() {
        val caixa = findViewById<android.widget.LinearLayout>(R.id.listSecoesItens)
        caixa.removeAllViews()
        val d = resources.displayMetrics.density
        val secoes = ordered.map { Categorias.secao(it) }.distinct()
        for (secao in secoes) {
            caixa.addView(TextView(this).apply {
                text = secao
                tag = secao
                textSize = 15f
                setTextColor(androidx.core.content.ContextCompat.getColorStateList(context, R.color.texto_botao_ficha))
                setBackgroundResource(R.drawable.botao_player)
                isFocusable = true
                setPadding((14 * d).toInt(), (6 * d).toInt(), (14 * d).toInt(), (6 * d).toInt())
                layoutParams = android.widget.LinearLayout.LayoutParams(
                    android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
                    android.widget.LinearLayout.LayoutParams.WRAP_CONTENT).apply { marginEnd = (6 * d).toInt() }
                val primeiro = { ordered.indexOfFirst { Categorias.secao(it) == secao } }
                setOnFocusChangeListener { _, foco ->
                    if (foco) {
                        listaFoco = primeiro().coerceAtLeast(0)
                        (channels.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(listaFoco, 0)
                    }
                }
                setOnClickListener { focusRow(primeiro().coerceAtLeast(0)) }
                setOnKeyListener { _, codigo, evento ->
                    if (evento.action == KeyEvent.ACTION_DOWN && codigo == KeyEvent.KEYCODE_DPAD_DOWN) {
                        focusRow(primeiro().coerceAtLeast(0)); true
                    } else false
                }
            })
        }
    }

    /** Cartão do canal focado na lista e a aba da seção dele acesa. */
    private fun mostrarPrevia(index: Int) {
        val canal = ordered.getOrNull(index) ?: return
        val previa = findViewById<View>(R.id.previa)
        val par = Epg.nowNext(canal.name)
        val agora = par?.first
        val depois = par?.second
        findViewById<TextView>(R.id.previaCanal).text =
            listOfNotNull(numeroDe(canal).takeIf { it > 0 }?.toString(), canal.name).joinToString("  ")
        findViewById<TextView>(R.id.previaAgora).text = agora?.title ?: getString(R.string.previa_sem_guia)
        findViewById<TextView>(R.id.previaDetalhe).text = listOfNotNull(
            agora?.let { getString(R.string.times, clock.format(Date(it.start)), clock.format(Date(it.stop)),
                ((it.stop - System.currentTimeMillis()) / 60_000L).coerceAtLeast(0)) },
            depois?.let { getString(R.string.up_next, it.title) },
        ).joinToString("\n")
        previa.visibility = if (listPanel.visibility == View.VISIBLE) View.VISIBLE else View.GONE
        val secao = Categorias.secao(canal)
        val caixa = findViewById<android.widget.LinearLayout>(R.id.listSecoesItens)
        for (i in 0 until caixa.childCount) {
            val chip = caixa.getChildAt(i)
            val esta = chip.tag == secao
            if (chip.isSelected != esta) chip.isSelected = esta
            if (esta && !chip.hasFocus()) {
                (caixa.parent as? android.widget.HorizontalScrollView)?.smoothScrollTo(
                    (chip.left - 40).coerceAtLeast(0), 0)
            }
        }
    }

    private fun focarSecaoAtual() {
        val secao = ordered.getOrNull(listaFoco)?.let { Categorias.secao(it) }
        val caixa = findViewById<android.widget.LinearLayout>(R.id.listSecoesItens)
        (caixa.findViewWithTag<View>(secao) ?: caixa.getChildAt(0) ?: vodEntrada).requestFocus()
    }

    /** Volta ao canal de antes — o "zap" entre dois canais, como no controle da TV. */
    private fun canalAnterior() {
        val nome = Preferencias.canalAnterior ?: return
        val indice = ordered.indexOfFirst { it.name == nome }
        if (indice >= 0) play(indice)
    }

    /**
     * Nenhuma fonte abriu. Em vez de um aviso sem saída, o que dá para fazer:
     * tentar de novo, ou ir a um canal parecido que esteja funcionando.
     */
    private fun canalForaDoAr(canal: Channel) {
        showStatus(getString(R.string.unavailable))
        if (isFinishing || isDestroyed) return
        val secao = Categorias.de(canal)
        val parecidos = ordered.filter { it.name != canal.name && Categorias.de(it) == secao }.take(4)
        Painel.mostrar(this, getString(R.string.fora_titulo, canal.name), listOf(
            Painel.Item(getString(R.string.player_tentar_de_novo), icone = R.drawable.ic_replay) { retries = 0; play(current, 0) },
        ) + parecidos.map { c ->
            Painel.Item(getString(R.string.fora_ir, c.name), Epg.nowNext(c.name)?.first?.title) {
                retries = 0; play(ordered.indexOf(c))
            }
        } + Painel.Item(getString(R.string.opcoes_canais), icone = R.drawable.ic_list) { openList() },
            getString(R.string.fora_sub))
    }

    private fun openGuide() {
        startActivityForResult(
            Intent(this, GuideActivity::class.java)
                .putExtra(GuideActivity.EXTRA_CHANNEL, ordered[current].name),
            REQUEST_GUIDE)
    }

    @Deprecated("Simples o bastante para o alvo mínimo do projeto")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != REQUEST_GUIDE || resultCode != Activity.RESULT_OK) return
        val name = data?.getStringExtra(GuideActivity.EXTRA_CHANNEL) ?: return
        val index = ordered.indexOfFirst { it.name == name }
        if (index >= 0) play(index)
    }

    // MARK: - Óculos de VR

    /**
     * Só nos óculos (ver Vr.kt): lá não há D-pad, e o ao vivo inteiro vivia
     * nele. Pinçar sobre o vídeo mostra esta barra, que faz o que as teclas
     * fazem — lista, canal acima e abaixo, guia, fontes, número, opções.
     */
    private var barraVr: View? = null
    private val esconderBarraVrDepois = Runnable { esconderBarraVr() }

    private fun esconderBarraVr() {
        handler.removeCallbacks(esconderBarraVrDepois)
        barraVr?.visibility = View.GONE
    }

    private fun mostrarBarraVr() {
        val barra = barraVr ?: return
        revealBanner()
        barra.visibility = View.VISIBLE
        adiarBarraVr()
    }

    private fun adiarBarraVr() {
        handler.removeCallbacks(esconderBarraVrDepois)
        handler.postDelayed(esconderBarraVrDepois, BANNER_MS)
    }

    private fun montarVr() {
        val raiz = findViewById<ViewGroup>(android.R.id.content).getChildAt(0) as? android.widget.FrameLayout ?: return
        // Por cima do vídeo e embaixo de todo o resto: com a lista aberta, tocar
        // na imagem fecha a lista (o "tocar fora"); sem ela, mostra ou esconde
        // a barra.
        Vr.camadaDeToque(raiz, 1) {
            when {
                listPanel.visibility == View.VISIBLE -> closeList()
                barraVr?.visibility == View.VISIBLE -> { esconderBarraVr(); handler.removeCallbacks(hideBanner); hideBanner.run() }
                else -> mostrarBarraVr()
            }
        }
        val fileira = Vr.fileira(this)
        fun botao(texto: String, icone: Int, soIcone: Boolean = false, acao: () -> Unit) {
            val b = Vr.botao(this, if (soIcone) "" else texto, icone) { acao(); if (barraVr?.visibility == View.VISIBLE) adiarBarraVr() }
            b.contentDescription = texto
            Vr.adicionar(fileira, b)
        }
        botao(getString(R.string.vr_voltar), R.drawable.ic_arrow_back) { Vr.voltar(this) }
        botao(getString(R.string.vr_canais), R.drawable.ic_list) { openList() }
        // Mesmo sentido das setas do controle: para cima é o canal de cima da lista.
        botao(getString(R.string.vr_canal_mais), R.drawable.ic_arrow_up, soIcone = true) {
            play((current - 1 + ordered.size) % ordered.size)
        }
        botao(getString(R.string.vr_canal_menos), R.drawable.ic_arrow_down, soIcone = true) {
            play((current + 1) % ordered.size)
        }
        botao(getString(R.string.guide), R.drawable.ic_guide) { esconderBarraVr(); openGuide() }
        botao(getString(R.string.vr_fontes), R.drawable.ic_swap) { escolherFonte(current) }
        botao(getString(R.string.vr_anterior), R.drawable.ic_history) { canalAnterior() }
        botao(getString(R.string.vr_numero), R.drawable.ic_dialpad) { esconderBarraVr(); openNumpad() }
        botao(getString(R.string.vr_opcoes), R.drawable.ic_more) { opcoes() }
        val rolagem = android.widget.HorizontalScrollView(this).apply {
            isHorizontalScrollBarEnabled = false
            setBackgroundResource(R.drawable.bg_top_scrim)
            addView(fileira)
            visibility = View.GONE
        }
        raiz.addView(rolagem, android.widget.FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, android.view.Gravity.TOP))
        barraVr = rolagem

        // Lista: um botão de fechar no cabeçalho (o BACK de quem não tem controle).
        (listHeader as? ViewGroup)?.addView(Vr.botao(this, "", R.drawable.ic_close) { closeList() }.apply {
            contentDescription = getString(R.string.vr_fechar)
        })
        findViewById<TextView>(R.id.listLegenda)?.setText(R.string.vr_legenda_lista)

        // Teclado numérico: tocar fora fecha; tocar no quadro não.
        numpad.setOnClickListener { closeNumpad() }
        findViewById<View>(R.id.numpadContainer)?.isClickable = true
        (numpad as? ViewGroup)?.getChildAt(0)?.isClickable = true
    }

    // MARK: - Overlays

    /**
     * O mesmo menu do topo das outras telas, por cima do vídeo — aparece com a
     * lista de canais e some com ela, para nada ficar sobre a imagem.
     */
    private var menuGlobal: View? = null

    private fun montarMenuGlobal() {
        val raiz = findViewById<ViewGroup>(android.R.id.content).getChildAt(0) as? android.widget.FrameLayout ?: return
        val barra = MenuGlobal.criar(this, Aba.AO_VIVO)
        barra.setBackgroundResource(R.drawable.bg_top_scrim)
        barra.visibility = View.GONE
        raiz.addView(barra, android.widget.FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        menuGlobal = barra
        // A lista começa abaixo do menu.
        listPanel.setPadding(listPanel.paddingLeft, (46 * resources.displayMetrics.density).toInt(),
            listPanel.paddingRight, listPanel.paddingBottom)
    }

    private fun openList() {
        esconderBarraVr()
        menuGlobal?.visibility = View.VISIBLE
        listaAbertaEm = SystemClock.elapsedRealtime()
        handler.removeCallbacks(hideBanner)
        banner.visibility = View.GONE
        adapter.refresh()
        listPanel.visibility = View.VISIBLE
        // Ainda não foi medido na primeira abertura, então a largura vem do
        // recurso: ler width aqui daria zero e a entrada não aconteceria.
        listPanel.translationX = -panelWidth()
        // O cabeçalho é focável e vem primeiro na ordem de percurso, então o
        // sistema o escolhe sozinho quando o painel aparece; o post abaixo
        // resolve isso pousando no canal atual assim que a lista tiver layout.
        // Repetir a mesma chamada aqui no fim da animação, sem condição, foi o
        // bug: se a pessoa já tivesse descido a lista nesses 180ms, o painel
        // arrastava o foco de volta para o canal atual, "subindo" a seleção
        // bem na hora em que ela ainda estava apertando para baixo. Só refaz o
        // foco se ele realmente escapou da lista (ficou no cabeçalho).
        listPanel.animate().translationX(0f).setDuration(180)
            .withEndAction { if (!channels.hasFocus()) focusRow(current) }.start()
        channels.post { focusRow(current) }
    }

    /**
     * Anda um item na lista aberta e rola/foca para lá.
     *
     * Não usa a busca de foco do RecyclerView (`View.focusSearch`), que é o que
     * bugava segurando a tecla num TV Box fraco: os eventos chegam mais rápido
     * do que ele consegue medir a próxima linha, a busca falha e o sistema
     * "recupera" pulando pro topo — daí o loop subindo. Aqui a posição é
     * contada por nós, então cada tecla sempre sabe exatamente para onde ir.
     */
    /**
     * Segurar a seta acelera: um por um no começo, depois de três em três,
     * depois de dez em dez — descer 900 canais um a um não é navegar.
     */
    private fun passo(event: KeyEvent?): Int {
        val r = event?.repeatCount ?: 0
        return when { r > 24 -> 10; r > 8 -> 3; else -> 1 }
    }

    /** Vai ao primeiro canal da seção anterior/seguinte. */
    private fun pularSecao(sentido: Int) {
        val secoes = ordered.map { Categorias.secao(it) }
        val atual = secoes.getOrNull(listaFoco) ?: return
        val alvo = if (sentido > 0) {
            (listaFoco until ordered.size).firstOrNull { secoes[it] != atual }
        } else {
            val inicioAtual = (listaFoco downTo 0).lastOrNull { secoes[it] == atual } ?: 0
            if (inicioAtual == 0) null
            else { val anterior = secoes[inicioAtual - 1]; (inicioAtual - 1 downTo 0).lastOrNull { secoes[it] == anterior } }
        } ?: return
        focusRow(alvo)
        showStatus(secoes[alvo])
        handler.removeCallbacks(esconderStatus)
        handler.postDelayed(esconderStatus, 1_200)
    }

    private val esconderStatus = Runnable { status.visibility = View.GONE }

    private fun moverFoco(delta: Int) {
        val alvo = (listaFoco + delta).coerceAtMost(ordered.size - 1)
        if (listaFoco + delta < 0) { if (listaFoco == 0) focarSecaoAtual() else focusRow(0); return }
        if (alvo == listaFoco) return
        listaFoco = alvo
        focusRow(alvo)
    }

    /**
     * Scrolls to a row and focuses it, retrying until the holder exists.
     *
     * A scroll only finishes on the next layout pass, so asking for the holder
     * straight away finds nothing, and the list would open with nothing focused
     * — a remote that does nothing. `geracao` faz uma chamada velha desistir se
     * `moverFoco` já pediu outra posição enquanto o post estava na fila —
     * sem isso, segurar a tecla podia deixar dois pedidos de foco correndo ao
     * mesmo tempo e o mais lento vencer, arrastando a seleção para trás.
     */
    private fun focusRow(index: Int, attempts: Int = 8, geracao: Int = ++focoGeracao) {
        if (geracao != focoGeracao) return
        listaFoco = index
        mostrarPrevia(index)
        (channels.layoutManager as LinearLayoutManager)
            .scrollToPositionWithOffset(index, channels.height / 3)
        channels.post {
            if (geracao != focoGeracao) return@post
            if (channels.findViewHolderForAdapterPosition(index)?.itemView?.requestFocus() == true) return@post
            if (attempts > 0) focusRow(index, attempts - 1, geracao)
            else channels.getChildAt(0)?.requestFocus()
        }
    }

    private fun panelWidth(): Float =
        if (listPanel.width > 0) listPanel.width.toFloat() else 430 * resources.displayMetrics.density

    private fun closeList() {
        menuGlobal?.visibility = View.GONE
        findViewById<View>(R.id.previa).visibility = View.GONE
        numpad.visibility = View.GONE
        listPanel.animate().translationX(-panelWidth()).setDuration(160)
            .withEndAction {
                listPanel.visibility = View.GONE
                listPanel.translationX = 0f
            }.start()
        playerView.requestFocus()
        revealBanner()
    }

    private fun updateBanner() {
        val channel = ordered[current]
        bannerNumber.text = numeroDe(channel).takeIf { it > 0 }?.toString().orEmpty()
        bannerChannel.text = channel.name
        val fonte = channel.sources.getOrNull(sourceIndex)
        // No lugar de "fonte 1/3 · servidor", o que o controle faz aqui.
        bannerSource.text = if (fonte == null) "" else getString(if (Vr.ativo) R.string.vr_banner_dica else R.string.banner_dica)
        bannerSource.visibility =
            if (bannerSource.text.isNullOrEmpty()) View.GONE else View.VISIBLE
        if (channel.logo != null) bannerLogo.load(channel.logo)
        else { bannerLogo.dispose(); bannerLogo.setImageDrawable(null) }

        val now = System.currentTimeMillis()
        val pair = Epg.nowNext(channel.name, now)
        val onAir = pair?.first
        if (onAir == null) {
            bannerProgramme.visibility = View.GONE
            bannerProgress.visibility = View.GONE
            bannerTimes.visibility = View.GONE
            return
        }
        bannerProgramme.visibility = View.VISIBLE
        bannerProgress.visibility = View.VISIBLE
        bannerTimes.visibility = View.VISIBLE
        bannerProgramme.text = listOfNotNull(onAir.title, onAir.shortDetail).joinToString(" · ")
        bannerProgress.progress = (onAir.progress(now) * 1000).toInt()
        val remaining = ((onAir.stop - now) / 60_000L).coerceAtLeast(0)
        val times = getString(
            R.string.times, clock.format(Date(onAir.start)), clock.format(Date(onAir.stop)), remaining)
        bannerTimes.text = pair.second?.let { "$times   ·   ${getString(R.string.up_next, it.title)}" }
            ?: times
    }

    private fun revealBanner() {
        if (listPanel.visibility == View.VISIBLE) return
        updateBanner()
        handler.removeCallbacks(hideBanner)
        if (banner.visibility != View.VISIBLE) {
            banner.alpha = 0f
            banner.visibility = View.VISIBLE
            banner.animate().alpha(1f).setDuration(180).start()
        } else {
            banner.alpha = 1f
        }
        handler.postDelayed(hideBanner, BANNER_MS)
    }

    private fun showStatus(text: String) {
        status.text = text
        status.visibility = View.VISIBLE
    }

    /// Rádio segue tocando em janela quando a pessoa sai do app. Só a rádio:
    /// canal e filme continuam parando ao sair. Muito TV Box não tem janela
    /// flutuante; nesses, nada muda.
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O &&
            packageManager.hasSystemFeature(android.content.pm.PackageManager.FEATURE_PICTURE_IN_PICTURE) &&
            isModoRadio && !isInPictureInPictureMode && ::player.isInitialized && player.isPlaying) {
            runCatching { enterPictureInPictureMode(android.app.PictureInPictureParams.Builder().build()) }
        }
    }

    override fun onStop() {
        super.onStop()
        emSegundoPlano = true
        player.playWhenReady = false
    }

    override fun onStart() {
        super.onStart()
        emSegundoPlano = false
        if (::player.isInitialized) player.playWhenReady = true
        // Voltando das configurações com a permissão já ligada (nos aparelhos
        // em que o sistema não matou o app no caminho), retoma a atualização.
        if (jaIniciou) atualizacao.retomarSePendente()
        jaIniciou = true
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        mediaSession?.release()
        player.release()
        super.onDestroy()
    }

    // MARK: - Voz (Assistant / Alexa)

    /**
     * Player de fachada só para a sessão de mídia.
     *
     * O de verdade nunca tem "próximo item" — cada canal é uma troca de fonte,
     * não uma playlist — então por padrão a sessão esconde os comandos de
     * pular. Aqui eles ficam sempre disponíveis e vão direto para `play`, que
     * é como "próximo/anterior canal" por voz chega a valer.
     */
    private inner class ChannelPlayer(player: Player) : ForwardingPlayer(player) {
        override fun isCommandAvailable(command: Int) = when (command) {
            Player.COMMAND_SEEK_TO_NEXT, Player.COMMAND_SEEK_TO_NEXT_MEDIA_ITEM,
            Player.COMMAND_SEEK_TO_PREVIOUS, Player.COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM -> true
            // Ao vivo não pausa — nem pelo Assistant, nem pelo controle de outro app.
            Player.COMMAND_PLAY_PAUSE -> false
            else -> super.isCommandAvailable(command)
        }

        override fun getAvailableCommands(): Player.Commands = super.getAvailableCommands()
            .buildUpon()
            .addAll(
                Player.COMMAND_SEEK_TO_NEXT, Player.COMMAND_SEEK_TO_NEXT_MEDIA_ITEM,
                Player.COMMAND_SEEK_TO_PREVIOUS, Player.COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM)
            .remove(Player.COMMAND_PLAY_PAUSE)
            .build()

        override fun pause() {}

        override fun hasNextMediaItem() = true
        override fun hasPreviousMediaItem() = true
        override fun seekToNext() = play((current + 1) % ordered.size)
        override fun seekToNextMediaItem() = play((current + 1) % ordered.size)
        override fun seekToPrevious() = play((current - 1 + ordered.size) % ordered.size)
        override fun seekToPreviousMediaItem() = play((current - 1 + ordered.size) % ordered.size)
    }

    /**
     * "Abrir/tocar <canal>" por voz chega aqui, disfarçado de pedido de item
     * de mídia — é como o Media3 traduz `playFromSearch` do Assistant e da
     * Alexa. Como não existe playlist de verdade, o item nunca é aceito: ele
     * só carrega o texto da busca, que vira uma troca de canal, e a lista de
     * volta fica vazia.
     */
    private val sessionCallback = object : MediaSession.Callback {
        override fun onAddMediaItems(
            mediaSession: MediaSession,
            controller: MediaSession.ControllerInfo,
            mediaItems: MutableList<MediaItem>,
        ): ListenableFuture<MutableList<MediaItem>> {
            val pedido = mediaItems.firstOrNull()
            val termo = pedido?.requestMetadata?.searchQuery
                ?: pedido?.mediaId?.takeIf { it.isNotBlank() }
            val índice = termo?.let { canalPorVoz(it) }
            if (índice != null) runOnUiThread { play(índice) }
            return Futures.immediateFuture(mutableListOf())
        }
    }

    /// O Assistant e a Alexa nem sempre mandam só o nome: "abrir Globo",
    /// "assistir Globo", "canal 5" chegam inteiros na busca. Só o COMEÇO da
    /// frase é limpo — nunca a palavra toda, em qualquer lugar — porque um
    /// nome de canal pode legitimamente conter "a", "o" ou "de" ("A&E",
    /// "TV Cultura de Minas") e sumir junto se a limpeza fosse ampla.
    private val PREFIXO_DE_COMANDO = Regex(
        "^(abrir|abre|abra|tocar|toca|toque|assistir|assista|ver|veja|colocar|" +
            "coloca|coloque|mudar|muda|mude|ir|trocar|troca|troque|ligar|liga|" +
            "ligue)\\s+")
    private val PREFIXO_CANAL = Regex("^canal\\s+")

    /** Acha o canal cujo nome ou número mais se aproxima do que a pessoa falou. */
    private fun canalPorVoz(consulta: String): Int? {
        val alvo = normalizarVoz(consulta)
        if (alvo.isBlank()) return null
        val termo = alvo.replace(PREFIXO_DE_COMANDO, "").replace(PREFIXO_CANAL, "").trim()
            .ifBlank { alvo }

        // "5" ou "canal 5" — o mesmo número que aparece do lado do nome na
        // lista, um a mais que o índice porque a lista começa em 1.
        termo.toIntOrNull()?.let { numero ->
            indicePorNumero(numero).takeIf { it >= 0 }?.let { return it }
        }

        ordered.indexOfFirst { normalizarVoz(it.name) == termo }
            .takeIf { it >= 0 }?.let { return it }
        ordered.indexOfFirst { normalizarVoz(it.name).startsWith(termo) }
            .takeIf { it >= 0 }?.let { return it }
        ordered.indexOfFirst { normalizarVoz(it.name).contains(termo) }
            .takeIf { it >= 0 }?.let { return it }
        // "abra a Globo" sem o prefixo tirado por inteiro ainda acha, porque
        // desta vez é a consulta que precisa conter o nome, não o contrário.
        return ordered.indexOfFirst { termo.contains(normalizarVoz(it.name)) }.takeIf { it >= 0 }
    }

    private fun normalizarVoz(texto: String): String =
        java.text.Normalizer.normalize(texto, java.text.Normalizer.Form.NFD)
            .replace(Regex("\\p{Mn}+"), "")
            .lowercase()
            .replace(Regex("[^a-z0-9]+"), " ")
            .trim()
}

/** Channel rows: number, logo, name and whatever is on air right now. */
private class ChannelAdapter(
    private val onPick: (Int) -> Unit,
    private val onFavorite: (Int) -> Unit,
) : RecyclerView.Adapter<ChannelAdapter.Holder>() {

    private var items: List<Channel> = emptyList()
    private var selected = 0
    var numero: (Channel) -> Int = { 0 }

    fun submit(list: List<Channel>) {
        items = list
        notifyDataSetChanged()
    }

    /** Redesenha só o que está à vista, para o guia recém-chegado aparecer. */
    fun refresh() = notifyDataSetChanged()

    fun select(index: Int) {
        val previous = selected
        selected = index
        notifyItemChanged(previous)
        notifyItemChanged(index)
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val number: TextView = view.findViewById(R.id.number)
        val logo: ImageView = view.findViewById(R.id.logo)
        val name: TextView = view.findViewById(R.id.name)
        val programme: TextView = view.findViewById(R.id.programme)
        val progress: ProgressBar = view.findViewById(R.id.rowProgress)
        val star: TextView = view.findViewById(R.id.star)
        val secao: TextView = view.findViewById(R.id.secao)
        /// Guardado para não pedir ao Coil a mesma imagem a cada redesenho.
        var loaded: String? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val holder = Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_channel, parent, false))
        // Nos óculos um toque na linha já sintoniza. Focável no modo toque, o
        // primeiro toque só focaria — no TV Box o modo toque nunca entra.
        if (Vr.ativo) holder.itemView.isFocusableInTouchMode = false
        // O crescimento no foco é o que dá a sensação de resposta imediata sem
        // custar nada: é a GPU, não uma nova medição de layout.
        holder.itemView.setOnFocusChangeListener { view, hasFocus ->
            val scale = if (hasFocus) 1.03f else 1f
            view.animate().scaleX(scale).scaleY(scale).setDuration(120).start()
        }
        return holder
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val channel = items[position]
        holder.number.text = numero(channel).takeIf { it > 0 }?.toString().orEmpty()
        holder.name.text = channel.name
        holder.star.visibility =
            if (Favorites.contains(channel.name)) View.VISIBLE else View.GONE
        val rotulo = Categorias.rotulo(items, position)
        holder.secao.text = rotulo.orEmpty()
        holder.secao.visibility = if (rotulo == null) View.GONE else View.VISIBLE

        val now = System.currentTimeMillis()
        val onAir = Epg.nowNext(channel.name, now)?.first
        if (onAir == null) {
            holder.programme.visibility = View.GONE
            holder.progress.visibility = View.GONE
        } else {
            holder.programme.visibility = View.VISIBLE
            holder.programme.text = onAir.title
            holder.progress.visibility = View.VISIBLE
            holder.progress.progress = (onAir.progress(now) * 1000).toInt()
        }

        if (holder.loaded != channel.logo) {
            holder.loaded = channel.logo
            if (channel.logo != null) holder.logo.load(channel.logo)
            // Sem cancelar, o logo do canal que ocupava a linha antes chegava
            // depois e ficava num canal sem logo nenhum.
            else { holder.logo.dispose(); holder.logo.setImageDrawable(null) }
        }

        holder.itemView.setOnClickListener { onPick(position) }
        holder.itemView.setOnLongClickListener { onFavorite(position); true }
        holder.itemView.isSelected = position == selected
    }

    override fun getItemCount() = items.size
}
