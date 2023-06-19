package com.example.hostelaccount.viewmodel.accounting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hostelaccount.data.data_sourse.AccountingItemModel
import com.example.hostelaccount.viewmodel.accounting.repository.AccountingRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class AccountingViewModel : ViewModel() {
    private var tempAccountingItem: AccountingItemModel? = null

    private lateinit var _repository: AccountingRepository

    fun init(repository: AccountingRepository) {
        _repository = repository
    }

    fun getAccountingItems(): MutableLiveData<List<AccountingItemModel>> {
        val accountingItems = MutableLiveData<List<AccountingItemModel>>()
        _repository.getAllEntries().onEach {
            accountingItems.postValue(it)
        }.launchIn(viewModelScope)
        return accountingItems
    }

    suspend fun saveAccountingItem(item: AccountingItemModel) {
        val insertedItemId = _repository.insertItem(item) // сохранение to local
        item.id = insertedItemId[0].toInt()// change id to AutIncr generated
        // внесение в сетевую БД
        //InsertLocalDBToRemoteDB(BackendConstants().insertAcc).insertToAccounting(item)
    }

    suspend fun deleteAccountingItem(item: AccountingItemModel) {
        if (item.id != null) {
            _repository.deleteById(item.id!!)
            //InsertLocalDBToRemoteDB(BackendConstants().deleteAcc).insertToAccounting(tempAccountingItem!!)
        }
    }


    fun saveTempItem(newData: AccountingItemModel) {
        tempAccountingItem = newData
    }

    fun getTempItem(): AccountingItemModel? {
        val accountingItemForReturn = tempAccountingItem
        tempAccountingItem = null
        return accountingItemForReturn
    }

}