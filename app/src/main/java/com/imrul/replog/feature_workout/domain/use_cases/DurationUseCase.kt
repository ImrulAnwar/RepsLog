package com.imrul.replog.feature_workout.domain.use_cases

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DurationUseCase {
    private val _elapsedTime = MutableStateFlow("00:00")
    val elapsedTime: StateFlow<String> =
        _elapsedTime.asStateFlow() // Provide an immutable StateFlow for consumers

    operator fun invoke(newDuration: String) {
        _elapsedTime.value = newDuration
    }

    private var startTime: Long = 0L
    private val updateInterval = 1000L
    fun start() {
        startTime = System.currentTimeMillis()

        // Start the coroutine to update the timer
        CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                updateTimer()
                delay(updateInterval)
            }
        }
    }

    private fun updateTimer() {
        val elapsedTime = System.currentTimeMillis() - startTime
        val seconds = (elapsedTime / 1000) % 60
        val minutes = (elapsedTime / (1000 * 60)) % 60

        val duration = String.format("%02d:%02d", minutes, seconds)

        // Update the notification with the new elapsed time
        _elapsedTime.value = duration
    }
}