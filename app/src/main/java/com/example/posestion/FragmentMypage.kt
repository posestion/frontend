package com.example.posestion

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.posestion.databinding.FragmentMypageBinding

class FragmentMypage : Fragment() {

    private lateinit var binding: FragmentMypageBinding
    private val user = MyApplication.user
    private lateinit var nickname : TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(layoutInflater)
        nickname = binding.fmypageTextNick
        nickname.text = user.getString("id", "").toString()

        binding.fmypageBtnPen.setOnClickListener {
            val intent = Intent(activity, ActivityChangeUser::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}