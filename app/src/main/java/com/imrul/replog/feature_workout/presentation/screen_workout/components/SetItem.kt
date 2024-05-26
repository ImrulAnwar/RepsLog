package com.imrul.replog.feature_workout.presentation.screen_workout.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.imrul.replog.core.Strings
import com.imrul.replog.core.presentation.components.CustomIcon
import com.imrul.replog.core.presentation.components.RegularTextField
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutViewModel
import com.imrul.replog.ui.theme.Maroon20
import com.imrul.replog.ui.theme.Maroon70

@Composable
fun SetItem(
    setIndex: Int,
    exerciseIndex: Int? = null,
    workoutViewModel: WorkoutViewModel = hiltViewModel(),
) {
    val listOfIsDone = workoutViewModel.listOfIsDone
    val listOfTillFailure = workoutViewModel.listOfTillFailure
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = {
                workoutViewModel.toggleTillFailure(setIndex)
            }, colors = ButtonColors(
                containerColor = if (listOfTillFailure[setIndex]) Maroon70 else Maroon20,
                contentColor = if (!listOfTillFailure[setIndex]) Maroon70 else Maroon20,
                disabledContainerColor = Maroon20,
                disabledContentColor = Maroon70
            )
        ) {
            Text("F", modifier = Modifier.padding(0.dp))
        }
        Text("50 kg x 12", color = Maroon20)
        RegularTextField(
            text = workoutViewModel.listOfWeights[setIndex].second,
            onValueChange = {
                workoutViewModel.onWeightValueChanged(
                    setIndex = setIndex,
                    content = it
                )
            },
            modifier = Modifier.width(60.dp)
        )
        RegularTextField(
            text = workoutViewModel.listOfReps[setIndex],
            onValueChange = {
                workoutViewModel.onRepValueChanged(
                    setIndex = setIndex,
                    content = it
                )
            },
            modifier = Modifier.width(60.dp)
        )
        SetIsDoneIcon(
            painter = rememberVectorPainter(image = Icons.Outlined.Done),
            contentDescription = Strings.FINISHED_SET_BUTTON,
            onClick = { workoutViewModel.toggleIsDone(setIndex) },
            isDone = listOfIsDone[setIndex]
        )
    }
}