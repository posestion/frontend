package com.example.posestion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.posestion.databinding.FragmentPoseshopmainBinding
import com.example.posestion.databinding.PoseshopmainBinding
import com.google.android.material.tabs.TabLayoutMediator

class PoseShopMainToolbar: Fragment() {
    private lateinit var binding: FragmentPoseshopmainBinding
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var viewModel: MyCustomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPoseshopmainBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val receivedValue = activity?.intent?.getStringExtra("key_name")
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        viewModel = ViewModelProvider(requireActivity()).get(MyCustomViewModel::class.java)

        binding.sbutton.setOnClickListener {
            val intent = Intent(requireContext(), PoseShopingactiv::class.java)
            intent.putExtra("key_name", receivedValue)
            intent.putIntegerArrayListExtra(
                "addedImageIds",
                ArrayList(viewModel.addedImageIds.value)
            )
            startActivity(intent)
        }
    }
}
