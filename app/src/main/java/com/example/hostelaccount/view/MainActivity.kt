package com.example.hostelaccount.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelaccount.R
import com.example.hostelaccount.view.util.adapter.NotificationListAdapter
import com.example.hostelaccount.databinding.ActivityMainBinding
import com.example.hostelaccount.data.data_sourse.DbManager
import com.example.hostelaccount.data.data_sourse.PeopleItemModel
import com.example.hostelaccount.view.util.InitMenuChoise
import com.example.hostelaccount.viewmodel.util.CalculatingMoney
import com.example.hostelaccount.viewmodel.Peoples.util.CreatingPeoplesList


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var recyclerView: RecyclerView

    private val adapter = NotificationListAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        InitMenuChoise(this).init(binding.bottomNavigation)
        // выделить выбранный пункт меню
    }


    override fun onResume() {
        super.onResume()
        // Запрос в БД, таблицу Peoples
        DbManager.getInstance(this)
            .peopleDao()
            .getAll()
            .asLiveData()
            .observe(this as LifecycleOwner){
                // подсчёт количества людей ныне проживающих
                binding.infNowLive.text = CreatingPeoplesList().getCountResidents(it)
                // отбор просроченых жильцов и вывод уведомлений
                val delaysPeople = CreatingPeoplesList().createDelayList(it)
                if (delaysPeople.isNotEmpty()){
                    binding.recViewNotifications.visibility = View.VISIBLE
                    initNotificRecView()
                    updateRecView(delaysPeople)
                }
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
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }


    private fun initNotificRecView(){
        recyclerView = binding.recViewNotificationList
        recyclerView.adapter = adapter
    }


    private fun updateRecView(list: List<PeopleItemModel>){
        adapter.setList(list)
    }
}