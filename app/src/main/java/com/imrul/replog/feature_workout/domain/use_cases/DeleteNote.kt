package com.imrul.replog.feature_workout.domain.use_cases

import com.imrul.replog.feature_workout.domain.model.Note
import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository

class DeleteNote(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(note: Note) = repository.deleteNote(note)
}