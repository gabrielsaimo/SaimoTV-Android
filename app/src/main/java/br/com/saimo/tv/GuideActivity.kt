package br.com.saimo.tv

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

    private var channels: List<Channel> = emptyList()
    private val programmes = ProgrammeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        channelList = findViewById(R.id.guideChannels)
        programmeList = findViewById(R.id.guideProgrammes)
        info = findViewById(R.id.guideInfo)

        channels = Favorites.sort(CATALOG)
        val startAt = intent.getStringExtra(EXTRA_CHANNEL)
            ?.let { name -> channels.indexOfFirst { it.name == name } }
            ?.takeIf { it >= 0 } ?: 0

        val adapter = ChannelStripAdapter(
            channels,
            onFocus = { show(it) },
            onPick = { index ->
                setResult(Activity.RESULT_OK, Intent().putExtra(EXTRA_CHANNEL, channels[index].name))
                finish()
            })

        channelList.layoutManager = LinearLayoutManager(this)
        channelList.adapter = adapter
        programmeList.layoutManager = LinearLayoutManager(this)
        programmeList.adapter = programmes

        info.text = getString(R.string.guide_info, Epg.channelsWithGuide, CATALOG.size)
        show(startAt)

        // Opening centred on what is being watched: with dozens of rows, landing
        // at the top means hunting for your own channel every time.
        channelList.post {
            (channelList.layoutManager as LinearLayoutManager)
                .scrollToPositionWithOffset(startAt, 200)
            channelList.findViewHolderForAdapterPosition(startAt)?.itemView?.requestFocus()
        }
    }

    private fun show(index: Int) {
        val channel = channels.getOrNull(index) ?: return
        programmes.submit(Epg.schedule(channel.name))
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
        val logo: ImageView = view.findViewById(R.id.logo)
        val name: TextView = view.findViewById(R.id.name)
        val programme: TextView = view.findViewById(R.id.programme)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_channel, parent, false))

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val channel = items[position]
        holder.name.text = if (Favorites.contains(channel.name)) "★ ${channel.name}" else channel.name
        val onAir = Epg.nowNext(channel.name)?.first
        holder.programme.text = onAir?.title.orEmpty()
        holder.programme.visibility =
            if (holder.programme.text.isNullOrEmpty()) View.GONE else View.VISIBLE
        if (channel.logo != null) holder.logo.load(channel.logo) else holder.logo.setImageDrawable(null)

        holder.itemView.setOnFocusChangeListener { _, hasFocus -> if (hasFocus) onFocus(position) }
        holder.itemView.setOnClickListener { onPick(position) }
    }

    override fun getItemCount() = items.size
}

/** Schedule of the focused channel. */
private class ProgrammeAdapter : RecyclerView.Adapter<ProgrammeAdapter.Holder>() {

    private var items: List<Programme> = emptyList()
    private val clock = SimpleDateFormat("HH:mm", Locale("pt", "BR"))

    fun submit(list: List<Programme>) {
        items = list
        notifyDataSetChanged()
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val time: TextView = view.findViewById(R.id.time)
        val poster: ImageView = view.findViewById(R.id.poster)
        val title: TextView = view.findViewById(R.id.programmeTitle)
        val detail: TextView = view.findViewById(R.id.programmeDetail)
        val onAir: TextView = view.findViewById(R.id.onAir)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_programme, parent, false))

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val programme = items[position]
        holder.time.text = clock.format(Date(programme.start))
        holder.title.text = programme.title
        holder.detail.text = programme.shortDetail.orEmpty()
        holder.detail.visibility =
            if (holder.detail.text.isNullOrEmpty()) View.GONE else View.VISIBLE
        holder.onAir.visibility =
            if (programme.isOnAir(System.currentTimeMillis())) View.VISIBLE else View.GONE

        if (programme.poster != null) {
            holder.poster.visibility = View.VISIBLE
            holder.poster.load(programme.poster)
        } else {
            holder.poster.visibility = View.GONE
        }
    }

    override fun getItemCount() = items.size
}
