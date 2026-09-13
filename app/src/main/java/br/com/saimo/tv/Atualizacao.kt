package br.com.saimo.tv

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import androidx.media3.common.util.UnstableApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Request
import org.json.JSONObject
import java.io.File

/**
 * Procura versão nova no GitHub e instala, com o aval de quem usa.
 *
 * O APK é instalado à mão, fora de loja, então não existe atualização
 * automática de fábrica: sem isto, uma correção só chega a quem lembra de
 * baixar o arquivo de novo — e num aparelho de TV isso praticamente não
 * acontece.
 *
 * A checagem é barata (um JSON de alguns KB) e acontece na abertura, com duas
 * travas para não incomodar: seis horas entre consultas e a versão que a pessoa
 * mandou pular, que não pergunta de novo.
 */
@UnstableApi
object Atualizacao {

    private const val REPO = "gabrielsaimo/SaimoPlayer"
    private const val ARQUIVO = "atualizacao"
    private const val PULADA = "pulada"
    private const val VISTO = "visto"
    private const val PENDENTE = "pendente"
    private const val ESPERA_MS = 6L * 60 * 60 * 1000

    data class Versao(val tag: String, val numero: String, val notas: String, val apk: String)

    /**
     * Devolve a versão nova quando há uma para oferecer, ou nulo.
     *
     * `manual` vem de quem pediu para checar: aí não há espera nem versão
     * pulada que valha. Uma atualização que a pessoa já aceitou e não terminou
     * conta como manual também — ver [marcarPendente].
     */
    suspend fun procurar(context: Context, manual: Boolean = false): Versao? =
        withContext(Dispatchers.IO) {
            val prefs = context.getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE)
            val agora = System.currentTimeMillis()
            val forcar = manual || prefs.getString(PENDENTE, null) != null
            if (!forcar && agora - prefs.getLong(VISTO, 0) < ESPERA_MS) return@withContext null
            prefs.edit().putLong(VISTO, agora).apply()

            val versao = buscar() ?: return@withContext null
            if (!forcar && prefs.getString(PULADA, null) == versao.tag) return@withContext null
            if (!maisNova(versao.numero, BuildConfig.VERSION_NAME)) {
                esquecerPendente(context)
                return@withContext null
            }
            versao
        }

    fun pular(context: Context, versao: Versao) {
        context.getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE)
            .edit().putString(PULADA, versao.tag).remove(PENDENTE).apply()
    }

    /**
     * Lembra que a pessoa escolheu atualizar.
     *
     * Do Android 8 em diante, ligar "instalar apps desconhecidos" para o app
     * faz o sistema matar o processo na hora. Sem esta marca, quem voltava das
     * configurações abria o app de novo e só via a oferta seis horas depois —
     * parecia que o app tinha fechado sozinho e a atualização sumido.
     */
    fun marcarPendente(context: Context, versao: Versao) {
        context.getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE)
            .edit().putString(PENDENTE, versao.tag).apply()
    }

    fun temPendente(context: Context): Boolean =
        context.getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE).getString(PENDENTE, null) != null

    fun esquecerPendente(context: Context) {
        context.getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE)
            .edit().remove(PENDENTE).apply()
    }

    private fun buscar(): Versao? = runCatching {
        val pedido = Request.Builder()
            .url("https://api.github.com/repos/$REPO/releases/latest")
            .header("Accept", "application/vnd.github+json")
            .header("User-Agent", Playback.DEFAULT_USER_AGENT)
            .build()
        val corpo = Playback.client.newCall(pedido).execute().use { resposta ->
            if (!resposta.isSuccessful) return@runCatching null
            resposta.body?.string()
        } ?: return@runCatching null

        val raiz = JSONObject(corpo)
        val tag = raiz.optString("tag_name").ifBlank { return@runCatching null }
        val ativos = raiz.optJSONArray("assets") ?: return@runCatching null
        // O mesmo release traz TV, Mac e celular. O da TV é o SaimoTV.apk; na
        // falta dele, qualquer ".apk" sem "cell" no nome. A regra antiga só
        // excluía nome *começando* por "saimo-cell", e o celular subiu como
        // "SaimoTV-Cell.apk": em ordem alfabética ele vem antes, e a TV Box
        // baixava e instalava o app do celular no lugar da atualização.
        val nomeDoCelular = Regex("cell|celular|mobile", RegexOption.IGNORE_CASE)
        var apk: String? = null
        for (i in 0 until ativos.length()) {
            val item = ativos.getJSONObject(i)
            val nome = item.optString("name")
            if (nome.equals("SaimoTV.apk", ignoreCase = true)) {
                apk = item.optString("browser_download_url")
                break
            }
            if (apk == null && nome.endsWith(".apk", true) && !nomeDoCelular.containsMatchIn(nome)) {
                apk = item.optString("browser_download_url")
            }
        }
        // A tag precisa ter cara de versão ("v1.2" ou "1.2.3"). Um "beta-v2"
        // não diz o que é mais novo que o quê, e comparar o número solto dele
        // com 1.0.0 ofereceria uma atualização para trás.
        val numero = numeroDaTag(tag) ?: return@runCatching null
        Versao(tag, numero, raiz.optString("body"), apk ?: return@runCatching null)
    }.getOrNull()

    /** "v1.2.3" e "1.2" viram "1.2.3" e "1.2"; qualquer outra coisa, nulo. */
    fun numeroDaTag(tag: String): String? =
        Regex("^[vV]?\\d+\\.\\d+(\\.\\d+)?$").find(tag)?.value?.trimStart('v', 'V')

    /**
     * Compara 1.10.0 com 1.9.3 pelo número de cada parte, não pelo texto —
     * como texto, "1.10" viria antes de "1.9".
     */
    fun maisNova(candidata: String, atual: String): Boolean {
        val a = candidata.split(".").map { it.filter(Char::isDigit).toIntOrNull() ?: 0 }
        val b = atual.split(".").map { it.filter(Char::isDigit).toIntOrNull() ?: 0 }
        for (i in 0 until maxOf(a.size, b.size)) {
            val x = a.getOrElse(i) { 0 }
            val y = b.getOrElse(i) { 0 }
            if (x != y) return x > y
        }
        return false
    }

    /**
     * Baixa o APK e entrega ao instalador do sistema.
     *
     * Vai pelo PackageInstaller, e não por um Intent de arquivo: assim não
     * precisa de FileProvider nem de permissão de leitura externa, e é o
     * próprio sistema quem mostra a confirmação — que num aparelho de TV é a
     * tela que o controle sabe operar.
     *
     * `aoAndar` recebe de 0 a 1 enquanto baixa; devolve a mensagem de erro, ou
     * nulo quando a instalação foi entregue ao sistema.
     */
    suspend fun instalar(context: Context, versao: Versao,
                         aoAndar: (Float) -> Unit): String? = withContext(Dispatchers.IO) {
        val destino = File(context.cacheDir, "saimo-update.apk")
        runCatching {
            val pedido = Request.Builder().url(versao.apk)
                .header("User-Agent", Playback.DEFAULT_USER_AGENT).build()
            Playback.client.newCall(pedido).execute().use { resposta ->
                if (!resposta.isSuccessful) return@withContext "download recusado (${resposta.code})"
                val corpo = resposta.body ?: return@withContext "download vazio"
                val total = corpo.contentLength()
                destino.outputStream().use { saida ->
                    corpo.byteStream().use { entrada ->
                        val buffer = ByteArray(64 * 1024)
                        var lido = 0L
                        while (true) {
                            val n = entrada.read(buffer)
                            if (n <= 0) break
                            saida.write(buffer, 0, n)
                            lido += n
                            if (total > 0) aoAndar(lido.toFloat() / total)
                        }
                    }
                }
            }
        }.onFailure { return@withContext "falha ao baixar: ${it.message}" }

        runCatching {
            val instalador = context.packageManager.packageInstaller
            val parametros = PackageInstaller.SessionParams(
                PackageInstaller.SessionParams.MODE_FULL_INSTALL)
            val id = instalador.createSession(parametros)
            instalador.openSession(id).use { sessao ->
                sessao.openWrite("saimo", 0, destino.length()).use { saida ->
                    destino.inputStream().use { it.copyTo(saida) }
                    sessao.fsync(saida)
                }
                // O Intent precisa ser explícito: do Android 14 em diante um
                // PendingIntent mutável com Intent implícito é recusado, e a
                // instalação morria antes de o sistema perguntar qualquer coisa.
                val aviso = PendingIntent.getBroadcast(
                    context, id,
                    Intent("br.com.saimo.tv.INSTALADO").setPackage(context.packageName),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
                sessao.commit(aviso.intentSender)
            }
            // Entregue ao sistema: dali em diante quem decide é a tela dele.
            esquecerPendente(context)
        }.onFailure { return@withContext "falha ao instalar: ${it.message}" }

        null
    }
}
