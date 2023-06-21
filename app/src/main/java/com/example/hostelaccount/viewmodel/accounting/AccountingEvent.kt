package com.example.hostelaccount.viewmodel.accounting

import com.example.hostelaccount.data.data_sourse.AccountingItemModel

sealed class AccountingEvent {
    data class SaveTempItem(val tempItem: AccountingItemModel) : AccountingEvent()
    data class DeleteItem(val item: AccountingItemModel): AccountingEvent()
    data class SaveItem(val item: AccountingItemModel): AccountingEvent()
    object GetTempItem: AccountingEvent()
    object GetAccountingItems: AccountingEvent()
}