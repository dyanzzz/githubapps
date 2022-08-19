package com.accenture.githubapps.api

import com.accenture.githubapps.BuildConfig
import com.accenture.githubapps.data.model.DetailUserResponse
import com.accenture.githubapps.data.model.User
import com.accenture.githubapps.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

/**
 * App REST API access points
 */
interface AppService {
    @GET("search/users")
    @Headers("Authorization: ${BuildConfig.API_DEVELOPER_TOKEN}")
    fun getSearchUser(
        @Query("q")
        query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: ${BuildConfig.API_DEVELOPER_TOKEN}")
    fun getUserDetail(
        @Path("username")
        username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: ${BuildConfig.API_DEVELOPER_TOKEN}")
    fun getFollowers(
        @Path("username")
        username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: ${BuildConfig.API_DEVELOPER_TOKEN}")
    fun getFollowing(
        @Path("username")
        username: String
    ): Call<ArrayList<User>>
}