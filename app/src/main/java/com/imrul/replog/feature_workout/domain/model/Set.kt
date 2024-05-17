package com.imrul.replog.feature_workout.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Set(
    @PrimaryKey
    val setId: Long? = null,
    val setType: String = setTypes[0],
    val weightValue: Float = 0f,
    val weightUnit: String = weightUnits[0],
    val reps: Float = 0f,
    val isDone: Boolean = false,
    val exerciseIdForeign: Long? = null,
    val previousWeightValue: Float = 0f,
    val previousWeightUnit: String = weightUnits[0],
    val previousReps: Float = 0f
) {
    companion object {
        val setTypes = listOf("regular set", "drop set", "super set", "warm up set", "failure set")
        val weightUnits = listOf("kg", "lb")
    }
}

