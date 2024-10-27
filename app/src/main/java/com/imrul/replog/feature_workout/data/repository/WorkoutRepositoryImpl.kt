package com.imrul.replog.feature_workout.data.repository

import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.model.Note
import com.imrul.replog.feature_workout.domain.model.Session
import com.imrul.replog.feature_workout.domain.model.Set
import com.imrul.replog.feature_workout.domain.model.Workout
import com.imrul.replog.feature_workout.domain.repository.WorkoutDatasource
import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow

class WorkoutRepositoryImp(
    private val datasource : WorkoutDatasource
) : WorkoutRepository {
    override suspend fun insertWorkout(workout: Workout): String = datasource.insertWorkout(workout)

    override suspend fun insertExercise(exercise: Exercise): String = datasource.insertExercise(exercise)

    override suspend fun insertSet(set: Set): String = datasource.insertSet(set)
    override suspend fun insertSession(session: Session): String = datasource.insertSession(session)

    override suspend fun insertNote(note: Note): String = datasource.insertNote(note)

    override suspend fun deleteWorkout(workout: Workout) {
        datasource.deleteWorkout(workout)
    }

    override suspend fun deleteExercise(exercise: Exercise) {
        datasource.deleteExercise(exercise)
    }

    override suspend fun deleteSet(set: Set) {
        datasource.deleteSet(set)
    }

    override suspend fun deleteSession(session: Session) {
        datasource.deleteSession(session)
    }

    override suspend fun deleteNote(note: Note) {
        datasource.deleteNote(note)
    }

    override suspend fun getAllWorkouts(): Flow<List<Workout>> {
        return datasource.getAllWorkouts()
    }

    override suspend fun getExerciseById(exerciseId: String?): Exercise?{
        return datasource.getExerciseById(exerciseId)
    }

    override suspend fun getAllSessionsByWorkoutId(workoutId: String?): Flow<List<Session>> =
        datasource.getAllSessionsByWorkoutId(workoutId)


    override suspend fun getLatestSessionByExerciseId(exerciseId: String?): Flow<Session?> =
        datasource.getLatestSessionByExerciseId(exerciseId)

    override suspend fun getAllNotes(): Flow<List<Note>> = datasource.getAllNotes()
    override suspend fun getNotesByForeignId(foreignId: String): Flow<List<Note>> =
        datasource.getNotesByForeignId(foreignId = foreignId)


    override suspend fun getAllSessions(): Flow<List<Session>> = datasource.getAllSessions()

    override suspend fun getAllExercises(): Flow<List<Exercise>> {
        return datasource.getAllExercises()
    }

    override suspend fun getAllSetsBySessionId(exerciseId: String?): Flow<List<Set>> {
        return datasource.getAllSetsBySessionId(exerciseId)
    }

    override suspend fun getAllSets(): Flow<List<Set>> {
        return datasource.getAllSets()
    }

    override suspend fun getWorkoutById(workoutId: String?): Workout {
        return datasource.getWorkoutById(workoutId)
    }
}