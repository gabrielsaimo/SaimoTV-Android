package br.com.saimo.tv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Request
import org.json.JSONArray
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import java.text.Normalizer
import org.json.JSONObject


@UnstableApi
class EventosActivity : TelaComMenu() {
    override val aba = Aba.EVENTOS
    private lateinit var lista: RecyclerView
    private lateinit var carregando: ProgressBar
    private lateinit var avisoErro: TextView
    private lateinit var dImgCasa: ImageView
    private lateinit var dImgFora: ImageView
    private lateinit var dImgLiga: ImageView
    private lateinit var dTimeCasa: TextView
    private lateinit var dTimeFora: TextView
    private lateinit var dLiga: TextView
    private lateinit var dHorario: TextView
    private lateinit var dTitulo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)
        
        lista = findViewById(R.id.eventosLista)
        carregando = findViewById(R.id.eventosCarregando)
        avisoErro = findViewById(R.id.eventosAvisoErro)
        dImgCasa = findViewById(R.id.eventoDestaqueImgCasa)
        dImgFora = findViewById(R.id.eventoDestaqueImgFora)
        dImgLiga = findViewById(R.id.eventoDestaqueLigaImg)
        dTimeCasa = findViewById(R.id.eventoDestaqueTimeCasa)
        dTimeFora = findViewById(R.id.eventoDestaqueTimeFora)
        dLiga = findViewById(R.id.eventoDestaqueLiga)
        dHorario = findViewById(R.id.eventoDestaqueHorario)
        dTitulo = findViewById(R.id.eventoDestaqueTitulo)
        
        lista.layoutManager = LinearLayoutManager(this)
        
        carregarEventos()
    }

    fun atualizarDestaque(ev: Evento) {
        if (ev.timeCasaLogo.isNotEmpty()) {
            dImgCasa.load(ev.timeCasaLogo)
        } else {
            dImgCasa.setImageDrawable(null)
        }
        
        if (ev.timeForaLogo.isNotEmpty()) {
            dImgFora.load(ev.timeForaLogo)
        } else {
            dImgFora.setImageDrawable(null)
        }
        
        if (ev.ligaLogo.isNotEmpty()) dImgLiga.load(ev.ligaLogo) else dImgLiga.setImageDrawable(null)
        
        dTimeCasa.text = ev.timeCasaNome
        dTimeFora.text = ev.timeForaNome
        dLiga.text = ev.ligaNome
        dHorario.text = ev.horarioFormatado
        dTitulo.text = ev.titulo
    }

    
    private fun carregarEventos() {
        carregando.visibility = View.VISIBLE
        avisoErro.visibility = View.GONE
        
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val embedDeferred = async { 
                    val pedido = Request.Builder().url("https://embedtv.cc/api/events").build()
                    Playback.client.newCall(pedido).execute().use { r ->
                        if (r.isSuccessful) r.body?.string() else null
                    }
                }
                
                val espnUrls = listOf(
                    "https://site.api.espn.com/apis/site/v2/sports/soccer/bra.1/scoreboard",
                    "https://site.api.espn.com/apis/site/v2/sports/soccer/bra.2/scoreboard",
                    "https://site.api.espn.com/apis/site/v2/sports/basketball/nba/scoreboard",
                    "https://site.api.espn.com/apis/site/v2/sports/football/nfl/scoreboard",
                    "https://site.api.espn.com/apis/site/v2/sports/baseball/mlb/scoreboard",
                    "https://site.api.espn.com/apis/site/v2/sports/hockey/nhl/scoreboard",
                    "https://site.api.espn.com/apis/site/v2/sports/soccer/eng.1/scoreboard",
                    "https://site.api.espn.com/apis/site/v2/sports/soccer/esp.1/scoreboard",
                    "https://site.api.espn.com/apis/site/v2/sports/soccer/ita.1/scoreboard",
                    "https://site.api.espn.com/apis/site/v2/sports/soccer/ger.1/scoreboard",
                    "https://site.api.espn.com/apis/site/v2/sports/soccer/uefa.champions/scoreboard",
                    "https://site.api.espn.com/apis/site/v2/sports/soccer/conmebol.libertadores/scoreboard",
                    "https://site.api.espn.com/apis/site/v2/sports/soccer/conmebol.sudamericana/scoreboard",
                    "https://site.api.espn.com/apis/site/v2/sports/volleyball/mens-college-volleyball/scoreboard",
                    "https://site.api.espn.com/apis/site/v2/sports/volleyball/womens-college-volleyball/scoreboard"
                )
                
                val espnDeferreds = espnUrls.map { url ->
                    async {
                        val pedido = Request.Builder().url(url).build()
                        Playback.client.newCall(pedido).execute().use { r ->
                            if (r.isSuccessful) r.body?.string() else null
                        }
                    }
                }
                
                val embedBody = embedDeferred.await()
                val espnBodies = espnDeferreds.awaitAll()
                
                if (embedBody == null) throw Exception("Erro de rede")
                val jsonEmbed = JSONArray(embedBody)
                val eventosDict = mutableMapOf<String, Evento>()
                
                fun norm(t: String): String {
                    val limpo = Normalizer.normalize(t, Normalizer.Form.NFD)
                        .replace("[^\\\\p{ASCII}]".toRegex(), "")
                        .lowercase()
                        .replace("clube", "")
                        .replace("esporte", "")
                        .replace("fc", "")
                        .replace("ec", "")
                        .replace("sp", "")
                        .replace("rj", "")
                        .replace("mg", "")
                        .replace("rs", "")
                        .replace("pr", "")
                        .replace("ba", "")
                        .replace("pe", "")
                        .replace("sc", "")
                        .replace("ce", "")
                        .replace("go", "")
                        .replace("mt", "")
                        .replace("ms", "")
                        .replace("pa", "")
                        .replace("rn", "")
                        .replace("pb", "")
                        .replace("al", "")
                        .replace("se", "")
                        .replace("pi", "")
                        .replace("ma", "")
                        .replace("am", "")
                        .replace("ro", "")
                        .replace("rr", "")
                        .replace("ap", "")
                        .replace("to", "")
                        .replace("df", "")
                        .replace("ac", "")
                        .replace(" ", "")
                    return limpo
                }

                // Carrega ESPN primeiro para base de dados riquíssima
                for (espnBody in espnBodies) {
                    if (espnBody == null) continue
                    try {
                        val espnJson = JSONObject(espnBody)
                        val leagues = espnJson.optJSONArray("leagues") ?: continue
                        val events = espnJson.optJSONArray("events") ?: continue
                        
                        val leagueName = leagues.optJSONObject(0)?.optString("name") ?: ""
                        val leagueLogo = leagues.optJSONObject(0)?.optJSONArray("logos")?.optJSONObject(0)?.optString("href") ?: ""
                        
                        for (i in 0 until events.length()) {
                            val ev = events.getJSONObject(i)
                            val title = ev.optString("name")
                            val dateStr = ev.optString("date")
                            
                            val comps = ev.optJSONArray("competitions")?.optJSONObject(0)?.optJSONArray("competitors") ?: continue
                            var homeName = ""
                            var homeLogo = ""
                            var awayName = ""
                            var awayLogo = ""
                            
                            for (c in 0 until comps.length()) {
                                val comp = comps.getJSONObject(c)
                                val team = comp.getJSONObject("team")
                                val isHome = comp.optString("homeAway") == "home"
                                if (isHome) {
                                    homeName = team.optString("name")
                                    homeLogo = team.optString("logo")
                                } else {
                                    awayName = team.optString("name")
                                    awayLogo = team.optString("logo")
                                }
                            }
                            
                            var inicioMs = 0L
                            var horarioFormatado = ""
                            try {
                                val formatoData = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'", java.util.Locale.US)
                                formatoData.timeZone = java.util.TimeZone.getTimeZone("UTC")
                                val dateObj = formatoData.parse(dateStr)
                                if (dateObj != null) {
                                    inicioMs = dateObj.time
                                    val sdfHorario = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
                                    horarioFormatado = sdfHorario.format(dateObj)
                                }
                            } catch (e: Exception) { }
                            
                            val key = norm(homeName) + "|" + norm(awayName)
                            eventosDict[key] = Evento(
                                titulo = title,
                                ligaNome = leagueName,
                                ligaLogo = leagueLogo,
                                timeCasaNome = homeName,
                                timeCasaLogo = homeLogo,
                                timeForaNome = awayName,
                                timeForaLogo = awayLogo,
                                inicio = dateStr,
                                fim = "",
                                playerUrl = "", // sem stream nativo ESPN
                                horarioFormatado = horarioFormatado,
                                canalNome = "",
                                canalLogo = ""
                            )
                        }
                    } catch (e: Exception) { e.printStackTrace() }
                }

                // Agora processa EmbedTV, enriquecendo ou adicionando
                for (i in 0 until jsonEmbed.length()) {
                    val obj = jsonEmbed.getJSONObject(i)
                    
                    val league = obj.getJSONObject("league")
                    val teams = obj.getJSONObject("teams")
                    val home = teams.getJSONObject("home")
                    val away = teams.getJSONObject("away")
                    val players = obj.optJSONArray("players")
                    val playerUrl = if (players != null && players.length() > 0) players.getString(0) else ""
                    
                    val homeName = home.getString("name")
                    val awayName = away.getString("name")
                    
                    val inicioStr = obj.getString("time_start")
                    var inicioMs = 0L
                    var horarioFormatado = ""
                    try {
                        val formatoData = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", java.util.Locale.US)
                        val dateObj = formatoData.parse(inicioStr)
                        if (dateObj != null) {
                            inicioMs = dateObj.time
                            val sdfHorario = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
                            horarioFormatado = sdfHorario.format(dateObj)
                        }
                    } catch (e: Exception) {}

                    val key = norm(homeName) + "|" + norm(awayName)
                    val espnMatch = eventosDict[key]
                    
                    // ACHAR O CANAL NA LISTA
                    var canalNome = ""
                    var canalLogo = ""
                    var temCanal = false
                    
                    if (playerUrl.isNotEmpty()) {
                        val slug = playerUrl.substringAfterLast("/").lowercase()
                        val slugLimpo = slug.replace("-", "").replace(" ", "")
                        val channel = CATALOG.firstOrNull { it.name.lowercase().replace(" ", "") == slugLimpo }
                            ?: CATALOG.firstOrNull { it.name.lowercase().contains(slugLimpo) }
                        
                        if (channel != null) {
                            canalNome = channel.name
                            canalLogo = channel.logo ?: ""
                            temCanal = true
                        }
                    }
                    
                    if (temCanal) {
                        if (espnMatch != null) {
                            eventosDict[key] = espnMatch.copy(playerUrl = playerUrl, canalNome = canalNome, canalLogo = canalLogo)
                        } else {
                            eventosDict[key] = Evento(
                                titulo = obj.getString("title"),
                                ligaNome = league.getString("name"),
                                ligaLogo = league.optString("image"),
                                timeCasaNome = homeName,
                                timeCasaLogo = home.optString("image"),
                                timeForaNome = awayName,
                                timeForaLogo = away.optString("image"),
                                inicio = inicioStr,
                                fim = obj.getString("time_end"),
                                playerUrl = playerUrl,
                                horarioFormatado = horarioFormatado,
                                canalNome = canalNome,
                                canalLogo = canalLogo
                            )
                        }
                    }
                }
                
                val eventosFinais = eventosDict.values.filter { it.canalNome.isNotEmpty() }.sortedBy { it.titulo }
                
                withContext(Dispatchers.Main) {
                    carregando.visibility = View.GONE
                    lista.adapter = EventosAdapter(eventosFinais)
                    focarQuandoPronto { lista.getChildAt(0) }
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
            holder.timeCasa.text = ev.timeCasaNome
            holder.timeFora.text = ev.timeForaNome
            holder.horario.text = ev.horarioFormatado
            
            if (ev.ligaLogo.isNotEmpty()) holder.imgLiga.load(ev.ligaLogo) else holder.imgLiga.setImageDrawable(null)
            if (ev.timeCasaLogo.isNotEmpty()) holder.imgCasaItem.load(ev.timeCasaLogo) else holder.imgCasaItem.setImageDrawable(null)
            if (ev.timeForaLogo.isNotEmpty()) holder.imgForaItem.load(ev.timeForaLogo) else holder.imgForaItem.setImageDrawable(null)
            
            holder.textoCanalItem.text = ev.canalNome
            if (ev.canalLogo.isNotEmpty()) holder.imgCanalItem.load(ev.canalLogo) else holder.imgCanalItem.setImageDrawable(null)
            
            holder.itemView.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    (holder.itemView.context as? EventosActivity)?.atualizarDestaque(ev)
                }
            }
            
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
        val timeCasa: TextView = v.findViewById(R.id.eventoTimeCasa)
        val timeFora: TextView = v.findViewById(R.id.eventoTimeFora)
        val horario: TextView = v.findViewById(R.id.eventoHorario)
        val imgLiga: ImageView = v.findViewById(R.id.imgLiga)
        val imgCasaItem: ImageView = v.findViewById(R.id.imgTimeCasaItem)
        val imgForaItem: ImageView = v.findViewById(R.id.imgTimeForaItem)
        val imgCanalItem: ImageView = v.findViewById(R.id.imgCanalItem)
        val textoCanalItem: TextView = v.findViewById(R.id.textoCanalItem)
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
        val horarioFormatado: String,
        val canalNome: String,
        val canalLogo: String
    )
}
