package com.example.posestion

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import com.example.posestion.databinding.ActivityTermsBinding

class Activityterms : AppCompatActivity() {

    private val binding: ActivityTermsBinding by lazy { ActivityTermsBinding.inflate(layoutInflater) }
    private lateinit var checkbox1 : CheckBox
    private lateinit var checkbox2 : CheckBox
    private lateinit var checkbox3 : CheckBox
    private lateinit var checkbox4 : CheckBox
    private lateinit var checkbox5 : CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.AtermsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.backbutton)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        checkbox1 = binding.AtermsCheck1
        checkbox2 = binding.AtermsCheck2
        checkbox3 = binding.AtermsCheck3
        checkbox4 = binding.AtermsCheck4
        checkbox5 = binding.AtermsCheck5

        val moretext1 = binding.AtermsText1
        val spanmoretext1 = SpannableStringBuilder("보기")
        val clickmoretext1 = object : ClickableSpan() {
            override fun onClick(view: View) {

            }

            override fun updateDrawState(tt: TextPaint) {
                super.updateDrawState(tt)
                tt.color = Color.parseColor("#767676")
            }
        }

        val moretext2 = binding.AtermsText2
        val spanmoretext2 = SpannableStringBuilder("보기")
        val clickmoretext2 = object : ClickableSpan() {
            override fun onClick(view: View) {

            }

            override fun updateDrawState(tt: TextPaint) {
                super.updateDrawState(tt)
                tt.color = Color.parseColor("#767676")
            }
        }

        binding.AtermsBtn.setOnClickListener {
            if(binding.AtermsCheck2.isChecked
                && binding.AtermsCheck3.isChecked
                && binding.AtermsCheck4.isChecked
                && binding.AtermsCheck5.isChecked){
                val intent = Intent(this, ActivitySignup::class.java)
                startActivity(intent)
                finish()
            }
        }

        spanmoretext1.setSpan(clickmoretext1, 0, spanmoretext1.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        moretext1.text = spanmoretext1
        moretext1.movementMethod = LinkMovementMethod.getInstance()

        spanmoretext2.setSpan(clickmoretext2, 0, spanmoretext2.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        moretext2.text = spanmoretext2
        moretext2.movementMethod = LinkMovementMethod.getInstance()

        checkbox1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkbox2.isChecked = true
                checkbox3.isChecked = true
                checkbox4.isChecked = true
                checkbox5.isChecked = true
            } else {
                if(checkbox2.isChecked
                    && checkbox3.isChecked
                    && checkbox4.isChecked
                    && checkbox5.isChecked){
                    checkbox1.isChecked = true
                }
            }
        }

        check(checkbox2)
        check(checkbox3)
        check(checkbox4)
        check(checkbox5)
    }

    fun check(check : CheckBox){
        check.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if(checkbox2.isChecked
                    && checkbox3.isChecked
                    && checkbox4.isChecked
                    && checkbox5.isChecked){
                    checkbox1.isChecked = true
                }
            } else {
                if(checkbox1.isChecked){
                    checkbox1.isChecked = false
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.login_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, ActivityLogin::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}