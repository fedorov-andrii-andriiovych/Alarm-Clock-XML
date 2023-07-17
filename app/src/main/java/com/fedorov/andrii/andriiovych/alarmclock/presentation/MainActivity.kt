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

        if (savedInstanceState == null) fragmentNavigation(null, MAIN_FRAGMENT)
    }

    fun fragmentNavigation(alarmModel: AlarmModel?, fragment: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        when (fragment) {
            SET_TIME_FRAGMENT -> fragmentTransaction.replace(R.id.container, SetTimeFragment())
                .addToBackStack("set_time_fragment")
            MAIN_FRAGMENT -> fragmentTransaction.replace(R.id.container, MainFragment())
            CHANGE_NOTE_FRAGMENT -> {
                val fragment = ChangeNoteFragment()
                fragment.arguments = Bundle().apply { putParcelable(NOTE, alarmModel) }
                fragmentTransaction.replace(R.id.container, fragment)
                    .addToBackStack("change_note_fragment")
            }
        }
        fragmentTransaction.commit()
    }

    companion object {
        const val SET_TIME_FRAGMENT = "SetTimeFragment"
        const val MAIN_FRAGMENT = "MainFragment"
        const val CHANGE_NOTE_FRAGMENT = "ChangeNoteFragment"
        const val NOTE = " note"
    }
}