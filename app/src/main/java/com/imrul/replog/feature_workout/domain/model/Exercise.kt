package com.imrul.replog.feature_workout.domain.model

data class Exercise(
    val id: String? = null,
    val name: String = "",
    val imageUrl: String? = null,
    val targetMuscleGroup: String? = muscleGroups[0],
    val weightType: String? = weightTypes[0],
    val userId: String? = null
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

    // I will make this a useCase
    fun doesMatchSearchQuery(query: String): Boolean {
        // this will return true if the name contains our search query
        // doesn't matter if the name has spaces
        val matchingCombinations = listOf(
            removeSpaces(name)
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }

    private fun removeSpaces(input: String): String {
        return input.replace("\\s".toRegex(), "")
    }
}
