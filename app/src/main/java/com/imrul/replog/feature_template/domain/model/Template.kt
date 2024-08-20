package com.imrul.replog.feature_template.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Template(
    @PrimaryKey
    val templateId: Long? = null,
    val name: String = "",
    val dateString: String = "",
    val weekdayString: String = "",
)
