package br.com.saimo.tv

import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi

/**
 * Mapa que guarda só os [maximo] usados mais recentemente.
 *
 * As fichas, perfis, temporadas, legendas e marcas de abertura eram guardados
 * em mapas sem fim: navegar uma hora pelo acervo deixava o app com o dobro da
 * memória de quando abriu, e num TV Box de 1 GB é essa sobra que faz o
 * sistema matar o app. O que sai daqui volta da rede ou do disco na próxima
 * vez que for pedido.
 */
fun <K, V> recentes(maximo: Int): MutableMap<K, V> =
    object : LinkedHashMap<K, V>(16, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean = size > maximo
    }

/**
 * O que dá para soltar quando o app sai da tela ou o sistema pede memória.
 * Tudo aqui volta sozinho — do disco ou da rede — quando alguém precisar.
 */
object Memoria {
    @OptIn(UnstableApi::class)
    fun liberar() {
        Vod.liberar()
        Detalhes.liberar()
        Legendas.liberar()
        Pulos.liberar()
        Capas.liberar()
    }
}
