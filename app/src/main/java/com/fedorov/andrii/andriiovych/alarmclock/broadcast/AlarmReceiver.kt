package com.fedorov.andrii.andriiovych.alarmclock.broadcast

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.fedorov.andrii.andriiovych.alarmclock.MainActivity
import com.fedorov.andrii.andriiovych.alarmclock.R
import com.fedorov.andrii.andriiovych.alarmclock.fragments.SetTimeFragment


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getIntExtra(SetTimeFragment.ID, 0)
        val descriptionIntent = intent.getStringExtra(SetTimeFragment.DESCRIPTION)
        val mediaPlayer = MediaPlayer.create(context, R.raw.sound)
        mediaPlayer.start()
        val fullScreenIntent = Intent(context.applicationContext, MainActivity::class.java)
        fullScreenIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val fullScreenPendingIntent = PendingIntent.getActivity(
            context, id,
            fullScreenIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val channel =
            NotificationChannel(
                "1",
                "Напоминания",
                NotificationManager.IMPORTANCE_HIGH).apply {
                description = ""
                enableVibration(true)
                enableLights(true)
                setSound(null, null)
                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        var builder = NotificationCompat.Builder(context, "1")
            .setSmallIcon(R.drawable.icon_note)
            .setContentTitle("Мои задачи:")
            .setContentText(descriptionIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_CALL)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .setOnlyAlertOnce(true)
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        with(NotificationManagerCompat.from(context.applicationContext)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(id, builder.build())
        }


    }
}