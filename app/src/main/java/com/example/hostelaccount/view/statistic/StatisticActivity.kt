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
import com.example.hostelaccount.view.util.InitMenuChoice
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
        InitMenuChoice(this).init(binding.bottomNavigation)
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

        // counting the number of people currently living
        viewModel.state.residentsCount?.observe(this){
            binding.infNowLive.text = it.toString()
            Log.d("TestMsg", it.toString())
        }

        // counting the amount of money in the cash register
        viewModel.state.accountingAmount?.observe(this){
            binding.infSum.text = it.toString()
        }

        // selecting overdue tenants and displaying notifications
        viewModel.state.delayResidentsList?.observe(this) {
            if (it.isNotEmpty()) {
                binding.recViewNotifications.visibility = View.VISIBLE
                initNotificationRecView()
                updateRecView(it)
            }
        }
    }



    // makes a menu item active when the focus is moved to that menu item
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            binding.bottomNavigation.selectedItemId = R.id.menu_main
        }
    }


    // on the main activity, pressing backwards closes all activities and terminates the application
    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }


    private fun initNotificationRecView(){
        recyclerView = binding.recViewNotificationList
        recyclerView.adapter = adapter
    }


    private fun updateRecView(list: List<PeopleItemModel>){
        adapter.setList(list)
    }
}