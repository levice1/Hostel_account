package com.example.hostelaccount.model

import com.example.hostelaccount.data.data_sourse.PeopleItemModel

data class RoomModel(
    var roomNum: Int,
    var people: List<PeopleItemModel>

)
//data class Resident(
//    var name: String,
//    var id: Int,
//    var liveFrom: String,
//    var liveTo: String,
//    var usMan: Boolean,
//    var additionalInfo: String,
//    var roomNum: Int
//)