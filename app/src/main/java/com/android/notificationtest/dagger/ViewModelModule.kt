package com.android.notificationtest.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.notificationtest.MainActivityViewModel
import com.android.notificationtest.utils.dagger.ViewModelFactory
import com.android.notificationtest.utils.dagger.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    fun bindMainActivityViewModel(viewModel: MainActivityViewModel?): ViewModel?

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory?): ViewModelProvider.Factory?

}