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
    private val _exercisesList = MutableStateFlow<List<Exercise>>(emptyList())
    val exercisesList = _exercisesList
    private val _originalExerciseList = MutableStateFlow<List<Exercise>>(emptyList())
    private val _weightTypeFilterList = MutableStateFlow(emptyList<String>())
    val weightTypeFilterList = _weightTypeFilterList
    private val _targetMuscleFilterList = MutableStateFlow(emptyList<String>())
    val targetMuscleFilterList = _targetMuscleFilterList

    var searchText by mutableStateOf("")
        private set
    var isSearching by mutableStateOf(false)
        private set


    fun onSearchTextChanged(value: String) {
        searchText = value
    }

    fun getAllExercises() {
        viewModelScope.launch {
            workoutUseCases.getAllExercises().collect {
                _exercisesList.value = it
                _originalExerciseList.value = it
            }
        }
    }

    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch {
            workoutUseCases.deleteExercise(exercise)
        }
    }

    fun updateExerciseList() {
        _exercisesList.value = _originalExerciseList.value.filter { exercise ->
            exercise.doesMatchSearchQuery(searchText) &&
                    (_weightTypeFilterList.value.isEmpty() || exercise.weightType in _weightTypeFilterList.value) &&
                    (_targetMuscleFilterList.value.isEmpty() || exercise.targetMuscleGroup in _targetMuscleFilterList.value)
        }
    }

    fun toggleWeightTypeOnFilter(value: String) {
        if (value in _weightTypeFilterList.value)
            removeWeightTypeOnFilter(value)
        else
            addWeightTypeOnFilter(value)
    }

    fun addWeightTypeOnFilter(value: String) {
        _weightTypeFilterList.value += value
    }

    fun removeWeightTypeOnFilter(value: String) {
        _weightTypeFilterList.value = _weightTypeFilterList.value.filter { it != value }
    }

    fun addTargetMuscleFilter(value: String) {
        _targetMuscleFilterList.value += value
    }

    fun removeTargetMuscleFilter(value: String) {
        _targetMuscleFilterList.value = _targetMuscleFilterList.value.filter { it != value }
    }


    fun toggleTargetMuscleFilter(value: String) {

        if (value in _targetMuscleFilterList.value)
            removeTargetMuscleFilter(value)
        else
            addTargetMuscleFilter(value)
    }

    fun toggleIsSearching() {
        isSearching = !isSearching
    }
}