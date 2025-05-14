package com.capstone.enviro.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Activities(
    val id: String,
    val title: String,
    val startDate: TimeStamp? = null,
    val endDate: TimeStamp? = null,
    val progress: Int,
    val type: String
)
