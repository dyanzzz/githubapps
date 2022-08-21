package com.accenture.githubapps.data.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {
    @Query("""
        SELECT login, id, avatar_url, gravatar_id, url, html_url,
        followers_url, following_url,  gists_url, starred_url, subscriptions_url, organizations_url, 
        repos_url, events_url, received_events_url, type, site_admin,
        events_url
        FROM FavoriteUser
    """)
    fun getFavoriteList(): LiveData<List<User>>

    @Query("""
        SELECT * FROM FavoriteUser WHERE login = :username
    """)
    fun getFavoriteUserDetail(username: String): LiveData<FavoriteUser>

    @Query("""
        SELECT count(*) FROM FavoriteUser WHERE login = :username
    """)
    fun checkUser(username: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favoriteUser: FavoriteUser)

    @Query("""
       SELECT login FROM TempFavoriteUserDelete
    """)
    fun checkTempFavorite(): LiveData<List<String>>

    @Query("""
       DELETE FROM FavoriteUser WHERE login = :username 
    """)
    fun removeFavorite(username: String)

    @Query("""
       DELETE FROM TempFavoriteUserDelete WHERE login = :username 
    """)
    fun removeTempFavorite(username: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTempFavoriteUserDelete(data: TempFavoriteUserDelete)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFollowerFavoriteUser(data: Follower)

    @Query("""
       DELETE FROM Follower WHERE owner = :username 
    """)
    fun removeFollowerFavoriteUser(username: String)

    @Query("""
        SELECT login, id, avatar_url, gravatar_id, url,
        html_url, followers_url, following_url, gists_url, starred_url, subscriptions_url, organizations_url,
        repos_url, events_url, received_events_url, type, site_admin
        FROM Follower WHERE owner = :userName
    """)
    fun getFollowerFavoriteUser(userName: String): LiveData<List<User>>
}