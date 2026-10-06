package br.com.saimo.tv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import coil3.load
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Request
import org.json.JSONArray

class EventosActivity : TelaComMenu() {
    override val aba = Aba.EVENTOS
    private lateinit var lista: RecyclerView
    private lateinit var carregando: View
    private lateinit var avisoErro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)

        lista = findViewById(R.id.eventosLista)
        carregando = findViewById(R.id.eventosCarregando)
        avisoErro = findViewById(R.id.eventosAvisoErro)

        lista.layoutManager = GridLayoutManager(this, 3)
        
        carregarEventos()
    }

    private fun carregarEventos() {
        carregando.visibility = View.VISIBLE
        avisoErro.visibility = View.GONE
        
        lifecycleScope.launch(Dispatchers.IO) {
            val pedido = Request.Builder().url("https://embedtv.cc/api/events").build()
            try {
                Playback.client.newCall(pedido).execute().use { r ->
                    val corpo = r.body?.string()
                    if (!r.isSuccessful || corpo == null) throw Exception("Erro de rede")
                    val json = JSONArray(corpo)
                    val eventos = mutableListOf<Evento>()
                    for (i in 0 until json.length()) {
                        val obj = json.getJSONObject(i)
                        val league = obj.getJSONObject("league")
                        val teams = obj.getJSONObject("teams")
                        val home = teams.getJSONObject("home")
                        val away = teams.getJSONObject("away")
                        val players = obj.optJSONArray("players")
                        val playerUrl = if (players != null && players.length() > 0) players.getString(0) else ""
                        
                        eventos.add(Evento(
                            titulo = obj.getString("title"),
                            ligaNome = league.getString("name"),
                            ligaLogo = league.optString("image"),
                            timeCasaNome = home.getString("name"),
                            timeCasaLogo = home.optString("image"),
                            timeForaNome = away.getString("name"),
                            timeForaLogo = away.optString("image"),
                            inicio = obj.getString("time_start"),
                            fim = obj.getString("time_end"),
                            playerUrl = playerUrl
                        ))
                    }
                    withContext(Dispatchers.Main) {
                        carregando.visibility = View.GONE
                        lista.adapter = EventosAdapter(eventos)
                        focarQuandoPronto { lista.getChildAt(0) }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    carregando.visibility = View.GONE
                    avisoErro.visibility = View.VISIBLE
                    avisoErro.text = "Erro ao carregar eventos. Tente novamente mais tarde."
                }
            }
        }
    }

    private inner class EventosAdapter(val itens: List<Evento>) : RecyclerView.Adapter<EventoViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_evento, parent, false)
            view.isFocusable = true
            view.isFocusableInTouchMode = true
            view.setBackgroundResource(R.drawable.row_focus)
            return EventoViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
            val ev = itens[position]
            holder.liga.text = ev.ligaNome
            holder.titulo.text = ev.titulo
            holder.timeCasa.text = ev.timeCasaNome
            holder.timeFora.text = ev.timeForaNome
            
            if (ev.ligaLogo.isNotEmpty()) holder.imgLiga.load(ev.ligaLogo)
            if (ev.timeCasaLogo.isNotEmpty()) holder.imgTimeCasa.load(ev.timeCasaLogo)
            if (ev.timeForaLogo.isNotEmpty()) holder.imgTimeFora.load(ev.timeForaLogo)
            
            holder.itemView.setOnClickListener {
                if (ev.playerUrl.isNotEmpty()) {
                    val slug = ev.playerUrl.substringAfterLast("/").lowercase()
                    val slugLimpo = slug.replace("-", "").replace(" ", "")
                    val channel = CATALOG.firstOrNull { it.name.lowercase().replace(" ", "") == slugLimpo }
                        ?: CATALOG.firstOrNull { it.name.lowercase().contains(slugLimpo) }
                    
                    if (channel != null) {
                        val i = Intent(this@EventosActivity, MainActivity::class.java)
                        i.putExtra(MainActivity.EXTRA_CANAL, channel.name)
                        startActivity(i)
                    } else {
                        Toast.makeText(this@EventosActivity, "Canal do evento não encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        }
        
        override fun getItemCount() = itens.size
    }

    private class EventoViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val liga: TextView = v.findViewById(R.id.eventoLiga)
        val titulo: TextView = v.findViewById(R.id.eventoTitulo)
        val timeCasa: TextView = v.findViewById(R.id.eventoTimeCasa)
        val timeFora: TextView = v.findViewById(R.id.eventoTimeFora)
        val imgLiga: ImageView = v.findViewById(R.id.imgLiga)
        val imgTimeCasa: ImageView = v.findViewById(R.id.imgTimeCasa)
        val imgTimeFora: ImageView = v.findViewById(R.id.imgTimeFora)
    }

    data class Evento(
        val titulo: String,
        val ligaNome: String,
        val ligaLogo: String,
        val timeCasaNome: String,
        val timeCasaLogo: String,
        val timeForaNome: String,
        val timeForaLogo: String,
        val inicio: String,
        val fim: String,
        val playerUrl: String
    )
}
