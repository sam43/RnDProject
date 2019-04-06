package com.looser43.rndproject.ui.activities

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.looser43.rndproject.R
import com.looser43.rndproject.callbacks.AdapterCallBacks
import com.looser43.rndproject.models.DataModelHorizontal
import com.looser43.rndproject.ui.adapter.DataAdapterHorizontal
import com.looser43.rndproject.ui.fragments.FragmentOne
import com.looser43.rndproject.ui.fragments.FragmentThree
import com.looser43.rndproject.ui.fragments.FragmentTwo
import com.looser43.rndproject.ui.fragments.PageFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), AdapterCallBacks {
    override fun onLongClick(v: View, position: Int) {
        Log.d("tabSelectedRe", "$position")
    }

    override fun onSingleClick(position: Int) {
        Log.d("tabSelectedRe", "$position")
    }

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
        //setSupportActionBar(toolbar)
        //supportActionBar?.title = "Sample"

        /*tab_layout.addTab(tab_layout.newTab().setText("Tab Name onek boro"))
        tab_layout.addTab(tab_layout.newTab().setText("Tab arekta o boro"))
        tab_layout.addTab(tab_layout.newTab().setText("Tab 3"))
        tab_layout.addTab(tab_layout.newTab().setText("Tab 4"))
        tab_layout.tabGravity = TabLayout.MODE_SCROLLABLE
        tab_layout.setBadgeText(0,"10")
        tab_layout.setBadgeText(2,"2")
        tab_layout.getTabAt(0)?.select()*/
        //selectFragment(0)
        /*smoothScroller = object : LinearSmoothScroller(this@MainActivity) {
            override fun getVerticalSnapPreference(): Int {
                return LinearSmoothScroller.SNAP_TO_START
            }
        }*/
        recyclerViewHori()
        first = true
        //setUpTabs()
        //setUpViewPager()
        setUpFab()
        selectFragment(0)
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

    private fun selectFragment(position: Int?) {
        when (position) {
            0 -> {
                fragment = FragmentOne()
                goToFragment(fragment)
            }
            1 -> {
                fragment = PageFragment()
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
        adapter =
            DataAdapterHorizontal(items = horizontalDataModelList, context = this@MainActivity, b = false, nl = false)

        rv_hori.adapter = adapter
        rv_hori.setHasFixedSize(false)

        layoutManagerHoriztal = androidx.recyclerview.widget.LinearLayoutManager(
            this@MainActivity,
            androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
            false
        )
        rv_hori.layoutManager = layoutManagerHoriztal
    }

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
