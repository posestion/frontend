package com.example.posestion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.posestion.databinding.PoseshopmainBinding
import com.google.android.material.tabs.TabLayoutMediator

class PoseshopMainFragment : Fragment() {
    private lateinit var binding: PoseshopmainBinding
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var viewModel: MyCustomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PoseshopmainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(MyCustomViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val receivedValue = activity?.intent?.getStringExtra("key_name")
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        binding.viewpager.apply {
            adapter = MyPagerAdapter(requireContext() as FragmentActivity)
        }

        val tabTitles = listOf("좋아요", "연령별", "HOT")

        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()


        binding.sbutton.setOnClickListener {
            val intent = Intent(requireContext(), PoseShopingactiv::class.java)
            intent.putExtra("key_name", receivedValue)
            intent.putIntegerArrayListExtra("addedImageIds", ArrayList(viewModel.addedImageIds.value))
            Log.d("MyRecyclerViewAdapter91", viewModel.addedImageIds.value.toString())
            startActivity(intent)
        }

        binding.filter.setOnClickListener {
            val intent = Intent(requireContext(), PoseshopFilter::class.java)
            startActivity(intent)
        }

        binding.edittext.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (event != null && actionId == EditorInfo.IME_ACTION_DONE) {
                    // 키패드 내리기
                    val imm: InputMethodManager =
                        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.edittext.windowToken, 0)
                    return true
                }
                return false
            }
        })
    }
    override fun onResume() {
        super.onResume()
        // 제거 로직이 필요 없음
    }
}