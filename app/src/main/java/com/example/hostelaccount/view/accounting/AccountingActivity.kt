package com.example.hostelaccount.view.accounting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.ActivityAccountingBinding
import com.example.hostelaccount.viewmodel.InitMenuChoise

class AccountingActivity : AppCompatActivity() {
    lateinit var binding: ActivityAccountingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        InitMenuChoise(this).initMenuChiose(binding.bottomNavigation)
        initFragment(R.id.fragmentLayoutAccounting ,AccountingListFragment.newInstance())
    }

    // на Peoples и Accounting активити, нажатие назад переходит на главное активити (Statistic)
    override fun onBackPressed() {
        InitMenuChoise(this).startMainActivity()
        super.onBackPressed()
    }
    private fun initFragment(idFrameLayout: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(idFrameLayout, fragment).commit()
    }
}