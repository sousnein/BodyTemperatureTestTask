package com.sousnein.bodytemperaturetesttask.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sousnein.bodytemperaturetesttask.R
import com.sousnein.bodytemperaturetesttask.db.Log
import com.sousnein.bodytemperaturetesttask.db.LogDatabase
import com.sousnein.bodytemperaturetesttask.ui.LOGS_LIST_IS_EMPTY
import com.sousnein.bodytemperaturetesttask.ui.TEMPERATURE_MEASURE
import com.sousnein.bodytemperaturetesttask.ui.setColorOfText
import kotlinx.android.synthetic.main.fragment_logs.*
import kotlinx.android.synthetic.main.fragment_logs.view.*
import kotlinx.android.synthetic.main.item_logs.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LogsRecyclerAdapter(private val logs: ArrayList<Log>, db: LogDatabase) :
    RecyclerView.Adapter<LogsRecyclerAdapter.LogsViewHolder>(), CoroutineScope {

    private val mDb = db
    private lateinit var job: Job

    class LogsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtDate: TextView = view.txtDate
        val txtTime: TextView = view.txtTime
        val txtTemperature: TextView = view.txtTemperature
        val btnDelete: TextView = view.btnDeleteLogItem
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LogsViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_logs, viewGroup, false)
        return LogsViewHolder(view = view)
    }

    override fun onBindViewHolder(holder: LogsViewHolder, position: Int) {
        val temperature = logs[position].temperature
        holder.txtDate.text = logs[position].date
        holder.txtTime.text = logs[position].time
        holder.txtTemperature.text = temperature.toString()
        holder.btnDelete.setOnClickListener {
            job = Job()
            launch {
                mDb.getLogDao().deleteLog(logs[position])
                logs.removeAt(position)

                notifyItemRemoved(position)
                notifyItemRangeChanged(position, logs.size + 1)
                if (logs.isEmpty())
                    LOGS_LIST_IS_EMPTY = true

                job.cancel()
            }
        }
        if (TEMPERATURE_MEASURE == "F")
            when (temperature) {
                in 94.0..99.9 -> {
                    holder.txtTemperature.setColorOfText(R.color.black)
                }
                in 100.0..102.9 -> {
                    holder.txtTemperature.setColorOfText(R.color.hot)
                }
                in 103.0..108.0 -> {
                    holder.txtTemperature.setColorOfText(R.color.veryHot)
                }
            }
        else
            when (temperature) {
                in 34.0..36.9 -> {
                    holder.txtTemperature.setColorOfText(R.color.black)
                }
                in 37.0..37.5 -> {
                    holder.txtTemperature.setColorOfText(R.color.hot)
                }
                in 37.6..44.0 -> {
                    holder.txtTemperature.setColorOfText(R.color.veryHot)
                }
            }
    }

    override fun getItemCount(): Int {
        return logs.size
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

}