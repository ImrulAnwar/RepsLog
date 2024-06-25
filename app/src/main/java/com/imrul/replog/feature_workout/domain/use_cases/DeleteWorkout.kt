package com.imrul.replog.feature_workout.domain.use_cases

import com.imrul.replog.feature_workout.domain.model.Workout
import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository
import com.imrul.replog.feature_workout.domain.util.deleteSetsAndExercise

class DeleteWorkout(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(workout: Workout) {
//        val exercisesInThatWorkout = repository.getExerciseById(workout.workoutId)
//        exercisesInThatWorkout.collect { listOfExercise ->
//            for (exercise in listOfExercise) {
//                deleteSetsAndExercise(repository, exercise)
//            }
//        }
        repository.deleteWorkout(workout)
    }
}