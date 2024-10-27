package com.imrul.replog.feature_workout.domain.model

data class Note(
    val id: String? = null,
    val idForeign: String? = null,
    val belongsTo: String = SESSION,
    val isPinned: Boolean = false,
    val content: String = "",
    val userId: String? = null
) {
    companion object {
        const val SESSION = "Session"
        const val WORKOUT = "Workout"
    }
}
