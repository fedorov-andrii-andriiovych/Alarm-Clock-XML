package com.fedorov.andrii.andriiovych.alarmclock.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fedorov.andrii.andriiovych.alarmclock.R
import com.fedorov.andrii.andriiovych.alarmclock.databinding.ActivityMainBinding
import com.fedorov.andrii.andriiovych.alarmclock.domain.models.AlarmModel
import com.fedorov.andrii.andriiovych.alarmclock.presentation.fragments.ChangeNoteFragment
import com.fedorov.andrii.andriiovych.alarmclock.presentation.fragments.MainFragment
import com.fedorov.andrii.andriiovych.alarmclock.presentation.fragments.SetTimeFragment
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