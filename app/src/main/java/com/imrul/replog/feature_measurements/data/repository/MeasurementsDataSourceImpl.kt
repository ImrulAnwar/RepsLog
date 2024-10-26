package com.imrul.replog.feature_measurements.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.imrul.replog.core.Constants.MEASUREMENTS_COLLECTION
import com.imrul.replog.feature_measurements.domain.model.Measurement
import com.imrul.replog.feature_measurements.domain.repository.MeasurementsDataSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class MeasurementsDataSourceImpl(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : MeasurementsDataSource {
    override suspend fun upsertMeasurement(measurement: Measurement): Long {
        auth.currentUser?.uid.let { userId ->
            val documentId = measurement.id ?: fireStore.collection(MEASUREMENTS_COLLECTION).document().id
            val data = hashMapOf(
                "id" to documentId,
                "value" to measurement.value,
                "unit" to measurement.unit,
                "category" to measurement.category,
                "timestamp" to measurement.timeStamp,
                "userId" to userId
            )
            fireStore
                .collection(MEASUREMENTS_COLLECTION)
                .document(documentId)
                .set(data)
                .await()
        }
        return 0
    }

    override suspend fun deleteMeasurement(measurement: Measurement) {
        measurement.id?.let { documentId ->  // Ensure there's an ID to delete
            fireStore
                .collection(MEASUREMENTS_COLLECTION)
                .document(documentId)
                .delete()
                .await()
        }
    }

    override suspend fun getAllMeasurements(): Flow<List<Measurement>> = callbackFlow {
        auth.currentUser?.uid?.let { userId ->
            // Reference to the collection with a query filter by userId
            val query = fireStore.collection(MEASUREMENTS_COLLECTION)
                .whereEqualTo("userId", userId)

            val listener = query.addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    close(error)  // Close the flow if there's an error
                    return@addSnapshotListener
                }

                // Map each document to a Measurement object and emit the list
                val measurements = querySnapshot?.documents?.mapNotNull { it.toObject(Measurement::class.java) } ?: emptyList()
                trySend(measurements)  // Emit the list of measurements
            }

            // Close the listener when the flow collector is done
            awaitClose { listener.remove() }
        }
    }

    override suspend fun getMeasurementById(id: String?): Measurement? {
        return id?.let { documentId ->
            val documentSnapshot = fireStore
                .collection(MEASUREMENTS_COLLECTION)
                .document(documentId)
                .get()
                .await()

            // Check if the document exists and map it to a Measurement object
            if (documentSnapshot.exists()) {
                documentSnapshot.toObject(Measurement::class.java)
            } else {
                null  // Return null if the document doesn't exist
            }
        }
    }
}