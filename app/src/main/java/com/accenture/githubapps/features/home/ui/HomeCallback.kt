package com.accenture.githubapps.features.home.ui

import com.accenture.githubapps.data.model.User

interface HomeCallback {
    fun onClick(data: User)
}