package com.example.hostelaccount.view.statistic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.ActivityMainBinding
import com.example.hostelaccount.data.data_sourse.PeopleItemModel
import com.example.hostelaccount.data.repository.AccountingRepositoryImpl
import com.example.hostelaccount.data.repository.PeopleRepositoryImpl
import com.example.hostelaccount.view.statistic.util.NotificationListAdapter
import com.example.hostelaccount.view.util.InitMenuChoise
import com.example.hostelaccount.viewmodel.statistic.StatisticViewModel


class StatisticActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var recyclerView: RecyclerView
    private val adapter = NotificationListAdapter()

    private lateinit var viewModel: StatisticViewModel

    private lateinit var repositoryPeople: PeopleRepositoryImpl
    private lateinit var repositoryAccounting: AccountingRepositoryImpl


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        InitMenuChoise(this).init(binding.bottomNavigation)
        viewModel = ViewModelProvider(this)[StatisticViewModel::class.java]
        repositoryPeople = PeopleRepositoryImpl(this@StatisticActivity)
        repositoryAccounting = AccountingRepositoryImpl(this@StatisticActivity)
    }


    override fun onResume() {
        super.onResume()
        viewModel.init(
            repositoryPeople,
            repositoryAccounting
        )

        // подсчёт количества людей ныне проживающих
        viewModel.state.residentsCount?.observe(this){
            binding.infNowLive.text = it.toString()
            Log.d("TestMsg", it.toString())
        }

        // подсчёт суммы денег в кассе
        viewModel.state.accountingAmount?.observe(this){
            binding.infSum.text = it.toString()
        }

        // отбор просроченых жильцов и вывод уведомлений
        viewModel.state.delayResidentsList?.observe(this) {
            if (it.isNotEmpty()) {
                binding.recViewNotifications.visibility = View.VISIBLE
                initNotificRecView()
                updateRecView(it)
            }
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
    @Suppress("DEPRECATION")
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