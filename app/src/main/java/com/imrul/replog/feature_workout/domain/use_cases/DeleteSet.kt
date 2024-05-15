package com.imrul.replog.feature_workout.domain.use_cases

import com.imrul.replog.feature_workout.domain.model.Set
import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository

class DeleteSet(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(set: Set) = repository.deleteSet(set)
}