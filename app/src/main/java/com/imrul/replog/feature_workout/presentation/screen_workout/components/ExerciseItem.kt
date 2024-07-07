package com.imrul.replog.feature_workout.presentation.screen_workout.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.imrul.replog.core.Routes
import com.imrul.replog.core.Strings
import com.imrul.replog.feature_exercises.presentation.components.DropDownMenuForExerciseItem
import com.imrul.replog.feature_workout.presentation.components.CustomTextField
import com.imrul.replog.feature_workout.presentation.components.DropDownMenuForExerciseName
import com.imrul.replog.feature_workout.presentation.components.WorkoutTitleTextField
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutViewModel
import com.imrul.replog.ui.theme.Maroon70


@Composable
fun ExerciseItem(
    exerciseIndex: Int,
    workoutViewModel: WorkoutViewModel
) {
    val listOfExercises = workoutViewModel.listOfExerciseName
    val listOfWeights = workoutViewModel.listOfWeights
    val listOfReps = workoutViewModel.listOfReps
    val listOfNotes = workoutViewModel.listOfNotes
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = listOfExercises[exerciseIndex],
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Maroon70,
                modifier = Modifier.padding(start = 5.dp)
            )
            DropDownMenuForExerciseName(
                onEditClicked = {

                },
                onDeleteClicked = { })
        }

        CustomTextField(
            text = if (listOfNotes.isNotEmpty()) listOfNotes[exerciseIndex] else "",
            onValueChange = {
                workoutViewModel.onNoteValueChanged(
                    exerciseIndex = exerciseIndex,
                    content = it
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 0.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "\t ${Strings.SET}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Maroon70,
                modifier = Modifier.width(60.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "\t ${Strings.PREVIOUS}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Maroon70,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "\tKG",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Maroon70,
                modifier = Modifier.width(80.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "\t ${Strings.REPS}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Maroon70,
                modifier = Modifier.width(80.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Maroon70,
                modifier = Modifier.width(44.dp)
            )
        }
        listOfWeights.forEachIndexed { setIndex, item ->
            if (item.first == exerciseIndex) {
                SetItem(
                    exerciseIndex = exerciseIndex,
                    setIndex = setIndex,
                    workoutViewModel = workoutViewModel
                )
            }
        }
        Text(
            text = "Add Set",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(10.dp)
                .clickable {
                    workoutViewModel.addSet(exerciseIndex = exerciseIndex)
                },
            color = Maroon70
        )
    }
}

