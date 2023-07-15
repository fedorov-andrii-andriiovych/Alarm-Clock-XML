package com.fedorov.andrii.andriiovych.alarmclock.data.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class AlarmModelEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var hours: Int,
    var minutes: Int,
    var day: Int,
    var month: Int,
    var year: Int,
    val description:String,
)
