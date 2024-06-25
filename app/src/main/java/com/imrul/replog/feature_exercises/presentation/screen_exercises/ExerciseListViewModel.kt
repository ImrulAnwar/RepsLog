package com.imrul.replog.feature_exercises.presentation.screen_exercises

import androidx.lifecycle.ViewModel
import com.imrul.replog.feature_workout.domain.use_cases.WorkoutUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val workoutUseCases: WorkoutUseCases
) : ViewModel() {
}