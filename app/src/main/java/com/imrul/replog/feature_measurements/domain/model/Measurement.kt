package com.imrul.replog.feature_measurements.domain.model

data class Measurement(
    val id: String? = null,
    val category: String = listOfCategories[0],
    val unit: String = listOfUnits[0],
    val timeStamp: Long = System.currentTimeMillis(),
    val value: Float = 0f,
    val userId: String? = null
) {
    companion object {
        val listOfCategories = listOf(
            "Weight",
            "Caloric Intake",
            "Caloric Expenditure",
            "Neck",
            "Shoulder",
            "Left Bicep",
            "Right Bicep",
            "Left Forearm",
            "Right Forearm",
            "Waist",
            "Hips",
            "Left Quad",
            "Right Quad",
            "Left Calf",
            "Right Calf"
        )

        val listOfUnits = listOf(
            "kg",
            "lb",
            "cm",
            "in"
        )
    }
}
