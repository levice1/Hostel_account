package com.example.hostelaccount.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hostelaccount.databinding.ActivityMainBinding
import com.example.hostelaccount.view.peoples.PeoplesActivity


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val db = DbManager.getDb(this)
//        db.getDao().getAllItem().asLiveData().observe(this){ list->
//            binding.tvList.text = ""
//            list.forEach {
//                val text = "Id: ${it.id} Name: ${it.guestName} Price: ${it.addInfo}\n"
//                binding.tvList.append(text)
//            }
//        }


        binding.button2.setOnClickListener {
            val i = Intent(this, PeoplesActivity::class.java)
            startActivity(i)
        }
    }
}