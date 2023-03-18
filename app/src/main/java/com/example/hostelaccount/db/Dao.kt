package com.example.hostelaccount.db
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hostelaccount.model.AccountingListModel
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun insertItem(People : People)


    @Query("SELECT * FROM Peoples")
    fun getAllItem(): Flow<ArrayList<AccountingListModel>>
}