package br.com.saimo.tv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@UnstableApi
class RadiosActivity : TelaComMenu() {
    override val aba = Aba.RADIOS
    private lateinit var lista: RecyclerView
    private lateinit var carregando: View
    private lateinit var vazio: TextView

    data class Radio(val nome: String, val url: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radios)
        
        lista = findViewById(R.id.radiosLista)
        carregando = findViewById(R.id.radiosCarregando)
        vazio = findViewById(R.id.radiosVazio)
        
        lista.layoutManager = GridLayoutManager(this, 5)
        
        carregar()
    }

    private fun carregar() {
        lifecycleScope.launch {
            carregando.visibility = View.VISIBLE
            val radios = withContext(Dispatchers.IO) {
                val arquivo1 = File(filesDir, "vod/radios.txt")
                val arquivo2 = File(filesDir, "radios.txt")
                val alvo = if (arquivo1.exists()) arquivo1 else arquivo2
                if (!alvo.exists()) return@withContext emptyList<Radio>()
                
                alvo.readLines().mapNotNull { linha ->
                    val partes = linha.split("|")
                    if (partes.size >= 2) Radio(partes[0].trim(), partes[1].trim()) else null
                }
            }
            carregando.visibility = View.GONE
            if (radios.isEmpty()) {
                vazio.visibility = View.VISIBLE
            } else {
                lista.adapter = Adaptador(radios)
            }
        }
    }

    private inner class Adaptador(val itens: List<Radio>) : RecyclerView.Adapter<Holder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_radio, parent, false)
            v.isFocusable = true
            v.isFocusableInTouchMode = true
            v.setBackgroundResource(R.drawable.row_focus)
            return Holder(v)
        }
        
        override fun onBindViewHolder(holder: Holder, position: Int) {
            val r = itens[position]
            holder.nome.text = r.nome
            holder.itemView.setOnClickListener {
                val i = Intent(this@RadiosActivity, MainActivity::class.java)
                i.putExtra(MainActivity.EXTRA_RADIO_NOME, r.nome)
                i.putExtra(MainActivity.EXTRA_RADIO_URL, r.url)
                startActivity(i)
            }
        }
        
        override fun getItemCount() = itens.size
    }

    private class Holder(v: View) : RecyclerView.ViewHolder(v) {
        val nome: TextView = v.findViewById(R.id.radioNome)
        val icone: ImageView = v.findViewById(R.id.radioIcone)
    }
}
