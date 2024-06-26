package com.imrul.replog.feature_exercises.presentation.screen_exercises

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
import com.imrul.replog.core.presentation.CustomButton
import com.imrul.replog.feature_exercises.presentation.components.ExerciseListItem
import com.imrul.replog.ui.theme.WhiteCustom

@Composable
fun ExerciseListScreen(
    navController: NavHostController,
    viewModel: ExerciseListViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val exerciseListState by viewModel.exercisesListState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getAllExercises()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteCustom)
            .padding(bottom = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        item {
            CustomButton(
                onClick = { navController.navigate(Routes.ScreenAddEditExercises) },
                text = "Add Exercise",
                modifier = Modifier.padding(10.dp)
            )
        }
        items(exerciseListState.size) { exercise ->
            ExerciseListItem(exercise = exerciseListState[exercise])
        }

    }

}