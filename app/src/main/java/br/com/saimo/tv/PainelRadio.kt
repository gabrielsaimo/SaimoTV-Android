package br.com.saimo.tv

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.RectF
import android.graphics.Shader
import android.os.Build
import android.os.SystemClock
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import coil3.dispose
import coil3.load
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sin

/**
 * O painel da rádio tocando (`painel_radio.xml`): cobre a tela preta que um
 * fluxo só de áudio deixaria. Quem toca é o MainActivity; aqui só se mostra.
 */
class PainelRadio(private val raiz: View, private val estaTocando: () -> Boolean) {
    private val fundo: ImageView = raiz.findViewById(R.id.radioFundo)
    private val logo: ImageView = raiz.findViewById(R.id.radioLogo)
    private val inicial: TextView = raiz.findViewById(R.id.radioInicial)
    private val nome: TextView = raiz.findViewById(R.id.radioNome)
    private val info: TextView = raiz.findViewById(R.id.radioInfo)
    private val equalizador: Equalizador = raiz.findViewById(R.id.radioEqualizador)
    private val pulso: View = raiz.findViewById(R.id.radioPulso)
    private var pulsando: ObjectAnimator? = null

    init {
        equalizador.estaTocando = estaTocando
        // O fundo é o próprio logo, bem grande e desfocado; sem desfoque (Android
        // antes do 12) ele fica só como uma mancha de cor, bem apagado.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            fundo.setRenderEffect(android.graphics.RenderEffect.createBlurEffect(90f, 90f, Shader.TileMode.CLAMP))
        } else {
            fundo.alpha = 0.12f
        }
    }

    fun mostrar(canal: Channel, posicao: Int, total: Int) {
        raiz.visibility = View.VISIBLE
        nome.text = canal.name
        val estilo = Radios.estiloDe(Radios.Radio(canal.name, "", null))?.let { raiz.context.getString(it.titulo) }
        info.text = listOfNotNull(estilo, raiz.context.getString(R.string.radio_posicao, posicao + 1, total))
            .joinToString("  ·  ")
        inicial.text = canal.name.firstOrNull { it.isLetterOrDigit() }?.uppercase().orEmpty()
        inicial.visibility = View.VISIBLE
        logo.dispose(); logo.setImageDrawable(null)
        fundo.dispose(); fundo.setImageDrawable(null)
        canal.logo?.let { endereco ->
            logo.load(endereco) { listener(onSuccess = { _, _ -> inicial.visibility = View.INVISIBLE }) }
            fundo.load(endereco)
        }
        if (pulsando == null) {
            pulsando = ObjectAnimator.ofFloat(pulso, View.ALPHA, 1f, 0.25f).apply {
                duration = 900
                repeatMode = ValueAnimator.REVERSE
                repeatCount = ValueAnimator.INFINITE
                start()
            }
        }
    }

    fun esconder() {
        raiz.visibility = View.GONE
        pulsando?.cancel(); pulsando = null
    }
}

/**
 * Manchas de cor (turquesa, roxo, rosa) andando devagar atrás da rádio.
 * Três gradientes por quadro, a uns 20 por segundo: leve até em TV Box antigo.
 */
class Aurora @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val cores = intArrayOf(0x804FE3D0.toInt(), 0x807A5CFF.toInt(), 0x66FF5C9E)
    private val tinta = Paint(Paint.ANTI_ALIAS_FLAG)
    private val inicio = SystemClock.uptimeMillis()

    override fun onDraw(canvas: Canvas) {
        val t = (SystemClock.uptimeMillis() - inicio) / 1000.0
        val w = width.toFloat(); val h = height.toFloat()
        for ((i, cor) in cores.withIndex()) {
            val fase = t / (14.0 + i * 5) * 2 * PI + i * 2.1
            val x = w * (0.5f + 0.38f * sin(fase).toFloat())
            val y = h * (0.45f + 0.3f * sin(fase * 0.7 + i).toFloat())
            val raio = maxOf(w, h) * (0.45f + 0.08f * i)
            tinta.shader = RadialGradient(x, y, raio, cor, cor and 0x00FFFFFF, Shader.TileMode.CLAMP)
            canvas.drawCircle(x, y, raio, tinta)
        }
        if (isShown) postInvalidateDelayed(50)
    }
}

/**
 * Barras de equalizador. Não é o som de verdade (ler o áudio pediria a
 * permissão do microfone): é um movimento que segue quando a rádio toca e
 * desce até parar quando ela carrega ou trava — o que já diz se está no ar.
 */
class Equalizador @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    var estaTocando: () -> Boolean = { false }
    private val barras = 28
    private val alturas = FloatArray(barras) { 0.08f }
    private val tinta = Paint(Paint.ANTI_ALIAS_FLAG)
    private val caixa = RectF()
    private val inicio = SystemClock.uptimeMillis()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        tinta.shader = LinearGradient(0f, h.toFloat(), 0f, 0f, 0xFF4FE3D0.toInt(), 0xFF9A7BFF.toInt(), Shader.TileMode.CLAMP)
    }

    override fun onDraw(canvas: Canvas) {
        val t = (SystemClock.uptimeMillis() - inicio) / 1000.0
        val tocando = estaTocando()
        val passo = width.toFloat() / barras
        val largura = passo * 0.55f
        for (i in 0 until barras) {
            // Soma de ondas com fases diferentes: cada barra dança de um jeito.
            val alvo = if (tocando) {
                (0.25 + 0.75 * abs(sin(t * (2.1 + i % 5 * 0.37) + i * 0.9) * sin(t * 0.9 + i * 0.31))).toFloat()
            } else 0.08f
            alturas[i] += (alvo - alturas[i]) * 0.25f
            val altura = height * alturas[i]
            val x = i * passo + (passo - largura) / 2
            caixa.set(x, height - altura, x + largura, height.toFloat())
            canvas.drawRoundRect(caixa, largura / 2, largura / 2, tinta)
        }
        if (isShown) postInvalidateDelayed(40)
    }
}
