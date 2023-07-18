package com.fedorov.andrii.andriiovych.alarmclock.presentation.fragments

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
import com.fedorov.andrii.andriiovych.alarmclock.presentation.AlarmCreator
import com.fedorov.andrii.andriiovych.alarmclock.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SetTimeFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    lateinit var binding: FragmentSetTimeBinding
    private lateinit var alarmModel: AlarmModel
    private var isDatePickerShow = false

    @Inject
    lateinit var alarmCreator: AlarmCreator

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
            addButton.setOnClickListener { saveModel() }
            cancelButton.setOnClickListener { toMainFragment() }
            showDatePickerButton.setOnClickListener {
                isDatePickerShow = !isDatePickerShow
                showDatePickerButton()
            }
        }
        mainViewModel.alarmId.observe(viewLifecycleOwner) { id ->
            setAlarm(alarmModel = alarmModel, id = id)
        }
    }

    private fun showDatePickerButton() {
        binding.apply {
            if (isDatePickerShow) {
                datePicker.visibility = View.VISIBLE
                showDatePickerButton.text = getString(R.string.gone_show_date)
            } else {
                datePicker.visibility = View.GONE
                showDatePickerButton.text = getString(R.string.choice_date)
            }
        }

    }

    private fun saveModel() {
        val model = AlarmModel(
            hours = binding.timePicker.hour,
            minutes = binding.timePicker.minute,
            day = binding.datePicker.dayOfMonth,
            month = binding.datePicker.month,
            year = binding.datePicker.year,
            description = binding.descriptionEditText.text.toString()
                .ifEmpty { getString(R.string.new_note) }
        )
        alarmModel = model
        mainViewModel.insert(alarmModel = model)
    }

    private fun setAlarm(alarmModel: AlarmModel, id: Long) {
        val result = alarmCreator.createAlarm(alarmModel = alarmModel.copy(id = id.toInt()))
        Toast.makeText(requireContext(), getString(R.string.note_is_saved), Toast.LENGTH_SHORT)
            .show()
        if (result) toMainFragment()
    }

    private fun toMainFragment() {
        activity?.supportFragmentManager?.popBackStack()
    }
}