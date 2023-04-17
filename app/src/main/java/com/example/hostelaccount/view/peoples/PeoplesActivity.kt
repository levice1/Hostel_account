package com.example.hostelaccount.view.peoples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.ActivityPeoplesBinding
import com.example.hostelaccount.view.FragmentManageHelper
import com.example.hostelaccount.viewmodel.InitMenuChoise

class PeoplesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPeoplesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeoplesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        InitMenuChoise(this).initMenuChiose(binding.bottomNavigation)
        FragmentManageHelper(supportFragmentManager)
            .initFragment(R.id.fragmentLayoutPeoples , ListRoomsFragment.newInstance())
    }


    // делает пункт меню активным когда фокус перемещается на это активити
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            binding.bottomNavigation.selectedItemId = R.id.menu_rooms
        }
    }

    // на Peoples и Accounting активити, нажатие назад переходит на:
    // если включен фрагмент добавления - переход на фграмент списка.
    // если включен фрагмент списка - переход на фграмент списка главное активити (Statistic).
    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentLayoutPeoples)
        if (currentFragment is AddNewPeopleFragment) {
            // Показать фрагмент списка
            FragmentManageHelper(supportFragmentManager)
                .initFragment(R.id.fragmentLayoutPeoples ,ListRoomsFragment.newInstance())
        } else if (currentFragment is ListRoomsFragment) {
            // перейти на главное активити (Statistic)
            InitMenuChoise(this).startMainActivity()
            super.onBackPressed()
        }
    }
}