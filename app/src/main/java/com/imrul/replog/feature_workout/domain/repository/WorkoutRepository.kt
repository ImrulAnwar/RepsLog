package com.imrul.replog.feature_workout.domain.repository

import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.model.Set
import com.imrul.replog.feature_workout.domain.model.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    suspend fun insertWorkout(workout: Workout): Long

    suspend fun insertExercise(exercise: Exercise): Long

    suspend fun insertSet(set: Set): Long

    suspend fun deleteWorkout(workout: Workout)

    suspend fun deleteExercise(exercise: Exercise)

    suspend fun deleteSet(set: Set)

    fun getAllWorkouts(): Flow<List<Workout>>

    fun getAllExercisesByWorkoutId(workoutId: Long?): Flow<List<Exercise>>

    fun getAllSetsByExerciseId(exerciseId: Long?): Flow<List<Set>>

    fun getWorkoutById(workoutId: Long?): Workout
    fun getAllExercises(): Flow<List<Exercise>>
    fun getAllSets(): Flow<List<Set>>
}