package com.accenture.githubapps.features.home.data

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
class HomeRepository @Inject constructor(private val service: AppService){

    fun requestListPopulars(liveDataList: MutableLiveData<List<User>>) {
        val call: Call<List<User>> = service.getUsers()
        call.enqueue(object : Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                liveDataList.postValue(null)
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                liveDataList.postValue(response.body())
            }
        })
    }
}