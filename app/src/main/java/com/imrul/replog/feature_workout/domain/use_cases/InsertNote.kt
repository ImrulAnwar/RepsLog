package com.imrul.replog.feature_workout.domain.use_cases

import com.imrul.replog.feature_workout.domain.model.Note
import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository

class InsertNote(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(note: Note) = repository.insertNote(note)
}