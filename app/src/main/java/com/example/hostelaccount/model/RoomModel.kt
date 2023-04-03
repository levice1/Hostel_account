package com.example.hostelaccount.model

data class RoomModel(
    var roomNum: Int,
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