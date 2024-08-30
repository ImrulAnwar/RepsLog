package com.imrul.replog.feature_measurements.domain.repository

import androidx.room.Delete
import androidx.room.Query
import com.imrul.replog.feature_measurements.domain.model.Measurement
import kotlinx.coroutines.flow.Flow

interface MeasurementRepository {
    suspend fun upsertMeasurement(measurement: Measurement): Long

    suspend fun deleteMeasurement(measurement: Measurement)

    fun getAllMeasurementsByCategory(category: String): Flow<List<Measurement>>
}