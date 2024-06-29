package com.imrul.replog.feature_exercises.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CustomFlowRow(
    filterList: List<String>,
    modifier: Modifier = Modifier,
    item: @Composable (String) -> Unit,
    onCLick: () -> Unit
) {
    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        filterList.forEach { text ->
            Box(Modifier.clickable {
                onCLick()
            }) {
                item(text)
            }
        }
    }
}