package com.imrul.replog.feature_workout.presentation.screen_workout_history

import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.imrul.replog.core.Routes
import com.imrul.replog.core.Strings
import com.imrul.replog.core.presentation.CustomButton
import com.imrul.replog.core.presentation.components.MiniPlayer
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutService
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutViewModel
import com.imrul.replog.feature_workout.presentation.screen_workout_history.components.WorkoutItem
import com.imrul.replog.ui.theme.Maroon70
import com.imrul.replog.ui.theme.WhiteCustom

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun WorkoutHistoryScreen(
    navController: NavHostController,
    workoutHistoryViewModel: WorkoutHistoryViewModel = hiltViewModel(),
    workoutViewModel: WorkoutViewModel
) {
    LaunchedEffect(Unit) {
        workoutHistoryViewModel.getAllWorkouts()
    }

    val workoutListState by workoutHistoryViewModel.workoutListState.collectAsState()
    val sessionsList by workoutHistoryViewModel.sessionsList.collectAsState()
    val context = LocalContext.current

    if (workoutViewModel.isInserting)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WhiteCustom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = Maroon70)
        }
    else
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WhiteCustom),
            verticalArrangement = Arrangement.Top
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = if (!workoutViewModel.isWorkOutRunning) 80.dp else 0.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    CustomButton(
                        onClick = {
                            workoutViewModel.clearAllData()
                            startWorkout(workoutViewModel, navController, context)
                        },
                        modifier = Modifier.padding(top = 20.dp),
                        text = Strings.START_EMPTY_WORKOUT
                    )
                }
                items(workoutListState) { workout ->
                    WorkoutItem(
                        workout = workout,
                        onClick = {
                            workoutViewModel.clearAllData()
                            workoutViewModel.setWorkoutName(workout.name)
                            startWorkout(workoutViewModel, navController, context)
                            workout.id?.let { workoutViewModel.getAllWorkoutNotes(it) }
                            sessionsList.forEach { session ->

                                //
                                if (session.workoutIdForeign == workout.id) {
                                    session.exerciseName?.let { name ->
                                        session.exerciseIdForeign?.let { exerciseId ->
                                            workoutViewModel.addExerciseAndSets(
                                                name = name,
                                                exerciseId = exerciseId,
                                                context = context
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    )
                }
            }
            if (workoutViewModel.isWorkOutRunning)
                MiniPlayer(
                    workoutViewModel = workoutViewModel,
                    navController = navController
                )
        }

}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private fun startWorkout(
    workoutViewModel: WorkoutViewModel,
    navController: NavHostController,
    context: Context
) {
    if (!workoutViewModel.isWorkOutRunning) {
        // start service
        Intent(context, WorkoutService::class.java).also {
            it.action = WorkoutService.Actions.START.toString()
            context.startForegroundService(it)
        }
        workoutViewModel.setWorkoutRunning(true)
        navController.navigate(Routes.ScreenWorkout)
    } else {
        Toast.makeText(context, "Already Running Workout", Toast.LENGTH_SHORT)
            .show()
    }
}