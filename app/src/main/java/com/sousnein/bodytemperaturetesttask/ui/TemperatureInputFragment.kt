package com.sousnein.bodytemperaturetesttask.ui

import android.app.Activity
import android.os.Bundle
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_temperature_input, container, false)
    }

    override fun onStart() {
        super.onStart()
        println("start")
        inputTemperature.requestFocus()
        showKeyboard(inputTemperature,activity as Activity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgAddTemperature.setOnClickListener {
            val input = inputTemperature.text.toString()
            if (input.isNotEmpty()) {
                val date = DateUtil.getDate()
                val time = DateUtil.getTime()
                val temperature = input.toDouble()

                launch {
                    val log = Log(date = date, time = time, temperature = temperature)
                    context?.let { _context ->
                        LogDatabase(_context).getLogDao().addLog(log)
                        _context.toast("Мы добавили вашу температуру")
                    }
                    findNavController().navigate(R.id.action_temperatureInputFragment_to_logsFragment)
                }
            } else context?.toast("Введите вашу температуру")
        }

    }


}