package com.accenture.githubapps.api

import com.accenture.githubapps.data.model.FavoriteUser
import com.accenture.githubapps.data.model.User
import retrofit2.Call
import retrofit2.http.*

/**
 * App REST API access points
 */
interface AppService {
    @GET("users")
    fun getUsers(): Call<List<User>>

    @GET("search/users")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<FavoriteUser>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<FavoriteUser>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<User>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<User>>
}