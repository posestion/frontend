package com.example.posestion

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyPagerAdapter(fa:FragmentActivity) :FragmentStateAdapter(fa) {
    private val NUM_PAGES=3
    override fun getItemCount(): Int =NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->MyFragment1()
            1->MyFragment2()
            else->MyFragment3()
        }
    }


}