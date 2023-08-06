package com.example.posestion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.posestion.databinding.ActivityHelpBinding

class ActivityHelp : AppCompatActivity() {

    private val binding: ActivityHelpBinding by lazy { ActivityHelpBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.AhelpToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.image_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.help_search -> {

                return true
            }
            R.id.help_basket -> {

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}