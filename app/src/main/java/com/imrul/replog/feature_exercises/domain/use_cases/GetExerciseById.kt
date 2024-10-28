package com.imrul.replog.feature_exercises.domain.use_cases

import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository

class GetExerciseById(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(exerciseId: String) =
        repository.getExerciseById(exerciseId)?: Exercise(id = exerciseId)
}