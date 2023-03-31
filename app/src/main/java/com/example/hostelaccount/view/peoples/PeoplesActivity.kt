package com.example.hostelaccount.view.peoples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.ActivityPeoplesBinding
import com.example.hostelaccount.viewmodel.InitMenuChoise

class PeoplesActivity : AppCompatActivity() {
    lateinit var binding: ActivityPeoplesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeoplesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        InitMenuChoise(this).initMenuChiose(binding.bottomNavigation)
        initFragment(R.id.fragmentLayoutPeoples , ListRoomsFragment.newInstance())
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