package com.fedorov.andrii.andriiovych.alarmclock.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fedorov.andrii.andriiovych.alarmclock.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}