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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.imrul.replog.core.Routes
import com.imrul.replog.feature_auth.presentation.screen_login.LoginScreen
import com.imrul.replog.feature_auth.presentation.screen_login.model.LoginState
import com.imrul.replog.feature_auth.presentation.screen_profile.ProfileScreen
import com.imrul.replog.feature_auth.presentation.screen_register.RegisterScreen
import com.imrul.replog.feature_measurements.presentation.screen_measurements.MeasurementsScreen
import com.imrul.replog.feature_workout.presentation.screen_exercises.ExercisesScreen
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutScreen
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutService
import com.imrul.replog.feature_workout.presentation.screen_workout_history.WorkoutHistoryScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    context: Context = LocalContext.current,
    isLoggedIn: Boolean
) {

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Routes.ScreenWorkoutHistory else Routes.ScreenLogin
    ) {
        composable<Routes.ScreenWorkoutHistory> {
            WorkoutHistoryScreen(navController = navController)
        }
        composable<Routes.ScreenWorkout> {
            WorkoutScreen(navController = navController)
        }

        composable<Routes.ScreenLogin> {
            LoginScreen(navController = navController)
        }
        composable<Routes.ScreenRegister> {
            RegisterScreen(navController = navController)
        }

        composable<Routes.ScreenProfile> {
            ProfileScreen(navController = navController)
        }
        composable<Routes.ScreenMeasurements> {
            MeasurementsScreen()
        }
        composable<Routes.ScreenExercises> {
            ExercisesScreen()
        }
    }
}