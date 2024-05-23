package com.imrul.replog.feature_workout.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.model.Set
import com.imrul.replog.feature_workout.domain.model.Workout
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: Workout): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(set: Set): Long

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Delete
    suspend fun deleteSet(set: Set)

    @Query("SELECT * FROM Workout")
    fun getAllWorkouts(): Flow<List<Workout>>

    @Query("SELECT * FROM Exercise WHERE workoutIdForeign = :workoutId")
    fun getAllExerciseByWorkoutId(workoutId: Long?): Flow<List<Exercise>>

    @Query("SELECT * FROM `Set` WHERE exerciseIdForeign = :exerciseId")
    fun getAllSetsByExerciseId(exerciseId: Long?): Flow<List<Set>>

    @Query("SELECT * FROM Workout where workoutId = :workoutId")

    fun getWorkoutById(workoutId: Long?): Workout

}