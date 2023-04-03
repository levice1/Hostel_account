package com.example.hostelaccount.viewmodel

import com.example.hostelaccount.db.local.AccountingItemModel
import java.text.SimpleDateFormat
import java.util.*

class SortAccountingItems(val list: List<AccountingItemModel>) {
    fun getSortedList(): List<AccountingItemModel> {
        val sortedEvents = list.sortedBy {
            try {
                SimpleDateFormat("dd-MM-yy").parse(it.date)
            } catch (e: Exception) {
                // Если не удается преобразовать дату, вернуть значение по умолчанию (Date(0))
                Date(0)
            }
        }
        return sortedEvents
    }
}