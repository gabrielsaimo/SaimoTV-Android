package br.com.saimo.tv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A busca, uma só para tudo: canais, filmes, séries, animes, doramas e atores.
 *
 * Antes: digitar tudo, apertar "Buscar", esperar uma lista de nomes — e anime
 * e dorama nem entravam. Agora os resultados mudam a cada letra, em fileiras de
 * capa, com o microfone para quem tem e o teclado físico para quem tem.
 */
@UnstableApi
class BuscaActivity : AppCompatActivity() {

    companion object {
        private const val TERMO = "busca.termo"
        fun abrir(context: Context, termo: String? = null) =
            context.startActivity(Intent(context, BuscaActivity::class.java).putExtra(TERMO, termo))
    }

    private lateinit var campo: TextView
    private lateinit var estado: TextView
    private lateinit var resultados: RecyclerView
    private val digitado = StringBuilder()
    private var trabalho: Job? = null
    private val adaptador by lazy { Inicio.Adapter(escopo = lifecycleScope, capaDe = { t, s -> Generos.capa(t, s) }) }

    private val voz = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { r ->
        val dito = r.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull() ?: return@registerForActivityResult
        digitado.clear(); digitado.append(dito)
        atualizar(imediato = true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busca)
        campo = findViewById(R.id.buscaTermo)
        estado = findViewById(R.id.buscaEstado)
        resultados = findViewById(R.id.buscaResultados)
        resultados.layoutManager = LinearLayoutManager(this)
        resultados.adapter = adaptador
        montarTeclado()
        intent.getStringExtra(TERMO)?.let { digitado.append(it) }
        // Prepara o índice enquanto a pessoa ainda procura a primeira letra.
        lifecycleScope.launch(semDerrubar) { Generos.carregar(this@BuscaActivity); Vod.entradas(this@BuscaActivity) }
        atualizar(imediato = true)
    }

    private fun montarTeclado() {
        val grade = findViewById<GridLayout>(R.id.buscaTeclas)
        val teclas = ('A'..'Z').map { it.toString() } + (0..9).map { it.toString() }
        for (t in teclas) grade.addView(tecla(t, 1) { digitar(t.lowercase()) })
        grade.addView(tecla(getString(R.string.busca_espaco), 2) { digitar(" ") })
        grade.addView(tecla(getString(R.string.busca_apagar), 1) { apagar() })
        grade.addView(tecla(getString(R.string.busca_limpar), 2) { digitado.clear(); atualizar(true) })
        val temVoz = packageManager.queryIntentActivities(Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0).isNotEmpty()
        if (temVoz) grade.addView(tecla(getString(R.string.busca_voz), 1) { ouvir() })
        grade.post { grade.getChildAt(0)?.requestFocus() }
    }

    private fun tecla(texto: String, colunas: Int, acao: () -> Unit): View = TextView(this).apply {
        text = texto
        gravity = Gravity.CENTER
        textSize = if (texto.length > 1) 15f else 20f
        typeface = android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL)
        setTextColor(ContextCompat.getColorStateList(context, R.color.texto_botao_ficha))
        setBackgroundResource(R.drawable.item_painel)
        isFocusable = true
        setOnClickListener { acao() }
        val d = resources.displayMetrics.density
        layoutParams = GridLayout.LayoutParams().apply {
            width = 0
            height = (46 * d).toInt()
            columnSpec = GridLayout.spec(GridLayout.UNDEFINED, colunas, 1f)
            setMargins((2 * d).toInt(), (2 * d).toInt(), (2 * d).toInt(), (2 * d).toInt())
        }
    }

    private fun ouvir() {
        runCatching {
            voz.launch(Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                .putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                .putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR")
                .putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.busca_dica)))
        }
    }

    private fun digitar(t: String) {
        if (digitado.length >= 40) return
        digitado.append(t)
        atualizar()
    }

    private fun apagar() {
        if (digitado.isNotEmpty()) digitado.deleteCharAt(digitado.length - 1)
        atualizar()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Teclado de verdade (USB, Bluetooth, o do celular): digita direto.
        val ch = event?.unicodeChar ?: 0
        if (ch > 0 && (Character.isLetterOrDigit(ch) || ch == ' '.code) &&
            keyCode != KeyEvent.KEYCODE_DPAD_CENTER && keyCode != KeyEvent.KEYCODE_ENTER) {
            digitar(ch.toChar().lowercase())
            return true
        }
        if (keyCode == KeyEvent.KEYCODE_DEL) { apagar(); return true }
        if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_VOICE_ASSIST) { ouvir(); return true }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * Refaz os resultados.
     *
     * Espera a pessoa parar de digitar um instante — uma busca por letra em
     * cinquenta mil títulos travaria um TV Box — e cancela a anterior.
     */
    private fun atualizar(imediato: Boolean = false) {
        campo.text = digitado
        trabalho?.cancel()
        val termo = digitado.toString().trim()
        trabalho = lifecycleScope.launch(semDerrubar) {
            if (!imediato) delay(280)
            if (termo.isEmpty()) { mostrarInicio(); return@launch }
            val filas = mutableListOf<Inicio.Fila>()

            val alvoNorm = Vod.normalizar(termo)
            val canais = Unlock.channels().filter { Vod.normalizar(it.name).contains(alvoNorm) }.take(20)
            if (canais.isNotEmpty()) filas += Inicio.Fila(getString(R.string.busca_canais), canais.map { c ->
                Inicio.Cartao(c.name, c.logo.orEmpty(), c.name.take(1).uppercase()) {
                    startActivity(Intent(this@BuscaActivity, MainActivity::class.java).putExtra(MainActivity.EXTRA_CANAL, c.name))
                }
            }, Inicio.Tipo.LARGO)

            val achados = if (termo.length >= 2) Vod.buscar(this@BuscaActivity, termo, 80) else emptyList()
            val filmes = achados.filter { !it.serie }
            val series = achados.filter { it.serie }
            if (filmes.isNotEmpty()) filas += Inicio.Fila(getString(R.string.vod_filmes), filmes.map { cartao(it) })
            if (series.isNotEmpty()) filas += Inicio.Fila(getString(R.string.vod_series), series.map { cartao(it) })

            val colecoes = if (termo.length >= 2) Vod.buscarColecoes(this@BuscaActivity, termo).take(30) else emptyList()
            if (colecoes.isNotEmpty()) filas += Inicio.Fila(getString(R.string.busca_colecoes), colecoes.map { (tipo, c) ->
                val alvo = Alvo(c.titulo, true, "", c.ano, tipo, c.tmdbId.toIntOrNull() ?: 0)
                Inicio.Cartao(c.nomeCompleto, "", c.titulo.take(1).uppercase(), procurarCapa = true,
                    nomeDaCapa = c.titulo, alvo = alvo) { lembrar(); FichaActivity.abrir(this@BuscaActivity, alvo) }
            })

            mostrar(filas, termo)
            if (achados.isEmpty() && colecoes.isEmpty() && termo.length >= 3) Telemetria.buscouSemAchar("vod", termo)

            // Atores vêm do TMDB: por último, e só com o termo parado.
            if (termo.length >= 3) {
                delay(500)
                val pessoas = Detalhes.pessoas(termo)
                if (pessoas.isNotEmpty() && digitado.toString().trim() == termo) {
                    filas += Inicio.Fila(getString(R.string.busca_atores), pessoas.map { p ->
                        Inicio.Cartao(p.nome, p.foto.orEmpty(), p.nome.take(1).uppercase()) {
                            lembrar(); AtorActivity.abrir(this@BuscaActivity, p)
                        }
                    })
                    mostrar(filas, termo)
                }
            }
        }
    }

    private fun cartao(a: Vod.Achado): Inicio.Cartao {
        val alvo = Alvo(a.titulo, a.serie, a.letra, a.ano)
        return Inicio.Cartao(a.nomeCompleto, "", a.titulo.take(1).uppercase(), procurarCapa = a.serie,
            nomeDaCapa = a.nomeCompleto, alvo = alvo,
            progresso = if (a.serie) null else Progresso.fracao(this, Progresso.chaveFilme(a.titulo))) {
            lembrar(); FichaActivity.abrir(this, alvo)
        }
    }

    private fun lembrar() = Preferencias.lembrarBusca(digitado.toString())

    private fun mostrar(filas: List<Inicio.Fila>, termo: String) {
        adaptador.trocar(filas)
        estado.visibility = if (filas.isEmpty()) View.VISIBLE else View.GONE
        estado.text = getString(R.string.busca_nada, termo)
    }

    /** Sem nada digitado: as buscas recentes, a um OK. */
    private fun mostrarInicio() {
        val recentes = Preferencias.historicoBusca
        if (recentes.isEmpty()) {
            adaptador.trocar(emptyList())
            estado.visibility = View.VISIBLE
            estado.text = getString(R.string.busca_comece)
            return
        }
        estado.visibility = View.GONE
        adaptador.trocar(listOf(Inicio.Fila(getString(R.string.busca_recentes), recentes.map { t ->
            Inicio.Cartao(t, "", "↺", subtitulo = null) {
                digitado.clear(); digitado.append(t); atualizar(true)
            }
        }, Inicio.Tipo.LARGO)))
    }
}
