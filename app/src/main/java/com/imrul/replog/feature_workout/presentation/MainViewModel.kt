package com.imrul.replog.feature_workout.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imrul.replog.feature_auth.domain.use_cases.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {
    val visiblePermissionsDialogueQueue = mutableStateListOf<String>()
    fun dismissDialog() {
        visiblePermissionsDialogueQueue.removeFirst()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted && !visiblePermissionsDialogueQueue.contains(permission)) {
            visiblePermissionsDialogueQueue.add(permission)
        }
    }
}