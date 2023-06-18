package com.example.hostelaccount.viewmodel.Peoples.repository

import com.example.hostelaccount.data.data_sourse.PeopleItemModel
import kotlinx.coroutines.flow.Flow


interface PeopleRepository {
    fun getPeoples() : Flow<List<PeopleItemModel>>
    suspend fun deleteById(id: Int)
    suspend fun insertItem(vararg peopleItemModels: PeopleItemModel) : List<Long>
}