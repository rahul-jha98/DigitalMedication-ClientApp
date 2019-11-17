package com.rahul.clientapp.data.database

import androidx.room.Entity

@Entity(tableName = "medicine", primaryKeys = ["name", "medicineId"])
class MedicineEntry (
    var name: String = "",
    var quantity: String = "",
    var breakfast: Int = 0,
    var lunch: Int = 0,
    var dinner: Int = 0,
    var medicineId : String = ""
)