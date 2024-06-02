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
import com.imrul.replog.feature_workout.domain.use_cases.DurationUseCase
import com.imrul.replog.feature_workout.domain.use_cases.WorkoutUseCases
import com.imrul.replog.feature_workout.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WorkoutService : Service() {

    @Inject
    lateinit var workoutUseCases: WorkoutUseCases
    private val notificationId = 1

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> CoroutineScope(Dispatchers.Default).launch { start() }
            Actions.STOP.toString() -> stopSelf()
        }
        return START_STICKY
    }

    private suspend fun start() {
        startForeground(notificationId, createNotification())

        workoutUseCases.durationUseCase.start()
        workoutUseCases.durationUseCase.elapsedTime.collect {
            val notification = createNotification(it)
            startForeground(notificationId, notification)
        }
    }

    private fun createNotification(duration: String = "00:00"): Notification {
        // Create an Intent to launch your main activity when the notification is clicked
        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, Constants.WORKOUT_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(Strings.NOTIFICATION_TITLE)
            .setContentText("${Strings.ELAPSED_TIME} $duration")
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(false)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setContentIntent(pendingIntent)
            .build()
            .apply {
                flags = flags or Notification.FLAG_NO_CLEAR
            }
    }
    enum class Actions {
        START, STOP
    }
}
