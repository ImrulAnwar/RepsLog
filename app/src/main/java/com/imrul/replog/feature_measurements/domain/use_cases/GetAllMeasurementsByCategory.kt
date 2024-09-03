package com.imrul.replog.feature_measurements.domain.use_cases

import com.imrul.replog.feature_measurements.domain.model.Measurement
import com.imrul.replog.feature_measurements.domain.repository.MeasurementRepository
import kotlinx.coroutines.flow.Flow

class GetAllMeasurementsByCategory(
    private val repository: MeasurementRepository
) {
    operator fun invoke(category: String): Flow<List<Measurement>> =
        repository.getAllMeasurementsByCategory(category)
}