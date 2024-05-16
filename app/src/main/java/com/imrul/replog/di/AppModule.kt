package com.imrul.replog.di

import android.content.Context
import androidx.room.Room
import com.imrul.replog.core.Constants.DATABASE_NAME
import com.imrul.replog.feature_workout.data.data_source.WorkoutDao
import com.imrul.replog.feature_workout.data.data_source.WorkoutDatabase
import com.imrul.replog.feature_workout.data.repository.WorkoutRepositoryImp
import com.imrul.replog.feature_workout.domain.repository.WorkoutRepository
import com.imrul.replog.feature_workout.domain.use_cases.DeleteExercise
import com.imrul.replog.feature_workout.domain.use_cases.DeleteSet
import com.imrul.replog.feature_workout.domain.use_cases.DeleteWorkout
import com.imrul.replog.feature_workout.domain.use_cases.GetAllExercisesByWorkoutId
import com.imrul.replog.feature_workout.domain.use_cases.GetAllSetsByExerciseId
import com.imrul.replog.feature_workout.domain.use_cases.GetAllWorkouts
import com.imrul.replog.feature_workout.domain.use_cases.GetWorkoutById
import com.imrul.replog.feature_workout.domain.use_cases.InsertExercise
import com.imrul.replog.feature_workout.domain.use_cases.InsertSet
import com.imrul.replog.feature_workout.domain.use_cases.InsertWorkout
import com.imrul.replog.feature_workout.domain.use_cases.WorkoutUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
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
    fun provideWorkoutRepository(dao: WorkoutDao) = WorkoutRepositoryImp(dao)

    @Singleton
    @Provides
    fun provideWokroutUseCases(repository: WorkoutRepository) = WorkoutUseCases(
        deleteExercise = DeleteExercise(repository),
        deleteSet = DeleteSet(repository),
        deleteWorkout = DeleteWorkout(repository),
        getAllExercisesByWorkoutId = GetAllExercisesByWorkoutId(repository),
        getAllSetsByExerciseId = GetAllSetsByExerciseId(repository),
        getAllWorkouts = GetAllWorkouts(repository),
        getWorkoutById = GetWorkoutById(repository),
        insertExercise = InsertExercise(repository),
        insertSet = InsertSet(repository),
        insertWorkout = InsertWorkout(repository)
    )
}