package com.example.hostelaccount.viewmodel.accounting.repository

import com.example.hostelaccount.data.data_sourse.AccountingItemModel
import kotlinx.coroutines.flow.Flow

interface AccountingRepository {
    fun getAllEntries(): Flow<List<AccountingItemModel>>
    suspend fun insertItem(accountingItem: AccountingItemModel): List<Long>
    suspend fun deleteById(id: Int)
}