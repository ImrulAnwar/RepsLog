package com.imrul.replog.feature_auth.domain.use_cases

data class AuthUseCases(
    val anonymousRegisterUseCase: AnonymousRegisterUseCase,
    val linkAccountUseCase: LinkAccountUseCase,
    val registerEmailUseCase: RegisterWithEmailUseCase,
    val signInWithEmailUseCase: SignInWithEmailUseCase,
    val signOutUseCase: SignOutUseCase,
    val currentUserUseCase: CurrentUserUseCase
)