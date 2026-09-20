package br.com.saimo.tv

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import coil.dispose
import coil.load
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
private const val HOLD_MS = 3_000L
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
private const val RETOMADAS_MAX = 3
/// Por quanto tempo a fonte continua sendo tratada como viva depois do último
/// quadro que ela entregou.
private const val CREDITO_DE_IMAGEM_MS = 30_000L
/// De quanto em quanto tempo o relógio do vídeo é conferido.
private const val VIGIA_MS = 2_000L

/**
 * The whole app: a channel list and a player, driven entirely by the remote.
 *
 * OK opens the list, OK again plays the focused channel, BACK closes it. Nothing
 * sits permanently over the picture — every overlay shows itself on a keypress
 * and leaves on its own.
 */
@UnstableApi
class MainActivity : AppCompatActivity() {

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
        vodEntrada.setOnClickListener {
            startActivity(Intent(this, VodActivity::class.java))
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

        Favorites.load(this)
        // A lista publicada de ontem já está em disco: abre com ela e troca
        // quando a de hoje chegar, para o app nunca abrir sem canais.
        Remote.loadCached(this)
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
                DefaultLoadControl.Builder()
                    // Meio segundo de buffer basta para começar a mostrar; o
                    // resto enche depois. Esperar dois segundos antes do
                    // primeiro quadro é o que fazia a troca de canal parecer
                    // lenta.
                    .setBufferDurationsMs(12_000, 40_000, 500, 2_000)
                    .setPrioritizeTimeOverSizeThresholds(true)
                    .build())
            .build().apply {
                playWhenReady = true
                addListener(playerListener)
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

        play(0)
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

    /** Pega a lista publicada sem tirar do ar o canal que está tocando. */
    private fun refreshCatalog() {
        lifecycleScope.launch(semDerrubar) {
            ofertarAtualizacao()
            if (!Remote.refresh(this@MainActivity)) return@launch
            reorder()
            updateBanner()
        }
    }

    /// Uma oferta por vez: a abertura e a volta das configurações podem pedir
    /// juntas, e dois diálogos empilhados confundem quem está no controle.
    private var ofertando = false
    private var baixando = false
    /// Distingue a primeira abertura (que já oferece pelo refreshCatalog) da
    /// volta de outra tela, como as configurações.
    private var jaIniciou = false

    /**
     * Oferece a versão nova, quando há uma.
     *
     * Pergunta em vez de trocar sozinho, e a checagem só acontece depois que o
     * canal já está no ar: atualizar é assunto de quem assiste, não do começo
     * da abertura.
     */
    private val procurarDeHoraEmHora = object : Runnable {
        override fun run() {
            if (!baixando) ofertarAtualizacao()
            handler.postDelayed(this, ATUALIZACAO_MS)
        }
    }

    private fun ofertarAtualizacao() {
        if (ofertando) return
        ofertando = true
        lifecycleScope.launch(semDerrubar) {
            val versao = try {
                Atualizacao.procurar(this@MainActivity)
            } finally {
                ofertando = false
            } ?: return@launch
            // A consulta leva segundos; mostrar diálogo numa tela que já
            // fechou é BadTokenException.
            if (isFinishing || isDestroyed) return@launch
            ofertando = true
            android.app.AlertDialog.Builder(this@MainActivity)
                .setTitle(getString(R.string.update_titulo, versao.numero))
                .setMessage(
                    listOf(getString(R.string.update_atual, BuildConfig.VERSION_NAME),
                           versao.notas.take(400))
                        .filter { it.isNotBlank() }.joinToString("\n\n"))
                .setPositiveButton(R.string.update_agora) { _, _ -> baixarAtualizacao(versao) }
                .setNegativeButton(R.string.update_depois) { _, _ ->
                    Atualizacao.adiar(versao)
                    Atualizacao.esquecerPendente(this@MainActivity)
                }
                .setNeutralButton(R.string.update_pular) { _, _ ->
                    Atualizacao.pular(this@MainActivity, versao)
                }
                .setOnDismissListener { ofertando = false }
                .show()
        }
    }

    private fun baixarAtualizacao(versao: Atualizacao.Versao) {
        Atualizacao.marcarPendente(this, versao)
        if (!podeInstalar()) {
            pedirPermissaoDeInstalar()
            return
        }
        if (baixando) return
        baixando = true
        showStatus(getString(R.string.update_baixando, 0))
        lifecycleScope.launch(semDerrubar) {
            try {
                val erro = Atualizacao.instalar(this@MainActivity, versao) { fracao ->
                    runOnUiThread {
                        showStatus(getString(R.string.update_baixando, (fracao * 100).toInt()))
                    }
                }
                showStatus(erro ?: getString(R.string.update_instalando))
            } finally {
                baixando = false
            }
        }
    }

    /// Do Android 8 em diante instalar APK pede a chave "apps desconhecidos"
    /// ligada para este app em particular; antes era uma chave só, do sistema,
    /// que a própria tela de instalação já oferece.
    private fun podeInstalar(): Boolean =
        Build.VERSION.SDK_INT < 26 || packageManager.canRequestPackageInstalls()

    /**
     * Explica antes de mandar para as configurações.
     *
     * Deixar o instalador descobrir sozinho dava na pior experiência possível:
     * a pessoa ligava a chave, o sistema matava o app por ter mudado a
     * permissão, e parecia que a TV tinha travado no meio da atualização.
     */
    private fun pedirPermissaoDeInstalar() {
        if (isFinishing || isDestroyed) return
        android.app.AlertDialog.Builder(this)
            .setTitle(R.string.update_permissao_titulo)
            .setMessage(R.string.update_permissao_texto)
            .setPositiveButton(R.string.update_permissao_abrir) { _, _ -> abrirPermissaoDeInstalar() }
            .setNegativeButton(R.string.update_depois) { _, _ ->
                Atualizacao.esquecerPendente(this)
            }
            .show()
    }

    /// Nem todo TV Box tem a tela específica do app; cai para a de segurança
    /// e, sem ela, para a raiz das configurações.
    private fun abrirPermissaoDeInstalar() {
        val tentativas = listOf(
            Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, "package:$packageName".toUri()),
            Intent(Settings.ACTION_SECURITY_SETTINGS),
            Intent(Settings.ACTION_SETTINGS))
        for (tentativa in tentativas) {
            if (runCatching { startActivity(tentativa) }.isSuccess) return
        }
        showStatus(getString(R.string.update_permissao_sem_tela))
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

    private fun play(index: Int, source: Int = 0, apósFalha: Boolean = false) {
        val mesmoCanal = ordered.getOrNull(current)?.name == ordered.getOrNull(index.coerceIn(ordered.indices))?.name
        current = index.coerceIn(ordered.indices)
        sourceIndex = source
        val channel = ordered[current]
        val chosen = channel.sources.getOrNull(sourceIndex) ?: channel.sources.first()
        tentativaDesde = android.os.SystemClock.elapsedRealtime()
        tocouAvisado = false
        bannerResolution.text = ""
        bannerResolution.visibility = View.GONE
        // Fonte nova, crédito zerado: o que a anterior entregou não vale para
        // ela. A retomada da mesma fonte não passa por aqui — ela só chama
        // `prepare()` — então zerar em toda abertura é o certo.
        retomadas = 0
        ultimaImagem = 0L
        ultimaPosicao = -1L
        if (!apósFalha) caiuAvisado = false
        // Falha passando para a próxima fonte, ou a reconexão do mesmo canal,
        // não é mais uma abertura; escolher canal ou fonte à mão é.
        Telemetria.comecou("live", channel.name, chosen.url, sourceIndex + 1,
            nova = !(apósFalha || (mesmoCanal && retries > 0)))

        // O aviso conta a tentativa inteira, não só o instante da troca: dizer
        // "fonte 1 falhou" por meio segundo e sumir não informa ninguém.
        showStatus(when {
            apósFalha -> getString(R.string.source_failed, sourceIndex,
                                   sourceIndex + 1, channel.sources.size)
            channel.sources.size > 1 ->
                getString(R.string.loading_source, sourceIndex + 1, channel.sources.size)
            else -> getString(R.string.loading)
        })
        handler.removeCallbacks(sourceTimeout)
        handler.postDelayed(sourceTimeout, SOURCE_TIMEOUT_MS)
        player.setMediaSource(Playback.mediaSource(this, chosen))
        player.prepare()
        player.playWhenReady = true

        adapter.select(current)
        updateBanner()
        revealBanner()
    }

    private val playerListener = object : Player.Listener {
        override fun onVideoSizeChanged(videoSize: VideoSize) {
            val rotulo = rotuloResolucao(videoSize.width, videoSize.height)
            bannerResolution.text = rotulo
            bannerResolution.visibility = if (rotulo.isEmpty()) View.GONE else View.VISIBLE
        }

        override fun onPlaybackStateChanged(state: Int) {
            if (state == Player.STATE_READY) {
                if (!tocouAvisado) {
                    tocouAvisado = true
                    ordered.getOrNull(current)?.let { canal ->
                        Telemetria.tocou("live", canal.name, canal.sources.getOrNull(sourceIndex)?.url.orEmpty(),
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

        override fun onPlayerError(error: PlaybackException) {
            handler.removeCallbacks(sourceTimeout)
            val channel = ordered[current]
            Telemetria.falhou("live", channel.name, channel.sources.getOrNull(sourceIndex)?.url.orEmpty(),
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
                player.seekToDefaultPosition()
                player.prepare()
                player.playWhenReady = true
                // Se a retomada também não trouxer imagem, o relógio desce para
                // a fonte seguinte sozinho.
                handler.postDelayed(sourceTimeout, SOURCE_TIMEOUT_MS)
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
                showStatus(getString(R.string.unavailable))
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
            return super.onKeyDown(keyCode, event)
        }
        val listOpen = listPanel.visibility == View.VISIBLE
        return when (keyCode) {
            // Um toque no OK mostra o que está no ar; segurar três segundos é
            // que abre a lista. O toque é o gesto que se dá o tempo todo, então
            // ele fica com a ação que não tira o vídeo da frente.
            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                if (listOpen) return super.onKeyDown(keyCode, event)
                if (event == null || event.repeatCount == 0) {
                    // Com a faixa já à vista, o segundo OK escolhe a fonte — é o
                    // único jeito com um controle que só tem direcional e OK.
                    if (banner.visibility == View.VISIBLE && banner.alpha > 0.5f) {
                        escolherFonte(current)
                        return true
                    }
                    heldOpen = false
                    revealBanner()
                    handler.postDelayed(openOnHold, HOLD_MS)
                }
                true
            }
            KeyEvent.KEYCODE_DPAD_LEFT -> {
                if (!listOpen) { openList(); true } else super.onKeyDown(keyCode, event)
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
                    channels.hasFocus() -> { moverFoco(-1); true }
                    else -> super.onKeyDown(keyCode, event)
                }
            }
            KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_CHANNEL_DOWN, KeyEvent.KEYCODE_MEDIA_NEXT -> {
                when {
                    !listOpen -> { play((current + 1) % ordered.size); true }
                    channels.hasFocus() -> { moverFoco(1); true }
                    else -> super.onKeyDown(keyCode, event)
                }
            }
            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE -> {
                player.playWhenReady = !player.playWhenReady; true
            }
            KeyEvent.KEYCODE_DPAD_RIGHT, KeyEvent.KEYCODE_GUIDE, KeyEvent.KEYCODE_INFO -> {
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
            KeyEvent.KEYCODE_MENU, KeyEvent.KEYCODE_SETTINGS, KeyEvent.KEYCODE_CAPTIONS -> {
                if (listOpen && keyCode == KeyEvent.KEYCODE_MENU) {
                    Favorites.toggle(this, ordered.getOrNull(listaFoco)?.name ?: ordered[current].name)
                    reorder()
                } else {
                    TrackMenu.show(this, player)
                }
                true
            }
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
                // Relógio para trás é linha do tempo nova — a volta para a
                // borda do ao vivo faz isso. Recomeça a contagem dali.
                if (pos < ultimaPosicao - 1_000) ultimaPosicao = pos
                if (player.isPlaying && pos > ultimaPosicao + 500) {
                    ultimaPosicao = pos
                    ultimaImagem = android.os.SystemClock.elapsedRealtime()
                    retomadas = 0
                }
            }
            handler.postDelayed(this, VIGIA_MS)
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
        Telemetria.falhou("live", channel.name, channel.sources.getOrNull(sourceIndex)?.url.orEmpty(),
            sourceIndex + 1, "sem imagem em ${SOURCE_TIMEOUT_MS / 1000} s")
        if (sourceIndex + 1 < channel.sources.size) {
            play(current, sourceIndex + 1, apósFalha = true)
        } else {
            avisarQueCaiu(channel)
            showStatus(getString(R.string.unavailable))
        }
    }

    private fun avisarQueCaiu(channel: Channel) {
        if (caiuAvisado) return
        caiuAvisado = true
        Telemetria.caiu("live", channel.name, channel.sources.size)
    }

    private var heldOpen = false
    private val openOnHold = Runnable {
        heldOpen = true
        openList()
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
            handler.removeCallbacks(openOnHold)
            if (listPanel.visibility != View.VISIBLE || heldOpen) return true
        }
        return super.onKeyUp(keyCode, event)
    }

    private var typed = StringBuilder()
    private val commitTyped = Runnable {
        val entered = typed.toString()
        typed = StringBuilder()
        status.visibility = View.GONE
        if (Unlock.consume(entered)) {
            reorder()
            return@Runnable
        }
        val number = entered.toIntOrNull()
        if (number != null && number in 1..ordered.size) play(number - 1)
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
            reorder()
            focusRow(current)
            return
        }
        val number = entered.toIntOrNull()
        if (number != null && number in 1..ordered.size) {
            closeList()
            play(number - 1)
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
        ordered = FontesDesativadas.peneirarCanais(Categorias.ordenar(Unlock.channels()))
        val found = ordered.indexOfFirst { it.name == playing }
        // Ao trancar com um desses no ar, o nome ficaria à vista na faixa;
        // volta para o primeiro canal comum antes de a lista encolher.
        if (found < 0 && playing != null) play(0) else current = found.coerceAtLeast(0)
        adapter.submit(ordered)
        adapter.select(current)
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
        handler.removeCallbacks(openOnHold)
        val itens = canal.sources.mapIndexed { i, fonte ->
            "${fonte.quality ?: "Qualidade não informada"} · " + getString(R.string.fontes_item, i + 1,
                fonte.url.toUri().host?.removePrefix("www.") ?: fonte.url.take(40))
        }.toTypedArray()
        val marcada = if (index == current) sourceIndex else -1
        android.app.AlertDialog.Builder(this)
            .setTitle(getString(R.string.fontes_titulo, canal.name))
            .setSingleChoiceItems(itens, marcada) { dialogo, escolhida ->
                dialogo.dismiss()
                if (listPanel.visibility == View.VISIBLE) closeList()
                retries = 0
                play(index, escolhida)
            }
            .show()
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

    // MARK: - Overlays

    private fun openList() {
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
    private fun moverFoco(delta: Int) {
        val alvo = listaFoco + delta
        if (alvo < 0) { vodEntrada.requestFocus(); return }
        if (alvo >= ordered.size) return
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
        bannerNumber.text = (current + 1).toString()
        bannerChannel.text = channel.name
        val fonte = channel.sources.getOrNull(sourceIndex)
        bannerSource.text = fonte?.let {
            getString(R.string.source_label, sourceIndex + 1, channel.sources.size,
                it.url.toUri().host ?: it.url.take(40))
        }.orEmpty()
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

    override fun onStop() {
        super.onStop()
        player.playWhenReady = false
    }

    override fun onStart() {
        super.onStart()
        if (::player.isInitialized) player.playWhenReady = true
        // Voltando das configurações com a permissão já ligada (nos aparelhos
        // em que o sistema não matou o app no caminho), retoma a atualização.
        if (jaIniciou && !baixando && Atualizacao.temPendente(this) && podeInstalar()) {
            ofertarAtualizacao()
        }
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
            else -> super.isCommandAvailable(command)
        }

        override fun getAvailableCommands(): Player.Commands = super.getAvailableCommands()
            .buildUpon()
            .addAll(
                Player.COMMAND_SEEK_TO_NEXT, Player.COMMAND_SEEK_TO_NEXT_MEDIA_ITEM,
                Player.COMMAND_SEEK_TO_PREVIOUS, Player.COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM)
            .build()

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
            if (numero in 1..ordered.size) return numero - 1
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
        holder.number.text = (position + 1).toString()
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
