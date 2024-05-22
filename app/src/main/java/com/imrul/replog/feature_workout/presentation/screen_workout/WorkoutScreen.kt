package com.imrul.replog.feature_workout.presentation.screen_workout

import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.imrul.replog.core.Strings
import com.imrul.replog.core.presentation.components.CustomIcon
import com.imrul.replog.core.presentation.components.RegularTextField
import com.imrul.replog.feature_workout.presentation.components.ExerciseItem
import com.imrul.replog.feature_workout.presentation.components.SetItem

@Composable
fun WorkoutScreen(
    workoutViewModel: WorkoutViewModel = hiltViewModel()
) {
    val workoutTitle = workoutViewModel.workoutTitle
    val listState = rememberScrollState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {

            }) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Filled.Add),
                    contentDescription = Strings.ADD_BUTTON
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // This is the top part
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp)
                ) {
                    CustomIcon(
                        painter = rememberVectorPainter(image = Icons.AutoMirrored.Filled.ArrowBack),
                        contentDescription = Strings.BACK_BUTTON,
                        onClick = { }
                    )

                    Spacer(modifier = Modifier.weight(1f))
                    CustomIcon(
                        painter = rememberVectorPainter(image = Icons.Filled.Close),
                        contentDescription = Strings.CANCELLED_WORKOUT_BUTTON,
                        onClick = {}
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    CustomIcon(
                        painter = rememberVectorPainter(image = Icons.Filled.Done),
                        contentDescription = Strings.FINISHED_WORKOUT_BUTTON,
                        onClick = {}
                    )
                }
                // This is the body

                RegularTextField(
                    text = workoutTitle,
                    onValueChange = { workoutViewModel.onWorkoutTitleChanged(it) },
                )
                Column(
                    modifier = Modifier.verticalScroll(listState)
                ) {
                    for (i in 1..5) {
                        ExerciseItem()
                    }
                }

            }
        }
    )

}

