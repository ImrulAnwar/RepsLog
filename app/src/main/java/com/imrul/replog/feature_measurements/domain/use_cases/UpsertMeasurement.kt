package com.imrul.replog.feature_measurements.domain.use_cases

import com.imrul.replog.feature_measurements.domain.model.Measurement
import com.imrul.replog.feature_measurements.domain.repository.MeasurementRepository

class UpsertMeasurement(
    private val repository: MeasurementRepository
) {
    suspend operator fun invoke(measurement: Measurement) {
        repository.upsertMeasurement(measurement)
    }
}