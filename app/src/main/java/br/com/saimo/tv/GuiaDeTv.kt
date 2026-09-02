package br.com.saimo.tv

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import java.util.Calendar
import java.util.TimeZone

/**
 * Reserva do guia para o que o meuguia.tv não lista — Sony Movies é o caso
 * que motivou, e a mesma varredura das sete categorias do guiadetv.com achou
 * mais cinco. Só entra para quem [MeuGuia] já não trouxe nada; canais fora
 * deste mapa nunca chegam a bater aqui.
 */
object GuiaDeTv {

    /** Catalog name -> slug do guiadetv.com. */
    private val CODES = mapOf(
        "SONY Movies" to "sony-movies",
        "SBT News" to "sbt-news",
        "Terra Viva" to "terra-viva",
        "Box Kids TV" to "box-kids",
        "X Sports" to "xsports",
        "N SPORTS" to "nsports",
    )

    /// Mesmo limite do MeuGuia: rolar a lista depressa não pode virar seis
    /// downloads de uma vez roubando banda do canal que acabou de abrir.
    private val gate = Semaphore(6)

    suspend fun fetch(names: List<String>, from: Long, to: Long): Map<String, List<Programme>> =
        coroutineScope {
            names.mapNotNull { name -> CODES[name]?.let { name to it } }
                .map { (name, slug) ->
                    async(Dispatchers.IO) {
                        val html = gate.withPermit {
                            runCatching {
                                Epg.download("https://www.guiadetv.com/canal/$slug")
                            }.getOrNull()
                        }
                        name to (html?.let { parse(it) }?.filter { it.stop > from && it.start < to }
                            ?: emptyList())
                    }
                }
                .awaitAll()
                .filter { it.second.isNotEmpty() }
                .toMap()
        }

    /**
     * `data-dt="AAAA-MM-DD HH:MM:SS-03:00"` seguido, adiante, de um link
     * `/programa/...` cujo texto é o título. O fim também não é publicado;
     * mesma regra do meuguia — vai até o próximo começar.
     */
    fun parse(html: String): List<Programme> {
        val zone = TimeZone.getTimeZone("America/Sao_Paulo")
        val padrao = Regex(
            "data-dt=\"(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):\\d{2}[^\"]*\"" +
                "[\\s\\S]*?<a[^>]*href=\"[^\"]*programa/[^\"]+\"[^>]*>[\\s\\S]*?" +
                "([A-Za-zÀ-ÿ0-9][^<]{2,150})")

        val vistos = LinkedHashMap<Long, String>()
        for (m in padrao.findAll(html)) {
            val (ano, mes, dia, hora, minuto) = m.destructured
            val calendario = Calendar.getInstance(zone)
            calendario.clear()
            calendario.set(ano.toInt(), mes.toInt() - 1, dia.toInt(), hora.toInt(), minuto.toInt(), 0)
            val titulo = Epg.decodeEntities(m.groupValues[6]).trim().replace(Regex("\\s+"), " ")
            if (titulo.length < 2) continue
            // O mesmo instante pode repetir na página — o link do programa
            // carrega metadados extras que também casam com o padrão.
            vistos.putIfAbsent(calendario.timeInMillis, titulo)
        }

        val ordenados = vistos.entries.sortedBy { it.key }
        return ordenados.mapIndexed { posicao, (inicio, titulo) ->
            val fim = if (posicao + 1 < ordenados.size) ordenados[posicao + 1].key
                      else inicio + 3_600_000
            Programme(titulo, "", inicio, fim)
        }
    }
}
