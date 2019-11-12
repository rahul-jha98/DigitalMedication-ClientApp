package com.rahul.clientapp.ui.medications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rahul.clientapp.data.DataRepository

class MedicationViewModel(val repository: DataRepository) : ViewModel() {
    var medicationList = repository.getAllMedications()
}