package br.com.saimo.tv

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat

/**
 * Modo toque: óculos de realidade virtual (Meta Quest e Meta VR Glasses no
 * Horizon OS, Pico, HTC Vive Focus, Samsung Galaxy XR e os demais Android XR)
 * e celular ou tablet em que alguém instale o app da TV.
 *
 * Nesses aparelhos o app abre numa janela flutuante e é usado com as mãos:
 * olhar mira, pinçar os dedos é um toque. Não existe D-pad — os óculos da Meta
 * nem vêm com controle — e o app inteiro foi feito para o controle remoto.
 * Então, só quando está num desses, aparece o que falta para o toque fazer
 * tudo o que o D-pad faz: barra de botões sobre o vídeo, barra de tempo que se
 * arrasta, botão de voltar, fechar tocando fora.
 *
 * No TV Box nada disso existe: [ativo] é falso e toda peça daqui só é montada
 * atrás dele. Óculos se reconhecem por recurso do sistema e por fabricante;
 * celular, por ter tela de toque de verdade e não estar em modo TV. O recurso
 * "touchscreen" sozinho não serve: TV Box com firmware de tablet o declara sem
 * ter tela nenhuma — o que conta é a tela que o sistema de fato encontrou.
 *
 * Para testar num aparelho que não é óculos (ou desligar num que é), abra o
 * app uma vez com o extra `saimo_vr` — fica gravado:
 *
 *     adb shell am start -n br.com.saimo.tv/.EscolhaActivity --es saimo_vr sim
 *     adb shell am start -n br.com.saimo.tv/.EscolhaActivity --es saimo_vr auto
 */
object Vr {

    private const val PREFS = "vr"
    private const val MODO = "modo"
    private const val EXTRA = "saimo_vr"

    /** Recursos que só óculos declaram. */
    private val RECURSOS = listOf(
        "android.hardware.vr.headtracking",          // Quest, Pico
        "android.hardware.vr.high_performance",
        "oculus.software.handtracking",               // Horizon OS
        "oculus.software.vr_mode",
        "com.oculus.feature.PASSTHROUGH",
        "android.software.xr.api.spatial",            // Android XR (Galaxy XR…)
        "android.software.xr.api.openxr",
        "android.software.xr.immersive",
        "com.pico.feature.handtracking",
    )

    /** Fabricante/marca/modelo de óculos Android conhecidos. */
    private val NOMES = listOf(
        "oculus", "quest 2", "quest 3", "quest pro", "pico", "bytedance", "vive focus", "vive xr",
        "play for dream", "yvr", "pimax", "lynx", "galaxy xr",
    )

    @Volatile var ativo: Boolean = false
        private set

    /** Celular ou tablet (não óculos): o voltar do sistema já existe lá. */
    @Volatile var celular: Boolean = false
        private set

    /** Por que foi (ou não) considerado óculos — vai para o registro. */
    var motivo: String = ""
        private set

    fun iniciar(app: Application) {
        val modo = app.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(MODO, "auto")
        decidir(app, modo)
        app.registerActivityLifecycleCallbacks(Ciclo)
    }

    private fun decidir(contexto: Context, modo: String?) {
        when (modo) {
            "sim" -> { ativo = true; celular = false; motivo = "forçado ligado" }
            "nao" -> { ativo = false; celular = false; motivo = "forçado desligado" }
            else -> {
                val pm = contexto.packageManager
                val recurso = RECURSOS.firstOrNull { runCatching { pm.hasSystemFeature(it) }.getOrDefault(false) }
                val identidade = listOf(Build.MANUFACTURER, Build.BRAND, Build.MODEL, Build.PRODUCT, Build.DEVICE)
                    .joinToString(" ") { it.orEmpty() }.lowercase()
                val nome = NOMES.firstOrNull { identidade.contains(it) }
                val oculos = recurso != null || nome != null
                celular = !oculos && telaDeToque(contexto)
                ativo = oculos || celular
                motivo = recurso?.let { "recurso $it" } ?: nome?.let { "aparelho \"$it\"" }
                    ?: if (celular) "celular/tablet com tela de toque" else "TV Box"
            }
        }
        Log.i("SaimoTV", "modo VR: $ativo ($motivo) — ${Build.MANUFACTURER} ${Build.MODEL}")
    }

    /**
     * Celular ou tablet: tela de toque de verdade e nada de modo TV. TV Box
     * sempre cai fora por um dos dois lados — ou está em modo TV/leanback, ou
     * não tem painel de toque (mesmo declarando o recurso).
     */
    private fun telaDeToque(contexto: Context): Boolean {
        val pm = contexto.packageManager
        val modoTv = (contexto.resources.configuration.uiMode and
            android.content.res.Configuration.UI_MODE_TYPE_MASK) ==
            android.content.res.Configuration.UI_MODE_TYPE_TELEVISION
        val leanback = runCatching {
            pm.hasSystemFeature(PackageManager.FEATURE_LEANBACK) ||
                pm.hasSystemFeature("android.software.leanback_only")
        }.getOrDefault(false)
        val dedo = contexto.resources.configuration.touchscreen ==
            android.content.res.Configuration.TOUCHSCREEN_FINGER
        return !modoTv && !leanback && dedo
    }

    /** O extra `saimo_vr` (sim, nao, auto), vindo por adb, troca o modo e fica gravado. */
    private fun lerPedido(activity: Activity) {
        val pedido = activity.intent?.getStringExtra(EXTRA)?.lowercase() ?: return
        if (pedido !in setOf("sim", "nao", "auto")) return
        activity.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putString(MODO, pedido).apply()
        decidir(activity, pedido)
    }

    /**
     * Telas que já têm o próprio caminho de volta no VR: a inicial (o sistema
     * fecha a janela), o ao vivo e o player (o voltar mora na barra deles).
     */
    private val SEM_VOLTAR_FLUTUANTE = setOf(
        "EscolhaActivity", "MainActivity", "PlayerActivity",
    )

    private object Ciclo : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(a: Activity, b: Bundle?) = lerPedido(a)
        override fun onActivityStarted(a: Activity) {
            if (ativo && !celular && a.javaClass.simpleName !in SEM_VOLTAR_FLUTUANTE) botaoVoltarFlutuante(a)
        }
        override fun onActivityResumed(a: Activity) {}
        override fun onActivityPaused(a: Activity) {}
        override fun onActivityStopped(a: Activity) {}
        override fun onActivitySaveInstanceState(a: Activity, b: Bundle) {}
        override fun onActivityDestroyed(a: Activity) {}
    }

    // MARK: - Voltar

    /**
     * O BACK de verdade, como o controle manda: passa pelo `onKeyDown` de cada
     * tela, que é onde mora o "primeiro fecha o painel, depois sai".
     */
    fun voltar(activity: Activity) {
        val agora = android.os.SystemClock.uptimeMillis()
        activity.dispatchKeyEvent(KeyEvent(agora, agora, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK, 0))
        activity.dispatchKeyEvent(KeyEvent(agora, agora, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK, 0))
    }

    private const val TAG_VOLTAR = "vr-voltar"

    private fun botaoVoltarFlutuante(activity: Activity) {
        val raiz = activity.findViewById<ViewGroup>(android.R.id.content) ?: return
        if (raiz.findViewWithTag<View>(TAG_VOLTAR) != null) return
        val botao = botao(activity, activity.getString(R.string.vr_voltar), R.drawable.ic_arrow_back) { voltar(activity) }
        botao.tag = TAG_VOLTAR
        botao.elevation = dp(activity, 12).toFloat()
        raiz.addView(botao, FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
            Gravity.BOTTOM or Gravity.START).apply {
            setMargins(dp(activity, 20), 0, 0, dp(activity, 20))
        })
    }

    // MARK: - Peças

    /**
     * Troca o PlayerView da tela por um com TextureView (ver
     * res/layout/video_textura.xml), no mesmo lugar e com o mesmo id. Chamar
     * logo depois do setContentView, antes de qualquer findViewById dele.
     */
    fun videoEmTextura(activity: Activity, id: Int) {
        val velho = activity.findViewById<View>(id) ?: return
        val pai = velho.parent as? ViewGroup ?: return
        val posicao = pai.indexOfChild(velho)
        val novo = activity.layoutInflater.inflate(R.layout.video_textura, pai, false)
        novo.id = id
        novo.layoutParams = velho.layoutParams
        pai.removeViewAt(posicao)
        pai.addView(novo, posicao)
    }

    fun dp(contexto: Context, valor: Int): Int = (valor * contexto.resources.displayMetrics.density).toInt()

    /**
     * Um botão no estilo dos do player, com no mínimo 56 dp de altura: no
     * óculos o alvo precisa de uns 3 graus do campo de visão (48 dp) para o
     * olhar acertar sem esforço.
     */
    fun botao(contexto: Context, texto: String, icone: Int = 0, acao: () -> Unit): TextView =
        TextView(contexto).apply {
            text = texto
            textSize = 17f
            typeface = android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL)
            setTextColor(ContextCompat.getColorStateList(contexto, R.color.texto_botao_ficha))
            setBackgroundResource(R.drawable.botao_player)
            gravity = Gravity.CENTER
            minHeight = dp(contexto, 56)
            minWidth = dp(contexto, 56)
            maxLines = 1
            isFocusable = true
            isClickable = true
            setPadding(dp(contexto, 18), dp(contexto, 8), dp(contexto, 18), dp(contexto, 8))
            if (icone != 0) Icones.inicio(this, icone)
            contentDescription = texto
            setOnClickListener { acao() }
        }

    /** Fileira de botões com espaço entre eles, para pôr sobre o vídeo. */
    fun fileira(contexto: Context): LinearLayout = LinearLayout(contexto).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER
        setPadding(dp(contexto, 16), dp(contexto, 12), dp(contexto, 16), dp(contexto, 12))
    }

    fun adicionar(fileira: LinearLayout, botao: View) {
        fileira.addView(botao, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            marginEnd = dp(fileira.context, 10)
        })
    }

    /**
     * Uma camada transparente por cima do vídeo que recebe o toque. O
     * PlayerView sem controlador ignora toque, então sem ela pinçar sobre a
     * imagem não faria nada. É clicável de propósito: o sistema do óculos só
     * destaca e entrega o pinçar a elementos clicáveis.
     */
    fun camadaDeToque(pai: ViewGroup, posicao: Int, aoTocar: () -> Unit): View =
        View(pai.context).apply {
            isClickable = true
            contentDescription = pai.context.getString(R.string.vr_mostrar_controles)
            setOnClickListener { aoTocar() }
            pai.addView(this, posicao, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        }

    /**
     * Faz uma barra de progresso (que no TV Box só se move pelas setas) aceitar
     * arrastar. O toque é recebido em [area] — a linha inteira do tempo, bem
     * maior que a barra de 6 dp, que o olhar não acertaria — e a posição é
     * medida sobre a [barra]: [aoMover] a cada movimento (para mostrar o
     * tempo), [aoSoltar] no fim, que é quando se pula.
     */
    @SuppressLint("ClickableViewAccessibility")
    fun arrastavel(area: View, barra: View, aoMover: (Float) -> Unit, aoSoltar: (Float) -> Unit) {
        area.isClickable = true
        area.minimumHeight = dp(area.context, 56)
        area.setOnClickListener { }
        val local = IntArray(2)
        area.setOnTouchListener { v, e ->
            barra.getLocationOnScreen(local)
            val largura = (barra.width - barra.paddingLeft - barra.paddingRight).coerceAtLeast(1)
            val fracao = ((e.rawX - local[0] - barra.paddingLeft) / largura).coerceIn(0f, 1f)
            when (e.actionMasked) {
                MotionEvent.ACTION_DOWN -> { v.parent?.requestDisallowInterceptTouchEvent(true); aoMover(fracao); true }
                MotionEvent.ACTION_MOVE -> { aoMover(fracao); true }
                MotionEvent.ACTION_UP -> { aoSoltar(fracao); v.performClick(); true }
                MotionEvent.ACTION_CANCEL -> true
                else -> false
            }
        }
    }
}
