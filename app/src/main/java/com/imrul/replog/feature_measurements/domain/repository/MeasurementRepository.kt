package com.imrul.replog.feature_measurements.domain.repository

import com.imrul.replog.feature_measurements.domain.model.Measurement
import kotlinx.coroutines.flow.Flow

interface MeasurementRepository {
    suspend fun upsertMeasurement(measurement: Measurement): Long

    suspend fun deleteMeasurement(measurement: Measurement)

    suspend fun getAllMeasurements(category: String): Flow<List<Measurement>>
    suspend fun getMeasurementsById(id: String?): Measurement?
}