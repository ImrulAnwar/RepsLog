package com.imrul.replog.feature_workout.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imrul.replog.core.Strings
import com.imrul.replog.ui.theme.Maroon10
import com.imrul.replog.ui.theme.Maroon70
import com.imrul.replog.ui.theme.WhiteCustom
import kotlin.math.exp

@Composable
fun DropDownMenuForExerciseName(
    modifier: Modifier = Modifier,
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = "More Details",
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    expanded = true
                },
            tint = Maroon70
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(WhiteCustom)
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
            ) {
                DropdownMenuItem(onClick = {
                    onEditClicked()
                    expanded = false
                }, text = {
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = Strings.ADD_A_NOTE,
                        color = Maroon70
                    )
                })
                DropdownMenuItem(onClick = {
                    onDeleteClicked()
                    expanded = false
                }, text = {
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = Strings.DELETE,
                        color = Maroon70
                    )
                })
            }
        }
    }
}
