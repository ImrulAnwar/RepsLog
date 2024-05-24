package com.imrul.replog.feature_workout.presentation.screen_workout_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.model.Set
import com.imrul.replog.feature_workout.domain.model.Workout
import com.imrul.replog.feature_workout.domain.use_cases.WorkoutUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutHistoryViewModel @Inject constructor(
    private val workoutUseCases: WorkoutUseCases
) : ViewModel() {
    private val _workoutListState = MutableStateFlow<List<Workout>>(emptyList())
    val workoutListState = _workoutListState
    private val _exercisesListState = MutableStateFlow<List<Exercise>>(emptyList())
    val exercisesListState = _exercisesListState
    private val _setsListState = MutableStateFlow<List<Set>>(emptyList())
    val setsListState = _setsListState

    fun getAllWorkouts() {
        viewModelScope.launch {
            workoutUseCases.getAllWorkouts().collect {
                _workoutListState.value = it
            }
        }
    }

    fun getAllExercise() {
        viewModelScope.launch {
            workoutUseCases.getAllExercises().collect {
                _exercisesListState.value = it
            }
        }
    }

    fun getAllSets() {
        viewModelScope.launch {
            workoutUseCases.getAllSets().collect {
                _setsListState.value = it
            }
        }
    }

    fun getSetCount(exerciseId: Long) {
        viewModelScope.launch {
            workoutUseCases.getAllSets().collect {
                it.map { set ->
                    set.exerciseIdForeign == exerciseId
                }.count()
            }
        }
    }
}