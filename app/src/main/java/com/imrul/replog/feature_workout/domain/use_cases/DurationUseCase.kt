package com.imrul.replog.feature_workout.domain.use_cases

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DurationUseCase {
    private val _elapsedTime = MutableStateFlow("00:00")
    val elapsedTime: StateFlow<String> = _elapsedTime.asStateFlow() // Provide an immutable StateFlow for consumers

    operator fun invoke(newDuration: String) {
        _elapsedTime.value = newDuration
    }
}