package com.accenture.githubapps.di.component

import com.accenture.githubapps.features.detail.ui.DetailFragment
import com.accenture.githubapps.features.detail.ui.FollowListFragment
import com.accenture.githubapps.features.home.ui.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class HomeFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailFragment

    @ContributesAndroidInjector
    abstract fun contributeFollowListFragment(): FollowListFragment
}
