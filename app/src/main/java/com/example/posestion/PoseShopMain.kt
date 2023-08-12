package com.example.posestion

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.posestion.databinding.PoseshopmainBinding
import com.google.android.material.tabs.TabLayoutMediator

class PoseShopMain : AppCompatActivity() {
    private lateinit var binding: PoseshopmainBinding
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var viewModel: MyCustomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= PoseshopmainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PoseshopMainFragment())
                .commit()
        }

        val receivedValue = intent.getStringExtra("key_name")
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        viewModel = ViewModelProvider(this).get(MyCustomViewModel::class.java)
        binding.viewpager.apply{
            adapter=MyPagerAdapter(context as FragmentActivity)
        }

        val tabTitles = listOf("좋아요", "연령별", "HOT")

        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        binding.sbutton.setOnClickListener {
            val intent = Intent(this, PoseShopingactiv::class.java)
            intent.putExtra("key_name", receivedValue)
            intent.putIntegerArrayListExtra(
                "addedImageIds",
                ArrayList(viewModel.addedImageIds.value)
            )
            startActivity(intent)
        }
        binding.filter.setOnClickListener({
            val intent = Intent(this, PoseshopFilter::class.java)
            startActivity(intent)
        })

        binding.edittext.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event != null) {
                    if (event.action == KeyEvent.ACTION_DOWN && keyCode === KeyEvent.KEYCODE_ENTER) {
                        //키패드 내리기
                        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(binding.edittext.getWindowToken(), 0)

                        //처리
                        //prcss()
                        return true
                    }
                }
                return false
            }
        })

    }
}