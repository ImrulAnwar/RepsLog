package com.imrul.replog.feature_workout.domain.repository

import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.model.Note
import com.imrul.replog.feature_workout.domain.model.Session
import com.imrul.replog.feature_workout.domain.model.Set
import com.imrul.replog.feature_workout.domain.model.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutDatasource {
    suspend fun insertWorkout(workout: Workout): String
    suspend fun insertExercise(exercise: Exercise): String
    suspend fun insertSet(set: Set): String
    suspend fun insertSession(session: Session): String
    suspend fun insertNote(note: Note): String
    suspend fun deleteWorkout(workout: Workout)
    suspend fun deleteExercise(exercise: Exercise)
    suspend fun deleteSet(set: Set)
    suspend fun deleteSession(session: Session)
    suspend fun deleteNote(note: Note)
    suspend fun getAllWorkouts(): Flow<List<Workout>>
    suspend fun getExerciseById(exerciseId: String?): Exercise?
    suspend fun getAllSessionsByWorkoutId(workoutId: String?): Flow<List<Session>>
    suspend fun getLatestSessionByExerciseId(exerciseId: String?): Flow<Session?>
    suspend fun getAllNotes(): Flow<List<Note>>
    suspend fun getNotesByForeignId(foreignId: String): Flow<List<Note>>
    suspend fun getAllSessions(): Flow<List<Session>>
    suspend fun getAllSetsBySessionId(exerciseId: String?): Flow<List<Set>>
    suspend fun getWorkoutById(workoutId: String?): Workout
    suspend fun getAllExercises(): Flow<List<Exercise>>
    suspend fun getAllSets(): Flow<List<Set>>
}