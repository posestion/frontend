package com.example.posestion

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.posestion.databinding.PoseshopBinding

class PoseshopFilter : AppCompatActivity() {
    private lateinit var binding: PoseshopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PoseshopBinding.inflate(layoutInflater)
        setContentView(binding.root)

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