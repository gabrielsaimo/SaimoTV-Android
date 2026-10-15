package br.com.saimo.tv

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil3.dispose
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * Os episódios da série, de dentro do player.
 *
 * Antes era o painel lateral de texto: "Episódio 4 · Nome", sem imagem, e
 * trocar de temporada pedia três OKs — abrir "Temporada 1 ▸ trocar", escolher
 * na segunda lista, cair de volta na primeira. Aqui é uma faixa na parte de
 * baixo da tela, igual à da ficha: as temporadas em pílulas no alto e os
 * episódios em cartões com a imagem da cena, quanto já foi visto e um selo
 * no que está tocando.
 *
 * O caminho do controle:
 * - abre na temporada que está tocando, com o foco no episódio no ar;
 * - esquerda e direita andam pelos episódios; para cima vai à pílula da
 *   temporada mostrada;
 * - nas pílulas, esquerda e direita já trocam a temporada (sem OK), e para
 *   baixo volta aos episódios — no que está no ar, ou no primeiro ainda não
 *   terminado daquela temporada;
 * - OK num episódio troca para ele; no que está no ar só fecha. VOLTAR fecha.
 *
 * Num TV Box de 1 GB: a imagem é a w300 do TMDB, carregada pelo Coil do app
 * (mesmo cache em memória e em disco) só para os cartões que estão na tela, e
 * tudo é solto quando o painel fecha.
 */
@UnstableApi
object PainelEpisodios {

    @Volatile
    var dialogAtivo: Dialog? = null
        internal set

    fun mostrar(
        activity: AppCompatActivity,
        r: Titulos.Resolvido,
        noAr: Titulos.Ep,
        aoEscolher: (Titulos.Ep) -> Unit,
    ): Dialog? {
        if (activity.isFinishing || activity.isDestroyed || r.episodios.isEmpty()) return null
        val dialogo = Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar)
        dialogAtivo = dialogo
        val vista = LayoutInflater.from(activity).inflate(R.layout.painel_episodios, null)
        // Os pedidos ao TMDB vivem só enquanto o painel está aberto.
        val escopo = CoroutineScope(SupervisorJob() + Dispatchers.Main + semDerrubar)

        // Tela baixa (celular deitado, ~390 dp): sem o respiro de cima e sem a
        // dica, senão os cartões passam da borda e o nome do episódio some.
        val metricas = activity.resources.displayMetrics
        val telaBaixa = metricas.heightPixels / metricas.density < 480
        if (telaBaixa) {
            vista.setPadding(vista.paddingLeft, Vr.dp(activity, 12), vista.paddingRight, Vr.dp(activity, 8))
            vista.findViewById<View>(R.id.peDica).visibility = View.GONE
        }
        vista.findViewById<TextView>(R.id.peTitulo).text = Generos.semAno(r.alvo.titulo)
        vista.findViewById<TextView>(R.id.peSubtitulo).text =
            activity.getString(R.string.player_eps_no_ar, noAr.temporada, noAr.numero)
        if (Vr.ativo) {
            vista.findViewById<TextView>(R.id.peDica).setText(R.string.player_eps_dica_toque)
            // Nos óculos não há BACK no controle: tocar fora fecha, e há um Fechar.
            Vr.adicionar(vista.findViewById(R.id.peCabecalho),
                Vr.botao(activity, activity.getString(R.string.vr_fechar), R.drawable.ic_close) { dialogo.dismiss() })
        }

        val lista = vista.findViewById<RecyclerView>(R.id.peLista)
        val barra = vista.findViewById<LinearLayout>(R.id.peTemporadas)
        lista.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        // Sem animação de troca: o cartão é redesenhado no lugar quando os
        // nomes chegam, e o foco não pula para fora dele.
        lista.itemAnimator = null
        lista.setItemViewCacheSize(4)

        var mostrada = 0
        val adaptador = Adaptador(r, noAr,
            aoEscolher = { e ->
                dialogo.dismiss()
                if (e != noAr) aoEscolher(e)
            },
            aoSubir = { barra.findViewWithTag<View>(mostrada)?.requestFocus() })
        lista.adapter = adaptador

        // A imagem larga da série, para o episódio que o TMDB não tem cena.
        escopo.launch {
            val ficha = Detalhes.de(r.alvo.titulo, true, r.tmdbId.takeIf { it > 0 })
            adaptador.reserva = ficha?.fundo?.replace("/w780/", "/w300/")
                ?: ficha?.capa ?: Generos.capa(r.alvo.titulo, true)
            adaptador.notifyItemRangeChanged(0, adaptador.itemCount)
        }

        /** Põe o foco no cartão [indice], esperando a lista montá-lo se preciso. */
        fun focar(indice: Int, tentativas: Int = 4) {
            if (indice !in 0 until adaptador.itemCount) return
            val pronto = lista.findViewHolderForAdapterPosition(indice)?.itemView
            if (pronto != null) { pronto.requestFocus(); return }
            lista.scrollToPosition(indice)
            if (tentativas > 0) lista.post { focar(indice, tentativas - 1) }
        }

        /** Para onde descer das pílulas: o episódio no ar, ou o primeiro por terminar. */
        fun indiceDeEntrada(): Int {
            val eps = adaptador.itens
            eps.indexOf(noAr).takeIf { it >= 0 }?.let { return it }
            return eps.indexOfFirst { (Progresso.fracao(activity, r.chave(it)) ?: 0f) < 1f }.coerceAtLeast(0)
        }

        fun mostrarTemporada(temporada: Int) {
            if (temporada == mostrada) return
            mostrada = temporada
            for (i in 0 until barra.childCount) barra.getChildAt(i).isSelected = barra.getChildAt(i).tag == temporada
            adaptador.trocar(r.episodios.filter { it.temporada == temporada }, emptyMap())
            lista.scrollToPosition(indiceDeEntrada())
            escopo.launch {
                val info = Detalhes.temporada(r.tmdbId, temporada).associateBy { it.numero }
                if (mostrada == temporada) adaptador.completar(info)
            }
        }

        val temporadas = r.temporadas
        if (temporadas.size > 1) {
            val d = activity.resources.displayMetrics.density
            for (t in temporadas) {
                barra.addView(TextView(activity).apply {
                    id = View.generateViewId()
                    text = activity.getString(R.string.vod_temporada, t)
                    tag = t
                    textSize = 17f
                    typeface = android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL)
                    setTextColor(ContextCompat.getColorStateList(activity, R.color.texto_botao_ficha))
                    setBackgroundResource(R.drawable.botao_player)
                    isFocusable = true
                    isClickable = true
                    if (Vr.ativo) minHeight = Vr.dp(activity, 56)
                    gravity = Gravity.CENTER
                    setPadding((20 * d).toInt(), (8 * d).toInt(), (20 * d).toInt(), (8 * d).toInt())
                    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT).apply { marginEnd = (10 * d).toInt() }
                    // Focar a temporada já troca a lista: sem OK a mais. O foco
                    // fica na pílula, e descer leva aos episódios dela.
                    setOnFocusChangeListener { _, foco -> if (foco) mostrarTemporada(t) }
                    setOnClickListener { mostrarTemporada(t) }
                    setOnKeyListener { _, codigo, evento ->
                        if (evento.action != KeyEvent.ACTION_DOWN) return@setOnKeyListener false
                        when (codigo) {
                            KeyEvent.KEYCODE_DPAD_DOWN -> { focar(indiceDeEntrada()); true }
                            // Acima das pílulas não há nada: a seta não foge do painel.
                            KeyEvent.KEYCODE_DPAD_UP -> true
                            else -> false
                        }
                    }
                })
            }
            // Nas pontas da fila de temporadas a seta para ali, como na ficha.
            barra.getChildAt(0)?.let { it.nextFocusLeftId = it.id }
            barra.getChildAt(barra.childCount - 1)?.let { it.nextFocusRightId = it.id }
        } else {
            vista.findViewById<HorizontalScrollView>(R.id.peTemporadasRolagem).visibility = View.GONE
        }
        mostrarTemporada(noAr.temporada)

        dialogo.setContentView(vista)
        dialogo.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setGravity(Gravity.BOTTOM)
            // Sem escurecer a tela: o degradê da faixa já separa do vídeo.
            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            setWindowAnimations(android.R.style.Animation_Translucent)
        }
        if (Vr.ativo) dialogo.setCanceledOnTouchOutside(true)
        dialogo.setOnDismissListener {
            if (dialogAtivo == dialogo) dialogAtivo = null
            escopo.cancel()
            // Solta as imagens: o Coil guarda no cache dele o que valer a pena.
            for (i in 0 until lista.childCount) {
                lista.getChildAt(i).findViewById<ImageView>(R.id.epImagem)?.let { it.dispose(); it.setImageDrawable(null) }
            }
            lista.adapter = null
        }
        dialogo.show()
        Vr.telaCheia(dialogo.window)
        // O foco começa no episódio que está tocando.
        lista.post { focar(adaptador.itens.indexOf(noAr).coerceAtLeast(0)) }
        return dialogo
    }

    private class Adaptador(
        private val r: Titulos.Resolvido,
        private val noAr: Titulos.Ep,
        private val aoEscolher: (Titulos.Ep) -> Unit,
        private val aoSubir: () -> Unit,
    ) : RecyclerView.Adapter<Adaptador.Holder>() {

        var itens: List<Titulos.Ep> = emptyList()
            private set
        private var info: Map<Int, Detalhes.EpisodioTmdb> = emptyMap()
        /// Falso até o TMDB responder pela temporada: antes disso a imagem da
        /// série entraria em todos os cartões só para ser trocada em seguida.
        private var respondeu = false
        /// A imagem da série, para quando o episódio não tem a dele.
        var reserva: String? = null

        fun trocar(lista: List<Titulos.Ep>, dados: Map<Int, Detalhes.EpisodioTmdb>) {
            itens = lista; info = dados; respondeu = false
            notifyDataSetChanged()
        }

        /** Nomes e imagens chegaram: redesenha no lugar, sem tirar o foco. */
        fun completar(dados: Map<Int, Detalhes.EpisodioTmdb>) {
            info = dados; respondeu = true
            notifyItemRangeChanged(0, itens.size)
        }

        class Holder(view: View) : RecyclerView.ViewHolder(view) {
            val moldura: View = view.findViewById(R.id.epMoldura)
            val imagem: ImageView = view.findViewById(R.id.epImagem)
            val numero: TextView = view.findViewById(R.id.epNumeroGrande)
            val noAr: TextView = view.findViewById(R.id.epNoAr)
            val visto: TextView = view.findViewById(R.id.epVisto)
            val progresso: ProgressBar = view.findViewById(R.id.epProgresso)
            val titulo: TextView = view.findViewById(R.id.epTitulo)
            val detalhe: TextView = view.findViewById(R.id.epDetalhe)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_episodio_player, parent, false)
            val holder = Holder(view)
            Cartoes.arredondar(holder.moldura)
            view.setOnKeyListener { _, codigo, evento ->
                if (evento.action == KeyEvent.ACTION_DOWN) when (codigo) {
                    KeyEvent.KEYCODE_DPAD_UP -> { aoSubir(); return@setOnKeyListener true }
                    // Abaixo dos cartões não há nada: a seta não foge do painel.
                    KeyEvent.KEYCODE_DPAD_DOWN -> return@setOnKeyListener true
                }
                Cartoes.segurarNasPontas(holder, codigo, evento)
            }
            view.setOnClickListener {
                itens.getOrNull(holder.bindingAdapterPosition)?.let(aoEscolher)
            }
            return holder
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val ep = itens[position]
            val dados = info[ep.numero]
            val contexto = holder.itemView.context
            holder.numero.text = ep.numero.toString()
            holder.titulo.text = listOfNotNull("${ep.numero}.", dados?.nome?.takeIf { it.isNotBlank() }
                ?: contexto.getString(R.string.vod_episodio, ep.numero)).joinToString(" ")
            holder.detalhe.text = listOfNotNull(dados?.duracao?.let { "$it min" },
                ep.versoes.joinToString(" / ") { Titulos.rotulo(it) }.takeIf { it.isNotEmpty() })
                .joinToString(" · ")
            val tocando = ep == noAr
            holder.itemView.isSelected = tocando
            holder.noAr.visibility = if (tocando) View.VISIBLE else View.GONE
            val fracao = Progresso.fracao(contexto, r.chave(ep))
            holder.visto.visibility = if (!tocando && fracao != null && fracao >= 1f) View.VISIBLE else View.GONE
            holder.progresso.visibility = if (fracao != null && fracao < 1f) View.VISIBLE else View.GONE
            fracao?.let { holder.progresso.progress = (it * 1000).toInt() }
            val url = dados?.imagem ?: reserva.takeIf { respondeu }
            // Mesmo endereço já na tela (os nomes chegaram depois): não recarrega.
            if (holder.imagem.tag != url || holder.imagem.drawable == null) {
                holder.imagem.tag = url
                Cartoes.carregar(holder.imagem, holder.numero, url)
            }
        }

        override fun onViewRecycled(holder: Holder) {
            holder.imagem.dispose()
            holder.imagem.setImageDrawable(null)
            holder.imagem.tag = null
        }

        override fun getItemCount() = itens.size
    }
}
