package com.accenture.githubapps.features.detail.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.accenture.githubapps.data.model.User
import com.accenture.githubapps.data.model.UserDetail
import com.accenture.githubapps.features.detail.data.DetailRepository
import com.accenture.githubapps.features.home.data.HomeRepository
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val repository: DetailRepository): ViewModel() {
    // === List
    private var _userDetail = MutableLiveData<UserDetail>()

    fun getUserDetail(): MutableLiveData<UserDetail> {
        return _userDetail
    }

    fun setUserDetail(username: String) {
        repository.requestUserDetail(_userDetail, username)
    }
}