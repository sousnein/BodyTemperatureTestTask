package com.sousnein.bodytemperaturetesttask.ui

import android.app.Activity
import android.content.Context
import android.preference.PreferenceManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

var TEMPERATURE_MEASURE:String? = null
var LOGS_LIST_IS_EMPTY = true

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun hideKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
}

fun showKeyboard(editText: EditText, activity: Activity) {
    editText.requestFocus()
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm!!.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
}

fun TextView.setColorOfText(color: Int) {
    this.setTextColor(ContextCompat.getColor(context, color))
}

fun setTemperatureMeasure(context: Context?) {
    val sharedPreferencesKey = "temperatureMeasure"
    val badValueForSharedPreferences = "unknown"
    val sharedPreferences = PreferenceManager
        .getDefaultSharedPreferences(context)
    TEMPERATURE_MEASURE = sharedPreferences.getString(sharedPreferencesKey, badValueForSharedPreferences)!!
}
