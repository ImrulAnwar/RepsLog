package com.imrul.replog.feature_exercises.presentation.screen_filter_exercise

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.imrul.replog.core.Strings
import com.imrul.replog.feature_exercises.presentation.components.CustomFlowRow
import com.imrul.replog.feature_exercises.presentation.screen_exercises.ExerciseListViewModel
import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.ui.theme.Maroon10
import com.imrul.replog.ui.theme.Maroon70
import com.imrul.replog.ui.theme.WhiteCustom

@Composable
fun FilterExerciseScreen(
    navController: NavHostController,
    viewModel: ExerciseListViewModel,
    context: Context = LocalContext.current,
) {

    val weightTypeFilterList by viewModel.weightTypeFilterList.collectAsState()
    val targetMuscleFilterList by viewModel.targetMuscleFilterList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteCustom),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = Strings.TARGET_MUSCLE_GROUP,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(15.dp)
        )
        HorizontalDivider(color = Maroon70)
        CustomFlowRow(
            filterList = Exercise.muscleGroups,
            item = { text ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (targetMuscleFilterList.contains(text)) Maroon70 else Maroon10)
                        .padding(10.dp),
                ) {
                    Text(
                        text = text,
                        color = if (targetMuscleFilterList.contains(text)) WhiteCustom else Maroon70
                    )
                }
            },
            onCLick = {
                viewModel.toggleTargetMuscleFilter(it)
            },
            modifier = Modifier.padding(5.dp)
        )
        Text(
            text = Strings.WEIGHT_TYPES,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(15.dp)
        )
        HorizontalDivider(color = Maroon70)
        CustomFlowRow(
            filterList = Exercise.weightTypes,
            item = { text ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (weightTypeFilterList.contains(text)) Maroon70 else Maroon10)
                        .padding(10.dp)
                ) {
                    Text(
                        text = text,
                        color = if (weightTypeFilterList.contains(text)) WhiteCustom else Maroon70
                    )
                }
            },
            onCLick = {
                viewModel.toggleWeightTypeFilter(it)
            },
            modifier = Modifier.padding(5.dp)
        )
    }
}