package com.imrul.replog.feature_auth.presentation.screen_login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.imrul.replog.feature_auth.domain.use_cases.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {
    var emailText by mutableStateOf("")
        private set
    var passwordText by mutableStateOf("")
        private set

    fun onEmailChanged(value: String) {
        emailText = value
    }

    fun onPasswordChanged(value: String) {
        passwordText = value
    }

}