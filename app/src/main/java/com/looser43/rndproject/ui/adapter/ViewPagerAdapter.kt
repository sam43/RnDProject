package com.looser43.rndproject.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.looser43.rndproject.ui.fragments.FragmentOne
import com.looser43.rndproject.ui.fragments.FragmentThree
import com.looser43.rndproject.ui.fragments.FragmentTwo
import com.looser43.rndproject.ui.fragments.PageFragment


internal class ViewPagerAdapter(manager: FragmentManager, private val pageNum: Int) :
    FragmentStatePagerAdapter(manager) {

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> {
                FragmentOne()
            }
            1 -> {
                PageFragment()
            }
            2 -> {
                FragmentThree()
            }
            3 -> {
                FragmentTwo()
            }
            else -> FragmentOne()
        }
    }

    override fun getCount(): Int {
        return pageNum
    }
}