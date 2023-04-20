package com.example.hostelaccount.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelaccount.R
import com.example.hostelaccount.adapter.NotificationListAdapter
import com.example.hostelaccount.adapter.RoomListAdapter
import com.example.hostelaccount.databinding.ActivityMainBinding
import com.example.hostelaccount.db.local.DbManager
import com.example.hostelaccount.db.local.PeopleItemModel
import com.example.hostelaccount.model.PeopleIdViewModel
import com.example.hostelaccount.model.PeoplesViewModel
import com.example.hostelaccount.viewmodel.CalculatingMoney
import com.example.hostelaccount.viewmodel.CalculatingPeople
import com.example.hostelaccount.viewmodel.CreatingPeoplesList
import com.example.hostelaccount.viewmodel.InitMenuChoise


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: NotificationListAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var peoplesVM: PeoplesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        InitMenuChoise(this).initMenuChiose(binding.bottomNavigation)
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
                initNotificRecView(CreatingPeoplesList().createDelayList(it))
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

    fun initNotificRecView(list: List<PeopleItemModel>){
        recyclerView = binding.recViewNotificationList
        adapter = NotificationListAdapter()
        recyclerView.adapter = adapter
        adapter.setList(list)
    }
}