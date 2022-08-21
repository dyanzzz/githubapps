package com.accenture.githubapps.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class TempFavoriteUserDelete(
    @PrimaryKey
    val login: String,
): Parcelable