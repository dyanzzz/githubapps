package com.accenture.githubapps.features.home.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.accenture.githubapps.data.model.User
import com.accenture.githubapps.data.model.UserDetail
import com.accenture.githubapps.features.home.data.HomeRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: HomeRepository): ViewModel() {
    // === List
    private var _listPopular = MutableLiveData<List<User>>()

    fun getListPopular(): MutableLiveData<List<User>> {
        return _listPopular
    }

    fun setListPopular() {
        repository.requestListPopulars(_listPopular)
    }
}