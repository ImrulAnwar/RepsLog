package com.imrul.replog.feature_measurements.domain.use_cases

import com.imrul.replog.feature_measurements.domain.model.Measurement
import com.imrul.replog.feature_measurements.domain.repository.MeasurementRepository

class GetAllMeasurementsByCategory(
    private val repository: MeasurementRepository
) {
    operator fun invoke(category: String){
        repository.getAllMeasurementsByCategory(category)
    }
}