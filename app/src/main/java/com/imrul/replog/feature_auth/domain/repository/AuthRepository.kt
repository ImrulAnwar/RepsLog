package com.imrul.replog.feature_auth.domain.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun signInWithEmail(email: String, password: String): FirebaseUser?
    suspend fun continueAsGuest(): FirebaseUser?
    suspend fun linkAccount(email: String, password: String)
    suspend fun register(username: String, email: String, password: String): FirebaseUser?
    suspend fun signInWithGoogle(idToken: String): FirebaseUser?
    fun currentUser(): FirebaseUser?
    fun signOut()
}