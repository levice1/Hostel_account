package com.example.hostelaccount.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.hostelaccount.db.local.PeopleItemModel
import com.example.hostelaccount.model.Resident
import com.example.hostelaccount.model.RoomModel
class CreatingPeoplesList {



    // функция для создания массива комнат с разспределёнными людьми
    fun createRoomList(peopleList: List<PeopleItemModel>): ArrayList<RoomModel> {
        // создаем пустые ArrayList для хранения RoomModel объектов
        val roomList = ArrayList<RoomModel>()

        // создаем карту для хранения данных людей в зависимости от номера комнаты
        val peopleMap = mutableMapOf<Int, MutableList<PeopleItemModel>>()
        for (people in peopleList) {
            if (!peopleMap.containsKey(people.roomNumber)) {
                peopleMap[people.roomNumber] = mutableListOf()
            }
            peopleMap[people.roomNumber]?.add(people)
        }
        // итерируемся по карте, создаем RoomModel объекты и добавляем их к roomList.
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
        // возвращаем roomList
        return roomList
    }

    fun createDelayList(peopleList: List<PeopleItemModel>) : List<PeopleItemModel> {
        val delayList = mutableListOf<PeopleItemModel>()
        val delayUsList = mutableListOf<PeopleItemModel>()
        val procDate = ProcessingDate()
        peopleList.forEach{
           if (procDate.calculateDaysDifference(it.liveTo) < 0) delayList.add(it)
        }
        delayList.forEach{
            if (!it.usPeople) delayUsList.add(it)
        }
        return delayUsList
    }

    fun getCountResidents(list: List<PeopleItemModel>): String {
        return list.count().toString()
    }

}