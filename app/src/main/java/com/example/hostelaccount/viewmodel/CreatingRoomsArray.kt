package com.example.hostelaccount.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.hostelaccount.db.DbManager
import com.example.hostelaccount.db.PeopleItemModel
import com.example.hostelaccount.model.Resident
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


}