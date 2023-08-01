package com.example.posestion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.posestion.databinding.FragmentWithdrawBinding


class FragmentWithdraw : Fragment() {


    private lateinit var binding: FragmentWithdrawBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWithdrawBinding.inflate(layoutInflater)
        return binding.root
    }
}