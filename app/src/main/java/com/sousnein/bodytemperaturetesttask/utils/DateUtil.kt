package com.sousnein.bodytemperaturetesttask.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private val calendar = Calendar.getInstance();

    @SuppressLint("SimpleDateFormat")
    fun getTime(): String {
        val date = Date()
        calendar.time = date
        val time = SimpleDateFormat("HH:mm").format(calendar.time)
        println(time)
        return time
    }

    @SuppressLint("SimpleDateFormat")
    fun getDate(): String {
        val date = Date()
        calendar.time = date
        val monthDay = SimpleDateFormat("MMMM d").format(calendar.time)
        return monthDay
    }
}