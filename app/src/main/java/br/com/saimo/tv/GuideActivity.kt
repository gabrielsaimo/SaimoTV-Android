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
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil3.dispose
import coil3.load
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
@androidx.media3.common.util.UnstableApi
class GuideActivity : TelaComMenu() {

    override val aba = Aba.AO_VIVO


    private lateinit var channelList: RecyclerView
    private lateinit var programmeList: RecyclerView
    private lateinit var info: TextView
    private lateinit var clockLabel: TextView
    private lateinit var ficha: View
    private lateinit var fichaCapa: ImageView
    private lateinit var fichaCanal: TextView
    private lateinit var fichaTitulo: TextView
    private lateinit var fichaHorario: TextView
    private lateinit var fichaProgresso: ProgressBar
    private lateinit var fichaDetalhe: TextView
    private lateinit var fichaSinopse: TextView
    private lateinit var fichaElenco: TextView
    private lateinit var fichaAssistir: TextView
    private lateinit var fichaFechar: TextView

    private val handler = Handler(Looper.getMainLooper())
    private val clock = SimpleDateFormat("HH:mm", Locale("pt", "BR"))

    private var channels: List<Channel> = emptyList()
    private val programmes = ProgrammeAdapter()
    private var focused = 0
    private var shown = -1

    /// A grade do canal focado entra um instante depois do foco, não no meio
    /// dele. O foco muda por dentro da passada de layout de uma RecyclerView
    /// (a da grade perde a linha focada e o sistema devolve o foco à coluna de
    /// canais), e trocar o adapter ali é IllegalStateException — era o que
    /// fechava o app ao correr o direcional pela programação. De quebra,
    /// segurar a seta por 300 canais deixa de remontar a grade 300 vezes.
    private val mostrarFocado = Runnable { show(focused) }

    /// O relógio e a linha "no ar" precisam acompanhar a passagem do tempo; sem
    /// isto a grade mostra o programa que já acabou enquanto ela fica aberta.
    private val tick = object : Runnable {
        override fun run() {
            clockLabel.text = clock.format(Date())
            // O guia pode continuar chegando com a tela aberta; se a grade do
            // canal focado mudou de tamanho, é dado novo e vale remontar.
            val schedule = channels.getOrNull(focused)?.let { Epg.schedule(it.name) }
            if (schedule != null && schedule.size != shown) show(focused)
            else if (Epg.tick() && !programmeList.isComputingLayout) programmes.refresh()
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

        // A mesma lista e a mesma numeração do Ao vivo: sem os canais que o
        // painel desligou, e com o número fixo de cada canal. Antes a grade
        // numerava pela posição e o UFC Fight Pass era 328 num e 340 no outro.
        channels = FontesDesativadas.peneirarCanais(Categorias.ordenar(Unlock.channels()))
        val numeros = Categorias.ordenarFixo(channels).mapIndexed { i, c -> c.name to i + 1 }.toMap()
        val startAt = intent.getStringExtra(EXTRA_CHANNEL)
            ?.let { name -> channels.indexOfFirst { it.name == name } }
            ?.takeIf { it >= 0 } ?: 0

        val adapter = ChannelStripAdapter(
            channels,
            numeros,
            onFocus = {
                focused = it
                handler.removeCallbacks(mostrarFocado)
                handler.postDelayed(mostrarFocado, 120)
            },
            onPick = {
                // Nos óculos não há foco que ande pela lista: o primeiro toque
                // num canal mostra a programação dele (o que o foco faz no TV
                // Box), e o segundo toque sintoniza.
                if (Vr.ativo && it != focused) {
                    focused = it
                    handler.removeCallbacks(mostrarFocado)
                    handler.post(mostrarFocado)
                    android.widget.Toast.makeText(this, R.string.vr_ver_programacao, android.widget.Toast.LENGTH_SHORT).show()
                } else tune(it)
            })
        // OK sobre um programa abre a ficha dele. Trocar de canal por engano
        // faz perder o lugar na grade, e quem parou num horário quer saber o
        // que é aquilo — sintonizar continua a um botão de distância.
        programmes.onPick = { programa -> abrirFicha(programa) }

        ficha = findViewById(R.id.guideFicha)
        fichaCapa = findViewById(R.id.fichaCapa)
        fichaCanal = findViewById(R.id.fichaCanal)
        fichaTitulo = findViewById(R.id.fichaTitulo)
        fichaHorario = findViewById(R.id.fichaHorario)
        fichaProgresso = findViewById(R.id.fichaProgresso)
        fichaDetalhe = findViewById(R.id.fichaDetalhe)
        fichaSinopse = findViewById(R.id.fichaSinopse)
        fichaElenco = findViewById(R.id.fichaElenco)
        fichaAssistir = findViewById(R.id.fichaAssistir)
        fichaFechar = findViewById(R.id.fichaFechar)
        fichaAssistir.setOnClickListener { tune(focused) }
        fichaFechar.setOnClickListener { fecharFicha() }

        channelList.layoutManager = LinearLayoutManager(this)
        channelList.adapter = adapter
        channelList.setHasFixedSize(true)
        programmeList.layoutManager = LinearLayoutManager(this)
        programmeList.adapter = programmes
        programmeList.setHasFixedSize(true)
        // Atualizar o "no ar" a cada 20 s não deve piscar as linhas.
        for (lista in listOf(channelList, programmeList)) {
            (lista.itemAnimator as? androidx.recyclerview.widget.SimpleItemAnimator)?.supportsChangeAnimations = false
        }

        info.text = getString(R.string.guide_info, Epg.channelsWithGuide, Remote.channels.size)
        clockLabel.text = clock.format(Date())
        show(startAt)

        // Opening centred on what is being watched: with dozens of rows, landing
        // at the top means hunting for your own channel every time.
        focused = startAt
        channelList.post { focusRow(startAt) }
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
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK &&
            ficha.visibility == View.VISIBLE) {
            fecharFicha()
            return true
        }
        if (currentFocus == null) {
            focusRow(focused)
            return true
        }
        // Canal sem guia: à direita não há grade para onde ir. Sem isto a
        // busca de foco do sistema achava o menu do topo e o foco saía da
        // lista; antes, pior, entrava numa grade vazia e ficava preso.
        if (keyCode == android.view.KeyEvent.KEYCODE_DPAD_RIGHT && channelList.hasFocus() && shown == 0) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * A ficha do programa escolhido.
     *
     * O guia traz o que traz: fora do que está no ar, a maioria dos programas
     * não tem sinopse em fonte nenhuma. Então a ficha mostra o que existe —
     * canal, título, horário, duração, categoria, temporada e episódio — em vez
     * de fingir um campo vazio.
     */
    private fun abrirFicha(programa: Programme) {
        val canal = channels.getOrNull(focused)
        fichaCanal.text = canal?.name.orEmpty()
        fichaTitulo.text = programa.title
        val minutos = ((programa.stop - programa.start) / 60000).toInt()
        fichaHorario.text = getString(R.string.ficha_horario,
            clock.format(Date(programa.start)), clock.format(Date(programa.stop)), minutos)

        val agora = System.currentTimeMillis()
        val noAr = programa.isOnAir(agora)
        fichaProgresso.visibility = if (noAr) View.VISIBLE else View.GONE
        if (noAr) fichaProgresso.progress = (programa.progress(agora) * 1000).toInt()

        fichaDetalhe.text = listOfNotNull(
            programa.shortDetail,
            if (noAr) getString(R.string.ficha_agora, ((programa.stop - agora) / 60000).toInt())
            else null).joinToString(" · ")
        fichaDetalhe.visibility =
            if (fichaDetalhe.text.isNullOrBlank()) View.GONE else View.VISIBLE

        fichaSinopse.text = programa.description.orEmpty()
        fichaSinopse.visibility =
            if (programa.description.isNullOrBlank()) View.GONE else View.VISIBLE
        fichaElenco.text = if (programa.cast.isEmpty()) ""
                           else getString(R.string.ficha_elenco, programa.cast.joinToString(", "))
        fichaElenco.visibility = if (programa.cast.isEmpty()) View.GONE else View.VISIBLE

        if (programa.poster != null) {
            fichaCapa.visibility = View.VISIBLE
            fichaCapa.load(programa.poster)
        } else {
            fichaCapa.visibility = View.GONE
        }

        ficha.visibility = View.VISIBLE
        // Programa que ainda vai começar: "me avise". Na hora, um painel
        // aparece por cima do que estiver na tela, e um OK leva ao canal.
        val lembrar = findViewById<TextView>(R.id.fichaLembrar)
        if (!noAr && programa.start > agora && canal != null) {
            val lembrete = Lembretes.Lembrete(canal.name, programa.title, programa.start)
            fun pintar() {
                val marcado = Lembretes.tem(canal.name, programa.start)
                lembrar.text = getString(if (marcado) R.string.lembrete_marcado else R.string.lembrete_marcar)
                Icones.inicio(lembrar, if (marcado) R.drawable.ic_bell_off else R.drawable.ic_bell)
            }
            pintar()
            lembrar.visibility = View.VISIBLE
            lembrar.setOnClickListener { Lembretes.alternar(lembrete); pintar(); programmes.refresh() }
        } else {
            lembrar.visibility = View.GONE
        }
        fichaAssistir.post { fichaAssistir.requestFocus() }
    }

    private fun fecharFicha() {
        ficha.visibility = View.GONE
        programmeList.post { programmeList.requestFocus() }
    }

    private fun show(index: Int) {
        // Rede de segurança para qualquer outro caminho que chegue aqui dentro
        // de um layout: tenta de novo logo depois, fora dele.
        if (programmeList.isComputingLayout) {
            programmeList.post { show(index) }
            return
        }
        val channel = channels.getOrNull(index) ?: return
        val schedule = Epg.schedule(channel.name)
        shown = schedule.size
        programmes.submit(schedule)
        // Grade vazia não recebe foco: a seta para a direita levava o foco a
        // uma lista sem nada, de onde nem cima nem baixo saíam.
        programmeList.isFocusable = schedule.isNotEmpty()
        programmeList.descendantFocusability =
            if (schedule.isEmpty()) ViewGroup.FOCUS_BLOCK_DESCENDANTS else ViewGroup.FOCUS_AFTER_DESCENDANTS
        // Abre no que está no ar, não às seis da manhã de ontem.
        val now = System.currentTimeMillis()
        val onAir = schedule.indexOfFirst { it.isOnAir(now) }.takeIf { it >= 0 } ?: 0
        programmeList.post {
            (programmeList.layoutManager as LinearLayoutManager)
                .scrollToPositionWithOffset(onAir, 0)
        }
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(tick)
    }

    override fun onStart() {
        super.onStart()
        handler.removeCallbacks(tick)
        handler.postDelayed(tick, 20_000)
        // Com o app em segundo plano o guia pode ter saído da memória: volta do
        // disco, e a grade e a coluna de canais se refazem.
        if (Epg.channelsWithGuide == 0) {
            lifecycleScope.launch(semDerrubar) {
                if (Epg.carregarCache(this@GuideActivity)) {
                    (channelList.adapter as? ChannelStripAdapter)?.refresh()
                    show(focused)
                }
            }
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

/// Marca de atualização parcial da coluna de canais: só o programa no ar mudou.
private val SO_O_PROGRAMA = Any()

/** Channel column of the guide. */
private class ChannelStripAdapter(
    private val items: List<Channel>,
    private val numeros: Map<String, Int>,
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
        val secao: TextView = view.findViewById(R.id.secao)
        var loaded: String? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_channel, parent, false))

    /** Só o programa no ar de cada linha, quando o guia chega de volta. */
    fun refresh() = notifyItemRangeChanged(0, items.size, SO_O_PROGRAMA)

    override fun onBindViewHolder(holder: Holder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty() && payloads.all { it === SO_O_PROGRAMA }) mostrarNoAr(holder, items[position])
        else onBindViewHolder(holder, position)
    }

    private fun mostrarNoAr(holder: Holder, channel: Channel) {
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
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val channel = items[position]
        holder.number.text = numeros[channel.name]?.toString().orEmpty()
        holder.name.text = channel.name
        holder.star.visibility =
            if (Favorites.contains(channel.name)) View.VISIBLE else View.GONE
        val rotulo = Categorias.rotulo(items, position)
        holder.secao.text = rotulo.orEmpty()
        holder.secao.visibility = if (rotulo == null) View.GONE else View.VISIBLE

        mostrarNoAr(holder, channel)

        if (holder.loaded != channel.logo) {
            holder.loaded = channel.logo
            if (channel.logo != null) holder.logo.load(channel.logo)
            else { holder.logo.dispose(); holder.logo.setImageDrawable(null) }
        }

        // A posição do momento do foco, não a do bind: a linha pode ter sido
        // reaproveitada desde então.
        holder.itemView.setOnFocusChangeListener { _, hasFocus ->
            val atual = holder.bindingAdapterPosition
            if (hasFocus && atual != RecyclerView.NO_POSITION) onFocus(atual)
        }
        holder.itemView.setOnClickListener {
            val atual = holder.bindingAdapterPosition
            if (atual != RecyclerView.NO_POSITION) onPick(atual)
        }
    }

    override fun getItemCount() = items.size
}

/** Schedule of the focused channel. */
private class ProgrammeAdapter : RecyclerView.Adapter<ProgrammeAdapter.Holder>() {

    private var items: List<Programme> = emptyList()
    private val clock = SimpleDateFormat("HH:mm", Locale("pt", "BR"))
    var onPick: ((Programme) -> Unit)? = null

    @android.annotation.SuppressLint("NotifyDataSetChanged")
    fun submit(list: List<Programme>) {
        items = list
        notifyDataSetChanged()
    }

    /// A cada 20 s só o "no ar" e a barra mudam: sem recarregar pôster nem texto.
    fun refresh() = notifyItemRangeChanged(0, items.size)

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
            else { holder.poster.dispose(); holder.poster.setImageDrawable(null) }
        }
        holder.poster.visibility = if (programme.poster != null) View.VISIBLE else View.GONE
        holder.itemView.setOnClickListener { onPick?.invoke(programme) }
    }

    override fun getItemCount() = items.size
}
