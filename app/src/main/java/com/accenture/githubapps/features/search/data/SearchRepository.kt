package com.accenture.githubapps.features.search.data

import androidx.lifecycle.MutableLiveData
import com.accenture.githubapps.api.AppService
import com.accenture.githubapps.data.model.User
import com.accenture.githubapps.data.resultMutableLiveDataRemoteByPassSearch
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val service: AppService,
){

    fun searchUsers(
        liveDataList: MutableLiveData<ArrayList<User>>,
        query: String,
    ) = resultMutableLiveDataRemoteByPassSearch(
        liveDataList,
        service.getSearchUser(query)
    )

}