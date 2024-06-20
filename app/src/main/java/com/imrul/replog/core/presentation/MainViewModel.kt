package com.imrul.replog.core.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.imrul.replog.feature_auth.domain.use_cases.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {
    val visiblePermissionsDialogueQueue = mutableStateListOf<String>()
    var selectedItemIndex by mutableIntStateOf(0)
        private set

    fun setSelectedItem(index: Int) {
        selectedItemIndex = index
    }

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