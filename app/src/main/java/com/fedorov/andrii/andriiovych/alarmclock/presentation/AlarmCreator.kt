package com.fedorov.andrii.andriiovych.alarmclock.presentation

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.fedorov.andrii.andriiovych.alarmclock.domain.models.AlarmModel
import com.fedorov.andrii.andriiovych.alarmclock.presentation.broadcast.AlarmReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class AlarmCreator @Inject constructor(@ApplicationContext val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    fun createAlarm(alarmModel: AlarmModel): Boolean {

        val calendar = Calendar.getInstance()
        calendar.set(
            alarmModel.year,
            alarmModel.month,
            alarmModel.day,
            alarmModel.hours,
            alarmModel.minutes,
            0
        )
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(ID, alarmModel.id)
        intent.putExtra(DESCRIPTION, alarmModel.description)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmModel.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
        return true
    }

    fun deleteAlarm(alarmModel: AlarmModel) {
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(ID, alarmModel.id)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            alarmModel.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    companion object {
        const val ID = "ALERT_ID"
        const val DESCRIPTION = "ALERT_DESCRIPTION"
    }
}