package com.imrul.replog.feature_workout.presentation.screen_workout

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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

    var listOfWeightRepsPair = mutableStateListOf<Pair<String?, String?>>()
        private set

    fun addWeightRepsPair(first: String, second: String) {
        listOfWeightRepsPair.add(Pair(first, second))
    }

    fun updateWeightRepsPair(index: Int, first: String? = null, second: String? = null) {
        if (index in listOfWeightRepsPair.indices) {
            listOfWeightRepsPair[index] = Pair(
                first ?: listOfWeightRepsPair[index].first,
                second ?: listOfWeightRepsPair[index].second
            )
        }
    }

    fun removeWeightRepsPair(index: Int) {
        if (index in listOfWeightRepsPair.indices) {
            listOfWeightRepsPair.removeAt(index)
        }
    }

    private val _exerciseList = MutableStateFlow<List<String>>(emptyList())

    // Expose the state as a StateFlow
    val exerciseList: StateFlow<List<String>> = _exerciseList.asStateFlow()

    private val _listOfSetState = MutableStateFlow<List<SetState>>(emptyList())


}