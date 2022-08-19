package com.accenture.githubapps.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Suppress("unused")
@Module
abstract class ViewModelModule {
    /*@Binds
    @IntoMap
    @ViewModelKey(VisitAddTransactionViewModel::class)
    abstract fun bindVisitAddTransactionViewModel(viewModel: VisitAddTransactionViewModel): ViewModel*/

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
