package com.example.hostelaccount.viewmodel.peoples.util

import com.example.hostelaccount.data.data_sourse.PeopleItemModel
import com.example.hostelaccount.model.RoomModel


class CreatingRoomList {
    // функция для создания массива комнат с разспределёнными людьми
     operator fun invoke(peopleList: List<PeopleItemModel>): ArrayList<RoomModel> {
        val roomList = ArrayList<RoomModel>()
        val peopleMap = mutableMapOf<Int, MutableList<PeopleItemModel>>()
        for (people in peopleList) {
            if (!peopleMap.containsKey(people.roomNumber)) {
                peopleMap[people.roomNumber] = mutableListOf()
            }
            peopleMap[people.roomNumber]?.add(people)
        }
        for ((roomNumber, peopleInRoom) in peopleMap) {
            val room = RoomModel(
                roomNum = roomNumber,
                people = mutableListOf()
            )
            for (i in 0 until 4) {
                if (i < peopleInRoom.size) {
                    val people = peopleInRoom[i]
                    val resident = PeopleItemModel(
                        guestName = people.guestName,
                        id = people.id!!,
                        liveFrom = people.liveFrom,
                        liveTo = people.liveTo,
                        usPeople = people.usPeople,
                        addInfo = people.addInfo,
                        roomNumber = people.roomNumber
                    )
                    room.people += (resident)
                } else {
                    room.people += (PeopleItemModel(0,0, "", "", "", false, ""))
                }
            }
            roomList.add(room)
        }
        return roomList
    }
}