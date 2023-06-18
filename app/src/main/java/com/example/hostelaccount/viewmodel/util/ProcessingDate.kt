package com.example.hostelaccount.viewmodel.util

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import java.util.*

open class ProcessingDate {

     @SuppressLint("SimpleDateFormat")
     val dateFormat = SimpleDateFormat("dd.MM.yy") // указываем формат даты


    fun getCurrentDate(): String {
        return dateFormat.format(Date())
    }


    fun calculateDaysDifference(dateString: String): Int {
        if (dateString == "--.--.--" || dateString == "") return 31
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        val currentDate = Date()
        val date = dateFormat.parse(dateString)
        val differenceInMillis = date.time - currentDate.time
        val differenceInDays = differenceInMillis / (1000 * 60 * 60 * 24)
        return differenceInDays.toInt()
    }
}