package com.imrul.replog.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.imrul.replog.ui.theme.Maroon70

@Composable
fun TitleTextField(
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
        label = { Text(label) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Maroon70,
            unfocusedTextColor = Maroon70,
            cursorColor = Maroon70,
            focusedBorderColor = Maroon70,
            unfocusedBorderColor = Color.Transparent,
            focusedLabelColor = Maroon70,
            unfocusedLabelColor = Color.Transparent,
        ),
    )
}