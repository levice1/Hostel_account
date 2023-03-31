package com.example.hostelaccount.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class GetCountMoney: ViewModel() {
    val moneyCountLiveData = MutableLiveData<Int>()
}