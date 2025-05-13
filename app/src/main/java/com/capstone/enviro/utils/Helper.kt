package com.capstone.enviro.utils

import com.capstone.enviro.domain.model.TimeStamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * Helper function to format the current date and time to a MongoDB-compatible ISO 8601 string.
 * MongoDB date format: {"$date": "2025-05-12T23:29:03.112Z"}
 *
 * @return A TimeStamp object containing the formatted date string.
 */
fun getMongoDBDate(): TimeStamp {
    val isoDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US)
    isoDateFormat.timeZone = TimeZone.getTimeZone("UTC")

    val currentTimeFormatted = isoDateFormat.format(Date())
    val currentTime = TimeStamp(date = currentTimeFormatted)

    return currentTime
}