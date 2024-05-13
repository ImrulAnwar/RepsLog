package com.imrul.replog.feature_workout.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

@Entity
data class Workout(
    @PrimaryKey
    val workoutId: Long? = null,
    val name: String = "",
    val duration: Int = 0,
    val date: String
)

data class ExercisesInWorkout(
    @Embedded val workout: Workout,
    @Relation(
        parentColumn = "workoutId",
        entityColumn = "workoutIdForeign"
    )
    val exercises: List<Exercise>
)
