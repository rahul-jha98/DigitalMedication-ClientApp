package com.rahul.clientapp.models

data class Medication(val doctorName: String = "",
                      val diseaseName: String ="",
                      val startDate: Long = 0L,
                      val endDate: Long = 0L,
                      val medicines: List<Medicine> = ArrayList<Medicine>())