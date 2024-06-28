package com.imrul.replog.feature_exercises.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.imrul.replog.core.presentation.CustomButton

@Composable
fun DeleteAlertDialog(
    onDelete: () -> Unit,
    onDismiss: () -> Unit
) {

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Confirm Deletion") },
        text = { Text("Are you sure you want to delete this exercise?") },
        confirmButton = {
            CustomButton(onClick = {
                onDelete()
                onDismiss()
            }, text = "Delete", fontSize = 14.sp)
        },
        dismissButton = {
            CustomButton(
                onClick = { onDismiss() },
                text = "Cancel",
                fontSize = 14.sp
            )
        }
    )
}