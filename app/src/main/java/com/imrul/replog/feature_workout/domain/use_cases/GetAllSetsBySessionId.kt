package com.imrul.replog.feature_workout.domain.use_cases

import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository

class GetAllSetsBySessionId(
    private val repository: WorkoutRepository
) {
    operator fun invoke(sessionId: Long) = repository.getAllSetsBySessionId(sessionId)
}