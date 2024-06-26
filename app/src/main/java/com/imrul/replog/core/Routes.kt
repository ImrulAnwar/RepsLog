package com.imrul.replog.core

import kotlinx.serialization.Serializable

open class Routes {
    @Serializable
    object ScreenWorkoutHistory : Routes()

    @Serializable
    object ScreenWorkout : Routes()

    @Serializable
    object ScreenLogin : Routes()

    @Serializable
    object ScreenRegister : Routes()

    @Serializable
    object ScreenMeasurements : Routes()

    @Serializable
    object ScreenExerciseList : Routes()

    @Serializable
    object ScreenAddEditExercises : Routes()

    @Serializable
    object ScreenProfile : Routes()

    @Serializable
    object ScreenRoutine : Routes()
}