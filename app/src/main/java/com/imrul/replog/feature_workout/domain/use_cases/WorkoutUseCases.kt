package com.imrul.replog.feature_workout.domain.use_cases

data class WorkoutUseCases(
    val deleteExercise: DeleteExercise,
    val deleteSet: DeleteSet,
    val deleteWorkout: DeleteWorkout,
    val deleteNote: DeleteNote,
    val deleteSession: DeleteSession,
    val getAllExercisesByWorkoutId: GetAllExercisesByWorkoutId,
    val getAllNotes: GetAllNotes,
    val getAllSessions: GetAllSessions,
    val getAllSessionsByExerciseId: GetAllSessionsByExerciseId,
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