package com.imrul.replog.feature_workout.presentation.screen_workout_history.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.imrul.replog.feature_workout.domain.model.Workout

@Composable
fun WorkoutItem(workout: Workout) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(text = workout.name)


    }
}