package com.fedorov.andrii.andriiovych.alarmclock.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.fedorov.andrii.andriiovych.alarmclock.R
import com.fedorov.andrii.andriiovych.alarmclock.data.AlarmModel
import com.fedorov.andrii.andriiovych.alarmclock.databinding.ItemAlarmBinding

interface AlarmActionListener {
    fun onAlarmDelete(alarmModel: AlarmModel)

    fun onSwitchClicked(alarmModel: AlarmModel)
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
            switchIsChecked.setOnCheckedChangeListener(null)
            holder.itemView.tag = alarm
            switchIsChecked.setOnCheckedChangeListener { _, isChecked ->
                if (alarm.isChecked != isChecked) {
                    alarmActionListener.onSwitchClicked(alarm)
                }
            }
            val time = "${alarm.hours}:${alarm.minutes}"
            timeTextView.text = time
            descriptionTextView.text = alarm.description
            switchIsChecked.isChecked = alarm.isChecked
        }
    }

}