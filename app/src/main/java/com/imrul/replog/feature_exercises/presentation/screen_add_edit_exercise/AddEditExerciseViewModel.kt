package com.imrul.replog.feature_exercises.presentation.screen_add_edit_exercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.use_cases.WorkoutUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditExerciseViewModel @Inject constructor(
    private val workoutUseCases: WorkoutUseCases
) : ViewModel() {
    val muscleGroupList = Exercise.muscleGroups
    val weightTypeList = Exercise.weightTypes
    var selectedMuscleGroup by mutableStateOf(muscleGroupList[0])
        private set
    var selectedWeightType by mutableStateOf(weightTypeList[0])
        private set

    fun onSelectedMuscleGroup(string: String) {
        selectedMuscleGroup = string
    }

    fun onSelectedWeightType(string: String) {
        selectedWeightType = string
    }
}