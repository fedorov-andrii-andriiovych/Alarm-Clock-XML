package com.fedorov.andrii.andriiovych.alarmclock.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class AlarmModel(
    val id: Int = 0,
    var hours: Int,
    var minutes: Int,
    var day: Int,
    var month: Int,
    var year: Int,
    val description:String,
): Parcelable
