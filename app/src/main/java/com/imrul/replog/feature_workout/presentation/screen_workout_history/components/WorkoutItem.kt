package com.imrul.replog.feature_workout.presentation.screen_workout_history.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.model.Set
import com.imrul.replog.feature_workout.domain.model.Workout
import com.imrul.replog.feature_workout.presentation.screen_workout_history.WorkoutHistoryViewModel

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
            .padding(10.dp)
    ) {
        Text(text = workout.name, fontWeight = FontWeight.Bold)

        // using column instead of lazy column is because i don't want nested scrolling
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            exerciseListState.forEach { exercise ->
                if (exercise.workoutIdForeign == workout.workoutId) {
                    var setCount = 0
                    setsListState.forEach { set ->
                        if (set.exerciseIdForeign == exercise.exerciseId) setCount++
                    }
                    Log.d("Problem check", "WorkoutItem: ${exercise.name}")
                    Text(text = "$setCount x ${exercise.name}")
                }
            }
        }

    }
}