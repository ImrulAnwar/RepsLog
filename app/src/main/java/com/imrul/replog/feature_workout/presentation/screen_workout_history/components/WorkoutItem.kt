package com.imrul.replog.feature_workout.presentation.screen_workout_history.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.imrul.replog.core.Strings
import com.imrul.replog.feature_workout.domain.model.Workout
import com.imrul.replog.feature_workout.presentation.screen_workout_history.WorkoutHistoryViewModel
import com.imrul.replog.ui.theme.Maroon70

@Composable
fun WorkoutItem(
    workout: Workout,
    workoutHistoryViewModel: WorkoutHistoryViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        workoutHistoryViewModel.getAllExercise()
        workoutHistoryViewModel.getAllSets()
    }
    val exerciseListState by workoutHistoryViewModel.exercisesListState.collectAsState()
    val setsListState by workoutHistoryViewModel.setsListState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .border(
                width = 1.dp,
                // Add border with rounded corners here
                shape = MaterialTheme.shapes.medium,
                color = Maroon70,
            )
            .padding(12.dp)
    ) {
        Text(
            text = workout.name,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Maroon70
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = workout.weekdayString,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Maroon70
            )
            Text(
                text = workout.dateString,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Maroon70
            )
        }
        Text(
            text = workout.durationString,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Maroon70
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = Strings.EXERCISE,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Maroon70
            )
            Text(
                text = Strings.BEST_SET,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Maroon70
            )
        }

        // using column instead of lazy column is because i don't want nested scrolling
//        Column(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            exerciseListState.forEach { exercise ->
//                if (exercise.workoutIdForeign == workout.workoutId) {
//                    Text(
//                        text = "${exercise.setCount} x ${exercise.name}",
//                        fontSize = 16.sp,
//                        color = Maroon70
//                    )
//                }
//            }
//        }
    }
}