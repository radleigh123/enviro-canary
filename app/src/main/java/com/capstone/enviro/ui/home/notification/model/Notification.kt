package com.capstone.enviro.ui.home.notification.model

data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val date: String,
    val time: String,
    val isRead: Boolean = false
)
