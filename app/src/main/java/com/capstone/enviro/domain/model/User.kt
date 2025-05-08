package com.capstone.enviro.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
//@JsonIgnoreUnknownKeys
data class User(
    val userId: String,
    val email: String,
    val name: String,
    val profilePicture: String?,
    val roles: List<String> = listOf(),
    val accountStatus: String,
    val activityLog: List<ActivityLog> = listOf(),
    val lastLogin: TimeStamp,
    val loginProvider: String,
    val createdAt: TimeStamp,
    val updatedAt: TimeStamp
)

@Serializable
data class ActivityLog(
    val action: String,
    val timestamp: TimeStamp
)

@Serializable
data class TimeStamp(
    @SerializedName("\$date")
    val date: String
)
