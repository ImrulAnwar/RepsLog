package com.imrul.replog.core.presentation.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.imrul.replog.core.Routes
import com.imrul.replog.feature_auth.presentation.screen_login.LoginScreen
import com.imrul.replog.feature_auth.presentation.screen_link_account.LinkAccountScreen
import com.imrul.replog.feature_auth.presentation.screen_profile.ProfileScreen
import com.imrul.replog.feature_auth.presentation.screen_profile.ProfileViewModel
import com.imrul.replog.feature_auth.presentation.screen_register.RegisterScreen
import com.imrul.replog.feature_exercises.presentation.screen_add_edit_exercise.AddEditExerciseScreen
import com.imrul.replog.feature_exercises.presentation.screen_exercises.ExerciseListScreen
import com.imrul.replog.feature_exercises.presentation.screen_exercises.ExerciseListViewModel
import com.imrul.replog.feature_exercises.presentation.screen_filter_exercise.FilterExerciseScreen
import com.imrul.replog.feature_measurements.presentation.screen_add_edit_measurements.AddEditMeasurementScreen
import com.imrul.replog.feature_measurements.presentation.screen_add_edit_measurements.AddEditMeasurementViewModel
import com.imrul.replog.feature_measurements.presentation.screen_measurements.MeasurementsScreen
import com.imrul.replog.feature_measurements.presentation.screen_measurements.MeasurementsViewModel
import com.imrul.replog.feature_workout.presentation.screen_exercise_list_from_workout.ExerciseListScreenFromWorkout
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutScreen
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutViewModel
import com.imrul.replog.feature_workout.presentation.screen_workout_history.WorkoutHistoryScreen
import com.imrul.replog.feature_workout.presentation.screen_workout_history.WorkoutHistoryViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NavGraph(
    modifier: Modifier,
    navController: NavHostController,
    context: Context = LocalContext.current,
    exerciseListViewModel: ExerciseListViewModel = hiltViewModel(),
    workoutViewModel: WorkoutViewModel = hiltViewModel(),
    measurementsViewModel: MeasurementsViewModel = hiltViewModel(),
    addEditMeasurementViewModel: AddEditMeasurementViewModel = hiltViewModel(),
) {

    NavHost(
        navController = navController,
        startDestination = Routes.ScreenLogin,
    ) {
        composable<Routes.ScreenWorkoutHistory> {
            WorkoutHistoryScreen(
                navController = navController,
                workoutViewModel = workoutViewModel,
            )
        }
        composable<Routes.ScreenWorkout> {
            WorkoutScreen(navController = navController, workoutViewModel = workoutViewModel)
        }

        composable<Routes.ScreenLinkAccount> {
            LinkAccountScreen(navController = navController)
        }

        composable<Routes.ScreenLogin> {
            LoginScreen(navController = navController)
        }
        composable<Routes.ScreenRegister> {
            RegisterScreen(navController = navController)
        }

        composable<Routes.ScreenProfile> {
            ProfileScreen(
                navController = navController,
                workoutViewModel = workoutViewModel,
            )
        }
        composable<Routes.ScreenMeasurements> {
            MeasurementsScreen(
                navController = navController,
                workoutViewModel = workoutViewModel,
                measurementsViewModel = measurementsViewModel,
                addEditMeasurementViewModel = addEditMeasurementViewModel
            )
        }

        composable<Routes.ScreenAddEditMeasurements> {
            val args = it.toRoute<Routes.ScreenAddEditMeasurements>()
            AddEditMeasurementScreen(
                navController = navController,
                measurementId = args.measurementId,
                viewModel = addEditMeasurementViewModel
            )
        }
        composable<Routes.ScreenExerciseList> {
            ExerciseListScreen(
                navController = navController,
                viewModel = exerciseListViewModel,
                workoutViewModel = workoutViewModel
            )
        }
        composable<Routes.ScreenExerciseListFromWorkout> {
            ExerciseListScreenFromWorkout(
                navController = navController,
                exerciseListViewModel = exerciseListViewModel,
                workoutViewModel = workoutViewModel
            )
        }
        composable<Routes.ScreenAddEditExercises> {
            val args = it.toRoute<Routes.ScreenAddEditExercises>()
            AddEditExerciseScreen(
                navController = navController,
                exerciseId = args.exerciseId
            )
        }
        composable<Routes.ScreenFilterExercise> {
            FilterExerciseScreen(navController = navController, viewModel = exerciseListViewModel)
        }
    }
}