package br.com.saimo.tv

import android.content.Context
import android.opengl.GLES20
import androidx.media3.common.Effect
import androidx.media3.common.util.GlProgram
import androidx.media3.common.util.GlUtil
import androidx.media3.common.util.Size
import androidx.media3.common.util.UnstableApi
import androidx.media3.effect.BaseGlShaderProgram
import androidx.media3.effect.GlEffect
import androidx.media3.exoplayer.ExoPlayer

/**
 * Super Resolução (Upscaling) em tempo real via GPU (OpenGL ES 2.0).
 *
 * Implementa reconstrução adaptativa de bordas e nitidez por contraste local
 * (inspirado no AMD FidelityFX CAS — Contrast Adaptive Sharpening), elevando
 * a percepção de definição de transmissões HD (720p) para Full HD e de
 * Full HD (1080p) para 4K sem criar halos em bordas já fortes.
 *
 * Por padrão vem desligado ("off") para não alterar o pipeline original em
 * aparelhos de entrada; quando ativado em Ajustes, roda 100% na GPU.
 */
@UnstableApi
object Upscaling {

    const val DESLIGADO = "off"
    const val NITIDEZ_HD = "hd"
    const val SUPER_4K = "4k"

    val modos = listOf(DESLIGADO, NITIDEZ_HD, SUPER_4K)

    fun ativo(): Boolean = Preferencias.upscaling != DESLIGADO

    fun sufixoResolucao(): String = when (Preferencias.upscaling) {
        NITIDEZ_HD -> " · Upscale FHD"
        SUPER_4K -> " · Upscale 4K"
        else -> ""
    }

    private val aplicado = java.util.WeakHashMap<ExoPlayer, String>()

    /** Aplica (ou remove) o shader de Super Resolução no ExoPlayer quando o modo muda. */
    fun aplicar(player: ExoPlayer) {
        runCatching {
            val modo = Preferencias.upscaling
            val anterior = aplicado[player] ?: DESLIGADO
            if (modo == anterior) return
            aplicado[player] = modo
            val efeitos: List<Effect> = when (modo) {
                NITIDEZ_HD -> listOf(CasGlEffect(intensidade = 0.65f, ultra4k = false))
                SUPER_4K -> listOf(CasGlEffect(intensidade = 1.0f, ultra4k = true))
                else -> emptyList()
            }
            player.setVideoEffects(efeitos)
        }
    }

    private class CasGlEffect(
        private val intensidade: Float,
        private val ultra4k: Boolean
    ) : GlEffect {
        override fun toGlShaderProgram(context: Context, useHdr: Boolean): BaseGlShaderProgram {
            return CasShaderProgram(useHdr, intensidade, ultra4k)
        }
    }

    private class CasShaderProgram(
        useHdr: Boolean,
        private val intensidade: Float,
        private val ultra4k: Boolean
    ) : BaseGlShaderProgram(useHdr, /* texturePoolCapacity = */ 1) {

        private val glProgram: GlProgram = GlProgram(VERTEX_SHADER, FRAGMENT_SHADER).apply {
            setBufferAttribute(
                "aFramePosition",
                GlUtil.getNormalizedCoordinateBounds(),
                GlUtil.HOMOGENEOUS_COORDINATE_VECTOR_SIZE
            )
            setFloatsUniform("uTransformationMatrix", GlUtil.create4x4IdentityMatrix())
            setFloatsUniform("uTexTransformationMatrix", GlUtil.create4x4IdentityMatrix())
        }

        private var texelW = 1f / 1920f
        private var texelH = 1f / 1080f

        override fun configure(inputWidth: Int, inputHeight: Int): Size {
            val w = inputWidth.coerceAtLeast(1)
            val h = inputHeight.coerceAtLeast(1)
            texelW = 1f / w.toFloat()
            texelH = 1f / h.toFloat()
            return Size(w, h)
        }

        override fun drawFrame(inputTexId: Int, presentationTimeUs: Long) {
            glProgram.use()
            glProgram.setSamplerTexIdUniform("uTexSampler", inputTexId, /* texUnitIndex = */ 0)
            glProgram.setFloatsUniform("uTexelSize", floatArrayOf(texelW, texelH))
            glProgram.setFloatUniform("uSharpness", intensidade)
            glProgram.setFloatUniform("uUltraMode", if (ultra4k) 1.0f else 0.0f)
            glProgram.bindAttributesAndUniforms()
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
            GlUtil.checkGlError()
        }

        override fun release() {
            super.release()
            runCatching { glProgram.delete() }
        }
    }

    private const val VERTEX_SHADER = """
        attribute vec4 aFramePosition;
        uniform mat4 uTransformationMatrix;
        uniform mat4 uTexTransformationMatrix;
        varying vec2 vTexSamplingCoord;
        void main() {
            gl_Position = uTransformationMatrix * aFramePosition;
            vec4 texPos = vec4(aFramePosition.x * 0.5 + 0.5, aFramePosition.y * 0.5 + 0.5, 0.0, 1.0);
            vTexSamplingCoord = (uTexTransformationMatrix * texPos).xy;
        }
    """

    /**
     * FidelityFX CAS (Contrast Adaptive Sharpening) + reconstrução diagonal 9-tap.
     */
    private const val FRAGMENT_SHADER = """
        precision highp float;
        uniform sampler2D uTexSampler;
        uniform vec2 uTexelSize;
        uniform float uSharpness;
        uniform float uUltraMode;
        varying vec2 vTexSamplingCoord;

        void main() {
            vec2 uv = vTexSamplingCoord;
            vec2 px = uTexelSize;

            vec3 c  = texture2D(uTexSampler, uv).rgb;
            vec3 n  = texture2D(uTexSampler, uv + vec2(0.0, -px.y)).rgb;
            vec3 s  = texture2D(uTexSampler, uv + vec2(0.0,  px.y)).rgb;
            vec3 w  = texture2D(uTexSampler, uv + vec2(-px.x, 0.0)).rgb;
            vec3 e  = texture2D(uTexSampler, uv + vec2( px.x, 0.0)).rgb;

            vec3 mnRGB = min(min(min(n, e), min(s, w)), c);
            vec3 mxRGB = max(max(max(n, e), max(s, w)), c);

            if (uUltraMode > 0.5) {
                vec3 nw = texture2D(uTexSampler, uv + vec2(-px.x, -px.y)).rgb;
                vec3 ne = texture2D(uTexSampler, uv + vec2( px.x, -px.y)).rgb;
                vec3 sw = texture2D(uTexSampler, uv + vec2(-px.x,  px.y)).rgb;
                vec3 se = texture2D(uTexSampler, uv + vec2( px.x,  px.y)).rgb;
                vec3 mnDiag = min(min(nw, ne), min(sw, se));
                vec3 mxDiag = max(max(nw, ne), max(sw, se));
                mnRGB = 0.5 * (mnRGB + min(mnRGB, mnDiag));
                mxRGB = 0.5 * (mxRGB + max(mxRGB, mxDiag));
            }

            vec3 rcpMx = 1.0 / max(mxRGB, vec3(0.0001));
            vec3 amp = clamp(min(mnRGB, 1.0 - mxRGB) * rcpMx, 0.0, 1.0);
            amp = sqrt(amp);

            float peak = -1.0 / mix(8.0, 4.4, clamp(uSharpness, 0.0, 1.0));
            vec3 wRGB = amp * peak;
            vec3 rcpWeight = 1.0 / (1.0 + 4.0 * wRGB);

            vec3 outColor = clamp(((n + s + w + e) * wRGB + c) * rcpWeight, 0.0, 1.0);

            if (uUltraMode > 0.5) {
                // Micro-contraste sutil para realçar texturas em telas 4K
                outColor = clamp((outColor - 0.5) * 1.035 + 0.5, 0.0, 1.0);
            }

            gl_FragColor = vec4(outColor, 1.0);
        }
    """
}
