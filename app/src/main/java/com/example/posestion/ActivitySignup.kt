package com.example.posestion

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.posestion.databinding.ActivitySignupBinding

class ActivitySignup : AppCompatActivity() {

    private val binding: ActivitySignupBinding by lazy { ActivitySignupBinding.inflate(layoutInflater) }
    private lateinit var spinner_phone : Spinner
    private lateinit var pwcheck : EditText
    private lateinit var pw : EditText
    private lateinit var pwchecktext : TextView
    private lateinit var pwtext : TextView

    private val pwcheckwatcherListener = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val inputText = s.toString()
            if (inputText.isEmpty()) {
                pwchecktext.visibility = View.INVISIBLE
            } else {
                pwchecktext.visibility = View.VISIBLE
                if (inputText == pw.text.toString()) {
                    pwchecktext.text = "비밀번호가 일치합니다."
                } else {
                    pwchecktext.text = "비밀번호가 일치하지 않습니다."
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private val pwwatcherListener = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val inputText = s.toString()
            if (inputText.isEmpty()) {
                pwtext.visibility = View.INVISIBLE
            } else {
                pwtext.visibility = View.VISIBLE
                val pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#\$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]).{8,}$".toRegex()

                if (inputText.matches(pattern)) {
                    pwtext.visibility = View.INVISIBLE
                } else {
                    pwtext.text = "비밀번호 형식이 일치하지 않습니다."
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        pwcheck = binding.AsignupEditPwCheck
        pw = binding.AsignupEditPw
        pwchecktext = binding.AsignupTextPwcheck
        pwtext = binding.AsignupTextPw

        pw.addTextChangedListener(pwwatcherListener)
        pwcheck.addTextChangedListener(pwcheckwatcherListener)

        setSupportActionBar(binding.AsignupToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.backbutton)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val dpValue = 88
        val pixels = (dpValue * Resources.getSystem().displayMetrics.density).toInt()

        spinner_phone = binding.AsignupSpinner
        spinner_phone.setDropDownWidth(pixels)

        ArrayAdapter.createFromResource(this, R.array.phone_spinner_item, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_phone.adapter = adapter
        }

        binding.AsignupBtnNext.setOnClickListener {
            val intent = Intent(this, ActivityMakeProfile::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.login_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, Activityterms::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}