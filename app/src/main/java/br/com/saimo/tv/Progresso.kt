package br.com.saimo.tv

import android.content.Context

/**
 * Onde cada filme e episódio parou, o que já foi visto e em que episódio cada
 * série está.
 *
 * A chave é o título e, na série, a temporada e o episódio — não a URL: o mesmo
 * episódio vem de fontes diferentes e, se a primeira falhar, a segunda tem de
 * retomar no mesmo ponto.
 *
 * Junto do ponto fica o endereço do título no acervo (letra, ano, coleção):
 * é o que deixa o "Continue assistindo" abrir o vídeo direto, em vez de passar
 * de novo por busca, temporada e episódio.
 */
object Progresso {

    private const val ARQUIVO = "progresso"
    /// Menos de um minuto não é "onde parou", é ter aberto e desistido.
    private const val MINIMO_MS = 60_000L
    /// A dois minutos do fim o título está visto: retomar ali só irrita.
    private const val SOBRA_MS = 120_000L

    fun chaveFilme(titulo: String) = "f|$titulo"

    fun chaveEpisodio(serie: String, temporada: Int, numero: Int) =
        "s|$serie|$temporada|$numero"

    private fun prefs(context: Context) = context.getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE)

    /**
     * Guarda onde parou.
     *
     * Duração desconhecida (0 ou C.TIME_UNSET) é "a fonte não diz quanto
     * dura"; guarda-se o ponto do mesmo jeito, só não dá para desenhar a barra.
     * Chegando perto do fim, o título vira "visto" em vez de sumir sem rastro.
     */
    fun salvar(context: Context, chave: String, posicao: Long, duracao: Long) {
        if (chave.isBlank() || posicao <= 0) return
        val temDuracao = duracao > 0 && duracao != Long.MIN_VALUE
        val guardada = if (temDuracao) duracao else 0L
        val editor = prefs(context).edit()
        if (temDuracao && posicao > duracao - SOBRA_MS) {
            editor.remove(chave).remove("$chave|d").remove("$chave|t")
                .putBoolean("v|$chave", true)
            tocarSerie(editor, chave)
            editor.apply()
            return
        }
        if (posicao < MINIMO_MS) {
            editor.remove(chave).remove("$chave|d").remove("$chave|t").apply()
            return
        }
        editor.putLong(chave, posicao)
            .putLong("$chave|d", guardada)
            .putLong("$chave|t", System.currentTimeMillis())
        tocarSerie(editor, chave)
        editor.apply()
    }

    /** O instante do episódio vale também para a série, que é quem aparece no "continuar". */
    private fun tocarSerie(editor: android.content.SharedPreferences.Editor, chave: String) {
        val campos = chave.split("|")
        if (campos.firstOrNull() != "s" || campos.size < 4) return
        editor.putLong("u|${campos[1]}|t", System.currentTimeMillis())
    }

    /** Onde o título mora no acervo, para abrir direto de uma lista local. */
    data class Endereco(
        val titulo: String,
        val serie: Boolean,
        val letra: String,
        val ano: String = "",
        val colecao: String = "",
        val tmdbId: Int = 0,
    ) {
        val alvo: Alvo get() = Alvo(titulo, serie, letra, ano, colecao, tmdbId)
    }

    /**
     * Anota que um título começou a tocar.
     *
     * Na série, anota também o episódio: é ele que o cartão da série no
     * "continuar" oferece, e o seguinte quando este acabar.
     */
    fun comecou(context: Context, chave: String, endereco: Endereco, temporada: Int = 0, episodio: Int = 0) {
        val editor = prefs(context).edit()
        val meta = listOf(endereco.titulo, if (endereco.serie) "s" else "f", endereco.letra,
            endereco.ano, endereco.colecao, endereco.tmdbId.toString()).joinToString("\t")
        editor.putString("m|$chave", meta)
        if (endereco.serie) {
            val nome = chave.split("|").getOrNull(1).orEmpty()
            editor.putString("u|$nome", "$meta\t$temporada\t$episodio")
                .putLong("u|$nome|t", System.currentTimeMillis())
        }
        editor.apply()
    }

    /** Tira o título do "continuar": é o "remover da fileira" do controle. */
    fun esquecer(context: Context, andamento: Andamento) {
        val editor = prefs(context).edit()
        if (andamento.serie) editor.remove("u|${andamento.nome}").remove("u|${andamento.nome}|t")
        editor.remove(andamento.chave).remove("${andamento.chave}|d").remove("${andamento.chave}|t")
        editor.apply()
    }

    fun visto(context: Context, chave: String): Boolean = prefs(context).getBoolean("v|$chave", false)

    /**
     * Um cartão do "Continue assistindo".
     *
     * O filme pela metade, ou a série — uma por série, no último episódio que
     * tocou. Se esse episódio já acabou, o cartão é do seguinte ([terminado]).
     */
    data class Andamento(
        val chave: String,
        /// O nome que vai na chave: o título (com ano, na série).
        val nome: String,
        val rotulo: String,
        val serie: Boolean,
        val fracao: Float,
        val quando: Long,
        val endereco: Endereco?,
        val temporada: Int = 0,
        val episodio: Int = 0,
        val terminado: Boolean = false,
    ) {
        /// Nome do título, que é por onde se acha ele no acervo.
        val titulo: String get() = endereco?.titulo ?: nome
    }

    private fun endereco(meta: String?): Endereco? {
        val c = meta?.split("\t") ?: return null
        if (c.size < 3 || c[0].isBlank()) return null
        return Endereco(c[0], c[1] == "s", c[2], c.getOrElse(3) { "" }, c.getOrElse(4) { "" },
            c.getOrNull(5)?.toIntOrNull() ?: 0)
    }

    fun emAndamento(context: Context): List<Andamento> {
        val tudo = prefs(context).all
        val out = mutableListOf<Andamento>()
        val seriesVistas = HashSet<String>()

        // Séries: uma por série, a partir da marca do último episódio.
        for ((chave, valor) in tudo) {
            if (!chave.startsWith("u|") || chave.endsWith("|t")) continue
            val nome = chave.removePrefix("u|")
            val campos = (valor as? String)?.split("\t") ?: continue
            val temporada = campos.getOrNull(6)?.toIntOrNull() ?: continue
            val episodio = campos.getOrNull(7)?.toIntOrNull() ?: continue
            val epChave = chaveEpisodio(nome, temporada, episodio)
            val posicao = tudo[epChave] as? Long ?: 0L
            val duracao = tudo["$epChave|d"] as? Long ?: 0L
            // Episódio que mal começou (menos de um minuto) continua valendo:
            // a série está em andamento nele, só não há barra para mostrar.
            val terminado = posicao <= 0L && tudo["v|$epChave"] == true
            seriesVistas += nome
            out += Andamento(
                chave = epChave, nome = nome,
                rotulo = if (terminado) "${Generos.semAno(nome)} · T$temporada E${episodio + 1}"
                         else "${Generos.semAno(nome)} · T$temporada E$episodio",
                serie = true,
                fracao = if (terminado || duracao <= 0) 0f else (posicao.toFloat() / duracao).coerceIn(0f, 1f),
                quando = tudo["u|$nome|t"] as? Long ?: 0L,
                endereco = endereco(campos.take(6).joinToString("\t")),
                temporada = temporada, episodio = episodio, terminado = terminado,
            )
        }

        for ((chave, valor) in tudo) {
            if (chave.contains("|d") || chave.endsWith("|t") || chave.startsWith("m|") ||
                chave.startsWith("v|") || chave.startsWith("u|")) continue
            val posicao = valor as? Long ?: continue
            val duracao = tudo["$chave|d"] as? Long ?: continue
            if (posicao <= 0 || duracao < 0) continue
            val campos = chave.split("|")
            val serie = campos.firstOrNull() == "s"
            val nome = campos.getOrNull(1) ?: continue
            // Episódio de série já aparece pelo cartão da série.
            if (serie && nome in seriesVistas) continue
            val temporada = campos.getOrNull(2)?.toIntOrNull() ?: 0
            val episodio = campos.getOrNull(3)?.toIntOrNull() ?: 0
            if (serie) seriesVistas += nome
            out += Andamento(
                chave = chave, nome = nome,
                rotulo = if (serie) "${Generos.semAno(nome)} · T$temporada E$episodio" else nome,
                serie = serie,
                fracao = if (duracao > 0) (posicao.toFloat() / duracao).coerceIn(0f, 1f) else 0f,
                quando = tudo["$chave|t"] as? Long ?: 0L,
                endereco = endereco(tudo["m|$chave"] as? String),
                temporada = temporada, episodio = episodio,
            )
        }
        return out.sortedByDescending { it.quando }
    }

    /** Posição guardada, ou zero. */
    fun posicao(context: Context, chave: String): Long = prefs(context).getLong(chave, 0L)

    /** Quanto do título já foi visto, de 0 a 1, ou nulo se nunca foi aberto. */
    fun fracao(context: Context, chave: String): Float? {
        val prefs = prefs(context)
        if (prefs.getBoolean("v|$chave", false) && !prefs.contains(chave)) return 1f
        val posicao = prefs.getLong(chave, 0L)
        val duracao = prefs.getLong("$chave|d", 0L)
        if (posicao <= 0 || duracao <= 0) return null
        return (posicao.toFloat() / duracao).coerceIn(0f, 1f)
    }

    /** O último episódio tocado de uma série, se houver: (temporada, episódio). */
    fun ultimoEpisodio(context: Context, nome: String): Pair<Int, Int>? {
        val campos = prefs(context).getString("u|$nome", null)?.split("\t") ?: return null
        val t = campos.getOrNull(6)?.toIntOrNull() ?: return null
        val e = campos.getOrNull(7)?.toIntOrNull() ?: return null
        return t to e
    }
}
