package com.imrul.replog.feature_exercises.presentation.screen_add_edit_exercise

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.imrul.replog.core.Strings
import com.imrul.replog.core.presentation.CustomButton
import com.imrul.replog.feature_exercises.presentation.components.CustomDropDownMenu
import com.imrul.replog.feature_workout.presentation.components.CustomTextField
import com.imrul.replog.ui.theme.WhiteCustom

@Composable
fun AddEditExerciseScreen(
    navController: NavHostController,
    viewModel: AddEditExerciseViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    exerciseId: Long
) {

    val selectedMuscleGroup = viewModel.selectedMuscleGroup
    val selectedWeightType = viewModel.selectedWeightType
    val exerciseName = viewModel.exerciseName
    LaunchedEffect(Unit) {
        if (exerciseId != -1L) {
            viewModel.initializeParameters(exerciseId = exerciseId)
        }
    }

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
                fontSize = 20.sp,
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
                fontSize = 20.sp,
            )
        }
        CustomButton(
            onClick = {
                viewModel.insertExercise(context = context, navController = navController)
            },
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp),
            text = Strings.SAVE_EXERCISE
        )

    }
}

