package com.imrul.replog.feature_exercises.presentation.screen_exercises

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.use_cases.WorkoutUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val workoutUseCases: WorkoutUseCases
) : ViewModel() {
    private val _exercisesListState = MutableStateFlow<List<Exercise>>(emptyList())
    val exercisesListState = _exercisesListState

    fun getAllExercises() {
        viewModelScope.launch {
            workoutUseCases.getAllExercises().collect {
                _exercisesListState.value = it
            }
        }
    }
}