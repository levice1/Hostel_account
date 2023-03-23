package com.example.hostelaccount.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class GetRoomsLiveDataModel: ViewModel() {
    val dbResponse = MutableLiveData<ArrayList<RoomModel>>()
}