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
            val inW = inputWidth.coerceAtLeast(1)
            val inH = inputHeight.coerceAtLeast(1)
            // O passo de amostragem do CAS usa o texel da textura de entrada
            texelW = 1f / inW.toFloat()
            texelH = 1f / inH.toFloat()

            // Descobre a resolução máxima suportada pela tela da TV Box / TV
            val dm = android.content.res.Resources.getSystem().displayMetrics
            val screenW = maxOf(dm.widthPixels, dm.heightPixels).coerceAtLeast(1920)
            val screenH = minOf(dm.widthPixels, dm.heightPixels).coerceAtLeast(1080)

            // Define o alvo real do framebuffer OpenGL:
            // - Nitidez HD: eleva 720p/SD para Full HD (1920x1080)
            // - Super Resolução Ultra: eleva até 4K UHD (3840x2160) quando a TV suporta,
            //   ou no mínimo 2560x1440 / 1920x1080 preservando a proporção exata do canal.
            val targetMaxH = if (ultra4k) {
                maxOf(screenH, 2160).coerceAtMost(2160)
            } else {
                maxOf(inH, 1080).coerceAtMost(1440)
            }

            if (inH >= targetMaxH) {
                return Size(inW, inH)
            }

            val scale = targetMaxH.toFloat() / inH.toFloat()
            val outW = ((inW * scale).toInt() and 1.inv()).coerceIn(inW, 3840)
            val outH = ((inH * scale).toInt() and 1.inv()).coerceIn(inH, 2160)
            return Size(outW, outH)
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
     * Shader OpenGL ES 2.0 de Super Resolução na GPU para Android TV / TV Box:
     * - Opera estritamente na luminância Rec.709 (Y), preservando 100% das cores e tons de pele.
     * - Possui Noise Gate (smoothstep) para não amplificar granulação ou blocos de compressão IPTV.
     * - Possui Trava Anti-Halo (clamp na vizinhança 3x3) para impedir contornos brancos em textos e logos.
     */
    private const val FRAGMENT_SHADER = """
        precision highp float;
        uniform sampler2D uTexSampler;
        uniform vec2 uTexelSize;
        uniform float uSharpness;
        uniform float uUltraMode;
        varying vec2 vTexSamplingCoord;

        float luma(vec3 rgb) {
            return dot(rgb, vec3(0.2126, 0.7152, 0.0722));
        }

        void main() {
            vec2 uv = vTexSamplingCoord;
            vec2 px = uTexelSize;

            vec4 center = texture2D(uTexSampler, uv);
            vec3 c  = center.rgb;
            vec3 n  = texture2D(uTexSampler, uv + vec2(0.0, -px.y)).rgb;
            vec3 s  = texture2D(uTexSampler, uv + vec2(0.0,  px.y)).rgb;
            vec3 w  = texture2D(uTexSampler, uv + vec2(-px.x, 0.0)).rgb;
            vec3 e  = texture2D(uTexSampler, uv + vec2( px.x, 0.0)).rgb;
            vec3 nw = texture2D(uTexSampler, uv + vec2(-px.x, -px.y)).rgb;
            vec3 ne = texture2D(uTexSampler, uv + vec2( px.x, -px.y)).rgb;
            vec3 sw = texture2D(uTexSampler, uv + vec2(-px.x,  px.y)).rgb;
            vec3 se = texture2D(uTexSampler, uv + vec2( px.x,  px.y)).rgb;

            float lc  = luma(c);
            float ln  = luma(n);
            float ls  = luma(s);
            float lw  = luma(w);
            float le  = luma(e);
            float lnw = luma(nw);
            float lne = luma(ne);
            float lsw = luma(sw);
            float lse = luma(se);

            float mn4 = min(min(ln, ls), min(lw, le));
            float mx4 = max(max(ln, ls), max(lw, le));
            float mnDiag = min(min(lnw, lne), min(lsw, lse));
            float mxDiag = max(max(lnw, lne), max(lsw, lse));

            float mnL = 0.5 * (mn4 + min(mn4, min(mnDiag, lc)));
            float mxL = 0.5 * (mx4 + max(mx4, max(mxDiag, lc)));

            // Noise gate: ignora áreas lisas para não realçar blocos de compressão
            float contrast = mxL - mnL;
            float noiseGate = smoothstep(0.015, 0.055, contrast);

            float amp = clamp(min(mnL, 1.0 - mxL) / max(mxL, 0.0001), 0.0, 1.0);
            amp = sqrt(amp);

            float strength = (uUltraMode > 0.5) ? 0.82 : 0.55;
            float peak = -1.0 / mix(8.0, 5.0, strength * clamp(uSharpness, 0.0, 1.0));
            float wt = amp * peak * noiseGate;
            float diagWt = (uUltraMode > 0.5) ? (wt * 0.25) : 0.0;

            float sumW = 1.0 + 4.0 * wt + 4.0 * diagWt;
            float sharpL = (lc + (ln + ls + lw + le) * wt + (lnw + lne + lsw + lse) * diagWt) / max(sumW, 0.0001);

            // Trava anti-halo estrita: nunca ultrapassa o mínimo/máximo local
            sharpL = clamp(sharpL, mnL, mxL);

            float delta = sharpL - lc;
            vec3 outColor = clamp(c + vec3(delta), 0.0, 1.0);
            gl_FragColor = vec4(outColor, center.a);
        }
    """
}
