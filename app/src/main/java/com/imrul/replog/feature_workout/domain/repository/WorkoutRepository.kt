package com.imrul.replog.feature_workout.domain.repository

import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.model.Set
import com.imrul.replog.feature_workout.domain.model.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    suspend fun insertWorkout(workout: Workout)

    suspend fun insertExercise(exercise: Exercise)

    suspend fun insertSet(set: Set)

    suspend fun deleteWorkout(workout: Workout)

    suspend fun deleteExercise(exercise: Exercise)

    suspend fun deleteSet(set: Set)

    fun getAllWorkouts(): Flow<List<Workout>>

    fun getAllExerciseByWorkoutId(workoutId: Long): Flow<List<Exercise>>

    fun getAllSetsByExerciseId(exerciseId: Long): Flow<List<Set>>

    fun getWorkoutById(workoutId: Long): Workout
}