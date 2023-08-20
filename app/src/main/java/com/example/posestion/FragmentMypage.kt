package com.example.posestion

import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.FragmentMypageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentMypage : Fragment() {

    private lateinit var binding: FragmentMypageBinding
    private lateinit var recyclerViewcontents: RecyclerView
    private lateinit var recyclerViewclass: RecyclerView
    private val ContentsData = mutableListOf<DataContents>()
    private val ClassData = mutableListOf<DataClass>()
    private var expert = false
    private val user = MyApplication.user
    private val editor = user.edit()
    private var token = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(layoutInflater)

        token = user.getString("jwt", "").toString()
        expert = user.getInt("expert", 0) != 0

        if(expert){
            binding.fmypageViewGone.visibility = View.VISIBLE
            binding.fmypageTextGone.visibility = View.VISIBLE
            binding.fmypageRvClass.visibility = View.VISIBLE
            binding.fmypageBtnClass.visibility = View.VISIBLE

            val dp28 = (28 * Resources.getSystem().displayMetrics.density).toInt()

            val itemSpacingDecoration = ItemSpacingDecoration(dp28)

            recyclerViewclass = binding.fmypageRvClass
            recyclerViewclass.addItemDecoration(itemSpacingDecoration)
            recyclerViewclass.layoutManager =
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
            recyclerViewclass.adapter = AdapterMypageClass(ClassData, resources)
        }

        binding.fmypagePostNum.text = "${user.getInt("post", 0)}"
        binding.fmypageFollowNum.text = "${user.getInt("follower", 0)}"
        binding.fmypageFollowingNum.text = "${user.getInt("following", 0)}"
        binding.fmypageTextNick.text = "${user.getString("nick", "")}"

        val imageUrl = user.getString("profileimage", "")
        val imageView = binding.fmypageProfile

        Glide.with(requireContext())
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)

        //새로고침 했을 때
        binding.fmypageBtnRefresh.setOnClickListener {
            binding.fmypageScroll.visibility = View.GONE
            binding.fmypageLoading.visibility = View.VISIBLE

            val call = RetrofitObject.getRetrofitService.mypage(token)
            call.enqueue(object : Callback<RetrofitClient.Responsemypage> {
                override fun onResponse(call: Call<RetrofitClient.Responsemypage>, response: Response<RetrofitClient.Responsemypage>) {
                    if (response.isSuccessful) {
                        val response = response.body()
                        if(response != null){
                            Log.d("Retrofit", response.message)
                            if(response.isSuccess){
                                val mypage = response.result[0]
                                expert = mypage.expert != 0
                                if(expert){
                                    binding.fmypageViewGone.visibility = View.VISIBLE
                                    binding.fmypageTextGone.visibility = View.VISIBLE
                                    binding.fmypageRvClass.visibility = View.VISIBLE
                                    binding.fmypageBtnClass.visibility = View.VISIBLE

                                    val dp28 = (28 * Resources.getSystem().displayMetrics.density).toInt()

                                    val itemSpacingDecoration = ItemSpacingDecoration(dp28)

                                    recyclerViewclass = binding.fmypageRvClass
                                    recyclerViewclass.addItemDecoration(itemSpacingDecoration)
                                    recyclerViewclass.layoutManager =
                                        StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
                                    recyclerViewclass.adapter = AdapterMypageClass(ClassData, resources)
                                }
                                binding.fmypagePostNum.text = "${mypage.post}"
                                binding.fmypageFollowNum.text = "${mypage.follower}"
                                binding.fmypageFollowingNum.text = "${mypage.following}"
                                binding.fmypageTextNick.text = "${mypage.nick}"

                                val imageUrl = mypage.profile
                                val imageView = binding.fmypageProfile

                                Glide.with(requireContext())
                                    .load(imageUrl)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(imageView)

                                editor.putInt("expert", mypage.expert)
                                editor.putString("profileimage", mypage.profile)
                                editor.putString("nick", mypage.nick)
                                editor.putInt("post", mypage.post)
                                editor.putInt("following", mypage.following)
                                editor.putInt("follower", mypage.follower)
                                editor.apply()
                            }
                            binding.fmypageScroll.visibility = View.VISIBLE
                            binding.fmypageLoading.visibility = View.GONE
                        }
                    }
                }

                override fun onFailure(call: Call<RetrofitClient.Responsemypage>, t: Throwable) {
                    val errorMessage = "Call Failed: ${t.message}"
                    Log.d("Retrofit", errorMessage)
                }
            })
        }

        //리사이클러뷰 컨텐츠
        recyclerViewcontents = binding.fmypageRvContent
        recyclerViewcontents.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerViewcontents.adapter = AdapterMypageContents(ContentsData, resources)


        binding.fmypageBtnPen.setOnClickListener {
            val intent = Intent(activity, ActivityChangeUser::class.java)
            startActivity(intent)
        }

        binding.fmypageBtnContent.setOnClickListener {
            val intent = Intent(activity, ActivityContents::class.java)
            startActivity(intent)
        }

        binding.fmypageBtnClass.setOnClickListener {
            val intent = Intent(activity, ActivityClass::class.java)
            startActivity(intent)
        }

        binding.fmypageBtnJim.setOnClickListener {
            val intent = Intent(activity, ActivityJim::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    //사진 28dp로 강제 변환
    class ItemSpacingDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view)
            if (position == parent.adapter?.itemCount?.minus(1)) {
                outRect.set(0, 0, 0, 0)
            } else {
                outRect.set(0, 0, spacing, 0)
            }
        }
    }
}