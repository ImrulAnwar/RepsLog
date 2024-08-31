package com.imrul.replog.core.presentation.components

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

@Composable
fun LineChartGraph(
    pointsData: List<Point> = listOf(
        Point(0f, 40f),
        Point(1f, 50f),
        Point(2f, 60f),
        Point(3f, 70f),
        Point(4f, 80f),
        Point(6f, 50f),
        Point(7f, 60f),
        Point(8f, 70f),
        Point(9f, 60f),
    )
) {
    val ySteps = 5
    val xSteps = pointsData.size - 1

    val xAxisData = AxisData.Builder()
        .axisStepSize(40.dp)
        .backgroundColor(WhiteCustom)
        .steps(xSteps)
        .labelData { i -> i.toString() }
        .labelAndAxisLinePadding(15.dp)
        .axisLineColor(Maroon70)
        .axisLabelColor(Maroon70)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(ySteps)
        .backgroundColor(WhiteCustom)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            val maxNum = 100
            val yScale = maxNum / ySteps
            (i * yScale).toString()
        }
        .axisLineColor(Maroon70)
        .axisLabelColor(Maroon70)
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    lineStyle = LineStyle(
                        color = Maroon70,
                        lineType = LineType.SmoothCurve(isDotted = false)
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
        gridLines = GridLines(color = Maroon20)
    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = lineChartData
    )
}