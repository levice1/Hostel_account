package com.example.hostelaccount.view.accounting

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.ActivityAccountingBinding
import com.example.hostelaccount.viewmodel.util.FragmentManageHelper
import com.example.hostelaccount.view.util.InitMenuChoice

@Suppress("DEPRECATION")
class AccountingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Колхоз! Нужно изменить на адекватный вариант!
        supportActionBar!!.title = Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.app_name) + "</font>")

        binding = ActivityAccountingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        InitMenuChoice(this).init(binding.bottomNavigation)
        FragmentManageHelper(supportFragmentManager)
            .initFragment(R.id.fragmentLayoutAccounting, AccountingListFragment.newInstance())
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            binding.bottomNavigation.selectedItemId = R.id.menu_accounting
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentLayoutAccounting)
        if (currentFragment is AccountingAddNewEntryFragment) {
            // go to list fragment
            FragmentManageHelper(supportFragmentManager)
                .initFragment(R.id.fragmentLayoutAccounting, AccountingListFragment.newInstance())
        } else if (currentFragment is AccountingListFragment) {
            // go to main activity (Statistic)
            InitMenuChoice(this).startMainActivity()
            super.onBackPressed()
        }
    }
}