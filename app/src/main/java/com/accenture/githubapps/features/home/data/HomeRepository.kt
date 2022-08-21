package com.accenture.githubapps.features.home.data

import androidx.lifecycle.MutableLiveData
import com.accenture.githubapps.api.AppService
import com.accenture.githubapps.data.model.FavoriteDao
import com.accenture.githubapps.data.model.User
import com.accenture.githubapps.data.resultMutableLiveDataRemoteByPass
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val service: AppService,
    private val dao: FavoriteDao,
){

    fun requestList(
        liveDataList: MutableLiveData<List<User>>
    ) = resultMutableLiveDataRemoteByPass(
        liveDataList,
        service.getUsers()
    )

    fun observeCheckFavoriteUser() = dao.checkTempFavorite()
    fun observeRemoveFavoriteUser(username: String) = dao.removeFavorite(username)
    fun observeRemoveTempFavoriteUser(username: String) = dao.removeTempFavorite(username)
    fun observeAllFavoriteUser() = dao.getFavoriteList()
}