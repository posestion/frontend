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
        Log.d("MyCustomViewModel11", addedImageIds.value.toString())
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
}