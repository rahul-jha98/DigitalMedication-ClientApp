package com.rahul.clientapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rahul.clientapp.data.DataRepository
import com.rahul.clientapp.ui.medications.MedicationViewModel

class RoomViewModelFactory constructor(private val repository: DataRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MedicationViewModel::class.java) -> MedicationViewModel(this.repository) as T
            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}