package com.imrul.replog.feature_measurements.domain.repository

import com.imrul.replog.feature_measurements.domain.model.Measurement
import kotlinx.coroutines.flow.Flow

interface MeasurementsDataSource {
    suspend fun upsertMeasurement(measurement: Measurement): Long
    suspend fun deleteMeasurement(measurement: Measurement)
    suspend fun getAllMeasurements(): Flow<List<Measurement>>
    suspend fun getMeasurementById(id: String?): Measurement?
}