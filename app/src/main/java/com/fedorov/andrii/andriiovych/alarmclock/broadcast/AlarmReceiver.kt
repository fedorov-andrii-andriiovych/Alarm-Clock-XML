package com.fedorov.andrii.andriiovych.alarmclock.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.fedorov.andrii.andriiovych.alarmclock.services.AlarmService

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val serviceIntent = Intent(context, AlarmService::class.java)
        context.startService(serviceIntent)
    }
}