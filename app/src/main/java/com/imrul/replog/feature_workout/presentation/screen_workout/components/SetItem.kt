package com.imrul.replog.feature_workout.presentation.screen_workout.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.imrul.replog.core.Strings
import com.imrul.replog.feature_workout.presentation.components.NumberTextField
import com.imrul.replog.feature_workout.presentation.components.WorkoutTitleTextField
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutViewModel
import com.imrul.replog.ui.theme.Maroon10
import com.imrul.replog.ui.theme.WhiteCustom
import com.imrul.replog.ui.theme.Maroon20
import com.imrul.replog.ui.theme.Maroon70
import com.imrul.replog.ui.theme.Maroon90
import com.imrul.replog.ui.theme.Red

@Composable
fun SetItem(
    setIndex: Int,
    exerciseIndex: Int? = null,
    workoutViewModel: WorkoutViewModel = hiltViewModel(),
) {
    val listOfIsDone = workoutViewModel.listOfIsDone
    val listOfTillFailure = workoutViewModel.listOfTillFailure
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (listOfIsDone[setIndex]) Maroon20 else WhiteCustom),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = {
                workoutViewModel.toggleTillFailure(setIndex)
            }, colors = ButtonColors(
                containerColor = if (listOfTillFailure[setIndex]) Red else Maroon10,
                contentColor = if (listOfTillFailure[setIndex]) Maroon90 else Maroon70,
                disabledContainerColor = Maroon20,
                disabledContentColor = WhiteCustom
            )
        ) {
            Text(
                if (!listOfTillFailure[setIndex]) "W" else "F",
                modifier = Modifier.padding(0.dp),
                fontWeight = FontWeight.Bold
            )
        }
        Text("50 kg x 12", color = Maroon70)
        NumberTextField(
            text = workoutViewModel.listOfWeights[setIndex].second,
            isDone = listOfIsDone[setIndex],
            onValueChange = {
                workoutViewModel.onWeightValueChanged(
                    setIndex = setIndex,
                    content = it
                )
            },
        )
        NumberTextField(
            text = workoutViewModel.listOfReps[setIndex],
            isDone = listOfIsDone[setIndex],
            onValueChange = {
                workoutViewModel.onRepValueChanged(
                    setIndex = setIndex,
                    content = it
                )
            },
        )
        SetIsDoneIcon(
            painter = rememberVectorPainter(image = Icons.Outlined.Done),
            contentDescription = Strings.FINISHED_SET_BUTTON,
            onClick = { workoutViewModel.toggleIsDone(setIndex) },
            isDone = listOfIsDone[setIndex]
        )
    }
}