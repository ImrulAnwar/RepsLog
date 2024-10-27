package com.imrul.replog.feature_workout.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.model.Note
import com.imrul.replog.feature_workout.domain.model.Session
import com.imrul.replog.feature_workout.domain.model.Set
import com.imrul.replog.feature_workout.domain.model.Workout
import com.imrul.replog.feature_workout.domain.repository.WorkoutDatasource
import kotlinx.coroutines.flow.Flow

class WorkoutDatasourceImpl(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : WorkoutDatasource{
    override suspend fun insertWorkout(workout: Workout): String {
        TODO("Not yet implemented")
    }

    override suspend fun insertExercise(exercise: Exercise): String {
        TODO("Not yet implemented")
    }

    override suspend fun insertSet(set: Set): String {
        TODO("Not yet implemented")
    }

    override suspend fun insertSession(session: Session): String {
        TODO("Not yet implemented")
    }

    override suspend fun insertNote(note: Note): String {
        TODO("Not yet implemented")
    }

    override suspend fun deleteWorkout(workout: Workout) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteExercise(exercise: Exercise) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSet(set: Set) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSession(session: Session) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllWorkouts(): Flow<List<Workout>> {
        TODO("Not yet implemented")
    }

    override suspend fun getExerciseById(exerciseId: String?): Exercise {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSessionsByWorkoutId(workoutId: String?): Flow<List<Session>> {
        TODO("Not yet implemented")
    }

    override suspend fun getLatestSessionByExerciseId(exerciseId: String?): Flow<Session?> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllNotes(): Flow<List<Note>> {
        TODO("Not yet implemented")
    }

    override suspend fun getNotesByForeignId(foreignId: String): Flow<List<Note>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSessions(): Flow<List<Session>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSetsBySessionId(exerciseId: String?): Flow<List<Set>> {
        TODO("Not yet implemented")
    }

    override suspend fun getWorkoutById(workoutId: String?): Workout {
        TODO("Not yet implemented")
    }

    override suspend fun getAllExercises(): Flow<List<Exercise>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSets(): Flow<List<Set>> {
        TODO("Not yet implemented")
    }
}