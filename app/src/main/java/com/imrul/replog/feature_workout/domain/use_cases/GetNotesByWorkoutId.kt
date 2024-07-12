package com.imrul.replog.feature_workout.domain.use_cases

import com.imrul.replog.feature_workout.domain.model.Note
import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.map

class GetNotesByWorkoutId(
    private val repository: WorkoutRepository
) {
    operator fun invoke(sessionId: Long) =
        repository.getNotesByForeignId(foreignId = sessionId).map { notes ->
            notes.filter { it.belongsTo == Note.WORKOUT }
        }
}