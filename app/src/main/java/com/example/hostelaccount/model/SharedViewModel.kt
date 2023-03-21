package com.example.hostelaccount.model

import androidx.lifecycle.ViewModel
import com.example.hostelaccount.db.AccountingItemModel


// КЛАСС ДЛЯ ViewModel С МЕТОДАМИ ЗАПИСИ И ПОЛУЧЕНИЯ ДАННЫХ
class SharedViewModel : ViewModel() {
    private var data: AccountingItemModel? = null

    fun setData(newData: AccountingItemModel) {
        data = newData
    }

    fun getData(): AccountingItemModel? {
        return data
    }
}