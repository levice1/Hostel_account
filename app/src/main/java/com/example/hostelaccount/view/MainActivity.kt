package com.example.hostelaccount.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.ActivityMainBinding
import com.example.hostelaccount.viewmodel.CalculatingMoney
import com.example.hostelaccount.viewmodel.CalculatingPeople
import com.example.hostelaccount.viewmodel.InitMenuChoise


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        InitMenuChoise(this).initMenuChiose(binding.bottomNavigation)
        // выделить выбранный пункт меню
    }

    override fun onResume() {
        super.onResume()
        // подсчёт количества людей ныне проживающих
        CalculatingPeople(this)
            .getCountResidents()
            .observe(this){
            binding.infNowLive.text = it.toString()
        }
        // подсчет суммы денег в кассе
        CalculatingMoney(this)
            .getSum()
            .observe(this){
                binding.infSum.text = it.toString()
            }
    }


    // делает пункт меню активным когда фокус перемещается на это активити
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            binding.bottomNavigation.selectedItemId = R.id.menu_main
        }
    }


    // на главном активити нажатие назад закрывает все активити и завершает работу приложения
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}