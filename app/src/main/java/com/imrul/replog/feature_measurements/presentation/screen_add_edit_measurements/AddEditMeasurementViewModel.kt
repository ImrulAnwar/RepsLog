package com.imrul.replog.feature_measurements.presentation.screen_add_edit_measurements

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.imrul.replog.feature_measurements.domain.model.Measurement
import com.imrul.replog.feature_measurements.domain.use_cases.MeasurementUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddEditMeasurementViewModel @Inject constructor(
    private val measurementUseCases: MeasurementUseCases
) : ViewModel() {
    var measurementValue by mutableStateOf("")
        private set
    var measurementUnit by mutableStateOf("")
        private set
    var measurementCategory by mutableStateOf("")
        private set

    var currentMeasurementId by mutableStateOf("")
        private set
    var timestamp by mutableLongStateOf(-1L)
        private set

    fun onMeasurementValueChange(string: String) {
        measurementValue = string
    }

    fun onMeasurementUnitChange(string: String) {
        measurementUnit = string
    }

    fun setCategory(string: String) {
        measurementCategory = string
    }

    fun clearData() {
        measurementValue = ""
        measurementUnit = ""
        measurementCategory = ""
        currentMeasurementId = ""
        timestamp = -1L
    }

    fun insertMeasurement(context: Context, navController: NavHostController) {
        viewModelScope.launch {
            if (!isNumber(measurementValue) || measurementValue.trim() == "") {
                Toast.makeText(
                    context,
                    "Value can not be empty & must be a number",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return@launch
            }

            if (measurementUnit.trim() == "") {
                Toast.makeText(context, "Unit can not be empty", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            }

            val measurement: Measurement
            if (currentMeasurementId == "") {
                measurement = Measurement(
                    value = measurementValue.toFloat(),
                    unit = measurementUnit,
                    timeStamp = System.currentTimeMillis(),
                    category = measurementCategory
                )
            } else {
                measurement = Measurement(
                    id = currentMeasurementId,
                    value = measurementValue.toFloat(),
                    unit = measurementUnit,
                    timeStamp = System.currentTimeMillis(),
                    category = measurementCategory
                )
            }
            try {
                navController.navigateUp()
                measurementUseCases.upsertMeasurement(measurement)
                clearData()
            } catch (e: Exception) {
                Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun deleteMeasurement(
        context: Context,
        navController: NavHostController,
        measurementId: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val measurement = measurementUseCases.getMeasurementById(measurementId)
                withContext(Dispatchers.Main) {
                    navController.navigateUp()
                }
                if (currentMeasurementId != "")
                    measurement?.let { measurementUseCases.deleteMeasurement(it) }
                clearData()
            } catch (_: Exception) {
            }
        }
    }


    fun initializeParameters(
        measurementId: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val measurement = measurementUseCases.getMeasurementById(measurementId)
            measurement?.let {

                currentMeasurementId = measurementId
                measurementValue = measurement.value.toString()
                measurementUnit = measurement.unit
                measurementCategory = measurement.category
                timestamp = measurement.timeStamp
            }
        }
    }

    fun isNumber(input: String): Boolean {
        return input.toDoubleOrNull() != null
    }
}