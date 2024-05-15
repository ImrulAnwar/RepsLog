package com.imrul.replog.feature_workout.domain.use_cases

import com.imrul.replog.feature_workout.domain.model.Workout
import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository

class InsertWorkout(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(workout: Workout) = repository.insertWorkout(workout)
}