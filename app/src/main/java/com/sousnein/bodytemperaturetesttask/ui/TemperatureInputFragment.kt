package com.sousnein.bodytemperaturetesttask.ui

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sousnein.bodytemperaturetesttask.R
import com.sousnein.bodytemperaturetesttask.db.Log
import com.sousnein.bodytemperaturetesttask.db.LogDatabase
import com.sousnein.bodytemperaturetesttask.utils.DateUtil
import kotlinx.android.synthetic.main.fragment_temperature_input.*
import kotlinx.coroutines.launch


class TemperatureInputFragment : BaseFragment() {
    private lateinit var temperatureMeasure: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         setTemperatureMeasure(this.context)


        return inflater.inflate(R.layout.fragment_temperature_input, container, false)

    }

    override fun onStart() {
        super.onStart()
        inputTemperature.requestFocus()
        showKeyboard(inputTemperature, activity as Activity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputTemperature.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                val str = s.toString()
                val p = str.indexOf(".")
                if (p >= 0) {
                    if (str.length - 2 > p) {
                        s.delete(s.length - 1, s.length)
                    }
                }
            }
        })
        imgAddTemperature.setOnClickListener {
            val input = inputTemperature.text.toString()
            if (input != "." && input.isNotEmpty() && checkThatTemperatureInputIsAlright(input.toDouble())) {
                val date = DateUtil.getDate()
                val time = DateUtil.getTime()
                val temperature = input.toDouble()
                LOGS_LIST_IS_EMPTY = false

                launch {
                    val log = Log(date = date, time = time, temperature = temperature)
                    context?.let { _context ->
                        LogDatabase(_context).getLogDao().addLog(log)
                        _context.toast("We added your temperature")
                    }
                    findNavController().navigate(R.id.action_temperatureInputFragment_to_logsFragment)
                }
            } else context?.toast("Enter correct temperature")
        }

    }

    private fun checkThatTemperatureInputIsAlright(input: Double): Boolean {
        if (TEMPERATURE_MEASURE == "F") {
            if (input in 94.0..108.0)
                return true
        } else {
            if (input in 34.0..44.0)
                return true
        }
        return false
    }
}