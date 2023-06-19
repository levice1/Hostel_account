package com.example.hostelaccount.viewmodel.util

import com.example.hostelaccount.data.data_sourse.PeopleItemModel

class GetResidentsCount {
    operator fun invoke(list: List<PeopleItemModel>): String {
        return list.count().toString()
    }
}