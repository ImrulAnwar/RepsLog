package com.imrul.replog.feature_workout.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.imrul.replog.core.Constants.WORKOUTS_COLLECTION
import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.model.Note
import com.imrul.replog.feature_workout.domain.model.Session
import com.imrul.replog.feature_workout.domain.model.Set
import com.imrul.replog.feature_workout.domain.model.Workout
import com.imrul.replog.feature_workout.domain.repository.WorkoutDatasource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class WorkoutDatasourceImpl(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : WorkoutDatasource {
    override suspend fun insertWorkout(workout: Workout): String {
        val documentId = workout.id ?: fireStore.collection(WORKOUTS_COLLECTION).document().id
        auth.currentUser?.uid.let { userId ->
            val data = hashMapOf(
                "id" to documentId,
                "name" to workout.name,
                "duration" to workout.duration,
                "date" to workout.date,
                "dateString" to workout.dateString,
                "weekdayString" to workout.weekdayString,
                "durationString" to workout.durationString,
                "userId" to userId
            )
            fireStore
                .collection(WORKOUTS_COLLECTION)
                .document(documentId)
                .set(data)
                .await()
        }
        return documentId
    }

    override suspend fun insertExercise(exercise: Exercise): String {
        TODO("Not yet implemented")
    }

    override suspend fun insertSet(set: Set): String {
        TODO("Not yet implemented")
    }

    override suspend fun insertSession(session: Session): String {
        TODO("Not yet implemented")
    }

    override suspend fun insertNote(note: Note): String {
        TODO("Not yet implemented")
    }

    override suspend fun deleteWorkout(workout: Workout) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteExercise(exercise: Exercise) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSet(set: Set) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSession(session: Session) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllWorkouts(): Flow<List<Workout>> = callbackFlow {
        auth.currentUser?.uid?.let { userId ->
            // Reference to the collection with a query filter by userId
            val query = fireStore.collection(WORKOUTS_COLLECTION)
                .whereEqualTo("userId", userId)

            val listener = query.addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    close(error)  // Close the flow if there's an error
                    return@addSnapshotListener
                }

                // Map each document to a Measurement object and emit the list
                val workouts = querySnapshot?.documents?.mapNotNull { it.toObject(Workout::class.java) } ?: emptyList()
                trySend(workouts)  // Emit the list of measurements
            }

            // Close the listener when the flow collector is done
            awaitClose { listener.remove() }
        }
    }


    override suspend fun getExerciseById(exerciseId: String?): Exercise {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSessionsByWorkoutId(workoutId: String?): Flow<List<Session>> {
        TODO("Not yet implemented")
    }

    override suspend fun getLatestSessionByExerciseId(exerciseId: String?): Flow<Session?> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllNotes(): Flow<List<Note>> {
        TODO("Not yet implemented")
    }

    override suspend fun getNotesByForeignId(foreignId: String): Flow<List<Note>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSessions(): Flow<List<Session>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSetsBySessionId(exerciseId: String?): Flow<List<Set>> {
        TODO("Not yet implemented")
    }

    override suspend fun getWorkoutById(workoutId: String?): Workout {
        TODO("Not yet implemented")
    }

    override suspend fun getAllExercises(): Flow<List<Exercise>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSets(): Flow<List<Set>> {
        TODO("Not yet implemented")
    }
}