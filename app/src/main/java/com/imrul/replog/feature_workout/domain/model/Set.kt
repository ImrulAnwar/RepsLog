package com.imrul.replog.feature_workout.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Set(
    @PrimaryKey
    val setId: Long? = null,
    val setType: String = SET_TYPE_WARM_UP,
    val weightValue: Float = 0f,
    val reps: Float = 0f,
    val isDone: Boolean = false,
    val sessionIdForeign: Long? = null,
    val timerDuration: Long? = null
) {
    companion object {
        const val SET_TYPE_WARM_UP = "warm up set"
        const val SET_TYPE_FAILURE = "failure"
    }
}

