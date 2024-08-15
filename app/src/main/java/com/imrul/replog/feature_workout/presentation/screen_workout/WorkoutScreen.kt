package com.imrul.replog.feature_workout.presentation.screen_workout

import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.imrul.replog.core.Routes
import com.imrul.replog.core.Strings
import com.imrul.replog.core.presentation.components.CustomIcon
import com.imrul.replog.feature_workout.presentation.components.AddNoteTextField
import com.imrul.replog.feature_workout.presentation.components.CustomTextField
import com.imrul.replog.feature_workout.presentation.components.WorkoutTitleTextField
import com.imrul.replog.feature_workout.presentation.screen_workout.components.DropDownMenuForWorkoutName
import com.imrul.replog.feature_workout.presentation.screen_workout.components.ExerciseItem
import com.imrul.replog.ui.theme.Maroon10
import com.imrul.replog.ui.theme.WhiteCustom
import com.imrul.replog.ui.theme.Maroon70
import com.imrul.replog.ui.theme.Maroon90

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkoutScreen(
    navController: NavHostController,
    workoutViewModel: WorkoutViewModel,
    context: Context = LocalContext.current
) {
    val elapsedTime = workoutViewModel.elapsedTime
    val workoutTitle = workoutViewModel.workoutTitle
    val listState = rememberScrollState()

    val listOfExercises = workoutViewModel.listOfExerciseName

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Routes.ScreenExerciseListFromWorkout) },
                shape = RoundedCornerShape(percent = 50),
                contentColor = Maroon70,
                containerColor = Maroon10
            ) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Filled.Add),
                    contentDescription = Strings.ADD_BUTTON,
                )
            }
        },
        content = { paddingValues ->
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
                        .padding(paddingValues)
                        .background(
                            color = WhiteCustom
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
                                navController.navigate(Routes.ScreenWorkoutHistory) { // Navigate to the destination
                                    navController.navigateUp()
                                }
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
                                    navController.navigate(Routes.ScreenWorkoutHistory) { // Navigate to the destination
                                        // When navigation is complete, stop the service and pop the back stack
                                        Intent(context, WorkoutService::class.java).also {
                                            it.action = WorkoutService.Actions.STOP.toString()
                                            context.startForegroundService(it)
                                        }
                                        navController.navigateUp()
                                    }
                                    workoutViewModel.insertWorkout()
                                }
                            }
                        )
                    }
                    // This is the body


                    Text(
                        elapsedTime,
                        color = Maroon70
                    )
                    Column(
                        modifier = Modifier.verticalScroll(listState),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            WorkoutTitleTextField(
                                text = workoutTitle,
                                onValueChange = { workoutViewModel.onWorkoutTitleChanged(it) },
                            )
                            DropDownMenuForWorkoutName(
                                addWorkoutNoteClicked = {
                                    workoutViewModel.addWorkoutNote()
                                }
                            )
                        }
                        if (workoutViewModel.listOfWorkoutNotes.isNotEmpty())
                            workoutViewModel.listOfWorkoutNotes.forEachIndexed { index, item ->
                                AddNoteTextField(
                                    text = item,
                                    onValueChange = {
                                        workoutViewModel.onWorkoutNoteValueChanged(
                                            index = index,
                                            content = it
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(15.dp, 0.dp),
                                    onSwiped = { workoutViewModel.removeWorkoutNote(index) }
                                )
                            }
                        listOfExercises.forEachIndexed { exerciseIndex, _ ->
                            ExerciseItem(
                                exerciseIndex = exerciseIndex,
                                workoutViewModel = workoutViewModel
                            )
                        }
                        Text(
                            text = Strings.CANCEL_WORKOUT,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable {
                                    navController.navigate(Routes.ScreenWorkoutHistory) { // Navigate to the destination
                                        // When navigation is complete, stop the service and pop the back stack
                                        Intent(context, WorkoutService::class.java).also {
                                            it.action = WorkoutService.Actions.STOP.toString()
                                            context.startForegroundService(it)
                                        }
                                        workoutViewModel.clearAllData()
                                        navController.popBackStack()
                                    }
                                },
                            color = Maroon90
                        )
                    }

                }
        }
    )
}


