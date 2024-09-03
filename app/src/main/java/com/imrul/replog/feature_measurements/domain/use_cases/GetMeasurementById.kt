package com.imrul.replog.feature_measurements.domain.use_cases

import com.imrul.replog.feature_measurements.domain.repository.MeasurementRepository

class GetMeasurementById(
    private val repository: MeasurementRepository
) {
    suspend operator fun invoke(id: Long) =
        repository.getMeasurementsById(id)
}
