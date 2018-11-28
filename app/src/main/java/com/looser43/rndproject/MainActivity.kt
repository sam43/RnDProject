package com.looser43.rndproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var fragment: PageFragment = PageFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Sample RnD"
        for (i in 0..4) {
            if (i == 1) {
                tab_layout.addTab(tab_layout.newTab().setText("Extra Large Tab Name No. " + (i + 1)))
                goToFragment()
            }
            else
                tab_layout.addTab(tab_layout.newTab().setText("Large Tab Name " + (i + 1)))
            //val tab = tab_layout.getTabAt(i)
            //tab?.setCustomView(R.layout.badgeview)
        }

    }

    private fun goToFragment() {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frag_container, fragment)
        ft.commit()
    }
}
