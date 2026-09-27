package br.com.saimo.tv

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat

/**
 * Ícones Material ao lado do texto, na cor do texto.
 *
 * Emoji muda de desenho de aparelho para aparelho — num TV Box antigo vira
 * quadrado vazio — e não acompanha a cor do botão focado. O vetor é o mesmo em
 * todo lugar e troca de branco para escuro junto com o texto.
 */
object Icones {

    private fun desenho(context: Context, @DrawableRes id: Int, px: Int, cor: ColorStateList?): Drawable? {
        val d = AppCompatResources.getDrawable(context, id)?.mutate() ?: return null
        d.setBounds(0, 0, px, px)
        return DrawableCompat.wrap(d).also { w ->
            if (cor != null) DrawableCompat.setTintList(w, cor)
            w.setBounds(0, 0, px, px)
        }
    }

    /** Ícone antes do texto. `0` tira o ícone. */
    fun inicio(tv: TextView, @DrawableRes id: Int, escala: Float = 1.15f) {
        if (id == 0) { tv.setCompoundDrawablesRelative(null, null, null, null); return }
        val px = (tv.textSize * escala).toInt()
        tv.setCompoundDrawablesRelative(desenho(tv.context, id, px, tv.textColors), null, null, null)
        tv.compoundDrawablePadding = (px * 0.45f).toInt()
    }

    /** Ícone depois do texto (a setinha de "escolher"). */
    fun fim(tv: TextView, @DrawableRes id: Int, escala: Float = 1.1f) {
        val px = (tv.textSize * escala).toInt()
        tv.setCompoundDrawablesRelative(null, null, desenho(tv.context, id, px, tv.textColors), null)
        tv.compoundDrawablePadding = (px * 0.3f).toInt()
    }

    /** Só o ícone, sem texto — tecla do teclado, aba de ajustes. */
    fun so(tv: TextView, @DrawableRes id: Int, escala: Float = 1.4f) {
        tv.text = ""
        val px = (tv.textSize * escala).toInt()
        tv.setCompoundDrawablesRelative(desenho(tv.context, id, px, tv.textColors), null, null, null)
        tv.compoundDrawablePadding = 0
        tv.minWidth = px + tv.paddingStart + tv.paddingEnd
        tv.gravity = android.view.Gravity.CENTER
    }

    /** Texto com um ícone embutido no começo — para linhas como "★ 8.2 · 2019". */
    fun emLinha(context: Context, @DrawableRes id: Int, texto: CharSequence, px: Int, cor: Int): CharSequence {
        val d = desenho(context, id, px, ColorStateList.valueOf(cor)) ?: return texto
        return SpannableStringBuilder("  ").append(texto).apply {
            setSpan(ImageSpan(d, ImageSpan.ALIGN_BASELINE), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }
}
