package com.imrul.replog.feature_workout.domain.util

import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository


suspend fun deleteSetsAndExercise(
    repository: WorkoutRepository,
    exercise: Exercise
) {
    val setsInThatExercise = repository.getAllSetsByExerciseId(exercise.exerciseId)
    setsInThatExercise.collect { listOfSets ->
        for (set in listOfSets) {
            repository.deleteSet(set)
        }
    }
    repository.deleteExercise(exercise)
}