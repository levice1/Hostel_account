package com.example.hostelaccount.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "peoples")
data class People (
    @PrimaryKey (autoGenerate = true)
    var id : Int? = null,
    @ColumnInfo ( name = "roomNumber")
    var roomNumber: Int,
    @ColumnInfo ( name = "guestName")
    var guestName: String,
    @ColumnInfo ( name = "liveFrom")
    var liveFrom: String,
    @ColumnInfo ( name = "liveTo" )
    var liveTo: String,
    @ColumnInfo ( name = "addInfo" )
    var addInfo:String
)