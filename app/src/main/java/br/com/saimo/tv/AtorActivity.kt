package br.com.saimo.tv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil3.dispose
import coil3.load
import coil3.transform.CircleCropTransformation
import coil3.request.crossfade
import coil3.request.transformations
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Uma pessoa e o que ela tem neste acervo, em capas.
 *
 * A primeira versão era uma lista de nomes. Aqui é o que se espera de uma TV:
 * a foto grande, quem ela é, e à direita as capas de tudo que dá para assistir
 * — tocar numa capa abre a ficha daquele título, e dali se assiste.
 *
 * O cruzamento com o acervo é pelo id do TMDB: nome igual não engana, e
 * refilmagem não vira o original.
 */
@androidx.media3.common.util.UnstableApi
class AtorActivity : TelaComMenu() {

    private val capas = CapasAdapter { achado ->
        FichaActivity.abrir(this, Alvo(achado.titulo, achado.serie, achado.letra, achado.ano))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ator)

        val id = intent.getIntExtra(ID, 0)
        val nome = intent.getStringExtra(NOME).orEmpty()
        val fotoPequena = intent.getStringExtra(FOTO)

        findViewById<TextView>(R.id.atorGrandeNome).text = nome
        findViewById<TextView>(R.id.atorGrandeInicial).text = nome.take(1).uppercase()
        // A foto pequena do elenco já está no cache: aparece na hora e a
        // grande substitui quando chegar.
        fotoPequena?.let { mostrarFoto(it) }

        val grade = findViewById<RecyclerView>(R.id.atorGrade)
        // Quantas colunas couberem, cada uma com pelo menos 128dp: numa TV
        // Box de 960dp de largura são sete capas lado a lado.
        val densidade = resources.displayMetrics.density
        val larguraUtil = resources.displayMetrics.widthPixels - (96 * densidade).toInt()
        val colunas = (larguraUtil / (128 * densidade)).toInt().coerceIn(3, 9)
        val larguraDaColuna = larguraUtil / colunas
        capas.alturaDaCapa = ((larguraDaColuna - 12 * densidade) * 1.5f).toInt()
        grade.layoutManager = GridLayoutManager(this, colunas)
        grade.adapter = capas
        grade.setHasFixedSize(true)
        grade.setItemViewCacheSize(colunas * 2)

        val estado = findViewById<TextView>(R.id.atorEstado)
        estado.text = getString(R.string.vod_ator_procurando)

        lifecycleScope.launch(semDerrubar) {
            val perfil = async { Detalhes.perfil(id) }
            val titulos = async {
                Generos.carregar(this@AtorActivity)
                Detalhes.acervoDe(this@AtorActivity, id)
            }

            launch { perfil.await()?.let { if (!isFinishing) mostrarPerfil(it) } }

            val achados = titulos.await()
            if (isFinishing || isDestroyed) return@launch
            findViewById<TextView>(R.id.atorContagem).text =
                resources.getQuantityString(R.plurals.vod_ator_no_acervo, achados.size, achados.size)
            if (achados.isEmpty()) {
                estado.text = getString(R.string.vod_ficha_sem_acervo) + "\n" +
                    getString(R.string.vod_ator_so_acervo)
                return@launch
            }
            estado.visibility = View.GONE
            capas.trocar(achados)
            grade.post { grade.getChildAt(0)?.requestFocus() ?: grade.requestFocus() }
        }
    }

    private fun mostrarPerfil(perfil: Detalhes.Perfil) {
        // A foto do elenco (185 px) já está no cache e ocupa bem os 116dp do
        // círculo; a grande só entra quando não havia foto nenhuma — trocar
        // uma pela outra deixava o círculo em branco até a grande chegar.
        if (intent.getStringExtra(FOTO) == null) perfil.foto?.let { mostrarFoto(it) }
        if (perfil.nome.isNotBlank()) findViewById<TextView>(R.id.atorGrandeNome).text = perfil.nome

        val dados = listOfNotNull(
            perfil.conhecidaPor.takeIf { it.isNotBlank() },
            idade(perfil.nascimento, perfil.falecimento),
            perfil.local.takeIf { it.isNotBlank() },
        )
        findViewById<TextView>(R.id.atorDados).text = dados.joinToString("   ·   ")
        findViewById<TextView>(R.id.atorBio).text = perfil.biografia
    }

    private fun mostrarFoto(endereco: String) {
        findViewById<ImageView>(R.id.atorGrandeFoto).load(endereco) {
            transformations(CircleCropTransformation())
            crossfade(200)
            listener(onSuccess = { _, _ ->
                findViewById<View>(R.id.atorGrandeInicial).visibility = View.GONE
            })
        }
    }

    /** "1956 · 70 anos", ou "1956 – 2020" para quem já se foi. */
    private fun idade(nascimento: String, falecimento: String): String? {
        val nasceu = nascimento.take(4).toIntOrNull() ?: return null
        val morreu = falecimento.take(4).toIntOrNull()
        if (morreu != null) return "$nasceu – $morreu"
        val agora = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        return "$nasceu · ${agora - nasceu} anos"
    }

    companion object {
        private const val ID = "ator.id"
        private const val NOME = "ator.nome"
        private const val FOTO = "ator.foto"

        fun abrir(context: Context, pessoa: Detalhes.Pessoa) {
            context.startActivity(Intent(context, AtorActivity::class.java)
                .putExtra(ID, pessoa.id)
                .putExtra(NOME, pessoa.nome)
                .putExtra(FOTO, pessoa.foto))
        }
    }
}

/**
 * As capas do que a pessoa tem no acervo.
 *
 * A altura da capa sai da largura da coluna, na proporção 2:3 do pôster, e a
 * grade tem quantas colunas couberem: com largura fixa, cinco colunas não
 * cabiam ao lado do perfil e os cartões se sobrepunham.
 */
internal class CapasAdapter(
    /// Largura fixa do cartão, para usar numa fileira que corre para o lado.
    private val largura: Int = 0,
    private val aoEscolher: (Vod.Achado) -> Unit,
) : RecyclerView.Adapter<CapasAdapter.Holder>() {

    private var itens: List<Detalhes.Trabalho> = emptyList()
    var alturaDaCapa = 0

    fun trocar(novos: List<Detalhes.Trabalho>) {
        itens = novos
        notifyDataSetChanged()
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val quadro: View = view.findViewById(R.id.trabalhoQuadro)
        val capa: ImageView = view.findViewById(R.id.trabalhoCapa)
        val inicial: TextView = view.findViewById(R.id.trabalhoInicial)
        val nome: TextView = view.findViewById(R.id.trabalhoNome)
        val papel: TextView = view.findViewById(R.id.trabalhoPapel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trabalho, parent, false)
        view.onFocusChangeListener = FichaActivity.crescerNoFoco
        val holder = Holder(view)
        view.setOnKeyListener { _, codigo, evento -> Cartoes.segurarNasPontas(holder, codigo, evento) }
        if (alturaDaCapa > 0) holder.quadro.layoutParams.height = alturaDaCapa
        if (largura > 0) view.layoutParams = (view.layoutParams ?: RecyclerView.LayoutParams(largura,
            ViewGroup.LayoutParams.WRAP_CONTENT)).also { it.width = largura }
        return holder
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val trabalho = itens[position]
        val achado = trabalho.achado
        holder.nome.text = achado.nomeCompleto
        holder.papel.text = trabalho.papel
        holder.inicial.text = achado.titulo.take(1).uppercase()
        holder.inicial.visibility = View.VISIBLE
        if (trabalho.capa != null) {
            holder.capa.load(trabalho.capa) {
                crossfade(120)
                listener(onSuccess = { _, _ -> holder.inicial.visibility = View.GONE })
            }
        } else {
            holder.capa.dispose()
            holder.capa.setImageDrawable(null)
        }
        holder.itemView.setOnClickListener { aoEscolher(achado) }
    }

    override fun getItemCount() = itens.size
}
