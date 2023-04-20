package com.example.hostelaccount.model

import androidx.lifecycle.ViewModel
import com.example.hostelaccount.db.local.AccountingItemModel
import com.example.hostelaccount.db.local.PeopleItemModel


// КЛАСС ДЛЯ ViewModel С МЕТОДАМИ ЗАПИСИ И ПОЛУЧЕНИЯ ДАННЫХ
class PeoplesViewModel : ViewModel() {

    private var peopleList : List<PeopleItemModel>? = null

    fun setData(newData: List<PeopleItemModel>) {
        peopleList = newData
    }

    fun getData(): List<PeopleItemModel>? {
        return peopleList
    }

    fun clearData(){
        peopleList = null
    }
}