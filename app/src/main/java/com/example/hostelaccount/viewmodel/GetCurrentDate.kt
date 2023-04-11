package com.example.hostelaccount.viewmodel

import android.icu.text.SimpleDateFormat
import java.util.*

class GetCurrentDate {

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy") // указываем формат даты

    private val currentDate = Date() // получаем текущую дату и время

    private val dateStr: String = dateFormat.format(currentDate) // преобразуем дату в строку с помощью заданного формата

    fun getDate():String {
        return dateStr
    }

}