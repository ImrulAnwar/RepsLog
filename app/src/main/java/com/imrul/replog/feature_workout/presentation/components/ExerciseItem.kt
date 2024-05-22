package com.imrul.replog.feature_workout.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutViewModel
import com.imrul.replog.ui.theme.Maroon70

@Composable
fun ExerciseItem(
    workoutViewModel: WorkoutViewModel = hiltViewModel()
) {
    val listOfWeightRepsPair: SnapshotStateList<Pair<String?, String?>> =
        workoutViewModel.listOfWeightRepsPair
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Exercise Name",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp),
            color = Maroon70
        )
        NoteTextField(
            text = "note",
            onValueChange = {},
            label = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp)
        )
        Text(
            text = "Add Set",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(10.dp)
                .clickable { workoutViewModel.addWeightRepsPair("", "") },
            color = Maroon70
        )

        listOfWeightRepsPair.forEachIndexed { index, _ ->
            SetItem(index)
        }
    }
}