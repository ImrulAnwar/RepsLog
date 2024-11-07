package com.imrul.replog.feature_auth.presentation.screen_login

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.imrul.replog.core.Routes
import com.imrul.replog.core.util.Resource
import com.imrul.replog.feature_auth.domain.use_cases.AuthUseCases
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID
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

    var isSigningOut by mutableStateOf(false)
        private set
    var isSigningIn by mutableStateOf(false)
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

    private fun currentUser() = viewModelScope.launch {
        authUseCases.currentUserUseCase().collect { result ->
            when (result) {
                is Resource.Success -> {
                    isLoggedIn = (result.data != null)
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
            isSigningIn = true
            authUseCases.signInWithEmailUseCase(emailText, passwordText).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        isLoggedIn = true
                        clearBackStackAndNavigate(navController, Routes.ScreenWorkoutHistory)
                        isSigningIn = false
                    }

                    is Resource.Error -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT)
                            .show()
                        isLoggedIn = false
                        isSigningIn = false
                    }

                    is Resource.Loading -> {

                    }
                }
            }
        }

    fun continueAsGuest(context: Context, navController: NavHostController) =
        viewModelScope.launch {
            isSigningIn = true
            authUseCases.continueAsGuest().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        isLoggedIn = true
                        clearBackStackAndNavigate(navController, Routes.ScreenWorkoutHistory)
                        isSigningIn = false
                    }

                    is Resource.Error -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT)
                            .show()
                        isLoggedIn = false
                        isSigningIn = false
                    }

                    is Resource.Loading -> {

                    }
                }
            }
        }

    fun signOut(context: Context, navController: NavHostController) = viewModelScope.launch {
        isSigningOut = true
        authUseCases.signOutUseCase().collect { result ->
            when (result) {
                is Resource.Success -> {
                    Intent(context, WorkoutService::class.java).also {
                        it.action = WorkoutService.Actions.STOP.toString()
                        context.startForegroundService(it)
                    }
                    clearBackStackAndNavigate(navController, Routes.ScreenLogin)
                    isLoggedIn = false
                    isSigningOut = false
                }

                is Resource.Error -> {
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                    isSigningOut = false
                }

                is Resource.Loading -> {
                }
            }
        }
    }

    fun continueWithGoogle(context: Context, navController: NavHostController) {
        val credentialManager = CredentialManager.create(context)
        //generating a nonce
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(CRED.WEB_CLIENT_ID)
            .setAutoSelectEnabled(false)
            .setNonce(hashedNonce)
            .build()

        val request: GetCredentialRequest =
            GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build()
        viewModelScope.launch {
            isSigningIn = true
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context
                )
                Toast.makeText(context, "successful", Toast.LENGTH_SHORT).show()
                val credential = result.credential
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken
                //passing the id token to signInWithCredential
                authUseCases.oAuthUseCase(googleIdToken).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            isLoggedIn = true
                            clearBackStackAndNavigate(navController, Routes.ScreenWorkoutHistory)
                            isSigningIn = false
                        }

                        is Resource.Error -> {
                            Toast.makeText(context, result.message, Toast.LENGTH_SHORT)
                                .show()
                            isLoggedIn = false
                            isSigningIn = false
                        }

                        is Resource.Loading -> {

                        }
                    }
                }
            } catch (e: Exception) {
//                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                isSigningIn = false
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