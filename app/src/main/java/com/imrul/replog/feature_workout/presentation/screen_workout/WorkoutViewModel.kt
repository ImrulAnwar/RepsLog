package com.imrul.replog.feature_workout.presentation.screen_workout

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.model.SetState
import com.imrul.replog.feature_workout.domain.use_cases.WorkoutUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
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

    private val _exerciseList = MutableStateFlow<List<String>>(emptyList())

    // Expose the state as a StateFlow
    val exerciseList: StateFlow<List<String>> = _exerciseList.asStateFlow()

    private val _listOfSetState = MutableStateFlow<List<SetState>>(emptyList())

    // Expose the state as a StateFlow
    val listOfSetState: StateFlow<List<SetState>> = _listOfSetState.asStateFlow()

    // Method to add an exercise to the list
    fun addExercise(exercise: String) {
        _exerciseList.value += exercise
    }

    fun addSet() {
        val setState = SetState()
        _listOfSetState.value += setState
    }

    fun onWeightValueChanged(value: String, index: Int = 0) {

        _listOfSetState.value[index].weightValue = value.toFloat()

    }

}