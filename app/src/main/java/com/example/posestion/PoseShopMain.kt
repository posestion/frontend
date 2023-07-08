package com.example.posestion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.posestion.databinding.PoseshopmainBinding
import com.google.android.material.tabs.TabLayoutMediator

class PoseShopMain : AppCompatActivity() {
    private lateinit var binding: PoseshopmainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= PoseshopmainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewpager.apply{
            adapter=MyPagerAdapter(context as FragmentActivity)
        }

        val tabTitles = listOf("상황별", "연령별", "HOT")

        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}