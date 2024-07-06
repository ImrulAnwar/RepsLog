package com.imrul.replog.feature_workout.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Session(
    @PrimaryKey
    val sessionId: Long? = null,
    val exerciseIdForeign: Long? = null,
    val exerciseName: String? = null,
    val workoutIdForeign: Long? = null,
    val repsSlower: Boolean = false,
    val weightUnit: String = weightUnits[0],
    val setCount: Long = 0,
    val isTimerEnabled: Boolean = false,
    val bestSet: String? = null
) {
    companion object {
        val weightUnits = listOf("kg", "lb")
    }
}

