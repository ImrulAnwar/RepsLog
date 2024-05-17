package com.imrul.replog.feature_workout.presentation.components

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
import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutViewModel

@Composable
fun ExerciseItem(
    workoutViewModel: WorkoutViewModel = hiltViewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Exercise Name", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        NoteTextField(
            text = "note",
            onValueChange = {},
            label = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp)
        )
        val listOfExercise: List<Exercise>
        for (i in 1..5) {
            SetItem()
        }
    }
}