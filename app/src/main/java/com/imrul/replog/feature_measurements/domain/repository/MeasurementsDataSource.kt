package com.imrul.replog.feature_measurements.domain.repository

import com.imrul.replog.feature_measurements.domain.model.Measurement
import kotlinx.coroutines.flow.Flow

interface MeasurementsDataSource {
    suspend fun upsertMeasurement(measurement: Measurement): Long
    suspend fun deleteMeasurement(measurement: Measurement)
    fun getAllMeasurementsByCategory(): Flow<List<Measurement>>
    fun getAllMeasurementById(id: Long?): Measurement
}