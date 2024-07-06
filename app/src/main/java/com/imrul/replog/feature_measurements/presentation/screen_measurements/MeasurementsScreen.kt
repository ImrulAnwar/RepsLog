package com.imrul.replog.feature_measurements.presentation.screen_measurements

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.imrul.replog.core.presentation.components.MiniPlayer
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutViewModel

@Composable
fun MeasurementsScreen(
    navController: NavHostController,
    workoutViewModel: WorkoutViewModel
) {
    if (workoutViewModel.isWorkOutRunning)
        MiniPlayer(
            workoutViewModel = workoutViewModel,
            navController = navController
        )

}