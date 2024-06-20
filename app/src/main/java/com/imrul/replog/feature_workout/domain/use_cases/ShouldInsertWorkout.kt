package com.imrul.replog.feature_workout.domain.use_cases

import androidx.compose.runtime.snapshots.SnapshotStateList

class ShouldInsertWorkout {
    operator fun invoke(listOfIsDone: SnapshotStateList<Boolean>): Boolean {
        for (item in listOfIsDone) {
            if (item) {
                return true
            }
        }
        return false
    }
}