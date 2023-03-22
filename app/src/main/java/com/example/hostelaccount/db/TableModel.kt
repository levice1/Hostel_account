package com.example.hostelaccount.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "peoples")
data class PeopleItemModel (
    @PrimaryKey (autoGenerate = true)
    var id : Int? = null,
    @ColumnInfo ( name = "roomNumber")
    var roomNumber: String,
    @ColumnInfo ( name = "guestName")
    var guestName: String,
    @ColumnInfo ( name = "liveFrom")
    var liveFrom: String,
    @ColumnInfo ( name = "liveTo" )
    var liveTo: String,
    @ColumnInfo ( name = "usPeople" )
    var usPeople: Boolean,
    @ColumnInfo ( name = "addInfo" )
    var addInfo:String
)


@Entity (tableName = "accounting")
data class AccountingItemModel (
    @PrimaryKey (autoGenerate = true)
    var id : Int? = null,
    @ColumnInfo ( name = "date")
    var date: String,
    @ColumnInfo ( name = "reason")
    var reason: String,
    @ColumnInfo ( name = "sum")
    var sum: Int,
    @ColumnInfo ( name = "profit" )
    var profit: Boolean
)