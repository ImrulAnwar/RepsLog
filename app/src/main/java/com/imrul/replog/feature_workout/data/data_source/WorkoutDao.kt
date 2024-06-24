package com.imrul.replog.feature_workout.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.model.Note
import com.imrul.replog.feature_workout.domain.model.Session
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: Session): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Delete
    suspend fun deleteSet(set: Set)

    @Delete
    suspend fun deleteSession(session: Session)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM Workout ORDER BY date DESC")
    fun getAllWorkouts(): Flow<List<Workout>>

    @Query("SELECT * FROM Exercise WHERE workoutIdForeign = :workoutId")
    fun getAllExercisesByWorkoutId(workoutId: Long?): Flow<List<Exercise>>

    @Query("SELECT * FROM `Session` WHERE exerciseIdForeign = :exerciseId")
    fun getAllSessionByExerciseId(exerciseId: Long?): Flow<List<Set>>

    fun getLatestSessionByExerciseId(exerciseId: Long?): Flow<Session>

    @Query("SELECT * FROM `Set` WHERE sessionIdForeign = :sessionId")
    fun getAllSetsBySessionId(sessionId: Long?): Flow<List<Set>>

    @Query("SELECT * FROM Exercise")
    fun getAllExercises(): Flow<List<Exercise>>

    @Query("SELECT * FROM `Note`")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM `Session`")
    fun getAllSessions(): Flow<List<Session>>

    @Query("SELECT * FROM `Set`")
    fun getAllSets(): Flow<List<Set>>

    @Query("SELECT * FROM Workout where workoutId = :workoutId")
    fun getWorkoutById(workoutId: Long?): Workout

}