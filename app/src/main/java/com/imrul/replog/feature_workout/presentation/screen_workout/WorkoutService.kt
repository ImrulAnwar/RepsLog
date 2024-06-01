package com.imrul.replog.feature_workout.presentation.screen_workout

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import com.imrul.replog.R
import com.imrul.replog.core.Constants
import com.imrul.replog.core.Strings
import com.imrul.replog.feature_workout.presentation.MainActivity
import com.imrul.replog.feature_workout.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class WorkoutService : Service() {
    private val handler = Handler(Looper.getMainLooper())
    private var startTime: Long = 0L
    private val updateInterval = 1000L // Update every second
    private val notificationId = 1

    @Inject
    lateinit var viewModel: WorkoutViewModel

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return START_STICKY
    }

    @SuppressLint("ForegroundServiceType")
    private fun start() {
        startTime = System.currentTimeMillis()
        updateTimer()

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
                .setContentText("${Strings.ELAPSED_TIME} 00:00")
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(false)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setContentIntent(pendingIntent)
                .build()
        notification.flags = Notification.FLAG_NO_CLEAR
        startForeground(notificationId, notification)
    }

    private fun updateTimer() {
        handler.postDelayed({
            val elapsedTime = System.currentTimeMillis() - startTime
            val seconds = (elapsedTime / 1000) % 60
            val minutes = (elapsedTime / (1000 * 60)) % 60

            val duration = String.format("%02d:%02d", minutes, seconds)

            // Update the notification with the new elapsed time
            val notificationIntent = Intent(this, MainActivity::class.java)
            notificationIntent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
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

            startForeground(notificationId, notification)

            // Schedule the next update
            updateTimer()
        }, updateInterval)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // Stop updating the timer
    }

    enum class Actions {
        START, STOP
    }
}
