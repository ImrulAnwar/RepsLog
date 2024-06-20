package com.imrul.replog.feature_auth.presentation.screen_login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.imrul.replog.core.Routes
import com.imrul.replog.core.util.Resource
import com.imrul.replog.feature_auth.domain.use_cases.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {
    var emailText by mutableStateOf("")
        private set
    var passwordText by mutableStateOf("")
        private set

    var isLoggedIn by mutableStateOf(false)
        private set

    fun onEmailChanged(value: String) {
        emailText = value
    }

    fun onPasswordChanged(value: String) {
        passwordText = value
    }

    init {
        currentUser()
    }

    fun currentUser() = viewModelScope.launch {
        authUseCases.currentUserUseCase().collect { result ->
            when (result) {
                is Resource.Success -> {
                    isLoggedIn = result.data != null
                }

                is Resource.Error -> {
                    isLoggedIn = false
                }

                is Resource.Loading -> {
                }
            }
        }
    }

    fun signInWithEmail(context: Context, navController: NavHostController) =
        viewModelScope.launch {
            authUseCases.signInWithEmailUseCase(emailText, passwordText).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        isLoggedIn = true
                        clearBackStackAndNavigate(navController, Routes.ScreenWorkoutHistory)
                    }

                    is Resource.Error -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT)
                            .show()
                        isLoggedIn = false
                    }

                    is Resource.Loading -> {

                    }
                }
            }
        }

    fun signOut(context: Context, navController: NavHostController) = viewModelScope.launch {
        authUseCases.signOutUseCase().collect { result ->
            when (result) {
                is Resource.Success -> {
                    clearBackStackAndNavigate(navController, Routes.ScreenLogin)
                    isLoggedIn = false
                }

                is Resource.Error -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                }
            }
        }
    }

    private fun clearBackStackAndNavigate(
        navController: NavHostController,
        newDestination: Routes
    ) {
        navController.navigate(newDestination) {
            popUpTo(0) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }
}