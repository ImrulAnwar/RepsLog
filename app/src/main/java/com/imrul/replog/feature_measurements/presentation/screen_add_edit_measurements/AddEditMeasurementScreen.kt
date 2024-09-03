package com.imrul.replog.feature_measurements.presentation.screen_add_edit_measurements

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.imrul.replog.core.Strings
import com.imrul.replog.core.presentation.CustomButton
import com.imrul.replog.feature_workout.presentation.components.CustomTextField
import com.imrul.replog.ui.theme.WhiteCustom

@Composable
fun AddEditMeasurementScreen(
    navController: NavHostController,
    viewModel: AddEditMeasurementViewModel,
    context: Context = LocalContext.current,
    measurementId: Long
) {

    LaunchedEffect(Unit) {
        if (measurementId != -1L) {
            viewModel.initializeParameters(measurementId = measurementId)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteCustom),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CustomTextField(
            text = viewModel.measurementValue,
            onValueChange = {
                viewModel.onMeasurementValueChange(it)
            },
            label = Strings.VALUE,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        )
        CustomTextField(
            text = viewModel.measurementUnit,
            onValueChange = {
                viewModel.onMeasurementUnitChange(it)
            },
            label = Strings.UNIT,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomButton(
                onClick = {
                viewModel.deleteMeasurement(context = context, navController = navController,measurementId = measurementId)
                },
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                text = Strings.DELETE
            )
            CustomButton(
                onClick = {
                viewModel.insertMeasurement(context = context, navController = navController)
                },
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                text = Strings.SAVE_MEASUREMENT
            )
        }


    }
}

