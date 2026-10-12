package br.com.saimo.tv

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Matrix
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
import kotlin.math.cos
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

    init {
        equalizador.estaTocando = estaTocando
        // O ponto "no ar" pulsa no mesmo quadro do equalizador. Era um
        // ObjectAnimator à parte, que redesenhava a 60 qps — o que somado às
        // manchas e às barras pesava 33% de CPU para uma tela só de áudio.
        equalizador.aCadaQuadro = { t ->
            pulso.alpha = (0.625 + 0.375 * cos(t * 2 * PI / 1.8)).toFloat()
        }
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
    }

    fun esconder() {
        raiz.visibility = View.GONE
    }
}

/**
 * Manchas de cor (turquesa, roxo, rosa) andando devagar atrás da rádio, a uns
 * 20 quadros por segundo. Cada mancha é um gradiente criado uma vez (no
 * tamanho da tela) e só deslocado por matriz: antes eram três gradientes
 * novos por quadro, lixo que o coletor tinha de recolher sem parar.
 */
class Aurora @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val cores = intArrayOf(0x804FE3D0.toInt(), 0x807A5CFF.toInt(), 0x66FF5C9E)
    private val tintas = Array(cores.size) { Paint(Paint.ANTI_ALIAS_FLAG) }
    private val raios = FloatArray(cores.size)
    private val matriz = Matrix()
    private val inicio = SystemClock.uptimeMillis()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        for ((i, cor) in cores.withIndex()) {
            raios[i] = maxOf(w, h) * (0.45f + 0.08f * i)
            tintas[i].shader = RadialGradient(0f, 0f, raios[i].coerceAtLeast(1f), cor, cor and 0x00FFFFFF, Shader.TileMode.CLAMP)
        }
    }

    override fun onDraw(canvas: Canvas) {
        val t = (SystemClock.uptimeMillis() - inicio) / 1000.0
        val w = width.toFloat(); val h = height.toFloat()
        for (i in cores.indices) {
            val fase = t / (14.0 + i * 5) * 2 * PI + i * 2.1
            val x = w * (0.5f + 0.38f * sin(fase).toFloat())
            val y = h * (0.45f + 0.3f * sin(fase * 0.7 + i).toFloat())
            matriz.setTranslate(x, y)
            tintas[i].shader?.setLocalMatrix(matriz)
            canvas.drawCircle(x, y, raios[i], tintas[i])
        }
        if (isShown) postInvalidateDelayed(QUADRO_MS)
    }
}

/// Um quadro a cada 50 ms (20 por segundo) para as manchas, as barras e o pulso.
private const val QUADRO_MS = 50L

/**
 * Barras de equalizador. Não é o som de verdade (ler o áudio pediria a
 * permissão do microfone): é um movimento que segue quando a rádio toca e
 * desce até parar quando ela carrega ou trava — o que já diz se está no ar.
 */
class Equalizador @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    var estaTocando: () -> Boolean = { false }
    /// Chamado a cada quadro com o tempo em segundos: é o que faz o ponto "no ar" pulsar.
    var aCadaQuadro: (Double) -> Unit = {}
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
        aCadaQuadro(t)
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
        if (isShown) postInvalidateDelayed(QUADRO_MS)
    }
}
