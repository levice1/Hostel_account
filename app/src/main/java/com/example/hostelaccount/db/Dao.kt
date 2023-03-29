package com.example.hostelaccount.db
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow




@Dao
interface PeopleDao {
    @Query("SELECT * FROM Peoples")
    fun getAll(): List<PeopleItemModel>

    @Query("SELECT * FROM Peoples WHERE id=:id")
    fun getById(id: Int): PeopleItemModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg peopleItemModels: PeopleItemModel)
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