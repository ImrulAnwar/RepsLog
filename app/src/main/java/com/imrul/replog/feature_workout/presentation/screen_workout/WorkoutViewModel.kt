package com.imrul.replog.feature_workout.presentation.screen_workout

import androidx.compose.runtime.MovableContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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

    var listOfWeights = mutableStateListOf<Pair<Int, String>>()
        private set
    var listOfReps = mutableStateListOf<Pair<Int, String>>()
        private set

    //    var listOfWeight = mutableStateListOf<Pair<String, String>>()
    var listOfExercises = mutableStateListOf<String>()
        private set

    fun addSet(first: String, second: String, exerciseIndex: Int? = null) {
        exerciseIndex?.let {
            listOfWeights.add(Pair(it, ""))
            listOfReps.add(Pair(it, ""))
        }
    }

    fun updateWeight(
        setIndex: Int,
        content: String,
    ) {
        listOfWeights[setIndex] = Pair(
            first = listOfWeights[setIndex].first,
            second = content
        )
    }

    fun updateRep(
        setIndex: Int,
        content: String,
    ) {
        listOfReps[setIndex] = Pair(
            first = listOfReps[setIndex].first,
            second = content
        )
    }

    fun addExercise() {
        listOfExercises.add("Exercise Name")
    }


}