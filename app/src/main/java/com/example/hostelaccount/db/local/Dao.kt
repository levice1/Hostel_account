package com.example.hostelaccount.db.local
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow




@Dao
interface PeopleDao {
    @Query("SELECT * FROM Peoples ORDER BY roomNumber ASC")
    fun getAll(): List<PeopleItemModel>

    @Query("SELECT * FROM Peoples WHERE id=:id")
    fun getById(id: Int): PeopleItemModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg peopleItemModels: PeopleItemModel)

    @Query("DELETE FROM Peoples WHERE id = :id")
    fun deleteById(id: Int)
}

@Dao
interface AccountingDao {
    @Query("SELECT * FROM Accounting ORDER BY date ASC")
    fun getAll(): Flow<List<AccountingItemModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg accountingItemModel: AccountingItemModel)

    @Query("DELETE FROM Accounting WHERE id = :id")
    fun deleteById(id: Int)
}