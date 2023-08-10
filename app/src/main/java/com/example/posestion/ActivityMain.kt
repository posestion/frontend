package com.example.posestion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.posestion.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ActivityMain : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.AmainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.svg_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        bottomNavigationView = binding.AmainBnv

        supportFragmentManager
            .beginTransaction()
            .replace(binding.AmainFrame.id, FragmentMypage())
            .commitAllowingStateLoss()

        bottomNavigationView.selectedItemId = R.id.bnv_mypage

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bnv_pose -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(binding.AmainFrame.id, FragmentMypage())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bnv_camera -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(binding.AmainFrame.id, FragmentMypage())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bnv_home -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(binding.AmainFrame.id, FragmentMypage())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bnv_board -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(binding.AmainFrame.id, FragmentMypage())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bnv_mypage -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(binding.AmainFrame.id, FragmentMypage())
                        .commitAllowingStateLoss()
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mypage_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mypage_help -> {
                val intent = Intent(this, ActivityHelp::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}