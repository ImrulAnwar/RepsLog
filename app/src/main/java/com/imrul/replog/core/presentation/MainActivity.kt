package com.imrul.replog.core.presentation

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
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.imrul.replog.core.presentation.navigation.NavGraph
import com.imrul.replog.core.util.BottomBarScreens
import com.imrul.replog.feature_auth.presentation.screen_login.LoginViewModel
import com.imrul.replog.feature_workout.presentation.components.PermissionDialog
import com.imrul.replog.feature_workout.presentation.components.PostNotificationTextProvider
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutService
import com.imrul.replog.ui.theme.Maroon10
import com.imrul.replog.ui.theme.Maroon20
import com.imrul.replog.ui.theme.Maroon70
import com.imrul.replog.ui.theme.RepLogTheme
import com.imrul.replog.ui.theme.WhiteCustom
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val permissionsToRequest = arrayOf(
        Manifest.permission.POST_NOTIFICATIONS
    )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RepLogTheme {

                val window = (LocalView.current.context as Activity).window
                window.statusBarColor =
                    if (!isSystemInDarkTheme()) Maroon70.toArgb() else Maroon20.toArgb()

                val navController = rememberNavController()
                val isLoggedIn = loginViewModel.isLoggedIn
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

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val selectedItemIndex = viewModel.selectedItemIndex
                val screens = listOf(
                    BottomBarScreens.WorkoutHistoryScreenObject,
                    BottomBarScreens.MeasurementsScreenObject,
                    BottomBarScreens.RoutineScreenObject,
                    BottomBarScreens.ExercisesScreenObject,
                    BottomBarScreens.ProfileScreenObject
                )

                val shouldShowBottomNavigation =
                    when (navBackStackEntry?.destination?.route?.substringAfterLast('.')) {
                        screens[0].route::class.java.simpleName,
                        screens[1].route::class.java.simpleName,
                        screens[2].route::class.java.simpleName,
                        screens[3].route::class.java.simpleName,
                        screens[4].route::class.java.simpleName,
                        -> true

                        else -> false
                    }

                LaunchedEffect(Unit) {
                    multiplePermissionsLauncher.launch(permissionsToRequest)
                }

                LaunchedEffect(selectedItemIndex) {
                    navController.popBackStack()
                    navController.navigate(screens[selectedItemIndex].route)
                }

                LaunchedEffect(navBackStackEntry) {
                    // because of a bug that was pre selecting the profile icon
                    if (navBackStackEntry?.destination?.route?.substringAfterLast('.') ==
                        screens[0].route::class.java.simpleName
                    ) {
                        viewModel.setSelectedItem(0)
                    }
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

                Scaffold(
                    bottomBar = {
                        if (shouldShowBottomNavigation)
                            NavigationBar(
                                containerColor = WhiteCustom,
                                modifier = Modifier.height(80.dp)
                            ) {
                                screens.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        colors = NavigationBarItemDefaults.colors(
                                            indicatorColor = Maroon20
                                        ),
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            if (selectedItemIndex != index) {
                                                viewModel.setSelectedItem(index)
                                            }
                                        },

                                        label = {
                                            Text(
                                                text = item.title,
                                                color = Maroon70,
                                                fontWeight = FontWeight.Bold
                                            )
                                        },
                                        alwaysShowLabel = false,
                                        icon = {
                                            Icon(
                                                modifier = Modifier.size(25.dp),
                                                painter = painterResource(id = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon),
                                                contentDescription = item.title,
                                                tint = if (index == selectedItemIndex) Maroon70 else Maroon20 // Set the desired color conditionally
                                            )
                                        }
                                    )
                                }
                            }
                    },
                    containerColor = Maroon10,
                    contentColor = Maroon70,
                    content = { paddingValues ->
                        NavGraph(
                            navController = navController,
                            isLoggedIn = isLoggedIn,
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                )
            }
        }
    }

    override fun onDestroy() {
        Intent(this, WorkoutService::class.java).also {
            it.action = WorkoutService.Actions.STOP.toString()
            this.startForegroundService(it)
        }
        super.onDestroy()
    }
}


fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}
