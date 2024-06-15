package com.imrul.replog.feature_workout.presentation.screen_workout.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.imrul.replog.ui.theme.Maroon10
import com.imrul.replog.ui.theme.Maroon20
import com.imrul.replog.ui.theme.Maroon70

@Composable
fun SetIsDoneIcon(
    painter: Painter,
    contentDescription: String,
    onClick: () -> Unit,
    padding: Dp = 10.dp,
    size: Dp = 24.dp,
    isDone: Boolean
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(if (isDone) Maroon70 else Maroon10) // Set the background color
            .clickable { onClick() }
            .padding(padding)
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            tint = if (!isDone) Maroon70 else Maroon20,
            modifier = Modifier
                .size(size) // Make sure the Icon fits within the Box
        )
    }
}