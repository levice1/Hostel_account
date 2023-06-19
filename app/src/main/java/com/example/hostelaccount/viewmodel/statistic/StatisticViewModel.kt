package com.example.hostelaccount.viewmodel.statistic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hostelaccount.data.data_sourse.PeopleItemModel
import com.example.hostelaccount.viewmodel.accounting.repository.AccountingRepository
import com.example.hostelaccount.viewmodel.peoples.repository.PeopleRepository
import com.example.hostelaccount.viewmodel.statistic.util.CalculatingAccountingAmount
import com.example.hostelaccount.viewmodel.statistic.util.CreateDelayResidentsList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class StatisticViewModel : ViewModel() {

    private lateinit var _repositoryPeople: PeopleRepository
    private lateinit var _repositoryAccounting: AccountingRepository

    fun init(
        repositoryPeoples: PeopleRepository,
        repositoryAccounting: AccountingRepository
    ) {
        _repositoryPeople = repositoryPeoples
        _repositoryAccounting = repositoryAccounting
    }


    fun getResidentsCount() : MutableLiveData<Int> {
        val residentsCountLD = MutableLiveData<Int>()
            _repositoryPeople.getPeoples().onEach {
                residentsCountLD.value = it.count()
            }.launchIn(viewModelScope)
        return residentsCountLD
    }


    fun getDelayResidents(): MutableLiveData<List<PeopleItemModel>> {
        val delayResidentsLD = MutableLiveData<List<PeopleItemModel>>()
        _repositoryPeople.getPeoples().onEach {
            delayResidentsLD.value = CreateDelayResidentsList().invoke(it)
        }.launchIn(viewModelScope)
        return delayResidentsLD
    }


    fun getAccountingAmount(): MutableLiveData<Int> {
        val accountingAmountLD = MutableLiveData<Int>()
            _repositoryAccounting.getAllEntries().onEach {
                accountingAmountLD.value = CalculatingAccountingAmount().invoke(it)
            }.launchIn(viewModelScope)
        return accountingAmountLD
    }
}