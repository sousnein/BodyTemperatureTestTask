package com.sousnein.bodytemperaturetesttask.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "log_table")
data class Log (
    val date:String,
    val time:String,
    val temperature:Double
){
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}