package com.imrul.replog.feature_workout.presentation.screen_workout_history

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.imrul.replog.core.Routes
import com.imrul.replog.core.Strings
import com.imrul.replog.core.presentation.CustomButton
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutService
import com.imrul.replog.feature_workout.presentation.screen_workout_history.components.WorkoutItem
import com.imrul.replog.ui.theme.Maroon70
import com.imrul.replog.ui.theme.WhiteCustom

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkoutHistoryScreen(
    navController: NavHostController,
    workoutHistoryViewModel: WorkoutHistoryViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        workoutHistoryViewModel.getAllWorkouts()
    }

    val workoutListState by workoutHistoryViewModel.workoutListState.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteCustom)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                CustomButton(
                    onClick = {
                        navController.navigate(Routes.ScreenWorkout)
                        // start service
                        Intent(context, WorkoutService::class.java).also {
                            it.action = WorkoutService.Actions.START.toString()
                            context.startForegroundService(it)
                        }
                    },
                    modifier = Modifier.padding(top = 20.dp),
                    text = Strings.START_EMPTY_WORKOUT
                )
            }
            items(workoutListState) { workout ->
                WorkoutItem(workout)
            }

        }
    }

}