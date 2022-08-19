package com.accenture.githubapps.di.component

import com.accenture.githubapps.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class HomeActivityModule {
    @ContributesAndroidInjector(modules = [HomeFragmentBuildersModule::class])
    abstract fun contributeHomeActivity(): HomeActivity
}
