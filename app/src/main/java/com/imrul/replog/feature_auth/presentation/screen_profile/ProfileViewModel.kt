package com.imrul.replog.feature_auth.presentation.screen_profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imrul.replog.core.util.Resource
import com.imrul.replog.feature_auth.domain.use_cases.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
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
    }

    fun clearData() {
        userName = "Anonymous"
        userEmail = "No Email"
        isAnonymous = false
    }

    fun initParam() = viewModelScope.launch {
        authUseCases.currentUserUseCase().collect { result ->
            when (result) {
                is Resource.Success -> {
                    userName = result.data?.displayName ?: "Anonymous"
                    userEmail = result.data?.email ?: "No Email"
                    isAnonymous = result.data?.isAnonymous ?: false
                    if (userName == "" || userEmail == "") {
                        userName = "Anonymous"
                        userEmail = "No Email"
                    }
                }

                is Resource.Error -> {
//                    isLoggedIn = false
                }

                is Resource.Loading -> {
                }
            }
        }
    }
}