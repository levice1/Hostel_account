package com.example.hostelaccount.db.remote

import com.example.hostelaccount.db.local.PeopleItemModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


class RequestToRemoteDB(item: PeopleItemModel) {


    private val json = "{\"id\":\"${item.id}\",\"roomNumber\":\"${item.roomNumber}\",\"guestName\":\"${item.guestName}\",\"liveFrom\":\"${item.liveFrom}\",\"liveTo\":\"${item.liveTo}\",\"usPeople\":\"${item.usPeople}\",\"addInfo\":\"${item.addInfo}\"}"

    @OptIn(DelicateCoroutinesApi::class)
    fun execute() {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.setRequestProperty("Content-Type", "application/json")

        GlobalScope.launch{
            OutputStreamWriter(connection.outputStream).use { writer ->
                writer.write(json)
            }
        }

        val responseCode = connection.responseCode
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw RuntimeException("HTTP POST request failed with error code: $responseCode")
        }
    }
}