package com.imrul.replog.feature_workout.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.imrul.replog.core.Strings
import com.imrul.replog.ui.theme.Maroon10
import com.imrul.replog.ui.theme.Maroon70
import com.imrul.replog.ui.theme.Maroon90
import kotlin.math.roundToInt

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun AddNoteTextField(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    label: String = "Add a Note",
    onSwiped: () -> Unit = {}
) {
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val sizePx = with(LocalDensity.current) { 200.dp.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1)

    LaunchedEffect(swipeableState.currentValue) {
        if (swipeableState.currentValue == 1) {
            onSwiped()
            swipeableState.snapTo(0)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.8f) },
                orientation = Orientation.Horizontal,
            )
            .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
    ) {
        OutlinedTextField(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            value = text,
            onValueChange = { onValueChange(it) },
            label = { Text(label) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Maroon70,
                unfocusedTextColor = Maroon70,
                cursorColor = Maroon70,
                focusedBorderColor = Maroon70,
                unfocusedBorderColor = Maroon10,
                focusedLabelColor = Maroon70,
                unfocusedLabelColor = Maroon10,
            ),
        )
    }
}

