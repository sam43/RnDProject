package com.looser43.rndproject.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.looser43.rndproject.R
import kotlinx.android.synthetic.main.fragment_fragment_one.*


class FragmentOne : Fragment() {
    private lateinit var fragment: Fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabs()
    }

    private fun setUpTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("Not Viewed"))
        tabLayout.addTab(tabLayout.newTab().setText("Viewed"))
        tabLayout.addTab(tabLayout.newTab().setText("Rejected"))
        //tabLayout.addTab(tabLayout.newTab().setText("Assessments"))
        tabLayout.setBadgeText(0, "10")
        tabLayout.setBadgeText(2, "2")
        //tabLayout.setBadgeText(3,"1")
        tabLayout.getTabAt(0)?.select()
        selectFragment(0)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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
                fragment = PageFragment()
                goToFragment(fragment)
            }
            1 -> {
                fragment = FragmentTwo()
                goToFragment(fragment)
            }
            2 -> {
                fragment = FragmentThree()
                goToFragment(fragment)
            }
            else -> goToFragment(fragment)
        }
    }

    private fun goToFragment(fragment: Fragment) {
        val ft = activity?.supportFragmentManager?.beginTransaction()
        ft?.replace(R.id.fragment1_container, fragment)
        ft?.commit()
    }
}
