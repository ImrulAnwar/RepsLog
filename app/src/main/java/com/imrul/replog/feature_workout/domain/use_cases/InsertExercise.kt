package com.imrul.replog.feature_workout.domain.use_cases

import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository

class InsertExercise(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(exercise: Exercise) = repository.insertExercise(exercise)
}