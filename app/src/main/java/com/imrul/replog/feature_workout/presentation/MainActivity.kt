package com.imrul.replog.feature_workout.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.imrul.replog.core.presentation.navigation.NavGraph
import com.imrul.replog.feature_workout.presentation.components.PermissionDialog
import com.imrul.replog.feature_workout.presentation.components.PostNotificationTextProvider
import com.imrul.replog.ui.theme.RepLogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val permissionsToRequest = arrayOf(
        Manifest.permission.POST_NOTIFICATIONS
    )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RepLogTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)

                val dialogQueue = viewModel.visiblePermissionsDialogueQueue
                val multiplePermissionsLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { perms ->
                        permissionsToRequest.forEach { permission ->
                            viewModel.onPermissionResult(
                                permission = permission,
                                isGranted = perms[permission] == true
                            )
                        }
                    }
                )
                LaunchedEffect(Unit) {
                    multiplePermissionsLauncher.launch(permissionsToRequest)
                }

                dialogQueue.reversed().forEach { permission ->
                    PermissionDialog(
                        permissionTextProvider = when (permission) {
                            Manifest.permission.POST_NOTIFICATIONS -> PostNotificationTextProvider()
                            else -> return@forEach
                        },
                        isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                            permission
                        ),
                        onDismiss = viewModel::dismissDialog,
                        onOkClick = {
                            viewModel.dismissDialog()
                            multiplePermissionsLauncher.launch(
                                arrayOf(permission)
                            )
                        },
                        onGotoAppSettingsClick = ::openAppSettings
                    )
                }

            }
        }
    }
}


fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}
