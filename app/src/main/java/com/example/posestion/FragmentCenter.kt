package com.example.posestion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.example.posestion.databinding.FragmentCenterBinding

class FragmentCenter : Fragment() {

    private lateinit var binding: FragmentCenterBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCenterBinding.inflate(layoutInflater)
        viewPager = binding.fcenterPager
        tabLayout = binding.fcenterTablayout

        val adapter = AdapterCenter(childFragmentManager, lifecycle)

        adapter.addFragment(FragmentFaq(), "FAQ")
        adapter.addFragment(FragmentMyask(), "내 문의내역")
        adapter.addFragment(FragmentAsk(), "문의하기")

        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()

        return binding.root
    }
}