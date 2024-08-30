package com.imrul.replog.feature_measurements.domain.use_cases

data class MeasurementUseCases (
    val deleteMeasurement: DeleteMeasurement,
    val getAllMeasurementsByCategory: GetAllMeasurementsByCategory,
    val upsertMeasurement: UpsertMeasurement
)