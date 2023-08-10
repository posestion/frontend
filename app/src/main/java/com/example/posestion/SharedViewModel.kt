package com.example.posestion

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    private val _imageList = MutableLiveData<List<Int>>()
    val imageList: LiveData<List<Int>> get() = _imageList
    private val _buttonStateList = MutableLiveData<List<Boolean>>()

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
    private val _removedImage = MutableLiveData<Int>()
    val removedImage: LiveData<Int> get() = _removedImage

    fun notifyImageRemoved(imageId: Int) {
        _removedImage.value = imageId
        Log.d("Image ID39", _removedImage.value.toString())
    }
}