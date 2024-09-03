package com.imrul.replog.feature_measurements.presentation.screen_measurements

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.imrul.replog.core.Routes
import com.imrul.replog.core.presentation.components.LineChartGraph
import com.imrul.replog.core.presentation.components.MiniPlayer
import com.imrul.replog.feature_exercises.presentation.components.CustomDropDownMenu
import com.imrul.replog.feature_measurements.domain.model.Measurement
import com.imrul.replog.feature_workout.presentation.screen_workout.WorkoutViewModel
import com.imrul.replog.ui.theme.Maroon70
import com.imrul.replog.ui.theme.WhiteCustom
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.*

@Composable
fun MeasurementsScreen(
    navController: NavHostController,
    workoutViewModel: WorkoutViewModel,
    measurementsViewModel: MeasurementsViewModel,
    context: Context = LocalContext.current
) {
    val measurementsList by measurementsViewModel.measurementList.collectAsState()

    LaunchedEffect(measurementsViewModel.selectedCategory) {
        measurementsViewModel.getAllMeasurementsByCategory(context)
    }
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
                                navController.navigate(
                                    Routes.ScreenAddEditMeasurements(
                                        measurementId = -1
                                    )
                                )
                                Toast
                                    .makeText(
                                        context,
                                        "${measurementsList.size}",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            },
                        tint = Maroon70
                    )
                }
            }
            items(measurementsList) { measurement ->
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = measurementsViewModel.convertTimestampToFormattedDateTime(
                            measurement.timeStamp
                        ), fontSize = 300.sp
                    )
                    Text(text = measurement.value.toString() + " " + measurement.unit)
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

