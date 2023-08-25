package com.example.posestion

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.drawerlayout.widget.DrawerLayout
import com.example.posestion.camera.CameraMainActivity
import com.example.posestion.camera.CameraSettingActivity
import com.example.posestion.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ActivityMain : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var drawerView : View
    private val user = MyApplication.user
    private val editor = user.edit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.AmainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.svg_menu2)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        drawerLayout = binding.AmainDrawerLayout
        drawerView = binding.AmainDrawer

        findViewById<Button>(R.id.drawer_home).setOnClickListener {
            binding.AmainToolbar.visibility = View.VISIBLE
            supportFragmentManager
                .beginTransaction()
                .replace(binding.AmainFrame.id, FragmentHome())
                .commitAllowingStateLoss()
            binding.AmainToolbarTitle.text = "POSESTION"
            binding.AmainToolbarTitle.setTypeface(resources.getFont(R.font.amatica_sc_bold), Typeface.BOLD)
            binding.AmainToolbarTitle.textSize = 24f
            drawerLayout.closeDrawer(drawerView)
        }

        findViewById<Button>(R.id.drawer_pose).setOnClickListener {
            binding.AmainToolbar.visibility = View.GONE
            supportFragmentManager
                .beginTransaction()
                .replace(binding.AmainFrame.id, PoseshopMainFragment()) // Replace with your fragment
                .commitAllowingStateLoss()
            binding.AmainToolbarTitle.text = "포즈상점"
            binding.AmainToolbarTitle.setTypeface(resources.getFont(R.font.sf_arabic_rounded), Typeface.BOLD)
            binding.AmainToolbarTitle.textSize = 20f
            bottomNavigationView.selectedItemId = R.id.bnv_pose
            drawerLayout.closeDrawer(drawerView)
        }

        findViewById<Button>(R.id.drawer_basket).setOnClickListener {
            val intent = Intent(this, PoseShopingactiv::class.java)
            startActivity(intent)
            drawerLayout.closeDrawer(drawerView)
        }

        findViewById<Button>(R.id.drawer_camera).setOnClickListener {
            binding.AmainToolbar.visibility = View.VISIBLE
            val intent = Intent(this, CameraMainActivity::class.java)
            startActivity(intent)
            drawerLayout.closeDrawer(drawerView)
        }

        findViewById<Button>(R.id.drawer_clip).setOnClickListener {
            binding.AmainToolbar.visibility = View.VISIBLE
            supportFragmentManager
                .beginTransaction()
                .replace(binding.AmainFrame.id, board_home())
                .commitAllowingStateLoss()
            binding.AmainToolbarTitle.text = "게시판"
            binding.AmainToolbarTitle.setTypeface(resources.getFont(R.font.sf_arabic_rounded), Typeface.BOLD)
            binding.AmainToolbarTitle.textSize = 20f
            drawerLayout.closeDrawer(drawerView)
        }

        findViewById<Button>(R.id.drawer_contents).setOnClickListener {
            binding.AmainToolbar.visibility = View.VISIBLE
            val intent = Intent(this, board_listpage::class.java)
            startActivity(intent)
            drawerLayout.closeDrawer(drawerView)

        }

        findViewById<Button>(R.id.drawer_class).setOnClickListener {
            binding.AmainToolbar.visibility = View.VISIBLE
            val intent = Intent(this, board_class_home::class.java)
            startActivity(intent)
            drawerLayout.closeDrawer(drawerView)
        }

        findViewById<Button>(R.id.drawer_mypage).setOnClickListener {
            binding.AmainToolbar.visibility = View.VISIBLE
            supportFragmentManager
                .beginTransaction()
                .replace(binding.AmainFrame.id, FragmentMypage())
                .commitAllowingStateLoss()
            binding.AmainToolbarTitle.text = "마이페이지"
            binding.AmainToolbarTitle.setTypeface(resources.getFont(R.font.sf_arabic_rounded), Typeface.BOLD)
            binding.AmainToolbarTitle.textSize = 20f
            drawerLayout.closeDrawer(drawerView)
        }

        findViewById<Button>(R.id.drawer_center).setOnClickListener {
            binding.AmainToolbar.visibility = View.VISIBLE
            val intent = Intent(this, ActivityCenter::class.java)
            startActivity(intent)
            drawerLayout.closeDrawer(drawerView)
        }

        findViewById<Button>(R.id.drawer_help).setOnClickListener {
            binding.AmainToolbar.visibility = View.VISIBLE
            val intent = Intent(this, ActivityHelp::class.java)
            startActivity(intent)
            drawerLayout.closeDrawer(drawerView)
        }

        findViewById<Button>(R.id.drawer_logout).setOnClickListener {
            if(user.getBoolean("autologin", false)){
                    editor.putBoolean("autologin", false)
                    editor.apply()
                }
                val intent = Intent(this, ActivityLogin::class.java)
                startActivity(intent)
                finish()
        }

        bottomNavigationView = binding.AmainBnv

        supportFragmentManager
            .beginTransaction()
            .replace(binding.AmainFrame.id, FragmentHome())
            .commitAllowingStateLoss()

        bottomNavigationView.selectedItemId = R.id.bnv_home

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bnv_pose -> {
                    binding.AmainToolbar.visibility = View.GONE
                    supportFragmentManager
                        .beginTransaction()
                        .replace(binding.AmainFrame.id, PoseshopMainFragment()) // Replace with your fragment
                        .commitAllowingStateLoss()
                    binding.AmainToolbarTitle.text = "포즈상점"
                    binding.AmainToolbarTitle.setTypeface(resources.getFont(R.font.sf_arabic_rounded), Typeface.BOLD)
                    binding.AmainToolbarTitle.textSize = 20f
                    true
                }
                R.id.bnv_camera -> {
                    binding.AmainToolbar.visibility = View.VISIBLE
                    supportFragmentManager
                        .beginTransaction()
                        .replace(binding.AmainFrame.id, FragmentHome())
                        .commitAllowingStateLoss()
                    binding.AmainToolbarTitle.text = "POSESTION"
                    binding.AmainToolbarTitle.setTypeface(resources.getFont(R.font.amatica_sc_bold), Typeface.BOLD)
                    binding.AmainToolbarTitle.textSize = 24f

                    val intent = Intent(this, CameraMainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.bnv_home -> {
                    binding.AmainToolbar.visibility = View.VISIBLE
                    supportFragmentManager
                        .beginTransaction()
                        .replace(binding.AmainFrame.id, FragmentHome())
                        .commitAllowingStateLoss()
                    binding.AmainToolbarTitle.text = "POSESTION"
                    binding.AmainToolbarTitle.setTypeface(resources.getFont(R.font.amatica_sc_bold), Typeface.BOLD)
                    binding.AmainToolbarTitle.textSize = 24f
                    true
                }
                R.id.bnv_board -> {
                    binding.AmainToolbar.visibility = View.VISIBLE
                    supportFragmentManager
                        .beginTransaction()
                        .replace(binding.AmainFrame.id, board_home())
                        .commitAllowingStateLoss()
                    binding.AmainToolbarTitle.text = "게시판"
                    binding.AmainToolbarTitle.setTypeface(resources.getFont(R.font.sf_arabic_rounded), Typeface.BOLD)
                    binding.AmainToolbarTitle.textSize = 20f
                    true
                }
                R.id.bnv_mypage -> {
                    binding.AmainToolbar.visibility = View.VISIBLE
                    supportFragmentManager
                        .beginTransaction()
                        .replace(binding.AmainFrame.id, FragmentMypage())
                        .commitAllowingStateLoss()
                    binding.AmainToolbarTitle.text = "마이페이지"
                    binding.AmainToolbarTitle.setTypeface(resources.getFont(R.font.sf_arabic_rounded), Typeface.BOLD)
                    binding.AmainToolbarTitle.textSize = 20f
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.empty_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(drawerView)
                return true
            }
//            R.id.mypage_help -> {
//                val intent = Intent(this, ActivityHelp::class.java)
//                startActivity(intent)
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}