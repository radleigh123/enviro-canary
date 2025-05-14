package com.capstone.enviro.utils

import android.util.Log
import com.capstone.enviro.SessionManager
import com.capstone.enviro.data.remote.TokenManager
import com.capstone.enviro.domain.model.TimeStamp
import com.google.firebase.auth.FirebaseAuth
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

fun parseDate(dateString: String): TimeStamp {
    val isoDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US)
    isoDateFormat.timeZone = TimeZone.getTimeZone("UTC")

    // Extract date string if input is a TimeStamp string representation
    val actualDateString = if (dateString.startsWith("TimeStamp(date=")) {
        val datePattern = "TimeStamp\\(date=([\\d\\-T:.Z]+)\\)".toRegex()
        datePattern.find(dateString)?.groupValues?.get(1) ?: dateString
    } else {
        dateString
    }

    return try {
        val date = isoDateFormat.parse(actualDateString)
        TimeStamp(date = isoDateFormat.format(date!!))
    } catch (e: Exception) {
        Log.e("ParseDate", "Error parsing date: $actualDateString (original: $dateString)", e)
        TimeStamp(date = actualDateString) // Return the extracted date string if parsing fails
    }
}
