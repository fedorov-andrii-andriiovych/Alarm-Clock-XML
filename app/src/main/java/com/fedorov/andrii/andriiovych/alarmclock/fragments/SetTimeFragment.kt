package com.fedorov.andrii.andriiovych.alarmclock.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        mainViewModel =
            ViewModelProvider(this, MainViewModelModelFactory())[MainViewModel::class.java]
        binding = FragmentSetTimeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addButton.setOnClickListener { addAlarm() }
        binding.cancelButton.setOnClickListener { toMainFragment() }
        binding.hourEditText.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(text: Editable?) {
                val input = text.toString()
                if (input.isNotEmpty()){
                    val hour = input.toInt()
                    if (hour > 23){
                        binding.hourEditText.setText("23")
                        binding.hourEditText.setSelection(input.length)
                    }
                }
            }
        })
        binding.minuteEditText.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(text: Editable?) {
                val input = text.toString()
                if (input.isNotEmpty()){
                    val hour = input.toInt()
                    if (hour > 59){
                        binding.minuteEditText.setText("59")
                        binding.minuteEditText.setSelection(input.length)
                    }
                }
            }

        })
    }

    private fun addAlarm() {
        val hours = binding.hourEditText.text.toString().toInt()
        val minutes = binding.minuteEditText.text.toString().toInt()
        val description = binding.descriptionEditText.text.toString()
        mainViewModel.insert(
            AlarmModel(
                hours = hours,
                minutes = minutes,
                description = description,
                isChecked = true
            )
        )
        toMainFragment()
    }

    fun toMainFragment() {
        activity?.supportFragmentManager?.popBackStack()
    }


}