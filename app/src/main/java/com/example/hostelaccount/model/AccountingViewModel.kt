package com.example.hostelaccount.model

import androidx.lifecycle.ViewModel
import com.example.hostelaccount.db.local.AccountingItemModel


// КЛАСС ДЛЯ ViewModel С МЕТОДАМИ ЗАПИСИ И ПОЛУЧЕНИЯ ДАННЫХ
class AccountingViewModel : ViewModel() {
    private var accountingData: AccountingItemModel? = null

    fun setData(newData: AccountingItemModel) {
        accountingData = newData
    }

    fun getData(): AccountingItemModel? {
        return accountingData
    }

    fun clearData(){
        accountingData = null
    }
}