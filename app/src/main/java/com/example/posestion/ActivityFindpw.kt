package com.example.posestion

import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.ActivityFindpwBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityFindpw : AppCompatActivity() {

    private val binding: ActivityFindpwBinding by lazy { ActivityFindpwBinding.inflate(layoutInflater) }
    private lateinit var spinner_phone : Spinner
    private lateinit var pwcheckedit : EditText
    private lateinit var pwedit : EditText
    private lateinit var pwchecktext : TextView
    private lateinit var pwtext : TextView
    private var pwcheck = false

    private val pwcheckwatcherListener = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val inputText = s.toString()
            if (inputText.isEmpty()) {
                pwchecktext.visibility = View.INVISIBLE
            } else {
                pwchecktext.visibility = View.VISIBLE
                if (inputText == pwedit.text.toString()) {
                    pwchecktext.text = "비밀번호가 일치합니다."
                    pwcheck = true
                } else {
                    pwchecktext.text = "비밀번호가 일치하지 않습니다."
                    pwcheck = false
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    //비밀번호 양식 확인
    private val pwwatcherListener = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val inputText = s.toString()
            if (inputText.isEmpty()) {
                pwtext.visibility = View.INVISIBLE
            } else {
                pwtext.visibility = View.VISIBLE
                val pattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#\$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]).{8,30}$".toRegex()

                if (inputText.matches(pattern)) {
                    pwtext.visibility = View.INVISIBLE
                } else {
                    pwtext.text = "비밀번호 형식이 올바르지 않습니다."
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.AfindpwToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.image_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        pwcheckedit = binding.AfindpwEditPwcheck
        pwedit = binding.AfindpwEditPw
        pwchecktext = binding.AfindpwTextPwcheck2
        pwtext = binding.AfindpwTextPwcheck1

        pwedit.addTextChangedListener(pwwatcherListener)
        pwcheckedit.addTextChangedListener(pwcheckwatcherListener)

        val dpValue = 66
        val pixels = (dpValue * Resources.getSystem().displayMetrics.density).toInt()

        spinner_phone = binding.AfindpwSpinner
        spinner_phone.setDropDownWidth(pixels)

        ArrayAdapter.createFromResource(this, R.array.phone_spinner_item, R.layout.spinner_text
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_phone.adapter = adapter
        }

        binding.AfindpwBtnChangepw.setOnClickListener{
            val id = binding.AfindpwEditId.text.toString()
            val name = binding.AfindpwEditName.text.toString()
            val pw = binding.AfindpwEditPw.text.toString()
            val phone = binding.AfindpwEditPhonenum.text.toString()

            val call = RetrofitObject.getRetrofitService
            call.resetpw(RetrofitClient.Requestpwreset(id, name, phone, pw))
                .enqueue(object : Callback<RetrofitClient.Responsepwreset> {
                    override fun onResponse(call: Call<RetrofitClient.Responsepwreset>, response: Response<RetrofitClient.Responsepwreset>) {
                        if (response.isSuccessful) {
                            Log.d("Retrofit", "왜안돼")
                            val result = response.body()
                            if(result != null){
                                if(result.isSuccess == true){
                                    Log.d("Retrofit", "비밀번호")
                                    resetpw()
                                }
                                Log.d("Retrofit", result.message.toString())
                            }
                        } else {
                            val errorBody = response.errorBody()?.string()
                            Log.d("Retrofit", "Response Error: $errorBody")
                        }
                    }
                    override fun onFailure(call: Call<RetrofitClient.Responsepwreset>, t: Throwable) {
                        val errorMessage = "Call Failed: ${t.message}"
                        Log.d("Retrofit", errorMessage)
                    }
                })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.empty_menu, menu)
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

    //dialog 띄우기
    private fun resetpw() {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
        val view = LayoutInflater.from(this).inflate(
            R.layout.dialog_pwreset,
            findViewById(R.id.dpwreset_layout)
        )

        builder.setView(view)

        val alertDialog = builder.create()

        view.findViewById<Button>(R.id.dpwreset_btn).setOnClickListener {
            val intent = Intent(this, ActivityLogin::class.java)
            startActivity(intent)
            finish()
            alertDialog.dismiss()
        }

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(0))

        alertDialog.show()
    }
}