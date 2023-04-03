package com.example.hostelaccount.view.accounting

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.ActivityAccountingBinding
import com.example.hostelaccount.view.FragmentManageHelper
import com.example.hostelaccount.viewmodel.InitMenuChoise

class AccountingActivity : AppCompatActivity() {
    lateinit var binding: ActivityAccountingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        InitMenuChoise(this).initMenuChiose(binding.bottomNavigation)
        FragmentManageHelper(supportFragmentManager)
            .initFragment(R.id.fragmentLayoutAccounting ,AccountingListFragment.newInstance())
    }

    // на Peoples и Accounting активити, нажатие назад переходит на главное активити (Statistic)
    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentLayoutAccounting)
        if (currentFragment is AccountingAddNewEntryFragment) {
            // Показать фрагмент списка
            FragmentManageHelper(supportFragmentManager)
                .initFragment(R.id.fragmentLayoutAccounting ,AccountingListFragment.newInstance())
        } else if (currentFragment is AccountingListFragment) {
            // перейти на главное активити (Statistic)
            InitMenuChoise(this).startMainActivity()
            super.onBackPressed()
        }
    }
}