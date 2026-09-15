package br.com.saimo.tv

/**
 * Extra line-up, present only after the code is typed on the remote.
 *
 * The channels live in a separate list rather than behind a flag on the normal
 * ones so nothing in the interface has to know they exist: while locked they
 * are not in the list at all, so there is no label, no gap and no count to
 * notice. Nothing is written to disk either — closing the app locks it again,
 * and the next person to open it sees exactly what everyone else sees.
 */
object Unlock {

    private const val CODE = "1010"

    var unlocked = false
        private set

    /** Canais à vista agora: a lista publicada mais, se destrancado, os extras. */
    fun channels(): List<Channel> =
        if (unlocked) Remote.channels + Remote.restritos else Remote.channels

    /**
     * Diz se a sequência digitada era o código, alternando o estado quando for.
     *
     * Devolver falso é indistinguível de um número de canal que não existe: um
     * código errado se parece exatamente com nada acontecendo.
     */
    fun consume(typed: String): Boolean {
        if (typed != CODE || Remote.restritos.isEmpty()) return false
        unlocked = !unlocked
        return true
    }
}
