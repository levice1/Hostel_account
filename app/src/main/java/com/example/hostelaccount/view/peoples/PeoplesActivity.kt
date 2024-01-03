package com.example.hostelaccount.view.peoples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.ActivityPeoplesBinding
import com.example.hostelaccount.viewmodel.util.FragmentManageHelper
import com.example.hostelaccount.view.util.InitMenuChoice

@Suppress("DEPRECATION")
class PeoplesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPeoplesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Колхоз! Нужно изменить на адекватный вариант!
        supportActionBar!!.title = Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.app_name) + "</font>")

        binding = ActivityPeoplesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        InitMenuChoice(this).init(binding.bottomNavigation)
        FragmentManageHelper(supportFragmentManager)
            .initFragment(R.id.fragmentLayoutPeoples, ListRoomsFragment.newInstance())
    }


    // makes a menu item active when the focus is moved to that menu item
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            binding.bottomNavigation.selectedItemId = R.id.menu_rooms
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentLayoutPeoples)
        if (currentFragment is AddNewPeopleFragment) {
            // show a fragment of the list
            FragmentManageHelper(supportFragmentManager)
                .initFragment(R.id.fragmentLayoutPeoples, ListRoomsFragment.newInstance())
        } else if (currentFragment is ListRoomsFragment) {
            // go to the main activity (Statistic)
            InitMenuChoice(this).startMainActivity()
            super.onBackPressed()
        }
    }
}