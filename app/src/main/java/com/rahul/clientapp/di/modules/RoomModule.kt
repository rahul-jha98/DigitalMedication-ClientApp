package com.rahul.clientapp.di.modules

import android.content.Context
import com.rahul.clientapp.AppExecutors
import com.rahul.clientapp.data.DataRepository
import com.rahul.clientapp.data.database.PatientDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [(ContextModule::class)])
object RoomModule {
    @Provides
    @Singleton
    fun provideAppExecutors() = AppExecutors.getInstance()

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context) = PatientDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideDataRepository(patientDatabase: PatientDatabase, appExecutors: AppExecutors) =
        DataRepository.getInstance(patientDatabase.medicationDao(),
            appExecutors)
}