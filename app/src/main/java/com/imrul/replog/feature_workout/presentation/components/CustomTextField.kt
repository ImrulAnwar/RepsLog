package com.imrul.replog.feature_workout.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.imrul.replog.core.Strings
import com.imrul.replog.ui.theme.Maroon10
import com.imrul.replog.ui.theme.Maroon70

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    label: String = Strings.ADD_A_NOTE,
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