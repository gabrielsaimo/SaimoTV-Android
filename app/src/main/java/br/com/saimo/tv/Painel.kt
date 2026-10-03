package br.com.saimo.tv

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Painel de opções que entra pela direita, no lugar dos diálogos de celular.
 *
 * O `AlertDialog` do Android é desenhado para o dedo a trinta centímetros:
 * letra pequena, lista apertada, rádio minúsculo. Do sofá não se lê. Aqui a
 * linha focada fica branca e grande, o vídeo continua à vista do lado, e
 * VOLTAR fecha — o mesmo gesto de todo o resto do app.
 */
object Painel {

    data class Item(
        val texto: String,
        val detalhe: String? = null,
        val marcado: Boolean = false,
        /// Ícone Material antes do texto; 0 é sem ícone.
        val icone: Int = 0,
        /// Falso mantém o painel aberto depois da escolha (ex.: alternar algo).
        val fecha: Boolean = true,
        val acao: () -> Unit,
    )

    fun mostrar(
        activity: Activity,
        titulo: String,
        itens: List<Item>,
        subtitulo: String? = null,
        aoFechar: (() -> Unit)? = null,
    ): Dialog? {
        if (activity.isFinishing || activity.isDestroyed || itens.isEmpty()) return null
        val dialogo = Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar)
        val vista = LayoutInflater.from(activity).inflate(R.layout.painel_lateral, null)
        vista.findViewById<TextView>(R.id.painelTitulo).text = titulo
        vista.findViewById<TextView>(R.id.painelSubtitulo).apply {
            text = subtitulo.orEmpty()
            visibility = if (subtitulo.isNullOrBlank()) View.GONE else View.VISIBLE
        }
        if (Vr.ativo) vista.findViewById<TextView>(R.id.painelDica)?.setText(R.string.vr_painel_dica)
        val lista = vista.findViewById<RecyclerView>(R.id.painelLista)
        lista.layoutManager = LinearLayoutManager(activity)
        // Nos óculos não há BACK no controle: tocar fora fecha, e há um Fechar.
        val itens = if (Vr.ativo) itens + Item(activity.getString(R.string.vr_fechar), icone = R.drawable.ic_close) {} else itens
        val adaptador = Adaptador(itens) { item ->
            if (item.fecha) dialogo.dismiss()
            item.acao()
        }
        lista.adapter = adaptador
        dialogo.setContentView(vista)
        dialogo.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setGravity(Gravity.END)
            addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            setDimAmount(0.35f)
            setWindowAnimations(android.R.style.Animation_Translucent)
        }
        if (Vr.ativo) dialogo.setCanceledOnTouchOutside(true)
        dialogo.setOnDismissListener { aoFechar?.invoke() }
        dialogo.show()
        // O foco começa no que já está marcado: abrir a lista de fontes com o
        // canal no ar e ter de procurar qual é a atual seria um passo a mais.
        val inicio = itens.indexOfFirst { it.marcado }.coerceAtLeast(0)
        lista.scrollToPosition(inicio)
        lista.post {
            lista.findViewHolderForAdapterPosition(inicio)?.itemView?.requestFocus()
                ?: lista.getChildAt(0)?.requestFocus()
        }
        return dialogo
    }

    private class Adaptador(
        private val itens: List<Item>,
        private val aoEscolher: (Item) -> Unit,
    ) : RecyclerView.Adapter<Adaptador.Holder>() {

        class Holder(view: View) : RecyclerView.ViewHolder(view) {
            val texto: TextView = view.findViewById(R.id.itemTexto)
            val detalhe: TextView = view.findViewById(R.id.itemDetalhe)
            val marca: TextView = view.findViewById(R.id.itemMarca)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_painel, parent, false))

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val item = itens[position]
            holder.texto.text = item.texto
            Icones.inicio(holder.texto, item.icone)
            holder.detalhe.text = item.detalhe.orEmpty()
            holder.detalhe.visibility = if (item.detalhe.isNullOrBlank()) View.GONE else View.VISIBLE
            holder.marca.visibility = if (item.marcado) View.VISIBLE else View.GONE
            holder.itemView.setOnClickListener { aoEscolher(item) }
        }

        override fun getItemCount() = itens.size
    }
}
