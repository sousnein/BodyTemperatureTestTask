package com.sousnein.bodytemperaturetesttask.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sousnein.bodytemperaturetesttask.R
import com.sousnein.bodytemperaturetesttask.db.Log
import com.sousnein.bodytemperaturetesttask.db.LogDatabase
import com.sousnein.bodytemperaturetesttask.ui.adapter.LogsRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_logs.*
import kotlinx.coroutines.launch


class LogsFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideKeyboard(activity as Activity)
        return inflater.inflate(R.layout.fragment_logs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch {
            context?.let {
                val db = LogDatabase.invoke(it)
                val logs = db.getLogDao().getAllNotes()

                logsRecyclerView.layoutManager = LinearLayoutManager(it)
                logsRecyclerView.adapter = LogsRecyclerAdapter(logs as ArrayList<Log>, db)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!LOGS_LIST_IS_EMPTY)
            txtYourLogsWillBeThere.visibility = View.GONE
    }
}