package com.imrul.replog.feature_workout.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    index: Int? = null,
    workoutViewModel: WorkoutViewModel = hiltViewModel(),
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { /*TODO*/ }, colors = ButtonColors(
                containerColor = Maroon70,
                contentColor = Maroon20,
                disabledContainerColor = Maroon70,
                disabledContentColor = Maroon20
            )
        ) {
            Text("F", modifier = Modifier.padding(0.dp))
        }
        Text("50 kg x 12", color = Maroon20)
        RegularTextField(
            text = workoutViewModel.listOfWeightRepsPair[index!!].first.toString(),
            onValueChange = { workoutViewModel.updateWeightRepsPair(index, first = it) },
            modifier = Modifier.width(60.dp)
        )
        RegularTextField(
            text = workoutViewModel.listOfWeightRepsPair[index!!].second.toString(),
            onValueChange = { workoutViewModel.updateWeightRepsPair(index, second = it) },
            modifier = Modifier.width(60.dp)
        )
        CustomIcon(
            painter = rememberVectorPainter(image = Icons.Filled.Done),
            contentDescription = Strings.FINISHED_SET_BUTTON,
            onClick = {}
        )
    }
}