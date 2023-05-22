package com.fedorov.andrii.andriiovych.alarmclock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.fedorov.andrii.andriiovych.alarmclock.databinding.ActivityMainBinding
import com.fedorov.andrii.andriiovych.alarmclock.fragments.MainFragment
import com.fedorov.andrii.andriiovych.alarmclock.fragments.SetTimeFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentNavigation(MAIN_FRAGMENT)

    }



  fun fragmentNavigation(fragment: String){
      val fragmentTransaction = supportFragmentManager.beginTransaction()
      when(fragment){
          SET_TIME_FRAGMENT -> fragmentTransaction.replace(R.id.container,SetTimeFragment()).addToBackStack("")
          MAIN_FRAGMENT -> fragmentTransaction.replace(R.id.container,MainFragment())
      }
      fragmentTransaction.commit()
    }

    companion object{
        const val SET_TIME_FRAGMENT = "SetTimeFragment"
        const val MAIN_FRAGMENT = "MainFragment"
    }
}