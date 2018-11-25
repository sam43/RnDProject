package com.looser43.rndproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //viewPager.adapter = SampleFragmentPagerAdapter(supportFragmentManager,this@MainActivity)

        // Give the TabLayout the ViewPager
        //tab_layout.setupWithViewPager(viewPager)

        val l_m = LinearLayoutManager(this)
        days_list_2.layoutManager = l_m
        val adapter = RecyclerViewAdapter(resources.getStringArray(R.array.days_names))
        days_list_2.adapter = adapter

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Sample RnD"
        //Try this for scrollable tabs, like in Google Play Store
        //tab_layout.tabMode = TabLayout.
        for (i in 0..4) {
            if (i == 1)
                tab_layout.addTab(tab_layout.newTab().setText("Extra Large Tab Name No. " + (i + 1)))
            else
                tab_layout.addTab(tab_layout.newTab().setText("Large Tab Name " + (i + 1)))
            //val tab = tab_layout.getTabAt(i)
            //tab?.setCustomView(R.layout.badgeview)
        }

        //tab_layout.setBadgeText(2,"5")
        //tab_layout.setBadgeText(4,"15")

        //setSupportActionBar(toolbar)

    }
}
