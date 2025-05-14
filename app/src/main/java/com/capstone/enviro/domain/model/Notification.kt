package com.capstone.enviro.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val date: TimeStamp,
    var isRead: Boolean = false
)
