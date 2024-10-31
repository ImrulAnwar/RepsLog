package com.imrul.replog.feature_auth.presentation.screen_link_account

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.imrul.replog.core.util.Resource
import com.imrul.replog.feature_auth.domain.use_cases.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinkAccountViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
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

    fun linkAccount(context: Context, navController: NavHostController) =
        viewModelScope.launch {
//            authUseCases.(
//                email = emailText,
//                username = usernameText,
//                password = passwordText
//            )
//                .collect { result ->
//                    when (result) {
//                        is Resource.Success -> {
//                            // show toast
//                            Toast.makeText(context, "Successfully Registered", Toast.LENGTH_SHORT)
//                                .show()
//                            navController.navigateUp()
//                        }
//
//                        is Resource.Error -> {
//                            Toast.makeText(
//                                context,
//                                result.message,
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            // show toast
//                        }
//
//                        is Resource.Loading -> {
//                            // show progressbar
//                        }
//                    }
//                }
        }

}