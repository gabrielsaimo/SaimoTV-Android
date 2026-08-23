package br.com.saimo.tv

import android.content.Context

/**
 * Filmes e séries marcados como favoritos.
 *
 * Guarda o tipo e a letra junto com o nome porque é assim que o acervo é
 * fatiado: com a letra na mão, abrir um favorito custa o mesmo pedaço de
 * catálogo que abrir o título pela navegação normal, sem varrer o acervo
 * inteiro atrás dele.
 *
 * A ordem importa — o que foi marcado por último aparece em cima —, e por isso
 * a lista é gravada como texto e não como conjunto: `StringSet` não guarda ordem.
 */
object VodFavoritos {

    private const val ARQUIVO = "vodfav"
    private const val CHAVE = "lista"

    data class Item(val titulo: String, val serie: Boolean, val letra: String)

    @Volatile
    private var cache: MutableList<Item>? = null

    private fun carregar(context: Context): MutableList<Item> {
        cache?.let { return it }
        val texto = context.getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE)
            .getString(CHAVE, "").orEmpty()
        val out = texto.lineSequence().mapNotNull { linha ->
            val campos = linha.split("\t")
            if (campos.size < 3 || campos[2].isBlank()) null
            else Item(campos[2], campos[0] == "s", campos[1])
        }.toMutableList()
        cache = out
        return out
    }

    fun lista(context: Context): List<Item> = carregar(context).toList()

    fun contem(context: Context, titulo: String, serie: Boolean): Boolean =
        carregar(context).any { it.titulo == titulo && it.serie == serie }

    /** Marca ou desmarca. Devolve o estado novo. */
    fun alternar(context: Context, item: Item): Boolean {
        val atual = carregar(context)
        val fora = atual.removeAll { it.titulo == item.titulo && it.serie == item.serie }
        if (!fora) atual.add(0, item)
        gravar(context, atual)
        return !fora
    }

    private fun gravar(context: Context, itens: List<Item>) {
        val texto = itens.joinToString("\n") {
            "${if (it.serie) "s" else "f"}\t${it.letra}\t${it.titulo}"
        }
        context.getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE)
            .edit().putString(CHAVE, texto).apply()
    }
}
