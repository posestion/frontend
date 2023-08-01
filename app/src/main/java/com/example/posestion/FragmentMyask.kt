package com.example.posestion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.posestion.databinding.FragmentMyaskBinding

class FragmentMyask : Fragment() {

    private lateinit var binding: FragmentMyaskBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyaskBinding.inflate(layoutInflater)
        return binding.root
    }
}