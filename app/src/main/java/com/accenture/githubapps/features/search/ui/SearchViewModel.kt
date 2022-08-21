package com.accenture.githubapps.features.search.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.accenture.githubapps.data.model.User
import com.accenture.githubapps.features.search.data.SearchRepository
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val repository: SearchRepository): ViewModel() {
    // === List
    private var _list = MutableLiveData<ArrayList<User>>()
    fun getResultListUser(): MutableLiveData<ArrayList<User>> = _list
    fun setResultListUser(query: String) {
        repository.searchUsers(_list, query)
    }
}