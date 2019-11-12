package com.rahul.clientapp.data.database

import androidx.room.Embedded
import androidx.room.Relation

class MedicationWIthMedicines (
    @Embedded
    var medication: MedicationEntry,

    @Relation(parentColumn = "id",
        entityColumn = "medicineId")
    var  medicineList : List<MedicineEntry>
)