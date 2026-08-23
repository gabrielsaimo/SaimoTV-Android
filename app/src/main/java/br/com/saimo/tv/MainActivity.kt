package br.com.saimo.tv

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.upstream.DefaultBandwidthMeter
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
private const val HOLD_MS = 3_000L
/// Uma fonte viva entrega imagem bem antes disto. Passou daqui sem tocar, é
/// fonte morta que não deu erro — e sem este prazo o canal ficaria carregando
/// para sempre em vez de descer para a próxima.
private const val SOURCE_TIMEOUT_MS = 12_000L

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
    private lateinit var playerView: PlayerView
    private lateinit var listPanel: View
    private lateinit var channels: RecyclerView
    private lateinit var listCount: TextView
    private lateinit var banner: View
    private lateinit var bannerLogo: ImageView
    private lateinit var bannerNumber: TextView
    private lateinit var bannerChannel: TextView
    private lateinit var bannerProgramme: TextView
    private lateinit var bannerProgress: ProgressBar
    private lateinit var bannerSource: TextView
    private lateinit var bannerTimes: TextView
    private lateinit var status: TextView
    private lateinit var listHeader: View
    private lateinit var numpad: View
    private lateinit var numpadValue: TextView

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
                if (listPanel.visibility == View.VISIBLE) adapter.refresh()
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
        bannerProgress = findViewById(R.id.bannerProgress)
        bannerSource = findViewById(R.id.bannerSource)
        bannerTimes = findViewById(R.id.bannerTimes)
        status = findViewById(R.id.status)
        listHeader = findViewById(R.id.listHeader)
        numpad = findViewById(R.id.numpad)
        numpadValue = findViewById(R.id.numpadValue)

        listHeader.setOnClickListener { openNumpad() }
        findViewById<View>(R.id.vodEntrada).setOnClickListener {
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
            }
        playerView.player = player
        playerView.useController = false

        play(0)
        refreshCatalog()
        handler.postDelayed(tick, TICK_MS)
        // Rede é uma só: 35 downloads do guia disputando banda com o canal que
        // acabou de abrir travam a imagem nos primeiros segundos. O guia entra
        // quando o vídeo já está rodando, ou em três segundos se não rodar.
        handler.postDelayed({ startGuide() }, 3_000)
    }

    /** Pega a lista publicada sem tirar do ar o canal que está tocando. */
    private fun refreshCatalog() {
        lifecycleScope.launch {
            if (!Remote.refresh(this@MainActivity)) return@launch
            reorder()
            updateBanner()
        }
    }

    private fun startGuide() {
        if (guideStarted) return
        guideStarted = true
        lifecycleScope.launch {
            Epg.load(this@MainActivity) {
                if (listPanel.visibility == View.VISIBLE) adapter.refresh()
                updateBanner()
            }
        }
    }

    // MARK: - Playback

    private fun play(index: Int, source: Int = 0, apósFalha: Boolean = false) {
        current = index.coerceIn(ordered.indices)
        sourceIndex = source
        val channel = ordered[current]
        val chosen = channel.sources.getOrNull(sourceIndex) ?: channel.sources.first()

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
        override fun onPlaybackStateChanged(state: Int) {
            if (state == Player.STATE_READY) {
                retries = 0
                handler.removeCallbacks(sourceTimeout)
                status.visibility = View.GONE
                startGuide()
            }
        }

        override fun onPlayerError(error: PlaybackException) {
            handler.removeCallbacks(sourceTimeout)
            val channel = ordered[current]
            // Each channel lists its sources in preference order; a dead or
            // expired link falls through to the next before giving up.
            if (sourceIndex + 1 < channel.sources.size) {
                play(current, sourceIndex + 1, apósFalha = true)
                return
            }
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
            KeyEvent.KEYCODE_DPAD_UP, KeyEvent.KEYCODE_CHANNEL_UP, KeyEvent.KEYCODE_MEDIA_PREVIOUS -> {
                if (listOpen) super.onKeyDown(keyCode, event)
                else { play((current - 1 + ordered.size) % ordered.size); true }
            }
            KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_CHANNEL_DOWN, KeyEvent.KEYCODE_MEDIA_NEXT -> {
                if (listOpen) super.onKeyDown(keyCode, event)
                else { play((current + 1) % ordered.size); true }
            }
            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE -> {
                player.playWhenReady = !player.playWhenReady; true
            }
            KeyEvent.KEYCODE_DPAD_RIGHT, KeyEvent.KEYCODE_GUIDE, KeyEvent.KEYCODE_INFO -> {
                if (!listOpen) { openGuide(); true } else super.onKeyDown(keyCode, event)
            }
            in KeyEvent.KEYCODE_0..KeyEvent.KEYCODE_9 -> {
                // Digitar o número é a busca que existe num controle remoto:
                // teclado virtual para texto livre seria pior de usar.
                typeDigit(keyCode - KeyEvent.KEYCODE_0); true
            }
            KeyEvent.KEYCODE_MENU -> {
                Favorites.toggle(this, ordered[current].name); reorder(); revealBanner(); true
            }
            else -> {
                if (!listOpen) revealBanner()
                super.onKeyDown(keyCode, event)
            }
        }
    }

    private val sourceTimeout = Runnable {
        val channel = ordered.getOrNull(current) ?: return@Runnable
        if (sourceIndex + 1 < channel.sources.size) {
            play(current, sourceIndex + 1, apósFalha = true)
        } else {
            showStatus(getString(R.string.unavailable))
        }
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
        ordered = Favorites.sort(Unlock.channels())
        val found = ordered.indexOfFirst { it.name == playing }
        // Ao trancar com um desses no ar, o nome ficaria à vista na faixa;
        // volta para o primeiro canal comum antes de a lista encolher.
        if (found < 0 && playing != null) play(0) else current = found.coerceAtLeast(0)
        adapter.submit(ordered)
        adapter.select(current)
        listCount.text = ordered.size.toString()
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
        // sistema o escolhe sozinho quando o painel aparece. Pedir o foco de
        // novo ao fim da animação garante que ele pouse no canal atual.
        listPanel.animate().translationX(0f).setDuration(180)
            .withEndAction { focusRow(current) }.start()
        channels.post { focusRow(current) }
    }

    /**
     * Scrolls to a row and focuses it, retrying until the holder exists.
     *
     * A scroll only finishes on the next layout pass, so asking for the holder
     * straight away finds nothing, and the list would open with nothing focused
     * — a remote that does nothing.
     */
    private fun focusRow(index: Int, attempts: Int = 8) {
        (channels.layoutManager as LinearLayoutManager)
            .scrollToPositionWithOffset(index, channels.height / 3)
        channels.post {
            if (channels.findViewHolderForAdapterPosition(index)?.itemView?.requestFocus() == true) return@post
            if (attempts > 0) focusRow(index, attempts - 1)
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
        if (channel.logo != null) bannerLogo.load(channel.logo) else bannerLogo.setImageDrawable(null)

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
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        player.release()
        super.onDestroy()
    }
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
            else holder.logo.setImageDrawable(null)
        }

        holder.itemView.setOnClickListener { onPick(position) }
        holder.itemView.setOnLongClickListener { onFavorite(position); true }
        holder.itemView.isSelected = position == selected
    }

    override fun getItemCount() = items.size
}
