package com.imrul.replog.feature_measurements.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.imrul.replog.core.Constants.MEASUREMENTS_COLLECTION
import com.imrul.replog.feature_measurements.domain.model.Measurement
import com.imrul.replog.feature_measurements.domain.repository.MeasurementsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class MeasurementsDataSourceImpl(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : MeasurementsDataSource {
    override suspend fun upsertMeasurement(measurement: Measurement): Long {
        auth.currentUser?.uid.let { userId ->
            val data = hashMapOf(
                "value" to measurement.value,
                "unit" to measurement.unit,
                "category" to measurement.category,
                "timestamp" to measurement.timeStamp,
                "userId" to userId
            )
            fireStore
                .collection(MEASUREMENTS_COLLECTION)
                .document()
                .set(data)
                .await()
        }
        return 0
    }

    override suspend fun deleteMeasurement(measurement: Measurement) {
        TODO("Not yet implemented")
    }

    override fun getAllMeasurementsByCategory(): Flow<List<Measurement>> {
        TODO("Not yet implemented")
    }

    override fun getAllMeasurementById(id: Long?): Measurement {
        TODO("Not yet implemented")
    }
}