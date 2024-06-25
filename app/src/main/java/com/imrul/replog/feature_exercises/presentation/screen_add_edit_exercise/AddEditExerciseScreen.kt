package com.imrul.replog.feature_exercises.presentation.screen_add_edit_exercise

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.imrul.replog.core.Routes
import com.imrul.replog.core.Strings
import com.imrul.replog.core.presentation.CustomButton
import com.imrul.replog.feature_exercises.presentation.components.CustomDropDownMenu
import com.imrul.replog.feature_workout.presentation.components.CustomTextField
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutService
import com.imrul.replog.ui.theme.WhiteCustom

@Composable
fun AddEditExerciseScreen(
    navController: NavHostController,
    viewModel: AddEditExerciseViewModel = hiltViewModel()
) {

    val selectedMuscleGroup = viewModel.selectedMuscleGroup
    val selectedWeightType = viewModel.selectedWeightType
    val exerciseName = viewModel.exerciseName
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteCustom),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CustomTextField(
            text = exerciseName,
            onValueChange = { viewModel.onExerciseNameChanged(it) },
            label = Strings.EXERCISE_NAME,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = Strings.MUSCLE_GROUP,
                fontWeight = FontWeight.Bold
            )
            CustomDropDownMenu(
                text = selectedMuscleGroup,
                items = viewModel.muscleGroupList,
                onItemSelected = { viewModel.onSelectedMuscleGroup(it) },
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = Strings.WEIGHT_TYPE,
                fontWeight = FontWeight.Bold
            )
            CustomDropDownMenu(
                text = selectedWeightType,
                items = viewModel.weightTypeList,
                onItemSelected = { viewModel.onSelectedWeightType(it) },
            )
        }
        CustomButton(
            onClick = {

            },
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp),
            text = Strings.SAVE_EXERCISE
        )

    }
}

