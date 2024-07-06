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
    val weightUnit: String = WEIGHT_UNIT_KG,
    val setCount: Long = 0,
    val isTimerEnabled: Boolean = false,
    val bestSet: String? = null
) {
    companion object {
        const val WEIGHT_UNIT_KG = "kg"
        const val WEIGHT_UNIT_LB = "lb"
    }
}

