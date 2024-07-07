package com.imrul.replog.feature_workout.domain.repository

import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.model.Note
import com.imrul.replog.feature_workout.domain.model.Session
import com.imrul.replog.feature_workout.domain.model.Set
import com.imrul.replog.feature_workout.domain.model.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    suspend fun insertWorkout(workout: Workout): Long

    suspend fun insertExercise(exercise: Exercise): Long

    suspend fun insertSet(set: Set): Long
    suspend fun insertSession(session: Session): Long
    suspend fun insertNote(note: Note): Long


    suspend fun deleteWorkout(workout: Workout)

    suspend fun deleteExercise(exercise: Exercise)

    suspend fun deleteSet(set: Set)
    suspend fun deleteSession(session: Session)

    suspend fun deleteNote(note: Note)


    fun getAllWorkouts(): Flow<List<Workout>>

    suspend fun getExerciseById(exerciseId: Long?): Exercise

    fun getAllSessionsByWorkoutId(workoutId: Long?): Flow<List<Session>>
    fun getLatestSessionByExerciseId(exerciseId: Long?): Flow<Session?>
    fun getAllNotes(): Flow<List<Note>>

    fun getAllSessions(): Flow<List<Session>>

    fun getAllSetsBySessionId(exerciseId: Long?): Flow<List<Set>>

    fun getWorkoutById(workoutId: Long?): Workout
    fun getAllExercises(): Flow<List<Exercise>>
    fun getAllSets(): Flow<List<Set>>
}