package com.imrul.replog.feature_measurements.data.repository

import com.imrul.replog.feature_measurements.data.data_source.MeasurementDao
import com.imrul.replog.feature_measurements.domain.model.Measurement
import com.imrul.replog.feature_measurements.domain.repository.MeasurementRepository
import com.imrul.replog.feature_measurements.domain.repository.MeasurementsDataSource
import kotlinx.coroutines.flow.Flow

class MeasurementRepositoryImpl(
    private val dao: MeasurementDao,
    private val datasource: MeasurementsDataSource
) : MeasurementRepository {
    override suspend fun upsertMeasurement(measurement: Measurement): Long =
        datasource.upsertMeasurement(measurement)

    override suspend fun deleteMeasurement(measurement: Measurement) =
        dao.deleteMeasurement(measurement)

    override fun getAllMeasurements(category: String): Flow<List<Measurement>> =
        dao.getAllMeasurementsByCategory()

    override suspend fun getMeasurementsById(id: Long?): Measurement =
        dao.getAllMeasurementById(id)

}