package com.capstone.enviro.utils.extensions

fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    val pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$".toRegex()
    return pattern.matches(this)
}

fun String.capitalizeWords(): String {
    return split(" ").map { it.capitalize() }.joinToString(" ")
}

fun String.removeExtraSpaces(): String {
    return replace("\\s+".toRegex(), " ").trim()
}

fun String.isNumeric(): Boolean {
    return matches("-?\\d+(\\.\\d+)?".toRegex())
}
