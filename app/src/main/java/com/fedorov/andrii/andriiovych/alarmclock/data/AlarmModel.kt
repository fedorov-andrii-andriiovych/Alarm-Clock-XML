package com.fedorov.andrii.andriiovych.alarmclock.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class AlarmModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var time: String,
    val description:String,
    val isChecked:Boolean
) {
}