package com.rahul.clientapp.models

data class Medication(val doctorName: String,
                      val diseaseName: String,
                      val startDate: Long,
                      val endDate: Long,
                      val medicines: List<Medicine>)