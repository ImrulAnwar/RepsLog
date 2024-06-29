package com.imrul.replog.feature_exercises.presentation.screen_exercises

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.use_cases.WorkoutUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val workoutUseCases: WorkoutUseCases
) : ViewModel() {
    private val _exercisesListState = MutableStateFlow<List<Exercise>>(emptyList())
    val exercisesListState = _exercisesListState

    var searchText by mutableStateOf("")
        private set

    fun onSearchTextChanged(value: String) {
        searchText = value
    }

    fun getAllExercises() {
        viewModelScope.launch {
            workoutUseCases.getAllExercises().collect {
                _exercisesListState.value = it
            }
        }
    }

    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch {
            workoutUseCases.deleteExercise(exercise)
        }
    }
}