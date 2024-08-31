package com.imrul.replog.feature_measurements.presentation.screen_measurements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.imrul.replog.core.presentation.components.LineChartGraph
import com.imrul.replog.core.presentation.components.MiniPlayer
import com.imrul.replog.feature_exercises.presentation.components.CustomDropDownMenu
import com.imrul.replog.feature_measurements.domain.model.Measurement
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutViewModel
import com.imrul.replog.ui.theme.Maroon70
import com.imrul.replog.ui.theme.WhiteCustom

@Composable
fun MeasurementsScreen(
    navController: NavHostController,
    workoutViewModel: WorkoutViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteCustom),
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "Category",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    CustomDropDownMenu(
                        text = "Category",
                        items = Measurement.listOfCategories,
                        onItemSelected = {},
                        fontSize = 20.sp
                    )
                }
            }
            item {
                LineChartGraph()
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "History",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Icon(
                        imageVector = Icons.Filled.Add, contentDescription = "Add Measurement",
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {

                            },
                        tint = Maroon70
                    )
                }
            }
        }
        if (workoutViewModel.isWorkOutRunning)
            MiniPlayer(
                workoutViewModel = workoutViewModel,
                navController = navController
            )
    }

}