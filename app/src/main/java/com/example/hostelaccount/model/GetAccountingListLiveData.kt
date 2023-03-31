package com.example.hostelaccount.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hostelaccount.db.AccountingItemModel


open class GetAccountingListLiveData: ViewModel() {
    val accountingListLiveData = MutableLiveData<List<AccountingItemModel>>()
}