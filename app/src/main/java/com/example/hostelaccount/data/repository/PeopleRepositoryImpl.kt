package com.example.hostelaccount.data.repository

import android.content.Context
import com.example.hostelaccount.data.data_sourse.DbManager
import com.example.hostelaccount.data.data_sourse.PeopleDao
import com.example.hostelaccount.data.data_sourse.PeopleItemModel
import com.example.hostelaccount.viewmodel.Peoples.repository.PeopleRepository
import kotlinx.coroutines.flow.Flow

class PeopleRepositoryImpl(context: Context): PeopleRepository {

    private var dao: PeopleDao

    init {
        dao = DbManager.getInstance(context).peopleDao()
    }


    override fun getPeoples(): Flow<List<PeopleItemModel>> {
        return dao.getAll()
    }

    override suspend fun deleteById(id: Int) {
        dao.deleteById(id)
    }

    override suspend fun insertItem(vararg peopleItemModels: PeopleItemModel): List<Long> {
        return dao.insertItem(*peopleItemModels)
    }

}