package com.fedorov.andrii.andriiovych.alarmclock.presentation.fragments

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fedorov.andrii.andriiovych.alarmclock.R
import com.fedorov.andrii.andriiovych.alarmclock.databinding.FragmentSetTimeBinding
import com.fedorov.andrii.andriiovych.alarmclock.domain.models.AlarmModel
import com.fedorov.andrii.andriiovych.alarmclock.presentation.broadcast.AlarmReceiver
import com.fedorov.andrii.andriiovych.alarmclock.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class SetTimeFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    lateinit var binding: FragmentSetTimeBinding
    var calendar: Calendar = Calendar.getInstance()
    private lateinit var description: String
    private var isDatePickerShow = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetTimeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            timePicker.setIs24HourView(true)
            addButton.setOnClickListener { addAlarm() }
            cancelButton.setOnClickListener { toMainFragment() }
            showDatePickerButton.setOnClickListener {
                isDatePickerShow = !isDatePickerShow
                showDatePickerButton()
            }
        }
        mainViewModel.alarmId.observe(viewLifecycleOwner) { id ->
            setAlarm(calendar.timeInMillis, id)
        }
    }

    private fun showDatePickerButton() {
        binding.apply {
            if (isDatePickerShow) {
                datePicker.visibility = View.VISIBLE
                showDatePickerButton.text = getString(R.string.gone_show_date)
            }
            else {
                datePicker.visibility = View.GONE
                showDatePickerButton.text = getString(R.string.choice_date)
            }
        }

    }

    private fun addAlarm() {
        val hours = binding.timePicker.hour
        val minutes = binding.timePicker.minute
        val day = binding.datePicker.dayOfMonth
        val month = binding.datePicker.month
        val year = binding.datePicker.year
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

        Toast.makeText(requireContext(), getString(R.string.note_is_saved), Toast.LENGTH_SHORT).show()
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