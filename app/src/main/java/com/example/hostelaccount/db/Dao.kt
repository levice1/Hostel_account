package com.example.hostelaccount.db
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hostelaccount.model.AccountingListModel
import kotlinx.coroutines.flow.Flow




@Dao
interface PeopleDao {
    @Query("SELECT * FROM Peoples")
    fun getAll(): List<People>

    @Insert
    fun insertAll(vararg peoples: People)
}

@Dao
interface AccountingDao {
    @Query("SELECT * FROM Accounting")
    fun getAll(): Flow<List<AccountingListModel>>

    @Insert
    fun insertAll(vararg accounting: Accounting)
}