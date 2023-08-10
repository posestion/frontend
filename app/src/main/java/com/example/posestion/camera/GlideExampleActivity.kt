package com.example.posestion.camera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.posestion.R
import com.example.posestion.databinding.ActivityGlideExampleBinding

class GlideExampleActivity : AppCompatActivity() {

    private val viewBinding: ActivityGlideExampleBinding by lazy {
        ActivityGlideExampleBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val photoImageUrl = "https://picsum.photos/seed/picsum/500/300"

        Glide.with(this).load(photoImageUrl).into(viewBinding.emampleImage)
    }
}