package br.com.saimo.tv

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.text.Normalizer
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.abs

/**
 * Os jogos de hoje e dos próximos dias que passam em algum canal da lista.
 *
 * Duas fontes: a agenda da embedtv diz em que canal cada jogo passa; os
 * placares da ESPN dizem se já começou, o placar e o tempo de jogo, e trazem
 * escudos melhores. Só entra jogo que tem canal — sem canal não há o que
 * assistir —, e jogo encerrado sai.
 *
 * As fileiras são por quando: ao vivo agora, começa em breve, hoje mais tarde,
 * amanhã e próximos dias. Com a tela aberta, o placar se atualiza sozinho.
 */
@UnstableApi
class EventosActivity : TelaComMenu() {
    override val aba = Aba.EVENTOS

    data class Jogo(
        val casa: String,
        val fora: String,
        val casaLogo: String,
        val foraLogo: String,
        val liga: String,
        val ligaLogo: String,
        val inicio: Long,
        val fim: Long,
        val canal: Channel,
        /// "pre", "in" ou "post", quando a ESPN conhece o jogo.
        val estado: String = "",
        val placar: String = "",
        val tempo: String = "",
    ) {
        val titulo get() = "$casa x $fora"
        fun aoVivo(agora: Long) = estado == "in" || (estado != "post" && agora in inicio..fim)
        fun acabou(agora: Long) = estado == "post" || agora > fim
    }

    private lateinit var filas: RecyclerView
    private lateinit var destaque: Destaque
    private val adaptador by lazy { Inicio.Adapter(escopo = lifecycleScope, aoFocar = { destaque.mostrar(it) }) }
    private var montou = false
    private var atualizando: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagina)
        filas = findViewById(R.id.inicioFilas)
        filas.layoutManager = Inicio.Filas(this)
        filas.adapter = adaptador
        destaque = Destaque(this, lifecycleScope, findViewById<ImageView>(R.id.inicioFundo),
            findViewById(R.id.inicioTitulo), findViewById(R.id.inicioMeta), findViewById(R.id.inicioSinopse))
        findViewById<TextView>(R.id.paginaNome).visibility = View.GONE
        destaque.padrao(getString(R.string.menu_eventos), getString(R.string.vod_carregando))
    }

    override fun onStart() {
        super.onStart()
        // O placar anda: enquanto a tela está aberta, busca de novo a cada minuto.
        atualizando = lifecycleScope.launch(semDerrubar) {
            while (isActive) {
                val jogos = withContext(Dispatchers.IO) { runCatching { buscar() }.getOrNull() }
                if (jogos == null && !montou) {
                    destaque.padrao(getString(R.string.menu_eventos), getString(R.string.eventos_erro))
                } else if (jogos != null) mostrar(jogos)
                delay(60_000)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        atualizando?.cancel()
    }

    // MARK: - Tela

    private fun mostrar(jogos: List<Jogo>) {
        if (isFinishing || isDestroyed) return
        val agora = System.currentTimeMillis()
        val hoje = diaDe(agora)
        val vivos = jogos.filter { !it.acabou(agora) }.sortedBy { it.inicio }
        val grupos = linkedMapOf<Int, MutableList<Jogo>>()
        for (j in vivos) {
            val grupo = when {
                j.aoVivo(agora) -> R.string.eventos_ao_vivo
                j.inicio - agora <= 2 * 3_600_000L -> R.string.eventos_em_breve
                diaDe(j.inicio) == hoje -> R.string.eventos_hoje
                diaDe(j.inicio) == hoje + 1 -> R.string.eventos_amanha
                else -> R.string.eventos_proximos
            }
            grupos.getOrPut(grupo) { mutableListOf() } += j
        }
        val ordem = listOf(R.string.eventos_ao_vivo, R.string.eventos_em_breve, R.string.eventos_hoje,
            R.string.eventos_amanha, R.string.eventos_proximos)
        val lista = ordem.mapNotNull { g ->
            grupos[g]?.let { Inicio.Fila(getString(g), it.map { j -> cartao(j, agora) }, Inicio.Tipo.EVENTO) }
        }
        if (lista.isEmpty()) {
            destaque.padrao(getString(R.string.menu_eventos), getString(R.string.eventos_nenhum))
            adaptador.trocar(emptyList())
            return
        }
        if (!montou) {
            val aoVivo = vivos.count { it.aoVivo(agora) }
            destaque.padrao(getString(R.string.menu_eventos),
                resources.getQuantityString(R.plurals.eventos_resumo, vivos.size, vivos.size, aoVivo))
        }
        adaptador.trocar(lista)
        if (!montou) {
            montou = true
            focarQuandoPronto {
                filas.findViewHolderForAdapterPosition(0)?.itemView
                    ?.findViewById<RecyclerView>(R.id.filaCapas)?.getChildAt(0)
            }
        }
    }

    private fun cartao(j: Jogo, agora: Long): Inicio.Cartao {
        val aoVivo = j.aoVivo(agora)
        val quando = quando(j.inicio, agora)
        val selo = if (aoVivo) listOf(getString(R.string.radio_ao_vivo), j.placar).filter { it.isNotBlank() }.joinToString("  ")
                   else quando
        val meta = listOf(j.liga,
            if (aoVivo) listOf(getString(R.string.radio_ao_vivo), j.placar, j.tempo).filter { it.isNotBlank() }.joinToString(" ")
            else quando,
        ).filter { it.isNotBlank() }.joinToString("  ·  ")
        return Inicio.Cartao(
            titulo = j.titulo, capa = "", inicial = j.casa.take(1),
            subtitulo = listOf(j.canal.name, j.liga).filter { it.isNotBlank() }.joinToString(" · "),
            colagem = listOf(j.casaLogo, j.foraLogo, j.ligaLogo),
            selo = selo, seloAoVivo = aoVivo,
            meta = meta,
            sinopse = getString(R.string.eventos_dica, j.canal.name),
        ) {
            startActivity(Intent(this, MainActivity::class.java).putExtra(MainActivity.EXTRA_CANAL, j.canal.name))
        }
    }

    private val hora = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val semana = SimpleDateFormat("EEE", Locale.forLanguageTag("pt-BR"))

    /** "21:30", "amanhã 16:00" ou "sáb 16:00". */
    private fun quando(inicio: Long, agora: Long): String {
        val d = diaDe(inicio) - diaDe(agora)
        val h = hora.format(Date(inicio))
        return when {
            d <= 0 -> h
            d == 1 -> getString(R.string.eventos_amanha_as, h)
            else -> "${semana.format(Date(inicio)).trimEnd('.')} $h"
        }
    }

    private fun diaDe(ms: Long): Int {
        val c = Calendar.getInstance().apply { timeInMillis = ms }
        return c.get(Calendar.YEAR) * 400 + c.get(Calendar.DAY_OF_YEAR)
    }

    // MARK: - Dados

    private fun texto(url: String): String? = runCatching {
        Playback.client.newCall(Request.Builder().url(url).header("User-Agent", Playback.DEFAULT_USER_AGENT).build())
            .execute().use { r -> if (r.isSuccessful) r.body.string() else null }
    }.getOrNull()

    private suspend fun buscar(): List<Jogo>? = kotlinx.coroutines.coroutineScope {
        val agenda = async { texto(EMBED) }
        val placares = ESPN.map { liga ->
            async { texto("https://site.api.espn.com/apis/site/v2/sports/$liga/scoreboard") }
        }
        val corpo = agenda.await() ?: return@coroutineScope null
        val espn = placares.awaitAll().filterNotNull().flatMap { lerEspn(it) }
        lerAgenda(corpo).map { jogo ->
            // O mesmo jogo na ESPN: mesmos times (em qualquer ordem) e começo
            // a menos de quatro horas um do outro.
            val par = espn.firstOrNull { e ->
                abs(e.inicio - jogo.inicio) < 4 * 3_600_000L &&
                    ((mesmoTime(e.casa, jogo.casa) && mesmoTime(e.fora, jogo.fora)) ||
                        (mesmoTime(e.casa, jogo.fora) && mesmoTime(e.fora, jogo.casa)))
            } ?: return@map jogo
            jogo.copy(
                casaLogo = par.casaLogo.ifEmpty { jogo.casaLogo },
                foraLogo = par.foraLogo.ifEmpty { jogo.foraLogo },
                ligaLogo = jogo.ligaLogo.ifEmpty { par.ligaLogo },
                estado = par.estado, placar = par.placar, tempo = par.tempo,
            )
        }
    }

    /** A agenda da embedtv: só jogos cujo canal existe na nossa lista. */
    private fun lerAgenda(corpo: String): List<Jogo> {
        val iso = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.US)
        val arr = runCatching { JSONArray(corpo) }.getOrNull() ?: return emptyList()
        return (0 until arr.length()).mapNotNull { i ->
            runCatching {
                val o = arr.getJSONObject(i)
                val players = o.optJSONArray("players")
                val url = if (players != null && players.length() > 0) players.getString(0) else return@runCatching null
                val canal = canalDo(url) ?: return@runCatching null
                val times = o.getJSONObject("teams")
                val casa = times.getJSONObject("home"); val fora = times.getJSONObject("away")
                val liga = o.optJSONObject("league")
                val inicio = iso.parse(o.getString("time_start"))?.time ?: return@runCatching null
                val fim = o.optString("time_end").takeIf { it.isNotBlank() }?.let { iso.parse(it)?.time }
                    ?: (inicio + 2 * 3_600_000L)
                Jogo(casa.getString("name"), fora.getString("name"), casa.optString("image"), fora.optString("image"),
                    liga?.optString("name").orEmpty(), liga?.optString("image").orEmpty(), inicio, fim, canal)
            }.getOrNull()
        }.distinctBy { "${it.titulo}|${it.inicio}" }
    }

    /** O canal pelo fim do endereço do player ("…/premiere-fc" → Premiere FC). */
    private fun canalDo(url: String): Channel? {
        val slug = url.substringAfterLast("/").lowercase(Locale.ROOT).replace("-", "").replace(" ", "")
        if (slug.isBlank()) return null
        return CATALOG.firstOrNull { it.name.lowercase(Locale.ROOT).replace(" ", "") == slug }
            ?: CATALOG.firstOrNull { it.name.lowercase(Locale.ROOT).replace(" ", "").contains(slug) }
    }

    private data class Placar(
        val casa: String, val fora: String, val casaLogo: String, val foraLogo: String,
        val ligaLogo: String, val inicio: Long, val estado: String, val placar: String, val tempo: String,
    )

    private fun lerEspn(corpo: String): List<Placar> = runCatching {
        val json = JSONObject(corpo)
        val ligaLogo = json.optJSONArray("leagues")?.optJSONObject(0)?.optJSONArray("logos")
            ?.optJSONObject(0)?.optString("href").orEmpty()
        val eventos = json.optJSONArray("events") ?: return@runCatching emptyList()
        val data = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'", Locale.US).apply { timeZone = TimeZone.getTimeZone("UTC") }
        (0 until eventos.length()).mapNotNull { i ->
            runCatching {
                val ev = eventos.getJSONObject(i)
                val comps = ev.optJSONArray("competitions")?.optJSONObject(0)?.optJSONArray("competitors")
                    ?: return@runCatching null
                var casa: JSONObject? = null; var fora: JSONObject? = null
                for (c in 0 until comps.length()) {
                    val comp = comps.getJSONObject(c)
                    if (comp.optString("homeAway") == "home") casa = comp else fora = comp
                }
                val ca = casa ?: return@runCatching null; val fo = fora ?: return@runCatching null
                val status = ev.optJSONObject("status")?.optJSONObject("type")
                val estado = status?.optString("state").orEmpty()
                Placar(
                    ca.getJSONObject("team").optString("name"), fo.getJSONObject("team").optString("name"),
                    ca.getJSONObject("team").optString("logo"), fo.getJSONObject("team").optString("logo"),
                    ligaLogo, data.parse(ev.optString("date"))?.time ?: 0L, estado,
                    if (estado == "pre") "" else "${ca.optString("score")} x ${fo.optString("score")}",
                    if (estado == "in") status?.optString("shortDetail").orEmpty() else "",
                )
            }.getOrNull()
        }
    }.getOrDefault(emptyList())

    /**
     * Mesmo time com nomes diferentes nas duas fontes ("Palmeiras" e "SE
     * Palmeiras", "Atlético-MG" e "Atletico Mineiro"). Só palavras inteiras
     * de sigla são tiradas: tirar "pa" ou "ma" de dentro do nome, como antes,
     * fazia "Palmeiras" virar "lmeiras" e casava times errados.
     */
    private fun mesmoTime(a: String, b: String): Boolean {
        val x = chaveTime(a); val y = chaveTime(b)
        if (x.isEmpty() || y.isEmpty()) return false
        return x == y || (x.length >= 4 && y.contains(x)) || (y.length >= 4 && x.contains(y))
    }

    private fun chaveTime(nome: String): String =
        Normalizer.normalize(nome, Normalizer.Form.NFD).replace(Regex("\\p{M}+"), "").lowercase(Locale.ROOT)
            .split(Regex("[^a-z0-9]+")).filter { it.isNotEmpty() && it !in SIGLAS }.joinToString("")

    private companion object {
        const val EMBED = "https://embedtv.cc/api/events"
        val SIGLAS = setOf("fc", "ec", "sc", "ac", "afc", "cf", "cd", "club", "clube", "esporte", "sport",
            "futebol", "de", "do", "da", "sp", "rj", "mg", "rs", "pr", "ba", "pe", "ce", "go", "sad")
        val ESPN = listOf(
            "soccer/bra.1", "soccer/bra.2", "soccer/bra.copa_do_brazil", "soccer/conmebol.libertadores",
            "soccer/conmebol.sudamericana", "soccer/uefa.champions", "soccer/uefa.europa", "soccer/eng.1",
            "soccer/esp.1", "soccer/ita.1", "soccer/ger.1", "soccer/fra.1", "soccer/por.1", "soccer/ned.1",
            "soccer/arg.1", "soccer/ksa.1", "soccer/fifa.world", "soccer/fifa.worldq.conmebol",
            "basketball/nba", "football/nfl", "baseball/mlb", "hockey/nhl", "mma/ufc",
        )
    }
}
