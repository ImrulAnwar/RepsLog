package com.imrul.replog.feature_auth.domain.use_cases

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.imrul.replog.core.util.Resource
import com.imrul.replog.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInWithEmailUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<FirebaseUser?>> = flow {
        try {
            emit(Resource.Loading())
            if (email.isBlank() or password.isBlank()) {
                emit(Resource.Error(message = "Fields can not be empty"))
                return@flow
            }
            val user = repository.signInWithEmail(email, password)
            emit(Resource.Success(user))
        } catch (e: FirebaseAuthException) {
            emit(Resource.Error(message = e.message.toString()))
        } catch (e: FirebaseNetworkException) {
            emit(Resource.Error(message = e.message.toString()))
        }
    }
}