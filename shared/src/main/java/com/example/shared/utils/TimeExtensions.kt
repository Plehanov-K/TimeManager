@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.shared.utils

import java.text.SimpleDateFormat
import java.util.*

fun String.parseTimeToMillis(pattern: String = "yyyy-MM-dd"): Long {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.parse(this).time
}

fun Long.parseTimeFromMillis(pattern: String = "yyyy-MM-dd"): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(this)
}

fun Int.parseTimeFromMinutes(): String {
    val hours = "${this / 60}".padStart(2, '0')
    val minutes = "${this % 60}".padStart(2, '0')

    return "$hours : $minutes"
}
