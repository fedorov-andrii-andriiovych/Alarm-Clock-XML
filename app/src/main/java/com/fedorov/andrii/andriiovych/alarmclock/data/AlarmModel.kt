package com.fedorov.andrii.andriiovych.alarmclock.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class AlarmModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var hours: Int,
    var minutes: Int,
    var day: Int,
    var month: Int,
    var year: Int,
    val description:String,
) {
}