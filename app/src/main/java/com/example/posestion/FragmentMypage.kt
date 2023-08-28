package com.example.posestion

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.posestion.MyApplication.Companion.classlist
import com.example.posestion.MyApplication.Companion.contentslist
import com.example.posestion.MyApplication.Companion.poselist
import com.example.posestion.connection.RetrofitClient
import com.example.posestion.databinding.FragmentMypageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentMypage : Fragment() {

    private lateinit var binding: FragmentMypageBinding
    private lateinit var recyclerViewcontents: RecyclerView
    private lateinit var recyclerViewclass: RecyclerView
    private lateinit var recyclerViewpose: RecyclerView
    private lateinit var classadapter: AdapterMypageClass
    private lateinit var contentsadapter: AdapterMypageContents
    private lateinit var poseadapter: AdapterMypagePose
    lateinit var activityMain: ActivityMain
    private var expert = false
    private val user = MyApplication.user
    private val editor = user.edit()
    private var token = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activityMain = context as ActivityMain
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(layoutInflater)

        token = user.getString("jwt", "").toString()
        expert = user.getInt("expert", 0) != 0

        //내가 올린 강의
        if(expert){
            binding.fmypageViewGone.visibility = View.VISIBLE
            binding.fmypageTextGone.visibility = View.VISIBLE
            binding.fmypageRvClass.visibility = View.VISIBLE
            binding.fmypageBtnClass.visibility = View.VISIBLE
            binding.fmypageImage3.visibility = View.VISIBLE

            val dp28 = (28 * Resources.getSystem().displayMetrics.density).toInt()

            val itemSpacingDecoration = ItemSpacingDecoration(dp28)

            classadapter = AdapterMypageClass(classlist, resources, activityMain)
            recyclerViewclass = binding.fmypageRvClass
            recyclerViewclass.addItemDecoration(itemSpacingDecoration)
            recyclerViewclass.layoutManager =
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
            recyclerViewclass.adapter = classadapter
            classadapter.notifyDataSetChanged()
        }

        //내가 올린 컨텐츠
        contentsadapter = AdapterMypageContents(contentslist, resources, requireContext())
        recyclerViewcontents = binding.fmypageRvContent
        recyclerViewcontents.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerViewcontents.adapter = contentsadapter
        contentsadapter.notifyDataSetChanged()

        //내가 올린 포즈
        poseadapter = AdapterMypagePose(poselist, resources, requireContext())
        recyclerViewpose = binding.fmypageRvPose
        recyclerViewpose.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        recyclerViewpose.adapter = poseadapter
        poseadapter.notifyDataSetChanged()

        //마이페이지 기본
        binding.fmypagePostNum.text = "${user.getInt("post", 0)}"
        binding.fmypageFollowNum.text = "${user.getInt("follower", 0)}"
        binding.fmypageFollowingNum.text = "${user.getInt("following", 0)}"
        binding.fmypageTextNick.text = "${user.getString("nick", "")}"

        val imageUrl = user.getString("profileimage", "")
        val imageView = binding.fmypageProfile

        Glide.with(requireContext())
            .load(imageUrl)
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
                                    binding.fmypageImage3.visibility = View.VISIBLE

                                    if(mypage.mypageclass != null){
                                        classlist = mypage.mypageclass
                                    }

                                    val dp28 = (28 * Resources.getSystem().displayMetrics.density).toInt()

                                    val itemSpacingDecoration = ItemSpacingDecoration(dp28)

                                    classadapter = AdapterMypageClass(classlist, resources, activityMain)
                                    recyclerViewclass = binding.fmypageRvClass
                                    recyclerViewclass.addItemDecoration(itemSpacingDecoration)
                                    recyclerViewclass.layoutManager =
                                        StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
                                    recyclerViewclass.adapter = classadapter
                                    classadapter.notifyDataSetChanged()
                                }
                                binding.fmypagePostNum.text = "${mypage.post}"
                                binding.fmypageFollowNum.text = "${mypage.follower}"
                                binding.fmypageFollowingNum.text = "${mypage.following}"
                                binding.fmypageTextNick.text = "${mypage.nick}"

                                val imageUrl = mypage.profile
                                val imageView = binding.fmypageProfile

                                Glide.with(requireContext())
                                    .load(imageUrl)
                                    .into(imageView)

                                if(mypage.myContent != null){
                                    contentslist = mypage.myContent
                                    contentsadapter.notifyDataSetChanged()
                                }
                                if(mypage.poseDrawer != null){
                                    poselist = mypage.poseDrawer
                                    poseadapter.notifyDataSetChanged()
                                }

                                editor.putInt("expert", mypage.expert)
                                editor.putString("profileimage", mypage.profile)
                                editor.putString("nick", mypage.nick)
                                editor.putInt("post", mypage.post)
                                editor.putInt("following", mypage.following)
                                editor.putInt("follower", mypage.follower)
                                editor.apply()

                                binding.fmypagePostNum.text = "${user.getInt("post", 0)}"
                                binding.fmypageFollowNum.text = "${user.getInt("follower", 0)}"
                                binding.fmypageFollowingNum.text = "${user.getInt("following", 0)}"
                                binding.fmypageTextNick.text = "${user.getString("nick", "")}"
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

        binding.fmypageBtnPen.setOnClickListener {
            val intent = Intent(activity, ActivityChangeUser::class.java)
            startActivity(intent)
        }

        binding.fmypageBtnContent.setOnClickListener {
            val intent = Intent(activity, ActivityMyContents::class.java)
            startActivity(intent)
        }

        binding.fmypageBtnClass.setOnClickListener {
            val intent = Intent(activity, ActivityClass::class.java)
            startActivity(intent)
        }

        binding.fmypageBtnPose.setOnClickListener {
            val intent = Intent(activity, ActivityPose::class.java)
            startActivity(intent)
        }

        binding.fmypageBtnBox.setOnClickListener {
            val intent = Intent(activity, ActivityBox::class.java)
            startActivity(intent)
        }

        return binding.root
    }

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