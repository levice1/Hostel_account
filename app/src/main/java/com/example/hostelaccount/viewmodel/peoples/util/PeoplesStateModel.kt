package com.example.hostelaccount.viewmodel.peoples.util

import com.example.hostelaccount.data.data_sourse.PeopleItemModel
import com.example.hostelaccount.model.RoomModel

data class PeoplesStateModel(
    val tempResidentItem: PeopleItemModel?,
    val listRooms: List<RoomModel>?
)
