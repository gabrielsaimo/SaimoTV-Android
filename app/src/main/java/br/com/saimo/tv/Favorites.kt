package br.com.saimo.tv

import android.content.Context

/** Favourites, kept in preferences and floated to the top of the list. */
object Favorites {

    private const val FILE = "saimo"
    private const val KEY = "favorites"

    private var cache: MutableSet<String> = mutableSetOf()

    fun load(context: Context) {
        cache = context.getSharedPreferences(FILE, Context.MODE_PRIVATE)
            .getStringSet(KEY, emptySet())!!.toMutableSet()
    }

    fun contains(name: String) = name in cache

    fun toggle(context: Context, name: String) {
        if (!cache.remove(name)) cache.add(name)
        context.getSharedPreferences(FILE, Context.MODE_PRIVATE)
            // Uma cópia, sempre: entregar o mesmo conjunto que as preferências
            // já guardam faz o Android achar que nada mudou e não gravar em
            // disco — o favorito valia até o app fechar e sumia depois.
            .edit().putStringSet(KEY, HashSet(cache)).apply()
    }

    /** Favourites first, then alphabetical. */
    fun sort(channels: List<Channel>): List<Channel> =
        channels.sortedWith(compareByDescending<Channel> { contains(it.name) }
            .thenBy { it.name.lowercase() })
}
