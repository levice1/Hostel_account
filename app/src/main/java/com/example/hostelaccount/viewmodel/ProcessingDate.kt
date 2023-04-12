package com.example.hostelaccount.viewmodel

import android.icu.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.sign

class ProcessingDate() {

    private val dateFormat = SimpleDateFormat("dd.MM.yy") // указываем формат даты


    fun getCurrentDate(): String {
        return dateFormat.format(Date())
    }

    fun getDaysDifference(checkingDate: String): Int {
        return if (checkingDate == "--.--.--" || checkingDate == "") {
            30
        } else {
            val currentDate = Date().time
            val checkDate = dateFormat.parse(checkingDate).time
            val difference = checkDate - currentDate
            val days = difference.toInt() / (1000 * 60 * 60 * 24)
            if (checkDate <= currentDate) {
                -days
            } else {
                days
            }
        }
    }


    fun calculateDaysDifference(dateString: String): Int {
        if (dateString == "--.--.--" || dateString == "") return   30
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        val currentDate = Date()
        val date = dateFormat.parse(dateString)
        val differenceInMillis = date.time - currentDate.time
        val differenceInDays = differenceInMillis / (1000 * 60 * 60 * 24)
        return differenceInDays.toInt()
    }
}











//    fun getDaysFromStartUnix(): Int {
//        val calendar = Calendar.getInstance() // создаем объект Calendar для текущей временной зоны
//        calendar.set(1970, 1, 1) // устанавливаем дату 1 января 1970 года
//        val currentDate = calendar.time // получаем объект Date для указанной даты
//        val currentTimeDays = (System.currentTimeMillis() - currentDate.time) / (24 * 60 * 60 * 1000) // вычисляем количество дней, прошедших с начала эпохи UNIX
//        return currentTimeDays.toInt()
//    }

//    fun getDaysFromStartUnixToDate(inputDate:String): Int {
//
//        val inputDateParts = inputDate.split(".") // разделяем строку по символу "."
//
//        val inputDay = inputDateParts[0].toInt()
//        val inputMonth = inputDateParts[1].toInt()
//        var inputYear = inputDateParts[2]
//        if (inputYear.length == 2) inputYear = "20$inputYear"
//
//        val calendar = Calendar.getInstance() // создаем объект Calendar для текущей временной зоны
//        calendar.set(1970,1 ,1 ) // устанавливаем дату 1 января 1970 года
//        val currentDate = calendar.time // получаем объект Date для указанной даты
//        val currentTimeDays = (System.currentTimeMillis() - currentDate.time) / (24 * 60 * 60 * 1000) // вычисляем количество дней, прошедших с начала эпохи UNIX
//        return currentTimeDays.toInt()
//    }





