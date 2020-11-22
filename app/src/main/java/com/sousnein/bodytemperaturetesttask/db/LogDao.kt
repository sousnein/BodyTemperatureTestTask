package com.sousnein.bodytemperaturetesttask.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LogDao{
    @Insert
    suspend fun addLog(log:Log)

    @Query("SELECT * FROM log_table ORDER BY id DESC")
    suspend fun getAllNotes():List<Log>

    @Delete
    suspend fun deleteLog(log: Log)
}