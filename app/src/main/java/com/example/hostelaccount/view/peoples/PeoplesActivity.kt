package com.example.hostelaccount.view.peoples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.ActivityPeoplesBinding

class PeoplesActivity : AppCompatActivity() {
    lateinit var binding: ActivityPeoplesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeoplesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFragment(R.id.fragmentLayoutPeoples , ListRoomsFragment.newInstance())
    }

    private fun initFragment(idFrameLayout: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(idFrameLayout, fragment).commit()
    }
}