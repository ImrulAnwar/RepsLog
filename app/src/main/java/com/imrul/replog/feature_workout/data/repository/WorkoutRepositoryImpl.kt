package com.imrul.replog.feature_workout.data.repository

import com.imrul.replog.feature_workout.data.data_source.WorkoutDao
import com.imrul.replog.feature_workout.domain.model.Exercise
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

    override suspend fun deleteWorkout(workout: Workout) {
        dao.deleteWorkout(workout)
    }

    override suspend fun deleteExercise(exercise: Exercise) {
        dao.deleteExercise(exercise)
    }

    override suspend fun deleteSet(set: Set) {
        dao.deleteSet(set)
    }

    override fun getAllWorkouts(): Flow<List<Workout>> {
        return dao.getAllWorkouts()
    }

    override fun getAllExercisesByWorkoutId(workoutId: Long?): Flow<List<Exercise>> {
        return dao.getAllExercisesByWorkoutId(workoutId)
    }
    override fun getAllExercises(): Flow<List<Exercise>> {
        return dao.getAllExercises()
    }

    override fun getAllSetsByExerciseId(exerciseId: Long?): Flow<List<Set>> {
        return dao.getAllSetsByExerciseId(exerciseId)
    }
    override fun getAllSets(): Flow<List<Set>> {
        return dao.getAllSets()
    }

    override fun getWorkoutById(workoutId: Long?): Workout {
        return dao.getWorkoutById(workoutId)
    }
}