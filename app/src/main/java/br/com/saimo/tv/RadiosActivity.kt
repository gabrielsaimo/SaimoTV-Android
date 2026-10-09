package br.com.saimo.tv

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

/**
 * As rádios, no formato da tela inicial: o destaque em cima mostra a rádio em
 * foco, e embaixo as fileiras — as ouvidas por último, uma por estilo (rock,
 * notícias, gospel...) e todas de A a Z, uma fileira por letra, para chegar a
 * qualquer uma com poucas setas.
 *
 * O estilo sai do nome da rádio: a lista publicada só tem nome, endereço e logo.
 */
@UnstableApi
class RadiosActivity : TelaComMenu() {
    override val aba = Aba.RADIOS

    private lateinit var filas: RecyclerView
    private lateinit var destaque: Destaque
    private val adaptador by lazy { Inicio.Adapter(escopo = lifecycleScope, aoFocar = { destaque.mostrar(it) }) }
    private var radios: List<Radios.Radio> = emptyList()
    private var montou = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagina)
        filas = findViewById(R.id.inicioFilas)
        filas.layoutManager = Inicio.Filas(this)
        filas.adapter = adaptador
        destaque = Destaque(this, lifecycleScope, findViewById<ImageView>(R.id.inicioFundo),
            findViewById(R.id.inicioTitulo), findViewById(R.id.inicioMeta), findViewById(R.id.inicioSinopse))
        findViewById<TextView>(R.id.paginaNome).visibility = View.GONE
        destaque.padrao(getString(R.string.menu_radios), getString(R.string.vod_carregando))

        // Na hora, o que já está no aparelho; a lista nova chega por trás.
        mostrar(Radios.locais(this))
        lifecycleScope.launch(semDerrubar) {
            val nova = Radios.baixar(this@RadiosActivity) ?: return@launch
            if (nova != radios) mostrar(nova)
        }
    }

    override fun onRestart() {
        super.onRestart()
        // A fileira de ouvidas por último muda depois de tocar uma.
        mostrar(radios)
    }

    private fun mostrar(lista: List<Radios.Radio>) {
        if (isFinishing || isDestroyed) return
        radios = lista
        destaque.padrao(getString(R.string.menu_radios),
            resources.getQuantityString(R.plurals.radios_ao_vivo, lista.size, lista.size))
        val porNome = lista.associateBy { it.nome }
        val filasNovas = mutableListOf<Inicio.Fila>()

        Radios.recentes(this).mapNotNull { porNome[it] }.takeIf { it.isNotEmpty() }?.let {
            filasNovas += Inicio.Fila(getString(R.string.radios_recentes), it.map(::cartao), Inicio.Tipo.LARGO)
        }
        val estilos = lista.groupBy { Radios.estiloDe(it) }
        for (estilo in Radios.ESTILOS) {
            val doEstilo = estilos[estilo].orEmpty()
            if (doEstilo.size >= 4) filasNovas += Inicio.Fila(getString(estilo.titulo), doEstilo.map(::cartao), Inicio.Tipo.LARGO)
        }
        // De A a Z: números e símbolos juntos numa fileira só, antes do A.
        lista.groupBy { r -> Radios.chave(r.nome).firstOrNull()?.takeIf { it in 'a'..'z' }?.uppercaseChar() ?: '#' }
            .toSortedMap()
            .forEach { (letra, daLetra) ->
                val titulo = if (letra == '#') getString(R.string.radios_numeros) else letra.toString()
                filasNovas += Inicio.Fila(titulo, daLetra.map(::cartao), Inicio.Tipo.LARGO)
            }

        if (filasNovas.isEmpty()) {
            destaque.padrao(getString(R.string.menu_radios), getString(R.string.radios_vazio))
        }
        adaptador.trocar(filasNovas)
        if (!montou && filasNovas.isNotEmpty()) {
            montou = true
            focarQuandoPronto {
                filas.findViewHolderForAdapterPosition(0)?.itemView
                    ?.findViewById<RecyclerView>(R.id.filaCapas)?.getChildAt(0)
            }
        }
    }

    private fun cartao(r: Radios.Radio): Inicio.Cartao {
        val estilo = Radios.estiloDe(r)?.let { getString(it.titulo) }
        return Inicio.Cartao(
            titulo = r.nome,
            capa = r.logo.orEmpty(),
            inicial = r.nome.firstOrNull { it.isLetterOrDigit() }?.uppercase() ?: "R",
            subtitulo = estilo,
            meta = listOfNotNull(getString(R.string.radios_no_ar), estilo).joinToString("  ·  "),
            sinopse = getString(R.string.radios_dica),
        ) { tocar(r) }
    }

    private fun tocar(r: Radios.Radio) {
        Radios.ouviu(this, r.nome)
        startActivity(Intent(this, MainActivity::class.java)
            .putExtra(MainActivity.EXTRA_RADIO_NOME, r.nome)
            .putExtra(MainActivity.EXTRA_RADIO_URL, r.url))
    }
}
