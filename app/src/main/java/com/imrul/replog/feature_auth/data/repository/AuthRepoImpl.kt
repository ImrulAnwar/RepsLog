package com.imrul.replog.feature_auth.data.repository

import com.google.firebase.auth.FirebaseUser
import com.imrul.replog.feature_auth.domain.data_source.AuthDataSource
import com.imrul.replog.feature_auth.domain.repository.AuthRepository

class AuthRepoImpl(
    private val dataSource: AuthDataSource
) : AuthRepository {
    override suspend fun signInWithEmail(email: String, password: String): FirebaseUser? =
        dataSource.signInWithEmail(email, password)

    override suspend fun continueAsGuest(): FirebaseUser? = dataSource.continueAsGuest()
    override suspend fun linkAccount(email: String, password: String) =
        dataSource.linkAccount(email, password)

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): FirebaseUser? = dataSource.register(username, email, password)

    override fun currentUser(): FirebaseUser? = dataSource.currentUser()

    override fun signOut() = dataSource.signOut()

}