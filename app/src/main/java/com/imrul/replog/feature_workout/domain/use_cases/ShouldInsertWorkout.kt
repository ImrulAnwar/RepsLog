package com.imrul.replog.feature_workout.domain.use_cases

import androidx.compose.runtime.snapshots.SnapshotStateList

class ShouldInsertWorkout {
    operator fun invoke(
        listOfIsDone: SnapshotStateList<Boolean>,
        listOfWeights: SnapshotStateList<Pair<Int, String>>,
        listOfReps: SnapshotStateList<String>
    ): Boolean {
        for (index in listOfIsDone.indices) {
            if (
                listOfIsDone[index] &&
                isInteger(listOfReps[index]) &&
                isFloatOrInteger(listOfWeights[index].second)
            ) {
                return true
            }
        }
        return false
    }

    private fun isFloatOrInteger(str: String): Boolean {
        return str.toFloatOrNull() != null || str.toIntOrNull() != null
    }

    private fun isInteger(str: String): Boolean {
        return str.toIntOrNull() != null
    }
}