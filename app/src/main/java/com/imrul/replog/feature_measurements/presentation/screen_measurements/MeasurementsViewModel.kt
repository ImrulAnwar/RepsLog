package com.imrul.replog.feature_measurements.presentation.screen_measurements

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.Point
import com.imrul.replog.feature_measurements.domain.model.Measurement
import com.imrul.replog.feature_measurements.domain.use_cases.MeasurementUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter.ofPattern
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MeasurementsViewModel @Inject constructor(
    private val measurementUseCases: MeasurementUseCases
) : ViewModel() {
    var selectedCategory by mutableStateOf(Measurement.listOfCategories[0])
        private set

    private val _measurementList = MutableStateFlow<List<Measurement>>(emptyList())
    val measurementList = _measurementList
    private val _pointsList = MutableStateFlow<List<Point>>(emptyList())
    val pointsList = _pointsList
    fun onSelectedCategory(string: String) {
        selectedCategory = string
    }

    fun getAllMeasurementsByCategory(context: Context) {
        viewModelScope.launch {
            measurementUseCases.getAllMeasurementsByCategory(selectedCategory).collect {
                _measurementList.value = it
            }
        }
    }

    fun updatePoints() {
        val listOfPoints = mutableListOf<Point>()
        _measurementList.value.forEach { measurement ->
            // Convert timestamp to day of the year
            val calendar = Calendar.getInstance().apply {
                timeInMillis = measurement.timeStamp
            }
            val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR) // Get the day of the year

            // Use the dayOfYear as the y-axis value
            listOfPoints.add(Point(dayOfYear.toFloat(), measurement.value))
        }
        pointsList.value = listOfPoints
    }


    fun convertTimestampToFormattedDateTime(timestamp: Long): String {
        val instant = Instant.ofEpochMilli(timestamp)
        val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

        val dateFormatter = ofPattern("MMM d")
        val timeFormatter = ofPattern("h:mm a")

        val date = dateTime.format(dateFormatter)
        val time = dateTime.format(timeFormatter).lowercase() // Convert to lowercase for "am"/"pm"

        return "$date $time"
    }
}