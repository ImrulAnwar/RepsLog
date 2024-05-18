package com.imrul.replog.feature_workout.presentation.screen_workout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.imrul.replog.feature_workout.domain.use_cases.WorkoutUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetViewModel @Inject constructor(
    private val wokoutUseCases: WorkoutUseCases
) : ViewModel(){
    var weightValue by mutableStateOf("")
        private set

    var repsValue by mutableStateOf("")
        private set

    fun onWeightValueChanged(value: String) {
        weightValue = value
    }

    fun onRepsValueChanged(value: String) {
        repsValue = value
    }
}