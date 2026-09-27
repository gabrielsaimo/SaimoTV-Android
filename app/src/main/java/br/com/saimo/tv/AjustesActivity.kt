package br.com.saimo.tv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Ajustes: cada linha mostra o valor atual e OK troca.
 *
 * Tudo aqui tem um padrão bom — ninguém precisa passar por esta tela para o
 * app funcionar bem. Ela existe para quem quer legenda maior, prefere
 * legendado, quer abrir direto no último canal, ou precisa do número do
 * aparelho para pedir ajuda.
 */
@UnstableApi
class AjustesActivity : TelaComMenu() {

    override val aba = Aba.AJUSTES


    companion object {
        fun abrir(context: Context) = context.startActivity(Intent(context, AjustesActivity::class.java))
    }

    private sealed interface Linha
    private data class Secao(val nome: String) : Linha
    private data class Opcao(val nome: String, val valor: () -> String, val acao: () -> Unit) : Linha

    private lateinit var lista: RecyclerView
    private val adaptador = Adaptador()
    private val atualizacao by lazy { OfertaDeAtualizacao(this) { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajustes)
        findViewById<TextView>(R.id.ajustesSobre).text = getString(R.string.ajustes_sobre,
            BuildConfig.VERSION_NAME, Preferencias.idCurto(this))
        lista = findViewById(R.id.ajustesLista)
        lista.layoutManager = LinearLayoutManager(this)
        lista.adapter = adaptador
        adaptador.linhas = linhas()
        focarQuandoPronto { lista.findViewHolderForAdapterPosition(1)?.itemView }
    }

    private fun simNao(v: Boolean) = getString(if (v) R.string.ajustes_ligado else R.string.ajustes_desligado)

    private fun linhas(): List<Linha> = listOf(
        Secao(getString(R.string.ajustes_reproducao)),
        Opcao(getString(R.string.ajustes_versao), { Titulos.rotulo(Preferencias.versao) }) {
            escolher(getString(R.string.ajustes_versao), listOf("dub", "leg"), Preferencias.versao,
                { Titulos.rotulo(it) }) { Preferencias.versao = it }
        },
        Opcao(getString(R.string.ajustes_proximo), { simNao(Preferencias.proximoAutomatico) }) {
            Preferencias.proximoAutomatico = !Preferencias.proximoAutomatico; redesenhar()
        },
        Opcao(getString(R.string.ajustes_pular), { simNao(Preferencias.pularAutomatico) }) {
            Preferencias.pularAutomatico = !Preferencias.pularAutomatico; redesenhar()
        },
        Opcao(getString(R.string.player_tamanho_legenda),
            { resources.getStringArray(R.array.tamanhos_legenda)[Preferencias.legenda.coerceIn(0, 3)] }) {
            val nomes = resources.getStringArray(R.array.tamanhos_legenda)
            escolher(getString(R.string.player_tamanho_legenda), nomes.indices.toList(), Preferencias.legenda,
                { nomes[it] }) { Preferencias.legenda = it }
        },
        Opcao(getString(R.string.ajustes_legenda_fundo), { simNao(Preferencias.legendaFundo) }) {
            Preferencias.legendaFundo = !Preferencias.legendaFundo; redesenhar()
        },
        Opcao(getString(R.string.ajustes_economia), { simNao(Preferencias.economia) }) {
            Preferencias.economia = !Preferencias.economia; redesenhar()
        },

        Secao(getString(R.string.ajustes_geral)),
        Opcao(getString(R.string.ajustes_abrir_em), {
            getString(if (Preferencias.abrirEm == "canal") R.string.ajustes_abrir_canal else R.string.ajustes_abrir_inicio)
        }) {
            escolher(getString(R.string.ajustes_abrir_em), listOf("inicio", "canal"), Preferencias.abrirEm,
                { getString(if (it == "canal") R.string.ajustes_abrir_canal else R.string.ajustes_abrir_inicio) }) {
                Preferencias.abrirEm = it
            }
        },
        Opcao(getString(R.string.ajustes_timer), {
            if (TimerDeSono.ativo) getString(R.string.ajustes_timer_em, TimerDeSono.restanteMin())
            else getString(R.string.ajustes_desligado)
        }) {
            escolher(getString(R.string.ajustes_timer), listOf(0, 30, 60, 90, 120, 180), -1,
                { if (it == 0) getString(R.string.ajustes_desligado) else getString(R.string.ajustes_minutos, it) }) {
                TimerDeSono.ligar(it)
            }
        },

        Secao(getString(R.string.ajustes_sistema)),
        Opcao(getString(R.string.ajustes_atualizar), { "v${BuildConfig.VERSION_NAME}" }) { procurarVersao() },
        Opcao(getString(R.string.ajustes_recarregar), { getString(R.string.ajustes_recarregar_dica) }) { recarregar() },
        Opcao(getString(R.string.ajustes_telemetria), { simNao(Preferencias.telemetria) }) {
            Preferencias.telemetria = !Preferencias.telemetria; redesenhar()
        },
    )

    private fun <T> escolher(titulo: String, valores: List<T>, atual: T, nome: (T) -> String, aoEscolher: (T) -> Unit) {
        Painel.mostrar(this, titulo, valores.map { v ->
            Painel.Item(nome(v), marcado = v == atual) { aoEscolher(v); redesenhar() }
        })
    }

    private fun redesenhar() = adaptador.notifyDataSetChanged()

    private fun procurarVersao() {
        lifecycleScope.launch(semDerrubar) {
            val versao = Atualizacao.procurar(this@AjustesActivity, manual = true)
            if (versao == null) {
                Toast.makeText(this@AjustesActivity, R.string.ajustes_em_dia, Toast.LENGTH_LONG).show()
            } else {
                atualizacao.ofertar()
            }
        }
    }

    /** Apaga o catálogo e as capas guardadas: a próxima abertura baixa tudo de novo. */
    private fun recarregar() {
        lifecycleScope.launch(semDerrubar) {
            withContext(Dispatchers.IO) {
                File(filesDir, "vod").deleteRecursively()
                runCatching { coil.Coil.imageLoader(this@AjustesActivity).diskCache?.clear() }
                Remote.refresh(this@AjustesActivity)
            }
            runCatching { coil.Coil.imageLoader(this@AjustesActivity).memoryCache?.clear() }
            Toast.makeText(this@AjustesActivity, R.string.ajustes_recarregado, Toast.LENGTH_LONG).show()
        }
    }

    private inner class Adaptador : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var linhas: List<Linha> = emptyList()
            set(v) { field = v; notifyDataSetChanged() }

        override fun getItemViewType(position: Int) = if (linhas[position] is Secao) 0 else 1

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val layout = if (viewType == 0) R.layout.item_ajuste_secao else R.layout.item_painel
            val vista = LayoutInflater.from(parent.context).inflate(layout, parent, false)
            return object : RecyclerView.ViewHolder(vista) {}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (val linha = linhas[position]) {
                is Secao -> (holder.itemView as TextView).text = linha.nome
                is Opcao -> {
                    holder.itemView.findViewById<TextView>(R.id.itemTexto).text = linha.nome
                    holder.itemView.findViewById<TextView>(R.id.itemDetalhe).apply {
                        text = linha.valor(); visibility = View.VISIBLE
                    }
                    holder.itemView.setOnClickListener { linha.acao() }
                }
            }
        }

        override fun getItemCount() = linhas.size
    }
}
