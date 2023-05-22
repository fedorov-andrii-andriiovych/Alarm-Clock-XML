package com.fedorov.andrii.andriiovych.alarmclock.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.fedorov.andrii.andriiovych.alarmclock.R
import com.fedorov.andrii.andriiovych.alarmclock.data.AlarmModel
import com.fedorov.andrii.andriiovych.alarmclock.databinding.FragmentSetTimeBinding
import com.fedorov.andrii.andriiovych.alarmclock.viewmodels.MainViewModel
import com.fedorov.andrii.andriiovych.alarmclock.viewmodels.MainViewModelModelFactory


class SetTimeFragment : Fragment() {
        lateinit var mainViewModel: MainViewModel
        lateinit var binding: FragmentSetTimeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainViewModel = ViewModelProvider(this,MainViewModelModelFactory())[MainViewModel::class.java]
        binding = FragmentSetTimeBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addButton.setOnClickListener { addAlarm() }
        binding.cancelButton.setOnClickListener { toMainFragment()  }
    }

    private fun addAlarm() {
        val hours = binding.hourEditText.text.toString()
        val minutes = binding.minuteEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()
        mainViewModel.insert(AlarmModel(time = "$hours:$minutes", description = description, isChecked = true))
        toMainFragment()
    }

    fun toMainFragment(){
        activity?.supportFragmentManager?.popBackStack()
    }


}