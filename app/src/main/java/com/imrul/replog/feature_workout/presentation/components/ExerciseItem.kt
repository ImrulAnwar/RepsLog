package com.imrul.replog.feature_workout.presentation.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutViewModel
import com.imrul.replog.ui.theme.Maroon70

@Composable
fun ExerciseItem(
    exerciseIndex: Int? = null,
    workoutViewModel: WorkoutViewModel = hiltViewModel()
) {
    val listOfExercises = workoutViewModel.listOfExercises
    val listOfWeights = workoutViewModel.listOfWeights
    val listOfReps = workoutViewModel.listOfReps
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Exercise Name",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp),
            color = Maroon70
        )
        NoteTextField(
            text = "note",
            onValueChange = {},
            label = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp)
        )
        Text(
            text = "Add Set",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(10.dp)
                .clickable {
                    workoutViewModel.addSet("", "", exerciseIndex = exerciseIndex)
                    if (listOfExercises.isEmpty()) {
                        Log.d("TAG", "ExerciseItem: list is empty")
                    }
                },
            color = Maroon70
        )



        listOfWeights.forEachIndexed { setIndex, item ->
            if (item.first == exerciseIndex) {
                SetItem(exerciseIndex = exerciseIndex, setIndex = setIndex)
            }
        }
    }
}

