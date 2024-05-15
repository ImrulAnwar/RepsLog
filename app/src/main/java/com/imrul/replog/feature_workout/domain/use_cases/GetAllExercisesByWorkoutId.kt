package com.imrul.replog.feature_workout.domain.use_cases

import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository

class GetAllExercisesByWorkoutId(
    private val repository: WorkoutRepository
) {
    operator fun invoke(workoutId: Long) = repository.getAllExerciseByWorkoutId(workoutId)
}