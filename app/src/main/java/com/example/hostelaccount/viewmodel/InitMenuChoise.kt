package com.example.hostelaccount.viewmodel

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.hostelaccount.R
import com.example.hostelaccount.view.MainActivity
import com.example.hostelaccount.view.accounting.AccountingActivity
import com.example.hostelaccount.view.peoples.PeoplesActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class InitMenuChoise(val context: Context) {

    fun initMenuChiose(navi: BottomNavigationView) {
        navi.setOnItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.menu_main -> startMainActivity()
                R.id.menu_rooms -> startPeoplesActivity()
                R.id.menu_accounting -> startAccountingActivity()
                else -> throw IllegalArgumentException("Invalid menu item selected")
            }
        }
    }


    private fun startPeoplesActivity(): Boolean{
        val intent = Intent(context, PeoplesActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        startActivity(context, intent, null)
        return true
    }


    private fun startAccountingActivity(): Boolean{
        val intent = Intent(context, AccountingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        startActivity(context, intent, null)
        return true
    }


    fun startMainActivity(): Boolean{
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        startActivity(context, intent, null)
        return true
    }
}