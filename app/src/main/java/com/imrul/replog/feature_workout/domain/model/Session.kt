package com.imrul.replog.feature_workout.domain.model


data class Session(
    val id: String? = null,
    val exerciseIdForeign: String? = null,
    val exerciseName: String? = null,
    val workoutIdForeign: String? = null,
    val repsSlower: Boolean = false,
    val weightUnit: String = WEIGHT_UNIT_KG,
    val setCount: Long = 0,
    val isTimerEnabled: Boolean = false,
    val bestSet: String? = null,
    val userId: String? = null,
    val timestamp: Long = System.currentTimeMillis()
) {
    companion object {
        const val WEIGHT_UNIT_KG = "kg"
        const val WEIGHT_UNIT_LB = "lb"
    }
}

