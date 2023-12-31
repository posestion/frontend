package com.example.posestion

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.posestion.MyApplication.Companion.adlist
import com.example.posestion.MyApplication.Companion.homeclasslist
import com.example.posestion.MyApplication.Companion.homehotclasslist
import com.example.posestion.MyApplication.Companion.homeposelist
import com.example.posestion.MyApplication.Companion.homestarclasslist
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentHome : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPager: ViewPager
    private lateinit var adadapter: AdapterHomeAD
    private lateinit var pagetext: TextView
    private lateinit var hotclassrecyclerView: RecyclerView
    private lateinit var hotclassadapter: AdapterHomehotclass
    private lateinit var poserecyclerView: RecyclerView
    private lateinit var poseadapter: AdapterHomepose
    private lateinit var myclassrecyclerView: RecyclerView
    private lateinit var myclassadapter: AdapterMypageClass
    private lateinit var starclassrecyclerView: RecyclerView
    private lateinit var starclassadapter: AdapterMypageClass
    private val user = MyApplication.user
    private var currentPage = 0
    private var page = 1

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        pagetext = binding.FhomeTextPage
        pagetext.text = "1/${adlist.size}"

        viewPager = binding.FhomeViewpager
        adadapter = AdapterHomeAD(adlist, viewPager)

        viewPager.adapter = adadapter
        adadapter.startAutoScroll()

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                adadapter.setCurrentPage(position)
                page = adadapter.getCurrentPage()+1
                pagetext.text = "${page}/${adlist.size}"
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        viewPager.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    adadapter.stopAutoScroll()
                }
                MotionEvent.ACTION_MOVE -> {
                    // 터치 이동 이벤트 처리
                }
                MotionEvent.ACTION_UP -> {
                    adadapter.startAutoScroll()
                }
            }
            true
        }

        viewPager.setCurrentItem(currentPage)

        hotclassrecyclerView = binding.FhomeRvHotclass
        hotclassadapter = AdapterHomehotclass(homehotclasslist, requireContext())

        hotclassrecyclerView.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        hotclassrecyclerView.adapter = hotclassadapter
        hotclassadapter.notifyDataSetChanged()

        poserecyclerView = binding.FhomeRvPose
        poseadapter = AdapterHomepose(homeposelist, requireContext())

        poserecyclerView.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        poserecyclerView.adapter = poseadapter
        poseadapter.notifyDataSetChanged()

        val classlist = mutableListOf<RetrofitClient.mypageclass>()
        if(homeclasslist.size != 0){
            if(homeclasslist.size <= 3){
                for(i in 0..homeclasslist.size){
                    classlist.add(homeclasslist[i])
                }
            }else{
                for(i in 0..2){
                    classlist.add(homeclasslist[i])
                }
            }
        }

        myclassrecyclerView = binding.FhomeRvClass
        myclassadapter = AdapterMypageClass(classlist, requireContext())

        myclassrecyclerView.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        myclassrecyclerView.adapter = myclassadapter
        myclassadapter.notifyDataSetChanged()

        val classstarlist = mutableListOf<RetrofitClient.mypageclass>()
        if(homestarclasslist.size != 0){
            if(homeclasslist.size <= 3){
                for(i in 0..homestarclasslist.size){
                    classstarlist.add(homestarclasslist[i])
                }
            }else{
                for(i in 0..2){
                    classstarlist.add(homestarclasslist[i])
                }
            }
        }
        starclassrecyclerView = binding.FhomeRvStarclass
        starclassadapter = AdapterMypageClass(classlist, requireContext())

        starclassrecyclerView.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        starclassrecyclerView.adapter = starclassadapter
        starclassadapter.notifyDataSetChanged()

        //수강중인 강의 전체보기
        binding.FhomeBtnClassall.setOnClickListener {

        }

        //인기있는 포즈 전체보기
        binding.FhomeBtnPoseall.setOnClickListener {

        }

        //HOT강의 전체보기
        binding.FhomeBtnHotclassall.setOnClickListener {

        }

        //찜한 강의 전체보기
        binding.FhomeBtnStarclass.setOnClickListener {

        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adadapter.stopAutoScroll()
    }
}