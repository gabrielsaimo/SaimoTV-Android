package br.com.saimo.tv

import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi

data class Source(
    val url: String,
    val referer: String? = null,
    val userAgent: String? = null,
    val keyId: String? = null,
    val key: String? = null,
    val quality: String? = null,
    /// O que diferencia a fonte das outras do canal ("Temporada 3"), quando o
    /// catálogo sabe; senão o app mostra o número dela.
    val nome: String? = null,
) {
    val isDash: Boolean get() = url.contains(".mpd", ignoreCase = true)
    val isHls: Boolean get() =
        url.contains(".m3u8", ignoreCase = true) ||
            url.substringBefore('?').endsWith(".txt", ignoreCase = true) ||
            url.contains("dadinhodetapioca.fun", ignoreCase = true)
}

data class Channel(
    val name: String,
    val logo: String? = null,
    val sources: List<Source>,
    val categoria: String? = null,
)

/**
 * A lista de reserva, que vem dentro do APK: é com ela que o app abre na
 * primeira vez, antes de baixar a publicada, e é ela que fica se a rede não
 * vier nunca.
 *
 * Até a 2.1.3 ela era código Kotlin gerado — 470 canais escritos como
 * chamadas de construtor, uns 0,6 MB de dex e uma classe gigante carregada
 * na abertura. Agora é o próprio catalogo.txt (e o restritos.txt), guardado
 * como recurso em `src/main/resources/reserva/` e lido pelo mesmo parser da
 * lista baixada, só quando alguém pede. Para atualizar: scripts/gen_catalog.py.
 */
@OptIn(UnstableApi::class)
private fun reserva(nome: String): List<Channel> =
    runCatching {
        Channel::class.java.classLoader!!.getResourceAsStream("reserva/$nome")!!
            .bufferedReader().use { Remote.lerCampos(it.readText()) }
    }.getOrDefault(emptyList())

val CATALOG: List<Channel> by lazy { reserva("catalogo.txt") }

val RESTRICTED: List<Channel> by lazy {
    reserva("restritos.txt").map { if (it.categoria == null) it.copy(categoria = "Adulto") else it }
}
