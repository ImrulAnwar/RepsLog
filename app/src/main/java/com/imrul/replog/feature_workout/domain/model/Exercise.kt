package com.imrul.replog.feature_workout.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

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

data class SetsInExercise(
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "exerciseIdForeign"
    )
    val sets: List<Set>
)
