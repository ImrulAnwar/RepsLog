package com.imrul.replog.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.imrul.replog.core.Routes
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutViewModel
import com.imrul.replog.ui.theme.WhiteCustom

@Composable
fun MiniPlayer(
    workoutViewModel: WorkoutViewModel,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
                navController.navigate(Routes.ScreenWorkout)
            }
            .background(WhiteCustom)
            .padding(10.dp)
    ) {
        Text(
            text = workoutViewModel.workoutTitle,
//            modifier = Modifier.padding(10.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = workoutViewModel.elapsedTime,
//            modifier = Modifier.padding(10.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}