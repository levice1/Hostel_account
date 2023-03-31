package com.example.hostelaccount.viewmodel

import android.content.Context
import androidx.lifecycle.asLiveData
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hostelaccount.db.AccountingItemModel
import com.example.hostelaccount.db.DbManager

class CalculatingMoney(val db: DbManager, val context: Context, val owner: LifecycleOwner = context as LifecycleOwner) {

    fun getSum() : LiveData<Int> {
        val totalSum = MutableLiveData<Int>()
        DbManager.getInstance(context)
            .accountingDao()
            .getAll()
            .asLiveData()
            .observe( owner ){
                totalSum.value = calculateTotalSum(it)
            }
        return totalSum
    }

    fun calculateTotalSum(items: List<AccountingItemModel>): Int {
        var totalSum = 0
        items.forEach { item ->
            val value = if (item.profit) item.sum else -item.sum
            totalSum += value
        }
        return totalSum
    }


}