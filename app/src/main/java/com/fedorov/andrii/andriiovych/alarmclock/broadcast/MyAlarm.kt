package com.fedorov.andrii.andriiovych.alarmclock.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import com.fedorov.andrii.andriiovych.alarmclock.R

class MyAlarm : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.sound)
        mediaPlayer.start()
    }
}