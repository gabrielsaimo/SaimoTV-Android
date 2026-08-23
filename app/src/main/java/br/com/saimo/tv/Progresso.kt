package br.com.saimo.tv

import android.content.Context

/**
 * Onde cada filme e episódio parou.
 *
 * A chave é o título e, na série, a temporada e o episódio — não a URL: o mesmo
 * episódio vem de fontes diferentes e, se a primeira falhar, a segunda tem de
 * retomar no mesmo ponto.
 *
 * Isto fica gravado de propósito, ao contrário das capas: voltar ao ponto em que
 * se parou não é enfeite, é a diferença entre continuar um filme e recomeçá-lo.
 */
object Progresso {

    private const val ARQUIVO = "progresso"
    /// Menos de um minuto não é "onde parou", é ter aberto e desistido.
    private const val MINIMO_MS = 60_000L
    /// A dois minutos do fim o episódio está visto: retomar ali só irrita.
    private const val SOBRA_MS = 120_000L

    fun chaveFilme(titulo: String) = "f|$titulo"

    fun chaveEpisodio(serie: String, temporada: Int, numero: Int) =
        "s|$serie|$temporada|$numero"

    fun salvar(context: Context, chave: String, posicao: Long, duracao: Long) {
        if (chave.isBlank() || duracao <= 0) return
        val prefs = context.getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE)
        if (posicao < MINIMO_MS || posicao > duracao - SOBRA_MS) {
            // Acabou de começar ou já terminou: nada a retomar.
            prefs.edit().remove(chave).remove("$chave|d").apply()
            return
        }
        prefs.edit().putLong(chave, posicao).putLong("$chave|d", duracao).apply()
    }

    /** Posição guardada, ou zero. */
    fun posicao(context: Context, chave: String): Long =
        context.getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE).getLong(chave, 0L)

    /** Quanto do título já foi visto, de 0 a 1, ou nulo se nunca foi aberto. */
    fun fracao(context: Context, chave: String): Float? {
        val prefs = context.getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE)
        val posicao = prefs.getLong(chave, 0L)
        val duracao = prefs.getLong("$chave|d", 0L)
        if (posicao <= 0 || duracao <= 0) return null
        return (posicao.toFloat() / duracao).coerceIn(0f, 1f)
    }
}
