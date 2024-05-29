package com.imrul.replog.feature_workout.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
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