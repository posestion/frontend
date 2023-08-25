package com.example.posestion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.posestion.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPager: ViewPager
    private lateinit var adadapter: AdapterHomeAD
    private lateinit var pagetext: TextView
    private var currentPage = 0
    private var adlist = mutableListOf<String>()
    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        pagetext = binding.FhomeTextPage

        //adlist.add("") url 주소 넣기

        viewPager = binding.FhomeViewpager
        adadapter = AdapterHomeAD()
        adadapter.getimage(adlist)

        viewPager.adapter = adadapter

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                adadapter.setCurrentPage(position)
                page = adadapter.getCurrentPage()+1
                pagetext.text = "${page}/${adlist.size}"
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        viewPager.setCurrentItem(currentPage)

        return binding.root
    }
}