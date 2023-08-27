package com.example.posestion

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.posestion.connection.RetrofitAPI
import com.example.posestion.connection.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCustomViewModel : ViewModel() {
    private val _addedImageIds: MutableLiveData<List<Int>> = MutableLiveData()
    val addedImageIds: LiveData<List<Int>> get() = _addedImageIds
    internal val imageUrls = mutableMapOf<Int, String>()
    private val _imageList = MutableLiveData<List<Int>>()
    val imageList: LiveData<List<Int>> get() = _imageList

    private lateinit var retrofitServiceWithToken: RetrofitAPI

    init {
        val token = MyApplication.user.getString("jwt", "").toString()
        retrofitServiceWithToken = RetrofitObject.getRetrofitServiceWithToken(token)
    }
    init {
        _addedImageIds.value = emptyList()
        _addedImageIds.observeForever {
            Log.d("MyCustomViewModel", "addedImageIds value changed: $it")
        }
        Log.d("MyCustomViewModel11", addedImageIds.value.toString())
    }

    fun addImageUrl(imageId: Int, imageUrl: String) {
        imageUrls[imageId] = imageUrl
        Log.d("MyRecyclerViewAdapter92", imageUrls.toString())
    }

    // 이미지 아이디에 해당하는 이미지 URL을 가져오는 메서드
    fun getImageUrlForId(imageId: Int): String? {
        Log.d("MyRecyclerViewAdapter93", imageUrls.toString())
        return imageUrls[imageId]
    }


    fun addImageId(imageId: Int) {
        Log.d("MyRecyclerViewAdapter32", _addedImageIds.value.toString())
        val currentList = _addedImageIds.value.orEmpty().toMutableList()
        Log.d("MyRecyclerViewAdapter33", currentList.toString())
        if (!currentList.contains(imageId)) {
            currentList.add(imageId)
            _addedImageIds.value = currentList
        }
        Log.d("MyRecyclerViewAdapter31",  imageId.toString())
        Log.d("MyRecyclerViewAdapter3", currentList.toString())
    }

    // 이미지 아이디 삭제
    fun removeImageId(imageId: Int) {
        Log.d("MyRecyclerViewAdapter72", _addedImageIds.value.toString())
        val currentList = _addedImageIds.value.orEmpty().toMutableList()
        val position = currentList.indexOf(imageId)

        if (position != -1) {
            currentList.removeAt(position)
            _addedImageIds.value = currentList
            removedImageIds.add(imageId)
            notifyImageRemoved(imageId)
        }
        Log.d("MyRecyclerViewAdapter7", currentList.toString())
        Log.d("MyRecyclerViewAdapter71", _addedImageIds.value.toString())
        Log.d("MyRecyclerViewAdapter73", addedImageIds.value.toString())
    }
    fun removeImageIds(imageIds: List<Int>) {
        val currentList = _addedImageIds.value.orEmpty().toMutableList()
        val removedIds = mutableListOf<Int>()

        for (imageId in imageIds) {
            if (currentList.contains(imageId)) {
                currentList.remove(imageId)
                removedIds.add(imageId)
            }
        }

        _addedImageIds.value = currentList
        removedImageIds.addAll(removedIds)
        notifyImagesRemoved(removedIds)
    }

    fun deleteImage(imageId: Int) {
        val imageUrl = getImageUrlForId(imageId) // 예시로 getImageUrlForId 함수를 정의해서 사용합니다.

        val currentList = _imageList.value.orEmpty().toMutableList()
        val position = currentList.indexOf(imageId)

        if (position != -1) {
            currentList.removeAt(position)
            _imageList.value = currentList

            notifyImageRemoved(imageId)

            // Remove image URL from imageUrls list
            imageUrls.remove(imageId)

            Log.d("RetrofitDelFavorite2", imageId.toString())
            // 호출하여 이미지를 서버에서 삭제하는 로직 추가
            retrofitServiceWithToken.posebasektdelete(imageId).enqueue(object :
                Callback<RetrofitClient.PoseBasketDelete> {
                override fun onResponse(
                    call: Call<RetrofitClient.PoseBasketDelete>,
                    response: Response<RetrofitClient.PoseBasketDelete>
                ) {
                    Log.d("RetrofitDelFavorite", "onResponse called")
                    if (response.isSuccessful) {
                        Log.d("RetrofitDelFavorite", "Image deletion successful")
                    }
                }

                override fun onFailure(call: Call<RetrofitClient.PoseBasketDelete>, t: Throwable) {
                    val errorMessage = "Call Failed: ${t.message}"
                    Log.d("RetrofitDelFavorite", errorMessage)
                }
            })
        }
    }


    override fun onCleared() {
        super.onCleared()
        _addedImageIds.removeObserver { /* Do something if needed */ }
    }
    private val _removedImage = MutableLiveData<Int>()
    val removedImage: LiveData<Int> get() = _removedImage
    private val removedImageIds = mutableListOf<Int>()

    private val _removedImages = MutableLiveData<List<Int>>()
    val removedImages: LiveData<List<Int>> get() = _removedImages
    private fun notifyImagesRemoved(imageIds: List<Int>) {
        _removedImages.value = imageIds
    }
    fun notifyImageRemoved(imageId: Int) {
        _removedImage.value = imageId
        Log.d("Image ID39", _removedImage.value.toString())
    }
    fun getRemovedImageIds(): List<Int> {
        return removedImageIds
    }
    fun getAllImageIds(): List<Int> {
        return _addedImageIds.value.orEmpty()
    }
    fun getAllImageUrls(): List<String> {
        return imageUrls.values.toList()
    }
}