package com.imrul.replog.core.presentation

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.imrul.replog.core.Strings
import com.imrul.replog.ui.theme.Maroon70
import com.imrul.replog.ui.theme.WhiteCustom

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String = "",
    colors: ButtonColors = ButtonColors(
        containerColor = Maroon70,
        contentColor = WhiteCustom,
        disabledContentColor = Maroon70,
        disabledContainerColor = WhiteCustom
    ),
    fontSize: TextUnit = 14.sp
) {
    Button(
        modifier = modifier,
        onClick = { onClick() },
        colors = colors
    ) {
        Text(
            text = text,
            fontSize = fontSize
        )
    }
}