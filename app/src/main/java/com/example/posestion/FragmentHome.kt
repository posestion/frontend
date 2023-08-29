package com.example.posestion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.posestion.MyApplication.Companion.adlist
import com.example.posestion.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPager: ViewPager
    private lateinit var adadapter: AdapterHomeAD
    private lateinit var pagetext: TextView
    private var currentPage = 0
    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        pagetext = binding.FhomeTextPage
        pagetext.text = "1/${adlist.size}"

        viewPager = binding.FhomeViewpager
        adadapter = AdapterHomeAD(adlist, viewPager)

        viewPager.adapter = adadapter
        adadapter.startAutoScroll()

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

        viewPager.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    adadapter.stopAutoScroll()
                }
                MotionEvent.ACTION_MOVE -> {
                    // 터치 이동 이벤트 처리
                }
                MotionEvent.ACTION_UP -> {
                    adadapter.startAutoScroll()
                }
            }
            true
        }

        viewPager.setCurrentItem(currentPage)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adadapter.stopAutoScroll()
    }
}