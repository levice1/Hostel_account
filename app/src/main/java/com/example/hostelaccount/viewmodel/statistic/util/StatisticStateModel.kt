package com.example.hostelaccount.viewmodel.statistic.util

import androidx.lifecycle.MutableLiveData
import com.example.hostelaccount.data.data_sourse.PeopleItemModel

data class StatisticStateModel(
    var residentsCount: MutableLiveData<Int>?,
    var delayResidentsList: MutableLiveData<List<PeopleItemModel>>?,
    var accountingAmount: MutableLiveData<Int>?
)
