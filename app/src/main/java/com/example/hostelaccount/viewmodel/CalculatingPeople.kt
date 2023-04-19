package com.example.hostelaccount.viewmodel

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.hostelaccount.db.local.DbManager

class CalculatingPeople(val context: Context) {

    fun getCountResidents(): MutableLiveData<Int> {
        val peopleCount = MutableLiveData<Int>()


        DbManager.getInstance(context)
            .peopleDao()
            .getAll()
            .asLiveData()
            .observe(context as LifecycleOwner){
                val countResidents = it.count() // получение колличества жильцов
                peopleCount.value = countResidents
            }
        return peopleCount
    }

}