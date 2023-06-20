package com.example.hostelaccount.viewmodel.accounting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hostelaccount.data.data_sourse.AccountingItemModel
import com.example.hostelaccount.data.remote.BackendConstants
import com.example.hostelaccount.data.remote.InsertLocalDBToRemoteDB
import com.example.hostelaccount.viewmodel.accounting.repository.AccountingRepository
import com.example.hostelaccount.viewmodel.accounting.util.AccountingStateModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class AccountingViewModel : ViewModel() {
    private var tempAccountingItem: AccountingItemModel? = null

    private lateinit var _repository: AccountingRepository

    private val _state: MutableLiveData<AccountingStateModel> = MutableLiveData()
    val state: LiveData<AccountingStateModel> = _state


    fun init(repository: AccountingRepository) {
        _repository = repository
    }


    fun onEvent(event: AccountingEvent) {
        when (event) {
            is AccountingEvent.GetAccountingItems -> {
                getAccountingItems()
            }

            is AccountingEvent.SaveItem -> {
                saveAccountingItem(event.item)
            }

            is AccountingEvent.DeleteItem -> {
                deleteAccountingItem(event.item)
            }

            is AccountingEvent.SaveTempItem -> {
                saveTempItem(event.tempItem)
            }

            is AccountingEvent.GetTempItem -> {
                getTempItem()
            }
        }
    }


    private fun getAccountingItems() {
        _repository.getAllEntries().onEach {
            _state.value = AccountingStateModel(null, it)
        }.launchIn(viewModelScope)
    }


    private fun saveAccountingItem(item: AccountingItemModel) {
        CoroutineScope(Dispatchers.IO).launch {
            // save to local db
            val insertedItemId = _repository.insertItem(item)
            // change id to AutIncrement generated
            item.id = insertedItemId[0].toInt()
            // save to remote db
            InsertLocalDBToRemoteDB(BackendConstants().insertAcc).insertToAccounting(item)
        }
    }


    private fun deleteAccountingItem(item: AccountingItemModel) {
        CoroutineScope(Dispatchers.IO).launch {
            if (item.id != null) {
                // delete from local db
                _repository.deleteById(item.id!!)
                // delete from remote db
                InsertLocalDBToRemoteDB(BackendConstants().deleteAcc).insertToAccounting(tempAccountingItem!!)
            }
        }
    }


    private fun saveTempItem(tempItem: AccountingItemModel) {
        tempAccountingItem = tempItem
    }


    private fun getTempItem() {
        _state.value = AccountingStateModel(tempAccountingItem, null)
        tempAccountingItem = null
    }
}