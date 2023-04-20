package com.example.hostelaccount.viewmodel

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.hostelaccount.db.local.DbManager
import com.example.hostelaccount.model.PeoplesViewModel

class CalculatingPeople(val context: Context) {

    fun getCountResidents(): MutableLiveData<Int> {
        val peopleCount = MutableLiveData<Int>()
        val peoplVM = PeoplesViewModel()


        DbManager.getInstance(context)
            .peopleDao()
            .getAll()
            .asLiveData()
            .observe(context as LifecycleOwner){
                peoplVM.setData(it)
                val countResidents = it.count() // получение колличества жильцов
                peopleCount.value = countResidents
            }
        return peopleCount
    }

}