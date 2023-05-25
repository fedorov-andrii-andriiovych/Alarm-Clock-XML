package com.fedorov.andrii.andriiovych.alarmclock.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import com.fedorov.andrii.andriiovych.alarmclock.MainActivity
import com.fedorov.andrii.andriiovych.alarmclock.R
import kotlin.math.log


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.sound)
        mediaPlayer.start()
        val mainIntent = Intent(context, MainActivity::class.java)
        mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        context.startActivity(mainIntent)
    }
}