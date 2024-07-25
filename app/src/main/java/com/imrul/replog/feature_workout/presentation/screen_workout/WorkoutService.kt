package com.imrul.replog.feature_workout.presentation.screen_workout

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.imrul.replog.feature_workout.domain.use_cases.WorkoutUseCases
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WorkoutService : Service() {

    @Inject
    lateinit var workoutUseCases: WorkoutUseCases
    private val notificationId = 1
    private var isRunning = false

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> {
                if (!isRunning) {
                    CoroutineScope(Dispatchers.Default).launch { start() }
                }
            }

            Actions.STOP.toString() -> {
                if (isRunning) stopSelf()
            }
        }
        return START_STICKY
    }


    private suspend fun start() {
        isRunning = true
        workoutUseCases.durationUseCase.start()
        startForeground(
            notificationId,
            workoutUseCases.createRunningWorkoutNotificationUseCase(context = applicationContext)
        )

        workoutUseCases.durationUseCase.elapsedTime.collect {
            val notification =
                workoutUseCases.createRunningWorkoutNotificationUseCase(it, applicationContext)
            startForeground(notificationId, notification)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }

    enum class Actions {
        START, STOP
    }
}
