package com.imrul.replog.feature_workout.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exercise(
    @PrimaryKey
    val exerciseId: Long? = null,
    val name: String = "",
    val imageUrl: String? = null,
    val targetMuscleGroup: String? = muscleGroups[0],
    val weightType: String? = weightTypes[0]
) {
    companion object {
        val muscleGroups = listOf(
            "Chest",
            "Lats",
            "Quads",
            "Hamstrings",
            "Glutes",
            "Abs",
            "Biceps",
            "Triceps",
            "Forearms",
            "Rear Shoulders",
            "Front Shoulders",
            "Traps",
            "Calves"
        )
        val weightTypes = listOf(
            "Dumbbell",
            "Barbell",
            "Cable",
            "Machine",
            "Other"
        )
    }
}
