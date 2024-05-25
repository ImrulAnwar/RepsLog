package com.imrul.replog.feature_workout.presentation.screen_workout

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.imrul.replog.core.Strings
import com.imrul.replog.core.presentation.components.CustomIcon
import com.imrul.replog.core.presentation.components.RegularTextField
import com.imrul.replog.feature_workout.presentation.screen_workout.components.ExerciseItem

@Composable
fun WorkoutScreen(
    navController: NavHostController,
    workoutViewModel: WorkoutViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val workoutTitle = workoutViewModel.workoutTitle
    val listState = rememberScrollState()

    val listOfExercises = workoutViewModel.listOfExercises

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                workoutViewModel.addExercise()
            }) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Filled.Add),
                    contentDescription = Strings.ADD_BUTTON
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // This is the top part
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp)
                ) {
                    CustomIcon(
                        painter = rememberVectorPainter(image = Icons.AutoMirrored.Filled.ArrowBack),
                        contentDescription = Strings.BACK_BUTTON,
                        onClick = {
                            navController.popBackStack()
                        }
                    )

                    Spacer(modifier = Modifier.weight(1f))
                    CustomIcon(
                        painter = rememberVectorPainter(image = Icons.Filled.Close),
                        contentDescription = Strings.CANCELLED_WORKOUT_BUTTON,
                        onClick = {
                            navController.popBackStack()
                        }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    CustomIcon(
                        painter = rememberVectorPainter(image = Icons.Filled.Done),
                        contentDescription = Strings.FINISHED_WORKOUT_BUTTON,
                        onClick = {
                            if (!workoutViewModel.shouldInsertWorkout()) {
                                Toast.makeText(
                                    context,
                                    "Please complete at least 1 set of exercise.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                workoutViewModel.insertWorkout()
                                navController.popBackStack()
                            }
                        }
                    )
                }
                // This is the body

                RegularTextField(
                    text = workoutTitle,
                    onValueChange = { workoutViewModel.onWorkoutTitleChanged(it) },
                )
                Column(
                    modifier = Modifier.verticalScroll(listState)
                ) {
                    listOfExercises.forEachIndexed { exerciseIndex, _ ->
                        ExerciseItem(exerciseIndex = exerciseIndex)
                    }
                }

            }
        }
    )

}


