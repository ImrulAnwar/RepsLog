package com.imrul.replog.core.presentation.navigation

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.imrul.replog.core.Routes
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutScreen
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutService
import com.imrul.replog.feature_workout.presentation.screen_workout_history.WorkoutHistoryScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    context: Context = LocalContext.current
) {

    var startDestination by remember { mutableStateOf<Routes>(Routes.ScreenWorkoutHistory) }
    LaunchedEffect(Unit) {
        startDestination = if (isServiceRunning(context, WorkoutService::class.java)) {
            Routes.ScreenWorkout
        } else {
            Routes.ScreenWorkoutHistory
        }
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Routes.ScreenWorkoutHistory> {
            WorkoutHistoryScreen(navController = navController)
        }
        composable<Routes.ScreenWorkout> {
            WorkoutScreen(navController = navController)
        }
    }
}


fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
    val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (service in manager.getRunningServices(Int.MAX_VALUE)) {
        if (serviceClass.name == service.service.className) {
            return true
        }
    }
    return false
}