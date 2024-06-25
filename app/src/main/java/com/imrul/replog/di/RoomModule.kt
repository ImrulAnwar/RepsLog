package com.imrul.replog.di

import android.content.Context
import androidx.room.Room
import com.imrul.replog.core.Constants.DATABASE_NAME
import com.imrul.replog.feature_workout.data.data_source.WorkoutDao
import com.imrul.replog.feature_workout.data.data_source.WorkoutDatabase
import com.imrul.replog.feature_workout.data.repository.WorkoutRepositoryImp
import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository
import com.imrul.replog.feature_workout.domain.use_cases.CreateRunningWorkoutNotificationUseCase
import com.imrul.replog.feature_workout.domain.use_cases.DeleteExercise
import com.imrul.replog.feature_workout.domain.use_cases.DeleteNote
import com.imrul.replog.feature_workout.domain.use_cases.DeleteSession
import com.imrul.replog.feature_workout.domain.use_cases.DeleteSet
import com.imrul.replog.feature_workout.domain.use_cases.DeleteWorkout
import com.imrul.replog.feature_workout.domain.use_cases.DurationUseCase
import com.imrul.replog.feature_workout.domain.use_cases.GetAllExercises
import com.imrul.replog.feature_workout.domain.use_cases.GetExerciseById
import com.imrul.replog.feature_workout.domain.use_cases.GetAllNotes
import com.imrul.replog.feature_workout.domain.use_cases.GetAllSessions
import com.imrul.replog.feature_workout.domain.use_cases.GetAllSessionsByWorkoutId
import com.imrul.replog.feature_workout.domain.use_cases.GetAllSets
import com.imrul.replog.feature_workout.domain.use_cases.GetAllSetsBySessionId
import com.imrul.replog.feature_workout.domain.use_cases.GetAllWorkouts
import com.imrul.replog.feature_workout.domain.use_cases.GetLatestSessionByExerciseId
import com.imrul.replog.feature_workout.domain.use_cases.GetWorkoutById
import com.imrul.replog.feature_workout.domain.use_cases.InsertExercise
import com.imrul.replog.feature_workout.domain.use_cases.InsertNote
import com.imrul.replog.feature_workout.domain.use_cases.InsertSession
import com.imrul.replog.feature_workout.domain.use_cases.InsertSet
import com.imrul.replog.feature_workout.domain.use_cases.InsertWorkout
import com.imrul.replog.feature_workout.domain.use_cases.ShouldInsertWorkout
import com.imrul.replog.feature_workout.domain.use_cases.WorkoutUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideWorkoutDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, WorkoutDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun workoutDao(
        database: WorkoutDatabase
    ) = database.workoutDao()

    @Singleton
    @Provides
    fun provideWorkoutRepository(dao: WorkoutDao): WorkoutRepository = WorkoutRepositoryImp(dao)

    @Singleton
    @Provides
    fun provideWorkoutUseCases(repository: WorkoutRepository) = WorkoutUseCases(
        deleteExercise = DeleteExercise(repository),
        deleteSet = DeleteSet(repository),
        deleteWorkout = DeleteWorkout(repository),
        getExerciseById = GetExerciseById(repository),
        getAllSetsBySessionId = GetAllSetsBySessionId(repository),
        getAllWorkouts = GetAllWorkouts(repository),
        getWorkoutById = GetWorkoutById(repository),
        insertExercise = InsertExercise(repository),
        insertSet = InsertSet(repository),
        insertWorkout = InsertWorkout(repository),
        getAllExercises = GetAllExercises(repository),
        getAllSets = GetAllSets(repository),
        shouldInsertWorkout = ShouldInsertWorkout(),
        durationUseCase = DurationUseCase(),
        createRunningWorkoutNotificationUseCase = CreateRunningWorkoutNotificationUseCase(),
        deleteNote = DeleteNote(repository),
        insertNote = InsertNote(repository),
        deleteSession = DeleteSession(repository),
        getAllNotes = GetAllNotes(repository),
        getAllSessions = GetAllSessions(repository),
        getAllSessionsByWorkoutId = GetAllSessionsByWorkoutId(repository),
        getLatestSessionByExerciseId = GetLatestSessionByExerciseId(repository),
        insertSession = InsertSession(repository)
    )
}