package com.example.hostelaccount.viewmodel

import com.example.hostelaccount.db.local.PeopleItemModel
import com.example.hostelaccount.model.Resident
import com.example.hostelaccount.model.RoomModel


class CreatingPeoplesList {
    // функция для создания массива комнат с разспределёнными людьми
    fun createRoomList(peopleList: List<PeopleItemModel>): ArrayList<RoomModel> {
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
                    val resident = Resident(
                        name = people.guestName,
                        id = people.id!!,
                        liveFrom = people.liveFrom,
                        liveTo = people.liveTo,
                        usMan = people.usPeople,
                        additionalInfo = people.addInfo
                    )
                    room.people += (resident)
                } else {
                    room.people += (Resident("", 0, "", "", false, ""))
                }
            }
            roomList.add(room)
        }
        return roomList
    }


    // функция для создания списка людей с истёкшим периодом оплаты
    fun createDelayList(peopleList: List<PeopleItemModel>) : List<PeopleItemModel> {
        val delayList = mutableListOf<PeopleItemModel>()
        val procDate = ProcessingDate()
        peopleList.forEach{
           if (procDate.calculateDaysDifference(it.liveTo) < 0 && !it.usPeople) delayList.add(it)
        }
        return delayList
    }


    // функция подсчёта количества проживающих людей
    fun getCountResidents(list: List<PeopleItemModel>): String {
        return list.count().toString()
    }

}