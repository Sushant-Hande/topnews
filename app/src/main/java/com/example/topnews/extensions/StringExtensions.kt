package com.example.topnews.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by shande on 30-05-2021
 */
@SuppressLint("SimpleDateFormat")
fun String.toFormattedDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val outputFormat = SimpleDateFormat("dd-MM-yyyy")
    val date: Date = inputFormat.parse(this)
    return outputFormat.format(date)
}