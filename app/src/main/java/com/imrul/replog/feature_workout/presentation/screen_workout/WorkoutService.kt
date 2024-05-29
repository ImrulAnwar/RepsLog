package com.imrul.replog.feature_workout.presentation.screen_workout

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.imrul.replog.R
import com.imrul.replog.core.Constants
import com.imrul.replog.core.Strings
import com.imrul.replog.feature_workout.presentation.MainActivity

class WorkoutService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("ForegroundServiceType")
    private fun start(duration: String = "00:50") {

        // Create an Intent to launch your main activity when the notification is clicked
        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification =
            NotificationCompat.Builder(this, Constants.WORKOUT_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(Strings.NOTIFICATION_TITLE)
                .setContentText("${Strings.ELAPSED_TIME} $duration")
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(false)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setContentIntent(pendingIntent)
                .build()
        notification.flags = Notification.FLAG_NO_CLEAR
        startForeground(1, notification)
    }

    enum class Actions {
        START, STOP
    }
}