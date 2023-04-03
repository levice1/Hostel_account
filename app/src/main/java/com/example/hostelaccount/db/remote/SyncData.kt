package com.example.hostelaccount.db.remote

import android.util.Log
import com.example.hostelaccount.db.local.PeopleItemModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SyncData {

    val retrofit = Retrofit.Builder()
        .baseUrl("http://a043um.forpsi.com/f146078/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val hostelAPI = retrofit.create(HostelAPI::class.java)

    val peopleList = listOf(
        PeopleItemModel(
            null,
            101,
            "John Doe",
            "2022-04-03",
            "2022-04-10",
            true,
            "Some additional info"
        ),
        PeopleItemModel(
            null,
            102,
            "Jane Smith",
            "2022-04-03",
            "2022-04-10",
            true,
            "Some additional info"
        )
    )



    fun sendDBData(){
        val call = hostelAPI.insertPeople(peopleList)
        call.enqueue(object : Callback<Void> {
        override fun onResponse(call: Call<Void>, response: Response<Void>) {
            if (response.isSuccessful) {
                Log.d("TestMsg", "Data uploaded successfully")
            } else {
                Log.e("TestMsg", "Failed to upload data")
            }
        }

        override fun onFailure(call: Call<Void>, t: Throwable) {
            Log.e("TestMsg", "Error uploading data", t)
        }
    })
}}