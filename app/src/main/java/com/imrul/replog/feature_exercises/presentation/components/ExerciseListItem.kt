package com.imrul.replog.feature_exercises.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imrul.replog.feature_workout.domain.model.Exercise
import com.imrul.replog.ui.theme.Maroon10

@Composable
fun ExerciseListItem(
    exercise: Exercise
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = exercise.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "More Details",
                modifier = Modifier.size(24.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            exercise.targetMuscleGroup?.let {
                Text(
                    text = it,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Maroon10)
                        .padding(10.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            exercise.weightType?.let {
                Text(
                    text = it,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Maroon10)
                        .padding(10.dp)
                )
            }
        }
    }
}