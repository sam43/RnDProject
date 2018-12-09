package com.looser43.rndproject

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.looser43.rndproject.adapter.DataAdapterHorizontal
import com.looser43.rndproject.fragments.FragmentOne
import com.looser43.rndproject.fragments.FragmentThree
import com.looser43.rndproject.fragments.FragmentTwo
import com.looser43.rndproject.models.DataModelHorizontal
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.badgeview.*


class MainActivity : AppCompatActivity() {

    private lateinit var fragment: Fragment
    private lateinit var tabName: TextView
    private lateinit var tabCount: TextView
    private lateinit var smoothScroller: RecyclerView.SmoothScroller
    private lateinit var v: View
    private var count: Int = 0
    private lateinit var layoutManagerHoriztal: LinearLayoutManager
    private var horizontalDataModelList: ArrayList<DataModelHorizontal> = ArrayList()
    private var adapter: DataAdapterHorizontal? = null
    private var first: Boolean = false
    private var p: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareData()
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Sample"

        tab_layout.addTab(tab_layout.newTab().setText("Tab Name onek boro"))
        tab_layout.addTab(tab_layout.newTab().setText("Tab arekta o boro"))
        tab_layout.addTab(tab_layout.newTab().setText("Tab 3"))
        tab_layout.addTab(tab_layout.newTab().setText("Tab 4"))
        tab_layout.tabGravity = TabLayout.MODE_SCROLLABLE
        tab_layout.getTabAt(1)?.select()
        selectFragment(1)
        /*smoothScroller = object : LinearSmoothScroller(this@MainActivity) {
            override fun getVerticalSnapPreference(): Int {
                return LinearSmoothScroller.SNAP_TO_START
            }
        }*/
        recyclerViewHori()
        first = true
        setUpTabs()
        //setUpViewPager()
        setUpFab()
    }

    private fun setUpFab() {
        fab_add.setOnClickListener {
            val a = DataModelHorizontal("${1 + count}", "extra$count")
            //horizontalDataModelList.add(a)
            if (first) {
                adapter?.add(p, a)
                //smoothScroller.targetPosition = 0
            } else {
                adapter?.add(p, a)
                //smoothScroller.targetPosition = p
            }
            //adapter?.notifyItemChanged(p)
            //layoutManagerHoriztal.startSmoothScroll(smoothScroller)
            p++
            count++
        }
    }

    private fun setUpTabs() {
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.d("tabSelectedRe", "${tab?.position} and ")
                selectFragment(tab?.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("tabSelectedUn", "${tab?.position} and ")
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.d("tabSelected", "${tab?.position} and $")
                selectFragment(tab?.position)
            }

        })
    }

    private fun selectFragment(position: Int?) {
        when (position) {
            0 -> {
                fragment = FragmentOne()
                goToFragment(fragment)
            }
            1 -> {
                fragment = FragmentThree()
                goToFragment(fragment)
            }
            2 -> {
                fragment = FragmentTwo()
                goToFragment(fragment)
            }
            3 -> {
                fragment = FragmentThree()
                goToFragment(fragment)
            }
            else -> goToFragment(fragment)
        }
    }

    private fun prepareData() {

        horizontalDataModelList.clear()

        var a = DataModelHorizontal("103", "All Applicants")
        horizontalDataModelList.add(a)
        a = DataModelHorizontal("10", "Not Viewed")
        horizontalDataModelList.add(a)
        a = DataModelHorizontal("", "null")
        horizontalDataModelList.add(a)
        adapter?.notifyDataSetChanged()
    }

    private fun recyclerViewHori() {
        adapter = DataAdapterHorizontal(horizontalDataModelList, this@MainActivity, false, false)

        rv_hori.adapter = adapter
        rv_hori.setHasFixedSize(false)

        layoutManagerHoriztal = androidx.recyclerview.widget.LinearLayoutManager(
            this@MainActivity,
            androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
            false
        )
        rv_hori.layoutManager = layoutManagerHoriztal
    }

    private fun createCustomView(title: String, count: Int): TabLayout.Tab {
        val tab = tab_layout.newTab().setCustomView(R.layout.badgeview)
        tab_name.text = title
        tab_count.text = count.toString()
        return tab
    }

    /*private fun setUpViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager, tab_layout.tabCount)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.d("tabSelectedRe","${tab?.position} and ${viewPager?.currentItem}")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("tabSelectedUn","${tab?.position} and ${viewPager?.currentItem}")
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { viewPager.currentItem = it }
                Log.d("tabSelected","${tab?.position} and ${viewPager?.currentItem}")
            }

        })

        *//*val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(FragmentOne(), "ONE")
        adapter.addFragment(FragmentTwo(), "TWO")
        adapter.addFragment(FragmentThree(), "THREE")
        adapter.addFragment(PageFragment(), "Four blaaa PageFragment")
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))*//*
    }*/

    private fun updateTab(selected: Boolean) {
        if (selected) {
            tabName.setTextColor(Color.WHITE)
        } else {
            tabName.setTextColor(Color.parseColor("#88ffffff"))
        }
    }

    private fun goToFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frag_container, fragment)
        ft.commit()
    }
}
