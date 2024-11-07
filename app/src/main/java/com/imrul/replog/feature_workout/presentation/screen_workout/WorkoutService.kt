package com.imrul.replog.feature_workout.presentation.screen_workout

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.imrul.replog.feature_workout.domain.use_cases.WorkoutUseCases
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WorkoutService : Service() {

    @Inject
    lateinit var workoutUseCases: WorkoutUseCases
    private val notificationId = 1
    private var isRunning = false

    private val serviceScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> {
                if (!isRunning) {
                    isRunning = true
                    // Start foreground immediately to avoid timing issues
                    startForeground(
                        notificationId,
                        workoutUseCases.createRunningWorkoutNotificationUseCase(context = applicationContext)
                    )
                    // Launch the rest of the start logic in the serviceScope
                    serviceScope.launch { start() }                }
            }

            Actions.STOP.toString() -> {
                if (isRunning) stopSelf()
            }
        }
        return START_STICKY
    }

    private suspend fun start() {
        workoutUseCases.durationUseCase.start()

        workoutUseCases.durationUseCase.elapsedTime.collect { elapsedTime ->
            val notification = workoutUseCases.createRunningWorkoutNotificationUseCase(elapsedTime, applicationContext)
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
