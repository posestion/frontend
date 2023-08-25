package com.example.posestion

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posestion.databinding.ActivityBoardClassHomeBinding
import com.example.posestion.databinding.ActivityBoardListpageBinding
import com.example.posestion.databinding.FragmentBoardHomeBinding

class board_home : Fragment() {
    private lateinit var binding: FragmentBoardHomeBinding

    private val listpage = listOf(
        listpage("전주 여행 가자마자 찍은 사진인데", "어때? 괜찮게 나왔어? 나 이거 인스타에 올리고 싶은데 나름 감성있는 정도라고 봐도 되는 수준이야? 댓글이나 좋아요로 의견 알려줘!!",false, 3, 14),
        listpage("나 이정도면 괜찮은거야?", "시진 찍는거 넘 어렵다 ㅠ", false,2, 3),
        listpage("이 사진 잘 나온 편임?", "나 카톡 프사할거야 ㄹㅇ 진지하게 예의", false,19, 4),
        listpage("전주 여행 가자마자 찍은 사진인데", "어때? 괜찮게 나왔어? 나 이거 인스타에 올리고 싶은데 나름 감성있는 정도라고 봐도 되는 수준이야? 댓글이나 좋아요로 의견 알려줘!!", false,3, 14),
        listpage("나 이정도면 괜찮은거야?", "시진 찍는거 넘 어렵다 ㅠ", false,2, 3),
        listpage("이 사진 잘 나온 편임?", "나 카톡 프사할거야 ㄹㅇ 진지하게 예의", false,19, 4),
        listpage("전주 여행 가자마자 찍은 사진인데", "어때? 괜찮게 나왔어? 나 이거 인스타에 올리고 싶은데 나름 감성있는 정도라고 봐도 되는 수준이야? 댓글이나 좋아요로 의견 알려줘!!", false,3, 14),
        listpage("전주 여행 가자마자 찍은 사진인데", "어때? 괜찮게 나왔어? 나 이거 인스타에 올리고 싶은데 나름 감성있는 정도라고 봐도 되는 수준이야? 댓글이나 좋아요로 의견 알려줘!!", false,3, 14),
        listpage("나 이정도면 괜찮은거야?", "시진 찍는거 넘 어렵다 ㅠ", false,2, 3),
        listpage("이 사진 잘 나온 편임?", "나 카톡 프사할거야 ㄹㅇ 진지하게 예의", false,19, 4),
        listpage("전주 여행 가자마자 찍은 사진인데", "어때? 괜찮게 나왔어? 나 이거 인스타에 올리고 싶은데 나름 감성있는 정도라고 봐도 되는 수준이야? 댓글이나 좋아요로 의견 알려줘!!", false,3, 14),
        listpage("전주 여행 가자마자 찍은 사진인데", "어때? 괜찮게 나왔어? 나 이거 인스타에 올리고 싶은데 나름 감성있는 정도라고 봐도 되는 수준이야? 댓글이나 좋아요로 의견 알려줘!!", false,3, 14),
        listpage("나 이정도면 괜찮은거야?", "시진 찍는거 넘 어렵다 ㅠ", false,2, 3),
        listpage("이 사진 잘 나온 편임?", "나 카톡 프사할거야 ㄹㅇ 진지하게 예의", false,19, 4),
        listpage("전주 여행 가자마자 찍은 사진인데", "어때? 괜찮게 나왔어? 나 이거 인스타에 올리고 싶은데 나름 감성있는 정도라고 봐도 되는 수준이야? 댓글이나 좋아요로 의견 알려줘!!", false,3, 14),
        listpage("전주 여행 가자마자 찍은 사진인데", "어때? 괜찮게 나왔어? 나 이거 인스타에 올리고 싶은데 나름 감성있는 정도라고 봐도 되는 수준이야? 댓글이나 좋아요로 의견 알려줘!!", false,3, 14),
        listpage("나 이정도면 괜찮은거야?", "시진 찍는거 넘 어렵다 ㅠ", false,2, 3),
        listpage("이 사진 잘 나온 편임?", "나 카톡 프사할거야 ㄹㅇ 진지하게 예의", false,19, 4),

        )
    private val hotclass = listOf(
        hotclass(false,"인물 사진 잘 찍는 법 종결합니다.",R.drawable.rectangle_142),
        hotclass(true,"똥손 탈출하는 전신샷 잘 찍는법",R.drawable.rectangle_62),
        hotclass(false,"인물 사진 잘 찍는 법 종결합니다.",R.drawable.rectangle_62),
        hotclass(false,"똥손 탈출하는 전신샷 잘 찍는법",R.drawable.rectangle_142),
        hotclass(false,"인물 사진 잘 찍는 법 종결합니다.",R.drawable.rectangle_142),
        hotclass(true,"똥손 탈출하는 전신샷 잘 찍는법",R.drawable.rectangle_62),
        hotclass(true,"인물 사진 잘 찍는 법 종결합니다.",R.drawable.rectangle_62),
        hotclass(true,"똥손 탈출하는 전신샷 잘 찍는법",R.drawable.rectangle_142),
        hotclass(true,"인물 사진 잘 찍는 법 종결합니다.",R.drawable.rectangle_142),
        hotclass(true,"똥손 탈출하는 전신샷 잘 찍는법",R.drawable.rectangle_62),
    )

    private val home_10photo = listOf(
        home_10photo(R.drawable.board_home_10photo_ex,"인물 구도 예쁘게 잘 찍는 방법","인플루언서"),
        home_10photo(R.drawable.board_home_10photo_ex1,"유럽여행 가서 인생짤 건지는 방법","인플루언서"),
        home_10photo(R.drawable.board_home_10photo_ex,"인물 구도 예쁘게 잘 찍는 방법","인플루언서"),
        home_10photo(R.drawable.board_home_10photo_ex1,"유럽여행 가서 인생짤 건지는 방법","인플루언서"),
        home_10photo(R.drawable.board_home_10photo_ex,"인물 구도 예쁘게 잘 찍는 방법","인플루언서"),
        home_10photo(R.drawable.board_home_10photo_ex1,"유럽여행 가서 인생짤 건지는 방법","인플루언서"),
        home_10photo(R.drawable.board_home_10photo_ex,"인물 구도 예쁘게 잘 찍는 방법","인플루언서"),
        home_10photo(R.drawable.board_home_10photo_ex1,"유럽여행 가서 인생짤 건지는 방법","인플루언서"),
        home_10photo(R.drawable.board_home_10photo_ex,"인물 구도 예쁘게 잘 찍는 방법","인플루언서"),
        home_10photo(R.drawable.board_home_10photo_ex1,"유럽여행 가서 인생짤 건지는 방법","인플루언서")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardHomeBinding.inflate(layoutInflater)


        initializeViews()
        binding.boardHomeListpageBtn.setOnClickListener {
            val intent = Intent(activity,board_listpage::class.java)
            startActivity(intent)
        }
        binding.boardHomeClassBtn.setOnClickListener {
            val intent = Intent(requireContext(),board_class_home::class.java)
            startActivity(intent)
        }

        return binding.root
    }
    private fun initializeViews(){
        val LinearLayoutManager0 = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        val LinearLayoutManager1 = LinearLayoutManager(requireContext())
        val LinearLayoutManager2 = GridLayoutManager(requireContext(),2)
        binding.boardHome10photoList.layoutManager = LinearLayoutManager0
        binding.boardHome10photoList.adapter = home_10photo_adapter(home_10photo)
        binding.boardHomeListpageList.layoutManager = LinearLayoutManager1
        binding.boardHomeListpageList.adapter = home_listpage_adapter(listpage)
        binding.boardHomeClassList.layoutManager = LinearLayoutManager2
        binding.boardHomeClassList.adapter = home_hotclass_list_adapter(hotclass)

    }
}