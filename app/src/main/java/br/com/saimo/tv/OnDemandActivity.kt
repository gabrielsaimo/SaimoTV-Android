package br.com.saimo.tv

import androidx.media3.common.util.UnstableApi

/**
 * A aba On Demand do menu do topo: filmes, séries, animes e doramas numa
 * página só, no formato da tela inicial — destaque grande em cima, as quatro
 * categorias em cartões largos e as fileiras do acervo embaixo.
 */
@UnstableApi
class OnDemandActivity : PaginaActivity() {
    override fun tipoDaPagina() = TODOS
}
