package com.imrul.replog.feature_auth.presentation.screen_profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imrul.replog.core.util.Resource
import com.imrul.replog.feature_auth.domain.use_cases.AuthUseCases
import com.imrul.replog.feature_workout.domain.use_cases.WorkoutUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val workoutUseCases: WorkoutUseCases
) : ViewModel() {
    var userName by mutableStateOf("Anonymous")
        private set
    var userEmail by mutableStateOf("No Email")
        private set
    var numberOfWorkouts by mutableIntStateOf(0)
        private set

    var isAnonymous by mutableStateOf(false)
        private set


    init {
        initParam()
        getWorkoutCount()
    }

    fun setUserNameAndEmail() = viewModelScope.launch {
        authUseCases.currentUserUseCase().collect { result ->
            when (result) {
                is Resource.Success -> {
                    userEmail = result.data?.email ?: "No Email"
                    userName = result.data?.displayName ?: "Anonymous"
                }

                is Resource.Error -> {
                }

                is Resource.Loading -> {
                }
            }
        }
    }

    private fun initParam() = viewModelScope.launch {
        authUseCases.currentUserUseCase().collect { result ->
            when (result) {
                is Resource.Success -> {
                    userEmail = result.data?.email ?: "No Email"
                    userName = result.data?.displayName ?: "Anonymous"
                    isAnonymous = result.data?.isAnonymous ?: false
                    if (userName == "" || userEmail == "") {
                        userName = "Anonymous"
                        userEmail = "No Email"
                    }
                }

                is Resource.Error -> {
                }

                is Resource.Loading -> {
                }
            }
        }
    }

    fun getWorkoutCount() {
        viewModelScope.launch {
            workoutUseCases.getAllWorkouts().collect {
                numberOfWorkouts = it.size
            }
        }
    }
}