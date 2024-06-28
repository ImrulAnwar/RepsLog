package com.imrul.replog.feature_exercises.presentation.screen_add_edit_exercise

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.domain.use_cases.WorkoutUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
    var exerciseName by mutableStateOf("")
        private set

    var currentExerciseId by mutableLongStateOf(-1L)
        private set

    fun onSelectedMuscleGroup(string: String) {
        selectedMuscleGroup = string
    }

    fun onSelectedWeightType(string: String) {
        selectedWeightType = string
    }

    fun onExerciseNameChanged(string: String) {
        exerciseName = string
    }

    fun insertExercise(context: Context, navController: NavHostController) {
        viewModelScope.launch {
            if (exerciseName == "") {
                Toast.makeText(context, "Exercise Name can not be empty.", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            }
            val exercise: Exercise
            if (currentExerciseId == -1L) {
                exercise = Exercise(
                    name = exerciseName,
                    targetMuscleGroup = selectedMuscleGroup,
                    weightType = selectedWeightType,
                    // might add imageUrl later
                )
            } else {
                exercise = Exercise(
                    exerciseId = currentExerciseId,
                    name = exerciseName,
                    targetMuscleGroup = selectedMuscleGroup,
                    weightType = selectedWeightType,
                    // might add imageUrl later
                )
            }
            try {
                workoutUseCases.insertExercise(exercise)
                navController.navigateUp()
            } catch (e: Exception) {
                Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun initializeParameters(
        exerciseId: Long,
    ) {
        viewModelScope.launch {
            val exercise = workoutUseCases.getExerciseById(exerciseId)
            currentExerciseId = exerciseId
            exerciseName = exercise.name
            selectedMuscleGroup = exercise.targetMuscleGroup.toString()
            selectedWeightType = exercise.weightType.toString()
        }
    }
}