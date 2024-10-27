package com.imrul.replog.feature_workout.domain.use_cases

import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository

class GetAllSessionsByWorkoutId(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(exerciseId: String) = repository.getAllSessionsByWorkoutId(exerciseId)
}