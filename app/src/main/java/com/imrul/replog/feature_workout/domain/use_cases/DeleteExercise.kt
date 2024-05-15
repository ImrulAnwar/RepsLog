package com.imrul.replog.feature_workout.domain.use_cases

import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.model.Set
import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow

class DeleteExercise(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(exercise: Exercise) {
        val setsInThatExercise = repository.getAllSetsByExerciseId(exercise.exerciseId)
        setsInThatExercise.collect {
            deleteAllTheSetsInThatExercise(it, repository)
        }
        repository.deleteExercise(exercise)
    }
}

suspend fun deleteAllTheSetsInThatExercise(listOfSets: List<Set>, repository: WorkoutRepository) {
    for (set in listOfSets) {
        repository.deleteSet(set)
    }
}

