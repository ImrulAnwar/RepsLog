package com.imrul.replog.feature_workout.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.imrul.replog.ui.theme.Maroon70

@Composable
fun WorkoutTitleTextField(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    label: String = ""
) {
    OutlinedTextField(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        value = text,
        textStyle = MaterialTheme.typography.labelMedium,
        onValueChange = { onValueChange(it) },
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Maroon70,
            unfocusedTextColor = Maroon70,
            cursorColor = Maroon70,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedLabelColor = Color.Transparent,
            unfocusedLabelColor = Color.Transparent,
        ),
    )
}