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
    private val PADRAO = Regex(
        "^(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):\\d{2}[^\"]*\"" +
            "[\\s\\S]*?<a[^>]*href=\"[^\"]*programa/[^\"]+\"[^>]*>[\\s\\S]*?" +
            "([A-Za-zÀ-ÿ0-9][^<]{2,150})")
    private val ESPACOS = Regex("\\s+")

    /// Em aparelho de 1 GB, duas: cada página vira String e lista inteiras.
    private val gate by lazy { Semaphore(if (Aparelho.poucaMemoria) 2 else 6) }

    suspend fun fetch(names: List<String>, from: Long, to: Long): Map<String, List<Programme>> =
        coroutineScope {
            names.mapNotNull { name -> CODES[name]?.let { name to it } }
                .map { (name, slug) ->
                    async(Dispatchers.IO) {
                        // O parse dentro da vez: fora dela, as páginas baixadas
                        // esperavam juntas na memória.
                        val lista = gate.withPermit {
                            runCatching {
                                parse(Epg.download("https://www.guiadetv.com/canal/$slug"))
                                    .filter { it.stop > from && it.start < to }
                            }.getOrNull()
                        }
                        name to (lista ?: emptyList())
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
        val vistos = LinkedHashMap<Long, String>()
        // Um pedaço por horário, em vez de um padrão sobre a página inteira: o
        // regex do Android (ICU) copia toda a entrada para memória nativa, que
        // só volta no próximo GC — com várias páginas, eram 80 MB fora do heap,
        // e o sistema matava o app com o canal no ar.
        for (pedaco in html.split("data-dt=\"").drop(1)) {
            val m = PADRAO.find(pedaco) ?: continue
            val (ano, mes, dia, hora, minuto) = m.destructured
            val calendario = Calendar.getInstance(zone)
            calendario.clear()
            calendario.set(ano.toInt(), mes.toInt() - 1, dia.toInt(), hora.toInt(), minuto.toInt(), 0)
            val titulo = Epg.decodeEntities(m.groupValues[6]).trim().replace(ESPACOS, " ")
            if (titulo.length < 2) continue
            // O mesmo instante pode repetir na página — o link do programa
            // carrega metadados extras que também casam com o padrão.
            // Nada de putIfAbsent: ele só existe do Android 7 em diante, e num
            // TV Box com 5 ou 6 a chamada derrubava o app ao carregar o guia.
            if (!vistos.containsKey(calendario.timeInMillis)) vistos[calendario.timeInMillis] = titulo
        }

        val ordenados = vistos.entries.sortedBy { it.key }
        return ordenados.mapIndexed { posicao, (inicio, titulo) ->
            val fim = if (posicao + 1 < ordenados.size) ordenados[posicao + 1].key
                      else inicio + 3_600_000
            Programme(titulo, "", inicio, fim)
        }
    }
}
