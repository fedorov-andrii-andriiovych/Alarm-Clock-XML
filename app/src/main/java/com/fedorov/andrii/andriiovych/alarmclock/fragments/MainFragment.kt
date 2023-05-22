package com.fedorov.andrii.andriiovych.alarmclock.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fedorov.andrii.andriiovych.alarmclock.MainActivity
import com.fedorov.andrii.andriiovych.alarmclock.adapters.AlarmActionListener
import com.fedorov.andrii.andriiovych.alarmclock.adapters.MainAdapter
import com.fedorov.andrii.andriiovych.alarmclock.data.AlarmModel
import com.fedorov.andrii.andriiovych.alarmclock.databinding.FragmentMainBinding
import com.fedorov.andrii.andriiovych.alarmclock.viewmodels.MainViewModel
import com.fedorov.andrii.andriiovych.alarmclock.viewmodels.MainViewModelModelFactory


class MainFragment : Fragment() {
    lateinit var adapter: MainAdapter
    lateinit var viewModel: MainViewModel
    lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, MainViewModelModelFactory())[MainViewModel::class.java]
        binding = FragmentMainBinding.inflate(inflater, container, false)

        adapter = MainAdapter(object : AlarmActionListener {
            override fun onAlarmDelete(alarmModel: AlarmModel) {
                val alertDialogBuilder = AlertDialog.Builder(context)
                alertDialogBuilder.setTitle("Вы точно хотите удалить?")
                alertDialogBuilder.setPositiveButton("Да") { dialog, _ ->
                    viewModel.delete(alarmModel)
                    dialog.dismiss()
                }
                alertDialogBuilder.setNegativeButton("Нет") { dialog, _ ->
                    dialog.dismiss()
                }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        })
        val layoutManager = LinearLayoutManager(activity)
        binding.rcView.layoutManager = layoutManager
        binding.rcView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addAlarmButton.setOnClickListener { addAlarm() }
        viewModel.getAll().observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }

    private fun addAlarm() {
        val activity = activity as MainActivity
        activity.fragmentNavigation(MainActivity.SET_TIME_FRAGMENT)
    }

}