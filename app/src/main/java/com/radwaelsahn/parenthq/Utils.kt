package com.radwaelsahn.parenthq

import android.content.Context
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*

fun readJSONfromFile(f: String, context: Context): String {

    return context.assets.open(f).bufferedReader().use { it.readText() }

}


fun getDate(date: Long): String {
    val timeFormatter = SimpleDateFormat("dd.MM.yyyy")
    return timeFormatter.format(Date(date * 1000L))
}


