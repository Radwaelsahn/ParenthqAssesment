package com.radwaelsahn.parenthq.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

fun readJSONfromFile(f: String, context: Context): String {

    return context.assets.open(f).bufferedReader().use { it.readText() }

}

//fun getDate(date: Long): String {
//    val timeFormatter = SimpleDateFormat("dd.MM.yyyy")
//    return timeFormatter.format(Date(date * 1000L))
//}




