package com.imrul.replog.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.imrul.replog.core.Routes
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutScreen
import com.imrul.replog.feature_workout.presentation.screen_workout_history.WorkoutHistoryScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Routes.ScreenWorkoutHistory
    ) {
        composable<Routes.ScreenWorkoutHistory> {
            WorkoutHistoryScreen(navController = navController)
        }
        composable<Routes.ScreenWorkout> {
            WorkoutScreen(navController = navController)
        }
    }
}