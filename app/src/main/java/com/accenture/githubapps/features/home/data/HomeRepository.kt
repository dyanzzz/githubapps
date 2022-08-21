package com.accenture.githubapps.features.home.data

import androidx.lifecycle.MutableLiveData
import com.accenture.githubapps.api.AppService
import com.accenture.githubapps.data.model.User
import com.accenture.githubapps.data.resultMutableLiveDataRemoteByPass
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(private val service: AppService){

    fun requestList(
        liveDataList: MutableLiveData<List<User>>
    ) = resultMutableLiveDataRemoteByPass(
        liveDataList,
        service.getUsers()
    )
}