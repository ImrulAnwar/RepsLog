package com.imrul.replog.feature_routine.presentation.screen_routine

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
import com.imrul.replog.feature_routine.presentation.components.TemplateItem
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutService
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutViewModel
import com.imrul.replog.feature_workout.presentation.screen_workout_history.WorkoutHistoryViewModel
import com.imrul.replog.ui.theme.Maroon70
import com.imrul.replog.ui.theme.WhiteCustom

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RoutineScreen(
    navController: NavHostController,
    workoutHistoryViewModel: WorkoutHistoryViewModel = hiltViewModel(),
    routinesViewModel: RoutinesViewModel,
    workoutViewModel: WorkoutViewModel
) {
    LaunchedEffect(Unit) {
        workoutHistoryViewModel.getAllWorkouts()
    }

    val workoutListState by workoutHistoryViewModel.workoutListState.collectAsState()
    val sessionsList by workoutHistoryViewModel.sessionsList.collectAsState()
    val context = LocalContext.current

    if (routinesViewModel.isInserting)
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
                    .padding(bottom = if (!routinesViewModel.isWorkOutRunning) 80.dp else 0.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    CustomButton(
                        onClick = {
                            routinesViewModel.clearAllData()
                            startWorkout(routinesViewModel, navController, context)
                        },
                        modifier = Modifier.padding(top = 20.dp),
                        text = Strings.CREATE_NEW_TEMPLATE
                    )
                }
                items(workoutListState) { workout ->
                    TemplateItem(
                        workout = workout,
                        onClick = {
                            routinesViewModel.clearAllData()
                            routinesViewModel.setWorkoutName(workout.name)
                            workout.workoutId?.let { routinesViewModel.getAllWorkoutNotes(it) }
                            startWorkout(routinesViewModel, navController, context)
                            sessionsList.forEach { session ->

                                //
                                if (session.workoutIdForeign == workout.workoutId) {
                                    session.exerciseName?.let { name ->
                                        session.exerciseIdForeign?.let { exerciseId ->
                                            routinesViewModel.addExerciseAndSets(
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


@RequiresApi(Build.VERSION_CODES.O)
private fun startWorkout(
    routinesViewModel: RoutinesViewModel,
    navController: NavHostController,
    context: Context
) {
    if (!routinesViewModel.isWorkOutRunning) {
        routinesViewModel.setWorkoutRunning(true)
        navController.navigate(Routes.ScreenWorkout)
        // start service
        Intent(context, WorkoutService::class.java).also {
            it.action = WorkoutService.Actions.START.toString()
            context.startForegroundService(it)
        }
    } else {
        Toast.makeText(context, "Already Running Workout", Toast.LENGTH_SHORT)
            .show()
    }
}