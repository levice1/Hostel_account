package com.example.hostelaccount.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class GetCountAllPeoples: ViewModel() {
    val peoplesCountLiveData = MutableLiveData<Int>()
}