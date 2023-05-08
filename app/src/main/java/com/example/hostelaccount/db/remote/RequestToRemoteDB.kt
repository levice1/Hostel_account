package com.example.hostelaccount.db.remote

import com.example.hostelaccount.db.local.AccountingItemModel
import com.example.hostelaccount.db.local.PeopleItemModel
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


class RequestToRemoteDB(private val process: String) {
    private val url = BackendConstants().url


    fun insertToPeople(item: PeopleItemModel) {
        val peoplesJson = "{\"id\":\"${item.id}\",\"roomNumber\":\"${item.roomNumber}\",\"guestName\":\"${item.guestName}\",\"liveFrom\":\"${item.liveFrom}\",\"liveTo\":\"${item.liveTo}\",\"usPeople\":\"${item.usPeople}\",\"addInfo\":\"${item.addInfo}\",\"process\":\"${process}\"}"
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.setRequestProperty("Content-Type", "application/json")

            OutputStreamWriter(connection.outputStream).use { writer ->
                writer.write(peoplesJson)
            }

        val responseCode = connection.responseCode
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw RuntimeException("HTTP POST request failed with error code: $responseCode")
        }
    }

    fun insertToAccounting(item: AccountingItemModel){
        val accountingJson = "{\"id\":\"${item.id}\",\"date\":\"${item.date}\",\"sum\":\"${item.sum}\",\"reason\":\"${item.reason}\",\"profit\":\"${item.profit}\",\"process\":\"${process}\"}"
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.setRequestProperty("Content-Type", "application/json")

        OutputStreamWriter(connection.outputStream).use { writer ->
            writer.write(accountingJson)
        }

        val responseCode = connection.responseCode
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw RuntimeException("HTTP POST request failed with error code: $responseCode")
        }
    }
}