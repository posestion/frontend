package com.example.posestion

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.posestion.MyApplication.Companion.user
import com.example.posestion.connection.RetrofitAPI
import com.example.posestion.connection.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SharedViewModel : ViewModel() {

    private lateinit var retrofitServiceWithToken: RetrofitAPI

    init {
        val token = user.getString("jwt", "").toString()
        retrofitServiceWithToken = RetrofitObject.getRetrofitServiceWithToken(token)
    }

    private val _imageList = MutableLiveData<List<Int>>()
    val imageList: LiveData<List<Int>> get() = _imageList
    private val _buttonStateList = MutableLiveData<List<Boolean>>()

    private val imageUrls = mutableMapOf<Int, String>()
    private val imageTitles = mutableMapOf<Int, String>()
    private val imageContents = mutableMapOf<Int, String>()
    private val imageTags = mutableMapOf<Int, List<String>?>()

    // 이미지 아이디와 이미지 URL을 매핑해서 맵에 추가하는 메서드
    fun addImageUrl(imageId: Int, imageUrl: String,imageTitle: String, imageContent: String, tagNames: List<String>?) {
        imageUrls[imageId] = imageUrl
        imageTitles[imageId] = imageTitle
        imageContents[imageId] = imageContent
        imageTags[imageId] = tagNames
    }

    // 이미지 아이디에 해당하는 이미지 URL을 가져오는 메서드
    fun getImageUrlForId(imageId: Int): String? {
        return imageUrls[imageId]
    }
    fun getImageTitleForId(imageId: Int): String? {
        return imageTitles[imageId]
    }
    fun getImageContentForId(imageId: Int): String? {
        return imageContents[imageId]
    }
    fun getImageTagsForId(imageId: Int): List<String>? {
        return imageTags[imageId]
    }
    // 새로운 이미지를 이미지 리스트에 추가하는 메서드
    fun addNewImage(imageId: Int) {
        _imageList.postValue(_imageList.value.orEmpty() + imageId)

        val currentButtonStateList = _buttonStateList.value.orEmpty().toMutableList()
        currentButtonStateList.add(false) // Assuming initially the button is not filled for the new image
        _buttonStateList.value = currentButtonStateList
    }
    fun removeImage(imageId: Int) {
        val currentList = _imageList.value.orEmpty().toMutableList()
        val position = currentList.indexOf(imageId)

        if (position != -1) {
            currentList.removeAt(position)
            _imageList.value = currentList

            val currentButtonStateList = _buttonStateList.value.orEmpty().toMutableList()
            currentButtonStateList.removeAt(position)
            _buttonStateList.value = currentButtonStateList

            notifyImageRemoved(imageId)
        }

        Log.d("Image ID38", imageId.toString())
    }

    fun deleteImage(imageId: Int) {
        val imageUrl = getImageUrlForId(imageId) // 예시로 getImageUrlForId 함수를 정의해서 사용합니다.

        val currentList = _imageList.value.orEmpty().toMutableList()
        val position = currentList.indexOf(imageId)

        if (position != -1) {
            currentList.removeAt(position)
            _imageList.value = currentList

            val currentButtonStateList = _buttonStateList.value.orEmpty().toMutableList()
            currentButtonStateList.removeAt(position)
            _buttonStateList.value = currentButtonStateList

            notifyImageRemoved(imageId)

            // Remove image URL from imageUrls list
            imageUrls.remove(imageId)
            imageTags.remove(imageId)

            // 호출하여 이미지를 서버에서 삭제하는 로직 추가
            retrofitServiceWithToken.posedelfavorite(imageId).enqueue(object :
                Callback<RetrofitClient.PoseDelfavorite> {
                override fun onResponse(
                    call: Call<RetrofitClient.PoseDelfavorite>,
                    response: Response<RetrofitClient.PoseDelfavorite>
                ) {
                    if (response.isSuccessful) {
                        // 이미지 삭제 성공
                    }
                }

                override fun onFailure(call: Call<RetrofitClient.PoseDelfavorite>, t: Throwable) {
                    val errorMessage = "Call Failed: ${t.message}"
                    Log.d("RetrofitDelFavorite", errorMessage)
                }
            })
        }
    }
    private val _removedImage = MutableLiveData<Int>()
    val removedImage: LiveData<Int> get() = _removedImage

    fun notifyImageRemoved(imageId: Int) {
        _removedImage.value = imageId
        Log.d("Image ID39", _removedImage.value.toString())
    }

    private val _searchResults = MutableLiveData<List<RetrofitClient.PoseSearch>>()
    val searchResults: LiveData<List<RetrofitClient.PoseSearch>> = _searchResults

    fun setSearchResults(results: List<RetrofitClient.PoseSearch>) {
        _searchResults.value = results
    }

    private val _searchhotResults = MutableLiveData<List<RetrofitClient.PoseSearchHot>>()
    val searchhotResults: LiveData<List<RetrofitClient.PoseSearchHot>> = _searchhotResults

    fun setSearchHotResults(results: List<RetrofitClient.PoseSearchHot>) {
        _searchhotResults.value = results
    }

    private val _filterDates = MutableLiveData<List<RetrofitClient.PoseFilterdate>>()
    val filterDates: LiveData<List<RetrofitClient.PoseFilterdate>> get() = _filterDates

    fun setFilterDates(dates: List<RetrofitClient.PoseFilterdate>) {
        _filterDates.value = dates
        Log.d("Retrofit87",  _filterDates.value.toString())
    }

    fun getFilterDates(): List<RetrofitClient.PoseFilterdate>? {
        Log.d("Retrofit86", filterDates.value.toString())
        return filterDates.value
    }


    private val _filterPopulates = MutableLiveData<List<RetrofitClient.PoseFilterpopular>>()
    val filterPopulates: LiveData<List<RetrofitClient.PoseFilterpopular>> get() = _filterPopulates

    fun setFilterPopulates(populates: List<RetrofitClient.PoseFilterpopular>?) {
        _filterPopulates.value = populates
    }

    fun getFilterPopulates(): List<RetrofitClient.PoseFilterpopular>? {
        return filterPopulates.value
    }

    private var resultBundle: Bundle? = null

    fun getResultBundle(): Bundle? {
        return resultBundle
    }

    fun setResultBundle(bundle: Bundle) {
        resultBundle = bundle
    }
}