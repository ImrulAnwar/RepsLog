package com.imrul.replog.feature_workout.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Set(
    @PrimaryKey
    val setId: Long? = null,
    val setType: String = setTypes[0],
    val weightValue: Float = 0f,
    val reps: Float = 0f,
    val isDone: Boolean = false,
    val sessionIdForeign: Long? = null,
    val timerDuration: Long? = null
) {
    companion object {
        val setTypes = listOf("regular set", "warm up set", "failure set")
    }
}

