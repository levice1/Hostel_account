package com.example.hostelaccount.viewmodel.Peoples

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hostelaccount.data.data_sourse.DbManager
import com.example.hostelaccount.data.data_sourse.PeopleItemModel
import com.example.hostelaccount.data.repository.PeopleRepositoryImpl
import com.example.hostelaccount.model.Resident
import com.example.hostelaccount.model.RoomModel
import com.example.hostelaccount.viewmodel.Peoples.util.CreatingPeoplesList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach



class PeoplesViewModel() : ViewModel() {

    private var residentData: PeopleItemModel? = null

    private val _peoplesListLD: MutableLiveData<List<PeopleItemModel>> = MutableLiveData()

    lateinit var _repository: PeopleRepositoryImpl


    fun init (repository: PeopleRepositoryImpl) {
        _repository = repository
        getAllPeoples()
    }
    private fun getAllPeoples() {
        // получение всех данных из БД и обновление адаптера RecView
        _repository.getPeoples().onEach {
                _peoplesListLD.value = it
            }.launchIn(viewModelScope)
    }

    fun getRoomsList(): MutableLiveData<List<RoomModel>> {
        val roomList = MutableLiveData<List<RoomModel>>()
        roomList.value = _peoplesListLD.value?.let { CreatingPeoplesList().createRoomList(it) }
        return roomList
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