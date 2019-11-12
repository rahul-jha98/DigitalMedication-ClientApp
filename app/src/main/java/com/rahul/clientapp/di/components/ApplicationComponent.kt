package com.rahul.clientapp.di.components

import com.rahul.clientapp.data.DataRepository
import com.rahul.clientapp.di.modules.ViewModelModule
import com.rahul.clientapp.viewmodels.RoomViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ViewModelModule::class)])
interface ApplicationComponent {
    fun getViewModelFactory() : RoomViewModelFactory
    fun getRepository() : DataRepository
}