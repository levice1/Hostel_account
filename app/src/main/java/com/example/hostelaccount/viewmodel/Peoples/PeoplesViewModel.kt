package com.example.hostelaccount.viewmodel.Peoples

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hostelaccount.data.data_sourse.PeopleItemModel
import com.example.hostelaccount.data.remote.BackendConstants
import com.example.hostelaccount.data.remote.InsertLocalDBToRemoteDB
import com.example.hostelaccount.data.repository.PeopleRepositoryImpl
import com.example.hostelaccount.model.Resident
import com.example.hostelaccount.model.RoomModel
import com.example.hostelaccount.viewmodel.Peoples.util.CreatingPeoplesList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class PeoplesViewModel : ViewModel() {

    private var residentData: PeopleItemModel? = null

    private lateinit var _repository: PeopleRepositoryImpl


    fun init (repository: PeopleRepositoryImpl) {
        _repository = repository
    }
    private fun getAllResidents(): MutableLiveData<List<PeopleItemModel>> {
        val allPeoplesLD = MutableLiveData<List<PeopleItemModel>>()
        // получение всех данных из БД и обновление адаптера RecView
        _repository.getPeoples().onEach {
                allPeoplesLD.value = it
            }.launchIn(viewModelScope)
        return allPeoplesLD
    }

    fun getRoomsList(): MutableLiveData<List<RoomModel>> {
        val roomList = MutableLiveData<List<RoomModel>>()
        _repository.getPeoples().onEach {
            roomList.postValue(CreatingPeoplesList().createRoomList(it))
        }.launchIn(viewModelScope)
        return roomList
    }

    suspend fun saveResident(resident: PeopleItemModel) {
        val insertedItemId = _repository.insertItem(resident) // сохранение to local
        resident.id = insertedItemId[0].toInt()// change id to AutIncr generated
        //InsertLocalDBToRemoteDB(BackendConstants().insertPeople).insertToPeople(resident)// save to remote
    }


    suspend fun deleteResident(resident: PeopleItemModel){
            _repository.deleteById(resident.id!!)
        //InsertLocalDBToRemoteDB(BackendConstants().deletePeople).insertToPeople(resident)
    }


    // так как CreatingRoomArray не возвращает в каждом резиденте номер комнаты, мы формируем обьект PeopleItemModel здесь.
    // Это сделано для передачи человека со всеми данными, из фрагмента списка комнат в фрагмент редактирования, при нажатии на него.
    fun saveResidentForRecView(resident: Resident?, roomNum: Int?) {
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

    fun getSavedResident(): PeopleItemModel? {
        val tempResidentData = residentData
        residentData = null
        return tempResidentData
    }

}