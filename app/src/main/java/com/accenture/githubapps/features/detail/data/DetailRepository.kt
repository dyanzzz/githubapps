package com.accenture.githubapps.features.detail.data

import androidx.lifecycle.MutableLiveData
import com.accenture.githubapps.api.AppService
import com.accenture.githubapps.data.model.User
import com.accenture.githubapps.data.model.UserDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailRepository @Inject constructor(private val service: AppService){

    fun requestUserDetail(liveDataList: MutableLiveData<UserDetail>, username: String) {
        val call: Call<UserDetail> = service.getUserDetail(username)
        call.enqueue(object : Callback<UserDetail> {
            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                liveDataList.postValue(null)
            }

            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                liveDataList.postValue(response.body())
            }
        })
    }
}