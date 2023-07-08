package com.example.posestion

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyPagerAdapter(fa:FragmentActivity) :FragmentStateAdapter(fa) {
    private val NUM_PAGES=3
    override fun getItemCount(): Int =NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{MyFragment.newInstance("상황별 포즈들","")}
            1->{MyFragment.newInstance("연령대 별 포즈들","")}
            else->{MyFragment.newInstance("HOT 포즈들","")}
        }
    }


}