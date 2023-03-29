package com.example.hostelaccount.model

//class RoomModel (
//    var roomNum : Int,
//    var nameMan1: String,
//    var idMan1: String,
//    var liveFromMan1: String,
//    var liveToMan1: String,
//    var usMan1: Boolean,
//    var additionalInfoMan1: String,
//    var nameMan2: String,
//    var idMan2: String,
//    var liveFromMan2: String,
//    var liveToMan2: String,
//    var usMan2: Boolean,
//    var additionalInfoMan2: String,
//    var nameMan3: String,
//    var idMan3: String,
//    var liveFromMan3: String,
//    var liveToMan3: String,
//    var usMan3: Boolean,
//    var additionalInfoMan3: String,
//    var nameMan4: String,
//    var idMan4: String,
//    var liveFromMan4: String,
//    var liveToMan4: String,
//    var usMan4: Boolean,
//    var additionalInfoMan4: String,
//)

data class RoomModel(
    var roomNum: String,
    var people: List<Resident>

)
data class Resident(
    var name: String,
    var id: Int,
    var liveFrom: String,
    var liveTo: String,
    var usMan: Boolean,
    var additionalInfo: String
)