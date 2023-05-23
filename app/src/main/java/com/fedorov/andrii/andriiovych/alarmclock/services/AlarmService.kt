package com.fedorov.andrii.andriiovych.alarmclock.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.media.Ringtone
import android.os.IBinder
import com.fedorov.andrii.andriiovych.alarmclock.R

class AlarmService : Service() {
    private var ringtone: Ringtone? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        val ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
//        ringtone = RingtoneManager.getRingtone(applicationContext, ringtoneUri)
//        ringtone?.play()

        val mediaPlayer = MediaPlayer.create(this, R.raw.horse)
        mediaPlayer.start()

        // Важно вызвать метод stopSelf(), чтобы остановить службу после выполнения задачи
        stopSelf()

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        ringtone?.stop()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}