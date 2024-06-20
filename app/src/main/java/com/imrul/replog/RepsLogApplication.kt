package com.imrul.replog

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.imrul.replog.core.Constants
import com.imrul.replog.core.Strings
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RepsLogApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constants.WORKOUT_NOTIFICATION_CHANNEL_ID,
                Strings.WORKOUT_NOTIFICATION_CHANNEL_TITLE,
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}