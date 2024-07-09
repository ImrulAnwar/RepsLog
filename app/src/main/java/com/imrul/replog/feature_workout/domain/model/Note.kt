package com.imrul.replog.feature_workout.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey
    val noteId: Long? = null,
    val idForeign: Long? = null,
    val belongsTo: String = SESSION,
    val isPinned: Boolean = false,
    val content: String = ""
) {
    companion object {
        const val SESSION = "Session"
        const val WORKOUT = "Workout"
    }
}
