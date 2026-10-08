package br.com.saimo.tv

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.media3.common.util.UnstableApi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/** A aba do menu do topo que cada tela representa. */
enum class Aba { INICIO, AO_VIVO, EVENTOS, ON_DEMAND, FAVORITOS, RADIOS, EXTRAS, BUSCAR, AJUSTES, NENHUMA }

/**
 * O menu do topo, o mesmo em todas as telas.
 *
 * Antes ele só existia na tela inicial: de dentro de uma ficha, para ir às
 * séries era VOLTAR, VOLTAR, VOLTAR. Agora a seta para cima, de qualquer
 * tela, chega nele — e de lá tudo está a um OK.
 *
 * Navegar pelo menu não empilha telas: volta à inicial e abre o destino por
 * cima dela, então VOLTAR sempre leva à tela inicial, nunca a um labirinto.
 */
@UnstableApi
object MenuGlobal {

    fun criar(tela: Activity, atual: Aba, aoFocar: (() -> Unit)? = null, aoInicio: (() -> Unit)? = null): View {
        val d = tela.resources.displayMetrics.density
        val barra = LinearLayout(tela).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding((40 * d).toInt(), (18 * d).toInt(), (40 * d).toInt(), (4 * d).toInt())
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        barra.addView(ImageView(tela).apply {
            setImageResource(R.mipmap.ic_launcher)
            layoutParams = LinearLayout.LayoutParams((28 * d).toInt(), (28 * d).toInt()).apply { marginEnd = (8 * d).toInt() }
        })
        val rolagem = HorizontalScrollView(tela).apply {
            isHorizontalScrollBarEnabled = false
            clipToPadding = false
            // A rolagem em si não recebe foco — só as abas.
            isFocusable = false
            descendantFocusability = ViewGroup.FOCUS_AFTER_DESCENDANTS
            layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
        }
        val abas = LinearLayout(tela).apply { orientation = LinearLayout.HORIZONTAL; id = R.id.menuGlobalAbas }
        rolagem.addView(abas)
        barra.addView(rolagem)

        val itens = listOf(
            Triple(Aba.BUSCAR, R.string.menu_buscar, R.drawable.ic_search),
            Triple(Aba.INICIO, R.string.menu_inicio, R.drawable.ic_home),
            Triple(Aba.AO_VIVO, R.string.menu_ao_vivo, R.drawable.ic_live_tv),
            Triple(Aba.EVENTOS, R.string.menu_eventos, R.drawable.ic_football),
            Triple(Aba.ON_DEMAND, R.string.menu_on_demand, R.drawable.ic_movie),
            Triple(Aba.FAVORITOS, R.string.vod_favoritos, R.drawable.ic_star),
            Triple(Aba.RADIOS, R.string.menu_radios, R.drawable.ic_radio),
            Triple(Aba.EXTRAS, R.string.vod_extras, R.drawable.ic_more),
            Triple(Aba.AJUSTES, R.string.menu_ajustes, R.drawable.ic_settings)
        )
        val temRadios = java.io.File(tela.filesDir, "vod/radios.txt").exists() || java.io.File(tela.filesDir, "radios.txt").exists()

        for ((aba, texto, icone) in itens) {
            // Always show radios button
            abas.addView(TextView(tela).apply {
                text = tela.getString(texto)
                tag = aba
                textSize = 16f
                maxLines = 1
                typeface = android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL)
                setTextColor(ContextCompat.getColorStateList(tela, R.color.texto_botao_ficha))
                setBackgroundResource(R.drawable.aba_menu)
                isFocusable = true
                isSelected = aba == atual
                // Extras só existe depois do código, e some de novo sem deixar vão.
                if (aba == Aba.EXTRAS) visibility = if (Unlock.unlocked) View.VISIBLE else View.GONE
                setPadding((11 * d).toInt(), (6 * d).toInt(), (11 * d).toInt(), (6 * d).toInt())
                layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT).apply { marginEnd = (1 * d).toInt() }
                when {
                    aba == Aba.AJUSTES -> Icones.so(this, icone)
                    icone != 0 -> Icones.inicio(this, icone)
                }
                setOnFocusChangeListener { _, foco -> if (foco) aoFocar?.invoke() }
                // Para os lados, o menu anda de aba em aba e para nas pontas:
                // no fim, a busca do sistema achava algo "à direita" lá embaixo.
                setOnKeyListener { v, codigo, evento ->
                    if (evento.action != android.view.KeyEvent.ACTION_DOWN) return@setOnKeyListener false
                    val passo = when (codigo) {
                        android.view.KeyEvent.KEYCODE_DPAD_RIGHT -> 1
                        android.view.KeyEvent.KEYCODE_DPAD_LEFT -> -1
                        else -> return@setOnKeyListener false
                    }
                    var i = abas.indexOfChild(v) + passo
                    while (i in 0 until abas.childCount && abas.getChildAt(i).visibility != View.VISIBLE) i += passo
                    abas.getChildAt(i)?.requestFocus()
                    true
                }
                setOnClickListener {
                    if (aba == Aba.INICIO && aoInicio != null) aoInicio() else ir(tela, aba, atual)
                }
            })
        }

        val relogio = TextView(tela).apply {
            textSize = 18f
            setTextColor(ContextCompat.getColor(tela, R.color.text_primary))
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT).apply { marginStart = (12 * d).toInt() }
        }
        barra.addView(relogio)
        val hora = SimpleDateFormat("HH:mm", Locale("pt", "BR"))
        val handler = Handler(Looper.getMainLooper())
        val tique = object : Runnable {
            override fun run() {
                relogio.text = hora.format(Date())
                if (relogio.isAttachedToWindow || !tela.isFinishing) handler.postDelayed(this, 20_000)
            }
        }
        barra.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) { handler.post(tique) }
            override fun onViewDetachedFromWindow(v: View) { handler.removeCallbacks(tique) }
        })
        return barra
    }

    /** Foca a aba da tela atual (ou a primeira): é para onde VOLTAR leva na inicial. */
    fun focar(barra: View?, aba: Aba) {
        val abas = barra?.findViewById<LinearLayout>(R.id.menuGlobalAbas) ?: return
        (abas.findViewWithTag<View>(aba) ?: abas.getChildAt(0))?.requestFocus()
    }

    fun temFoco(barra: View?) = barra?.hasFocus() == true

    /** Mostra ou esconde o Extras conforme o código: o menu já montado não se refaz sozinho. */
    fun atualizar(barra: View?) {
        val abas = barra?.findViewById<LinearLayout>(R.id.menuGlobalAbas) ?: return
        abas.findViewWithTag<View>(Aba.EXTRAS)?.visibility = if (Unlock.unlocked) View.VISIBLE else View.GONE
    }

    private fun ir(tela: Activity, aba: Aba, atual: Aba) {
        if (aba == atual) return
        val inicio = Intent(tela, EscolhaActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val destino: Intent? = when (aba) {
            Aba.INICIO, Aba.NENHUMA -> null
            Aba.AO_VIVO -> Intent(tela, MainActivity::class.java)
            Aba.EVENTOS -> Intent(tela, EventosActivity::class.java)
            Aba.ON_DEMAND -> Intent(tela, OnDemandActivity::class.java)
            Aba.FAVORITOS -> GradeActivity.intent(tela, GradeActivity.FAVORITOS)
            Aba.RADIOS -> Intent(tela, RadiosActivity::class.java)
            Aba.EXTRAS -> GradeActivity.intent(tela, GradeActivity.EXTRAS)
            Aba.BUSCAR -> Intent(tela, BuscaActivity::class.java)
            Aba.AJUSTES -> Intent(tela, AjustesActivity::class.java)
        }
        if (destino == null) tela.startActivity(inicio)
        else tela.startActivities(arrayOf(inicio, destino))
        tela.overridePendingTransition(0, 0)
    }
}

/**
 * Tela com o menu do topo e a imagem de fundo do app.
 *
 * Quem herda daqui chama `setContentView(layout)` como sempre: o menu entra
 * em cima do layout, sem nenhum XML repetido.
 */
@UnstableApi
abstract class TelaComMenu : AppCompatActivity() {

    protected open val aba: Aba = Aba.NENHUMA
    protected open fun aoFocarMenu() {}
    protected open val aoMenuInicio: (() -> Unit)? = null
    protected var barraMenu: View? = null
        private set

    /**
     * Foca o que `achar` devolver assim que existir — listas desenham um quadro
     * depois de receberem os dados, e um único `post` às vezes chega cedo.
     */
    protected fun focarQuandoPronto(tentativas: Int = 20, achar: () -> View?) {
        val alvo = achar()
        if (alvo?.requestFocus() == true) return
        if (tentativas > 0) window.decorView.postDelayed({ focarQuandoPronto(tentativas - 1, achar) }, 60)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawableResource(R.drawable.fundo_app)
    }

    override fun onResume() {
        super.onResume()
        MenuGlobal.atualizar(barraMenu)
    }

    override fun setContentView(layoutResID: Int) {
        // Recorta cada parte no próprio espaço: a imagem larga da ficha, sem
        // isso, desenhava por cima do menu.
        val raiz = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        val barra = MenuGlobal.criar(this, aba, { aoFocarMenu() }, aoMenuInicio)
        barraMenu = barra
        raiz.addView(barra)
        val conteudo = layoutInflater.inflate(layoutResID, raiz, false)
        raiz.addView(conteudo, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f))
        super.setContentView(raiz)
        // O sistema dá o primeiro foco ao primeiro focável da tela — que seria
        // a aba "Início". Enquanto o conteúdo não pega o foco, o menu fica de
        // fora; depois (ou em dois segundos, se o conteúdo não tiver nada), a
        // seta para cima chega nele normalmente.
        val abas = barra.findViewById<ViewGroup>(R.id.menuGlobalAbas)
        abas.descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS
        val liberar = Runnable { abas.descendantFocusability = ViewGroup.FOCUS_AFTER_DESCENDANTS }
        raiz.viewTreeObserver.addOnGlobalFocusChangeListener { _, novo ->
            if (novo != null && abas.descendantFocusability == ViewGroup.FOCUS_BLOCK_DESCENDANTS) liberar.run()
        }
        raiz.postDelayed(liberar, 2_000)
    }
}
