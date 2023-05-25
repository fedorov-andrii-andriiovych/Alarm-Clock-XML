package com.fedorov.andrii.andriiovych.alarmclock.broadcast

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.fedorov.andrii.andriiovych.alarmclock.MainActivity
import com.fedorov.andrii.andriiovych.alarmclock.R
import com.fedorov.andrii.andriiovych.alarmclock.fragments.SetTimeFragment
import kotlin.math.log


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getIntExtra(SetTimeFragment.ID,0)
        val descriptionIntent = intent.getStringExtra(SetTimeFragment.DESCRIPTION)

        val fullScreenIntent = Intent(context.applicationContext, MainActivity::class.java)
        val fullScreenPendingIntent = PendingIntent.getActivity(context, 0,
            fullScreenIntent, PendingIntent.FLAG_IMMUTABLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "+"
            val descriptionText = "+"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("1", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        var builder = NotificationCompat.Builder(context, "1")
            .setSmallIcon(R.drawable.icon_alarm)
            .setContentTitle("Моя заметка!")
            .setContentText(descriptionIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setFullScreenIntent(fullScreenPendingIntent, true)

        with(NotificationManagerCompat.from(context.applicationContext)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(1, builder.build())
        }

        val mediaPlayer = MediaPlayer.create(context, R.raw.sound)
        mediaPlayer.start()
    }
}