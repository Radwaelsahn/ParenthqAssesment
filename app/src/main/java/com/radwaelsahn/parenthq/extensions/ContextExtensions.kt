package com.radwaelsahn.parenthq.extensions

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.support.v4.content.ContextCompat
import android.widget.Toast

fun Context.color(res: Int): Int = ContextCompat.getColor(this, res)

fun Context.isConnectedToInternet(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val activeNetwork = cm.activeNetworkInfo

    return activeNetwork != null && activeNetwork.isConnectedOrConnecting
}

fun Context.showToast(message: String, length: Int) {
    Toast.makeText(this, message, length)
            .show()
}

fun Context.readJSONfromFile(f: String): String {
    return assets.open(f).bufferedReader().use { it.readText() }
}

fun Context.toast(toastMessage: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, toastMessage, duration).show()
}

