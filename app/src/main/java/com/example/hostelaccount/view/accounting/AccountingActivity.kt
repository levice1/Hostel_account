package com.example.hostelaccount.view.accounting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.ActivityAccountingBinding

class AccountingActivity : AppCompatActivity() {
    lateinit var binding: ActivityAccountingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFragment(R.id.fragmentLayoutAccounting ,AccountingListFragment.newInstance())
    }
    private fun initFragment(idFrameLayout: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(idFrameLayout, fragment).commit()
    }
}