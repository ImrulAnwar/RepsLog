package com.imrul.replog.feature_measurements.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.imrul.replog.feature_measurements.domain.model.Measurement
import kotlinx.coroutines.flow.Flow

@Dao
interface MeasurementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeasurement(measurement: Measurement): Long

    @Delete
    suspend fun deleteMeasurement(measurement: Measurement)

    @Query("SELECT * FROM Measurement")
    fun getAllMeasurementsByCategory(): Flow<List<Measurement>>

    @Query("SELECT * FROM Measurement WHERE measurementId = :id")
    fun getAllMeasurementById(id: Long?): Measurement
}