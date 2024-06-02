package com.imrul.replog.feature_workout.domain.use_cases

data class WorkoutUseCases(
    val deleteExercise: DeleteExercise,
    val deleteSet: DeleteSet,
    val deleteWorkout: DeleteWorkout,
    val getAllExercisesByWorkoutId: GetAllExercisesByWorkoutId,
    val getAllSetsByExerciseId: GetAllSetsByExerciseId,
    val getAllWorkouts: GetAllWorkouts,
    val getWorkoutById: GetWorkoutById,
    val insertExercise: InsertExercise,
    val insertSet: InsertSet,
    val insertWorkout: InsertWorkout,
    val getAllExercises: GetAllExercises,
    val getAllSets: GetAllSets,
    val shouldInsertWorkout: ShouldInsertWorkout,
    val durationUseCase: DurationUseCase,
    val createRunningWorkoutNotificationUseCase: CreateRunningWorkoutNotificationUseCase
)