package com.example.hostelaccount.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

 class AccountingModel (
    var id : Int? = null,
    var date: String,
    var reason: String,
    var sum: Int,
    var profit: Boolean
)