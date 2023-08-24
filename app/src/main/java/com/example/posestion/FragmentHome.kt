package com.example.posestion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.posestion.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPager: ViewPager
    private lateinit var adadapter: AdapterHomeAD
    private var adlist = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        adlist.add("https://blog.kakaocdn.net/dn/lItsm/btqVIcdCuDF/tfEixQxuXjhVoclkmG6w9K/img.jpg")

        viewPager = binding.FhomeViewpager
        adadapter = AdapterHomeAD()
        adadapter.getimage(adlist)

        viewPager.adapter = adadapter


        return binding.root
    }
}