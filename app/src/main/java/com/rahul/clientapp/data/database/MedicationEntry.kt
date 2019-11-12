package com.rahul.clientapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medication")
class MedicationEntry (
    @PrimaryKey
    var id : Int = 0,
    var doctorName : String = "",
    var diseaseName : String = "",
    var startDate : Long = 0L,
    var endDate: Long = 0L
)