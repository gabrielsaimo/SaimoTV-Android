package br.com.saimo.tv

/** Decodificador puro: metadados do catálogo nunca são fontes de vídeo. */
internal object VodFormato {
    fun montar(valor: String, bases: List<String>): String {
        val item = valor.trim()
        if (item.startsWith("https://") || item.startsWith("http://")) return item
        val corte = item.indexOf(':')
        if (corte <= 0 || corte == item.lastIndex) return ""
        val indice = item.take(corte).toIntOrNull() ?: return ""
        val base = bases.getOrNull(indice) ?: return ""
        val resto = item.substring(corte + 1)
        return if (resto.contains('.')) base + resto else "$base$resto.mp4"
    }

    fun fontes(campos: List<String>, bases: List<String>): Map<String, List<String>> {
        val out = linkedMapOf<String, List<String>>()
        for (campo in campos) {
            val marca = campo.indexOf('=')
            if (marca <= 0) continue
            val versao = campo.take(marca).trim()
            if (versao != "dub" && versao != "leg") continue
            val urls = campo.substring(marca + 1).split(',')
                .map { montar(it, bases) }.filter { it.isNotEmpty() }
            if (urls.isNotEmpty()) out[versao] = (out[versao].orEmpty() + urls).distinct()
        }
        return out
    }
}
