package com.accenture.githubapps.features.home.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.accenture.githubapps.data.model.User
import com.accenture.githubapps.features.home.data.HomeRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: HomeRepository): ViewModel() {
    // === List
    private var _listPopular = MutableLiveData<List<User>>()
    fun getListPopular(): MutableLiveData<List<User>> = _listPopular
    fun setListPopular() {
        repository.requestList(_listPopular)
    }

    fun getCheckFavoriteUsers() = repository.observeCheckFavoriteUser()
    fun getRemoveFavoriteUsers(username: String) = repository.observeRemoveFavoriteUser(username).also {
        repository.observeRemoveTempFavoriteUser(username)
    }
    fun getAllFavoriteUsers() = repository.observeAllFavoriteUser()
}