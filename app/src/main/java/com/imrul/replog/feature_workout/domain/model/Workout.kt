package com.imrul.replog.feature_workout.domain.model

data class Workout(
    val id: String? = null,
    val name: String = "",
    val duration: Int = 0,
    val date: Long = System.currentTimeMillis(),
    val dateString: String = "",
    val weekdayString: String = "",
    val durationString: String = "",
    val userId: String? = null
)
