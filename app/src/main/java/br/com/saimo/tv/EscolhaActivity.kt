package br.com.saimo.tv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

/**
 * A abertura: TV ao vivo, ou filmes e séries.
 *
 * O app abria direto no último canal. Quem ligava a TV Box para ver um filme
 * esperava o canal conectar, o vídeo começar e o áudio sair, só para então
 * sair dele — e num aparelho de 1 GB esse canal ainda ficava ocupando memória
 * por baixo do catálogo inteiro.
 *
 * Aqui são dois cartões e nada mais. O foco já começa no que a pessoa escolheu
 * da última vez, então para quem sempre vê a mesma coisa continua sendo um OK
 * só. Voltar de qualquer um dos dois lados cai aqui, e voltar daqui sai do app.
 */
class EscolhaActivity : AppCompatActivity() {

    private lateinit var tv: View
    private lateinit var filmes: View
    private lateinit var status: TextView
    private val atualizacao by lazy { OfertaDeAtualizacao(this) { mostrarStatus(it) } }
    private var jaIniciou = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_escolha)

        tv = findViewById(R.id.escolhaTv)
        filmes = findViewById(R.id.escolhaFilmes)
        status = findViewById(R.id.escolhaStatus)

        findViewById<TextView>(R.id.escolhaSaudacao).text = saudacao()
        findViewById<TextView>(R.id.escolhaVersao).text =
            getString(R.string.escolha_versao, BuildConfig.VERSION_NAME)

        tv.setOnClickListener { abrir(TV) }
        filmes.setOnClickListener { abrir(FILMES) }
        for (cartao in listOf(tv, filmes)) {
            // Focável também no modo de toque: alguns TV Box com mouse aéreo
            // abrem nele, e aí o foco inicial não seria aplicado.
            cartao.isFocusableInTouchMode = true
            // O crescimento no foco é a GPU, não uma nova medição de layout:
            // resposta imediata sem custo, até num TV Box de 2016.
            cartao.setOnFocusChangeListener { view, foco ->
                val escala = if (foco) 1.06f else 1f
                view.animate().scaleX(escala).scaleY(escala).setDuration(140).start()
            }
        }

        (if (ultima(this) == FILMES) filmes else tv).requestFocus()
        contar()

        // A versão nova é oferecida aqui também: quem só assiste filmes nunca
        // passava pela tela de canais, que era a única que avisava.
        tv.postDelayed({ if (!isFinishing) atualizacao.ofertar() }, 2_500)
    }

    override fun onStart() {
        super.onStart()
        if (jaIniciou) atualizacao.retomarSePendente()
        jaIniciou = true
    }

    override fun onResume() {
        super.onResume()
        // Voltando de um dos lados, o foco fica no cartão de onde se veio.
        if (!tv.hasFocus() && !filmes.hasFocus()) {
            (if (ultima(this) == FILMES) filmes else tv).requestFocus()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Atalhos de controle remoto: o botão de guia ou de canal vai direto
        // para a TV, e o de "filmes"/busca vai para o acervo.
        when (keyCode) {
            KeyEvent.KEYCODE_GUIDE, KeyEvent.KEYCODE_CHANNEL_UP,
            KeyEvent.KEYCODE_CHANNEL_DOWN, KeyEvent.KEYCODE_TV -> { abrir(TV); return true }
            KeyEvent.KEYCODE_SEARCH -> { abrir(FILMES); return true }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun abrir(lado: String) {
        getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putString(ULTIMA, lado).apply()
        val destino = if (lado == TV) MainActivity::class.java else VodActivity::class.java
        startActivity(Intent(this, destino))
    }

    /** "Boa noite" às dez da noite: a abertura fala como gente. */
    private fun saudacao(): String {
        val hora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return getString(when (hora) {
            in 5..11 -> R.string.escolha_bom_dia
            in 12..17 -> R.string.escolha_boa_tarde
            else -> R.string.escolha_boa_noite
        })
    }

    /**
     * Quantos canais e quantos títulos há: o número diz o que cada lado tem
     * melhor que qualquer ícone. Os dois vêm do que já está em disco — nada
     * aqui espera a rede para a tela aparecer.
     */
    private fun contar() {
        lifecycleScope.launch(semDerrubar) {
            val canais = withContext(Dispatchers.IO) {
                Remote.loadCached(this@EscolhaActivity)
                Remote.channels.size
            }
            if (canais > 0) {
                findViewById<TextView>(R.id.escolhaTvDetalhe).text =
                    resources.getQuantityString(R.plurals.escolha_canais, canais, canais)
            }
            val titulos = runCatching { Vod.indice(this@EscolhaActivity) }.getOrNull()
                ?.sumOf { it.filmes + it.series } ?: 0
            if (titulos > 0 && !isFinishing) {
                findViewById<TextView>(R.id.escolhaFilmesDetalhe).text =
                    resources.getQuantityString(R.plurals.escolha_titulos,
                        titulos, "%,d".format(titulos).replace(',', '.'))
            }
        }
    }

    private fun mostrarStatus(texto: String) {
        status.text = texto
        status.visibility = View.VISIBLE
    }

    companion object {
        private const val PREFS = "escolha"
        private const val ULTIMA = "ultima"
        private const val TV = "tv"
        private const val FILMES = "filmes"

        private fun ultima(context: Context): String =
            context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .getString(ULTIMA, TV) ?: TV
    }
}
