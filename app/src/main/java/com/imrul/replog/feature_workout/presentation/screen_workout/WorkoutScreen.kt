package com.imrul.replog.feature_workout.presentation.screen_workout

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.imrul.replog.core.Strings
import com.imrul.replog.core.presentation.components.CustomIcon
import com.imrul.replog.core.presentation.components.TitleTextField
import com.imrul.replog.feature_workout.presentation.screen_workout.components.ExerciseItem
import com.imrul.replog.ui.theme.Maroon10
import com.imrul.replog.ui.theme.Maroon20
import com.imrul.replog.ui.theme.Maroon70

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkoutScreen(
    navController: NavHostController,
    workoutViewModel: WorkoutViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val workoutTitle = workoutViewModel.workoutTitle
    val listState = rememberScrollState()

    val listOfExercises = workoutViewModel.listOfExercises
    Log.d("ViewModelSize", "WorkoutScreen: ${workoutViewModel.listOfExercises.size}")

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { workoutViewModel.addExercise() },
                shape = RoundedCornerShape(percent = 50),
                contentColor = Maroon70,
                containerColor = Maroon20
            ) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Filled.Add),
                    contentDescription = Strings.ADD_BUTTON,
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(
                        color = Maroon10
                    ),
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
                        painter = rememberVectorPainter(image = Icons.Filled.Done),
                        contentDescription = Strings.FINISHED_WORKOUT_BUTTON,
                        onClick = {
                            if (!workoutViewModel.shouldInsertWorkout()) {
                                Toast.makeText(
                                    context,
                                    Strings.COMPLETE_EXERCISE,
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                workoutViewModel.insertWorkout()
                                navController.popBackStack()
                                // Stop Service
                                Intent(context, WorkoutService::class.java).also {
                                    it.action = WorkoutService.Actions.STOP.toString()
                                    context.startForegroundService(it)
                                }
                            }
                        }
                    )
                }
                // This is the body

                TitleTextField(
                    text = workoutTitle,
                    onValueChange = { workoutViewModel.onWorkoutTitleChanged(it) },
                )
                Column(
                    modifier = Modifier.verticalScroll(listState),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    listOfExercises.forEachIndexed { exerciseIndex, _ ->
                        ExerciseItem(exerciseIndex = exerciseIndex)
                    }
                    Text(
                        text = Strings.CANCEL_WORKOUT,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                navController.popBackStack()
                                // stop service
                                Intent(context, WorkoutService::class.java).also {
                                    it.action = WorkoutService.Actions.STOP.toString()
                                    context.startForegroundService(it)
                                }
                            },
                        color = Maroon70
                    )
                }

            }
        }
    )

}


