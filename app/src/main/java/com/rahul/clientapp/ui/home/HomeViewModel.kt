package com.rahul.clientapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rahul.clientapp.data.DataRepository
import com.rahul.clientapp.utils.TimeUtils

class HomeViewModel(repository: DataRepository) : ViewModel() {
    val medicationList = repository.getActiveMedication(TimeUtils.getTodaysMidninghtTimeInMillis())
}