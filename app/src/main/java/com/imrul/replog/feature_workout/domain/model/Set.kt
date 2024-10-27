package com.imrul.replog.feature_workout.domain.model

data class Set(
    val id: String? = null,
    val setType: String = SET_TYPE_WARM_UP,
    val weightValue: Float = 0f,
    val reps: Float = 0f,
    val isDone: Boolean = false,
    val sessionIdForeign: String? = null,
    val timerDuration: Long? = null,
    val userId: String? = null
) {
    companion object {
        const val SET_TYPE_WARM_UP = "warm up set"
        const val SET_TYPE_FAILURE = "failure"
    }
}

