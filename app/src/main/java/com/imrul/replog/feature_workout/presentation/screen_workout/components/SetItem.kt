package com.imrul.replog.feature_workout.presentation.screen_workout.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.imrul.replog.core.Strings
import com.imrul.replog.feature_workout.presentation.components.NumberTextField
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutViewModel
import com.imrul.replog.ui.theme.Maroon10
import com.imrul.replog.ui.theme.Maroon20
import com.imrul.replog.ui.theme.Maroon70
import com.imrul.replog.ui.theme.Maroon90
import com.imrul.replog.ui.theme.Red
import com.imrul.replog.ui.theme.WhiteCustom
import kotlin.math.roundToInt

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun SetItem(
    setIndex: Int,
    exerciseIndex: Int? = null,
    workoutViewModel: WorkoutViewModel,
    onSwiped: () -> Unit
) {
    val listOfIsDone = workoutViewModel.listOfIsDone
    val listOfTillFailure = workoutViewModel.listOfTillFailure

    val swipeable = rememberSwipeableState(initialValue = 0)
    val sizePx = with(LocalDensity.current) { 200.dp.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1) // Maps the swipe positions to states

    LaunchedEffect(swipeable.currentValue) {
        if (swipeable.currentValue == 1) {
            onSwiped()
            swipeable.snapTo(0)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .swipeable(
                state = swipeable,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(.8f) },
                orientation = Orientation.Horizontal,
            )
    ) {
        Box(
            modifier = Modifier
                .background(Maroon90)
                .offset { IntOffset(swipeable.offset.value.roundToInt(), -1) }
        )

        // add a red box which will appear from outside of the screen
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (listOfIsDone[setIndex]) Maroon20 else WhiteCustom)
                .offset { IntOffset(swipeable.offset.value.roundToInt(), 0) }
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    workoutViewModel.toggleTillFailure(setIndex)
                }, colors = ButtonColors(
                    containerColor = if (listOfTillFailure[setIndex]) Red else Maroon10,
                    contentColor = if (listOfTillFailure[setIndex]) Maroon90 else Maroon70,
                    disabledContainerColor = Maroon20,
                    disabledContentColor = WhiteCustom
                ),
                modifier = Modifier.width(60.dp)
            ) {
                Text(
                    if (!listOfTillFailure[setIndex]) "W" else "F",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(0.dp),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = workoutViewModel.listOfPrevious[setIndex],
                fontSize = 12.sp,
                color = Maroon70,
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            NumberTextField(
                text = workoutViewModel.listOfWeights[setIndex].second,
                isDone = listOfIsDone[setIndex],
                onValueChange = {
                    workoutViewModel.onWeightValueChanged(
                        setIndex = setIndex,
                        content = it
                    )
                }, modifier = Modifier.width(50.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            NumberTextField(
                text = workoutViewModel.listOfReps[setIndex],
                isDone = listOfIsDone[setIndex],
                onValueChange = {
                    workoutViewModel.onRepValueChanged(
                        setIndex = setIndex,
                        content = it
                    )
                }, modifier = Modifier.width(50.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            SetIsDoneIcon(
                painter = rememberVectorPainter(image = Icons.Outlined.Done),
                contentDescription = Strings.FINISHED_SET_BUTTON,
                onClick = { workoutViewModel.toggleIsDone(setIndex) },
                isDone = listOfIsDone[setIndex]
            )
        }
    }
}
