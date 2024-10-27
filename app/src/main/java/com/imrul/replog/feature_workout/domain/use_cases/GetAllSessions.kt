package com.imrul.replog.feature_workout.domain.use_cases

import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository

class GetAllSessions(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke() = repository.getAllSessions()
}