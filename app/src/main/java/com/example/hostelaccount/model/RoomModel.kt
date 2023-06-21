package com.example.hostelaccount.model

import com.example.hostelaccount.data.data_sourse.PeopleItemModel

data class RoomModel(
    var roomNum: Int,
    var people: List<PeopleItemModel>

)