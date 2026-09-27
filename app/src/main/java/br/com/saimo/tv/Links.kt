package br.com.saimo.tv

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.media3.common.util.UnstableApi

/**
 * Os links `saimo://` que levam direto a um lugar do app.
 *
 * São eles que a tela inicial do Google TV (Continuar assistindo, destaques)
 * e a busca do sistema abrem: `saimo://assistir` toca, `saimo://titulo` abre a
 * ficha, `saimo://canal` sintoniza um canal ao vivo.
 */
@UnstableApi
object Links {

    private fun base(acao: String, alvo: Alvo) = Uri.Builder().scheme("saimo").authority(acao)
        .appendQueryParameter("t", alvo.titulo)
        .appendQueryParameter("s", if (alvo.serie) "1" else "0")
        .appendQueryParameter("l", alvo.letra)
        .appendQueryParameter("a", alvo.ano)
        .appendQueryParameter("c", alvo.colecao)
        .appendQueryParameter("id", alvo.tmdbId.toString())

    fun assistir(alvo: Alvo, temporada: Int = 0, episodio: Int = 0): Uri = base("assistir", alvo)
        .appendQueryParameter("T", temporada.toString())
        .appendQueryParameter("E", episodio.toString())
        .build()

    fun ficha(alvo: Alvo): Uri = base("titulo", alvo).build()

    fun canal(nome: String): Uri =
        Uri.Builder().scheme("saimo").authority("canal").appendQueryParameter("n", nome).build()

    private fun alvo(uri: Uri): Alvo? {
        val titulo = uri.getQueryParameter("t")?.takeIf { it.isNotBlank() } ?: return null
        return Alvo(titulo, uri.getQueryParameter("s") == "1", uri.getQueryParameter("l").orEmpty(),
            uri.getQueryParameter("a").orEmpty(), uri.getQueryParameter("c").orEmpty(),
            uri.getQueryParameter("id")?.toIntOrNull() ?: 0)
    }

    /**
     * Abre o que o link pede. Devolve falso se o link não é nosso ou está
     * incompleto — aí quem chamou segue o caminho normal da abertura.
     */
    fun abrir(context: Context, uri: Uri?): Boolean {
        if (uri?.scheme != "saimo") return false
        when (uri.host) {
            "assistir" -> {
                val alvo = alvo(uri) ?: return false
                PlayerActivity.abrir(context, alvo,
                    uri.getQueryParameter("T")?.toIntOrNull() ?: 0,
                    uri.getQueryParameter("E")?.toIntOrNull() ?: 0)
            }
            "titulo" -> FichaActivity.abrir(context, alvo(uri) ?: return false)
            "canal" -> context.startActivity(Intent(context, MainActivity::class.java)
                .putExtra(MainActivity.EXTRA_CANAL, uri.getQueryParameter("n") ?: return false))
            else -> return false
        }
        return true
    }
}
