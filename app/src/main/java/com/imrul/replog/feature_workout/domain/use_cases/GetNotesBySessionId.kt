package com.imrul.replog.feature_workout.domain.use_cases

import android.util.Log
import com.imrul.replog.feature_workout.domain.model.Note
import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesBySessionId(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(sessionId: String): Flow<List<Note>> {
        return repository.getNotesByForeignId(foreignId = sessionId).map { notes ->
            notes.filter {
                it.belongsTo == Note.SESSION
            }
        }
    }

}