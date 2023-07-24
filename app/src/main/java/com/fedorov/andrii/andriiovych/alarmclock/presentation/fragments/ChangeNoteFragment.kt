package com.fedorov.andrii.andriiovych.alarmclock.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fedorov.andrii.andriiovych.alarmclock.R
import com.fedorov.andrii.andriiovych.alarmclock.databinding.FragmentChangeNoteBinding
import com.fedorov.andrii.andriiovych.alarmclock.domain.models.AlarmModel
import com.fedorov.andrii.andriiovych.alarmclock.presentation.AlarmCreator
import com.fedorov.andrii.andriiovych.alarmclock.presentation.viewmodels.AlarmViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChangeNoteFragment : Fragment() {
    private val alarmViewModel: AlarmViewModel by viewModels()
    lateinit var binding: FragmentChangeNoteBinding
    private var isShowDate: Boolean = false
    private var isShowTime: Boolean = false

    @Inject
    lateinit var alarmCreator: AlarmCreator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val alarmModel = arguments?.getParcelable<AlarmModel>(MainFragment.NOTE)!!
        binding.apply {
            timePicker.setIs24HourView(true)
            cancelButton.setOnClickListener {
               toMainScreen()
            }
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

    private fun initTimeAndDate(alarmModel: AlarmModel) {
        binding.apply {
            datePicker.init(alarmModel.year, alarmModel.month, alarmModel.day, null)
            timePicker.hour = alarmModel.hours
            timePicker.minute = alarmModel.minutes
            descriptionEditText.setText(alarmModel.description)
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
        alarmViewModel.update(alarmModel = alarmModel)
        val result = setAlarm(alarmModel)
        if (result) toMainScreen()
    }

    private fun setAlarm(alarmModel: AlarmModel): Boolean {
        val result = alarmCreator.createAlarm(alarmModel = alarmModel)
        Toast.makeText(requireContext(), getString(R.string.note_is_changed), Toast.LENGTH_SHORT)
            .show()
        return result
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

    private fun toMainScreen() {
        this.findNavController().navigate(R.id.action_changeNoteFragment_to_mainFragment2)
    }
}