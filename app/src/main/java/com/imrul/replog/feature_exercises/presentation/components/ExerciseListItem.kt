package com.imrul.replog.feature_exercises.presentation.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.imrul.replog.core.Routes
import com.imrul.replog.feature_exercises.presentation.screen_exercises.ExerciseListViewModel
import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.ui.theme.Maroon10

@Composable
fun ExerciseListItem(
    navController: NavHostController,
    context: Context = LocalContext.current,
    exercise: Exercise,
    viewModel: ExerciseListViewModel = hiltViewModel(),
    onClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteAlertDialog(onDelete = {
            viewModel.deleteExercise(exercise)
        }, onDismiss = { showDialog = false })
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = exercise.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            DropDownMenuForExerciseItem(
                onEditClicked = {
                    navController.navigate(
                        Routes.ScreenAddEditExercises(
                            exerciseId = exercise.id ?: ""
                        )
                    )
                },
                onDeleteClicked = { showDialog = true })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            exercise.targetMuscleGroup?.let {
                Text(
                    text = it,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Maroon10)
                        .padding(10.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            exercise.weightType?.let {
                Text(
                    text = it,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Maroon10)
                        .padding(10.dp)
                )
            }
        }
    }
}