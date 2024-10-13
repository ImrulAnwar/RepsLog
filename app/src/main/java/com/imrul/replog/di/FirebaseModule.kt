package com.imrul.replog.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.imrul.replog.feature_auth.data.data_source.AuthDataSourceImpl
import com.imrul.replog.feature_auth.data.repository.AuthRepoImpl
import com.imrul.replog.feature_auth.domain.data_source.AuthDataSource
import com.imrul.replog.feature_auth.domain.repository.AuthRepository
import com.imrul.replog.feature_auth.domain.use_cases.ContinueAsGuestUseCase
import com.imrul.replog.feature_auth.domain.use_cases.AuthUseCases
import com.imrul.replog.feature_auth.domain.use_cases.CurrentUserUseCase
import com.imrul.replog.feature_auth.domain.use_cases.LinkAccountUseCase
import com.imrul.replog.feature_auth.domain.use_cases.OAuthUseCase
import com.imrul.replog.feature_auth.domain.use_cases.RegisterWithEmailUseCase
import com.imrul.replog.feature_auth.domain.use_cases.SignInWithEmailUseCase
import com.imrul.replog.feature_auth.domain.use_cases.SignOutUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    @Singleton
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    @Singleton
    fun provideAuthDataSource(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): AuthDataSource = AuthDataSourceImpl(auth, firestore)

    @Singleton
    @Provides
    fun provideAuthRepository(dataSource: AuthDataSource): AuthRepository = AuthRepoImpl(dataSource)

    @Singleton
    @Provides
    fun provideAuthUseCases(repository: AuthRepository) = AuthUseCases(
        continueAsGuest = ContinueAsGuestUseCase(repository),
        linkAccountUseCase = LinkAccountUseCase(repository),
        registerEmailUseCase = RegisterWithEmailUseCase(repository),
        signInWithEmailUseCase = SignInWithEmailUseCase(repository),
        signOutUseCase = SignOutUseCase(repository),
        currentUserUseCase = CurrentUserUseCase(repository),
        oAuthUseCase = OAuthUseCase(repository)
    )
}