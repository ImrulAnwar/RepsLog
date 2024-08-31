package com.imrul.replog.feature_measurements.presentation.screen_measurements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.imrul.replog.core.presentation.components.LineChartGraph
import com.imrul.replog.core.presentation.components.MiniPlayer
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutViewModel

@Composable
fun MeasurementsScreen(
    navController: NavHostController,
    workoutViewModel: WorkoutViewModel
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        LineChartGraph()
        if (workoutViewModel.isWorkOutRunning)
            MiniPlayer(
                workoutViewModel = workoutViewModel,
                navController = navController
            )
    }

}