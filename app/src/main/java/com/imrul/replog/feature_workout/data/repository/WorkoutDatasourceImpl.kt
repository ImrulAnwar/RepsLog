package com.imrul.replog.feature_workout.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.imrul.replog.core.Constants.EXERCISES_COLLECTION
import com.imrul.replog.core.Constants.NOTES_COLLECTION
import com.imrul.replog.core.Constants.SESSIONS_COLLECTION
import com.imrul.replog.core.Constants.SETS_COLLECTION
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
        val documentId = exercise.id ?: fireStore.collection(EXERCISES_COLLECTION).document().id
        auth.currentUser?.uid.let { userId ->
            val data = hashMapOf(
                "id" to documentId,
                "name" to exercise.name,
                "imageUrl" to exercise.imageUrl,
                "targetMuscleGroup" to exercise.targetMuscleGroup,
                "weightType" to exercise.weightType,
                "userId" to userId
            )
            fireStore
                .collection(EXERCISES_COLLECTION)
                .document(documentId)
                .set(data)
                .await()
        }
        return documentId
    }

    override suspend fun insertSet(set: Set): String {
        val documentId = set.id ?: fireStore.collection(SETS_COLLECTION).document().id
        auth.currentUser?.uid.let { userId ->
            val data = hashMapOf(
                "id" to documentId,
                "setType" to set.setType,
                "weightValue" to set.weightValue,
                "reps" to set.reps,
                "isDone" to set.isDone,
                "sessionIdForeign" to set.sessionIdForeign,
                "timerDuration" to set.timerDuration,
                "userId" to userId
            )
            fireStore
                .collection(SETS_COLLECTION)
                .document(documentId)
                .set(data)
                .await()
        }
        return documentId
    }

    override suspend fun insertSession(session: Session): String {
        val documentId = session.id ?: fireStore.collection(SESSIONS_COLLECTION).document().id
        auth.currentUser?.uid.let { userId ->
            val data = hashMapOf(
                "id" to documentId,
                "exerciseIdForeign" to session.exerciseIdForeign,
                "exerciseName" to session.exerciseName,
                "workoutIdForeign" to session.workoutIdForeign,
                "repsSlower" to session.repsSlower,
                "weightUnit" to session.weightUnit,
                "setCount" to session.setCount,
                "isTimerEnabled" to session.isTimerEnabled,
                "bestSet" to session.bestSet,
                "userId" to userId
            )
            fireStore
                .collection(SESSIONS_COLLECTION)
                .document(documentId)
                .set(data)
                .await()
        }
        return documentId
    }

    override suspend fun insertNote(note: Note): String {
        val documentId = note.id ?: fireStore.collection(NOTES_COLLECTION).document().id
        auth.currentUser?.uid.let { userId ->
            val data = hashMapOf(
                "id" to documentId,
                "idForeign" to note.idForeign,
                "belongsTo" to note.belongsTo,
                "isPinned" to note.isPinned,
                "content" to note.content,
                "userId" to userId
            )
            fireStore
                .collection(NOTES_COLLECTION)
                .document(documentId)
                .set(data)
                .await()
        }
        return documentId
    }

    override suspend fun deleteWorkout(workout: Workout) {
        workout.id?.let { documentId ->  // Ensure there's an ID to delete
            fireStore
                .collection(WORKOUTS_COLLECTION)
                .document(documentId)
                .delete()
                .await()
        }
    }

    override suspend fun deleteExercise(exercise: Exercise) {
        exercise.id?.let { documentId ->  // Ensure there's an ID to delete
            fireStore
                .collection(EXERCISES_COLLECTION)
                .document(documentId)
                .delete()
                .await()
        }
    }

    override suspend fun deleteSet(set: Set) {
        set.id?.let { documentId ->  // Ensure there's an ID to delete
            fireStore
                .collection(SETS_COLLECTION)
                .document(documentId)
                .delete()
                .await()
        }
    }

    override suspend fun deleteSession(session: Session) {
        session.id?.let { documentId ->  // Ensure there's an ID to delete
            fireStore
                .collection(SESSIONS_COLLECTION)
                .document(documentId)
                .delete()
                .await()
        }
    }

    override suspend fun deleteNote(note: Note) {
        note.id?.let { documentId ->  // Ensure there's an ID to delete
            fireStore
                .collection(NOTES_COLLECTION)
                .document(documentId)
                .delete()
                .await()
        }
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
                val workouts =
                    querySnapshot?.documents?.mapNotNull { it.toObject(Workout::class.java) }
                        ?: emptyList()
                trySend(workouts)  // Emit the list of measurements
            }

            // Close the listener when the flow collector is done
            awaitClose { listener.remove() }
        }
    }


    override suspend fun getExerciseById(exerciseId: String?): Exercise? {
        return exerciseId?.let { documentId ->
            val documentSnapshot = fireStore
                .collection(EXERCISES_COLLECTION)
                .document(documentId)
                .get()
                .await()

            // Check if the document exists and map it to a Measurement object
            if (documentSnapshot.exists()) {
                documentSnapshot.toObject(Exercise::class.java)
            } else
                null
        }
    }


    override suspend fun getAllSessionsByWorkoutId(workoutId: String?): Flow<List<Session>> =
        callbackFlow {
            auth.currentUser?.uid?.let { userId ->
                // Reference to the collection with a query filter by userId
                val query = fireStore.collection(SESSIONS_COLLECTION)
                    .whereEqualTo("userId", userId)
                    .whereEqualTo("workoutIdForeign", workoutId)

                val listener = query.addSnapshotListener { querySnapshot, error ->
                    if (error != null) {
                        close(error)  // Close the flow if there's an error
                        return@addSnapshotListener
                    }

                    // Map each document to a Measurement object and emit the list
                    val sessions =
                        querySnapshot?.documents?.mapNotNull { it.toObject(Session::class.java) }
                            ?: emptyList()
                    trySend(sessions)  // Emit the list of measurements
                }

                // Close the listener when the flow collector is done
                awaitClose { listener.remove() }
            }
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