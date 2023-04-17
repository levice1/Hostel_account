package com.example.hostelaccount.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.hostelaccount.db.local.DbManager

class CalculatingPeople(val db: DbManager, private val peoplesCountLiveData: MutableLiveData<Int>) {

    fun getCountResidents() {
        Thread {
            peoplesCountLiveData.postValue(db.peopleDao().getAll().count())
        }.start()
    }

}