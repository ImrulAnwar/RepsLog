package com.imrul.replog.feature_exercises.presentation.screen_add_edit_exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.imrul.replog.feature_exercises.presentation.components.CustomDropDownMenu
import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.ui.theme.WhiteCustom

@Composable
fun AddEditExerciseScreen(
    navController: NavHostController,
    viewModel: AddEditExerciseViewModel = hiltViewModel()
) {

    val selectedMuscleGroup = viewModel.selectedMuscleGroup
    val selectedWeightType = viewModel.selectedWeightType
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteCustom),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center
    ) {
        CustomDropDownMenu(
            text = selectedMuscleGroup,
            items = viewModel.muscleGroupList,
            onItemSelected = { viewModel.onSelectedMuscleGroup(it) },
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        CustomDropDownMenu(
            text = selectedWeightType,
            items = viewModel.weightTypeList,
            onItemSelected = { viewModel.onSelectedWeightType(it) },
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

