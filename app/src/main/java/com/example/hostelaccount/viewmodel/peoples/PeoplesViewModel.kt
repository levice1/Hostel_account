package com.example.hostelaccount.viewmodel.peoples

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hostelaccount.data.data_sourse.PeopleItemModel
import com.example.hostelaccount.data.remote.BackendConstants
import com.example.hostelaccount.data.remote.InsertLocalDBToRemoteDB
import com.example.hostelaccount.viewmodel.peoples.repository.PeopleRepository
import com.example.hostelaccount.viewmodel.peoples.util.CreatingRoomList
import com.example.hostelaccount.viewmodel.peoples.util.PeoplesStateModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class PeoplesViewModel : ViewModel() {

    private var tempResident: PeopleItemModel? = null

    private lateinit var _repository: PeopleRepository

    private val _state: MutableLiveData<PeoplesStateModel> = MutableLiveData()
    val state: LiveData<PeoplesStateModel> = _state

    private var getRoomListJob: Job? = null


    fun init(repository: PeopleRepository) {
        _repository = repository
    }

    fun onEvent(event: PeoplesEvent) {
        when (event) {
            is PeoplesEvent.GetRoomsList -> {
                getRoomsList()
            }

            is PeoplesEvent.SaveResident -> {
                saveResident(event.resident)
            }

            is PeoplesEvent.DeleteResident -> {
                deleteResident(event.resident)
            }

            is PeoplesEvent.SaveTempResident -> {
                saveTempResident(event.tempResident)
            }

            is PeoplesEvent.GetTempResident -> {
                getTempResident()
            }
        }
    }

    private fun getRoomsList() {
        // cancel previous coroutine, and start new
        getRoomListJob?.cancel()
        getRoomListJob = _repository.getPeoples().onEach {
            _state.value = PeoplesStateModel(null, CreatingRoomList().invoke(it))
        }.launchIn(viewModelScope)
    }

    private fun saveResident(resident: PeopleItemModel) {
        CoroutineScope(Dispatchers.IO).launch {
            // save to local db
            val insertedItemId = _repository.insertItem(resident)
            // change id to AutIncrement generated
            resident.id = insertedItemId[0].toInt()
            // save to remote db
            InsertLocalDBToRemoteDB(BackendConstants().insertPeople).insertToPeople(resident)
        }
    }


    private fun deleteResident(resident: PeopleItemModel) {
        CoroutineScope(Dispatchers.IO).launch {
            if (resident.id != null) {
                // del from local db
                _repository.deleteById(resident.id!!)
                // del from remote db
                InsertLocalDBToRemoteDB(BackendConstants().deletePeople).insertToPeople(resident)
            }
        }
    }


    private fun saveTempResident(resident: PeopleItemModel) {
        tempResident = resident
    }


    private fun getTempResident() {
        _state.value = PeoplesStateModel(tempResident, null)
        tempResident = null
    }
}

