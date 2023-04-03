package com.example.hostelaccount.viewmodel

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.hostelaccount.db.local.AccountingItemModel
import java.text.SimpleDateFormat
import java.util.*

class SortAccountingItems(val list: List<AccountingItemModel>) {
    fun getSortedList(): List<AccountingItemModel> {
        Log.d("TestMsg", "before: $list")
        val test = list.sortedByDescending { it.date.toDateOrNull() ?: Date(0) }
        Log.d("TestMsg", "result: $test")
        return test
    }

    private fun String.toDateOrNull(): Date? {
        return try {
            SimpleDateFormat("dd-MM-yy").parse(this)
        } catch (e: Exception) {
            null
        }
    }
}