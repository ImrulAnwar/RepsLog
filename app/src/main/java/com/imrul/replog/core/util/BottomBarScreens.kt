package com.imrul.replog.core.util

import com.imrul.replog.R
import com.imrul.replog.core.Routes
import com.imrul.replog.core.Strings

sealed class BottomBarScreens(
    val route: Routes,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val title: String
) {
    data object WorkoutHistoryScreenObject : BottomBarScreens(
        route = Routes.ScreenWorkoutHistory,
        selectedIcon = R.drawable.icon_history,
        unselectedIcon = R.drawable.icon_history,
        title = Strings.HISTORY
    )

    data object MeasurementsScreenObject : BottomBarScreens(
        route = Routes.ScreenMeasurements,
        selectedIcon = R.drawable.icon_measure,
        unselectedIcon = R.drawable.icon_measure,
        title = Strings.MEASURE
    )

    data object ExercisesScreenObject : BottomBarScreens(
        route = Routes.ScreenExerciseList,
        selectedIcon = R.drawable.icon_exercises,
        unselectedIcon = R.drawable.icon_exercises,
        title = Strings.EXERCISES
    )

    data object ProfileScreenObject : BottomBarScreens(
        route = Routes.ScreenProfile,
        selectedIcon = R.drawable.icon_profile,
        unselectedIcon = R.drawable.icon_profile,
        title = Strings.PROFILE
    )
}