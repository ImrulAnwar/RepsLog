package com.imrul.replog.feature_exercises.domain.use_cases

import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository
import com.imrul.replog.feature_workout.domain.util.deleteSetsAndExercise

class DeleteExercise(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(exercise: Exercise) {
        deleteSetsAndExercise(repository, exercise)
    }
}


