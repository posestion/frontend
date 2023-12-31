package com.example.posestion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.posestion.databinding.FragmentFaqBinding

class FragmentFaq : Fragment() {

    private lateinit var binding: FragmentFaqBinding
    private lateinit var answer1: ConstraintLayout
    private lateinit var answer2: ConstraintLayout
    private lateinit var answer3: ConstraintLayout
    private lateinit var answerS1: ConstraintLayout
    private lateinit var answerS2: ConstraintLayout
    private lateinit var answerS3: ConstraintLayout
    private lateinit var buttonQ1: ImageButton
    private lateinit var buttonQ2: ImageButton
    private lateinit var buttonQ3: ImageButton
    private lateinit var buttonS1: ImageButton
    private lateinit var buttonS2: ImageButton
    private lateinit var buttonS3: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFaqBinding.inflate(layoutInflater)

        answer1 = binding.FfaqLayoutA1
        answer2 = binding.FfaqLayoutA2
        answer3 = binding.FfaqLayoutA3
        buttonQ1 = binding.FfaqBtnQ1
        buttonQ2 = binding.FfaqBtnQ2
        buttonQ3 = binding.FfaqBtnQ3

        answerS1 = binding.FfaqLayoutShop1
        answerS2 = binding.FfaqLayoutShop2
        answerS3 = binding.FfaqLayoutShop3
        buttonS1 = binding.FfaqBtnShop1
        buttonS2 = binding.FfaqBtnShop2
        buttonS3 = binding.FfaqBtnShop3

        buttonQ1.setOnClickListener {
            if(answer1.visibility == View.GONE){
                answer1.visibility = View.VISIBLE
                answer1.animate().alpha(1.0f).setDuration(500).start()
            }else{
                answer1.animate().alpha(0.0f).setDuration(500).withEndAction {
                    answer1.visibility = View.GONE
                }.start()
            }
        }

        buttonQ2.setOnClickListener {
            if(answer2.visibility == View.GONE){
                answer2.visibility = View.VISIBLE
                answer2.animate().alpha(1.0f).setDuration(500).start()
            }else{
                answer2.animate().alpha(0.0f).setDuration(500).withEndAction {
                    answer2.visibility = View.GONE
                }.start()
            }
        }

        buttonQ3.setOnClickListener {
            if(answer3.visibility == View.GONE){
                answer3.visibility = View.VISIBLE
                answer3.animate().alpha(1.0f).setDuration(500).start()
            }else{
                answer3.animate().alpha(0.0f).setDuration(500).withEndAction {
                    answer3.visibility = View.GONE
                }.start()
            }
        }

        buttonS1.setOnClickListener {
            if(answerS1.visibility == View.GONE){
                answerS1.visibility = View.VISIBLE
                answerS1.animate().alpha(1.0f).setDuration(500).start()
            }else{
                answerS1.animate().alpha(0.0f).setDuration(500).withEndAction {
                    answerS1.visibility = View.GONE
                }.start()
            }
        }

        buttonS2.setOnClickListener {
            if(answerS2.visibility == View.GONE){
                answerS2.visibility = View.VISIBLE
                answerS2.animate().alpha(1.0f).setDuration(500).start()
            }else{
                answerS2.animate().alpha(0.0f).setDuration(500).withEndAction {
                    answerS2.visibility = View.GONE
                }.start()
            }
        }

        buttonS3.setOnClickListener {
            if(answerS3.visibility == View.GONE){
                answerS3.visibility = View.VISIBLE
                answerS3.animate().alpha(1.0f).setDuration(500).start()
            }else{
                answerS3.animate().alpha(0.0f).setDuration(500).withEndAction {
                    answerS3.visibility = View.GONE
                }.start()
            }
        }

        return binding.root
    }
}