package com.imrul.replog.feature_workout.presentation.screen_workout_history

import androidx.lifecycle.ViewModel
import com.imrul.replog.feature_workout.domain.model.Workout
import com.imrul.replog.feature_workout.domain.use_cases.WorkoutUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class WorkoutHistoryViewModel @Inject constructor(
    private val workoutUseCases: WorkoutUseCases
) : ViewModel() {
    private val _workoutListState = MutableStateFlow<List<Workout>>(emptyList())
    val workoutListState = _workoutListState
}