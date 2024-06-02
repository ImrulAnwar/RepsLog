package com.imrul.replog.feature_workout.domain.use_cases

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.imrul.replog.R
import com.imrul.replog.core.Constants
import com.imrul.replog.core.Strings
import com.imrul.replog.feature_workout.presentation.MainActivity

class CreateRunningWorkoutNotificationUseCase {
    operator fun invoke(duration: String = "00:00", context: Context): Notification {
        // Create an Intent to launch your main activity when the notification is clicked
        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(context, Constants.WORKOUT_NOTIFICATION_CHANNEL_ID)
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
}