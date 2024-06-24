package com.imrul.replog.feature_workout.domain.use_cases

import com.imrul.replog.feature_workout.domain.model.Session
import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository

class InsertSession(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(session: Session) = repository.insertSession(session)
}