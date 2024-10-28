package com.imrul.replog.feature_measurements.presentation.screen_measurements

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.imrul.replog.core.Routes
import com.imrul.replog.core.presentation.components.LineChartGraph
import com.imrul.replog.core.presentation.components.MiniPlayer
import com.imrul.replog.feature_exercises.presentation.components.CustomDropDownMenu
import com.imrul.replog.feature_measurements.domain.model.Measurement
import com.imrul.replog.feature_measurements.presentation.screen_add_edit_measurements.AddEditMeasurementViewModel
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutViewModel
import com.imrul.replog.ui.theme.Maroon70
import com.imrul.replog.ui.theme.WhiteCustom

@Composable
fun MeasurementsScreen(
    navController: NavHostController,
    workoutViewModel: WorkoutViewModel,
    measurementsViewModel: MeasurementsViewModel,
    addEditMeasurementViewModel: AddEditMeasurementViewModel,
    context: Context = LocalContext.current
) {
    val measurementsList by measurementsViewModel.measurementList.collectAsState()
    val pointsList by measurementsViewModel.pointsList.collectAsState()

    LaunchedEffect(measurementsViewModel.selectedCategory, measurementsList) {
        measurementsViewModel.getAllMeasurementsByCategory(context)
        measurementsViewModel.updatePoints()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteCustom),
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .weight(1f)
                .padding(bottom = if (!workoutViewModel.isWorkOutRunning) 80.dp else 0.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                        text = measurementsViewModel.selectedCategory,
                        items = Measurement.listOfCategories,
                        onItemSelected = {
                            measurementsViewModel.onSelectedCategory(it)
                        },
                        fontSize = 20.sp
                    )
                }
            }
            item {
                if (pointsList.size < 2) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Add at least 2 dates to see the graph"
                    )
                }
            }
            item {
                LineChartGraph(pointsList)
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
                                navController.navigate(
                                    Routes.ScreenAddEditMeasurements(
                                        measurementId = ""
                                    )
                                )
                                addEditMeasurementViewModel.setCategory(measurementsViewModel.selectedCategory)
                            },
                        tint = Maroon70
                    )
                }
            }
            items(measurementsList.reversed()) { measurement ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .clickable {
                            navController.navigate(
                                Routes.ScreenAddEditMeasurements(
                                    measurementId = measurement.id ?: ""
                                )
                            )
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = measurementsViewModel.convertTimestampToFormattedDateTime(
                            measurement.timeStamp
                        ),
                        fontSize = 18.sp
                    )
                    Text(
                        text = measurement.value.toString() + " " + measurement.unit,
                        fontSize = 18.sp
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

