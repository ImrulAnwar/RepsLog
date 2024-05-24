package com.imrul.replog.feature_workout.domain.use_cases

import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository

class GetAllSets(
    private val repository: WorkoutRepository
) {
    operator fun invoke() = repository.getAllSets()
}