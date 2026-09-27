package br.com.saimo.tv

import android.app.SearchManager
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.provider.BaseColumns
import androidx.media3.common.util.UnstableApi
import kotlinx.coroutines.runBlocking

/**
 * O acervo na busca da própria TV.
 *
 * O Android TV pergunta a cada app que se declara pesquisável o que ele tem
 * com aquele nome — é assim que "Vingadores" dito no microfone do controle, na
 * tela inicial, mostra o filme do Saimo junto do resto. Escolher o resultado
 * abre o link `saimo://titulo`, que cai direto na ficha.
 *
 * Roda fora da linha principal (o sistema chama de uma thread própria), então
 * esperar o índice aqui não trava tela nenhuma.
 */
@UnstableApi
class BuscaDoSistema : ContentProvider() {

    override fun onCreate(): Boolean = true

    override fun query(
        uri: Uri, projection: Array<out String>?, selection: String?,
        selectionArgs: Array<out String>?, sortOrder: String?,
    ): Cursor {
        val colunas = arrayOf(
            BaseColumns._ID,
            SearchManager.SUGGEST_COLUMN_TEXT_1,
            SearchManager.SUGGEST_COLUMN_TEXT_2,
            SearchManager.SUGGEST_COLUMN_RESULT_CARD_IMAGE,
            SearchManager.SUGGEST_COLUMN_CONTENT_TYPE,
            SearchManager.SUGGEST_COLUMN_PRODUCTION_YEAR,
            SearchManager.SUGGEST_COLUMN_INTENT_ACTION,
            SearchManager.SUGGEST_COLUMN_INTENT_DATA,
        )
        val cursor = MatrixCursor(colunas)
        val termo = (selectionArgs?.firstOrNull() ?: uri.lastPathSegment).orEmpty()
            .takeUnless { it == SearchManager.SUGGEST_URI_PATH_QUERY }.orEmpty().trim()
        if (termo.length < 2) return cursor
        val contexto = context ?: return cursor
        if (Preferencias.estaPronta().not()) Preferencias.iniciar(contexto)
        val limite = uri.getQueryParameter(SearchManager.SUGGEST_PARAMETER_LIMIT)?.toIntOrNull() ?: 20
        val achados = runCatching {
            runBlocking {
                Generos.carregar(contexto)
                Vod.buscar(contexto, termo, limite)
            }
        }.getOrDefault(emptyList())
        achados.forEachIndexed { i, a ->
            val ano = a.ano.ifBlank { Regex("\\((\\d{4})\\)\\s*$").find(a.titulo)?.groupValues?.get(1).orEmpty() }
            cursor.addRow(arrayOf<Any?>(
                i,
                Generos.semAno(a.titulo),
                listOf(if (a.serie) "Série" else "Filme", ano).filter { it.isNotBlank() }.joinToString(" · "),
                Generos.capa(a.nomeCompleto, a.serie) ?: Generos.capa(a.titulo, a.serie),
                if (a.serie) "video/x-series" else "video/mp4",
                ano.toIntOrNull(),
                android.content.Intent.ACTION_VIEW,
                Links.ficha(Alvo(a.titulo, a.serie, a.letra, a.ano)).toString(),
            ))
        }
        return cursor
    }

    override fun getType(uri: Uri): String = SearchManager.SUGGEST_MIME_TYPE
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?) = 0
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?) = 0
}
