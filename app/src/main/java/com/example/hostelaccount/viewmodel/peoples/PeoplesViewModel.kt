package com.example.hostelaccount.viewmodel.peoples

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hostelaccount.data.data_sourse.PeopleItemModel
import com.example.hostelaccount.model.Resident
import com.example.hostelaccount.model.RoomModel
import com.example.hostelaccount.viewmodel.peoples.repository.PeopleRepository
import com.example.hostelaccount.viewmodel.peoples.util.CreatingRoomList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class PeoplesViewModel : ViewModel() {

    private var tempResident: PeopleItemModel? = null

    private lateinit var _repository: PeopleRepository


    fun init(repository: PeopleRepository) {
        _repository = repository
    }

    fun getRoomsList(): MutableLiveData<List<RoomModel>> {
        val roomList = MutableLiveData<List<RoomModel>>()
        _repository.getPeoples().onEach {
            roomList.postValue(CreatingRoomList().invoke(it))
        }.launchIn(viewModelScope)
        return roomList
    }

    suspend fun saveResident(resident: PeopleItemModel) {
        val insertedItemId = _repository.insertItem(resident) // сохранение to local
        resident.id = insertedItemId[0].toInt()// change id to AutIncr generated
        //InsertLocalDBToRemoteDB(BackendConstants().insertPeople).insertToPeople(resident)// save to remote
    }


    suspend fun deleteResident(resident: PeopleItemModel) {
        if (resident.id != null) {
            _repository.deleteById(resident.id!!)
            //InsertLocalDBToRemoteDB(BackendConstants().deletePeople).insertToPeople(resident)
        }
    }


    // так как CreatingRoomArray не возвращает в каждом резиденте номер комнаты, мы формируем обьект PeopleItemModel здесь.
    // Это сделано для передачи человека со всеми данными, из фрагмента списка комнат в фрагмент редактирования, при нажатии на него.
    fun saveTempResident(resident: Resident?, roomNum: Int?) {
        if (resident != null && roomNum != null) {
            tempResident = PeopleItemModel(
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

    fun getTempResident(): PeopleItemModel? {
        val tempResidentData = tempResident
        tempResident = null
        return tempResidentData
    }

}