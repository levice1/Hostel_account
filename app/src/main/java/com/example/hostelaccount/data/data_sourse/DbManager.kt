package com.example.hostelaccount.data.data_sourse

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [PeopleItemModel::class, AccountingItemModel::class], version = 1)
abstract class DbManager : RoomDatabase() {


    abstract fun peopleDao(): PeopleDao
    abstract fun accountingDao(): AccountingDao

    companion object {
        private var instance: DbManager? = null

        fun getInstance(context: Context): DbManager {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    DbManager::class.java, "hostel-db0"
                ).build()
            }
            return instance!!
        }
    }
}