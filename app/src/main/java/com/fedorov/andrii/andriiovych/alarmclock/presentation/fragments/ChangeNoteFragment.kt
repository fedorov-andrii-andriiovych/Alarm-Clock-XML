package com.fedorov.andrii.andriiovych.alarmclock.presentation.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fedorov.andrii.andriiovych.alarmclock.R
import com.fedorov.andrii.andriiovych.alarmclock.databinding.FragmentChangeNoteBinding
import com.fedorov.andrii.andriiovych.alarmclock.domain.models.AlarmModel
import com.fedorov.andrii.andriiovych.alarmclock.presentation.MainActivity
import com.fedorov.andrii.andriiovych.alarmclock.presentation.broadcast.AlarmReceiver
import com.fedorov.andrii.andriiovych.alarmclock.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class ChangeNoteFragment : Fragment() {
    private val mainViewModel: MainViewModel by viewModels()
    lateinit var binding: FragmentChangeNoteBinding
    private var isShowDate: Boolean = false
    private var isShowTime: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val alarmModel = arguments?.getParcelable<AlarmModel>(MainActivity.NOTE)
        initState(alarmModel!!)
        binding.apply {
            cancelButton.setOnClickListener { toFragment() }
            editButton.setOnClickListener { saveChanges(alarmModel) }
            showDatePickerButton.setOnClickListener {
                isShowDate = !isShowDate
                showDate()
            }
            showTimePickerButton.setOnClickListener {
                isShowTime = !isShowTime
                showTime()
            }
        }
        initTimeAndDate(alarmModel)
    }
    private fun initTimeAndDate(alarmModel: AlarmModel){
        binding.apply {
            datePicker.init(alarmModel.year,alarmModel.month,alarmModel.day,null)
            timePicker.hour = alarmModel.hours
            timePicker.minute = alarmModel.minutes
        }
    }

    private fun saveChanges(model: AlarmModel) {
            val alarmModel = AlarmModel(
                id = model.id,
                hours = binding.timePicker.hour,
                minutes = binding.timePicker.minute,
                day = binding.datePicker.dayOfMonth,
                month = binding.datePicker.month,
                year = binding.datePicker.year,
                description = binding.descriptionEditText.text.toString()
            )
            mainViewModel.update(alarmModel)
        setAlarm(alarmModel)
        toFragment()
    }
    private fun setAlarm(alarmModel: AlarmModel) {
        val calendar = Calendar.getInstance()
        calendar.set(alarmModel.year,alarmModel.month,alarmModel.day,alarmModel.hours,alarmModel.minutes,0)
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        intent.putExtra(ID,alarmModel.id)
        intent.putExtra(DESCRIPTION, alarmModel.description)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            alarmModel.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
        Toast.makeText(requireContext(), getString(R.string.note_is_changed), Toast.LENGTH_SHORT).show()
    }

    private fun initState(alarmModel: AlarmModel) {
        binding.apply {
            descriptionEditText.setText(alarmModel.description)
        }
    }

    private fun showDate() {
        binding.apply {
            if (isShowDate) {
                datePicker.visibility = View.VISIBLE
                showDatePickerButton.text = getString(R.string.gone_show_date)
            } else {
                datePicker.visibility = View.GONE
                showDatePickerButton.text = getString(R.string.change_data)
            }
        }
    }

    private fun showTime() {
        binding.apply {
            if (isShowTime) {
                timePicker.visibility = View.VISIBLE
                showTimePickerButton.text = getString(R.string.gone_show_time)
            } else {
                timePicker.visibility = View.GONE
                showDatePickerButton.text = getString(R.string.change_time)
            }
        }
    }

    private fun toFragment() {
        activity?.supportFragmentManager?.popBackStack()
    }

    companion object {
        private const val ID = "ALERT_ID"
        private const val DESCRIPTION = "ALERT_DESCRIPTION"
    }

}