package com.example.hostelaccount.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import com.example.hostelaccount.databinding.ActivityMainBinding
import com.example.hostelaccount.db.DbManager
import com.example.hostelaccount.db.People


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
            val item = People(null,1,
                binding.edName.text.toString(),
                binding.edPrice.text.toString(),"12","none"
            )
//            Thread{
//                db.getDao().insertItem(item)
//            }.start()
        }
    }
}