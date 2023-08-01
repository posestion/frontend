package com.example.posestion

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.posestion.databinding.ActivityFindidBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityFindid : AppCompatActivity() {

    private val binding: ActivityFindidBinding by lazy { ActivityFindidBinding.inflate(layoutInflater) }
    private lateinit var spinner_phone : Spinner
    private var userid = ""
    private var name = ""
    private var phonenum = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.AfindidToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.backbutton)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val dpValue = 66
        val pixels = (dpValue * Resources.getSystem().displayMetrics.density).toInt()

        spinner_phone = binding.AfindidSpinner
        spinner_phone.setDropDownWidth(pixels)

        ArrayAdapter.createFromResource(this, R.array.phone_spinner_item, R.layout.spinner_text
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_phone.adapter = adapter
        }

        binding.AfindidBtnFind.setOnClickListener {
            name = binding.AfindidEditName.text.toString()
            phonenum = binding.AfindidEditPhonenum.text.toString()
            val call = RetrofitObject.getRetrofitService
            call.findid(Requestfindid(name, phonenum))
                .enqueue(object : Callback<Responsefindid> {
                    override fun onResponse(call: Call<Responsefindid>, response: Response<Responsefindid>) {
                        if (response.isSuccessful) {
                            val result = response.body()
                            if(result != null){
                                Log.d("Retrofit", result.message.toString())
                                if(result.isSuccess == true ){
                                    userid = result.result.userid
                                    gologin()
                                    findidsuccess(userid, name)
                                }
                                else{
                                    gosignup()
                                    findidfail()
                                }
                            }
                        } else {
                            val errorBody = response.errorBody()?.string()
                            Log.d("Retrofit", "Response Error: $errorBody")
                        }
                    }
                    override fun onFailure(call: Call<Responsefindid>, t: Throwable) {
                        val errorMessage = "Call Failed: ${t.message}"
                        Log.d("Retrofit", errorMessage)
                    }
                })
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

    //ID 찾기 성공시 dialog
    private fun findidsuccess(userid : String, username : String) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
        val view = LayoutInflater.from(this).inflate(
            R.layout.dialog_findid,
            findViewById(R.id.dfindid_layout)
        )

        // 다이얼로그 텍스트 설정
        builder.setView(view)
        view.findViewById<TextView>(R.id.dfindid_id).text = userid
        view.findViewById<TextView>(R.id.dfindid_name).text = username

        val alertDialog = builder.create()

        view.findViewById<ImageButton>(R.id.dfindid_btn).setOnClickListener {
            alertDialog.dismiss()
        }

        // 다이얼로그 형태 지우기
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(0))

        alertDialog.show()
    }

    //ID 찾기 실패시 dialog
    private fun findidfail() {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
        val view = LayoutInflater.from(this).inflate(
            R.layout.dialog_findid_fail,
            findViewById(R.id.dfindid_fail)
        )

        builder.setView(view)

        val alertDialog = builder.create()

        view.findViewById<ImageButton>(R.id.dfindid_fail_btn).setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(0))

        alertDialog.show()
    }

    private fun gologin(){
        val next = binding.AfindidTextNext
        next.visibility = View.VISIBLE
        val spannext = SpannableStringBuilder("로그인하러가기")
        val clicknext = object : ClickableSpan() {
            override fun onClick(view: View) {
                val intent = Intent(view.context, ActivityLogin::class.java)
                view.context.startActivity(intent)
                finish()
            }

            override fun updateDrawState(tt: TextPaint) {
                super.updateDrawState(tt)
                tt.color = Color.parseColor("#5F5F5F")
            }
        }

        spannext.setSpan(clicknext, 0, spannext.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        next.text = spannext
        next.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun gosignup(){
        val next = binding.AfindidTextNext
        next.visibility = View.VISIBLE
        val spannext = SpannableStringBuilder("회원가입하러가기")
        val clicknext = object : ClickableSpan() {
            override fun onClick(view: View) {
                val intent = Intent(view.context, Activityterms::class.java)
                view.context.startActivity(intent)
                finish()
            }

            override fun updateDrawState(tt: TextPaint) {
                super.updateDrawState(tt)
                tt.color = Color.parseColor("#5F5F5F")
            }
        }

        spannext.setSpan(clicknext, 0, spannext.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        next.text = spannext
        next.movementMethod = LinkMovementMethod.getInstance()
    }
}