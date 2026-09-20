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
            prefs.edit().remove(chave).remove("$chave|d").remove("$chave|t").apply()
            return
        }
        prefs.edit()
            .putLong(chave, posicao)
            .putLong("$chave|d", duracao)
            .putLong("$chave|t", System.currentTimeMillis())
            .apply()
    }

    /**
     * O que está pela metade, do visto mais recentemente para o mais antigo.
     *
     * É o que alimenta a fileira "Continue assistindo". O horário passou a ser
     * gravado junto por causa dela: sem ele não dá para dizer qual filme foi o
     * último, e uma fileira em ordem alfabética não é continuar coisa nenhuma.
     * Quem gravou antes disso não tem horário e fica no fim — uma vez só, até
     * abrir o título de novo.
     */
    fun emAndamento(context: Context): List<Andamento> {
        val prefs = context.getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE)
        val tudo = prefs.all
        val out = mutableListOf<Andamento>()
        for ((chave, valor) in tudo) {
            if (chave.endsWith("|d") || chave.endsWith("|t")) continue
            val posicao = valor as? Long ?: continue
            val duracao = tudo["$chave|d"] as? Long ?: continue
            if (posicao <= 0 || duracao <= 0) continue
            val quando = tudo["$chave|t"] as? Long ?: 0L
            val campos = chave.split("|")
            val serie = campos.firstOrNull() == "s"
            val titulo = campos.getOrNull(1) ?: continue
            val rotulo = if (serie && campos.size >= 4) {
                "$titulo · T${campos[2]} E${campos[3]}"
            } else {
                titulo
            }
            out += Andamento(
                chave = chave,
                titulo = titulo,
                rotulo = rotulo,
                serie = serie,
                fracao = (posicao.toFloat() / duracao).coerceIn(0f, 1f),
                quando = quando,
            )
        }
        return out.sortedByDescending { it.quando }
    }

    data class Andamento(
        val chave: String,
        /// O nome do título, que é por onde se acha ele no acervo.
        val titulo: String,
        /// O que aparece na capa: na série, com temporada e episódio.
        val rotulo: String,
        val serie: Boolean,
        val fracao: Float,
        val quando: Long,
    )

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
