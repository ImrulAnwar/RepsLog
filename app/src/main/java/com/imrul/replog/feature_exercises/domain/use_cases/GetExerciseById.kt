package com.imrul.replog.feature_exercises.domain.use_cases

import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository

class GetExerciseById(
    private val repository: WorkoutRepository
) {
    operator fun invoke(workoutId: Long) = repository.getExerciseById(workoutId)
}