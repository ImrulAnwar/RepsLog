package com.imrul.replog.feature_auth.domain.data_source

import com.google.firebase.auth.FirebaseUser

interface AuthDataSource {
    suspend fun signInWithEmail(email: String, password: String): FirebaseUser?
    suspend fun signInAnonymously(): FirebaseUser?
    suspend fun register(username: String, email: String, password: String): FirebaseUser?
    suspend fun linkAccount(email: String, password: String)
    fun currentUser(): FirebaseUser?
    fun signOut()
}