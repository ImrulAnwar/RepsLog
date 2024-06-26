package com.imrul.replog.feature_workout.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey
    val noteId: Long? = null,
    val idForeign: Long? = null,
    val belongsTo: String = mayBelongTo[0],
    val isPinned: Boolean = false
) {
    companion object {
        val mayBelongTo = listOf("session", "workout")
    }
}
