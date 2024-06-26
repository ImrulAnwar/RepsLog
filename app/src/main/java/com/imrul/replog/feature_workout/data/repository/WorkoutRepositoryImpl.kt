package com.imrul.replog.feature_workout.data.repository

import com.imrul.replog.feature_workout.data.data_source.WorkoutDao
import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.model.Note
import com.imrul.replog.feature_workout.domain.model.Session
import com.imrul.replog.feature_workout.domain.model.Set
import com.imrul.replog.feature_workout.domain.model.Workout
import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow

class WorkoutRepositoryImp(
    private val dao: WorkoutDao
) : WorkoutRepository {
    override suspend fun insertWorkout(workout: Workout): Long = dao.insertWorkout(workout)

    override suspend fun insertExercise(exercise: Exercise): Long = dao.insertExercise(exercise)

    override suspend fun insertSet(set: Set): Long = dao.insertSet(set)
    override suspend fun insertSession(session: Session): Long = dao.insertSession(session)

    override suspend fun insertNote(note: Note): Long = dao.insertNote(note)

    override suspend fun deleteWorkout(workout: Workout) {
        dao.deleteWorkout(workout)
    }

    override suspend fun deleteExercise(exercise: Exercise) {
        dao.deleteExercise(exercise)
    }

    override suspend fun deleteSet(set: Set) {
        dao.deleteSet(set)
    }

    override suspend fun deleteSession(session: Session) {
        dao.deleteSession(session)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    override fun getAllWorkouts(): Flow<List<Workout>> {
        return dao.getAllWorkouts()
    }

    override fun getExerciseById(exerciseId: Long?): Exercise {
        return dao.getExerciseById(exerciseId)
    }

    override fun getAllSessionsByWorkoutId(workoutId: Long?): Flow<List<Session>> =
        dao.getAllSessionByWorkoutId(workoutId)


    override fun getLatestSessionByExerciseId(exerciseId: Long?): Flow<Session> =
        dao.getLatestSessionByExerciseId(exerciseId)

    override fun getAllNotes(): Flow<List<Note>> = dao.getAllNotes()

    override fun getAllSessions(): Flow<List<Session>> = dao.getAllSessions()

    override fun getAllExercises(): Flow<List<Exercise>> {
        return dao.getAllExercises()
    }

    override fun getAllSetsBySessionId(exerciseId: Long?): Flow<List<Set>> {
        return dao.getAllSetsBySessionId(exerciseId)
    }

    override fun getAllSets(): Flow<List<Set>> {
        return dao.getAllSets()
    }

    override fun getWorkoutById(workoutId: Long?): Workout {
        return dao.getWorkoutById(workoutId)
    }
}