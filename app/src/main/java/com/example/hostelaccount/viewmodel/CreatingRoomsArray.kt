package com.example.hostelaccount.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.hostelaccount.db.DbManager
import com.example.hostelaccount.db.PeopleItemModel
import com.example.hostelaccount.model.RoomModel

class CreatingRoomsArray(context: Context) {

    // DB
    val db = DbManager.getInstance(context)


    // функция для возврата данных из обьекта
    fun getRoomList (liveData: MutableLiveData<ArrayList<RoomModel>>) {
        Thread{
            val result =  createRoomList(db.peopleDao().getAll())
            liveData.postValue(result)
        }.start()
    }

    // функция для создания массива комнат с разспределёнными людьми
    private fun createRoomList(peopleList: List<PeopleItemModel>): ArrayList<RoomModel> {
        // создаем пустые ArrayList для хранения RoomModel объектов
        val roomList = ArrayList<RoomModel>()

        // создаем карту для хранения данных людей в зависимости от номера комнаты
        val peopleMap = mutableMapOf<String, MutableList<PeopleItemModel>>()
        for (people in peopleList) {
            if (!peopleMap.containsKey(people.roomNumber)) {
                peopleMap[people.roomNumber] = mutableListOf()
            }
            peopleMap[people.roomNumber]?.add(people)
        }
        // итерируемся по карте, создаем RoomModel объекты и добавляем их к roomList.
        for ((roomNumber, peopleInRoom) in peopleMap) {
            val room = RoomModel(
                roomNum = roomNumber.toInt(),
                nameMan1 = "",
                idMan1 = "",
                liveFromMan1 = "",
                liveToMan1 = "",
                additionalInfoMan1 = "",
                nameMan2 = "",
                idMan2 = "",
                liveFromMan2 = "",
                liveToMan2 = "",
                additionalInfoMan2 = "",
                nameMan3 = "",
                idMan3 = "",
                liveFromMan3 = "",
                liveToMan3 = "",
                additionalInfoMan3 = "",
                nameMan4 = "",
                idMan4 = "",
                liveFromMan4 = "",
                liveToMan4 = "",
                additionalInfoMan4 = ""
            )
            for ((index, people) in peopleInRoom.withIndex()) {
                when (index) {
                    0 -> {
                        room.nameMan1 = people.guestName
                        room.idMan1 = people.roomNumber
                        room.liveFromMan1 = people.liveFrom
                        room.liveToMan1 = people.liveTo
                        room.additionalInfoMan1 = people.addInfo
                    }
                    1 -> {
                        room.nameMan2 = people.guestName
                        room.idMan2 = people.roomNumber
                        room.liveFromMan2 = people.liveFrom
                        room.liveToMan2 = people.liveTo
                        room.additionalInfoMan2 = people.addInfo
                    }
                    2 -> {
                        room.nameMan3 = people.guestName
                        room.idMan3 = people.roomNumber
                        room.liveFromMan3 = people.liveFrom
                        room.liveToMan3 = people.liveTo
                        room.additionalInfoMan3 = people.addInfo
                    }
                    3 -> {
                        room.nameMan4 = people.guestName
                        room.idMan4 = people.roomNumber
                        room.liveFromMan4 = people.liveFrom
                        room.liveToMan4 = people.liveTo
                        room.additionalInfoMan4 = people.addInfo
                    }
                }
            }
            roomList.add(room)
        }
        // возвращаем roomList
        return roomList
    }

}