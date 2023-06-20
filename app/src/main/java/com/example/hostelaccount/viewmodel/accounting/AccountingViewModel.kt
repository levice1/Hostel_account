package com.example.hostelaccount.viewmodel.accounting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hostelaccount.data.data_sourse.AccountingItemModel
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


    fun onEvent(event: AccountingEvent){
        when (event) {
            is AccountingEvent.GetAccountingItems -> {
                getAccountingItems()
            }
            is AccountingEvent.SaveItem -> {
                saveAccountingItem(event.item)
            }
            is AccountingEvent.DeleteItem ->{
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
            val insertedItemId = _repository.insertItem(item) // сохранение to local
            item.id = insertedItemId[0].toInt()// change id to AutIncr generated
            // внесение в сетевую БД
            //InsertLocalDBToRemoteDB(BackendConstants().insertAcc).insertToAccounting(item)
        }
    }


    private fun deleteAccountingItem(item: AccountingItemModel) {
        CoroutineScope(Dispatchers.IO).launch {
            if (item.id != null) {
                _repository.deleteById(item.id!!)
                //InsertLocalDBToRemoteDB(BackendConstants().deleteAcc).insertToAccounting(tempAccountingItem!!)
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