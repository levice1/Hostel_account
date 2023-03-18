package com.example.hostelaccount.view.peoples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hostelaccount.R
import com.example.hostelaccount.databinding.ActivityPeoplesBinding

class PeoplesActivity : AppCompatActivity() {
    lateinit var binding: ActivityPeoplesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPeoplesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}