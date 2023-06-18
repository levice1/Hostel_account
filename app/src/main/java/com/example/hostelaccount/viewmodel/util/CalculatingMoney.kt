package com.example.hostelaccount.viewmodel.util

import android.content.Context
import androidx.lifecycle.asLiveData
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hostelaccount.data.data_sourse.AccountingItemModel
import com.example.hostelaccount.data.data_sourse.DbManager

class CalculatingMoney(val context: Context) {

    fun getSum() : LiveData<Int> {
        val totalSum = MutableLiveData<Int>()
        DbManager.getInstance(context)
            .accountingDao()
            .getAll()
            .asLiveData()
            .observe( context as LifecycleOwner ){
                totalSum.value = calculateTotalSum(it)
            }
        return totalSum
    }

    private fun calculateTotalSum(items: List<AccountingItemModel>): Int {
        var totalSum = 0
        items.forEach { item ->
            val value = if (item.profit) item.sum else -item.sum
            totalSum += value
        }
        return totalSum
    }


}