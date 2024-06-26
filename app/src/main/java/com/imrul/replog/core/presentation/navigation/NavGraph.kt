package com.imrul.replog.core.presentation.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.imrul.replog.core.Routes
import com.imrul.replog.feature_auth.presentation.screen_login.LoginScreen
import com.imrul.replog.feature_auth.presentation.screen_profile.ProfileScreen
import com.imrul.replog.feature_auth.presentation.screen_register.RegisterScreen
import com.imrul.replog.feature_exercises.presentation.screen_add_edit_exercise.AddEditExerciseScreen
import com.imrul.replog.feature_measurements.presentation.screen_measurements.MeasurementsScreen
import com.imrul.replog.feature_exercises.presentation.screen_exercises.ExerciseListScreen
import com.imrul.replog.feature_routine.presentation.screen_routine.RoutineScreen
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutScreen
import com.imrul.replog.feature_workout.presentation.screen_workout_history.WorkoutHistoryScreen
import com.imrul.replog.ui.theme.WhiteCustom

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    context: Context = LocalContext.current,
    isLoggedIn: Boolean,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Routes.ScreenWorkoutHistory else Routes.ScreenLogin,
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
            MeasurementsScreen(navController = navController)
        }
        composable<Routes.ScreenExerciseList> {
            ExerciseListScreen(navController = navController)
        }
        composable<Routes.ScreenAddEditExercises> {
            val args = it.toRoute<Routes.ScreenAddEditExercises>()
            AddEditExerciseScreen(navController = navController, exerciseId = args.exerciseId)
        }
        composable<Routes.ScreenRoutine> {
            RoutineScreen(navController = navController)
        }
    }
}