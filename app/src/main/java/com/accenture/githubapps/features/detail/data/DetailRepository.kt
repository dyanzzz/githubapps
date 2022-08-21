package com.accenture.githubapps.features.detail.data

import androidx.lifecycle.MutableLiveData
import com.accenture.githubapps.api.AppService
import com.accenture.githubapps.data.model.User
import com.accenture.githubapps.data.model.UserDetail
import com.accenture.githubapps.data.resultMutableLiveDataRemoteByPass
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailRepository @Inject constructor(private val service: AppService){

    fun requestUserDetail(
        liveDataList: MutableLiveData<UserDetail>,
        username: String
    ) = resultMutableLiveDataRemoteByPass(
        liveDataList,
        service.getUserDetail(username)
    )

    fun requestUserFollowers(
        liveDataList: MutableLiveData<List<User>>,
        username: String,
        statusFollow: String
    ) = resultMutableLiveDataRemoteByPass(
        liveDataList,
        if (statusFollow == "Followers") service.getFollowers(username) else service.getFollowing(username)
    )
}