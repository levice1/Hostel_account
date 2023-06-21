package com.example.hostelaccount.data.repository

import android.content.Context
import com.example.hostelaccount.data.data_sourse.AccountingDao
import com.example.hostelaccount.data.data_sourse.AccountingItemModel
import com.example.hostelaccount.data.data_sourse.DbManager
import com.example.hostelaccount.viewmodel.accounting.repository.AccountingRepository
import kotlinx.coroutines.flow.Flow

class AccountingRepositoryImpl(context: Context) : AccountingRepository {

    private var dao: AccountingDao

    init {
        dao = DbManager.getInstance(context).accountingDao()
    }

    override fun getAllEntries(): Flow<List<AccountingItemModel>> {
        return dao.getAll()
    }

    override suspend fun insertItem(accountingItem: AccountingItemModel): List<Long> {
        return dao.insertItem(accountingItem)
    }

    override suspend fun deleteById(id: Int) {
        dao.deleteById(id)
    }
}