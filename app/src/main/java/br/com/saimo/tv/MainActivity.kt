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
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
import kotlin.math.min
import kotlin.math.pow

/**
 * The whole app: a channel list and a player, driven entirely by the remote.
 *
 * OK opens the list, OK again plays the focused channel, BACK closes it. There
 * is nothing else to learn and nothing that can be misconfigured.
 */
private const val REQUEST_GUIDE = 1

@UnstableApi
class MainActivity : AppCompatActivity() {

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var listPanel: View
    private lateinit var channels: RecyclerView
    private lateinit var banner: View
    private lateinit var bannerChannel: TextView
    private lateinit var bannerProgramme: TextView
    private lateinit var status: TextView
    private lateinit var hint: View

    private val handler = Handler(Looper.getMainLooper())
    private val hideBanner = Runnable { banner.visibility = View.GONE }
    /// Sem isto uma linha só mudaria quando outra coisa a redesenhasse, e o
    /// programa atual ficaria congelado depois de terminar.
    private val tick = object : Runnable {
        override fun run() {
            if (Epg.tick()) {
                adapter.notifyDataSetChanged()
                updateBanner()
            }
            handler.postDelayed(this, 15_000)
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
        banner = findViewById(R.id.banner)
        bannerChannel = findViewById(R.id.bannerChannel)
        bannerProgramme = findViewById(R.id.bannerProgramme)
        status = findViewById(R.id.status)
        hint = findViewById(R.id.hint)

        Favorites.load(this)
        channels.layoutManager = LinearLayoutManager(this)
        channels.adapter = adapter
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
                    .setBufferDurationsMs(15_000, 50_000, 1_500, 3_000)
                    .build())
            .build().apply {
            playWhenReady = true
            addListener(playerListener)
        }
        playerView.player = player
        playerView.useController = false

        play(0)
        handler.postDelayed(tick, 15_000)

        lifecycleScope.launch {
            Epg.load(this@MainActivity) {
                adapter.notifyDataSetChanged()
                updateBanner()
            }
        }
    }

    // MARK: - Playback

    private fun play(index: Int, source: Int = 0) {
        current = index.coerceIn(ordered.indices)
        sourceIndex = source
        val channel = ordered[current]
        val chosen = channel.sources.getOrNull(sourceIndex) ?: channel.sources.first()

        showStatus(getString(R.string.loading))
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
                status.visibility = View.GONE
            }
        }

        override fun onPlayerError(error: PlaybackException) {
            val channel = ordered[current]
            // Each channel lists its sources in preference order; a dead or
            // expired link falls through to the next before giving up.
            if (sourceIndex + 1 < channel.sources.size) {
                play(current, sourceIndex + 1)
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
        val listOpen = listPanel.visibility == View.VISIBLE
        return when (keyCode) {
            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                if (!listOpen) { openList(); true } else super.onKeyDown(keyCode, event)
            }
            KeyEvent.KEYCODE_DPAD_LEFT -> {
                if (!listOpen) { openList(); true } else super.onKeyDown(keyCode, event)
            }
            KeyEvent.KEYCODE_BACK, KeyEvent.KEYCODE_ESCAPE -> {
                if (listOpen) { closeList(); true } else super.onKeyDown(keyCode, event)
            }
            KeyEvent.KEYCODE_CHANNEL_UP, KeyEvent.KEYCODE_MEDIA_NEXT -> {
                play((current + 1) % ordered.size); true
            }
            KeyEvent.KEYCODE_CHANNEL_DOWN, KeyEvent.KEYCODE_MEDIA_PREVIOUS -> {
                play((current - 1 + ordered.size) % ordered.size); true
            }
            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE -> {
                player.playWhenReady = !player.playWhenReady; true
            }
            KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_GUIDE, KeyEvent.KEYCODE_INFO -> {
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

    private var typed = StringBuilder()
    private val commitTyped = Runnable {
        val number = typed.toString().toIntOrNull()
        typed = StringBuilder()
        status.visibility = View.GONE
        if (number != null && number in 1..ordered.size) play(number - 1)
    }

    private fun typeDigit(digit: Int) {
        if (typed.length >= 3) typed = StringBuilder()
        typed.append(digit)
        showStatus(getString(R.string.channel_number, typed.toString()))
        handler.removeCallbacks(commitTyped)
        handler.postDelayed(commitTyped, 1_200)
    }

    private fun reorder() {
        val playing = ordered.getOrNull(current)?.name
        ordered = Favorites.sort(CATALOG)
        current = ordered.indexOfFirst { it.name == playing }.coerceAtLeast(0)
        adapter.submit(ordered)
        adapter.select(current)
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

    private fun openList() {
        listPanel.visibility = View.VISIBLE
        hint.visibility = View.GONE
        channels.post {
            channels.scrollToPosition(current)
            channels.findViewHolderForAdapterPosition(current)?.itemView?.requestFocus()
                ?: channels.getChildAt(0)?.requestFocus()
        }
    }

    private fun closeList() {
        listPanel.visibility = View.GONE
        hint.visibility = View.VISIBLE
        playerView.requestFocus()
        revealBanner()
    }

    // MARK: - Overlays

    private fun updateBanner() {
        val channel = ordered[current]
        bannerChannel.text = channel.name
        val onAir = Epg.nowNext(channel.name)?.first
        bannerProgramme.text = onAir?.let {
            listOfNotNull(it.title, it.shortDetail).joinToString(" · ")
        } ?: ""
        bannerProgramme.visibility = if (bannerProgramme.text.isNullOrEmpty()) View.GONE else View.VISIBLE
    }

    private fun revealBanner() {
        banner.visibility = View.VISIBLE
        handler.removeCallbacks(hideBanner)
        handler.postDelayed(hideBanner, 6_000)
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
        // tick pára junto
        player.release()
        super.onDestroy()
    }
}

/** Channel rows: logo, name and whatever is on air right now. */
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

    fun select(index: Int) {
        val previous = selected
        selected = index
        notifyItemChanged(previous)
        notifyItemChanged(index)
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val logo: ImageView = view.findViewById(R.id.logo)
        val name: TextView = view.findViewById(R.id.name)
        val programme: TextView = view.findViewById(R.id.programme)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_channel, parent, false))

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val channel = items[position]
        holder.name.text =
            if (Favorites.contains(channel.name)) "★ ${channel.name}" else channel.name
        val onAir = Epg.nowNext(channel.name)?.first
        holder.programme.text = onAir?.let {
            listOfNotNull(it.title, it.shortDetail).joinToString(" · ")
        } ?: ""
        holder.programme.visibility =
            if (holder.programme.text.isNullOrEmpty()) View.GONE else View.VISIBLE

        if (channel.logo != null) holder.logo.load(channel.logo) else holder.logo.setImageDrawable(null)

        holder.itemView.setOnClickListener { onPick(position) }
        holder.itemView.setOnLongClickListener { onFavorite(position); true }
        holder.itemView.isSelected = position == selected
    }

    override fun getItemCount() = items.size
}
