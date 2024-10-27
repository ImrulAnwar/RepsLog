package com.imrul.replog.feature_workout.data.data_source

import androidx.room.RoomDatabase

abstract class WorkoutDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
}