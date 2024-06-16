package com.imrul.replog.feature_auth.presentation.screen_register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    //authUseCases
) : ViewModel() {


    var usernameText by mutableStateOf("")
        private set

    var emailText by mutableStateOf("")
        private set

    var passwordText by mutableStateOf("")
        private set
    var confirmPasswordText by mutableStateOf("")
        private set

    fun onEmailChanged(value: String) {
        emailText = value
    }

    fun onPasswordChanged(value: String) {
        passwordText = value
    }

    fun onConfirmPasswordChanged(value: String) {
        confirmPasswordText = value
    }

    fun onUsernameChanged(value: String) {
        usernameText = value
    }

}