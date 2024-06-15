package com.imrul.replog.feature_workout.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.model.Set
import com.imrul.replog.feature_workout.domain.model.Workout

@Database(
    entities = [
        Workout::class,
        Exercise::class,
        Set::class
    ],
    version = 2
)
abstract class WorkoutDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
}