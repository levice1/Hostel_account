package com.example.hostelaccount.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Dao
import androidx.room.RoomDatabase
import com.example.hostelaccount.view.accounting.AccountingListFragment


@Database(entities = [People::class, Accounting::class], version = 1)
abstract class DbManager : RoomDatabase() {
    abstract fun peopleDao(): PeopleDao
    abstract fun accountingDao(): AccountingDao

    companion object {
        private var instance: DbManager? = null

        fun getInstance(context: Context): DbManager {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    DbManager::class.java, "my-database"
                ).build()
            }
            return instance!!
        }
    }
}