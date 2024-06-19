package com.imrul.replog.feature_auth.presentation.screen_login.model

import com.google.firebase.auth.FirebaseUser

data class LoginState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: String? = null
)