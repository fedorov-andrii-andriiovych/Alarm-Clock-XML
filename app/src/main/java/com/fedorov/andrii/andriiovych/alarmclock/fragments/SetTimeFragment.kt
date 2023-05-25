package com.fedorov.andrii.andriiovych.alarmclock.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.fedorov.andrii.andriiovych.alarmclock.broadcast.AlarmReceiver
import com.fedorov.andrii.andriiovych.alarmclock.data.AlarmModel
import com.fedorov.andrii.andriiovych.alarmclock.databinding.FragmentSetTimeBinding
import com.fedorov.andrii.andriiovych.alarmclock.viewmodels.MainViewModel
import com.fedorov.andrii.andriiovych.alarmclock.viewmodels.MainViewModelModelFactory
import java.util.Calendar


class SetTimeFragment : Fragment() {
    lateinit var mainViewModel: MainViewModel
    lateinit var binding: FragmentSetTimeBinding
    lateinit var calendar: Calendar

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
        binding.showTimeButton.setOnClickListener { showTimePicker() }
        binding.showDateButton.setOnClickListener { showDatePicker() }
        mainViewModel.alarmId.observe(viewLifecycleOwner) { id ->
            setAlarm(calendar.timeInMillis, id)
        }
    }

    private fun showDatePicker() {
        TODO("Not yet implemented")
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
            binding.hourEditText.text =String.format("%02d",selectedHour)
            binding.minuteEditText.text = String.format("%02d",selectedMinute)
        }, hour, minute, true)
        timePickerDialog.show()
    }


    private fun addAlarm() {
        val hours = binding.hourEditText.text.toString().toInt()
        val minutes = binding.minuteEditText.text.toString().toInt()
        val description = binding.descriptionEditText.text.toString()
        calendar = Calendar.getInstance()
        calendar.set(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            hours,
            minutes,
            0
        )
        mainViewModel.insert(
            AlarmModel(
                hours = hours,
                minutes = minutes,
                description = description,
                isChecked = true
            )
        )
    }

    private fun setAlarm(time: Long, id: Long) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
        time,
        pendingIntent
        )

        Toast.makeText(requireContext(), "Будильник установлен", Toast.LENGTH_SHORT).show()
        toMainFragment()
    }

    private fun toMainFragment() {
        activity?.supportFragmentManager?.popBackStack()
    }


}