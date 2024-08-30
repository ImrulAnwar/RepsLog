package com.imrul.replog.feature_measurements.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.imrul.replog.feature_measurements.domain.model.Measurement

@Database(
    entities = [
        Measurement::class
    ],
    version = 1
)
abstract class MeasurementDatabase : RoomDatabase() {
    abstract fun measurementDao(): MeasurementDao
}