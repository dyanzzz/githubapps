package com.accenture.githubapps.features.detail.data

import androidx.lifecycle.MutableLiveData
import com.accenture.githubapps.api.AppService
import com.accenture.githubapps.data.model.*
import com.accenture.githubapps.data.resultMutableLiveDataRemoteByPass
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailRepository @Inject constructor(
    private val service: AppService,
    private val dao: FavoriteDao,
){

    fun requestUserDetail(
        liveDataList: MutableLiveData<FavoriteUser>,
        username: String
    ) = resultMutableLiveDataRemoteByPass(
        liveDataList,
        service.getUserDetail(username)
    )

    fun requestUserFollowers(
        liveDataList: MutableLiveData<List<User>>,
        username: String,
        isFollower: Boolean
    ) = resultMutableLiveDataRemoteByPass(
        liveDataList,
        if (isFollower) service.getFollowers(username) else service.getFollowing(username)
    )

    fun observeInsertUserFavorite(favoriteUser: FavoriteUser) {
        try {
            dao.insertFavorite(favoriteUser)
        } catch (e: Exception) {
            Timber.e("###>>> Failed insert %s", e.message)
        }
    }

    fun observeRemoveUserFavorite(username: String) {
        try {
            val data = TempFavoriteUserDelete(username)
            dao.insertTempFavoriteUserDelete(data)
        } catch (e: Exception) {
            Timber.e("###>>> Failed remove %s", e.message)
        }
    }
    fun observeRemoveTempFavoriteUser(username: String) = dao.removeTempFavorite(username)

    fun observeCheckDetailUserFavorite(username: String) = dao.checkUser(username)

    fun observeFavoriteUserDetail(username: String) = dao.getFavoriteUserDetail(username)

    fun observeInsertFollowerFavoriteUser(follower: Follower) {
        try {
            dao.insertFollowerFavoriteUser(follower)
        } catch (e: Exception) {
            Timber.e("###>>> Failed insert %s", e.message)
        }
    }

    fun observeRemoveFollowerFavoriteUser(username: String) = dao.removeFollowerFavoriteUser(username)

    fun observeFollowerFavoriteUser(userName: String) = dao.getFollowerFavoriteUser(userName)

}