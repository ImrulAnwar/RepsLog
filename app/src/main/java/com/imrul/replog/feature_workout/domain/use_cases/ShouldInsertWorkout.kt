package com.imrul.replog.feature_workout.domain.use_cases

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.imrul.replog.feature_workout.domain.model.Workout
import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository

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