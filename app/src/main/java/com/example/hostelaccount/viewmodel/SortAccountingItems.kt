package com.example.hostelaccount.viewmodel

import com.example.hostelaccount.db.local.AccountingItemModel
import java.text.SimpleDateFormat
import java.util.*


// Old. To delete!
class SortAccountingItems(val list: List<AccountingItemModel>) {
    fun getSortedList(): List<AccountingItemModel> {
        val test = list.sortedByDescending { it.date.toDateOrNull() ?: Date(0) }
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