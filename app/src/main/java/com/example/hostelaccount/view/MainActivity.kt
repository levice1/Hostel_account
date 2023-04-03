package com.example.hostelaccount.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.hostelaccount.databinding.ActivityMainBinding
import com.example.hostelaccount.db.local.DbManager
import com.example.hostelaccount.model.GetCountAllPeoples
import com.example.hostelaccount.viewmodel.CalculatingMoney
import com.example.hostelaccount.viewmodel.CalculatingPeople
import com.example.hostelaccount.viewmodel.InitMenuChoise


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val getCountAllPeoples: GetCountAllPeoples by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        InitMenuChoise(this).initMenuChiose(binding.bottomNavigation)
    }

    override fun onResume() {
        super.onResume()
        val db = DbManager.getInstance(this)
        // подсчёт количества людей ныне проживающих
        CalculatingPeople(DbManager.getInstance(this),getCountAllPeoples.peoplesCountLiveData).getCountResidents()
        getCountAllPeoples.peoplesCountLiveData.observe(this) {
            binding.infNowLive.text = it.toString()
        }
        // подсчет суммы денег в кассе
        CalculatingMoney(db,this)
            .getSum()
            .observe(this){
                binding.infSum.text = it.toString()
            }
    }


    // на главном активити нажатие назад закрывает все активити и завершает работу приложения
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}