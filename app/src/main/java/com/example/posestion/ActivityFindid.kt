package com.example.posestion

import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.posestion.databinding.ActivityFindidBinding

class ActivityFindid : AppCompatActivity() {

    private val binding: ActivityFindidBinding by lazy { ActivityFindidBinding.inflate(layoutInflater) }
    private lateinit var spinner_phone : Spinner

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
            findidsuccess()
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
    private fun findidsuccess() {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
        val view = LayoutInflater.from(this).inflate(
            R.layout.dialog_findid,
            findViewById(R.id.dfindid_layout)
        )

        // 다이얼로그 텍스트 설정
        builder.setView(view)
        view.findViewById<TextView>(R.id.dfindid_id).text = "id"
        view.findViewById<TextView>(R.id.dfindid_name).text = "name"

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

        val alertDialog = builder.create()

        view.findViewById<ImageButton>(R.id.dfindid_fail_btn).setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(0))

        alertDialog.show()
    }
}