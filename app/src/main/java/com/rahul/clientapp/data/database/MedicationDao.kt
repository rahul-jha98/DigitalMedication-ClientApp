package com.rahul.clientapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MedicationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedication(medicationEntry: MedicationEntry)

    @Insert
    fun insertMedicines(medicineEntry: List<MedicineEntry>)

    @Query("SELECT * FROM medication ORDER BY endDate")
    fun getAllMedications() : LiveData<List<MedicationWIthMedicines>>

    @Query("SELECT * FROM medicine INNER JOIN (SELECT * FROM medication WHERE medication.endDate > :currTimeInMillis) T ON medicine.medicineId = T.id WHERE breakfast = 1")
    fun getActiveMorningMedications(currTimeInMillis : Long, durationOfDay: Int) : LiveData<List<MedicineEntry>>

    @Query("SELECT * FROM medicine INNER JOIN (SELECT * FROM medication WHERE medication.endDate > :currTimeInMillis) T ON medicine.medicineId = T.id WHERE lunch = 1")
    fun getActiveAfternoonMedications(currTimeInMillis : Long, durationOfDay: Int) : LiveData<List<MedicineEntry>>

    @Query("SELECT * FROM medicine INNER JOIN (SELECT * FROM medication WHERE medication.endDate > :currTimeInMillis) T ON medicine.medicineId = T.id WHERE dinner = 1")
    fun getActiveNightMedications(currTimeInMillis : Long, durationOfDay: Int) : LiveData<List<MedicineEntry>>
}