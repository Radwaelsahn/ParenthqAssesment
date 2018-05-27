package com.radwaelsahn.parenthq.extensions

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateString(dateFormat: Int = DateFormat.MEDIUM): String {
    val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
    return df.format(this)
}


fun getDate(date: Long): String {
    val timeFormatter = SimpleDateFormat("dd.MM.yyyy")
    return timeFormatter.format(Date(date * 1000L))
}