package com.imrul.replog.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.imrul.replog.ui.theme.Maroon20
import com.imrul.replog.ui.theme.Maroon70
import com.imrul.replog.ui.theme.WhiteCustom
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun LineChartGraph(
    pointsData: List<Point> =
        listOf(
            Point(1f, 50f),
            Point(2f, 60f),
            Point(3f, 90f),
            Point(4f, 80f),
            Point(6f, 50f),
            Point(7f, 60f),
            Point(8f, 70f),
            Point(9f, 60f),
            Point(10f, 50f),
            Point(12f, 60f),
            Point(13f, 90f),
            Point(14f, 80f),
            Point(16f, 50f),
            Point(17f, 60f),
            Point(18f, 70f),
            Point(19f, 60f),
            Point(21f, 50f),
            Point(22f, 60f),
            Point(23f, 90f),
            Point(24f, 80f),
            Point(26f, 50f),
            Point(27f, 60f),
            Point(28f, 70f),
            Point(29f, 60f),
        )
) {
    val ySteps = 5
    val xSteps = pointsData.size - 1
    val minValY = pointsData.minByOrNull { it.y }?.y
    val maxValY = pointsData.maxByOrNull { it.y }?.y


    // eitar label e date thakbe, and logic change kora lagbe

    // screen width calculate kortesi jate joto data ie thakuk always jate screen e fit kore graph ta
    val minValX = pointsData.minByOrNull { it.x }?.x
    val maxValX = pointsData.maxByOrNull { it.x }?.x
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp / 6 * 5
    val axisStepSize =
        if (xSteps > 0) screenWidth / ((maxValX ?: 5f) - (minValX ?: 0f)) else screenWidth

    // to convert the x value to a date
    val dateFormatter = DateTimeFormatter.ofPattern("MMM d")

    val xAxisData = AxisData.Builder()
        .backgroundColor(WhiteCustom)
        .steps(xSteps)
        .axisStepSize(axisStepSize)
        .labelData { i ->
            // Convert day of the year to a LocalDate and format it
            val dayOfYear = (minValX?.toInt() ?: 0) + i
            val date = LocalDate.ofYearDay(LocalDate.now().year, dayOfYear)
            date.format(dateFormatter)  // Format to "Sep 23"
        }
        .labelAndAxisLinePadding(20.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(ySteps)
        .backgroundColor(WhiteCustom)
        .labelAndAxisLinePadding(20.dp)
        .axisLineColor(Maroon70)
        .labelData { i ->
            val min = minValY ?: 0f  // Provide a default value in case `minVal` is null
            val max = maxValY ?: 1f  // Provide a default value in case `maxVal` is null
            val yScale = (max - min) / ySteps
            ((i * yScale) + min).toInt().toString()
        }.build()


    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    lineStyle = LineStyle(
                        color = Maroon70,
                        lineType = LineType.SmoothCurve(isDotted = true)
                    ),
                    intersectionPoint = IntersectionPoint(
                        color = Maroon70
                    ),
                    selectionHighlightPoint = SelectionHighlightPoint(
                        color = Maroon70,
                    ),
                    shadowUnderLine = ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Maroon20,
                                WhiteCustom
                            )
                        )
                    ),
                    selectionHighlightPopUp = SelectionHighlightPopUp()
                )
            )
        ),
        backgroundColor = WhiteCustom,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(color = WhiteCustom)
    )
    if (pointsData.isNotEmpty())
        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(WhiteCustom),
            lineChartData = lineChartData
        )
}