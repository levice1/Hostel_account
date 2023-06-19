package com.example.hostelaccount.viewmodel.util

import com.example.hostelaccount.data.data_sourse.PeopleItemModel

class CreateDelayResidentsList {
    operator fun invoke(peopleList: List<PeopleItemModel>): List<PeopleItemModel> {
        val delayList = mutableListOf<PeopleItemModel>()
        val procDate = ProcessingDate()
        peopleList.forEach {
            if (procDate.calculateDaysDifference(it.liveTo) < 0 && !it.usPeople) delayList.add(it)
        }
        return delayList
    }
}
