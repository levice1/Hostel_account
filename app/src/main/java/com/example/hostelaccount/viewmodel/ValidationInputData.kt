package com.example.hostelaccount.viewmodel

import java.text.ParseException

class ValidationInputData : ProcessingDate() {

    fun validateDateStr(str: String): Boolean {
        val  dF = ProcessingDate().dateFormat
        dF.isLenient = false
        return try {
            dF.parse(str)
            true
        } catch (e: ParseException) {
            false
        }
    }

    fun validateNameStr(str: String):Boolean {
        val name = str.trim()
        return name.isNotEmpty()
    }

    fun validateIntNum(str: String): Boolean {
        val roomNum = str.trim().toIntOrNull()
        return roomNum != null && roomNum >= 0
    }

}