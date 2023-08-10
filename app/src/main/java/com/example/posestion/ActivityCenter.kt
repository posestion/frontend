package com.example.posestion

import android.os.Bundle
import android.view.MenuItem
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

        setSupportActionBar(binding.AcenterToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.svg_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewPager = binding.AcenterPager
        tabLayout = binding.AcenterTablayout

        val adapter = AdapterCenter(this)

        adapter.addFragment(FragmentFaq(), "FAQ")
        adapter.addFragment(FragmentMyask(), "내 문의내역")
        adapter.addFragment(FragmentAsk(), "문의하기")

        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.center_basket -> {

                return true
            }
            R.id.center_search -> {

                return true
            }

            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}