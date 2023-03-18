package com.example.hostelaccount.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.Dao
import androidx.room.RoomDatabase
import com.example.hostelaccount.view.accounting.AccountingListFragment


@Database(entities = [People::class], version = 1)
abstract class DbManager : RoomDatabase() {
     abstract fun getDao(): com.example.hostelaccount.db.Dao

    companion object {
        fun getDb (context: AccountingListFragment): DbManager {
            return Room.databaseBuilder(context.applicationContext,
                DbManager::class.java,
                "hostelAccount.db")
                .build()
        }
    }
}