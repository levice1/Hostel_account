package com.example.hostelaccount.view.accounting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.ActivityAccountingBinding
import com.example.hostelaccount.viewmodel.util.FragmentManageHelper
import com.example.hostelaccount.view.util.InitMenuChoise

class AccountingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        InitMenuChoise(this).init(binding.bottomNavigation)
        FragmentManageHelper(supportFragmentManager)
            .initFragment(R.id.fragmentLayoutAccounting ,AccountingListFragment.newInstance())
    }


    // делает пункт меню активным когда фокус перемещается на это активити
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            binding.bottomNavigation.selectedItemId = R.id.menu_accounting
        }
    }

    // на Peoples и Accounting активити, нажатие назад переходит на главное активити (Statistic)
    @Deprecated("Deprecated in Java")
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