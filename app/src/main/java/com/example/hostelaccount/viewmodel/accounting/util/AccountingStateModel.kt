package com.example.hostelaccount.viewmodel.accounting.util

import com.example.hostelaccount.data.data_sourse.AccountingItemModel

data class AccountingStateModel(
    val tempAccountingItem: AccountingItemModel?,
    val listAccountingItems: List<AccountingItemModel>?
)
