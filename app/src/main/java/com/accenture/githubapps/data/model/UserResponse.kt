package com.accenture.githubapps.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserResponse(
    val items : ArrayList<User>
): Parcelable
