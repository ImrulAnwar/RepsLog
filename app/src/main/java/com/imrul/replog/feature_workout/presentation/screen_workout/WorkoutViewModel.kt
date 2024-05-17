package com.imrul.replog.feature_workout.presentation.screen_workout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.imrul.replog.feature_workout.domain.use_cases.WorkoutUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val workoutUseCases: WorkoutUseCases
) : ViewModel() {
    var workoutTitle by mutableStateOf("Workout Title")
        private set

    fun onWorkoutTitleChanged(value: String) {
        workoutTitle = value
    }

}