package com.imrul.replog.feature_auth.data.data_source

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.imrul.replog.core.Constants.USERS_COLLECTION
import com.imrul.replog.feature_auth.domain.data_source.AuthDataSource
import kotlinx.coroutines.tasks.await

class AuthDataSourceImpl(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : AuthDataSource {
    override suspend fun signInWithEmail(email: String, password: String): FirebaseUser? =
        auth
            .signInWithEmailAndPassword(email, password)
            .await()
            .user

    override suspend fun continueAsGuest(): FirebaseUser? =
        auth.signInAnonymously().await().user

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): FirebaseUser? = auth
        .createUserWithEmailAndPassword(email, password)
        .await()
        .user.apply {
            createUserForFirestore(
                username = username,
                uid = this?.uid,
                email = email
            )
        }

    override suspend fun linkAccount(
        username: String,
        email: String,
        password: String
    ): FirebaseUser? {
        val credential = EmailAuthProvider.getCredential(email, password)
        return currentUser()?.linkWithCredential(credential)?.await()?.user.apply {
            createUserForFirestore(
                username = username,
                uid = this?.uid,
                email = email
            )
        }
    }

    override suspend fun signInWithGoogle(idToken: String): FirebaseUser? {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)

        val user = auth.signInWithCredential(firebaseCredential).await().user
        user?.let {
            // Retrieve the user information from FirebaseUser
            val uid = it.uid
            val email = it.email
            val username = it.displayName ?: "Username" // Placeholder if displayName is null

            // Check if user exists in Firestore
            val userDoc = fireStore.collection("users").document(uid).get().await()
            if (!userDoc.exists()) {
                // User is new, create a Firestore document
                createUserForFirestore(
                    username = username,
                    uid = uid,
                    email = email
                )
            }
        }
        return user
    }


    private suspend fun createUserForFirestore(username: String, uid: String?, email: String?) {
        uid?.let {
            val data = hashMapOf(
                "uid" to uid,
                "displayName" to username,
                "email" to email
            )
            fireStore
                .collection(USERS_COLLECTION)
                .document(uid)
                .set(data)
                .await()
        }
    }

    override fun currentUser(): FirebaseUser? = auth.currentUser

    override fun signOut() = auth.signOut()
}