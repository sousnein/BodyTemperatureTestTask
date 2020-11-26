package com.sousnein.bodytemperaturetesttask

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_choose_measure_temperature.*


class ChooseMeasureTemperature : AppCompatActivity() {
    private lateinit var temperatureMeasure: String
    private val sharedPreferencesKey = "temperatureMeasure"
    private lateinit var sharedPreferences: SharedPreferences
    private val badValueForSharedPreferences = "unknown"
    private val prefix = "°"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(this@ChooseMeasureTemperature)
        val savedTemperatureMeasure =
            sharedPreferences.getString(sharedPreferencesKey, badValueForSharedPreferences)
        if (savedTemperatureMeasure != badValueForSharedPreferences) {
            temperatureMeasure = savedTemperatureMeasure!!
            startMainActivity()
            this.finish()
        } else
            setContentView(R.layout.activity_choose_measure_temperature)

    }

    override fun onResume() {
        super.onResume()
        temperatureMeasure = txtChosenMeasure.text.toString().removePrefix(prefix)

        switchMeasure.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                txtChosenMeasure.text = "°C"
            else
                txtChosenMeasure.text = "°F"

            temperatureMeasure = txtChosenMeasure.text.toString().removePrefix(prefix)
        }

        txtBtnNext.setOnClickListener {
            saveResult()
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this.applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

    private fun saveResult() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("temperatureMeasure", temperatureMeasure);
        editor.apply()
    }
}