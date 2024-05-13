package com.imrul.replog.di

import android.content.Context
import androidx.room.Room
import com.imrul.replog.core.Constants.TEST_DATABASE_NAME
import com.imrul.replog.feature_workout.data.data_source.WorkoutDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Provides
    @Named(TEST_DATABASE_NAME)
    fun provideInMemoryDb(
        @ApplicationContext context: Context
    ) = Room.inMemoryDatabaseBuilder(
        context,
        WorkoutDatabase::class.java
    ).allowMainThreadQueries().build()
}