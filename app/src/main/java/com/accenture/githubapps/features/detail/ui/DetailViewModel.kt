package com.accenture.githubapps.features.detail.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.accenture.githubapps.data.model.User
import com.accenture.githubapps.data.model.FavoriteUser
import com.accenture.githubapps.data.model.Follower
import com.accenture.githubapps.features.detail.data.DetailRepository
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val repository: DetailRepository): ViewModel() {
    // === List
    private var _userDetail = MutableLiveData<FavoriteUser>()
    fun getUserDetail(): MutableLiveData<FavoriteUser> = _userDetail
    fun setUserDetail(username: String) {
        repository.requestUserDetail(_userDetail, username)
    }

    private var _user = MutableLiveData<List<User>>()
    fun getUserFollow(): MutableLiveData<List<User>> = _user
    fun setUserFollow(isFollower: Boolean, username: String) {
        repository.requestUserFollowers(_user, username, isFollower)
    }

    fun postUserFavorite(favoriteUser: FavoriteUser) {
        repository.observeInsertUserFavorite(favoriteUser).also {
            repository.observeRemoveTempFavoriteUser(favoriteUser.login)
        }
    }

    fun removeUserFavorite(username: String) {
        repository.observeRemoveUserFavorite(username).also {
            repository.observeRemoveFollowerFavoriteUser(username)
        }
    }

    fun checkUserDetailFavorite(username: String) = repository.observeCheckDetailUserFavorite(username)

    fun getFavoriteUserDetail(username: String) = repository.observeFavoriteUserDetail(username)

    fun postFollowerFavoriteUser(follower: Follower) {
        repository.observeInsertFollowerFavoriteUser(follower)
    }

    fun getFollowerFavoriteUser(userName: String) = repository.observeFollowerFavoriteUser(userName)
}