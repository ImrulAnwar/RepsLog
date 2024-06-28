package com.imrul.replog.feature_exercises.domain.use_cases

import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository

class GetExerciseById(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(exerciseId: Long) = repository.getExerciseById(exerciseId)
}