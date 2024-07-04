package com.imrul.replog.feature_workout.presentation.screen_workout.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.imrul.replog.core.Strings
import com.imrul.replog.feature_workout.presentation.components.CustomTextField
import com.imrul.replog.feature_workout.presentation.components.WorkoutTitleTextField
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutViewModel
import com.imrul.replog.ui.theme.Maroon70


@Composable
fun ExerciseItem(
    exerciseIndex: Int,
    workoutViewModel: WorkoutViewModel = hiltViewModel()
) {
    val listOfExercises = workoutViewModel.listOfExerciseName
    val listOfWeights = workoutViewModel.listOfWeights
    val listOfReps = workoutViewModel.listOfReps
    val listOfNotes = workoutViewModel.listOfNotes
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        WorkoutTitleTextField(
            text = listOfExercises[exerciseIndex],
            onValueChange = {
                workoutViewModel.onExerciseNameValueChanged(
                    exerciseIndex = exerciseIndex,
                    content = it
                )
            },
        )
        CustomTextField(
            text = listOfNotes[exerciseIndex],
            onValueChange = {
                workoutViewModel.onNoteValueChanged(
                    exerciseIndex = exerciseIndex,
                    content = it
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = Strings.SET,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Maroon70
            )
            Text(
                text = Strings.PREVIOUS,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Maroon70
            )
            Text(
                text = "KG",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Maroon70
            )
            Text(
                text = Strings.REPS,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Maroon70
            )
            Text(
                text = "\t\t\t",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Maroon70
            )
        }
        listOfWeights.forEachIndexed { setIndex, item ->
            if (item.first == exerciseIndex) {
                SetItem(exerciseIndex = exerciseIndex, setIndex = setIndex)
            }
        }
        Text(
            text = "Add Set",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(10.dp)
                .clickable {
                    workoutViewModel.addSet("", "", exerciseIndex = exerciseIndex)
                },
            color = Maroon70
        )
    }
}

