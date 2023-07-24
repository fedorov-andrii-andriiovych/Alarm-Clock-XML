package com.fedorov.andrii.andriiovych.alarmclock.presentation.fragments

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fedorov.andrii.andriiovych.alarmclock.R
import com.fedorov.andrii.andriiovych.alarmclock.databinding.FragmentMainBinding
import com.fedorov.andrii.andriiovych.alarmclock.domain.models.AlarmModel
import com.fedorov.andrii.andriiovych.alarmclock.presentation.AlarmCreator
import com.fedorov.andrii.andriiovych.alarmclock.presentation.adapters.AlarmActionListener
import com.fedorov.andrii.andriiovych.alarmclock.presentation.adapters.MainAdapter
import com.fedorov.andrii.andriiovych.alarmclock.presentation.broadcast.AlarmReceiver
import com.fedorov.andrii.andriiovych.alarmclock.presentation.viewmodels.AlarmViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : Fragment() {
    lateinit var adapter: MainAdapter
    private val viewModel: AlarmViewModel by viewModels()
    lateinit var binding: FragmentMainBinding
    @Inject
    lateinit var alarmCreator: AlarmCreator
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        adapter = MainAdapter(object : AlarmActionListener {
            override fun onAlarmDelete(alarmModel: AlarmModel) {
                alarmCreator.deleteAlarm(alarmModel)
                deleteAlarm(alarmModel)
            }

            override fun onChangeNote(alarmModel: AlarmModel) {
                val bundle = Bundle().apply { putParcelable(NOTE,alarmModel) }
                this@MainFragment.findNavController().navigate(R.id.action_mainFragment_to_changeNoteFragment,bundle)
            }
        })
        val layoutManager = LinearLayoutManager(activity)
        binding.rcView.layoutManager = layoutManager
        binding.rcView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addAlarmButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_mainFragment_to_setTimeFragment)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getAll().collect { list ->
                    adapter.submitList(list)
                }
            }
        }
    }

    fun deleteAlarm(alarmModel: AlarmModel) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(getString(R.string.Are_want_to_delete))
        alertDialogBuilder.setPositiveButton(getString(R.string.yes)) { dialog, _ ->
            val alarmManager =
                requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                alarmModel.id,
                intent,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                } else {
                    PendingIntent.FLAG_UPDATE_CURRENT
                }
            )
            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()
            viewModel.delete(alarmModel)
            dialog.dismiss()
        }
        alertDialogBuilder.setNegativeButton(getString(R.string.no)) { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    companion object {
        const val NOTE = " note"
    }
}