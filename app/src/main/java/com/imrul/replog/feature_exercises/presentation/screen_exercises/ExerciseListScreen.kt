package com.imrul.replog.feature_exercises.presentation.screen_exercises

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.imrul.replog.core.Routes

@Composable
fun ExerciseListScreen(
    navController: NavHostController
) {
    Button(onClick = { navController.navigate(Routes.ScreenAddEditExercises) }) {
        Text(text = "Go to edit screen")
    }

}