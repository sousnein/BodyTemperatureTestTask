package com.sousnein.bodytemperaturetesttask.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sousnein.bodytemperaturetesttask.R
import com.sousnein.bodytemperaturetesttask.db.Log
import com.sousnein.bodytemperaturetesttask.db.LogDatabase
import com.sousnein.bodytemperaturetesttask.ui.BaseFragment
import kotlinx.android.synthetic.main.item_logs.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

class LogsRecyclerAdapter(private val logs: ArrayList<Log>, db: LogDatabase) :
    RecyclerView.Adapter<LogsRecyclerAdapter.LogsViewHolder>(),CoroutineScope {

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
        holder.txtDate.text = logs[position].date
        holder.txtTime.text = logs[position].time
        holder.txtTemperature.text = logs[position].temperature.toString()
        holder.btnDelete.setOnClickListener {
            job = Job()

            launch {
                mDb.getLogDao().deleteLog(logs[position])
                logs.removeAt(position)

                notifyItemRemoved(position)
                notifyItemRangeChanged(position,logs.size+1)

                job.cancel()
            }
        }
    }

    override fun getItemCount(): Int {
        return logs.size ?: 0
    }

    override val coroutineContext: CoroutineContext
        get() = job+Dispatchers.Main

}