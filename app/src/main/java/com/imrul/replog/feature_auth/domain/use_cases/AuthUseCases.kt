package com.imrul.replog.feature_auth.domain.use_cases

data class AuthUseCases(
    val continueAsGuest: ContinueAsGuestUseCase,
    val linkAccountUseCase: LinkAccountUseCase,
    val registerEmailUseCase: RegisterWithEmailUseCase,
    val signInWithEmailUseCase: SignInWithEmailUseCase,
    val signOutUseCase: SignOutUseCase,
    val currentUserUseCase: CurrentUserUseCase
)