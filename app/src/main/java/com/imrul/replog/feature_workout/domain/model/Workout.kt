package com.imrul.replog.feature_workout.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Workout(
    @PrimaryKey
    val workoutId: Long? = null,
    val name: String = "",
    val duration: Int = 0,
    val date: Long = System.currentTimeMillis(),
    val dateString: String = "",
    val weekdayString: String = "",
    val durationString: String = ""
)
