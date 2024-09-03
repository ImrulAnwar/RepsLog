package com.imrul.replog.feature_measurements.data.repository

import com.imrul.replog.feature_measurements.data.data_source.MeasurementDao
import com.imrul.replog.feature_measurements.domain.model.Measurement
import com.imrul.replog.feature_measurements.domain.repository.MeasurementRepository
import kotlinx.coroutines.flow.Flow

class MeasurementRepositoryImpl(
    private val dao: MeasurementDao
) : MeasurementRepository {
    override suspend fun upsertMeasurement(measurement: Measurement): Long =
        dao.upsertMeasurement(measurement)

    override suspend fun deleteMeasurement(measurement: Measurement) =
        dao.deleteMeasurement(measurement)

    override fun getAllMeasurementsByCategory(category: String): Flow<List<Measurement>> =
        dao.getAllMeasurementsByCategory(category)

    override fun getMeasurementsById(id: Long?): Measurement =
        dao.getAllMeasurementById(id)

}