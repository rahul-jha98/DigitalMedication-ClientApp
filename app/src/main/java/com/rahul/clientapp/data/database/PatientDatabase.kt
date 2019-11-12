package com.rahul.clientapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [(MedicationEntry::class), (MedicineEntry::class)], version = 1, exportSchema = false)
abstract class PatientDatabase : RoomDatabase(){
    companion object {
        private const val DATABASE_NAME = "patientDb"
        private val LOCK = Object()

        @Volatile
        var databaseInstance : PatientDatabase? = null

        fun getInstance(context: Context) : PatientDatabase {
            if(databaseInstance == null) {
                synchronized(LOCK) {
                    if(databaseInstance == null) {
                        databaseInstance = Room.databaseBuilder(context.applicationContext,
                            PatientDatabase::class.java,
                            PatientDatabase.DATABASE_NAME).build()
                    }
                }
            }
            return databaseInstance!!
        }
    }

    abstract fun medicationDao() : MedicationDao
}