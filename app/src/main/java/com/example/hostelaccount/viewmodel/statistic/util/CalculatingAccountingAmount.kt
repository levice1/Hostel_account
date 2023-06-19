package com.example.hostelaccount.viewmodel.statistic.util

import com.example.hostelaccount.data.data_sourse.AccountingItemModel

class CalculatingAccountingAmount {

    fun invoke(items: List<AccountingItemModel>): Int {
        var totalSum = 0
        items.forEach { item ->
            val value = if (item.profit) item.sum else -item.sum
            totalSum += value
        }
        return totalSum
    }
}