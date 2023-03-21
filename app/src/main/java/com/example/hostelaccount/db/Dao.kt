package com.example.hostelaccount.db
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
    fun getAll(): Flow<List<AccountingItemModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg accountingItemModel: AccountingItemModel)

    @Query("DELETE FROM Accounting WHERE id = :id")
    fun deleteById(id: Int)
}