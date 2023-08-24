package com.example.posestion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide

class AdapterHomeAD : PagerAdapter() {

    private var imagelist = mutableListOf<String>()

    override fun getCount(): Int {
        return imagelist.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.layout_ad, container, false)
        val imageUrl = imagelist[position]

        val adImage = view.findViewById<ImageView>(R.id.ad_image)

        Glide.with(view)
            .load(imageUrl)
            .into(adImage)

        adImage.clipToOutline = true
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun getimage(image: MutableList<String>){
        imagelist = image
    }
}