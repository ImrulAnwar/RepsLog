package com.imrul.replog.feature_auth.domain.use_cases

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.imrul.replog.core.util.Resource
import com.imrul.replog.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterWithEmailUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(
        username: String,
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser?>> = flow {
        try {
            emit(Resource.Loading())
            val user = repository.register(username, email, password)
            // Set Display Name
            val profileChangeRequest = UserProfileChangeRequest.Builder().let {
                it.displayName = username
                it.build()
            }
            user?.updateProfile(profileChangeRequest)
            emit(Resource.Success(user))
        } catch (e: FirebaseAuthException) {
            emit(Resource.Error(message = e.message.toString()))
        } catch (e: FirebaseNetworkException) {
            emit(Resource.Error(message = e.message.toString()))
        }
    }
}