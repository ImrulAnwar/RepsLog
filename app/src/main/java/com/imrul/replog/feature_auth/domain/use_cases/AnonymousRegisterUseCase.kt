package com.imrul.replog.feature_auth.domain.use_cases

import com.imrul.replog.feature_auth.domain.repository.AuthRepository
import javax.inject.Inject

class AnonymousRegisterUseCase @Inject constructor(
    private val repository: AuthRepository
){
}