package com.imrul.replog.feature_workout.domain.use_cases

import com.imrul.replog.feature_workout.domain.model.Session
import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class GetLatestSessionByExerciseId(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(exerciseId: String): Flow<Session?> =
        repository.getLatestSessionByExerciseId(exerciseId)
            .map { sessions ->
                sessions.maxByOrNull { it.timestamp } // Get the latest session by timestamp
            }

}