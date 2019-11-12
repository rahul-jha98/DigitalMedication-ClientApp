package com.rahul.clientapp

import android.app.Application
import com.rahul.clientapp.di.components.ApplicationComponent
import com.rahul.clientapp.di.modules.ContextModule
import com.rahul.clientapp.di.modules.RoomModule
import com.rahul.clientapp.di.modules.ViewModelModule
import com.rahul.clientapp.di.components.DaggerApplicationComponent

class ClinetApplication: Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder()
            .contextModule(ContextModule(this))
            .roomModule(RoomModule)
            .viewModelModule(ViewModelModule)
            .build()
    }
}