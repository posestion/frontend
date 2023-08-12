package com.example.posestion

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyCustomViewModel : ViewModel() {
    private val _addedImageIds: MutableLiveData<List<Int>> = MutableLiveData()
    val addedImageIds: LiveData<List<Int>> get() = _addedImageIds

    init {
        _addedImageIds.value = emptyList()
        _addedImageIds.observeForever {
            Log.d("MyCustomViewModel", "addedImageIds value changed: $it")
        }
    }

    fun addImageId(imageId: Int) {
        val currentList = _addedImageIds.value.orEmpty().toMutableList()
        if (!currentList.contains(imageId)) {
            currentList.add(imageId)
            _addedImageIds.value = currentList
        }
        Log.d("MyRecyclerViewAdapter31",  imageId.toString())
        Log.d("MyRecyclerViewAdapter3", currentList.toString())
    }

    // 이미지 아이디 삭제
    fun removeImageId(position: Int) {
        val currentList = _addedImageIds.value.orEmpty().toMutableList()
        if (position in 0 until currentList.size) {
            currentList.removeAt(position)
            _addedImageIds.value = currentList
        }
    }
    override fun onCleared() {
        super.onCleared()
        _addedImageIds.removeObserver { /* Do something if needed */ }
    }
}