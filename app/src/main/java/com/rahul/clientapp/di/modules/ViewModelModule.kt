package com.rahul.clientapp.di.modules

import com.rahul.clientapp.data.DataRepository
import com.rahul.clientapp.viewmodels.RoomViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [(RoomModule::class)])
object ViewModelModule {
    @Provides
    @Singleton
    fun provideViewModelFactory(repository : DataRepository) = RoomViewModelFactory(repository)
}