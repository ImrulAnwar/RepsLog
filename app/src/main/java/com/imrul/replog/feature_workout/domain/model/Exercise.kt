package com.imrul.replog.feature_workout.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exercise(
    @PrimaryKey
    val exerciseId: Long? = null,
    val name: String = "",
    val note: String = "",
    val repsSlower: Boolean = false,
    val imageUrl: String? = null,
    val workoutIdForeign: Long? = null
)
