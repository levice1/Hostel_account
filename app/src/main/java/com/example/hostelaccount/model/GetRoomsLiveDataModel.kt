package com.example.hostelaccount.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class GetRoomsLiveDataModel: ViewModel() {
    val roomsLiveData = MutableLiveData<ArrayList<RoomModel>>()
}