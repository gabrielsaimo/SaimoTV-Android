package br.com.saimo.tv

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * The full guide.
 *
 * The macOS build draws a channels-by-time grid, which works with a pointer.
 * On a television, moving a cursor around two axes with a D-pad is slow, so the
 * same information is split: channels on the left, and the focused channel's
 * schedule on the right. Left and right move between the two, up and down move
 * within, and OK on a channel tunes to it.
 */
class GuideActivity : AppCompatActivity() {

    private lateinit var channelList: RecyclerView
    private lateinit var programmeList: RecyclerView
    private lateinit var info: TextView
    private lateinit var clockLabel: TextView

    private val handler = Handler(Looper.getMainLooper())
    private val clock = SimpleDateFormat("HH:mm", Locale("pt", "BR"))

    private var channels: List<Channel> = emptyList()
    private val programmes = ProgrammeAdapter()
    private var focused = 0
    private var shown = -1

    /// O relógio e a linha "no ar" precisam acompanhar a passagem do tempo; sem
    /// isto a grade mostra o programa que já acabou enquanto ela fica aberta.
    private val tick = object : Runnable {
        override fun run() {
            clockLabel.text = clock.format(Date())
            // O guia pode continuar chegando com a tela aberta; se a grade do
            // canal focado mudou de tamanho, é dado novo e vale remontar.
            val schedule = channels.getOrNull(focused)?.let { Epg.schedule(it.name) }
            if (schedule != null && schedule.size != shown) show(focused)
            else if (Epg.tick()) programmes.refresh()
            info.text = getString(R.string.guide_info, Epg.channelsWithGuide, Remote.channels.size)
            handler.postDelayed(this, 20_000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        channelList = findViewById(R.id.guideChannels)
        programmeList = findViewById(R.id.guideProgrammes)
        info = findViewById(R.id.guideInfo)
        clockLabel = findViewById(R.id.guideClock)

        channels = Favorites.sort(Unlock.channels())
        val startAt = intent.getStringExtra(EXTRA_CHANNEL)
            ?.let { name -> channels.indexOfFirst { it.name == name } }
            ?.takeIf { it >= 0 } ?: 0

        val adapter = ChannelStripAdapter(
            channels,
            onFocus = { focused = it; show(it) },
            onPick = { tune(it) })
        // OK sobre um programa sintoniza o canal daquela programação: quem
        // navegou até ali quer ver o canal, não voltar à coluna da esquerda.
        programmes.onPick = { tune(focused) }

        channelList.layoutManager = LinearLayoutManager(this)
        channelList.adapter = adapter
        channelList.setHasFixedSize(true)
        programmeList.layoutManager = LinearLayoutManager(this)
        programmeList.adapter = programmes
        programmeList.setHasFixedSize(true)

        info.text = getString(R.string.guide_info, Epg.channelsWithGuide, Remote.channels.size)
        clockLabel.text = clock.format(Date())
        show(startAt)

        // Opening centred on what is being watched: with dozens of rows, landing
        // at the top means hunting for your own channel every time.
        focused = startAt
        channelList.post { focusRow(startAt) }
        handler.postDelayed(tick, 20_000)
    }

    /**
     * Scrolls to a row and focuses it, retrying until the holder exists.
     *
     * A scroll only finishes on the next layout pass, so asking for the holder
     * straight away finds nothing — and the screen would open with nothing
     * focused, which on a television means a remote that does nothing at all.
     */
    private fun focusRow(index: Int, attempts: Int = 8) {
        (channelList.layoutManager as LinearLayoutManager)
            .scrollToPositionWithOffset(index, channelList.height / 3)
        channelList.post {
            if (channelList.findViewHolderForAdapterPosition(index)?.itemView?.requestFocus() == true) return@post
            if (attempts > 0) focusRow(index, attempts - 1)
            else channelList.getChildAt(0)?.requestFocus()
        }
    }

    private fun tune(index: Int) {
        val channel = channels.getOrNull(index) ?: return
        setResult(Activity.RESULT_OK, Intent().putExtra(EXTRA_CHANNEL, channel.name))
        finish()
    }

    /** Rede de segurança: se nada ficou focado, o controle não pode morrer. */
    override fun onKeyDown(keyCode: Int, event: android.view.KeyEvent?): Boolean {
        if (currentFocus == null) {
            focusRow(focused)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun show(index: Int) {
        val channel = channels.getOrNull(index) ?: return
        val schedule = Epg.schedule(channel.name)
        shown = schedule.size
        programmes.submit(schedule)
        // Abre no que está no ar, não às seis da manhã de ontem.
        val now = System.currentTimeMillis()
        val onAir = schedule.indexOfFirst { it.isOnAir(now) }.takeIf { it >= 0 } ?: 0
        programmeList.post {
            (programmeList.layoutManager as LinearLayoutManager)
                .scrollToPositionWithOffset(onAir, 0)
        }
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    companion object {
        const val EXTRA_CHANNEL = "channel"
    }
}

/** Channel column of the guide. */
private class ChannelStripAdapter(
    private val items: List<Channel>,
    private val onFocus: (Int) -> Unit,
    private val onPick: (Int) -> Unit,
) : RecyclerView.Adapter<ChannelStripAdapter.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val number: TextView = view.findViewById(R.id.number)
        val logo: ImageView = view.findViewById(R.id.logo)
        val name: TextView = view.findViewById(R.id.name)
        val programme: TextView = view.findViewById(R.id.programme)
        val progress: ProgressBar = view.findViewById(R.id.rowProgress)
        val star: TextView = view.findViewById(R.id.star)
        var loaded: String? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_channel, parent, false))

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

        holder.itemView.setOnFocusChangeListener { _, hasFocus -> if (hasFocus) onFocus(position) }
        holder.itemView.setOnClickListener { onPick(position) }
    }

    override fun getItemCount() = items.size
}

/** Schedule of the focused channel. */
private class ProgrammeAdapter : RecyclerView.Adapter<ProgrammeAdapter.Holder>() {

    private var items: List<Programme> = emptyList()
    private val clock = SimpleDateFormat("HH:mm", Locale("pt", "BR"))
    var onPick: (() -> Unit)? = null

    fun submit(list: List<Programme>) {
        items = list
        notifyDataSetChanged()
    }

    fun refresh() = notifyDataSetChanged()

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val time: TextView = view.findViewById(R.id.time)
        val poster: ImageView = view.findViewById(R.id.poster)
        val title: TextView = view.findViewById(R.id.programmeTitle)
        val detail: TextView = view.findViewById(R.id.programmeDetail)
        val progress: ProgressBar = view.findViewById(R.id.programmeProgress)
        val onAir: TextView = view.findViewById(R.id.onAir)
        var loaded: String? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_programme, parent, false))

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val programme = items[position]
        val now = System.currentTimeMillis()
        holder.time.text = clock.format(Date(programme.start))
        holder.title.text = programme.title
        holder.detail.text = programme.shortDetail.orEmpty()
        holder.detail.visibility =
            if (holder.detail.text.isNullOrEmpty()) View.GONE else View.VISIBLE

        val live = programme.isOnAir(now)
        holder.onAir.visibility = if (live) View.VISIBLE else View.GONE
        holder.progress.visibility = if (live) View.VISIBLE else View.GONE
        if (live) holder.progress.progress = (programme.progress(now) * 1000).toInt()

        if (holder.loaded != programme.poster) {
            holder.loaded = programme.poster
            if (programme.poster != null) holder.poster.load(programme.poster)
            else holder.poster.setImageDrawable(null)
        }
        holder.poster.visibility = if (programme.poster != null) View.VISIBLE else View.GONE
        holder.itemView.setOnClickListener { onPick?.invoke() }
    }

    override fun getItemCount() = items.size
}
