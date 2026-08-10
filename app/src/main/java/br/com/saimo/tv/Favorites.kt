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
            .edit().putStringSet(KEY, cache).apply()
    }

    /** Favourites first, then the catalog order. */
    fun sort(channels: List<Channel>): List<Channel> =
        channels.sortedWith(compareByDescending<Channel> { contains(it.name) }
            .thenBy { channels.indexOf(it) })
}
