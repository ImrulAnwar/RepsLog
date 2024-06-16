package com.imrul.replog.feature_auth.domain.use_cases

data class AuthUseCases(
    val anonymousRegisterUseCase: AnonymousRegisterUseCase,
    val linkAccountUseCase: LinkAccountUseCase,
    val registerEmailUseCase: RegisterEmailUseCase,
    val signInWithEmailUseCase: SignInWithEmailUseCase,
    val signOutUseCase: SignOutUseCase
)