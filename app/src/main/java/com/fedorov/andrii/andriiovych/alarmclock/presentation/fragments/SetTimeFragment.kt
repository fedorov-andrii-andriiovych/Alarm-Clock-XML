package com.fedorov.andrii.andriiovych.alarmclock.presentation.fragments

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fedorov.andrii.andriiovych.alarmclock.R
import com.fedorov.andrii.andriiovych.alarmclock.databinding.FragmentSetTimeBinding
import com.fedorov.andrii.andriiovych.alarmclock.domain.models.AlarmModel
import com.fedorov.andrii.andriiovych.alarmclock.presentation.broadcast.AlarmReceiver
import com.fedorov.andrii.andriiovych.alarmclock.presentation.viewmodels.MainViewModel
import com.fedorov.andrii.andriiovych.alarmclock.presentation.viewmodels.MainViewModelModelFactory
import java.util.*


class SetTimeFragment : Fragment() {
    lateinit var mainViewModel: MainViewModel
    lateinit var binding: FragmentSetTimeBinding
    var calendar: Calendar = Calendar.getInstance()
    private lateinit var description:String

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
        binding.timePicker.setIs24HourView(true)
        binding.addButton.setOnClickListener { addAlarm() }
        binding.cancelButton.setOnClickListener { toMainFragment() }
        binding.showDateButton.setOnClickListener { showDatePicker() }
        mainViewModel.alarmId.observe(viewLifecycleOwner) { id ->
            setAlarm(calendar.timeInMillis, id)
        }
        initTime()
    }

    private fun initTime() {
        binding.apply {
            dayTextView.text = calendar.get(Calendar.DAY_OF_MONTH).toString()
            monthTextView.text = String.format("%02d", calendar.get(Calendar.MONTH) + 1)
            yearTextView.text = calendar.get(Calendar.YEAR).toString()
        }
    }

    private fun showDatePicker() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            binding.apply {
                dayTextView.text = selectedDay.toString()
                monthTextView.text = String.format("%02d", selectedMonth + 1)
                yearTextView.text = selectedYear.toString()
            }
        }, year, month, day).show()
    }

    private fun addAlarm() {
        val hours = binding.timePicker.hour
        val minutes = binding.timePicker.minute
        val day = binding.dayTextView.text.toString().toInt()
        val month = binding.monthTextView.text.toString().toInt()
        val year = binding.yearTextView.text.toString().toInt()
        description =
            binding.descriptionEditText.text.toString().ifEmpty { getString(R.string.new_note) }
        calendar.set(
            year,
            month - 1,
            day,
            hours,
            minutes,
            0
        )
        mainViewModel.insert(
            AlarmModel(
                hours = hours,
                minutes = minutes,
                description = description,
                day = day,
                month = month,
                year = year
            )
        )
    }

    private fun setAlarm(time: Long, id: Long) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        intent.putExtra(ID, id.toInt())
        intent.putExtra(DESCRIPTION, description)
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

        Toast.makeText(requireContext(), "Заметка сохранена", Toast.LENGTH_SHORT).show()
        toMainFragment()
    }

    private fun toMainFragment() {
        activity?.supportFragmentManager?.popBackStack()
    }

    companion object {
        const val ID = "ALERT_ID"
        const val DESCRIPTION = "ALERT_DESCRIPTION"
    }

}