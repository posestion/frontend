package com.example.posestion

import android.content.Intent
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.posestion.databinding.FragmentMypageBinding

class FragmentMypage : Fragment() {

    private lateinit var binding: FragmentMypageBinding
    private lateinit var nickname : TextView
    private lateinit var recyclerViewcontents: RecyclerView
    private lateinit var recyclerViewclass: RecyclerView
    private val user = MyApplication.user
    private val ContentsData = mutableListOf<DataContents>()
    private val ClassData = mutableListOf<DataClass>()
    private val expert = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(layoutInflater)

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

        nickname = binding.fmypageTextNick
        nickname.text = user.getString("id", "").toString()

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