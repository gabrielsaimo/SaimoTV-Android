package br.com.saimo.tv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
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
                        
                        val inicioStr = obj.getString("time_start")
                        val fimStr = obj.getString("time_end")
                        
                        var fimMs = 0L
                        var inicioMs = 0L
                        try {
                            val formatoData = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", java.util.Locale.US)
                            val dataFim = formatoData.parse(fimStr)
                            val dataInicio = formatoData.parse(inicioStr)
                            if (dataFim != null) fimMs = dataFim.time
                            if (dataInicio != null) inicioMs = dataInicio.time
                        } catch (e: Exception) { }
                        
                        if (fimMs == 0L && inicioMs > 0L) {
                            fimMs = inicioMs + (2 * 60 * 60 * 1000)
                        }
                        
                        if (fimMs > 0 && System.currentTimeMillis() > fimMs) {
                            continue
                        }

                        val league = obj.getJSONObject("league")
                        val teams = obj.getJSONObject("teams")
                        val home = teams.getJSONObject("home")
                        val away = teams.getJSONObject("away")
                        val players = obj.optJSONArray("players")
                        val playerUrl = if (players != null && players.length() > 0) players.getString(0) else ""
                        
                        var horarioFormatado = ""
                        try {
                            val sdfHorario = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
                            if (inicioMs > 0L) horarioFormatado = sdfHorario.format(java.util.Date(inicioMs))
                        } catch (e: Exception) {}
                        
                        eventos.add(Evento(
                            titulo = obj.getString("title"),
                            ligaNome = league.getString("name"),
                            ligaLogo = league.optString("image"),
                            timeCasaNome = home.getString("name"),
                            timeCasaLogo = home.optString("image"),
                            timeForaNome = away.getString("name"),
                            timeForaLogo = away.optString("image"),
                            inicio = inicioStr,
                            fim = fimStr,
                            playerUrl = playerUrl,
                            horarioFormatado = horarioFormatado
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
            holder.horario.text = ev.horarioFormatado
            
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
        
        override fun getItemCount() = itens.size
    }

    private class EventoViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val liga: TextView = v.findViewById(R.id.eventoLiga)
        val titulo: TextView = v.findViewById(R.id.eventoTitulo)
        val timeCasa: TextView = v.findViewById(R.id.eventoTimeCasa)
        val timeFora: TextView = v.findViewById(R.id.eventoTimeFora)
        val horario: TextView = v.findViewById(R.id.eventoHorario)
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
        val playerUrl: String,
        val horarioFormatado: String
    )
}
