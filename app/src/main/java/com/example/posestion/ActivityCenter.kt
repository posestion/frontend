package com.example.posestion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.posestion.databinding.ActivityCenterBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ActivityCenter : AppCompatActivity() {

    private val binding: ActivityCenterBinding by lazy { ActivityCenterBinding.inflate(layoutInflater) }
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewPager = binding.fcenterPager
        tabLayout = binding.fcenterTablayout

        val adapter = AdapterCenter(this)

        adapter.addFragment(FragmentFaq(), "FAQ")
        adapter.addFragment(FragmentMyask(), "내 문의내역")
        adapter.addFragment(FragmentAsk(), "문의하기")

        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()
    }
}