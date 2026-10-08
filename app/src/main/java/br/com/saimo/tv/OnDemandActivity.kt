package br.com.saimo.tv

import android.os.Bundle
import android.view.View
import androidx.media3.common.util.UnstableApi

@UnstableApi
class OnDemandActivity : TelaComMenu() {
    override val aba = Aba.ON_DEMAND

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ondemand)

        findViewById<View>(R.id.btnFilmes).setOnClickListener {
            GradeActivity.abrir(this, GradeActivity.FILMES)
        }
        
        findViewById<View>(R.id.btnSeries).setOnClickListener {
            GradeActivity.abrir(this, GradeActivity.SERIES)
        }
        
        findViewById<View>(R.id.btnAnimes).setOnClickListener {
            GradeActivity.abrir(this, GradeActivity.ANIMES)
        }
        
        findViewById<View>(R.id.btnDoramas).setOnClickListener {
            GradeActivity.abrir(this, GradeActivity.DORAMAS)
        }
    }
}
