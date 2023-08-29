package com.example.posestion

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.posestion.connection.RetrofitClient
import java.util.Timer
import java.util.TimerTask

class AdapterHomeAD(private val adlist: MutableList<RetrofitClient.HomeAd>,
private val viewPager: ViewPager) : PagerAdapter() {

    private var currentPage = 0
    val handler = Handler(Looper.getMainLooper())
    private var timer: Timer? = null

    override fun getCount(): Int {
        return adlist.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.layout_ad, container, false)
        val imageUrl = adlist[position].bannerimage

        val adImage = view.findViewById<ImageView>(R.id.ad_image)

        Glide.with(view)
            .load(imageUrl)
            .into(adImage)

        adImage.clipToOutline = true
        container.addView(view)
        return view
    }

    fun startAutoScroll() {
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                handler.post {
                    if (currentPage == adlist.size) {
                        currentPage = 0
                    }
                    viewPager.setCurrentItem(currentPage++, true)
                }
            }
        }, 0, 3000) // 3초마다 실행
    }

    fun stopAutoScroll() {
        timer?.cancel()
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun getCurrentPage(): Int {
        return currentPage
    }

    fun setCurrentPage(position: Int) {
        currentPage = position
    }
}