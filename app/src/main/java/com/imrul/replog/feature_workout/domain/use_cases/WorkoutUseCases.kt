package com.imrul.replog.feature_workout.domain.use_cases

import com.imrul.replog.feature_exercises.domain.use_cases.DeleteExercise
import com.imrul.replog.feature_exercises.domain.use_cases.GetAllExercises
import com.imrul.replog.feature_exercises.domain.use_cases.GetExerciseById
import com.imrul.replog.feature_exercises.domain.use_cases.InsertExercise

data class WorkoutUseCases(
    val deleteExercise: DeleteExercise,
    val deleteSet: DeleteSet,
    val deleteWorkout: DeleteWorkout,
    val deleteNote: DeleteNote,
    val deleteSession: DeleteSession,
    val getExerciseById: GetExerciseById,
    val getAllNotes: GetAllNotes,
    val getNotesByWorkoutId: GetNotesByWorkoutId,
    val getNotesBySessionId: GetNotesBySessionId,
    val getAllSessions: GetAllSessions,
    val getAllSessionsByWorkoutId: GetAllSessionsByWorkoutId,
    val getAllSetsBySessionId: GetAllSetsBySessionId,
    val getAllWorkouts: GetAllWorkouts,
    val getLatestSessionByExerciseId: GetLatestSessionByExerciseId,
    val getWorkoutById: GetWorkoutById,
    val insertExercise: InsertExercise,
    val insertNote: InsertNote,
    val insertSession: InsertSession,
    val insertSet: InsertSet,
    val insertWorkout: InsertWorkout,
    val getAllExercises: GetAllExercises,
    val getAllSets: GetAllSets,
    val shouldInsertWorkout: ShouldInsertWorkout,
    val durationUseCase: DurationUseCase,
    val createRunningWorkoutNotificationUseCase: CreateRunningWorkoutNotificationUseCase
)