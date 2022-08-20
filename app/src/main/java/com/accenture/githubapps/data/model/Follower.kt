package com.accenture.githubapps.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Follower(
  @PrimaryKey(autoGenerate = true)
  val number: Int,
  val login: String,
  val id: Int,
  val avatar_url: String,
  val gravatar_id: String,
  val url: String,
  val html_url: String,
  val followers_url: String,
  val following_url: String,
  val gists_url: String,
  val starred_url: String,
  val subscriptions_url: String,
  val organizations_url: String,
  val repos_url: String,
  val events_url: String,
  val received_events_url: String,
  val type: String,
  val site_admin: Boolean,
  var owner: String,
  var page: Int,
  var isFollower: Boolean
) : Parcelable
