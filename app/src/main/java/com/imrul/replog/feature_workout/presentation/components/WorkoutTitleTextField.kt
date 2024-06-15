package com.imrul.replog.feature_workout.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imrul.replog.core.Strings
import com.imrul.replog.ui.theme.Maroon20
import com.imrul.replog.ui.theme.Maroon70

@Composable
fun WorkoutTitleTextField(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
    text: String,
    onValueChange: (String) -> Unit,
    label: String = ""
) {
    OutlinedTextField(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        value = text,
        onValueChange = { onValueChange(it) },
        textStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp // Adjust the font size as needed
        ),
        label = { Text(label) },
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