package com.capstone.enviro.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Call
import retrofit2.http.GET

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
    val activity: String,
    val timestamp: TimeStamp
)

@Serializable
data class TimeStamp(
    @SerialName("\$date")
    val date: String
)

interface UserService {
    @GET("/users")
    fun getUsers(): Call<List<User>>
}