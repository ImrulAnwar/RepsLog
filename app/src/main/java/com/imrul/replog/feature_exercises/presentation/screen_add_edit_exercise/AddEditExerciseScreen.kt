package com.imrul.replog.feature_exercises.presentation.screen_add_edit_exercise

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.imrul.replog.core.Routes

@Composable
fun AddEditExerciseScreen(
    navController: NavHostController
) {
    Button(onClick = { navController.navigateUp() }) {
        Text(text = "Go to exercise screen")
    }

}