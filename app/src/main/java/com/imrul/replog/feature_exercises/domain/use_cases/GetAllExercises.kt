package com.imrul.replog.feature_exercises.domain.use_cases

import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.map

class GetAllExercises(
    private val repository: WorkoutRepository
) {
    operator fun invoke() = repository.getAllExercises().map { exercises ->
        exercises.sortedBy { it.name }
    }
}