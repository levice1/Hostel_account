package com.example.hostelaccount.model

import androidx.lifecycle.ViewModel
import com.example.hostelaccount.db.PeopleItemModel


// КЛАСС ДЛЯ  ViewModel С МЕТОДАМИ ЗАПИСИ И ПОЛУЧЕНИЯ ДАННЫХ

class PeopleIdViewModel : ViewModel() {
    private var residentData: PeopleItemModel? = null

    // так как CreatingRoomArray не возвращает в каждом резиденте номер комнаты, мы формируем обьект PeopleItemModel здесь.
    // Это сделано для передачи человека со всеми данными, из фрагмента списка комнат в фрагмент редактирования, при нажатии на него.
    fun setData(resident: Resident?, roomNum: String?) {
        if (resident != null && roomNum != null) {
            residentData = PeopleItemModel(
                resident.id,
                roomNum,
                resident.name,
                resident.liveFrom,
                resident.liveTo,
                resident.usMan,
                resident.additionalInfo
            )
        }
    }

    fun getData(): PeopleItemModel? {
        return residentData
    }

    override fun onCleared() {
        super.onCleared()
    }
}