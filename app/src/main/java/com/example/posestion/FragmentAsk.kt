package com.example.posestion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.posestion.databinding.FragmentAskBinding

class FragmentAsk : Fragment() {

    private lateinit var binding: FragmentAskBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAskBinding.inflate(layoutInflater)
        return binding.root
    }
}