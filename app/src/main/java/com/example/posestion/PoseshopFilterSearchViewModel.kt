package com.example.posestion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.posestion.connection.RetrofitClient

class PoseshopFilterSearchViewModel: ViewModel() {
    private val _searchResults = MutableLiveData<List<RetrofitClient.PoseSearch>>()
    val searchResults: LiveData<List<RetrofitClient.PoseSearch>> = _searchResults

    fun setSearchResults(results: List<RetrofitClient.PoseSearch>) {
        _searchResults.value = results
    }

    fun getSearchResults(): List<RetrofitClient.PoseSearch>? {
        return _searchResults.value
    }
}