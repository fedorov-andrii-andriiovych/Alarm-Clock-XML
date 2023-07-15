package com.fedorov.andrii.andriiovych.alarmclock.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fedorov.andrii.andriiovych.alarmclock.databinding.ItemAlarmBinding
import com.fedorov.andrii.andriiovych.alarmclock.domain.models.AlarmModel

interface AlarmActionListener {
    fun onAlarmDelete(alarmModel: AlarmModel)
}

class MainAdapter(private val alarmActionListener: AlarmActionListener) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>(), View.OnClickListener {

    class MainViewHolder(val binding: ItemAlarmBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onClick(v: View) {
        val alarmModel = v.tag as AlarmModel
        when (v.id) {

            else -> {
                alarmActionListener.onAlarmDelete(alarmModel)
            }
        }
    }

    var alarmModels = mutableListOf<AlarmModel>()

    fun submitList(newData: List<AlarmModel>) {
        alarmModels.clear()
        alarmModels.addAll(newData)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = alarmModels.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAlarmBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val alarm = alarmModels[position]
        with(holder.binding) {
            holder.itemView.tag = alarm
            val time = "${alarm.hours}:${String.format("%02d", alarm.minutes)}"
            val date = "${alarm.day}.${String.format("%02d",alarm.month)}.${alarm.year}"
            timeTextView.text = time
            dateTextView.text = date
            descriptionTextView.text = alarm.description
        }
    }

}