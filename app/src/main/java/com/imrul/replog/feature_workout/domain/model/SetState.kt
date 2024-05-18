package com.imrul.replog.feature_workout.domain.model

data class SetState(
    val setType: String = Set.setTypes[0],
    var weightValue: Float = 0f,
    val weightUnit: String = Set.weightUnits[0],
    val reps: Float = 0f,
    val isDone: Boolean = false,
    val exerciseIdForeign: Long? = null,
    val previousWeightValue: Float = 0f,
    val previousWeightUnit: String = Set.weightUnits[0],
    val previousReps: Float = 0f
)
